package com.umuc.swen.java.stockanalyzer.daomodels;

import java.io.Serializable;

/**
 *
 * @author roy
 */
public class StockLogs implements Serializable {
    
    private int log_id;
    
    private String source;
    
    private String start_date;
    
    private String end_date;
    
    private String status;
    
    private String log;
    
    public int getLog_id() {
        return log_id;
    }
    
    public void setLog_id(int log_id) {
        this.log_id = log_id;
    }
    
    public String getSource() {
        return source;
    }
    
    public void setSource(String source) {
        this.source = source;
    }
    
    public String getStart_date() {
        return start_date;
    }
    
    public void setStart_date(String start_date) {
        this.start_date = start_date;
    }
    
    public String getEnd_date() {
        return end_date;
    }
    
    public void setEnd_date(String end_date) {
        this.end_date = end_date;
    }
    
    public String getStatus() {
        return status;
    }
    
    public void setStatus(String status) {
        this.status = status;
    }
    
    public String getLog() {
        return log;
    }
    
    public void setLog(String log) {
        this.log = log;
    }
    
    @Override
    
    public String toString() {
        return "StockLogs [log_id=" + log_id + ", source=" + source + ", start_date=" + start_date + ", end_date=" + end_date + ", status=" + status + ", log=" + log + "]";
    }
    
}
