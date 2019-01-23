package com.zxm.rpc.remote;

import com.alibaba.fastjson.JSONObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author zxm
 * @Description
 * @Date Create in 下午 2:39 2018/9/18 0018
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<String> {
    private ConcurrentHashMap<String,RpcResult> rpcResultMap;

    public NettyClientHandler(ConcurrentHashMap<String,RpcResult> rpcResultMap){
        this.rpcResultMap = rpcResultMap;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg){
        RpcResult rpcResult = JSONObject.parseObject(msg,RpcResult.class);
        rpcResultMap.put(rpcResult.getId(),rpcResult);
    }

}
