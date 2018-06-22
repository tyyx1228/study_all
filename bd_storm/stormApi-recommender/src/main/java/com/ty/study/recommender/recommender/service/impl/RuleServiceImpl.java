package com.ty.study.recommender.recommender.service.impl;


import com.ty.study.recommender.recommender.domain.Product;
import com.ty.study.recommender.recommender.domain.Template;
import com.ty.study.recommender.recommender.service.RuleService;

import java.util.HashMap;
import java.util.Map;

/**
 * Describe: 请补充类描述
 * Author:   maoxiangyi
 * Domain:   www.itcast.cn
 * Data:     2015/12/2.
 */
public class RuleServiceImpl implements RuleService {

    @Override
    public Template getTemplateByAdId(String adId) {
        Template template = new Template();
        template.setNum(12);
        template.setType(1);
        Map<Integer, Product> map = new HashMap<>();
        //String skuid, String title, String price, String producturl, String pic
        map.put(new Integer(1), new Product("1739475", "我的推荐，你的选择", "199999", "http://item.jd.com/1739475.html", "http://img13.360buyimg.com/n6/s488x350_jfs/t2476/214/1387387908/47235/648d8471/5653ca40N964e7ee4.jpg"));
        template.setProducts(map);
        return "121".equals(adId) ? template : null;
    }

    @Override
    public boolean isExist(String adId) {
        return "121".equals(adId);
    }
}
