package com.ty.study;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.*;
import java.util.*;

/**
 * 加载properties配置
 *
 * @author relax tongyu
 * @create 2018-06-08 12:10
 **/
@Slf4j
public class Config {

    private static Map<String,String> mapper = new HashMap<>();

    static {
        URL url = Config.class.getClassLoader().getResource("");
        log.info("scaning path to find config file -> " + url.toString());
        try {
            Files.list(Paths.get(url.toURI()))
                    .filter(x -> x.getFileName().toString().startsWith("spider-") && x.getFileName().toString().endsWith(".properties") )
                    .forEach(x-> {
                        String filePath = x.toAbsolutePath().toString();
                        Properties prop = new Properties();
                        try (
                            InputStream in = Files.newInputStream(Paths.get(filePath), StandardOpenOption.READ)
                        ){
                            prop.load(in);
                            mapper.putAll((Map) prop);
                            log.info("loaded properties file: " + x.getFileName().toString());
                        }catch (IOException e) {
                            log.error("loading config file: " + x.getFileName().toString() + "failed", e);
                        }
                    });

        } catch (IOException | URISyntaxException e) {
            log.error("", e);
        }
    }

    public static Optional<String> get(String key){
        String s = mapper.get(key);
        return s==null ? Optional.empty() : Optional.of(s.trim());
    }

    public static void main(String[] args) {
        try {
            int i = 1 / 0;
        }catch (Exception e){
            log.error("params={}",12, e);
        }
    }


}
