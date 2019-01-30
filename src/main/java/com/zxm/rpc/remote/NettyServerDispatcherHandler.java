package com.zxm.rpc.remote;

import com.zxm.rpc.proxy.ProviderProxyInvoker;
import com.zxm.rpc.utils.RpcProtocol;
import com.zxm.rpc.utils.RpcResult;
import com.zxm.rpc.utils.RpcSerializer;
import io.netty.channel.Channel;

/**
 * @Author zxm
 * @Description
 * @Date Create in 下午 3:34 2019/1/30 0030
 */
public class NettyServerDispatcherHandler implements Runnable {
    private ProviderProxyInvoker invoker;

    private Channel channel;
    private String msg;

    public NettyServerDispatcherHandler(ProviderProxyInvoker invoker, Channel channel, String msg) {
        this.invoker = invoker;
        this.channel = channel;
        this.msg = msg;
    }

    @Override
    public void run() {
        RpcProtocol rpcProtocol = RpcSerializer.deSerialize(msg, RpcProtocol.class);
        Object response = invoker.invoke(rpcProtocol);
        channel.writeAndFlush(RpcSerializer.serialize(rpcResultWrapper(rpcProtocol.getId(), response)));
    }

    private RpcResult rpcResultWrapper(String id, Object response) {
        RpcResult rpcResult = new RpcResult();
        rpcResult.setId(id);
        rpcResult.setValue(response);
        return rpcResult;
    }
}
