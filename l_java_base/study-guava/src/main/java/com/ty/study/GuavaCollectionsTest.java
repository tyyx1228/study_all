package com.ty.study;

import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

import java.util.*;

/**
 * guava集合操作
 *
 * guava对java集合做了扩展
 *
 * @author relax tongyu
 * @create 2018-06-19 10:20
 **/
@Slf4j
public class GuavaCollectionsTest {

    /**
     * 创建集合
     */
    @Test
    public void testCreateCollections(){
        class Elm implements Comparator<Elm>{

            @Override
            public int compare(Elm o1, Elm o2) {
                return 0;
            }
        }

        class Entity implements Comparable<Entity> {
            @Override
            public int compareTo(Entity o) {
                return 0;
            }
        }

        ArrayList<String> list = Lists.newArrayList();
        ArrayList<String> list1 = Lists.newArrayList("a", "b");

        TreeSet<Entity> set = Sets.newTreeSet();
        set.add(new Entity());
        TreeSet<Elm> set1 = Sets.newTreeSet(new Elm());
        HashSet<Integer> set2 = Sets.newHashSet(Lists.newArrayList(6, 2, 4, 1, 9));


        HashMap<String, String> map = Maps.newHashMap();
    }


    @Test
    public void testList_API(){
        ArrayList<Integer> list1 = Lists.newArrayList(1, 3 , 2, 9, 5, 4);

        log.info(Lists.reverse(list1).toString()); //将list中的元反转

        log.info(Lists.partition(list1, 4).toString());  //将list中的元素拆分成多个分区，每个分区长度为4

        log.info(
            Lists.transform(    //转换list<int>为list<String>
                list1,
                new Function<Integer, String>() {
                    @Override
                    public String apply(Integer input) {
                        return String.valueOf(input + 100);
                    }
            }).toString()
        );
    }


    /**
     * Set集合的交、并、差
     */
    @Test
    public void testSets_API(){

        HashSet<Integer> one = Sets.newHashSet(Lists.newArrayList(1, 8, 5, 4, 3, 9, 6));
        HashSet<Integer> two = Sets.newHashSet(Lists.newArrayList(1, 7, 5, 0, 3, 2, 6));

        log.info(Sets.union(one, two).toString()); //合并两个Set集合，此过程会去重    并集

        log.info(Sets.difference(one, two).toString()); //找出集合one中有而集合two中没有的元素 差集

        log.info(Sets.intersection(one, two).toString()); //找出集合one集合two中的共同元素  交集
    }


    @Test
    public void testMaps_API(){
        HashMap<String, String> map = Maps.<String, String>newHashMap();


    }



}
