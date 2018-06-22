package com.ty.study.recommender.recommender.service;

import java.util.List;

/**
 * Describe: 获取各推荐模型产生的推荐结果
 * Author:   maoxiangyi
 * Domain:   www.itcast.cn
 * Data:     2015/12/2.
 */
public interface RecommendModelService {

    List<String> recommendByUserCF(String userId, int num);

    List<String> recommendByItemCF(String userId, int num);

    List<String> defaultRecommend(String adId);

    List<String> recommendByItemCF(String userId, int needNum, String views);

    List<String> recommendByContent(String userId, int num, String views);
}
