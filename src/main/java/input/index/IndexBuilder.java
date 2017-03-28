package input.index;

import input.datasource.DataProviderManager;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.impl.HttpSolrClient;
import org.apache.solr.common.SolrInputDocument;
import shared.models.Record;
import solr.MySolrClient;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Steven's on 2017/3/4.
 */
public class IndexBuilder {

    private static long indexCount = 0;
    private HttpSolrClient solrClient = null;

    IndexBuilder(){
        solrClient = MySolrClient.getInstance().getClient();
    }

    public static void main(String args[]) {
        IndexBuilder indexBuilder = new IndexBuilder();
        indexBuilder.build();
    }
    boolean build(){
        List<Record> records = DataProviderManager.getRecordList();
        List<SolrInputDocument> docs = new ArrayList<SolrInputDocument>();

        for (Record record : records) {
            docs.add(parse2SolrDoc(record));
        }

        try {
            solrClient.add(docs);
            solrClient.commit();
            return true;
        } catch (SolrServerException e) {
            e.printStackTrace();
        } catch (IOException e) {
            System.out.println(e);
        }
        return false;
    }

    SolrInputDocument parse2SolrDoc(Record record){
        SolrInputDocument solrInputDocument = new SolrInputDocument();
        solrInputDocument.addField("id",indexCount++);
        solrInputDocument.addField("recordtype",record.getRecordType().getValue());
        solrInputDocument.addField("context",record.getContext());
        return solrInputDocument;
    }
}
