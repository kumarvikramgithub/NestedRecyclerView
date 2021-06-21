package com.example.nestedrecyclerview;

import java.util.ArrayList;

public class ParentModel {
    String tvName;
    ArrayList<ChildModel> list;

    public ParentModel(String tvName, ArrayList<ChildModel> list) {
        this.tvName = tvName;
        this.list = list;
    }

    public ParentModel() {
    }

    public String getTvName() {
        return tvName;
    }

    public void setTvName(String tvName) {
        this.tvName = tvName;
    }

    public ArrayList<ChildModel> getList() {
        return list;
    }

    public void setList(ArrayList<ChildModel> list) {
        this.list = list;
    }
}
