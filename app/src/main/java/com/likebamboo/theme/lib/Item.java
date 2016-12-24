package com.likebamboo.theme.lib;


import android.content.res.ColorStateList;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.StateListDrawable;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.likebamboo.theme.model.ModelItem;

import java.io.File;

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
     * 资源id
     */
    protected int resId;

    /**
     * 资源tag
     */
    protected String resTag;

    public Item(View v, ItemType type, int resId) {
        this.view = v;
        this.type = type;
        this.resId = resId;
        this.resTag = v.getResources().getResourceEntryName(resId);
        Log.e("theme", "theme item = > " + resTag);
    }

    public Item(View v, ItemType type, int resId, String tag) {
        this.view = v;
        this.type = type;
        this.resId = resId;
        this.resTag = tag;
    }

    /**
     * 主题更新
     *
     * @param themeDir 主题文件夹
     * @param model    主题model
     */
    public void onThemeChange(String themeDir, ModelItem model) {
        // 没有主题
        if (themeDir == null || model == null) {
            switch (type) {
                case bgColor: // 背景色
                    // 如果是本地资源，直接使用id就行
                    view.setBackgroundColor(resId);
                    break;
                case bgDrawable: // 背景图
                    // 如果是本地资源，直接使用id就行
                    view.setBackgroundResource(resId);
                    break;
                case srcDrawable: // 资源图
                    if (!(view instanceof ImageView)) {
                        break;
                    }

                    // 如果是本地资源，直接使用id就行
                    ((ImageView) view).setImageResource(resId);
                    break;
                case textColor: // 字体颜色
                    if (!(view instanceof TextView)) {
                        break;
                    }
                    ((TextView) view).setTextColor(view.getResources().getColor(resId));
                    break;
                case text: // 文案
                    if (!(view instanceof TextView)) {
                        break;
                    }
                    // 如果是本地资源，直接使用id就行
                    ((TextView) view).setText(resId);
                    break;
                default:
                    break;
            }
            return;
        }
        switch (type) {
            case bgColor: // 背景色
                try {
                    int color = Color.parseColor(model.getBg());
                    view.setBackgroundColor(color);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case bgDrawable: // 背景图
                try {
                    Drawable bg = BitmapDrawable.createFromPath(themeDir + File.separator + model.getBg());
                    view.setBackgroundDrawable(bg);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case srcDrawable: // 资源图
                if (!(view instanceof ImageView)) {
                    break;
                }
                try {
                    Drawable icon = BitmapDrawable.createFromPath(themeDir + File.separator + model.getIcon());
                    Drawable iconS = BitmapDrawable.createFromPath(themeDir + File.separator + model.getIconS());
                    if (icon != null && iconS != null) {
                        Log.e("theme", "wentaoli = > s, " + resTag);
                        StateListDrawable drawable = new StateListDrawable();
                        drawable.addState(new int[]{android.R.attr.state_checked}, iconS);
                        drawable.addState(new int[]{android.R.attr.state_focused}, iconS);
                        drawable.addState(new int[]{android.R.attr.state_selected}, iconS);
                        drawable.addState(new int[]{}, icon);
                        ((ImageView) view).setImageDrawable(drawable);
                    } else {
                        ((ImageView) view).setImageDrawable(icon);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case textColor: // 字体颜色
                if (!(view instanceof TextView)) {
                    break;
                }
                try {
                    int color = Color.parseColor(model.getTextColor());
                    int colorS = Integer.MAX_VALUE;
                    try {
                        colorS = Color.parseColor(model.getTextColorS());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    if (colorS != Integer.MAX_VALUE) {
                        int[] colors = new int[]{colorS, colorS, colorS, color};
                        int[][] states = new int[4][];
                        states[0] = new int[]{android.R.attr.state_pressed};
                        states[1] = new int[]{android.R.attr.state_focused};
                        states[2] = new int[]{android.R.attr.state_selected};
                        states[3] = new int[]{};
                        ((TextView) view).setTextColor(new ColorStateList(states, colors));
                    } else {
                        ((TextView) view).setTextColor(color);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                break;
            case text: // 文案
                if (!(view instanceof TextView)) {
                    break;
                }

                // 不是本地资源，必须获取资源才行
                ((TextView) view).setText(model.getText());
                break;
            default:
                break;
        }
    }

}
