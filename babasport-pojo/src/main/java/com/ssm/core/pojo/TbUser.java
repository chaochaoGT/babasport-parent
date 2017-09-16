package com.ssm.core.pojo;

import java.io.Serializable;

/**
 * 测试pojo
 *
 * Created by Administrator on 2017/8/13.
 */
public class TbUser implements Serializable {

    private int id;
    private  String name;
    private  String sex;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }


}
