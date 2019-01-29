package com.zxm.rpc.remote;

/**
 * @Author zxm
 * @Description
 * @Date Create in 下午 2:36 2019/1/28 0028
 */
public class NettySocketClientFactory {
    private volatile static NettyClientBootstrap nettyClientBootstrap;

    private NettySocketClientFactory(){}

    public static NettyClientBootstrap newInstance(Integer port, String host){
        if(nettyClientBootstrap==null){
            synchronized (NettyClientBootstrap.class){
                if(nettyClientBootstrap==null){
                    try {
                        nettyClientBootstrap = new NettyClientBootstrap(port, host);
                    } catch (Exception e) {
                        throw new RuntimeException("netty client init exception, msg is :" + e.getMessage());
                    }
                }
            }
        }
        return nettyClientBootstrap;
    }
}
