package com.ty.study.service;

import com.ty.study.dao.EmployeeRepository;
import com.ty.study.pojo.Employee;
import lombok.Setter;

import java.util.List;

/**
 * @author relax tongyu
 * @create 2018-07-28 15:38
 **/
@Setter
public class EmployeeServiceImpl implements EmployeeService{
    private EmployeeRepository employeeRepository;
    public List<Employee> find(){
        return employeeRepository.findByLastName("Smith");
    }

    public void save(Employee employee){
        employeeRepository.save(employee);
    }


}
