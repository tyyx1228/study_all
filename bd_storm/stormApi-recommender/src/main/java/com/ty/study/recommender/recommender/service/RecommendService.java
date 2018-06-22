package com.ty.study.recommender.recommender.service;

import com.ty.study.recommender.recommender.domain.Product;

import java.util.List;

/**
 * Describe: 请补充类描述
 * Author:   guyong
 * Domain:   www.itcast.cn
 * Data:     2015/12/2.
 */
public interface RecommendService {

    /**
     * 根据推荐位和用户实现个性化推荐
     *
     * @param adId   广告位的编号
     * @param userId 用户编号
     * @param views   用户当前会话浏览的商品
     * @return 返回混合推荐的商品
     */
    List<Product> recomend(String adId, String userId, String views);

    /**
     * 根据推荐位进行默认的推荐
     * 不同的推荐位有不同的默认推荐策略
     * @param adId
     * @return
     */
    List<Product> defaultRecommend(String adId);

}
