package com.ty.study.recommender.recommender.action;

import com.ty.study.recommender.recommender.domain.Product;
import com.ty.study.recommender.recommender.service.RecommendService;
import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionSupport;
import org.apache.struts2.StrutsStatics;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Describe:
 * Author:   maoxiangyi
 * Domain:   www.itcast.cn
 * Data:     2015/12/2.
 */
public class RecommendAction extends ActionSupport {
    private String userId;//请求推荐的用户编号
    private String adId;//请求推荐的广告位

    private List<Product> recomendResult; //返回推荐结果，并转化为Json字符串

    private RecommendService recommendService;

    /**
     * 输入两个显式的信息，用户编号，广告位编号，从Request对象中获取用户的cookies信息。
     *
     * @return
     */
    public String recomend() {
        String views = readUserViewCookie();
        recomendResult = recommendService.recomend(adId, userId, views);
        return "SUCCESS";
    }

    /*
     * 从cookies中获取用户访问的商品列表
     * @return
     */
    private String readUserViewCookie() {
        HttpServletRequest request = (HttpServletRequest) ActionContext.getContext().get(StrutsStatics.HTTP_REQUEST);
        Cookie cookies[] = request.getCookies();
        Cookie cookie = null;
        if (cookies != null) {
            for (int i = 0; i < cookies.length; i++) {
                if ("view".equals(cookies[i].getName())) {
                    cookie = cookies[i];
                    break;
                }
            }
        }
        if (cookie == null) {
            return "";
        }
        return cookie.getValue();
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getAdId() {
        return adId;
    }

    public void setAdId(String adId) {
        this.adId = adId;
    }


    public void setRecommendService(RecommendService recommendService) {
        this.recommendService = recommendService;
    }
}
