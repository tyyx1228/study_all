package com.ty.attraction.entity;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import lombok.Data;
import lombok.ToString;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author relax
 * @date 2018/10/21 13:58
 */
@ToString
@Data
public class AttractionInfo {

    //景点id
    private int id;

    //景点名称
    private String attractionName;

    //景点评分
    private String commentScore;

    //图文详解
    private String detailUrl;

    //是否收费产品
    private Boolean showAsPoi;

    //景点类别id
    private int categoryId;

    //景点所属类别名称
    private String categoryName;

    //地区
    private Integer districtId;

    //景点映像标签
    List<String> tagNameList = Lists.newArrayList();

    //景点详情概要
    private AttractionDetail detail;

    /**
     * Map<K, V>  K为表头
     * @return
     */
    public Map<String, Object> getExportDataModel(){
        HashMap<String, Object> dataModel = Maps.newHashMap();
        dataModel.put("景点编号", getId());
        dataModel.put("景点名称", getAttractionName());
        dataModel.put("评分", getCommentScore());
        dataModel.put("图文详解", getDetailUrl());
        dataModel.put("是否收费产品", getShowAsPoi());
        dataModel.put("景点类别ID", getCategoryId());
        dataModel.put("景点所属类别名称", getCategoryName());
        dataModel.put("景点所在地ID",getDistrictId());
        dataModel.put("映像标签", String.join(",", tagNameList));

        if(detail!=null){
            dataModel.put("特色", detail.getFeature());
            dataModel.put("介绍", detail.getIntrodcution());
            dataModel.put("地址", detail.getAddress());
            dataModel.put("开放时间", detail.getOpenTimeDesc());
            dataModel.put("提示Tips", detail.getTips());
            dataModel.put("交通", detail.getTraffic());
        }else{
            dataModel.put("特色", null);
            dataModel.put("介绍", null);
            dataModel.put("地址", null);
            dataModel.put("开放时间", null);
            dataModel.put("提示Tips", null);
            dataModel.put("交通", null);
        }
        return dataModel;
    }

}
