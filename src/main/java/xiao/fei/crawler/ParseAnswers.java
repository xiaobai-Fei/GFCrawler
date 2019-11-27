package xiao.fei.crawler;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import xiao.fei.models.AnswerModel;
import xiao.fei.models.ResultModel;

import java.util.Iterator;

public class ParseAnswers {

    public ResultModel parseTopicAnswer(Document document) {
        ResultModel resultModel = new ResultModel();

        String bodyString = document.body().text();
        JSONObject jsonObject = JSON.parseObject(bodyString);

        //解析内容
        parseContent(resultModel, jsonObject);

        //paging 也就是URL解析
        parseURL(resultModel, jsonObject);
        return resultModel;
    }

    private void parseContent(ResultModel resultModel, JSONObject jsonObject) {
        JSONArray jsonArray = jsonObject.getJSONArray("data");
        Iterator iterator = jsonArray.iterator();

        while (iterator.hasNext()) {
            AnswerModel answerModel = new AnswerModel();
            JSONObject childObject = (JSONObject) iterator.next();
            JSONObject authorObject = childObject.getJSONObject("author");
            int gender = authorObject.getInteger("gender");
            //除去匿名用户和男性
            if (gender == 1)
                continue;
            String contentString = (String) childObject.get("content");
            //清洗内容
            Document paragraphDocument = Jsoup.parseBodyFragment(contentString);
            Elements elements = paragraphDocument.getElementsByTag("p");

            String name = authorObject.getString("name");
            String url_token = authorObject.getString("url_token");
            String answer = getText(elements);

            //封装解析结果
//            answerModel.setAnswer(answer);
//            answerModel.setUserName(name);
//            answerModel.setUserToken(url_token);

            resultModel.add(answerModel);

        }
    }

    private void parseURL(ResultModel resultModel, JSONObject jsonObject) {
        JSONObject pagingJsonAbject = jsonObject.getJSONObject("paging");
        if (pagingJsonAbject.containsKey("next")) {
            String nextURL = pagingJsonAbject.getString("next");
            resultModel.setNextURL(nextURL);
        }
        if (pagingJsonAbject.containsKey("is_end")) {
            boolean is_end = pagingJsonAbject.getBoolean("is_end");
            resultModel.set_end(is_end);
        }
    }

    private String getText(Elements elements) {
        StringBuilder sb = new StringBuilder();
        for (Element element : elements) {
            if (sb.length() != 0)
                sb.append("\r\n");
            sb.append(element.text());
        }
        return sb.toString();
    }
}