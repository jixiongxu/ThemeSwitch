package com.likebamboo.theme;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v4.app.FragmentTabHost;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.likebamboo.theme.lib.Item;
import com.likebamboo.theme.lib.ItemType;
import com.likebamboo.theme.lib.Theme;
import com.likebamboo.theme.utils.AssetsUtils;

import java.io.File;


/**
 * 主界面
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    /**
     * 首页Tab
     */
    public static final String TAB_HOME = "home";

    /**
     * 发现Tab
     */
    public static final String TAB_FIND = "find";

    /**
     * 体育直播Tab
     */
    public static final String TAB_LIVE = "live";

    /**
     * 个人中心Tab
     */
    public static final String TAB_USER = "user";


    /**
     * toolbar
     */
    protected Toolbar mToolbar;

    /**
     * 主题管理
     */
    private Theme mTheme = new Theme();

    /**
     * tab host
     */
    private FragmentTabHost mTabHost;

    /**
     * 主题文件路径
     */
    private String themeFilePath = null;
    /**
     *
     */
    private Button switchThemBt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        String unZipDir = getDir("theme", MODE_PRIVATE).getAbsoluteFile() + File.separator + "zip" + File.separator;
        try {
            AssetsUtils.unZip(this, "theme.zip", unZipDir, true);
            themeFilePath = unZipDir;
        } catch (Exception e) {
            e.printStackTrace();
            themeFilePath = null;
        }
        // 初始化views
        initView();

        switchThemBt = (Button) findViewById(R.id.switch_theme);
        switchThemBt.setOnClickListener(this);
    }

    /**
     *
     */
    private void initView() {
        mToolbar = (Toolbar) findViewById(R.id.toolbar);

        mTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mTabHost.setup(this, getSupportFragmentManager(), R.id.tab_content);

        mTabHost.addTab(buildTabSpec(TAB_HOME, R.string.tab_home, R.drawable.tab_home), BaseFragment.class, null);
        mTabHost.addTab(buildTabSpec(TAB_LIVE, R.string.tab_live, R.drawable.tab_live), BaseFragment.class, null);
        mTabHost.addTab(buildTabSpec(TAB_FIND, R.string.tab_find, R.drawable.tab_find), BaseFragment.class, null);
        mTabHost.addTab(buildTabSpec(TAB_USER, R.string.tab_user, R.drawable.tab_user), BaseFragment.class, null);

    }

    /**
     * 创建一个Tabspec
     *
     * @param tabTag tag
     * @param strId  字符串Id
     * @param iconId iconId
     * @return tab
     */
    private TabHost.TabSpec buildTabSpec(String tabTag, int strId, int iconId) {
        @SuppressLint("InflateParams")
        View tab = getLayoutInflater().inflate(R.layout.main_tab_item, null);

        TextView textTv = (TextView) tab.findViewById(R.id.text);
        textTv.setText(strId);

        ImageView iconIv = (ImageView) tab.findViewById(R.id.img);
        iconIv.setImageResource(iconId);

        // 主题 -> tab文字颜色
        mTheme.add(new Item(textTv, ItemType.textColor, R.color.tab_color, "main_tab"));
        // 主题 -> tab文字
        mTheme.text(textTv, strId);
        // 主题 -> tab图片
        mTheme.srcDrawable(iconIv, iconId);

        return mTabHost.newTabSpec(tabTag).setIndicator(tab);
    }


    /**
     * 切换主题
     */
    private void switchTheme() {
        if (themeFilePath == null) {
            Toast.makeText(this, "没有找到apk主题包", Toast.LENGTH_SHORT).show();
            return;
        }
        if (TextUtils.isEmpty(Theme.getCurrentThemePath())) {
            boolean ret = mTheme.switchTheme(this, themeFilePath);
            if (ret) switchThemBt.setText(R.string.theme_default);
        } else {
            boolean ret = mTheme.switchTheme(this, null);
            if (ret) switchThemBt.setText(R.string.theme_christmas);
        }
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.switch_theme) {
            switchTheme();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mTheme.clear();
    }
}