package shared.models;

import com.sun.org.apache.regexp.internal.RE;

/**
 * Created by Steven's on 2017/3/3.
 */
public class Record {

    private Long id;
    private Long lineNo;
    private RecordType recordType;
    private String context;

    public Record() {}

    public Record(String context){
        this.context = context;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getLineNo() {
        return lineNo;
    }

    public void setLineNo(Long lineNo) {
        this.lineNo = lineNo;
    }

    public RecordType getRecordType() {
        return recordType;
    }

    public void setRecordType(RecordType recordType) {
        this.recordType = recordType;
    }

    public String getContext() {
        return context;
    }

    public void setContext(String context) {
        this.context = context;
    }
}
