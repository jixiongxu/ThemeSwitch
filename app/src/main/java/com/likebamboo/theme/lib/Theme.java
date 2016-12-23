package com.likebamboo.theme.lib;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.likebamboo.theme.R;

/**
 * 主题管理类
 * Created by wentaoli on 2016/12/22.
 */
public class Theme {

    /**
     * 当前主题
     */
    private static int sCurrentTheme = R.style.AppTheme;

    /**
     * 获取当前主题
     *
     * @return 主题
     */
    public static int getCurrentTheme() {
        return sCurrentTheme;
    }

    /**
     * items
     */
    private final List<Item> items;

    public Theme() {
        this.items = new ArrayList<>();
    }

    /**
     * 切换主题到相应style
     *
     * @param context context
     */
    public void switchTheme(Context context) {
        if (context == null || items.isEmpty()) {
            return;
        }
        sCurrentTheme = (sCurrentTheme == R.style.AppTheme) ? R.style.AppThemeNight : R.style.AppTheme;
        // 切换
        int[] attrs = new int[items.size()];
        int i = 0;
        for (Item item : items) {
            attrs[i] = item.getAttrId();
            ++i;
        }

        Resources.Theme theme = context.getResources().newTheme();
        theme.applyStyle(sCurrentTheme, true);
        TypedArray a = theme.obtainStyledAttributes(sCurrentTheme, attrs);
        int index = 0;
        for (Item item : items) {
            item.onThemeChange(a, index++);
        }
        a.recycle();
    }

    /**
     * 添加一个String 主题
     *
     * @param view   view
     * @param attrId string属性id
     * @return this
     */
    public Theme text(TextView view, int attrId) {
        return add(new Item(view, ItemType.text, attrId));
    }

    /**
     * 添加一个textColor主题item
     *
     * @param view   view
     * @param attrId color属性id
     * @return this
     */
    public Theme textColor(TextView view, int attrId) {
        return add(new Item(view, ItemType.textColor, attrId));
    }

    /**
     * 添加一个textColor主题item
     *
     * @param view   view
     * @param attrId color属性id
     * @return this
     */
    public Theme textColor(Toolbar view, int attrId) {
        return add(new Item(view, ItemType.textColor, attrId));
    }

    /**
     * 添加一个背景color主题item
     *
     * @param view   view
     * @param attrId color属性id
     * @return this
     */
    @SuppressWarnings("unused")
    public Theme bgColor(View view, int attrId) {
        return add(new Item(view, ItemType.bgColor, attrId));
    }

    /**
     * 添加一个背景图片主题item
     *
     * @param view   view
     * @param attrId drawable属性id
     * @return this
     */
    public Theme bgDrawable(View view, int attrId) {
        return add(new Item(view, ItemType.bgDrawable, attrId));
    }

    /**
     * 添加一个src图片主题item
     *
     * @param view   view
     * @param attrId drawable属性id
     * @return this
     */
    public Theme srcDrawable(ImageView view, int attrId) {
        return add(new Item(view, ItemType.srcDrawable, attrId));
    }

    /**
     * 添加item
     *
     * @param item item
     * @return this
     */
    public Theme add(Item item) {
        if (item != null) {
            items.add(item);
        }
        return this;
    }
}
