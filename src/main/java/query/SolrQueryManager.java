package query;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import shared.models.Record;
import shared.models.RecordType;
import solr.MySolrClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

/**
 * Created by Steven's on 2017/3/10.
 */
public class SolrQueryManager {
    private static HttpSolrClient solrClient = MySolrClient.getInstance().getClient();

    public static List<Record> query(String queryStr) throws SolrServerException, IOException {
        List<Record> recordList = new ArrayList<Record>();

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
            List contextList = (List)doc.getFieldValue("context");
            String context = (String) contextList.get(0);
            RecordType recordType = RecordType.findByValue((String) doc.getFieldValue("recordtype"));
            Record record = new Record(context);
            record.setRecordType(recordType);
            record.setId(Long.parseLong(id));
            recordList.add(record);
        }

        return recordList;
    }
}
