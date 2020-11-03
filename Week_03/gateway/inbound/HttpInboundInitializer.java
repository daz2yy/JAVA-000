package gateway.inbound;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpServerCodec;

public class HttpInboundInitializer extends ChannelInitializer<SocketChannel> {
    private String proxyServer;

    public HttpInboundInitializer(String proxyServer) {
        this.proxyServer = proxyServer;
    }
    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        ChannelPipeline p = channel.pipeline();
        // 解析 Http 头的 Handler
        p.addLast(new HttpServerCodec());
        // 解析 Http Content 的 Handler，并设置最大的 body 长度，超过了？
        p.addLast(new HttpObjectAggregator(1024*1024));
        // 自定义过滤器
        p.addLast(new FilterRunnerHandler());
        // 逻辑处理
        p.addLast(new HttpInboundHandler(this.proxyServer));
    }
}
