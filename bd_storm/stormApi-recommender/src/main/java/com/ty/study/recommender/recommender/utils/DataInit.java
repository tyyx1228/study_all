package com.ty.study.recommender.recommender.utils;

import com.ty.study.recommender.recommender.domain.Product;
import com.google.gson.Gson;
import org.junit.Test;
import redis.clients.jedis.ShardedJedis;

import java.util.List;
import java.util.Random;

/**
 * Describe: 请补充类描述
 * Author:   guyong
 * Domain:   www.itcast.cn
 * Data:     2015/12/3.
 */
public class DataInit {

//    public static void main(String[] args) {
//        initProduct();//初始化所有的商品的信息
//        initBaseUserRecommend();//初始化所有的基于用户的信息
//        initBaseItemRecommend();//初始化所有用户基于用户的推荐
//        initBaseItemResult();//初始化物品与物品之间的关联度
//        initBaseContentResult();//初始基于内容的商品关联度
//        initUser();//初始化所有的用户信息
//        initDefaultRecomend();//初始化广告位的默认推荐信息
//    }

    @Test
    public void getData() {
        ShardedJedis jedis = MyShardedJedisPool.getResource();
        //获取基于用户的推荐，用户guyong
        System.out.println("获取用户 guyong ，基于用户的推荐结果======================");
        System.out.println(jedis.hget("recom:guyong", "userCF"));
        System.out.println();
        System.out.println();

        //获取基于物品的推荐，用户guyong
        System.out.println("获取用户 guyong ，基于物品的推荐结果（离线物品）======================");
        System.out.println(jedis.hget("recom:guyong", "itemCF"));
        System.out.println();
        System.out.println();

        //打印基于物品的物品相似度
        System.out.println("打印基于物品的物品相似度======================");
        List<Product> productList = JDProduct.getProduct();
        for (Product product : productList) {
            System.out.println(product.getId() + "   " + jedis.get("recom:baseItem:" + product.getId()));
        }
        System.out.println();
        System.out.println();

        //打印基于内容的物品相似度
        System.out.println("打印基于内容的物品相似度======================");
        for (Product product : productList) {
            System.out.println(product.getId() + "   " + jedis.get("recom:baseContent:" + product.getId()));
        }
        System.out.println();
        System.out.println();

        //获取广告位121的默认推荐产品信息
        System.out.println("获取广告位121的默认推荐产品信息======================");
        System.out.println(jedis.hget("recom:default", "121"));
        System.out.println();
        System.out.println();

        //获取所有的商品信息
        System.out.println("获取所有的商品信息======================");
        for (Product product : productList) {
            System.out.println(product.getId() + "   " + jedis.get("recom:prod:" + product.getId()));
        }
        System.out.println();
        System.out.println();
    }


    @Test
    public void initDefaultRecomend() {
        ShardedJedis jedis = MyShardedJedisPool.getResource();
        List<Product> productList = JDProduct.getProduct(); //通过网络爬虫获取电商网站的商品数据
//        productList = productList.subList(20, 40);  //截取20个商品作为默认推荐商品列表
        String adId = "121";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < productList.size(); i++) {
            sb.append(productList.get(i).getId());
            if (i != productList.size() - 1) {
                sb.append(",");
            }
        }

        jedis.hset("recom:default", adId, sb.toString());
        System.out.println("为广告位121设置默认推荐产品成功." + productList.size());

    }

    /**
     * 为用户顾雍初始化基于itemCF的推荐结果
     * recom:guyong   itemCF   12121212,12121212,12121212,12121212
     * recom:guyong   userCF   12121212,12121212,12121212,12121212
     */
    @Test
    public void initBaseItemRecommend() {
        ShardedJedis jedis = MyShardedJedisPool.getResource();
        List<Product> productList = JDProduct.getProduct();
//        productList = productList.subList(10, 30);
        String user = "guyong";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < productList.size(); i++) {
            sb.append(productList.get(i).getId());
            if (i != productList.size() - 1) {
                sb.append(",");
            }
        }
        //为用户推荐基于用户的推荐产品---根据昨天的记录计算好的
        jedis.hset("recom:" + user, "itemCF", sb.toString());
        System.out.println("为用户guyong设置基于物品的推荐产品" + productList.size());
    }


    /**
     * 为用户顾雍初始化基于itemCF的推荐结果
     * recom:guyong   itemCF   12121212,12121212,12121212,12121212,
     * recom:guyong   userCF   12121212,12121212,12121212,12121212
     */
    @Test
    public void initBaseUserRecommend() {
        ShardedJedis jedis = MyShardedJedisPool.getResource();
        List<Product> productList = JDProduct.getProduct();
//        productList = productList.subList(0, 20);
        String user = "guyong";
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < productList.size(); i++) {
            sb.append(productList.get(i).getId());
            if (i != productList.size() - 1) {
                sb.append(",");
            }
        }
        jedis.hset("recom:" + user, "userCF", sb.toString());
        System.out.println("为用户guyong设置基于用户的推荐产品" + productList.size());
    }

    /**
     * 保存物品与物品之间的相似度
     * recom:baseItem:productId    121212121:4.2,34343434:3.4
     */
    @Test
    public void initBaseItemResult() {
        ShardedJedis jedis = MyShardedJedisPool.getResource();
        List<Product> productList = JDProduct.getProduct();
        int num = productList.size();
        for (int i = 0; i < num; i++) {
            Product product = productList.get(i);
            StringBuffer sb = new StringBuffer();
            for (int j = 0; j < 10; j++) {
                sb.append(productList.get(new Random().nextInt(54)).getId());
                sb.append(":" + (new Random().nextInt(6) + 1));
                if (j != 9) {
                    sb.append(",");
                }
            }
            jedis.set("recom:baseItem:" + product.getId(), sb.toString());
        }
        System.out.println("基于物品：初始化物品与物品之间的相似度关系,productId=" + productList.size());
    }

    /**
     * 保存物品与物品之间的相似度
     * recom:baseContent:productId    121212121:4.2,34343434:3.4
     */
    @Test
    public void initBaseContentResult() {
        ShardedJedis jedis = MyShardedJedisPool.getResource();
        List<Product> productList = JDProduct.getProduct();
        int num = productList.size();
        for (int i = 0; i < num; i++) {
            Product product = productList.get(i);
            StringBuffer sb = new StringBuffer();
            for (int j = 0; j < 10; j++) {
                sb.append(productList.get(new Random().nextInt(54)).getId());
                sb.append(":" + (new Random().nextInt(6) + 1));
                if (j != 9) {
                    sb.append(",");
                }
            }
            jedis.set("recom:baseContent:" + product.getId(), sb.toString());
        }
        System.out.println("基于内容：初始化物品与物品之间的相似度关系" + productList.size());
    }

    public void initUser() {
        //todo
    }

    /**
     * 初始化商品信息
     * recom:prod:productid    json,product对象转化的
     */
    @Test
    public void initProduct() {
        ShardedJedis jedis = MyShardedJedisPool.getResource();
        List<Product> productList = JDProduct.getProduct();
        for (Product product : productList) {
            String skuid = product.getId();
            jedis.set("recom:prod:" + skuid, new Gson().toJson(product));
        }
        System.out.println("初始化商品信息." + productList.size());
    }

}
