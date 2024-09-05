package com.ty.study.model;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Date;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * 内容详情（明细页）
 *
 * @author relax tongyu
 * @create 2018-06-08 15:07
 **/
@Setter
@Getter
@ToString
public class ContentDetail extends Page {

    /**
     * 内容（文章）发布时间
     */
    private Date contentDeployTime;

    /**
     * 内容（文章）作者
     */
    private String auth;

    /**
     * desc
     */
    private String desc;

    /**
     * 评论内容
     */
    private List<String> comments = newArrayList();


    public String print() {
        int startOffset = super.getClass().getName().length() + 2;
//        int endOffset = super.toString().lastIndexOf(")");
        String superContent =super.toString().substring(startOffset);
        String thisContent = this.toString();
        return thisContent.substring(0, thisContent.length()-1) + ", " + superContent ;
    }
}
