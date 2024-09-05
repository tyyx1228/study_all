package com.ty.study;

import com.ty.study.model.Page;
import com.ty.study.parser.XParser;
import com.ty.study.thread.XTask;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Date;

/**
 * @author relax tongyu
 * @create 2018-06-08 14:32
 **/
@Slf4j
public class TestMain {

    public static void main(String[] args) throws IOException {
        try(TaskExecutor taskExecutor =new TaskExecutor(8);){
//https://cl.d1v.xyz
            String baseUrl = "https://cl.uxwu.xyz/";//thread0806.php?fid=2
            String targetUrl = baseUrl + "/thread0806.php?fid=2";

            Document doc = RequestUtil.request(targetUrl, 3);
            Date startTime = new Date();

            //首页
            Page page = new Page();
            page.setBaseUrl(baseUrl);
            page.setDoc(doc);
            page.setUrl(targetUrl);
            page.setStartTime(startTime);
            page.setUrlName(doc.getElementsByTag("title").get(0).text());
            page.setDepth(Math.max(1, page.getDepth()));
            XTask xTask = new XTask(page, new XParser());


            Elements contentPage = doc.getElementsByClass("pages");
            String pageUrl = null;
            for(Element p : contentPage){
                for(Element e : p.getElementsByTag("a")){
                    if("下一頁".equals(e.text().trim())){
                        String pageNum = e.attr("href");
                        pageUrl = baseUrl + "/" + pageNum.substring(0, pageNum.length()-1);
                    }
                }
            }

            taskExecutor.execute(xTask);
            for(int i=2; i<103; i++){
                Document nextDoc = Jsoup.connect(pageUrl+i).get();
                Page nextPage = new Page();
                nextPage.setBaseUrl(baseUrl);
                nextPage.setFromUrl(pageUrl+(i-1));
                nextPage.setDoc(nextDoc);
                nextPage.setUrl(pageUrl+i);
                nextPage.setStartTime(new Date());
                nextPage.setUrlName(doc.getElementsByTag("title").get(0).text());
                nextPage.setDepth(Math.max(1, page.getDepth()));
                taskExecutor.execute(new XTask(nextPage, new XParser()));
            }

        }catch (Exception e){
            log.error("", e);
        }

    }
}
