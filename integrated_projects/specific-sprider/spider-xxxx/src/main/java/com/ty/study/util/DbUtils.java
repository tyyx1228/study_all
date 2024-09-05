package com.ty.study.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author relax tongyu
 * @create 2018-06-09 18:53
 **/
@Slf4j
public class DbUtils {

    private static SqlSessionFactory sqlSessionFactory;

    static {
        try {
            InputStream in = Resources.getResourceAsStream("mybatis-config.xml");
            sqlSessionFactory = new SqlSessionFactoryBuilder().build(in, "mysql");
        }catch (IOException e){
            log.error("", e);
        }

    }

    public static SqlSession sqlSession(){
        return sqlSessionFactory.openSession(true);
    }

    public static void main(String[] args) {
        log.info(sqlSessionFactory.openSession().toString());
    }
}
