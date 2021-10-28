package com.psi.springboot;

import com.psi.springboot.util.MessageConstant;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;

@SpringBootTest
class JobApplicationTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    //调度器
    @Scheduled(cron = "0 0/1 * * * ?")//1分钟执行一次
    public void clean() {
        SetOperations<String, String> setOperations = redisTemplate.opsForSet();
        Set<String> difference = setOperations.difference(MessageConstant.ALL_PIC, MessageConstant.MYSQL_PIC);
        //删除差集图片
        for (String fileName : difference) {
            File file = new File("D:/Upload/YLAssistant/" + fileName);
            if (file.exists()) {
                file.delete();
            }
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
        String format = sdf.format(new Date());
        System.out.println("定时清理残余图片！" + format);
    }

}
