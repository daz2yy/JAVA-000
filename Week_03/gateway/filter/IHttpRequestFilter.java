package gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

public interface IHttpRequestFilter {
    
    boolean filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx);
    
}
