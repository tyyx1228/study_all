package com.ty.study.recommender.recommender.service.impl.model;

import com.ty.study.recommender.recommender.cache.RedisHandler;
import com.ty.study.recommender.recommender.domain.Item;
import com.ty.study.recommender.recommender.service.RecommendModelService;
import org.apache.commons.lang3.StringUtils;

import java.util.*;

/**
 * Describe: 提供不同模型的返回值
 * <p>
 * Author:   maoxiangyi
 * Domain:   www.itcast.cn
 * Data:     2015/12/2.
 */
public class RecommendModelServiceImpl implements RecommendModelService {

    /**
     * 基于用户的相似度进行推荐，这个是算法模型计算好的结果，批处理。
     *
     * @param userId 用户的编号
     * @param num    需要推荐的数量
     * @return 返回推荐的商品编号，顺序就是推荐的优先级。
     */
    @Override
    public List<String> recommendByUserCF(String userId, int num) {
        /**
         * 放在一个人的推荐结果存Hash数据结构中
         * field等于模型的标志
         * value等于模型给该用户推荐的结果
         */
        List<String> list = getProductIdListByCache("recom:" + userId, "userCF");
        return list.size() > num ? list.subList(0, num) : list;
    }

    /**
     * 基于物品的相似度，并根据用户前一天的会话的浏览记录进行推荐
     *
     * @param userId 用户的编号
     * @param num    需要的商品的数量
     * @return 返回推荐的商品编号，顺序就是推荐的优先级。
     */
    @Override
    public List<String> recommendByItemCF(String userId, int num) {
        return recommendByItemCF(userId, num, null);
    }

    @Override
    public List<String> recommendByContent(String userId, int num, String views) {
        List<String> list = new ArrayList<>();
        //如果浏览记录等于空，直接获取昨天的推荐结果。
        if (views == null) {
            return getProductIdListByCache("recom:" + userId, "itemCF");
        }
        list = realContentRecomment(userId, num, views);
        return list.size() > num ? list.subList(0, num) : list;
    }

    private List<String> realContentRecomment(String userId, int num, String views) {
        //设置当前商品推荐结果的权重
        Map<String, Double> idMap = new HashMap<>();
        String[] ids = views.split(",");
        for (String id : ids) {
            if (idMap.get(id) == null) {
                idMap.put(id, 1.0);
            } else {
                idMap.put(id, idMap.get(id).doubleValue() + 0.1);
            }
        }
        //获取每个Item的推荐结果,将推荐结果存放在List中
        List<Item> items = new ArrayList<>();
        for (String id : idMap.keySet()) {
            //获取当前商品的权重
            double weight = idMap.get(id);
            //获取当前商品的相似商品列表
            String value = getContentByItemId(id);
            //判断Redis中获取的数据不为空
            if (StringUtils.isNotBlank(value)) {
                String[] itemStrs = value.split(",");
                for (String itemStr : itemStrs) {
                    String[] arr = itemStr.split(":");
                    Item item = new Item(arr[0], Double.parseDouble(arr[1]) * weight);
                    if (!items.contains(item)) {
                        items.add(item);
                    }
                }
            }
        }
        //对结果进行排序
        Collections.sort(items);
        return getIds(items);
    }

    private String getContentByItemId(String id) {
        return RedisHandler.getString("recom:baseContent:" + id);
    }

    /**
     * 根据用户的浏览记录，获取推荐结果
     * 如果用户的浏览记录中有重复的商品，对这个商品计算出来的权重值*1.1。
     *
     * @param userId
     * @param needNum
     * @param views
     * @return
     */
    @Override
    public List<String> recommendByItemCF(String userId, int needNum, String views) {
        List<String> list = new ArrayList<>();
        //如果浏览记录等于空，直接获取昨天的推荐结果。
        if (views == null) {
            return getProductIdListByCache("recom:" + userId, "itemCF");
        }
        //实时计算基于物品相似度的推荐结果
        list = realItemCF(userId, needNum, views);
        return list.size() > needNum ? list.subList(0, needNum) : list;
    }

    /**
     * 如果用户浏览的次数越多，我们可以认为根据当前商品推荐出来的结果用户更倾向
     * 多浏览一次的权重值为0.1
     *
     * @param userId
     * @param needNum
     * @param views
     * @return
     */
    private List<String> realItemCF(String userId, int needNum, String views) {
        //设置当前商品推荐结果的权重
        Map<String, Double> idMap = new HashMap<>();
        String[] ids = views.split(",");
        for (String id : ids) {
            if (idMap.get(id) == null) {
                idMap.put(id, 1.0);
            } else {
                idMap.put(id, idMap.get(id).doubleValue() + 0.1);
            }
        }
        //获取每个Item的推荐结果,将推荐结果存放在List中
        List<Item> items = new ArrayList<>();
        for (String id : idMap.keySet()) {
            //获取当前商品的权重
            double weight = idMap.get(id);
            //获取当前商品的相似商品列表
            String value = getBaseItemByItemId(id);
            //判断Redis中获取的数据不为空
            if (StringUtils.isNotBlank(value)) {
                String[] itemStrs = value.split(",");
                for (String itemStr : itemStrs) {
                    String[] arr = itemStr.split(":");
                    //使用当前物品的相似度 乘以 多次浏览的权重值
                    Item item = new Item(arr[0], Double.parseDouble(arr[1]) * weight);
                    if (!items.contains(item)) {
                        items.add(item);
                    }
                }
            }
        }
        //对结果进行排序
        Collections.sort(items);
        return getIds(items);
    }

    private List<String> getIds(List<Item> items) {
        List<String> list = new ArrayList<>();
        for (Item item : items) {
            list.add(item.getId());
        }
        return list;
    }

    private String getBaseItemByItemId(String id) {
        return RedisHandler.getString("recom:baseItem:" + id);
    }

    /**
     * 从Redis中获取推荐的商品信息
     *
     * @param key
     * @param field
     * @return
     */
    private List<String> getProductIdListByCache(String key, String field) {
        List<String> list = new ArrayList<>();
        String recommends = RedisHandler.getValueByHashField(key, field);
        if (StringUtils.isNotBlank(recommends)) {
            String[] items = recommends.split(",");
            for (String item : items) {
                if (item.contains(":")) {
                    list.add(item.substring(0, item.indexOf(":")));
                } else {
                    list.add(item);
                }
            }
        }
        return list;
    }

    @Override
    public List<String> defaultRecommend(String adId) {
        /**
         * 将每个推荐位的默认推荐结果存放在Hash结构中
         * field等于推荐位的编号
         * value等于推荐位的默认推荐结果，多个商品编号使用逗号分隔
         */
        return getProductIdListByCache("recom:default", adId);
    }
}
