package input.context;

import shared.util.HtmlUtil;

/**
 * Created by Steven's on 2017/3/3.
 */
public class ContextParser {
    /**
     *
     * @param context
     * @return
     */
    public static String preParse(String context){
        if (context == null) {
            return "";
        }
        return HtmlUtil.getTextFromHtml(context);
    }
}
