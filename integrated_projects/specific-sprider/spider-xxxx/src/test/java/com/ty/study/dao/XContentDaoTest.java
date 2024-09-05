package com.ty.study.dao;

import com.ty.study.model.ContentDetail;
import org.apache.ibatis.session.SqlSession;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.Date;

import static org.junit.Assert.*;

public class XContentDaoTest {

    private XContentDao xContentDao ;

    @Before
    public void init(){
        xContentDao = new XContentDao();
    }

    @Test
    public void insertData() {


        ContentDetail cd = new ContentDetail();
        cd.setUrl("ss");
        cd.setBaseUrl("sdf");
        cd.setStartTime(new Date());
        cd.setContentDeployTime(new Date());
        System.out.println(xContentDao.insertData(cd));
    }

    @Test
    public void countByUrl(){
        System.out.println(xContentDao.countByUrl("sd"));
    }
}