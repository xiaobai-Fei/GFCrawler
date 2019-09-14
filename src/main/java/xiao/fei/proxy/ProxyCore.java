package xiao.fei.proxy;

import xiao.fei.proxy.Proxy_66ip.ProxyService;

import java.net.Proxy;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ProxyCore extends Thread{
    //这两个容器可以保证线程安全吗？
    //TODO
    //waiting for verification
    private static BlockingQueue<Proxy> rawProxies = new LinkedBlockingQueue<>();

    //have verification

    private static BlockingQueue<Proxy> effectiveProxies =  new BlockingQueueUnRepeat<>();

    ProxyValidate proxyValidate = new ProxyValidate();

    public boolean verifyProxy(Proxy proxy){
        boolean isValid = proxyValidate.validateProxy(proxy);
        return isValid;
    }

    @Override
    public void run() {
        //start a new thread to fetch more new proxies
//        FillRawProxiesThread fillRawProxiesThread = new FillRawProxiesThread(rawProxies);
//        fillRawProxiesThread.start();
        while (true) {
            try {
                if (rawProxies.isEmpty()){
                    ProxyService proxyService = new ProxyService();
                    List<Proxy> proxies = proxyService.getProxies();
                    if (proxies.isEmpty()){
                        continue;
                    }
                    rawProxies.addAll(proxies);
                }
                Proxy proxy = rawProxies.take();
                boolean isValid = verifyProxy(proxy);
                if (isValid){
                    effectiveProxies.add(proxy);
                    System.out.println(proxy);
                }

            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static BlockingQueue<Proxy> getEffectiveProxies() {
        return effectiveProxies;
    }

    public static void main(String[] args){
        ProxyCore proxyCore = new ProxyCore();
        proxyCore.start();
    }
}
