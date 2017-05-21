package solrsearchweb.controller;

import com.csvreader.CsvReader;
import input.datasource.support.CSVDataProviderImpl;
import input.index.IndexBuilder;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import query.SolrQueryManager;
import shared.models.JsonResponse;
import shared.models.Record;
import shared.models.RecordType;
import shared.util.CodeTimer;
import shared.util.ListUtil;
import solr.SolrServerClient;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;

/**
 * Created by Steven's on 2017/3/20.
 */
@Controller
@RequestMapping("/action/")
public class SearchController {
    @RequestMapping("/search/")
    @ResponseBody
    public List<Record> searchByKeyword(HttpServletRequest request){
        List<Record> records;
        try {
            String keyword = request.getParameter("keyword");
            if (keyword != null) {
                keyword = keyword.replaceAll(" ","");
            }
            Integer pageNum = 1;
            Integer pageSize = 10;
            pageNum = Integer.parseInt(request.getParameter("pageNum"));
            pageSize = Integer.parseInt(request.getParameter("pageSize"));
            records = SolrQueryManager.queryWithHightLight(keyword,pageNum,pageSize);
        }catch (Exception ex){
            return new ArrayList<Record>();
        }
        return records;
    }

    @RequestMapping("/suggest/")
    @ResponseBody
    public List<String> getSuggest(HttpServletRequest request){
        List<String> suggestions;
        try {
            String keyword = request.getParameter("word");
            suggestions = SolrQueryManager.getSuggest(request.getParameter("keyword"));
        }catch (Exception ex){
            return new ArrayList<String>();
        }
        return suggestions;
    }

    @RequestMapping("/upload/")
    @ResponseBody
    public JsonResponse uploadFile(HttpServletRequest request,@RequestParam MultipartFile[] csvfile){
        Long time = 0L;
        Integer num = 0;
        if (csvfile != null){
            for (MultipartFile myfile : csvfile) {
                try {
                    InputStream input = myfile.getInputStream();
                    final CsvReader reader =   new CsvReader(input, Charset.forName("UTF-8"));
                    CSVDataProviderImpl csvDataProvider = new CSVDataProviderImpl();
                    //获取前端页面上传的CSV的题目内容
                    final List<Record> records = csvDataProvider.getDataSource(reader);
                    num = records.size();
                    // 分10组 并发导入索引
                    final Map<Integer,List<Record>> map = ListUtil.split(records,num / 10);
                    final List<Future<Boolean>> futureList = new ArrayList<Future<Boolean>>();
                    final List<Boolean> resultList = new ArrayList<Boolean>();
                    Boolean allResult = true;
                    Long startTimeMs = 0L;
                    Long endTimeMs = 0L;
                    startTimeMs = System.currentTimeMillis();
                    for (List<Record> recordList : map.values()) {
                        Future<Boolean> indexBuilder = IndexBuilder.buildAsync(recordList);
                        futureList.add(indexBuilder);
                    }
                    //获取分组 每组是否索引成功
                    for (Future<Boolean> booleanFuture : futureList) {
                        resultList.add(booleanFuture.get());
                    }
//                    IndexBuilder.build(records);
                    //提交Solr
                    SolrServerClient.getInstance().getServer().commit();
                    endTimeMs = System.currentTimeMillis();
                    //计算索引构建耗时
                    time =endTimeMs - startTimeMs;
                    for (Boolean aBoolean : resultList) {
                        allResult = allResult && aBoolean;
                    }
                    if (!allResult) {
                        throw new Exception();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    JsonResponse response = new JsonResponse();
                    response.setCode(500);
                    response.setMsg("构建失败 ");
                    response.setExeTime(time);
                    return response;
                }
            }
        }
        JsonResponse response = new JsonResponse();
        response.setCode(200);
        response.setNum(num);
        response.setMsg("构建成功 ");
        response.setExeTime(time);
        return response;
    }
}
