package com.likebamboo.theme.lib;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 主题管理类
 * Created by wentaoli on 2016/12/22.
 */
public class Theme {

    /**
     * 当前主题路径，null表示默认主题
     */
    private static String sCurrentThemePath = null;

    /**
     * 主题资源resources
     */
    private static String sThemePkg;

    /**
     * 主题资源resources
     */
    private static Resources sResources;

    /**
     * items
     */
    private final List<Item> items;

    public Theme() {
        this.items = new ArrayList<>();
    }


    public static String getCurrentThemePath() {
        return sCurrentThemePath;
    }

    /**
     * 切换主题到相应style
     *
     * @param context      context
     * @param themeApkPath 主题apk路径
     */
    public boolean switchTheme(Context context, String themeApkPath) {
        if (context == null || items.isEmpty() || ("" + themeApkPath).equals("" + sCurrentThemePath)) {
            return false;
        }
        // 清除主题
        if (TextUtils.isEmpty(themeApkPath)) {
            sCurrentThemePath = null;
            sThemePkg = null;
            sResources = null;
            for (Item item : items) {
                item.onThemeChange(null, null);
            }
            return true;
        }

        if (sResources == null || sThemePkg == null) {
            File f = new File(themeApkPath);
            if (!f.exists() || f.isDirectory() || !f.canRead()) {
                return false;
            }
            // 获取皮肤包信息
            PackageInfo packageInfo = getThemePackageInfo(context, themeApkPath);
            // 获取皮肤资源信息
            Resources resources = genThemeResources(context, themeApkPath);
            if (packageInfo != null && resources != null) {
                sResources = resources;
                sThemePkg = packageInfo.packageName;
            }
        }
        if (sResources == null || sThemePkg == null) {
            return false;
        }
        sCurrentThemePath = themeApkPath;
        for (Item item : items) {
            item.onThemeChange(sThemePkg, sResources);
        }
        return true;
    }

    /**
     * 获取主题包信息
     *
     * @param apkPath apk文件路径
     * @return 主题资源
     */
    private static PackageInfo getThemePackageInfo(Context outContext, String apkPath) {
        try {
            // 获取packageInfo
            return outContext.getPackageManager().getPackageArchiveInfo(apkPath, PackageManager.GET_ACTIVITIES);
        } catch (Throwable e) {
            Log.e("wentaoli theme", "wentaoli, getThemePackageInfo error :" + e, e);
        }
        return null;
    }


    /**
     * 获取主题资源
     *
     * @param outContext 外部资源Context
     * @param apkPath    apk文件路径
     * @return Resources
     */
    private static Resources genThemeResources(Context outContext, String apkPath) {
        try {
            AssetManager assetManager = AssetManager.class.newInstance();
            Method addAssetPath = AssetManager.class.getMethod("addAssetPath", String.class);
            addAssetPath.invoke(assetManager, apkPath);
            DisplayMetrics metrics = outContext.getResources().getDisplayMetrics();
            Configuration con = outContext.getResources().getConfiguration();
            return new Resources(assetManager, metrics, con);
        } catch (Throwable e) {
            Log.e("wentaoli theme", "wentaoli, genThemeResources error :" + e, e);
        }
        return null;
    }

    /**
     * 添加一个String 主题
     *
     * @param view  view
     * @param resId string资源id
     * @return this
     */
    public Theme text(TextView view, int resId) {
        return add(new Item(view, ItemType.text, resId));
    }

    /**
     * 添加一个textColor主题item
     *
     * @param view  view
     * @param resId color资源id
     * @return this
     */
    public Theme textColor(TextView view, int resId) {
        return add(new Item(view, ItemType.textColor, resId));
    }

    /**
     * 添加一个textColor主题item
     *
     * @param view  view
     * @param resId color资源id
     * @return this
     */
    public Theme textColor(TextView view, int resId, boolean isSelector) {
        Item item = new Item(view, ItemType.textColor, resId);
        if (isSelector) item.isSelector();
        return add(item);
    }

    /**
     * 添加一个背景color主题item
     *
     * @param view  view
     * @param resId color资源id
     * @return this
     */
    @SuppressWarnings("unused")
    public Theme bgColor(View view, int resId) {
        return add(new Item(view, ItemType.bgColor, resId));
    }

    /**
     * 添加一个背景图片主题item
     *
     * @param view  view
     * @param resId drawable资源id
     * @return this
     */
    public Theme bgDrawable(View view, int resId) {
        return add(new Item(view, ItemType.bgDrawable, resId));
    }

    /**
     * 添加一个src图片主题item
     *
     * @param view  view
     * @param resId drawable资源id
     * @return this
     */
    public Theme srcDrawable(ImageView view, int resId) {
        return add(new Item(view, ItemType.srcDrawable, resId));
    }

    /**
     * 添加一个src图片主题item
     *
     * @param view  view
     * @param resId drawable资源id
     * @return this
     */
    public Theme srcDrawable(ImageView view, int resId, boolean isSelector) {
        Item item = new Item(view, ItemType.srcDrawable, resId);
        if (isSelector) item.isSelector();
        return add(item);
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
