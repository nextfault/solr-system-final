package input.datasource;

import com.sun.org.apache.regexp.internal.RE;
import input.context.ContextParser;
import input.datasource.support.CSVDataProviderImpl;
import shared.models.Record;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Steven's on 2017/3/3.
 */
public class DataProviderManager {
    private static final CSVDataProviderImpl csvDataProviderImpl = new CSVDataProviderImpl();

    public static List<Record> getRecordList(){
        List<Record> records = new ArrayList<Record>();
        try {
            records.addAll(csvDataProviderImpl.getDataSource("D:\\math.csv"));
        }catch (Exception ex){
            System.out.println();
        }

        preParse(records);
        return null;
    }

    public static List<Record> preParse(List<Record> records){
        if (records == null) return new ArrayList<Record>();
        for (Record record : records) {
            record.setContext(ContextParser.preParse(record.getContext()));
        }
        return records;
    }
}
