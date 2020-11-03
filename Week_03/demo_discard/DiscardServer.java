package demo_discard;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.concurrent.Future;

public class DiscardServer {
    private int port;

    public DiscardServer(int port) {
        this.port = port;
    }

    public static void main(String[] args) {
        int p = 8080;
        if (args.length > 0) {
            p = Integer.parseInt(args[0]);
        }
        DiscardServer server = new DiscardServer(p);
        server.run();
    }

    private void run() {
        // Netty 实现了多种事件轮询的类，这里用了 NIO 模型，可以配置多少线程来处理连接
        EventLoopGroup boss = new NioEventLoopGroup(1);
        EventLoopGroup worker = new NioEventLoopGroup(16);
        try {
            // 服务端启动类，对应的还有客户端启动类 BootStrap
            ServerBootstrap server = new ServerBootstrap();
            server.group(boss, worker);
            // 设定用来处理连接的 Channel 类型
            server.channel(NioServerSocketChannel.class);
            // 1. 设定 Channel 处理器，每个新的连接进来都会创建这个 Handler 来处理数据
            // 2. ChannelInitializer 是帮助我们初始化 ChannelHandler 的工具
            // 3. 通过管道 pipeline 的方式添加 Handler，依次处理进来的数据
            server.childHandler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    socketChannel.pipeline().addLast(new DiscardServerHandler());
                }
            });
            // 设置 accept 队列最大值，超过的就不接收了
            server.option(ChannelOption.SO_BACKLOG, 128);
            // option 是设置正在连接的连接，childOption 是设置 accept 之后的连接
            server.childOption(ChannelOption.SO_KEEPALIVE, true);

            // 绑定监听连接
            ChannelFuture f = server.bind(this.port).sync();
            // wait 直到 socket 关闭，在这里不会发生
            f.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            boss.shutdownGracefully();
            worker.shutdownGracefully();
        }
    }
}
