package com.itheima.jobs;

import com.itheima.constant.RedisConstant;
import com.itheima.utils.TxyunUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Set;

public class ClearImgJob {
    @Autowired
    private JedisPool jedisPool;

    public void clearImg() {
        Set<String> sdiff = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PIC_DB_RESOURCES);
        for (String s : sdiff) {
            TxyunUtils.DeleteObject(s);
            jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES, s);
            System.out.println("clean"+s);
        }
    }
}
