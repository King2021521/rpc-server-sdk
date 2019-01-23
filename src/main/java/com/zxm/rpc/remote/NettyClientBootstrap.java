package com.zxm.rpc.remote;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author zxm
 * @Description netty client
 * @Date Create in 下午 2:37 2018/9/18 0018
 */
public class NettyClientBootstrap {
    private int port;
    private String host;
    private SocketChannel socketChannel;

    private ConcurrentHashMap<String,RpcResult> rpcResultMap;

    public NettyClientBootstrap(int port, String host) throws Exception {
        this.host = host;
        this.port = port;
        rpcResultMap = new ConcurrentHashMap<>();
        start();
    }

    private void start() throws Exception {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class);
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
        bootstrap.option(ChannelOption.TCP_NODELAY, true);
        bootstrap.group(eventLoopGroup);
        bootstrap.remoteAddress(this.host, this.port);
        bootstrap.handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel socketChannel) {
                socketChannel.pipeline().addLast(new StringDecoder(), new StringEncoder(), new NettyClientHandler(rpcResultMap));
            }
        });
        ChannelFuture future = bootstrap.connect(this.host, this.port).sync();
        if (future.isSuccess()) {
            socketChannel = (SocketChannel) future.channel();
            System.out.println("connect server  success|");
        }
    }

    public int getPort() {
        return this.port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public SocketChannel getSocketChannel() {
        return socketChannel;
    }

    public void setSocketChannel(SocketChannel socketChannel) {
        this.socketChannel = socketChannel;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public RpcResult getRpcResult(String id){
        return rpcResultMap.get(id);
    }

    public void removeRpcResult(String id){
        rpcResultMap.remove(id);
    }
}

