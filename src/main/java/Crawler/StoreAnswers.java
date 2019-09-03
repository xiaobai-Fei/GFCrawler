package Crawler;

import Utils.HBaseDao;
import Utils.SolrDao;
import org.apache.solr.common.SolrInputDocument;

import java.io.FileOutputStream;
import java.io.FileWriter;
import java.util.List;

/*
 * 持久化内容
 **/
public class StoreAnswers {

    private static SolrDao solrDao = new SolrDao();

    private static HBaseDao hBaseDao = new HBaseDao();

    public void storeAnswersToSolr(List<AnswerModel> answerModelList){
        for (AnswerModel answerModel : answerModelList) {
            SolrInputDocument solrInputDocument = solrDao.constructSolrInputDocument(answerModel);
            //solr本身可以设置batch size或者time真正发送一次HTTP连接
            solrDao.commitDocument(solrInputDocument);
        }
    }

    public void  storeAnswersToHBase(List<AnswerModel> answerModelList){
        //TODO
    }

    public void storeAnswersToHDFS(List<AnswerModel> answerModelList){
        //TODO
    }

    public void storeAnswersToMySql(List<AnswerModel> answerModelList){
        //TODO
    }

    public void storeToFileSystem(List<AnswerModel> answerModelList) {
        //TODO 有待优化
        try(FileOutputStream fileOutputStream = new FileOutputStream("src/main/java/Crawler/fetch.log",true);) {
            for (AnswerModel answerModel : answerModelList) {
                fileOutputStream.write(answerModel.getUserToken().getBytes());
                fileOutputStream.write("\r\n".getBytes());
                System.out.println(answerModel.getUserName()+":"+answerModel.getUserToken());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
