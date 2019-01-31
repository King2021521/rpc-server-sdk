package com.zxm.rpc.registry;

import redis.clients.jedis.Jedis;

/**
 * @Author zxm
 * @Description jedisClient factory
 * @Date Create in 上午 9:36 2019/1/31 0031
 */
public class JedisClientFactory {
    private volatile static Jedis jedis;

    private JedisClientFactory() {
    }

    public static Jedis newInstance(String host, Integer port) {
        if (jedis == null) {
            synchronized (Jedis.class) {
                if (jedis == null) {
                    jedis = new Jedis(host, port);
                }
            }
        }
        return jedis;
    }
}
