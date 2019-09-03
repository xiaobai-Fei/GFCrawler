package Proxy;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;

public class ProxyFetcher {

    public Document fetchData(String url) {
        if (url==null || "".equals(url)){
            return  null;
        }
        try {
            Document doc = Jsoup.connect(url)
                    .header("Accept", "*/*")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
                    .header("Referer", "https://www.baidu.com/")
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0")
                    .timeout(5000)
                    .ignoreContentType(true)
                    .get();
            return doc;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    public static void main(String[] args)throws Exception {
        ProxyFetcher fetchData = new ProxyFetcher();
        fetchData.fetchData(RecycleQueue.getNextURL());
    }
}
