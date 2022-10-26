package com.atguigu.yygh.hosp.repository;

import com.atguigu.yygh.model.hosp.Department;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @author zzj
 * @date 2022/10/25
 */
@Repository
public interface DepartmentRepository extends MongoRepository<Department,String> {
    Department findByHoscodeAndDepcode(String hoscode, String depcode);

    Department getDepartmentByHoscodeAndDepcode(String hoscode, String depcode);
}
