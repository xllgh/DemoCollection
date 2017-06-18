package com.xll.administrator.entity;

import com.orm.SugarRecord;

/**
 * Created by Administrator on 2017/4/6.
 */
public class Student extends SugarRecord{

    private String name;
    private long id;

    public Student(String name,long id){
        this.name=name;
        this.id=id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public void setId(int id) {
        this.id = id;
    }
}
