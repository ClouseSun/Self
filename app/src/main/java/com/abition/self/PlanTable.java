package com.abition.self;

import cn.bmob.v3.BmobObject;
import cn.bmob.v3.datatype.BmobDate;

public class PlanTable extends BmobObject {
    private BmobDate dateFrom;
    private BmobDate dateTo;
    private BmobDate persist_date;
    private String user_id;
    private Integer type;
    private String name;

    public PlanTable(String name, BmobDate dateFrom, BmobDate dateTo, String user_id, Integer type) {
        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.persist_date = dateFrom;
        this.user_id = user_id;
        this.type = type;
        this.name = name;
    }

    public BmobDate getDateFrom() {
        return dateFrom;
    }

    public BmobDate getDateTo() {
        return dateTo;
    }

    public BmobDate getPersist_date() {
        return persist_date;
    }

    public void setPersist_date(BmobDate input) {
        persist_date = input;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String input) {
        user_id = input;
    }

    public String getName() {
        return name;
    }

    public void setName(String input) {
        name = input;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer input) {
        type = input;
    }
}
