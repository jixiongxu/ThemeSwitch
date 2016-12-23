package com.likebamboo.theme;

import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.likebamboo.theme.lib.Item;
import com.likebamboo.theme.lib.ItemType;
import com.likebamboo.theme.lib.Theme;

import static com.likebamboo.theme.R.id.toolbar;
import static com.likebamboo.theme.R.id.viewpager;


/**
 * 主界面
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    public static final String[] DATAS = {"first fragment", "second fragment", "third fragment"};

    /**
     * toolbar
     */
    protected Toolbar mToolbar;

    /**
     * tabLayout
     */
    private TabLayout mTabLayout;

    /**
     * viewPager
     */
    private ViewPager mViewPager = null;

    /**
     * adapter
     */
    private TabFragmentAdapter adapter = null;

    /**
     * 主题管理
     */
    private Theme mTheme;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(Theme.getCurrentTheme());
        setContentView(R.layout.activity_main);
        // 初始化views
        initView();
        // 设置toolbar
        setSupportActionBar(mToolbar);
        // 设置数据
        setUpViewPager(DATAS);

        mTheme = new Theme().bgColor(mToolbar, R.attr.colorPrimary)
                .textColor(mToolbar, R.attr.default_text_color)
                .add(new Item(mToolbar, ItemType.text, R.attr.default_title_name) {
                    @Override
                    public void onThemeChange(TypedArray typedArray, int index) {
                        mToolbar.setTitle(typedArray.getString(index));
                    }
                })
                .bgColor(mTabLayout, R.attr.colorPrimary)
                .add(new Item(mTabLayout, ItemType.textColor, R.attr.default_text_color) {
                    @Override
                    public void onThemeChange(TypedArray typedArray, int index) {
                        int color = typedArray.getColor(index, Color.WHITE);
                        ((TabLayout) view).setTabTextColors(color, color);
                    }
                })
                .add(new Item(mTabLayout, ItemType.bgColor, R.attr.colorAccent) {
                    @Override
                    public void onThemeChange(TypedArray typedArray, int index) {
                        mTabLayout.setSelectedTabIndicatorColor(typedArray.getColor(index, Color.BLACK));
                    }
                });

        findViewById(R.id.switch_theme).setOnClickListener(this);
    }

    /**
     * 切换主题
     */
    private void switchTheme() {
        mTheme.switchTheme(this);
    }

    private void initView() {
        mToolbar = (Toolbar) findViewById(toolbar);
        mTabLayout = (TabLayout) findViewById(R.id.tabs);
        mViewPager = (ViewPager) findViewById(viewpager);
    }

    /**
     * 初始化ViewPager对象
     */
    private void setUpViewPager(String[] datas) {
        adapter = new TabFragmentAdapter(getSupportFragmentManager());
        int i = 0;
        for (String item : datas) {
            BaseFragment f = BaseFragment.newInstance(item);
            adapter.addFragment(f, "TAB " + (++i));
        }
        mViewPager.setAdapter(adapter);

        // 以填充的形式展示TAB
        mTabLayout.setTabMode(TabLayout.MODE_FIXED);
        //
        mTabLayout.setupWithViewPager(mViewPager);
    }

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.switch_theme) {
            switchTheme();
        }
    }
}
