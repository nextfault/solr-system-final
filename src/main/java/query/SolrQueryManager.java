package query;

import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import shared.models.Record;
import shared.models.RecordType;
import solr.MySolrClient;

import java.io.IOException;
import java.util.*;

/**
 * Created by Steven's on 2017/3/10.
 */
public class SolrQueryManager {
    private static HttpSolrClient solrClient = MySolrClient.getInstance().getQueryClient();

    public static List<Record> query(String queryStr,Integer pageNum,Integer pageSize) throws SolrServerException, IOException {
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

    /**
     *
     * @param queryStr 关键词
     * @param pageNum 页号
     * @param pageSize 每页大小
     * @return
     * @throws SolrServerException
     * @throws IOException
     */
    public static List<Record> queryWithHightLight(String queryStr,Integer pageNum,Integer pageSize) throws SolrServerException, IOException {
        List<Record> recordList = new ArrayList<Record>();
        SolrQuery query = new SolrQuery();
        query.setRequestHandler("/select");
        query.setQuery(String.format("context:%s",queryStr));
        query.setHighlight(true); // 开启高亮组件
        query.addHighlightField("context");// 高亮字段
        query.setHighlightSimplePre("<mark>");// 标记
        query.setHighlightSimplePost("</mark>");
        query.setStart((pageNum - 1) * pageSize);
        query.setRows(pageSize);
//        query.setSort("id", SolrQuery.ORDER.asc);
        QueryResponse rsp = solrClient.query(query);

        Map rspMap = rsp.getHighlighting();

        Iterator<SolrDocument> iter = rsp.getResults().iterator();
        SolrDocumentList docList = rsp.getResults();
        while(iter.hasNext()){
            SolrDocument doc = iter.next();
            String id  = (String)doc.getFieldValue("id");
            Map m = (Map) rspMap.get(id);
            List contextList = (List) m.get("context");
            String context = (String) contextList.get(0);
            RecordType recordType = RecordType.findByValue((String) doc.getFieldValue("recordtype"));
            Record record = new Record(context);
            record.setRecordType(recordType);
            record.setId(Long.parseLong(id));
            recordList.add(record);
        }
        return recordList;
    }

    public static List<String> getSuggest(String word) throws SolrServerException, IOException {
        List<String> result = new ArrayList<String>();
        SolrQuery query = new SolrQuery();
        query.set("q", word);
        query.set("qt", "/suggest");
        query.set("spellcheck.count", "10");
        QueryResponse response = solrClient.query(query);
        SpellCheckResponse spellCheckResponse = response.getSpellCheckResponse();
        if (spellCheckResponse != null){
            List<SpellCheckResponse.Suggestion> suggestionList = spellCheckResponse.getSuggestions();
            for (SpellCheckResponse.Suggestion suggestion : suggestionList) {
                result.addAll(suggestion.getAlternatives());
            }
        }
        return result;
    }
}
