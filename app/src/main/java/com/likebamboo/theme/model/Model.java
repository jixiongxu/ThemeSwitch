
package com.likebamboo.theme.model;

import com.google.gson.annotations.SerializedName;

public class Model {

    @SerializedName("list")
    private java.util.List<ModelItem> mList;
    @SerializedName("md5")
    private String mMd5;
    @SerializedName("name")
    private String mName;
    @SerializedName("size")
    private String mSize;

    public java.util.List<ModelItem> getList() {
        return mList;
    }

    public void setList(java.util.List<ModelItem> list) {
        mList = list;
    }

    public String getMd5() {
        return mMd5;
    }

    public void setMd5(String md5) {
        mMd5 = md5;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getSize() {
        return mSize;
    }

    public void setSize(String size) {
        mSize = size;
    }

}
