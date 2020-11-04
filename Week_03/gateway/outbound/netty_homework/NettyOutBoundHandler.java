package gateway.outbound.netty_homework;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

import java.net.URI;
import java.nio.charset.Charset;

import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class NettyOutBoundHandler extends ChannelInboundHandlerAdapter {
    public ChannelHandlerContext targetCtx;
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
//        URI uri = new URI("/user/get");
//        FullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_0, HttpMethod.GET, uri.toASCIIString());
//        request.headers().add(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
//        request.headers().add(HttpHeaderNames.CONTENT_LENGTH,request.content().readableBytes());
//        ctx.writeAndFlush(request);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            FullHttpResponse response = (FullHttpResponse) msg;
            if (response.getStatus() != HttpResponseStatus.ACCEPTED) {
                response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
            }
            ByteBuf buf = response.content();
            String result = buf.toString(CharsetUtil.UTF_8);
            System.out.println("response -> " + result);
            targetCtx.write(response).addListener(ChannelFutureListener.CLOSE);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }
}
