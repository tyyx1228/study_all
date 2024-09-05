package com.ty.study.thread;

import com.ty.study.model.ContentDetail;
import com.ty.study.model.Page;
import com.ty.study.page.PageContent;
import com.ty.study.parser.Parser;
import com.ty.study.task.BaseTask;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.collect.Lists.newArrayList;

/**
 * 爬取任务
 *
 * @author relax tongyu
 * @create 2018-06-08 15:53
 **/
@Slf4j
public class XTask extends BaseTask{
    private long depth;

    public XTask(Page page, Parser parser) {
        super(page, parser);
        this.depth = page.getDepth();
    }

    @Override
    public void execute(Page page) {

        try{
            List parse = getParser().parse(newArrayList(page));
        }catch (Exception e){
            e.printStackTrace();
        }




    }



}
