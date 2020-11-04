package gateway.inbound;

import gateway.filter.HttpRequestFilter;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.ReferenceCountUtil;

public class FilterRunnerHandler extends ChannelInboundHandlerAdapter {
    private HttpRequestFilter filter;

    public FilterRunnerHandler() {
        filter = new HttpRequestFilter();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        try {
            FullHttpRequest request = (FullHttpRequest) msg;
            boolean result = filter.filter(request, ctx);
            // 过滤器不通过，请求抛弃
            if (!result) {
                ReferenceCountUtil.release(msg);
                ctx.writeAndFlush("Http Request Error.").addListener(ChannelFutureListener.CLOSE);
                return;
            }
            // 下一个 pipeline channel 继续执行
            ctx.fireChannelRead(msg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
