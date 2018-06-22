package com.ty.study.double11.view;

import com.ty.study.double11.util.DateUtils;
import redis.clients.jedis.Jedis;

import java.util.Set;

public class Double11View {
    public static void main(String[] args) {
        Jedis jedis = new Jedis("127.0.0.1",6379);
        //获取双十一店铺排行榜前十名
        while(true){

            System.out.println("==============店铺 订单数 排行榜单==============================");
            String numSortKey = "order:sort:shop:num:" + DateUtils.getDate();
            Set<String> numSort = jedis.zrevrange(numSortKey, 0, 10);
            for (String name : numSort) {
                System.out.println(name + "         "
                        + jedis.zrevrank(numSortKey, name) + "            "
                        + jedis.zscore(numSortKey, name));
            }

            System.out.println("==============店铺 优惠价 排行榜单==============================");
            String promotionPriceSortKey = "order:sort:shop:PromotionPrice:" + DateUtils.getDate();
            Set<String> promotionPriceSort = jedis.zrevrange(promotionPriceSortKey, 0, 10);
            for (String name : promotionPriceSort) {
                System.out.println(name + "\t"
                        + jedis.zrevrank(promotionPriceSortKey, name) + "            "
                        + jedis.zscore(promotionPriceSortKey, name));
            }

            System.out.println("==============店铺 成交价 排行榜单==============================");
            String realPaySortKey = "order:sort:shop:RealPay:" + DateUtils.getDate();
            Set<String> realPaySort = jedis.zrevrange(realPaySortKey, 0, 10);
            for (String name : realPaySort) {
                System.out.println(name + "         "
                        + jedis.zrevrank(realPaySortKey, name) + "            "
                        + jedis.zscore(realPaySortKey, name));
            }

            System.out.println("==============店铺 订单人数 排行榜单============================");
            String userNumSortKey = "order:sort:shop:userNum:" + DateUtils.getDate();
            Set<String> userNumSort = jedis.zrevrange("order:sort:shop:userNum:" + DateUtils.getDate(), 0, 10);
            for (String name : userNumSort) {
                System.out.println(name + "         "
                        + jedis.zrevrank(userNumSortKey, name) + "            "
                        + jedis.zscore(userNumSortKey, name));
            }
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            System.out.println();
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
