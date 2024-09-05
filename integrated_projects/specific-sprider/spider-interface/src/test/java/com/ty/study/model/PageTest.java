package com.ty.study.model;

import org.junit.Test;

public class PageTest {

    @Test
    public void stoString() {
        Page page = new Page();
        page.setBaseUrl("sdflkdf");
        System.out.println(page);


        ContentDetail contentDetail = new ContentDetail();
        System.out.println(contentDetail.print());
    }
}