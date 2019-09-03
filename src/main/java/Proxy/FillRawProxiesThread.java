package Proxy;

import org.jsoup.nodes.Document;

import java.net.Proxy;
import java.util.List;
import java.util.concurrent.BlockingQueue;

public class FillRawProxiesThread extends  Thread {

    ProxyFetcher fetcher = new ProxyFetcher();

    ProxyParser parser = new ProxyParser();

    BlockingQueue<Proxy> rawProxies ;
    public FillRawProxiesThread(BlockingQueue<Proxy> rawProxies) {
        this.rawProxies= rawProxies;
    }

    @Override
    public void run() {
        while (true) {
            Document document = fetcher.fetchData(RecycleQueue.getNextURL());
            if (null == document)
                continue;
            List<Proxy> proxyList = parser.parseData(document);
            rawProxies.addAll(proxyList);
            try {
                Thread.sleep(100000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
