package solrsearchweb.controller;

import org.apache.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import query.SolrQueryManager;
import shared.models.Record;

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
        try {
            String keyword = request.getParameter("keyword");
            String dd = "";
//            SolrQueryManager.query(request.getParameter("keyword"));
        }catch (Exception ex){
            return new ArrayList<Record>();
        }
        List<Record> a = new ArrayList<Record>();
        a.add(new Record("xxx"));
        a.add(new Record("ccc"));
        return a;
    }
}
