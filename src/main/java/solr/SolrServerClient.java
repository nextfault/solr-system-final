package solr;

import org.apache.solr.client.solrj.impl.HttpSolrClient;

/**
 * Created by Steven's on 2017/4/12.
 */
public class SolrServerClient {
    private String SOLR_ADMIN_URL = "http://localhost:8983/solr/basic";
    private static HttpSolrClient server = null;
    private volatile static SolrServerClient solrServiceClient = null;



    private SolrServerClient() {
        this.getServer();
    }


    /**
     * SolrServerClient 是线程安全的 需要采用单例模式
     * 此处实现方法适用于高频率调用查询
     *
     * @return SolrServerClient
     */
    public static SolrServerClient getInstance() {
        if (solrServiceClient == null) {
            synchronized (SolrServerClient.class) {
                if (solrServiceClient == null) {
                    solrServiceClient = new SolrServerClient();
                }
            }
        }
        return solrServiceClient;
    }


    /**
     * 初始化的HttpSolrServer 对象,并获取此唯一对象
     * 配置按需调整
     * @return 此server对象
     */
    public HttpSolrClient getServer() {
        if (server == null) {
            server = new HttpSolrClient(SOLR_ADMIN_URL);
            server.setConnectionTimeout(3000);
            server.setDefaultMaxConnectionsPerHost(100);
            server.setMaxTotalConnections(100);
        }
        return server;
    }
}
