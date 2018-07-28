package com.ty.study;

import com.google.gson.Gson;
import com.ty.study.pojo.Employee;
import com.ty.study.service.EmployeeService;
import com.ty.study.service.EmployeeServiceImpl;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;

import java.util.Map;

/**
 * 说明：
 *  当前测试使用的elasticsearch集群版本为 6.1.3
 *  spring-data-elasticsearch的版本为 3.1.0.RC1，目前只有3.1.x的版本支持elasticsearch6，且官方支持的版本为6.2.2
 *
 * 另外：
 *  spring-data-elasticsearch不支持实体类和索引字段的字段名称映射
 * @author relax tongyu
 * @create 2018-07-28 13:11
 **/
@Log4j2
public class TestSpringDataEs {
    private static ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-data-es.xml");
    private static EmployeeService employeeService = ctx.getBean(EmployeeServiceImpl.class);

    public static void main(String[] args) {
//        testElasticsearchTemplatAndClient();
        testCreateIndexAndSaveData();
        testSpringDataESSearch();
    }

    public static void testSpringDataESSearch(){
        log.info(employeeService.find());
    }

    public static void testCreateIndexAndSaveData(){
        employeeService.save(new Employee("Alice", "Smith"));
        employeeService.save(new Employee("Bob", "Smith"));
    }


    /**
     * 测试spring-elasticsearch提供的Template client
     */
    public static void testElasticsearchTemplatAndClient(){
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-data-es.xml");
        ElasticsearchTemplate et = (ElasticsearchTemplate) ctx.getBean("elasticsearchTemplate");

        long st = System.currentTimeMillis();
        Map setting = et.getSetting("warn_event");
        Gson gson = new Gson();
        String s = gson.toJson(setting);
        log.info(s);

        Map mapping = et.getMapping("megacorp", "employee");
        String s1 = gson.toJson(mapping);
        log.info(s1);
        log.info("耗时：{} ms", System.currentTimeMillis() - st);
    }

}
