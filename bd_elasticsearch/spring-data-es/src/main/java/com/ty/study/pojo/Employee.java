package com.ty.study.pojo;

import lombok.Data;
import org.springframework.core.annotation.AliasFor;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;


/**
 * 注：除了Id字段外，文档对象无法映射elasticsearch中的字段，要想映射，对象字段名称必须与es中的字段名称完全相同
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
