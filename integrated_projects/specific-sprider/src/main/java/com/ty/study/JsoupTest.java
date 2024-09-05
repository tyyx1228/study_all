package com.ty.study;

import com.ty.study.page.PageContent;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * 首个Jsoup 测试案例
 *
 * @author relax tongyu
 * @create 2018-06-07 22:15
 **/
@Slf4j
public class JsoupTest {


    public static void main(String[] args) throws IOException, URISyntaxException {
//        String url = "http://www.baidu.com";
        String baseUrl = "https://cl.u16.win";
        String targetUrl = baseUrl + "/thread0806.php?fid=2";

        Document doc = Jsoup.parse(new URL(targetUrl), 10000);
//        doc.body().getElementsByClass("");

        Elements contentPage = doc.getElementsByClass("pages");
        String pageStr = null;
        for(Element p : contentPage){
            for(Element e : p.getElementsByTag("a")){
                if("下一頁".equals(e.text().trim())){
                    String pageNum = e.attr("href");
                    pageStr = baseUrl + "/" + pageNum.substring(0, pageNum.length()-1);
                }
            }
        }
        log.info(contentPage.toString());



        Elements contentTbl = doc.getElementById("ajaxtable").getElementsByClass("tal");
        List<Element> contentA = new ArrayList();
        contentTbl.forEach(x -> {
            Element a = x.getElementsByTag("a").get(0);
            contentA.add(a);
        });
        for (Element e : contentA){
            String href = baseUrl + "/"+e.attr("href");
            String val = e.text();

            PageContent pageContent = new PageContent();
            pageContent.setPartentUrl(targetUrl);
            pageContent.setChildUrl(href);
            pageContent.setChildUrlName(val);


            log.info(pageContent.toString());
        }

//        logger.info(contentPage.toString());


    }

}
