package com.atguigu.yygh.hosp.mongodb;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

/**
 * @author zzj
 * @date 2022/10/24
 */
@SpringBootTest
public class TestMongoUser {
    @Autowired
    private MongoTemplate mongoTemplate;
    @Test
    public void add(){
        User user = new User();
        user.setAge(23);
        user.setName("张三3");
        user.setEmail("4932203@qq.com");
        User insert = mongoTemplate.insert(user);
        System.out.println("insert = " + insert);
    }
    @Test
    public void findUsersPage(){
        Query query = new Query(Criteria.where("age").is(20));
        List<User> users = mongoTemplate.find(query, User.class);
        System.out.println("users = " + users);
    }
}
