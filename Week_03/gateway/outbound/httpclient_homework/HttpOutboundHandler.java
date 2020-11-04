package gateway.outbound.httpclient_homework;

import gateway.filter.HttpRequestFilter;
import gateway.outbound.netty_homework.NettyHttpClient;
import gateway.router.RandomRouter;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpUtil;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

/**
 * 转发请求的处理器
 * @author
 */
public class HttpOutboundHandler {
    private String backendUrl;
    private List<String> backendUrls;
    private ExecutorService proxyService;
    private CloseableHttpAsyncClient httpClient;
    private RandomRouter randomRouter;

    public HttpOutboundHandler(String backendUrl) {
        this.backendUrl = backendUrl.endsWith("/") ? backendUrl.substring(0, backendUrl.length()-1) : backendUrl;
        randomRouter = new RandomRouter();
        this.backendUrls = new ArrayList<String>();
        this.backendUrls.add("http://www.baidu.com");
        this.backendUrls.add("http://www.baidu.com");
    }

    /**
     *
     * @param request 从 channel 那里获取来的完整数据
     * @param ctx Channel 的上下文，可以用来返回给客户端数据，这样就不用各种 return 数据了
     */
    public void handle(final FullHttpRequest request, final ChannelHandlerContext ctx) {
//        final String url = this.backendUrl + request.getUri();
        // 随机路由
        final String url = randomRouter.route(this.backendUrls) + request.getUri();
        for (Map.Entry<String, String> e : request.headers()) {
            System.out.println(e.getKey() + " => " + e.getValue());
        }
        doGetTest(request, ctx, url);
        // netty 客户端请求
//        nettyGet(request, ctx, url);
    }

    // 上节课实现的请求
    public void doGetTest(final FullHttpRequest request, final ChannelHandlerContext ctx, final String url) {
        // Http 客户端
        HttpClientBuilder builder = HttpClientBuilder.create();
        CloseableHttpClient httpClient = builder.build();
        FullHttpResponse response_return = null;
        // URL
        HttpGet get = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(get);
            int status = response.getStatusLine().getStatusCode();
            if (status != HttpStatus.SC_OK) {
                response_return = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
                System.out.println(" ================> 请求出错了！ \nstatus: " + status + "\nresponse: " + response.toString());
            } else {
                // 这里不能打开，reponse 的内容貌似只能读取一次，再次获取数据会报 StreamClose
//                HttpEntity entity = response.getEntity();
//                System.out.println("\n\nStatus: " + response.getStatusLine().getStatusCode());
//                System.out.println("Content-Type: " + entity.getContentType());
//                System.out.println("Content-Length: " + entity.getContentLength());
//                System.out.println("Content: " + EntityUtils.toString(entity, StandardCharsets.UTF_8));

                byte[] body = EntityUtils.toByteArray(response.getEntity());
                response_return = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(body));
            }
        } catch (IOException e) {
            response_return = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
            ctx.close();
            e.printStackTrace();
        } finally {
            // 返回请求
            if (request != null) {
                if (!HttpUtil.isKeepAlive(request)) {
                    ctx.write(response_return).addListener(ChannelFutureListener.CLOSE);
                } else {
                    ctx.write(response_return);
                }
            }
            try {
                if (response != null) {
                    response.close();
                }
                if (httpClient != null) {
                    httpClient.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            ctx.flush();
        }
    }

    // NettyClient
    public void nettyGet(final FullHttpRequest request, final ChannelHandlerContext ctx, final String url) {
        NettyHttpClient client = new NettyHttpClient();
        client.connect("www.baidu.com", 80, request, ctx);
    }

}
