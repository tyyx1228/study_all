package com.ty.study.recommender.recommender.service;



import com.ty.study.recommender.recommender.domain.Product;

import java.util.List;

/**
 * Describe: 提供商品信息查询，包括类目，价格等属性。
 * Author:   maoxiangyi
 * Domain:   www.itcast.cn
 * Data:     2015/12/2.
 */
public interface ProductService {

    /**
     * 根据商品编号获取商品的信息，包括类目，价位，是否上下线等
     * @param productIds 商品编号
     * @return 返回商品列表
     */
    List<Product> baseInfo(List<String> productIds);


}
