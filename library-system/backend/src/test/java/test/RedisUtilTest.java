package test;

import cn.cd.App;
import cn.cd.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest(classes = App.class)
@Slf4j
public class RedisUtilTest {
    @Autowired
    private RedisUtil redisUtil;

    @Test
    public void setTest(){
        redisUtil.set("username","xiaowang");
    }

    @Test
    public void setExpireTest(){
        redisUtil.set("username","xiaowang22", 6L);
    }
}
