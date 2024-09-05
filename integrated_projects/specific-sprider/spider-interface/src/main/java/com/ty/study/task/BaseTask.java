package com.ty.study.task;

import com.ty.study.model.Page;
import com.ty.study.parser.Parser;
import lombok.Data;

/**
 * @author relax tongyu
 * @create 2018-06-08 16:05
 **/
@Data
public abstract class BaseTask implements Task {

    private Parser parser;

    private Page page;

    public BaseTask(Page page, Parser parser){
        this.page = page;
        this.parser = parser;
    }

    @Override
    public void run() {
        execute(page);
    }
}
