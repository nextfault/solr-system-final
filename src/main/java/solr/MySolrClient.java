package solr;

import org.apache.solr.client.solrj.impl.HttpSolrClient;

/**
 * Created by Steven's on 2017/3/9.
 */
public class MySolrClient {

    private static final String URL="http://localhost:8983/solr/basic";
    private static MySolrClient instance = new MySolrClient();
    private HttpSolrClient client = null;

    public static MySolrClient getInstance() {
        return instance;
    }

    private MySolrClient() {
        client = new HttpSolrClient(URL);
        client.setConnectionTimeout(3000);
    }

    public HttpSolrClient getClient(){
        return client;
    }
}
