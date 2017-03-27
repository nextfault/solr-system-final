package solrsearchweb.controller;

import org.apache.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import query.SolrQueryManager;
import shared.models.Record;
import shared.models.RecordType;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

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
            String dd = "";
            records = SolrQueryManager.query(request.getParameter("keyword"));
        }catch (Exception ex){
            return new ArrayList<Record>();
        }

        return records;
    }
}
