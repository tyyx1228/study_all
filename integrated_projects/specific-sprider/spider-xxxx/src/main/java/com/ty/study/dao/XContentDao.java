package com.ty.study.dao;

import com.ty.study.model.ContentDetail;
import com.ty.study.util.DbUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.session.SqlSession;

import java.util.HashMap;

import static com.google.common.collect.Maps.newHashMap;

/**
 * @author relax tongyu
 * @create 2018-06-09 19:22
 **/
@Slf4j
public class XContentDao {

    public int insertData(ContentDetail cd){
        try (SqlSession session = DbUtils.sqlSession()){
            return session.insert("xxxx-content.insertData", cd);
        }catch (Exception e){
            log.error("", e);
        }
        return 0;
    }


    public long countByUrl(String url){
        try (SqlSession session = DbUtils.sqlSession()){
            HashMap<String, String> param = newHashMap();
            param.put("param", url);
            return session.selectOne("xxxx-content.countByUrl", param);
        }catch (Exception e){
            log.error("", e);
        }
        return 0;
    }

}
