package shared.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Steven's on 2017/4/11.
 */
public class ListUtil {
    /**
     * list 分组
     * @param list
     * @param splitSize
     * @param <T>
     * @return
     */
    public static <T> Map<Integer, List<T>> split(List<T> list, int splitSize){
        Map<Integer,List<T>> map = new HashMap<Integer, List<T>>();
        int batchNo = 0;
        List<T> subList = null;
        for (int i=0; i<list.size(); i++){
            if (i % splitSize == 0){
                batchNo++;
                subList = new ArrayList<T>();
                map.put(batchNo,subList);
            }
            subList.add(list.get(0));
        }
        return map;
    }

}
