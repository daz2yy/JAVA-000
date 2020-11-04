package gateway.inbound;

import gateway.outbound.httpclient_homework.HttpOutboundHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.ReferenceCountUtil;

public class HttpInboundHandler extends ChannelInboundHandlerAdapter {
    private String proxyServer;
    private HttpOutboundHandler homeworkHandler;
//    private gateway.outbound.httpclient.HttpOutboundHandler handler1;


    public HttpInboundHandler(String proxyServer) {
        this.proxyServer = proxyServer;
        homeworkHandler = new HttpOutboundHandler(this.proxyServer);
//        handler1 = new gateway.outbound.httpclient.HttpOutboundHandler(this.proxyServer);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            FullHttpRequest request = (FullHttpRequest) msg;
            // PS：不在这里加 filter，尝试用 handler 来实现 filter 了
//            handler1.handle(request, ctx);
            homeworkHandler.handle(request, ctx);
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }
}
