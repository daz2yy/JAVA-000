package gateway.outbound.httpclient;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.concurrent.FutureCallback;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.nio.client.HttpAsyncClient;
import org.apache.http.nio.reactor.IOReactor;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.omg.PortableServer.CurrentPackage.NoContext;

import java.io.IOException;
import java.util.concurrent.*;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * 转发请求的处理器
 * @author
 */
public class HttpOutboundHandler {
    private String backendUrl;
    private ExecutorService proxyService;
    private CloseableHttpAsyncClient httpClient;

    public HttpOutboundHandler(String backendUrl) {
        this.backendUrl = backendUrl.endsWith("/") ? backendUrl.substring(0, backendUrl.length()-1) : backendUrl;
        int cores = Runtime.getRuntime().availableProcessors() * 2;
        int keepaliveTime = 1000;
        int queueSize = 2048;
        // 拒绝策略，CallerRunsPolicy 策略是：添加到线程池失败就主线程自己干了，脾气暴躁！
        RejectedExecutionHandler handler = new ThreadPoolExecutor.CallerRunsPolicy();
        proxyService = new ThreadPoolExecutor(cores, cores,
                keepaliveTime, TimeUnit.MILLISECONDS, new ArrayBlockingQueue<>(queueSize),
                new NamedThreadFactory("proxyService"), handler);

        IOReactorConfig ioConfig = IOReactorConfig.custom()
                .setConnectTimeout(1000)
                .setSoTimeout(1000)
                .setIoThreadCount(cores)
                .setRcvBufSize(32 * 1024)
                .build();

        httpClient = HttpAsyncClients.custom()
                .setMaxConnTotal(40)
                .setMaxConnPerRoute(8)
                .setDefaultIOReactorConfig(ioConfig)
                .setKeepAliveStrategy(((httpResponse, httpContext) -> 6000))
                .build();
        httpClient.start();
    }

    /**
     *
     * @param request 从 channel 那里获取来的完整数据
     * @param ctx Channel 的上下文，可以用来返回给客户端数据，这样就不用各种 return 数据了
     */
    public void handle(final FullHttpRequest request, final ChannelHandlerContext ctx) {
        final String url = this.backendUrl + request.getUri();
        proxyService.submit(()->fetchGet(request, ctx, url));
    }

    public void fetchGet(final FullHttpRequest request, final ChannelHandlerContext ctx, final String url) {
        final HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader(HTTP.CONN_DIRECTIVE, HTTP.CONN_KEEP_ALIVE);
        // 线程池执行 http，需要写回到函数处理
        httpClient.execute(httpGet, new FutureCallback<HttpResponse>() {
            @Override
            public void completed(HttpResponse httpResponse) {
                httpResponse(request, ctx, httpResponse);
            }

            @Override
            public void failed(Exception e) {
                httpGet.abort();
                e.printStackTrace();
            }

            @Override
            public void cancelled() {
                httpGet.abort();
            }
        });
    }

    private void httpResponse(final FullHttpRequest request, final ChannelHandlerContext ctx, final HttpResponse httpResponse) {
        FullHttpResponse response = null;
        try {
            byte[] body = EntityUtils.toByteArray(httpResponse.getEntity());
            response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(body));
            response.headers().set("Content-Type", "application/json");
            // 为什么返回请求的长度？
            response.headers().setInt("Content-Length", Integer.parseInt(httpResponse.getFirstHeader("Content-Length").getValue()));
        } catch (IOException e) {
            e.printStackTrace();
            response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
            ctx.close();
        } finally {
            if (request != null) {
                if (!HttpUtil.isKeepAlive(request)) {
                    ctx.write(response).addListener(ChannelFutureListener.CLOSE);
                } else {
                    ctx.write(response);
                }
            }
            ctx.flush();
        }
    }
}
