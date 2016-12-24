
package com.likebamboo.theme.model;

import com.google.gson.annotations.SerializedName;

public class ModelItem {

    @SerializedName("bg")
    private String mBg;
    @SerializedName("icon")
    private String mIcon;
    @SerializedName("icon_s")
    private String mIconS;
    @SerializedName("name")
    private String mName;
    @SerializedName("text")
    private String mText;
    @SerializedName("text_color")
    private String mTextColor;
    @SerializedName("text_color_s")
    private String mTextColorS;

    public String getBg() {
        return mBg;
    }

    public void setBg(String bg) {
        mBg = bg;
    }

    public String getIcon() {
        return mIcon;
    }

    public void setIcon(String icon) {
        mIcon = icon;
    }

    public String getIconS() {
        return mIconS;
    }

    public void setIconS(String icon_s) {
        mIconS = icon_s;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public String getTextColor() {
        return mTextColor;
    }

    public void setTextColor(String text_color) {
        mTextColor = text_color;
    }

    public String getTextColorS() {
        return mTextColorS;
    }

    public void setTextColorS(String text_color_s) {
        mTextColorS = text_color_s;
    }
}
