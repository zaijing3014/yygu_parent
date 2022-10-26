package com.atguigu.yygh.hosp.mongodb;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @author zzj
 * @date 2022/10/24
 */
@Data
//将此类声明为一个mongodb 文档 指定这个类型所对应的 集合 名称
@Document("User")
public class User {
    @Id
    private String id;
    private String name;
    private Integer age;
    private String email;
    private String createDate;
}