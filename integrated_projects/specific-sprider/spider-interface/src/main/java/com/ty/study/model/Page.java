package com.ty.study.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;

import java.io.Serializable;
import java.util.Date;

/**
 * 网页内容
 *
 * @author relax tongyu
 * @create 2018-06-07 22:53
 **/
@Setter
@Getter
@ToString
public class Page implements Serializable{

    /**
     * 爬取深度
     */
    private long depth = 1;

    /**
     * 当前页面深度
     */
    private long currentDepth = 0;

    /**
     * 资源
     */
    private String baseUrl;

    /**
     * 当前页面来自哪里
     */
    private String fromUrl;

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
     * 访问当前文档对象的开始时间
     */
    private Date startTime;


    public boolean isRoot(){
        return StringUtils.isEmpty(fromUrl);
    }


}
