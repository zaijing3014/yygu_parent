package com.atguigu.yygh.hosp.repository;

import com.atguigu.yygh.model.hosp.Hospital;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * @author zzj
 * @date 2022/10/25
 */
public interface HospitalRepository extends MongoRepository<Hospital,String> {
    Hospital findByHoscode(String hoscode);
}
