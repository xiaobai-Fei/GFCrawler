package xiao.fei.proxy;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.net.InetSocketAddress;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.net.Proxy;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@NoArgsConstructor
public class ProxyParser {

    @Setter
    private Set<Document> documents =null;

    public static final String ipPattern = "((25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))\\.){3}(25[0-5]|2[0-4]\\d|((1\\d{2})|([1-9]?\\d)))";
    public static final String portPattern="[0-9]{1,4}";

    @Getter
    private List<Proxy> proxies =new ArrayList<>();

    public ProxyParser(Set<Document> documents) {
        this.documents=documents;
    }

    public List<Proxy> parseData(){
        if (this.documents==null){
            return null;
        }
        Iterator<Document> iterator = documents.iterator();
        while (iterator.hasNext()){
            Document document = iterator.next();
            parseData(document);
        }
        return proxies;
    }

    public List<Proxy> parseData(Document document) {
        Elements trElements= document.body().select("tr");
        for (Element trElement : trElements) {
            Iterator<Element> iterator=  trElement.children().iterator();
            while (iterator.hasNext()){
                String ip = iterator.next().ownText();
                if (isMatches(ipPattern,ip) && iterator.hasNext()){
                    String port = iterator.next().ownText();
                    if (isMatches(portPattern,port)){
                        constructProxy(ip,Integer.valueOf(port));
                        break;
                    }

                }
            }
        }
       return proxies;
    }
    public static boolean isMatches(String pattern,String str) {
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher(str);
        return m.matches();
    }

    public void constructProxy(String host,int port){
        Proxy proxy= new Proxy(java.net.Proxy.Type.HTTP, InetSocketAddress.createUnresolved(host, port));
        proxies.add(proxy);
    }

}
