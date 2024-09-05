package com.ty.study.page;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

/**
 * 网页内容
 *
 * @author relax tongyu
 * @create 2018-06-07 22:53
 **/
@Data
public class PageContent {

    private String partentUrl;

    private String childUrl;

    private String childUrlName;

}
