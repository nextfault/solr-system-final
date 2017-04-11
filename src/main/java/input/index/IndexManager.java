package input.index;

import org.apache.lucene.analysis.synonym.SolrSynonymParser;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import solr.MySolrClient;

/**
 * Created by Steven's on 2017/4/11.
 */
public class IndexManager {
    public static void deleteAllIndex(){
        HttpSolrClient client = MySolrClient.getInstance().getQueryClient();
        try {
            client.deleteByQuery( "*:*" );
            client.commit();
        }catch (Exception ex){
            System.out.println(ex.toString());
        }
    }
    //TODO 获取当前索引数
    public static long getIndexCount(){
       return 0;
    }
    public static void main(String[] args){
        IndexManager.deleteAllIndex();
    }
}
