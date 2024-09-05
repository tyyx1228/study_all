package com.ty.study.parser;

import com.ty.study.DateUtils;
import com.ty.study.RequestUtil;
import com.ty.study.dao.XContentDao;
import com.ty.study.model.ContentDetail;
import com.ty.study.model.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;
import org.jsoup.nodes.TextNode;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

import static com.google.common.collect.Lists.newArrayList;
import static com.google.common.collect.Sets.newHashSet;

/**
 * @author relax tongyu
 * @create 2018-06-08 16:11
 **/
@Slf4j
public class XParser implements Parser<Page, ContentDetail>{

    private XContentDao xcd =  new XContentDao();

    @Override
    public ContentDetail parse(Page page) {

        ContentDetail detail = new ContentDetail();
        detail.setFromUrl(page.getUrl());
        detail.setStartTime(new Date());
        detail.setBaseUrl(page.getBaseUrl());
        detail.setUrlName(page.getUrlName());

        Document doc = page.getDoc();
        Elements targetA = doc.getElementsByTag("a");

        //寻找下载地址
        for(Element e : targetA){
            if(e.text().contains("rmdown")){
                detail.setUrl(e.text());
                break;
            }
        }

        if(detail.getUrl()==null){
            return null;
        }
        if(xcd.countByUrl(detail.getUrl()) > 0){
            log.info("发现重复记录，{}", detail.getUrl());
            return null;
        }

        //寻找抗描述信息
        Elements divs = doc.getElementsByTag("div");
        ArrayList<String> objects = newArrayList();
        signal: for(Element div: divs){
            Elements tpc_content_do_not_catch1 = div.getElementsByClass("tpc_content do_not_catch");
            if(tpc_content_do_not_catch1.size()==0){
                continue;
            }

            for(Element e: tpc_content_do_not_catch1){
                List<Node> textNodes1 = e.childNodes();
                textNodes1.forEach(x -> {
                    boolean b = x instanceof TextNode;
                    if(b){
                        String text = ((TextNode) x).text();
                        if(validData(text)){
                            objects.add(text);
                        }
                    }
                });
            }

            if(!objects.isEmpty()) break signal;

            for(Element e: tpc_content_do_not_catch1) {
                Elements f18 = e.getElementsByClass("f18");
                if (f18.size() == 0) {
                    continue;
                } else {
                    List<Node> nodes = f18.get(0).childNodes();
                    for (Node n : nodes) {
                        boolean b = n instanceof TextNode;
                        if (b) {
                            String text = ((TextNode) n).text();
                            if (validData(text)) {
                                objects.add(text);
                            }
                        }
                    }

                    if(!objects.isEmpty()) break signal;
                }
            }

            if(!objects.isEmpty()) break signal;
        }

        if(!objects.isEmpty()){
            StringBuffer sb = new StringBuffer();
            for (int i=0; i<objects.size(); i++){
                sb.append(objects.get(i)).append("\n");
            }
            detail.setDesc(sb.toString());
        }

        return detail;
    }

    @Override
    public List<ContentDetail> parse(List<Page> pages) {
        HashSet<ContentDetail> cds  = newHashSet();

        for(Page page : pages){
            Elements contentRows = page.getDoc().getElementById("ajaxtable").getElementsByClass("tr3 t_one tac");
            for(Element tr : contentRows){
                Elements tds = tr.select("td");
                if(tds.size()==0){
                    continue;
                }
                Element tdIDX1 = tds.get(1).getElementsByTag("a").get(0);
                String href =  page.getBaseUrl() + "/"+tdIDX1.attr("href");
                String urlName = tdIDX1.text();

                String deployTime = null;
                Elements td4 = tds.size()>4 ? tds.get(4).getElementsByTag("a") : null;
                if(td4!=null && td4.size()!=0){
                    deployTime = td4.get(0).text();
                }




                try {
//                    String auth = tds.size()>4 ? tds.get(4)!=null ? tds.get(4).childNode(2).toString(): null : null;
                    Document doc = RequestUtil.request(href, 3);
                    Page childPage = new Page();
                    childPage.setBaseUrl(page.getBaseUrl());
                    childPage.setFromUrl(page.getUrl());
                    childPage.setStartTime(new Date());
                    childPage.setDoc(doc);
                    childPage.setUrl(href);
                    childPage.setUrlName(urlName);
                    ContentDetail cd = parse(childPage);
                    if(cd!=null){
                        cd.setContentDeployTime(StringUtils.isEmpty(deployTime) ? null : DateUtils.parse(deployTime, "yyyy-MM-dd HH:mm"));
//                        cd.setAuth(StringUtils.isEmpty(auth) ? null : auth.startsWith("by:") ? auth.substring("by:".length()).trim() : auth);

//                        if(StringUtils.isEmpty(cd.getDesc()))
                        //log.info(cd.print());
                        xcd.insertData(cd);
                        cds.add(cd);
                    }
                } catch (IOException e) {
                    log.error("解析URL异常: {}, {}", urlName, href, e);
                }

            }
        }

        return newArrayList(cds);
    }


    public boolean validData(String text){
        return text.contains("影片格式") || text.contains("影片格式") || text.contains("影片大小")
                || text.contains("影片名稱") || text.contains("做種期限") || text.contains("預覽圖片")
                || text.contains("影片名称") || text.contains("档案大小") || text.contains("档案格式")
                || text.contains("影片时间") || text.contains("特 征 码") || text.contains("特征码")
                || text.contains("做种说明") || text.contains("种子期限") || text.contains("影图预览")
                || text.contains("収録時間") || text.contains("特徵碼") || text.contains("主演女優")
                || text.contains("主演") || text.contains("出演") || text.contains("驗證碼")
                || text.contains("验证码") || text.contains("商品番号") || text.contains("女優名")
                || text.contains("再生時間") || text.contains("配信日") || text.contains("年齢")
                || text.contains("名前") || text.contains("檔案大小") || text.contains("檔案格式")
                || text.contains("是否有碼")

                ;
    }
}
