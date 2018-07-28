package com.ty.study.document;

import java.util.Date;

/**
 * twitter用户实体
 *
 * @author relax tongyu
 * @create 2018-03-23 15:40
 **/
public class TwitterUser {
    private String user;
    private Date postDate;
    private String message;


    public TwitterUser(String user, Date postDate, String message) {
        this.user = user;
        this.postDate = postDate;
        this.message = message;
    }


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Date getPostDate() {
        return postDate;
    }

    public void setPostDate(Date postDate) {
        this.postDate = postDate;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
