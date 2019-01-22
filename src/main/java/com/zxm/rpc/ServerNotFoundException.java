package com.zxm.rpc;

/**
 * @Author
 * @Description
 * @Date Create in 下午 5:08 2019/1/22 0022
 */
public class ServerNotFoundException extends RuntimeException {
    public ServerNotFoundException(String message) {
        super(message);
    }
}
