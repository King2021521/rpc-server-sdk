package com.zxm.rpc.remote;

import com.zxm.rpc.proxy.ProviderProxyInvoker;

/**
 * @Author zxm
 * @Description
 * @Date Create in 下午 2:36 2019/1/28 0028
 */
public class NettySocketServerFactory {
    private volatile static NettySocketServer nettySocketServer;

    private NettySocketServerFactory(){}

    public static NettySocketServer newInstance(Integer port, ProviderProxyInvoker invoker){
        if(nettySocketServer==null){
            synchronized (NettySocketServer.class){
                if(nettySocketServer==null){
                    try {
                        nettySocketServer = new NettySocketServer(port, invoker);
                    } catch (Exception e) {
                        throw new RuntimeException("netty server start exception, msg is :" + e.getMessage());
                    }
                }
            }
        }
        return nettySocketServer;
    }
}
