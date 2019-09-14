package xiao.fei.proxy.Proxy_66ip;

import xiao.fei.proxy.ProxyFetcher;

import org.jsoup.nodes.Document;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.util.ArrayList;
import java.util.List;

public class ProxyService {

    private static  final String url = "http://www.66ip.cn/nmtq.php?getnum=&isp=0&anonymoustype=3&start=&ports=&export=&ipaddress=&area=0&proxytype=1&api=66ip";

    private ProxyFetcher proxyFetcher = new ProxyFetcher();
    public List<Proxy> getProxies(){
        Document document = proxyFetcher.fetchData(url);
        if (document!=null){
            return parseDocument(document);
        }

        return  null;

    }

    private List<Proxy> parseDocument(Document document) {
        List<Proxy> proxies = new ArrayList<>();
        String text= document.text();
        String[] addresses = text.split(" ");
        if (addresses!=null&& addresses.length>1){
            for(int i=1;i<addresses.length;i++){
                String address = addresses[i];
                String[] ip_port = address.split(":");
                String ip = ip_port[0];
                int port = Integer.valueOf(ip_port[1]);
                Proxy proxy= new Proxy(java.net.Proxy.Type.HTTP, InetSocketAddress.createUnresolved(ip, port));
                proxies.add(proxy);
            }
        }
        return proxies;
    }

    //Test
    public static void main(String[] args){
        ProxyService proxyService = new ProxyService();
        proxyService.getProxies();
    }
}
