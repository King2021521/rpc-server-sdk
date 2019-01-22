package com.zxm.rpc.config;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @Author
 * @Description
 * @Date Create in 下午 4:33 2019/1/22 0022
 */
public class RegistryConfig {
    private String host;

    private Integer port;

    private String serverName;

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Integer getPort() {
        return port;
    }

    public void setPort(Integer port) {
        this.port = port;
    }

    public String getServerName() {
        return serverName;
    }

    public void setServerName(String serverName) {
        this.serverName = serverName;
    }

    public String getLocalHostAddrss() throws UnknownHostException{
        return InetAddress.getLocalHost().getHostAddress();
    }
}
