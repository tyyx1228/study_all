package com.ty.study.tags;

import org.arabidopsis.ahocorasick2.AhoCorasick;
import org.arabidopsis.ahocorasick2.SearchResult;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Set;
// 斗地主（单机） -> 斗地主
public class AcSearch {
    public static String searchValue(AhoCorasick ac, String name) {
        try {
            Iterator<SearchResult> iter = ac.search(name.getBytes());
            SearchResult result = null;
            if (iter.hasNext()) {
                result = iter.next();
                Set<String> sid = result.getOutputs();
                if (!sid.isEmpty()) {
                    Object[] array = sid.toArray();
                    Arrays.sort(array);
                    return array[array.length - 1].toString();
                }
            }
            return "0";
        } catch (Exception e) {
            return "0";
        }
    }
}
