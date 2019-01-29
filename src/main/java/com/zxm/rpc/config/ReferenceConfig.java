package com.zxm.rpc.config;

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

    public String getRemoteHost(){
        return remoteHost;
    }

    public void setRemoteHost(String remoteHost){
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

    public ReferenceConfig(){
    }

}
