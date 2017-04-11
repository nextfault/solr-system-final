package solr;

import org.apache.solr.client.solrj.impl.ConcurrentUpdateSolrClient;
import org.apache.solr.client.solrj.impl.HttpSolrClient;

/**
 * Created by Steven's on 2017/3/9.
 */
public class MySolrClient {

    private static final String URL="http://localhost:8983/solr/basic";
    private static MySolrClient instance = new MySolrClient();
    private HttpSolrClient queryClient = null;

    /**
     * queryClient
     * @return
     */
    public static MySolrClient getInstance() {
        return instance;
    }

    private MySolrClient() {
        queryClient = new HttpSolrClient(URL);
        queryClient.setConnectionTimeout(3000);
    }

    public HttpSolrClient getQueryClient(){
        return queryClient;
    }

    public static HttpSolrClient getUpdateClient(){
        HttpSolrClient client = new HttpSolrClient(URL);
        return client;
    }
}
