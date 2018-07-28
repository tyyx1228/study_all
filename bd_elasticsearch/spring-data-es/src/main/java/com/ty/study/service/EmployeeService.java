package com.ty.study.service;

import com.ty.study.pojo.Employee;

import java.util.List;

/**
 * @author relax tongyu
 * @create 2018-07-28 15:50
 **/
public interface EmployeeService {
    List<Employee> find();

    void save(Employee e);
}
