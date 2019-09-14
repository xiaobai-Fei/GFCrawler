package xiao.fei.proxy;

//循环队列
public class RecycleQueue {

    private static final String baseUrl = "https://www.xicidaili.com/nn/";

    //这个应该必须加 volatile 否则多线程有可能出问题
    private volatile static int nextPageNumber = 1;

    private static final int MaxPage = 10;

    //这个synchronized 也必须加
    public static synchronized String getNextURL(){
        if (nextPageNumber>MaxPage){
            nextPageNumber = 1;
        }
        nextPageNumber++;
        return baseUrl+nextPageNumber;
    }

}
