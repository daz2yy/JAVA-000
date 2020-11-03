package gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

public class HttpRequestFilter {
    public boolean runFilter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        fullRequest.headers().set("nio", "zhuangxiaoda");
        return true;
    }
}
