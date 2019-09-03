import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class TestValidate {

    public static void main(String[] args){
        final String baiduUrl = "https://www.baidu.com";
        try {
            Document doc = Jsoup.connect(baiduUrl)
                    .header("Accept", "*/*")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
                    .header("Referer", "https://www.baidu.com/")
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0")
                    .timeout(5000)
                    .ignoreContentType(true)
                    .get();
            String content = doc.text();
            System.out.println(content);
        }catch (IOException e){
            e.printStackTrace();

        }
    }
}
