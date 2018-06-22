package com.ty.study.recommender.recommender.service.impl;


import com.ty.study.recommender.recommender.domain.Product;
import com.ty.study.recommender.recommender.domain.Template;
import com.ty.study.recommender.recommender.service.ProductService;
import com.ty.study.recommender.recommender.service.RecommendModelService;
import com.ty.study.recommender.recommender.service.RecommendService;
import com.ty.study.recommender.recommender.service.RuleService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Describe: 处理推荐逻辑
 * Author:   guyong
 * Domain:   www.itcast.cn
 * Data:     2015/12/2.
 */
public class RecommendServiceImpl implements RecommendService {
    //推荐模型
    private RecommendModelService recommendModelService;
    //针对广告位的推荐规则
    private RuleService ruleService;
    //商品服务
    private ProductService productService;

    @Override
    public List<Product> recomend(String adId, String userId, String views) {
        List<Product> recommendResult = new ArrayList<Product>();
        //判断当前广告位是否有对应的推荐模型,如果没有推荐推荐模型就返回NULL
        if (!ruleService.isExist(adId)) {
            return recommendResult;
        }
        //获取推荐对应的规则,计算需要推荐的商品数量
        Template template = ruleService.getTemplateByAdId(adId);
        //根据广告位使用推荐模型计算的结果，每个广告位都有独立的一个或者多个模型进行支撑
        recommendResult = recommend(adId, template.getNum(), userId, views);
        //对硬推广告进行设置
        setSaleAd(template.getProducts(), recommendResult);
        return recommendResult;
    }

    /**
     * 将销售出去的硬广插入其中
     *
     * @param products
     * @param recommendResult
     */
    private void setSaleAd(Map<Integer, Product> products, List<Product> recommendResult) {
        if (products.size() > 0) {
            for (Integer index : products.keySet()) {
                if (index.intValue() <= recommendResult.size() && index.intValue() >= 0) {
                    //这里的实现，是直接替代 某个位置上已经存在的结果。
                    recommendResult.set((index.intValue() - 1), products.get(index));
                }
            }
        }
    }

    /**
     * 每个广告位都有独立的推荐模型支撑，模型是不能通用的。
     *
     * @param adId    广告位的编号
     * @param needNum 广告位需要推荐的商品数量
     * @param userId  当前访问的用户编号
     * @param views   用户在当前会话其间浏览的商品
     * @return
     */
    private List<Product> recommend(String adId, int needNum, String userId, String views) {
        List<Product> list = new ArrayList<Product>();
        if ("其他广告".equals(adId)) {
            //属于其他广告的模型
        }
        //如果广告编号等于121,表示猜你喜欢的广告位
        if ("121".equals(adId)) {
            //猜你喜欢有两个维度的分析，
            //猜与你相似的用户喜欢什么，猜你当前感兴趣的物品相似的物品
            //猜与你相似的用户是离线计算，通过批处理计算基于用户的协同过滤推荐。
            //  猜物品的相似度，
            //          根据上一次浏览或下单的基于物品进行推荐
            //              浏览次数越多的商品，推荐的权重越高
            //          根据你本次浏览的商品，实时的获取基于物品推荐的结果
            //              浏览次数越多的商品，推荐的权重越高
            //          根据你本次浏览的商品，实时基于物品内容的推荐结果进行推荐
            //              浏览次数越多的商品，根据共同属性推荐的商品比重越高
            //  以上推荐结果进行混合推荐
            //          去重，排序，截取多余的商品，产生推荐结果
            //  补全或添加强推的广告位

            //获取基于用户的相似度推荐结果，批处理计算出来的
            List<String> baseUserList = recommendModelService.recommendByUserCF(userId, needNum);
            List<Product> baseUserProducts = productService.baseInfo(baseUserList);
            checkProduct(baseUserProducts);


            //获取基于物品的离线推荐结果，前一天晚上计算出来的,根据用户昨天浏览的商品编号计算的
            List<String> baseItemList = recommendModelService.recommendByItemCF(userId, needNum);
            List<Product> baseItemProducts = productService.baseInfo(baseItemList);
            checkProduct(baseItemProducts);


            //获取基于物品的的实时推荐结果，实时计算出来的，根据用户今天浏览的商品编号计算出来的
            List<String> baseItemListReal = recommendModelService.recommendByItemCF(userId, needNum, views);
            List<Product> baseItemProductsReal = productService.baseInfo(baseItemListReal);
            checkProduct(baseItemProductsReal);


            //获取基于内容的实时推荐结果，根据用户当前浏览的商品属性，实时计算出来的。重复浏览的商品的有较高的排序
            List<String> baseContentList = recommendModelService.recommendByContent(userId, needNum, views);
            List<Product> baseContentProducts = productService.baseInfo(baseContentList);
            checkProduct(baseContentProducts);


            //获取默认的推荐结果
            List<String> defaultIdsList = recommendModelService.defaultRecommend(adId);
            List<Product> defaultList = productService.baseInfo(defaultIdsList);
            checkProduct(defaultList);

            //对推荐结果进行排序，排序算法根据需求来的
            //第一位：基于物品的实时推荐结果 ---------------------AAAAAAAA
            //第二位：基于用户的离线推荐结果 ---------------------BBBBBBBB
            //第三位：基于物品的离线推荐结果 ---------------------CCCCCCC
            //第四位：基于内容的实时推荐结果 ---------------------DDDDDDD
            //第五位：基于物品的实时推荐结果 ---------------------AAAAAAAA
            //第六位：基于用户的离线推荐结果 ---------------------BBBBBBBB
            for (int i = 1; i <= needNum; i++) {
                if ((i % 6 == 1) || (i % 6 == 5)) {
                    //封装基于物品的实时推荐结果,如果元素不够，就从默认的推荐推荐结果中获取
                    getFirstValidItem(list, baseItemProductsReal, defaultList);
                } else if ((i % 6 == 2) || (i % 6 == 0)) {
                    //封装基于用户的离线推荐结果，如果元素不够，就从默认的推荐推荐结果中获取
                    getFirstValidItem(list, baseUserProducts, defaultList);
                } else if (i % 6 == 3) {
                    //封装基于物品的离线推荐结果，如果元素不够，就从默认的推荐推荐结果中获取
                    getFirstValidItem(list, baseItemProducts, defaultList);
                } else {
                    //封装基于内容的离线推荐结果，如果元素不够，就从默认的推荐推荐结果中获取
                    getFirstValidItem(list, baseContentProducts, defaultList);
                }
            }
        }
        return list;
    }

    /**
     * 从原始List中获取一个有效的元素存放到目标List中
     * 如果原始List中没有足够多的元素，就从默认推荐的结果中获取。
     *
     * @param tarList 目标List
     * @param orgList 原始List
     */
    private void getFirstValidItem(List<Product> tarList, List<Product> orgList, List<Product> defaultList) {
        if (orgList == null || orgList.size() < 1) {
            tarList.add(defaultList.get(0));
            defaultList.remove(0);
        }
        Product product = null;
        //将原始的list中的product，抽取一个放入到到目标list中。
        // 1、先获取一个product
        //2、担心目标list中，已经包含了product
        //3、判断目标list中是否已经了该product
        //      4、如果有，再循环，
        do {
            if (orgList.size() > 0) {
                product = orgList.get(0);
                orgList.remove(0);
            } else {
                product = null;
            }
        } while (product != null && tarList.contains(product));
        if (product != null) {
            tarList.add(product);
        }
    }

    private boolean isProductInTarList(Product product, List<Product> tarList) {
        boolean isContains = tarList.contains(product);
        System.out.println(isContains);
        return isContains;
    }


    private void checkProduct(List<Product> recommendList) {
        int size = recommendList.size();
        for (int i = 0; i < size; i++) {
            //如果商品状态为下线状态，将商品移除掉
            if (recommendList.get(i).getStatus() != 1) {
                recommendList.remove(i);
                i--;
                size--;
            }
        }
    }

    public List<Product> defaultRecommend(String adId) {
        return productService.baseInfo(recommendModelService.defaultRecommend(adId));
    }

    public RecommendModelService getRecommendModelService() {
        return recommendModelService;
    }

    public void setRecommendModelService(RecommendModelService recommendModelService) {
        this.recommendModelService = recommendModelService;
    }

    public RuleService getRuleService() {
        return ruleService;
    }

    public void setRuleService(RuleService ruleService) {
        this.ruleService = ruleService;
    }

    public ProductService getProductService() {
        return productService;
    }

    public void setProductService(ProductService productService) {
        this.productService = productService;
    }
}
