package com.example.boby;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Color;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.os.Bundle;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;
import com.example.boby.fragment.FragmentCar;
import com.example.boby.fragment.FragmentHomepage;
import com.example.boby.navigationbar.NavigationTabBar;
import com.example.boby.widght.BaseActivity;

import java.util.ArrayList;
@SuppressLint("ResourceAsColor")
public class MainActivity extends BaseActivity {
    private FragmentHomepage homePage;
    private FragmentCar fragmentCar;
    private ArrayList<Fragment> fgList;
    private  Context context;
    private NavigationView navigationView;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initFragmentpager();
        context = this;
        initDrawer();
        initUI();
    }

    private void initFragmentpager() {
        fgList = new ArrayList<Fragment>();
        homePage = new FragmentHomepage();
        fragmentCar = new FragmentCar();
        fgList.add(homePage);
        fgList.add(fragmentCar);
    }

    @Override
    public View onCreateView(String name, Context context, AttributeSet attrs) {
        return super.onCreateView(name, context, attrs);
    }

    /**
     * 初始化抽屉
     */
    private void initDrawer() {
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            View headerLayout = navigationView.inflateHeaderView(R.layout.nav_header_main);
            RelativeLayout headerBackground = (RelativeLayout) headerLayout.findViewById(R.id.header_background);
            drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        }
    }

    private void initUI() {
//        final NoScrollViewPager viewPager = (NoScrollViewPager) findViewById(R.id.vp_horizontal_ntb);
//        MyPagerAdapter myPagerAdapter = new MyPagerAdapter(getSupportFragmentManager(), fgList);
//        viewPager.setAdapter(myPagerAdapter);

        final String[] colors = getResources().getStringArray(R.array.default_preview);

        final NavigationTabBar navigationTabBar = (NavigationTabBar) findViewById(R.id.ntb_horizontal);
        navigationTabBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "被点击了" + v.getId(), Toast.LENGTH_SHORT).show();
            }
        });
        navigationTabBar.setOnTabBarSelectedIndexListener(new NavigationTabBar.OnTabBarSelectedIndexListener() {
            @Override
            public void onStartTabSelected(NavigationTabBar.Model model, int index) {
                Log.i("====", "开始被选择" + index);
                if (index == 2) drawer.openDrawer(navigationView);
            }

            @Override
            public void onEndTabSelected(NavigationTabBar.Model model, int index) {
                Log.i("====", "结束被选择" + index);
            }
        });
        final ArrayList<NavigationTabBar.Model> models = new ArrayList<>();
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.homenormal),
                        Color.parseColor(colors[0]))
                        .selectedIcon(getResources().getDrawable(R.drawable.homeselect))
                        .title("IYO")
                        .build()
        );
        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.sort_press),
                        Color.parseColor(colors[1]))
                        //.selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title("月子中心")
                        .build()
        );

        models.add(
                new NavigationTabBar.Model.Builder(
                        getResources().getDrawable(R.drawable.my_press),
                        Color.parseColor(colors[4]))
                        //.selectedIcon(getResources().getDrawable(R.drawable.ic_eighth))
                        .title("我的")
                        .build()
        );
        navigationTabBar.setModels(models);
//        navigationTabBar.setViewPager(viewPager, 0);
        navigationTabBar.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(final int position, final float positionOffset, final int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(final int position) {
                navigationTabBar.getModels().get(position).hideBadge();
            }

            @Override
            public void onPageScrollStateChanged(final int state) {

            }
        });

        navigationTabBar.postDelayed(new Runnable() {
            @Override
            public void run() {
                for (int i = 0; i < navigationTabBar.getModels().size(); i++) {
                    final NavigationTabBar.Model model = navigationTabBar.getModels().get(i);
                    navigationTabBar.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            model.showBadge();
                        }
                    }, i * 100);
                }
            }
        }, 500);
    }
}
