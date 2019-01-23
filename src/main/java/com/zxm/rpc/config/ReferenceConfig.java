package com.zxm.rpc.config;

import com.zxm.rpc.remote.NettyClientBootstrap;
import com.zxm.rpc.remote.RpcResult;
import io.netty.channel.socket.SocketChannel;

/**
 * @Author
 * @Description
 * @Date Create in 上午 10:47 2019/1/23 0023
 */
public class ReferenceConfig {
    //ms
    private long timeout = 5000;

    private String remoteHost;

    private Integer remotePort;

    private NettyClientBootstrap nettyClientBootstrap;

    public String getRemoteHost(){
        return remoteHost;
    }

    public void setRemoteHost(){
        this.remoteHost = remoteHost;
    }

    public Integer getRemotePort() {
        return remotePort;
    }

    public void setRemotePort(Integer remotePort) {
        this.remotePort = remotePort;
    }

    public long getTimeout() {
        return timeout;
    }

    public void setTimeout(long timeout) {
        this.timeout = timeout;
    }

    public void initBootstrap(){
        try {
            nettyClientBootstrap = new NettyClientBootstrap(remotePort,remoteHost);
        } catch (Exception e) {
        }
    }

    public SocketChannel getSocketChannel(){
        if(this.nettyClientBootstrap==null){
            throw new RuntimeException("nettyClientBootstrap is not init!");
        }
        return this.nettyClientBootstrap.getSocketChannel();
    }

    public RpcResult getRpcResult(String id){
        return this.nettyClientBootstrap.getRpcResult(id);
    }

    public void removeRpcResult(String id){

    }

    public ReferenceConfig(){
    }

}
