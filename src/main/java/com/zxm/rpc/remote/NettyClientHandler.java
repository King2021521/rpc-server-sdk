package com.zxm.rpc.remote;

import com.zxm.rpc.utils.RpcResult;
import com.zxm.rpc.utils.RpcSerializer;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author zxm
 * @Description
 * @Date Create in 下午 2:39 2018/9/18 0018
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<String> {
    Logger log = LoggerFactory.getLogger(NettyClientHandler.class);
    private ConcurrentHashMap<String, RpcResult> rpcResultMap;

    public NettyClientHandler(ConcurrentHashMap<String, RpcResult> rpcResultMap) {
        this.rpcResultMap = rpcResultMap;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) {
        log.info("invoke remote [{}] success, result is:{}", ctx.channel().remoteAddress(), msg);
        RpcResult rpcResult = RpcSerializer.deSerialize(msg, RpcResult.class);
        rpcResultMap.put(rpcResult.getId(), rpcResult);
    }

}
