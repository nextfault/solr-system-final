package shared.util;

import java.util.concurrent.Callable;

/**
 * Created by Steven's on 2017/3/3.
 */
public class CodeTimer {
    /**
     * 计算平均执行时间
     * @param methodName
     * @param executeTimes
     * @param callable
     * @return
     * @throws Exception
     */
    public static Long execute(String methodName, Integer executeTimes, Callable<Boolean> callable) throws Exception{
        Long totalTime = 0L;
        for (int i = 0; i < executeTimes; i++){
            Long startTimeMs = 0L;
            Long endTimeMs = 0L;
            try {
                startTimeMs = System.currentTimeMillis();
                callable.call();
            } catch (Exception ex){
                System.out.println(ex);
            }finally {
                endTimeMs = System.currentTimeMillis();
            }
            Long time = endTimeMs - startTimeMs;
            System.out.println("[Method] " + methodName + " execute time is " + time + " execute " + executeTimes + " times ");
            totalTime+=time;
        }
        return totalTime / executeTimes;
    }
}
