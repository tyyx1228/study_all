package com.ty.attraction.entity;

import lombok.Data;
import lombok.ToString;
import org.apache.commons.lang.StringUtils;

/**
 * @author relax
 * @date 2018/10/21 20:24
 */
@Data
public class AttractionDetail {

    //特色
    private String feature;

    //介绍
    private String introdcution;

    //地址
    private String address;

    //开放时间
    private String openTimeDesc;

    //提示
    private String tips;

    //交通
    private String traffic;


    public static String formatChinese(String s){
        if(StringUtils.isBlank(s)){
            return s;
        }

        return s.replaceAll("\\u201c", "").replaceAll("\\u201d", "")
                .replaceAll("<p class=\"inset-p\">", "")
                .replaceAll("</p>", "")
                .replaceAll("<br />", "")
                .replaceAll("<br/>", "")
                .replaceAll("<p>", "")
                .replaceAll("\\n", "")
                .replaceAll("\n", "")
                .replaceAll("\\u201c", "")
                .replaceAll("\\u201d", "")
                .replaceAll("<[^>]+>", "");
    }

}
