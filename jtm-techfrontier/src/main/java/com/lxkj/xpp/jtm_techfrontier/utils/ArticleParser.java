package com.lxkj.xpp.jtm_techfrontier.utils;

import android.text.TextUtils;

import com.lxkj.xpp.jtm_techfrontier.bean.Article;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/11/30.
 */

public class ArticleParser implements RespParser<List<Article>> {
    private static SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    @Override
    public List<Article> parseResponse(String result) throws JSONException {
        JSONArray jsonArray=new JSONArray(result);
        List<Article> articleLists = new LinkedList<Article>();
        for (int i=0;i<jsonArray.length();i++){
          JSONObject jsonObject=  jsonArray.optJSONObject(i);
            articleLists.add(paraseItem(jsonObject));
        }
        return articleLists;
    }

    private Article paraseItem(JSONObject itemObject) {
        Article articleItem = new Article();
        articleItem.title = itemObject.optString("title");
        articleItem.author = itemObject.optString("author");
        articleItem.post_id = itemObject.optString("post_id");
        String category = itemObject.optString("category");
        articleItem.category = TextUtils.isEmpty(category) ? 0 : Integer.valueOf(category);
        articleItem.publishTime = formatDate(dateformat, itemObject.optString("date"));
        return articleItem;
    }

    private static String formatDate(SimpleDateFormat dateFormat, String dateString) {
        try {
            Date date = dateFormat.parse(dateString);
            return dateFormat.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return "";
    }
}
