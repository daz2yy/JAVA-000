package gateway.outbound.netty_homework;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.*;

public class NettyHttpClient {
    private final static int ReConnectTime = 3;
    private Channel channel;

    public void connect(String host, int port, FullHttpRequest request, ChannelHandlerContext ctx) {
        EventLoopGroup worker = new NioEventLoopGroup();

        try {
            Bootstrap b = new Bootstrap();
            // 客户端不需要设置这些参数？!
//            b.option(ChannelOption.SO_BACKLOG, 128);
//            b.option(ChannelOption.SO_KEEPALIVE, true);
//            b.option(ChannelOption.TCP_NODELAY, true);
//            b.option(ChannelOption.SO_SNDBUF, 32 * 1024);
//            b.option(ChannelOption.SO_RCVBUF, 32 * 1024);

            b.group(worker);
            b.channel(NioSocketChannel.class);
            b.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    // 客户端接收到的是httpResponse响应，所以要使用HttpResponseDecoder进行解码
                    socketChannel.pipeline().addLast(new HttpClientCodec());
                    // 客户端发送的是httprequest，所以要使用HttpRequestEncoder进行编码 ??
                    socketChannel.pipeline().addLast(new HttpObjectAggregator(1024*1024));
                    NettyOutBoundHandler handler = new NettyOutBoundHandler();
                    handler.targetCtx = ctx;
                    socketChannel.pipeline().addLast(handler);
                }
            });
            // 连接服务
            ChannelFuture f = b.connect(host, port).sync();
            channel = f.channel();
            // 转发请求
            channel.writeAndFlush(request);
            // 等待socket关闭
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            worker.shutdownGracefully();
        }
    }

    public void request(FullHttpRequest request) {
        channel.writeAndFlush(request);
    }

}
