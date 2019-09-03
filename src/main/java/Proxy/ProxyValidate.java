package Proxy;

import com.sun.org.glassfish.gmbal.Description;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.net.Proxy;

public class ProxyValidate {

    //位于美国的服务器，响应速度一般般
    private static final String url = "http://icanhazip.com/";
    //位于美国的服务器，响应速度一般般
    private static final String url2 = "http://httpbin.org/get";

    private static final String baiduUrl = "https://www.baidu.com";

    public boolean validateProxy(Proxy proxy) {
        try {
            Document doc = Jsoup.connect(baiduUrl)
                    .header("Accept", "*/*")
                    .header("Accept-Encoding", "gzip, deflate")
                    .header("Accept-Language", "zh-CN,zh;q=0.8,en-US;q=0.5,en;q=0.3")
                    .header("Referer", "https://www.baidu.com/")
                    .header("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64; rv:48.0) Gecko/20100101 Firefox/48.0")
                    .timeout(3000)
                    .ignoreContentType(true)
                    .proxy(proxy)
                    .get();
            if (doc!=null)
                return true;
        }catch (IOException e){
//            e.printStackTrace();
            return false;
        }
        //url1解析
//        String id = doc.text();
//        System.out.println(id);
//        if (ParseData.isMatches(ParseData.ipPattern,id)){
//            return true;
//        }
        //url2 解析
//        if (id.startsWith("{") && id.endsWith("}"))
//            return true;
//        return false;
        return  false;
    }

    public static void main(String[] args){
        try {
            ProxyFetcher fetcher = new ProxyFetcher();
            fetcher.fetchData(RecycleQueue.getNextURL());
            ProxyParser parseData = new ProxyParser();
            parseData.parseData();
//            Proxy proxy = new Proxy(Proxy.Type.HTTP, InetSocketAddress.createUnresolved("117.95.232.75",9999));
            ProxyValidate validateProxy = new ProxyValidate();
            for (Proxy proxy:parseData.getProxies()){
                try {
                    System.out.println(validateProxy.validateProxy(proxy));
                } catch (Exception e) {
                    System.out.println(false);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
