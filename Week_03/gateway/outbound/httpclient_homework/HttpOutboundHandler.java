package gateway.outbound.httpclient_homework;

import gateway.filter.HttpRequestFilter;
import gateway.router.RandomRouter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;
import org.apache.http.HttpEntity;
import org.apache.http.HttpStatus;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ExecutorService;

/**
 * 转发请求的处理器
 * @author
 */
public class HttpOutboundHandler {
    private String backendUrl;
    private String[] backendUrls;
    private ExecutorService proxyService;
    private CloseableHttpAsyncClient httpClient;
    private RandomRouter randomRouter;
    private final static int serverNum = 4;

    public HttpOutboundHandler(String backendUrl) {
        this.backendUrl = backendUrl.endsWith("/") ? backendUrl.substring(0, backendUrl.length()-1) : backendUrl;
        randomRouter = new RandomRouter();
        this.backendUrls = new String[serverNum];
        this.backendUrls[0] = "http://localhost:8081";
        this.backendUrls[1] = "http://localhost:8082";
        this.backendUrls[2] = "http://localhost:8083";
        this.backendUrls[3] = "http://localhost:8088";
    }

    /**
     *
     * @param request 从 channel 那里获取来的完整数据
     * @param ctx Channel 的上下文，可以用来返回给客户端数据，这样就不用各种 return 数据了
     */
    public void handle(final FullHttpRequest request, final ChannelHandlerContext ctx) {
//        final String url = this.backendUrl + request.getUri();
        // 随机路由
        final String url = randomRouter.getRandomServer(this.backendUrls) + request.getUri();
        for (Map.Entry<String, String> e : request.headers()) {
            System.out.println(e.getKey() + " => " + e.getValue());
        }
        doGetTest(request, ctx, url);
    }

    // 上节课实现的请求
    public void doGetTest(final FullHttpRequest request, final ChannelHandlerContext ctx, final String url) {
        // Http 客户端
        HttpClientBuilder builder = HttpClientBuilder.create();
        CloseableHttpClient httpClient = builder.build();
        // URL
        HttpGet get = new HttpGet(url);
        CloseableHttpResponse response = null;
        try {
            response = httpClient.execute(get);
            int status = response.getStatusLine().getStatusCode();
            if (status != HttpStatus.SC_OK) {
                System.out.println(" ================> 请求出错了！ \nstatus: " + status + "\nresponse: " + response.toString());
            } else {
                HttpEntity entity = response.getEntity();
                System.out.println("\n\nStatus: " + response.getStatusLine().getStatusCode());
                System.out.println("Content-Type: " + entity.getContentType());
                System.out.println("Content-Length: " + entity.getContentLength());
                System.out.println("Content: " + EntityUtils.toString(entity));
            }

            // 返回请求
            ctx.write(response);
            ctx.flush();
        } catch (IOException e) {
            ctx.close();
            e.printStackTrace();
        } finally {
            try {
                if (httpClient != null) {
                    httpClient.close();
                }
                if (response != null) {
                    response.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
