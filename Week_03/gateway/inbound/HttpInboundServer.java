package gateway.inbound;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.PooledByteBufAllocator;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoop;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.epoll.EpollChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import java.awt.*;

public class HttpInboundServer {
    private static final Logger logger = LoggerFactory.getLogger(HttpInboundServer.class);

    private int port;
    private String proxyServer;

    public HttpInboundServer(int port, String proxyServer) {
        this.port = port;
        this.proxyServer = proxyServer;
    }

    public void run() {
        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup worker = new NioEventLoopGroup(16);
        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            // 设置参数
            bootstrap.option(ChannelOption.SO_BACKLOG, 128);
            bootstrap.option(ChannelOption.TCP_NODELAY, true);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.option(ChannelOption.SO_RCVBUF, 32 * 1024);
            bootstrap.option(ChannelOption.SO_SNDBUF, 32 * 1024);
            bootstrap.option(ChannelOption.SO_REUSEADDR, true);
            bootstrap.option(EpollChannelOption.SO_REUSEPORT, true);
            bootstrap.childOption(ChannelOption.SO_KEEPALIVE, true);
            // ??
            bootstrap.option(ChannelOption.ALLOCATOR, PooledByteBufAllocator.DEFAULT);

            // 设置处理器等
            bootstrap.group(boss, worker);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.handler(new LoggingHandler(LogLevel.INFO));
            // 这里写错了一次：HttpInboundHandler，会报错：不是 shareHandler 无法多次加载，以及：
            // 接收到数据的时候报错：io.netty.buffer.PooledUnsafeDirectByteBuf cannot be cast to io.netty.handler.codec.http.FullHttpRequest
            // ?? 还有服务初始化的时候会报错，一系列这样：警告: Unknown channel option 'TCP_NODELAY' for channel '[id: 0x3ba2908b]'
            bootstrap.childHandler(new HttpInboundInitializer(this.proxyServer));
            // 绑定端口
            ChannelFuture f = bootstrap.bind(this.port).sync();
            logger.info("开启netty http服务器，监听地址和端口为 http://127.0.0.1:" + port + '/');
            // 等待关闭
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
