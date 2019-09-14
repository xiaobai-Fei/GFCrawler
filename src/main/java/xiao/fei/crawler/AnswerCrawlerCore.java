package xiao.fei.crawler;

import xiao.fei.models.ResultModel;
import xiao.fei.proxy.ProxyCore;
import org.jsoup.nodes.Document;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class AnswerCrawlerCore extends Thread {


    private static ConcurrentLinkedQueue<Document> documents = new ConcurrentLinkedQueue<>();

    //阻塞等待，会一直阻塞。
    private static BlockingQueue<String> linkedBlockingQueue = new LinkedBlockingQueue<>();
    private static String initRequestUrl =
            "https://www.zhihu.com/api/v4/questions/304090066/answers?include=data[*].is_normal,admin_closed_comment,reward_info,is_collapsed,annotation_action,annotation_detail,collapse_reason,is_sticky,collapsed_by,suggest_edit,comment_count,can_comment,content,editable_content,voteup_count,reshipment_settings,comment_permission,created_time,updated_time,review_info,relevant_info,question,excerpt,relationship.is_authorized,is_author,voting,is_thanked,is_nothelp,is_labeled,is_recognized,paid_info,paid_info_content;data[*].mark_infos[*].url;data[*].author.follower_count,badge[*].topics&limit=5&offset=10&platform=desktop&sort_by=default";

    static {
        //这个片段必须是static，因为它只需要执行一次。
        linkedBlockingQueue.add(initRequestUrl);
    }

    @Override
    public void run() {
        FetchAnswers fetchAnswers = new FetchAnswers();
        ParseAnswers parseAnswers = new ParseAnswers();
        StoreAnswers storeAnswers = new StoreAnswers();
        try {
            while (true) {
                String tmpUrl= linkedBlockingQueue.take();
                Document document = fetchAnswers.fetchTopicAnswers(tmpUrl);
                if (document==null){
                    linkedBlockingQueue.add(tmpUrl);
                    continue;
                }
                documents.add(document);

                //这边可以开线程池，直接丢进去处理
                if (documents.isEmpty()) {
                    continue;
                }
                ResultModel resultModel = parseAnswers.parseTopicAnswer(documents.poll());
                if (!resultModel.isEmpty()) {
                    storeAnswers.storeToFileSystem(resultModel.getAnswerModelList());
                }
                if (null != resultModel.getNextURL()) {
                    linkedBlockingQueue.add(resultModel.getNextURL());
                }
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args){
        //解耦两个模块需要消息中间件redis
        ProxyCore proxyCore = new ProxyCore();
        proxyCore.start();

        AnswerCrawlerCore answerCrawlerCore = new AnswerCrawlerCore();
        answerCrawlerCore.start();
    }
}
