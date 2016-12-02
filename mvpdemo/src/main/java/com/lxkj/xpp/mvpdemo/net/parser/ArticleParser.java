package com.lxkj.xpp.mvpdemo.net.parser;

import android.text.TextUtils;

import com.lxkj.xpp.mvpdemo.beans.Article;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by Administrator on 2016/12/2.
 * 将服务器返回的json数据转化为文章列表的解析器
 */

public class ArticleParser implements RespParser<List<Article>>{
private static SimpleDateFormat dateformat=new SimpleDateFormat("yyyy-MM-dd HH:mm");
    @Override
    public List<Article> parseResponse(String result) throws JSONException {
        JSONArray jsonArray = new JSONArray(result);
        List<Article> articleLists = new LinkedList<Article>();
        int count = jsonArray.length();
        for (int i = 0; i < count; i++) {
            JSONObject itemObject = jsonArray.optJSONObject(i);
            articleLists.add(parseItem(itemObject));
        }
        return articleLists;
    }
    private Article parseItem(JSONObject itemObject){
        Article articleItem=new Article();
        articleItem.title=itemObject.optString("title");
        articleItem.author = itemObject.optString("author");
        articleItem.post_id = itemObject.optString("post_id");
        String category = itemObject.optString("category");
        articleItem.category = TextUtils.isEmpty(category) ? 0 : Integer.valueOf(category);
        articleItem.publishTime = formatDate(dateformat, itemObject.optString("date"));
        return articleItem;
    }
    private static String formatDate(SimpleDateFormat dateFormat,String dataString){
        try {
            Date date=dateFormat.parse(dataString);
            return dateFormat.format(date);

        }catch (ParseException e){
            e.printStackTrace();
        }
        return "";

    }
}
