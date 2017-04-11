package solrsearchweb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by Steven's on 2017/3/20.
 */

@Controller
@RequestMapping("/solrSearch")
public class ViewController {
    @RequestMapping
    public String defaultPage() {
        return "Main";
    }

    @RequestMapping("/input")
    public String inputPage(){
        return "Input";
    }
}
