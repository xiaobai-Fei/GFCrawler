package Crawler;

import Proxy.ProxyCore;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.Proxy;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class FetchAnswers {

    ProxyCore proxyCore = new ProxyCore();

    public Document fetchTopicAnswers(String requestUrl ) {
        try {
            Document document = Jsoup.connect(requestUrl)
                    .header("Accept", "*/*")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
                    .header("Referer", "https://www.baidu.com/")
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0")
                    .timeout(5000)
                    .proxy(
                            //保证一个proxy可以使用很多次，直到不能用
                            proxyCore.getEffectiveProxies().isEmpty()
                                    ?proxyCore.getEffectiveProxies().take()
                                    :proxyCore.getEffectiveProxies().peek())
                    .ignoreContentType(true)
                    .get();
            return document;
        }catch (IOException |InterruptedException e){
            //TODO log
            e.printStackTrace();
            try {

                if (!proxyCore.getEffectiveProxies().isEmpty()){
                    proxyCore.getEffectiveProxies().take();
                }
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
        }
        return  null;
    }
}
