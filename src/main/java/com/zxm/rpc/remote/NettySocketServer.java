package com.zxm.rpc.remote;

import com.zxm.rpc.proxy.ProviderProxyInvoker;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * @Author zxm
 * @Description netty服务端
 * @Date Create in 下午 2:19 2018/6/15 0015
 */
@ChannelHandler.Sharable
public class NettySocketServer implements Runnable{
    private static final Log log = LogFactory.getLog(NettySocketServer.class);

    private int port;

    private ProviderProxyInvoker invoker;

    public NettySocketServer(Integer port, ProviderProxyInvoker invoker) throws Exception {
        if (null == port) {
            this.port = 8888;
        } else {
            this.port = port.intValue();
        }

        if (invoker != null) {
            this.invoker = invoker;
        }
    }

    @Override
    public void run() {
        try {
            this.init(port);
        } catch (Exception e) {
        }
    }

    private void init(int port) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap bootstrap = new ServerBootstrap();
            bootstrap.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    //保持连接数
                    .option(ChannelOption.SO_BACKLOG, 1024)
                    //有数据立即发送
                    .childOption(ChannelOption.TCP_NODELAY, true)
                    //保持长连接
                    .childOption(ChannelOption.SO_KEEPALIVE, true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel socketChannel) {
                            socketChannel
                                    .pipeline()
                                    .addLast(new StringDecoder(), new StringEncoder(), new NettyServerReadHandler(invoker));
                        }
                    });

            ChannelFuture f = bootstrap.bind(port).sync();

            if (f.isSuccess()) {
                log.info("netty server started success!!!\r\n（Copyright© Nicholas.Tony)");
            } else {
                log.info("long connection started fail");
            }


            f.channel().closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
