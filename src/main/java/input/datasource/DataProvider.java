package input.datasource;

import shared.models.Record;

import java.util.List;

/**
 * Created by Steven's on 2017/3/3.
 */
public interface DataProvider {
    List<Record> getDataSource(String filePath) throws Exception;
}
