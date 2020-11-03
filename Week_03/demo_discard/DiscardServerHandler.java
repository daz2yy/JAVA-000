package demo_discard;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class DiscardServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        ByteBuf in = (ByteBuf)msg;
//        final ByteBuf time = ctx.alloc().buffer(4);
        try {
             while (in.isReadable()) {
                 // 服务端控制台打印信息
                 System.out.println(in.readByte());
//                 System.out.flush();

                 // 返回给客户端
//                 time.writeInt((int) (System.currentTimeMillis() / 1000L + 2208988800L));
//                 ctx.writeAndFlush(time);
             }
        } finally {
            // ByteBuf 类型需要手动释放内存，不在 JVM 内存管理中
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        // 关闭连接的时候，发生了异常
        cause.printStackTrace();
        ctx.close();
    }
}
