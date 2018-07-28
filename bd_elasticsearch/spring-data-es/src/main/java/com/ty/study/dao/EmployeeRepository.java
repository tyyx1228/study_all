package com.ty.study.dao;

import com.ty.study.pojo.Employee;
import org.springframework.data.elasticsearch.annotations.Query;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.data.repository.Repository;

import java.util.List;

/**
 * @author relax tongyu
 * @create 2018-07-28 13:12
 **/
public interface EmployeeRepository extends ElasticsearchRepository<Employee, String> {

    public Employee findByFirstName(String firstName);

    public List<Employee> findByLastName(String lastName);


}
