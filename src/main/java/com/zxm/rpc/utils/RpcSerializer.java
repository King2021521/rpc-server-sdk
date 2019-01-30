package com.zxm.rpc.utils;

import com.alibaba.fastjson.JSONObject;

/**
 * @Author zxm
 * @Description
 * @Date Create in 下午 3:54 2019/1/30 0030
 */
public class RpcSerializer {

    /**
     * 序列化
     *
     * @param object
     * @return
     */
    public static String serialize(Object object) {
        if (null == object) {
            throw new IllegalArgumentException("serialize object is not null");
        }
        return JSONObject.toJSONString(object);
    }

    /**
     * 反序列化
     *
     * @param clazz
     * @param object
     * @param <T>
     * @return
     */
    public static <T> T deSerialize(String object, Class<T> clazz) {
        return JSONObject.parseObject(object, clazz);
    }
}
