package org.csu.mypetstore.hook;


import org.csu.mypetstore.Constants;
import org.springframework.stereotype.Component;
import redis.clients.jedis.Jedis;

import javax.annotation.PreDestroy;

/**
 * @Author:高益基
 * @Description:destroy钩子函数，清空redisDB。
 */
@Component
public class backDestroy {

    @PreDestroy
    public void destroy() {
       if(Constants.REDIS_MODE){
           Jedis jedis = new Jedis("localhost", 6379);
           jedis.select(1);
           jedis.flushDB();
           jedis.close();
       }
    }
}


