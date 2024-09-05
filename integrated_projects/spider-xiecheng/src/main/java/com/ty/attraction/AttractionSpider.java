package com.ty.attraction;

import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ty.attraction.entity.AttractionDetail;
import com.ty.attraction.entity.AttractionInfo;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Connection;
import org.jsoup.Jsoup;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 爬取景点
 *
 * @author relax
 * @date 2018/10/21 13:39
 */
@Slf4j
public class AttractionSpider {
    private static HashMap<String, String> header = Maps.newHashMap();
    static {
        header.put("Content-Type", "application/json");
        header.put("Accept", "application/json;charset=UTF-8");
        header.put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36");
    }


    public static void main(String[] args) throws IOException {

//        "https://m.ctrip.com/webapp/you/sightList/category.html?DistrictId=1&DistrictName=%E5%8C%97%E4%BA%AC&CateId=4931&navBarStyle=white"
        String listUrl = "https://m.ctrip.com/restapi/soa2/13342/json/getDestAggregation?_fxpcqlniredt=09031033410320707575";
        String params = "{\"Index\":1,\"Count\":20,\"DistrictId\":1,\"CategoryId\":1711,\"lat\":39,\"lon\":116,\"subCategoryId\":0,\"head\":{\"cid\":\"09031033410320707575\",\"ctok\":\"\",\"cver\":\"1.0\",\"lang\":\"01\",\"sid\":\"8888\",\"syscode\":\"09\",\"auth\":null,\"extension\":[{\"name\":\"protocal\",\"value\":\"https\"}]},\"contentType\":\"json\"}";

        Map<Integer, String> categoryList = categoryList(listUrl, params);

        ExcelWriter writer = ExcelUtil.getWriter("d:/InsightInfo.xlsx");

        for(Map.Entry<Integer, String> pair: categoryList.entrySet()){
            List<AttractionInfo> attractionInfoList = attractionSampleList(listUrl, pair.getKey(), 39, 116);

            int columns = 1;
            ArrayList<Map<String, Object>> dataModelForExport = Lists.newArrayList();
            for(AttractionInfo info: attractionInfoList){
                info.setCategoryName(pair.getValue());
                Map<String, Object> dataModel = info.getExportDataModel();
                dataModelForExport.add(dataModel);

                if(columns==1){
                    columns = dataModel.size();
                }
            }

            writer.setSheet(pair.getValue());
            writer.merge(columns-1, pair.getValue());
            writer.write(dataModelForExport);
            writer.flush();
        }
        writer.close();
    }


    public static AttractionInfo detailPage(AttractionInfo attractionInfo) throws IOException {
        String detailUrl = "https://m.ctrip.com/restapi/soa2/13342/json/getsightdetail?_fxpcqlniredt=09031033410320707575";
        String paramsPattern = "{\"SightId\":%s,\"districtId\":%s,\"isSearchLandingPage\":false,\"head\":{\"cid\":\"09031033410320707575\",\"ctok\":\"\",\"cver\":\"1.0\",\"lang\":\"01\",\"sid\":\"8888\",\"syscode\":\"09\",\"auth\":null,\"extension\":[{\"name\":\"protocal\",\"value\":\"https\"}]},\"contentType\":\"json\"}";
        String params = String.format(paramsPattern, attractionInfo.getId(), attractionInfo.getDistrictId());
        String body = Jsoup.connect(detailUrl)
                .requestBody(params)
                .headers(header)
                .ignoreContentType(true)
                .method(Connection.Method.POST)
                .execute().body();
        JSONObject json = JSONUtil.parseObj(body);
        String feature = json.getStr("feature");
        String introdcution = json.getStr("introdcution");
        String address = json.getStr("address");
        String openTimeDesc = json.getStr("openTimeDesc");
        String tips = json.getStr("tips");
        String traffic = json.getStr("traffic");

        AttractionDetail detail = new AttractionDetail();
        detail.setFeature(AttractionDetail.formatChinese(feature));
        detail.setOpenTimeDesc(AttractionDetail.formatChinese(openTimeDesc));
        detail.setIntrodcution(AttractionDetail.formatChinese(introdcution));
        detail.setAddress(AttractionDetail.formatChinese(address));
        detail.setTips(AttractionDetail.formatChinese(tips));
        detail.setTraffic(AttractionDetail.formatChinese(traffic));


        attractionInfo.setDetail(detail);
        return attractionInfo;

    }


    public static List<AttractionInfo> attractionSampleList(String tagUrl, int categoryId, int lat, int lon) throws IOException {
        String paramsTemplate = "{\"Index\":1,\"Count\":20,\"DistrictId\":1,\"CategoryId\":%s,\"lat\":%s,\"lon\":%s,\"subCategoryId\":0,\"head\":{\"cid\":\"09031033410320707575\",\"ctok\":\"\",\"cver\":\"1.0\",\"lang\":\"01\",\"sid\":\"8888\",\"syscode\":\"09\",\"auth\":null,\"extension\":[{\"name\":\"protocal\",\"value\":\"https\"}]},\"contentType\":\"json\"}";
        String params = String.format(paramsTemplate, categoryId, lat, lon);
        String body = Jsoup.connect(tagUrl)
                .requestBody(params)
                .headers(header)
                .ignoreContentType(true)
                .method(Connection.Method.POST)
                .execute().body();

        JSONObject json = JSONUtil.parseObj(body);

        JSONArray list = json.getJSONArray("list");
        List<AttractionInfo> resList = Lists.newArrayList();
        for(Object o : list){
            JSONObject obj = JSONUtil.parseObj(o);
            Integer id = obj.getInt("id");
            String name = obj.getStr("name");
            String commentScore = obj.getStr("commentScore");
            List<String> tagNameList = Lists.newArrayList();
            JSONArray tagList = obj.getJSONArray("tagNameList");
            if(tagList!=null) {
                tagNameList = tagList.toList(String.class);
            }
            String h5Url = obj.getStr("h5Url");
            Boolean showAsPoi = obj.getBool("showAsPoi");
            Integer districtId = obj.getInt("districtId");

            log.debug("name={}, tagNameList={}, commentScore={}, h5Url={}, showAsPoi={}",
                    name, tagNameList==null ? "" : String.join(",", tagNameList), commentScore, h5Url, showAsPoi);

            AttractionInfo attractionInfo = new AttractionInfo();
            attractionInfo.setAttractionName(name);
            attractionInfo.setCommentScore(commentScore);
            attractionInfo.setId(id);
            attractionInfo.setDetailUrl(h5Url);
            attractionInfo.setTagNameList(tagNameList);
            attractionInfo.setShowAsPoi(showAsPoi);
            attractionInfo.setCategoryId(categoryId);
            attractionInfo.setDistrictId(districtId);


            detailPage(attractionInfo);


            resList.add(attractionInfo);
        }
        return resList;
    }


    /**
     * 获取标签页
     * @param url
     * @param params
     * @return
     * @throws IOException
     */
    public static Map<Integer, String> categoryList(String url, String params) throws IOException {
        String body = Jsoup.connect(url)
                .requestBody(params)
                .headers(header)
                .ignoreContentType(true)
                .method(Connection.Method.POST)
                .execute().body();
        JSONObject json = JSONUtil.parseObj(body);
        JSONArray categoryListJson = json.getJSONArray("categoryList");
        Map<Integer, String> categoryList = Maps.newHashMap();
        for(Object o : categoryListJson){
            JSONObject catagory = JSONUtil.parseObj(o);
            String categoryId = catagory.getStr("categoryId");
            String categoryName = catagory.getStr("categoryName");
            if(categoryId!=null){
                categoryList.put(Integer.valueOf(categoryId), categoryName);
            }
        }
        return categoryList;
    }
}
