package com.likebamboo.theme.lib;


import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.StateListDrawable;
import android.text.TextUtils;
import android.util.Log;
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
     * 资源id
     */
    protected int resId;

    /**
     * 资源名称
     */
    protected String resName;

    /**
     * 资源是否是个选择器
     */
    protected boolean isSelector;

    public Item(View v, ItemType type, int resId) {
        this.view = v;
        this.type = type;
        this.resId = resId;
        if (this.resId > 0) {
            Resources resources = view.getContext().getResources();
            resName = resources.getResourceEntryName(this.resId);
        }
    }

    /**
     * 资源是一个selector
     */
    public void isSelector() {
        isSelector = true;
    }

    /**
     * 主题更新
     *
     * @param themePkg 主题包名
     * @param themeRes 主题资源
     */
    public void onThemeChange(String themePkg, Resources themeRes) {
        // 没有主题吗？
        if (themeRes == null) {
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
        int themeResId;
        switch (type) {
            case bgColor: // 背景色
                themeResId = getResourceId(themePkg, themeRes, "color", resName);
                // 不是本地资源，必须获取资源才行
                if (isSelector) {
                    ColorStateList stateList = themeRes.getColorStateList(themeResId);
                    if (stateList == null) {
                        break;
                    }
                    StateListDrawable bg = new StateListDrawable();
                    // 获取默认颜色
                    int defaultColor = stateList.getDefaultColor();
                    bg.addState(new int[]{}, new ColorDrawable(defaultColor));
                    // 选中色
                    int[] selectSet = {android.R.attr.state_selected};
                    bg.addState(selectSet, new ColorDrawable(stateList.getColorForState(selectSet, defaultColor)));
                    // 按下色
                    selectSet = new int[]{android.R.attr.state_pressed};
                    bg.addState(selectSet, new ColorDrawable(stateList.getColorForState(selectSet, defaultColor)));
                    // checked色
                    selectSet = new int[]{android.R.attr.state_checked};
                    bg.addState(selectSet, new ColorDrawable(stateList.getColorForState(selectSet, defaultColor)));
                    view.setBackgroundDrawable(bg);
                    break;
                }
                view.setBackgroundColor(themeRes.getColor(themeResId));
                break;
            case bgDrawable: // 背景图
                themeResId = getResourceId(themePkg, themeRes, "drawable", resName);
                // 不是本地资源，必须获取资源才行
                view.setBackgroundDrawable(themeRes.getDrawable(themeResId));
                break;
            case srcDrawable: // 资源图
                if (!(view instanceof ImageView)) {
                    break;
                }
                themeResId = getResourceId(themePkg, themeRes, "drawable", resName);
                // 不是本地资源，必须获取资源才行
                ((ImageView) view).setImageDrawable(themeRes.getDrawable(themeResId));
                break;
            case textColor: // 字体颜色
                if (!(view instanceof TextView)) {
                    break;
                }

                themeResId = getResourceId(themePkg, themeRes, "color", resName);
                if (isSelector) {
                    ((TextView) view).setTextColor(themeRes.getColorStateList(themeResId));
                } else {
                    ((TextView) view).setTextColor(themeRes.getColor(themeResId));
                }
                break;
            case text: // 文案
                if (!(view instanceof TextView)) {
                    break;
                }

                themeResId = getResourceId(themePkg, themeRes, "string", resName);
                // 不是本地资源，必须获取资源才行
                ((TextView) view).setText(themeRes.getString(themeResId));
                break;
            default:
                break;
        }
    }


    /**
     * 获取主题包中资源的id
     *
     * @param pkg     包名
     * @param res     资源
     * @param resType 资源类型
     * @param resName 资源名称
     * @return 资源id
     */
    public static int getResourceId(String pkg, Resources res, String resType, String resName) {
        if (res == null || TextUtils.isEmpty(resType) || TextUtils.isEmpty(resName)) {
            return -1;
        }
        try {
            return res.getIdentifier(resName, resType, pkg);
        } catch (Exception e) {
            Log.e("wentaoli skin", "wentaoli - > getResourceId error " + e, e);
        }
        return -1;
    }

}
