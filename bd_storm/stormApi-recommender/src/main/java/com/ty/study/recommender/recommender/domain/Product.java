package com.ty.study.recommender.recommender.domain;

/**
 * Describe: 请补充类描述
 * Author:   maoxiangyi
 * Domain:   www.itcast.cn
 * Data:     2015/12/2.
 */
public class Product implements Comparable {

    private String id;//商品编号
    private String name;//商品名称
    private String price;//商品价格
    private String pic;//商品图片地址
    private String url;//跳转到详情页
    private int status;//商品状态

    public Product() {
    }

    public Product(String skuid, String title, String price, String producturl, String pic) {
        this.id = skuid;
        this.name = title;
        this.price = price;
        this.url = producturl;
        this.pic = pic;
        this.status = 1;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Product{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", price='" + price + '\'' +
                ", pic='" + pic + '\'' +
                ", url='" + url + '\'' +
                ", status='" + status + '\'' +
                '}';
    }

    @Override
    public int compareTo(Object o) {
        Product product = (Product) o;
        int r = product.getId().equals(this.getId()) == true ? 0 : 1;
        return r;
    }

    @Override
    public boolean equals(Object obj) {
        Product product = (Product) obj;
        return product.getId().equals(this.getId());
    }
}
