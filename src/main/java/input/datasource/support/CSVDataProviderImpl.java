package input.datasource.support;

import com.csvreader.CsvReader;
import input.datasource.DataProvider;
import shared.models.Record;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Steven's on 2017/3/3.
 */
public class CSVDataProviderImpl implements DataProvider{

    public List<Record> getDataSource(String filePath) throws Exception {
        CsvReader csvReader = null;
        List<Record> recordList = new ArrayList<Record>();
        if (!checkFileType(filePath)) return recordList;
        try {
            csvReader = CsvReaderBuilder(filePath);
        }catch (Exception ex){
            System.out.println(ex);
        }

        if (csvReader != null){
            while (csvReader.readRecord()){
                Record record = new Record(csvReader.getRawRecord());
                record.setLineNo(csvReader.getCurrentRecord());
                recordList.add(record);
            }
        }
        return recordList;
    }

    public List<Record> getDataSource(CsvReader csvReader) throws IOException {
        List<Record> recordList = new ArrayList<Record>();
        if (csvReader != null){
            while (csvReader.readRecord()){
                Record record = new Record(csvReader.getRawRecord());
                record.setLineNo(csvReader.getCurrentRecord());
                recordList.add(record);
            }
        }
        return recordList;
    }

    public static void main(String[] args) throws Exception{
        CSVDataProviderImpl imp = new CSVDataProviderImpl();
        imp.getDataSource("D:\\math.csv");
    }

    private CsvReader CsvReaderBuilder(String filePath) throws Exception{
        if (filePath == null) return  null;
        File file = new File(filePath);
        CsvReader cr2 = new CsvReader(new FileReader(file));
        return cr2;
    }

    private boolean checkFileType(String filePath){
        if (filePath == null) return false;
        int index = filePath.lastIndexOf(".");
        String fileTypeStr = filePath.substring(index + 1, filePath.length());
        fileTypeStr = fileTypeStr.toUpperCase();
        if (fileTypeStr.equals("CSV")){
            return true;
        }else {
            return false;
        }
    }

}
