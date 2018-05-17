package com.applicationswp.dataparser;


import java.util.ArrayList;
import java.util.List;

public class DataNode {
    private DataNode parent;
    private List<DataNode> children;
    private String userID;

    public DataNode() {
        children = new ArrayList<>();
    }

    public DataNode getParent() {
        return parent;
    }

    public void setParent(DataNode parent) {
        this.parent = parent;
    }

    public List<DataNode> getChildren() {
        return children;
    }

    public void setChildren(List<DataNode> children) {
        this.children = children;
    }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {
        this.userID = userID;
    }
}
