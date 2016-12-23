package com.likebamboo.theme.lib;


import android.content.res.TypedArray;
import android.graphics.Color;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 主题item抽象类
 * <p>
 * Created by wentaoli on 2016/12/22.
 */
public class Item {

    /**
     * view
     */
    protected View view;

    /**
     * 类型
     */
    protected ItemType type;

    /**
     * 该资源所使用的属性值
     */
    protected int attrId;

    public Item(View mView, ItemType mSetterType, int attrId) {
        this.view = mView;
        this.type = mSetterType;
        this.attrId = attrId;
    }

    public View getView() {
        return view;
    }

    public void setView(View view) {
        this.view = view;
    }

    public ItemType getType() {
        return type;
    }

    public void setType(ItemType type) {
        this.type = type;
    }

    public int getAttrId() {
        return attrId;
    }

    public void setAttrId(int attrId) {
        this.attrId = attrId;
    }

    /**
     * 皮肤更新
     *
     * @param typedArray typedArray
     * @param index      index
     */
    public void onThemeChange(TypedArray typedArray, int index) {
        switch (type) {
            case bgColor: // 背景颜色
                view.setBackgroundColor(typedArray.getColor(index, Color.BLACK));
                break;
            case bgDrawable: // 背景图
                view.setBackgroundDrawable(typedArray.getDrawable(index));
                break;
            case srcDrawable: // src图
                if (view instanceof ImageView) {
                    ((ImageView) view).setImageDrawable(typedArray.getDrawable(index));
                }
                break;
            case textColor: // 文案颜色
                if (view instanceof TextView) {
                    ((TextView) view).setTextColor(typedArray.getColor(index, Color.WHITE));
                } else if (view instanceof Toolbar) {
                    ((Toolbar) view).setTitleTextColor(typedArray.getColor(index, Color.WHITE));
                }
                break;
        }
    }
}
