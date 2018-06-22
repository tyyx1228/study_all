package com.ty.study.mystorm.wordcount;

import com.ty.study.mystorm.core.MyTopologyBuilder;

/**
 * 1、环环相扣
 *      Spout---->collector---->Bolt1----->collector---->Bolt2
 *      Spout: spout.nextTuple(){   collector.emit(new Values())}
 *      Storm : collector1    queue1
 *      Bolt1:
 *          while(t){
 *              collector.get() 获取一个数据
 *              bolt1.execute(Tuple input){
 *                  collector.emit(new Values())
 *      Storm : collector2   queue2
 *      Bolt2:
 *          while(t){
 *              collector2.get() 获取一个数据
 *              bolt1.execute(Tuple input){
 *                  collector.emit(new Values())
 * 2、并发度
 * Spout---->collector---->Bolt1----->collector---->Bolt2
 *                        Bolt1----->collector---->Bolt2
 *                        Bolt1----->collector---->Bolt2
 *
 *3、分组策略
 *
 */
public class DriverMain {
    public static void main(String[] args) {
        MyTopologyBuilder  myTopologyBuilder = new MyTopologyBuilder();
        myTopologyBuilder.setSpout("spout", new MySentenceSpout(), 1);
        myTopologyBuilder.setBolt("bolt1", new MySplitBolt(), 1);
        myTopologyBuilder.setBolt("bolt2", new MyWordCountBolt(), 1);
        myTopologyBuilder.setBolt("bolt3", new MyPrintBolt(), 1);
        myTopologyBuilder.setBolt("bolt4", new TheEnd(), 1);
        myTopologyBuilder.submit();
    }
}
