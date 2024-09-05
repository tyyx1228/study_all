package com.ty.study.model;

import lombok.Data;
import org.jsoup.nodes.Document;

import java.util.Date;

/**
 * 网页内容
 *
 * @author relax tongyu
 * @create 2018-06-07 22:53
 **/
@Data
public class Page {

    /**
     * 当前页面来自哪里
     */
    private String fromtUrl;

    /**
     * 当前页面url
     */
    private String url;

    /**
     * 当前页面标题
     */
    private String urlName;

    /**
     * 当前页面文档对象
     */
    private Document doc;

    /**
     * 犾取当前文档对象的开始时间
     */
    private Date startTime;


}
