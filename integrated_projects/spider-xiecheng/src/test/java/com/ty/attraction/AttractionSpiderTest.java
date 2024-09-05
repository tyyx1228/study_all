package com.ty.attraction;

import org.junit.Test;

import static org.junit.Assert.*;

public class AttractionSpiderTest {

    @Test
    public void categoryList() {

        String str = "观大道尽收眼底；远眺北京城，天气好的话，甚至能看到南边<a href=\"http://you.ctrip.com/sight/beijing1/5170.html\" target=\"_blank\" class=\"inset-p-link\">景山公园</a>的轮廓。";

        String s = str.replaceAll("<[^>]+>", "");
        System.out.println(s);
    }
}