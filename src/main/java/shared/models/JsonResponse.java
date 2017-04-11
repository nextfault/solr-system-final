package shared.models;

/**
 * Created by Steven's on 2017/4/9.
 */
public class JsonResponse {
    private Integer code;
    private Integer num;
    private String msg;
    private Long exeTime;
    private String msg1;

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Long getExeTime() {
        return exeTime;
    }

    public void setExeTime(Long exeTime) {
        this.exeTime = exeTime;
    }

    public String getMsg1() {
        return msg1;
    }

    public void setMsg1(String msg1) {
        this.msg1 = msg1;
    }
}
