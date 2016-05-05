package com.abition.self;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;

public class PlanTable extends BmobObject {
    private String title;
    private String user_id;
    private Integer type;
    private BmobDate dateFrom;
    private BmobDate dateTo;
    private BmobDate datePersist;
    private Integer status;

    public PlanTable(){

    }

    public PlanTable(String title, BmobDate dateFrom, BmobDate datePersist, BmobDate dateTo, String user_id, Integer type, Integer status) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.datePersist = datePersist;
        this.user_id = user_id;
        this.type = type;
        this.title = title;
        this.status = status;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public BmobDate getDateFrom() {
        return dateFrom;
    }

    public void setDateFrom(BmobDate dateFrom) {
        this.dateFrom = dateFrom;
    }

    public BmobDate getDateTo() {
        return dateTo;
    }

    public void setDateTo(BmobDate dateTo) {
        this.dateTo = dateTo;
    }

    public BmobDate getDatePersist() {
        return datePersist;
    }

    public void setDatePersist(BmobDate datePersist) {
        this.datePersist = datePersist;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }
}
