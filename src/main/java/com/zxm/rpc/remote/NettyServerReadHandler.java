package com.zxm.rpc.remote;

import com.alibaba.fastjson.JSONObject;
import com.zxm.rpc.proxy.ProviderProxyInvoker;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author zxm
 * @Description 服务端数据读取处理器
 * @Date Create in 下午 2:37 2018/6/15 0015
 */
public class NettyServerReadHandler extends SimpleChannelInboundHandler<String> {
    Logger log = LoggerFactory.getLogger(NettyServerReadHandler.class);

    private ProviderProxyInvoker invoker;

    public NettyServerReadHandler(ProviderProxyInvoker invoker) {
        if (invoker != null) {
            this.invoker = invoker;
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        channelReadExecute(ctx.channel(), msg);
    }

    private void channelReadExecute(Channel channel, String msg) {
        RpcProtocol rpcProtocol = (RpcProtocol) JSONObject.parse(msg);
        Object result = invoker.invoke(rpcProtocol);

        RpcResult rpcResult = new RpcResult();
        rpcResult.setId(rpcProtocol.getId());
        rpcResult.setValue(result);
        channel.writeAndFlush(JSONObject.toJSONString(rpcResult));

    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        ctx.close();
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        log.info("client " + ctx.channel().remoteAddress() + " connected");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
    }
}
