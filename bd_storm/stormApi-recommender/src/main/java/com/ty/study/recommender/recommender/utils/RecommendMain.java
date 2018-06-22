package com.ty.study.recommender.recommender.utils;

import com.ty.study.recommender.recommender.domain.Product;
import com.ty.study.recommender.recommender.service.RecommendService;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.List;

/**
 * Describe: 请补充类描述
 * Author:   maoxiangyi
 * Domain:   www.itcast.cn
 * Data:     2015/12/4.
 */
public class RecommendMain {
    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("spring-config.xml");
        RecommendService recommendService = (RecommendService) ctx.getBean("recommendService");
        long start = System.currentTimeMillis();
        //根据用户guyong的浏览记录，为广告位121，推荐24个商品
        // cookie  http协议
        List<Product> list = recommendService.recomend("121", "guyong", "10041396144,1205650,1026254997");
        for (Product product : list) {
            System.out.println(product);
        }
        System.out.println("推荐耗时：" + (System.currentTimeMillis() - start));
    }
}
