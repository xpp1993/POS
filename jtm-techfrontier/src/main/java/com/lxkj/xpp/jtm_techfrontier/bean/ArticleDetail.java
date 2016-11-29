package com.lxkj.xpp.jtm_techfrontier.bean;

/**
 * Created by Administrator on 2016/11/29.
 */

public class ArticleDetail {
    public String postId;
    public String content;

    public ArticleDetail() {
    }

    public ArticleDetail(String pid, String html) {
        postId = pid;
        content = html;
    }
}
