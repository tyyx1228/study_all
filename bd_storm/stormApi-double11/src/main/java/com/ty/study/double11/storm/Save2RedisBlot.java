package com.ty.study.double11.storm;

import backtype.storm.task.TopologyContext;
import backtype.storm.topology.BasicOutputCollector;
import backtype.storm.topology.OutputFieldsDeclarer;
import backtype.storm.topology.base.BaseBasicBolt;
import backtype.storm.tuple.Tuple;
import com.ty.study.double11.domain.PaymentInfo;
import com.ty.study.double11.util.DateUtils;
import com.google.gson.Gson;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

import java.util.Map;

/**
 * Describe: 请补充类描述
 * Author:   maoxiangyi
 * Domain:   www.itcast.cn
 * Data:     2015/11/3.
 */
public class Save2RedisBlot extends BaseBasicBolt {
    private static final long serialVersionUID = -1646484130063396534L;
    private JedisPool pool;

    @Override
    public void prepare(Map stormConf, TopologyContext context) {
        //change "maxActive" -> "maxTotal" and "maxWait" -> "maxWaitMillis" in all examples
        JedisPoolConfig config = new JedisPoolConfig();
        //控制一个pool最多有多少个状态为idle(空闲的)的jedis实例。
        config.setMaxIdle(5);
        //控制一个pool可分配多少个jedis实例，通过pool.getResource()来获取；
        //如果赋值为-1，则表示不限制；如果pool已经分配了maxActive个jedis实例，则此时pool的状态为exhausted(耗尽)。
        //在borrow一个jedis实例时，是否提前进行validate操作；如果为true，则得到的jedis实例均是可用的；
        config.setMaxTotal(1000 * 100);
        //表示当borrow(引入)一个jedis实例时，最大的等待时间，如果超过等待时间，则直接抛出JedisConnectionException；
        config.setMaxWaitMillis(30);
        config.setTestOnBorrow(true);
        config.setTestOnReturn(true);
        /**
         *如果你遇到 java.net.SocketTimeoutException: Read timed out exception的异常信息
         *请尝试在构造JedisPool的时候设置自己的超时值. JedisPool默认的超时时间是2秒(单位毫秒)
         */
        pool = new JedisPool(config, "127.0.0.1", 6379);
        super.prepare(stormConf, context);
    }

    public void execute(Tuple input, BasicOutputCollector collector) {
        //读取订单数据
        String paymentInfoStr = input.getStringByField("message");
        //将订单数据解析成JavaBean
        PaymentInfo paymentInfo = new Gson().fromJson(paymentInfoStr, PaymentInfo.class);
        //计算业务订单量
        Jedis jedis = pool.getResource();
        if (paymentInfo != null) {
            //计算订单的总数
            jedis.incrBy("orderTotalNum", 1);
            //计算总的销售额
            jedis.incrBy("orderTotalPrice", paymentInfo.getProductPrice());
            //计算折扣后的销售额
            jedis.incrBy("orderPromotionPrice", paymentInfo.getPromotionPrice());
            //计算实际交易额
            jedis.incrBy("orderTotalRealPay", paymentInfo.getPayPrice());
            jedis.incrBy("userNum", 1);
        }
        //计算所有一级、二级、三级、品类下业务指标
        String[] catagorys = paymentInfo.getCatagorys().split(",");
        for(int i=0;i<catagorys.length;i++){
            //计算总的订单数
            jedis.incrBy("order:cata:"+i+":"+catagorys[i]+":num:"+ DateUtils.getDate(), 1);
            //计算折扣后的销售额
            jedis.incrBy("order:cata:"+i+":"+catagorys[i]+":PromotionPrice:"+ DateUtils.getDate(), paymentInfo.getPromotionPrice());
            //计算实际交易额
            jedis.incrBy("order:cata:"+i+":"+catagorys[i]+":RealPay:"+ DateUtils.getDate(), paymentInfo.getPayPrice());
            jedis.incrBy("order:cata:"+i+":"+catagorys[i]+":userNum:"+ DateUtils.getDate(), 1);
        }

        //计算店铺的业务指标
        //计算总的销售额
        jedis.incrBy("order:shop:"+paymentInfo.getShopId()+":num:"+ DateUtils.getDate(), 1);
        //计算折扣后的销售额
        jedis.incrBy("order:shop:"+paymentInfo.getShopId()+":PromotionPrice:"+ DateUtils.getDate(), paymentInfo.getPromotionPrice());
        //计算实际交易额
        jedis.incrBy("order:shop:"+paymentInfo.getShopId()+":RealPay:"+ DateUtils.getDate(), paymentInfo.getPayPrice());
        jedis.incrBy("order:shop:"+paymentInfo.getShopId()+":userNum:"+ DateUtils.getDate(), 1);

        //店铺排序
        jedis.zincrby("order:sort:shop:num:"+ DateUtils.getDate(),1,paymentInfo.getShopId());
        jedis.zincrby("order:sort:shop:PromotionPrice:" + DateUtils.getDate(), paymentInfo.getPromotionPrice(), paymentInfo.getShopId());
        jedis.zincrby("order:sort:shop:RealPay:"+ DateUtils.getDate(),paymentInfo.getPayPrice(),paymentInfo.getShopId());
        jedis.zincrby("order:sort:shop:userNum:"+ DateUtils.getDate(),1,paymentInfo.getShopId());

        //按照分类进行排序
        //按照店铺中的商品进行排序
        //....

    }

    public void declareOutputFields(OutputFieldsDeclarer declarer) {

    }
}
