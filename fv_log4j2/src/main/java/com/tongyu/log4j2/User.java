package com.tongyu.log4j2;

/**
 * 测试对象
 * Created by relax on 2018/1/25.
 */
public class User {
    private String name;
    private String birthdayCalendar;

    public User(String name, String birthdayCalendar) {
        this.name = name;
        this.birthdayCalendar = birthdayCalendar;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthdayCalendar() {
        return birthdayCalendar;
    }

    public void setBirthdayCalendar(String birthdayCalendar) {
        this.birthdayCalendar = birthdayCalendar;
    }
}
