package com.likebamboo.theme.lib;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.likebamboo.theme.model.Model;
import com.likebamboo.theme.model.ModelItem;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
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
     *
     */
    private static List<ModelItem> sModelItems = null;

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
     * @param context  context
     * @param themeDir 主题存放目录
     */
    public boolean switchTheme(Context context, String themeDir) {
        if (context == null || items.isEmpty() || ("" + themeDir).equals("" + sCurrentThemePath)) {
            return false;
        }
        // 清除主题
        if (TextUtils.isEmpty(themeDir)) {
            sCurrentThemePath = null;
            for (Item item : items) {
                item.onThemeChange(null, null);
            }
            return true;
        }

        if (sModelItems == null) {
            sModelItems = parseModels(themeDir + File.separator + "config.json");
        }

        if (sModelItems == null) {
            return false;
        }
        sCurrentThemePath = themeDir;
        for (Item item : items) {
            for (ModelItem model : sModelItems) {
                if (model.getName().equals(item.resTag)) {
                    item.onThemeChange(sCurrentThemePath, model);
                }
            }
        }
        return true;
    }

    private static List<ModelItem> parseModels(String filePath) {
        File f = new File(filePath);
        if (!f.exists() || !f.isFile()) {
            return null;
        }

        InputStream in = null;
        ByteArrayOutputStream bos = null;
        try {
            byte[] tempbytes = new byte[512];
            int len = 0;
            in = new FileInputStream(f);
            bos = new ByteArrayOutputStream();
            // 读入多个字节到字节数组中，byteread为一次读入的字节数
            while ((len = in.read(tempbytes)) != -1) {
                bos.write(tempbytes, 0, len);
            }
            Model model = new Gson().fromJson(new String(bos.toByteArray()), Model.class);
            return model == null ? null : model.getList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (bos != null) {
                try {
                    bos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    /**
     * 清除数据
     */
    public void clear() {
        sCurrentThemePath = null;
        sModelItems = null;
        items.clear();
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
