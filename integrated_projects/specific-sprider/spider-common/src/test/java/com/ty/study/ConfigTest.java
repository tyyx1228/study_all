package com.ty.study;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

@Slf4j
public class ConfigTest {

    @Test
    public void get() {
        log.info(Config.get("a").isPresent() + "");
        log.info(Config.get("ab").isPresent() + "");
    }
}