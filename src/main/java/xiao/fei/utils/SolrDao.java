package xiao.fei.utils;

import xiao.fei.models.AnswerModel;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.SolrInputDocument;
import org.apache.solr.common.params.MapSolrParams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class SolrDao {

    private static ArrayList<HttpSolrClient> clientsPool=new ArrayList<>();

    public SolrInputDocument constructSolrInputDocument(AnswerModel answer) {
        SolrInputDocument inputDocument = new SolrInputDocument();
//        inputDocument.addField("user_token", answer.getUserToken());
//        inputDocument.addField("user_name",answer.getUserName());
//        inputDocument.addField("answers", answer.getAnswer());
        return inputDocument;
    }

    public synchronized boolean  commitDocument(SolrInputDocument solrInputDocument) {
        if (clientsPool.isEmpty()){
            initClientPool();
        }
        try {
            HttpSolrClient httpSolrClient = clientsPool.get(0);
            httpSolrClient.add(solrInputDocument);
            httpSolrClient.commit();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("提交失败！");
            return false;
        }
        return true ;
    }

    private void initClientPool() {
        try {
            ResourceBundle resourceBundle = ResourceBundle.getBundle("Utils/utils");
            String solrUrl = resourceBundle.getString("solrUrl");
            HttpSolrClient.Builder  builder = new HttpSolrClient.Builder(solrUrl);
            builder.withConnectionTimeout(5000).withSocketTimeout(5000);
            HttpSolrClient httpSolrClient =builder.build();
            clientsPool.add(httpSolrClient);
        }catch (Exception e){
            System.out.println("请确认你的配置文件设置的正确!");
        }
    }

    public void query(String queryString){
        try {
        if (clientsPool.isEmpty())
            initClientPool();
        HttpSolrClient httpSolrClient = clientsPool.get(0);
        final Map<String, String> queryParamMap = new HashMap<String, String>();
        queryParamMap.put("q", "青春");
//        queryParamMap.put("fl", "id, name");
        queryParamMap.put("sort", "id asc");
        MapSolrParams queryParams = new MapSolrParams(queryParamMap);

        SolrQuery solrQuery = new SolrQuery();
        solrQuery.setQuery("answers:青春");
        final QueryResponse response = httpSolrClient.query(solrQuery);
        final SolrDocumentList documents = response.getResults();

        System.out.println("Found "+documents.getNumFound());
        for(SolrDocument document : documents) {
            final String user_token = (String) document.getFirstValue("user_token");
            final String answers = document.getFieldValue("answers").toString();
            System.out.println(user_token+"_____________________________"+answers);
//            final String name = (String) document.getFirstValue("answers");

        }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //Test
    public static void main(String[] args) {
        try {
            SolrDao solrDao = new SolrDao();
//            SolrInputDocument inputDocument = solrDao.constructSolrInputDocument("");
//            solrDao.commitDocument(inputDocument);
            solrDao.query("");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
