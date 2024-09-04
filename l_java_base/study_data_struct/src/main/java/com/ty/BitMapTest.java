package com.ty;

import org.junit.Test;
import sun.security.util.BitArray;

import java.util.*;

/**
 * @author: tongyu
 * @date: 2024/8/6 10:54
 * @email: tongyu@powerbeijing.com
 */
public class BitMapTest {

    @Test
    public void testFindBitMap(){
        int[] ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};

        BitArray bitArray = new BitArray(10 + 1);
        for (int anInt : ints) {
            bitArray.set(anInt, true);
        }

        int tofind = 1;

        boolean b = tofind< bitArray.length() && bitArray.get(tofind);
        if(b){
            System.out.println("存在");
        }
    }


    /**
     *  有序的数组中找出重复的数值
     */
    @Test
    public void testRepeatBitMap(){
        // 初始化数据集
        Random random = new Random();
        int maxNum = 10000000;
        int minNum = 1;

        List<Integer> ints = new ArrayList();
        for (int i = 0; i < maxNum; i++) {
            int num = random.nextInt(maxNum);
            if(num==0){
                continue;
            }
            ints.add(num);
        }

        order(ints);
// 22.3     2230
//  3.40    0340
//        System.out.println("原始数据集：" + ints);

        BitArray bitArray = new BitArray(maxNum+1);

        // 找重复的数，同时自动排序
        Map<Integer, Integer> cnt = new HashMap<>();
        long start = System.currentTimeMillis();
        for (int anInt : ints) {
            boolean b = bitArray.get(anInt);
            if(b){
//                System.out.println(anInt + "重复 +1");
                cnt.put(anInt, cnt.getOrDefault(anInt, 0) + 1);
                continue;
            }
            bitArray.set(anInt, true);
        }
        long end = System.currentTimeMillis();
        System.out.println("排序&查重耗时：" + (end-start) + " ms");
//        System.out.println("重复次数：" + cnt);


        // 输出排序后的数值列表
        ArrayList<Integer> orderResut = new ArrayList<>();
        for (int i = 0; i < bitArray.length(); i++) {
            if(bitArray.get(i)){
                orderResut.add(i);
                // 重复值补全输出
                for (int j = 0; j < cnt.getOrDefault(i, 0); j++) {
                    orderResut.add(i);
                }
            }
        }
//        System.out.println("排序结果集：" + orderResut);
    }



    public void order(List<Integer> list){
        ArrayList<Integer> integers = new ArrayList<>();
        for (Integer integer : list) {
            integers.add(integer);
        }
        long s = System.currentTimeMillis();
        Collections.sort(integers);
        long e = System.currentTimeMillis();
        System.out.println("Java集合排序耗时：" +  (e-s) + " ms");
    }
}
