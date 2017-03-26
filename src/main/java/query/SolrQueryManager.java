package query;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import solr.MySolrClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

/**
 * Created by Steven's on 2017/3/10.
 */
public class SolrQueryManager {
    private static HttpSolrClient solrClient = null;

    SolrQueryManager(){
        solrClient = MySolrClient.getInstance().getClient();
    }

    public static void query(String queryStr) throws SolrServerException, IOException {
        SolrQuery query = new SolrQuery();
        query.setRequestHandler("/select");
        query.setQuery(String.format("context:%s",queryStr));
        query.setSort("id", SolrQuery.ORDER.asc);
        QueryResponse rsp = solrClient.query(query);

        Iterator<SolrDocument> iter = rsp.getResults().iterator();
        SolrDocumentList docList = rsp.getResults();

        while(iter.hasNext()){
            SolrDocument doc = iter.next();
            String id  = (String)doc.getFieldValue("id");
            Collection<String> names = doc.getFieldNames();
            for (String fName : names){
                java.lang.Object fValue = doc.getFieldValue(fName);
                boolean isString = fValue instanceof String;
                boolean isList = fValue instanceof ArrayList<?>;
                if (isString){
                    fValue = (String)fValue;
                    System.out.print("fieldName:"+fName+"  filedValue:"+fValue+"\n");
                }
                if (isList){
                    for (String s : (ArrayList<String>)fValue){
                        System.out.print("fieldName:"+fName+"  filedValue:"+String.valueOf(s)+"\n");
                    }
                }
            }
        }
    }
}
