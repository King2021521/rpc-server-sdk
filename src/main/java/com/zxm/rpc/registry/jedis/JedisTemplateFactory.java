package com.zxm.rpc.registry.jedis;

/**
 * @Author
 * @Description
 * @Date Create in 上午 10:48 2019/1/31 0031
 */
public class JedisTemplateFactory {
    private volatile static JedisTemplate template;

    private JedisTemplateFactory() {
    }

    public static JedisTemplate newInstance(String host, Integer port) {
        if (template == null) {
            synchronized (JedisTemplate.class) {
                if (template == null) {
                    template = new JedisTemplate(host, port);
                }
            }
        }
        return template;
    }
}
