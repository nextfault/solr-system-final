package shared.models;

/**
 * Created by Steven's on 2017/3/3.
 */
public enum RecordType {

    Chinese("Chinese"),
    Math("Math");


    private final String value;

    RecordType(String value){
        this.value = value;
    }

    public String getValue(){
        return value;
    }

    public static RecordType findByValue(String value){
        if (value.equals("Chinese")){
            return Chinese;
        }else if(value.equals("Math")){
            return Math;
        }else {
            return null;
        }
    }

}
