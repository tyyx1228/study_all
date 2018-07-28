package com.ty.study.pojo;

import lombok.Data;
import org.springframework.core.annotation.AliasFor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;


/**
 * @author relax tongyu
 * @create 2018-07-28 13:16
 **/
@Data
@Document(indexName = "customer", type = "customer", createIndex = true)
public class Employee {

    @Id
    private String id;

    private String firstName;

    private String lastName;

    public Employee() {
    }

    public Employee(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
}
