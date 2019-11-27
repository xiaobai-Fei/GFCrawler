package xiao.fei.crawler;

import xiao.fei.models.AnswerModel;
import xiao.fei.utils.HBaseDao;
import xiao.fei.utils.MySqlUtils;
import xiao.fei.utils.SolrDao;
import org.apache.solr.common.SolrInputDocument;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.List;

/*
 * 持久化内容
 **/
public class StoreAnswers {

    private static SolrDao solrDao = new SolrDao();

    private static HBaseDao hBaseDao = new HBaseDao();

    public static void storeAnswersToSolr(List<AnswerModel> answerModelList){
        for (AnswerModel answerModel : answerModelList) {
            SolrInputDocument solrInputDocument = solrDao.constructSolrInputDocument(answerModel);
            //solr本身可以设置batch size或者time真正发送一次HTTP连接
            solrDao.commitDocument(solrInputDocument);
        }
    }

    public static void  storeAnswersToHBase(List<AnswerModel> answerModelList){
        //TODO
    }

    public static void storeAnswersToHDFS(List<AnswerModel> answerModelList){
        //TODO
    }

    public static void storeAnswersToMySql(List<AnswerModel> answerModelList){
        try{
            Connection connection = MySqlUtils.getConnection();
            for(AnswerModel answerModel :answerModelList){
                StringBuffer sql = new StringBuffer("insert into answers (answer_id , url_token , name , content) values ");
                sql.append("( "+"\"" +answerModel.getUrl_token()+"\"");
                sql.append(",");
                sql.append("\""+answerModel.getAnswer_id()+"\"");
                sql.append(",");
                sql.append("\""+answerModel.getName()+"\"");
                sql.append(",");
                sql.append("\""+answerModel.getContent()+"\"");
                sql.append(" )");
                boolean result = connection.prepareStatement(sql.toString()).execute();
                System.out.println("Inserting result is " + result);
            }
        }catch (SQLException e){
            e.printStackTrace();
        }

    }

    public static void storeToFileSystem(List<AnswerModel> answerModelList) {
        //TODO 有待优化
//        try(FileOutputStream fileOutputStream = new FileOutputStream("fetch.log",true);) {
//            for (AnswerModel answerModel : answerModelList) {
//                fileOutputStream.write(answerModel.getUserToken().getBytes());
//                fileOutputStream.write("\r\n".getBytes());
//                System.out.println(answerModel.getUserName()+":"+answerModel.getUserToken());
//            }
//        }catch (Exception e){
//            e.printStackTrace();
//        }
    }

}
