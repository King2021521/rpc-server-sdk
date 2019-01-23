package com.zxm.rpc.remote;

import java.io.Serializable;

/**
 * @Author
 * @Description
 * @Date Create in 下午 4:41 2019/1/23 0023
 */
public class RpcResult implements Serializable{
    private static final long serialVersionUID = -8478844905397248185L;
    private String id;

    private Object value;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Object getValue() {
        return value;
    }

    public void setValue(Object value) {
        this.value = value;
    }
}
