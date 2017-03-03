package input.datasource.support;

import com.csvreader.CsvReader;
import input.datasource.DataProvider;
import shared.models.Record;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileReader;
import java.io.InputStreamReader;
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
    public static void main(String[] args) throws Exception{
        CSVDataProviderImpl imp = new CSVDataProviderImpl();
        imp.getDataSource("D:\\math.csv");
    }

    private CsvReader CsvReaderBuilder(String filePath) throws Exception{
        if (filePath == null) return  null;
        File file = new File(filePath);
//        // 一般，Uses ISO-8859-1 as the Charset.
//        CsvReader cr1 = new CsvReader(path);
//        // 有中文的
//        CsvReader cr2 = new CsvReader(new FileReader(new File(path)));
//        // 需要指定读入编码的
//        CsvReader cr = new CsvReader(new InputStreamReader(new FileInputStream(new File(path)),"UTF-8"));
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
