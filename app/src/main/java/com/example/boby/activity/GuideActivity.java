package com.example.boby.activity;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.support.v4.view.ViewPager.PageTransformer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.example.boby.R;
import com.example.boby.adapter.GuidePagerAdapter;
import com.example.boby.utils.ScreenMetrics;

import java.util.ArrayList;
import java.util.List;

public class GuideActivity extends AppCompatActivity {

    private LinearLayout linearLayout_guide;
    private Context mContext = this;
    private ImageView[] imageViews;
    private ViewPager viewPager_guide;
    private List<LinearLayout> totalList_linearLayout = new ArrayList<LinearLayout>();
    private static final float MIN_SCALE = 0.75f;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        initView();
        initData();
        initImageView();
        initViewPager();

    }

    private void initView() {
        viewPager_guide = (ViewPager) findViewById(R.id.viewPager_guide);
        linearLayout_guide = (LinearLayout) findViewById(R.id.linearLayout_guide);

    }

    private void initData() {
        int[] images = new int[] { R.drawable.guide1, R.drawable.guide2,
                R.drawable.guide3 };
        for (int i = 0; i < 3; i++) {
            LinearLayout linearLayout = new LinearLayout(mContext);
            linearLayout.setLayoutParams(new ViewGroup.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            linearLayout.setBackgroundResource(images[i]);
            totalList_linearLayout.add(linearLayout);

        }

        ImageView imageView_enter = new ImageView(mContext);
        ViewGroup.LayoutParams layoutParams_enter = new ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        imageView_enter.setLayoutParams(layoutParams_enter);
        imageView_enter.setBackgroundResource(R.drawable.btn_guide);
        imageView_enter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, LoginActivity.class));
                finish();
                overridePendingTransition(R.anim.anim_activity_in,
                        R.anim.anim_activity_out);
            }
        });
        totalList_linearLayout.get(2).setGravity(
                Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
        totalList_linearLayout.get(2).setPadding(0, 0, 0, ScreenMetrics.getHeight(mContext,9));
        totalList_linearLayout.get(2).addView(imageView_enter);

    }

    private void initViewPager() {

        viewPager_guide
                .setAdapter(new GuidePagerAdapter(totalList_linearLayout));
        viewPager_guide.setPageTransformer(true, new PageTransformer() {

            @Override
            public void transformPage(View view, float position) {
                int pageWidth = view.getWidth();

                if (position < -1) { // [-Infinity,-1)
                    // This page is way off-screen to the left.
                    view.setAlpha(0);

                } else if (position <= 0) { // [-1,0]

                    view.setAlpha(1);
                    view.setTranslationX(0);
                    view.setScaleX(1);
                    view.setScaleY(1);

                } else if (position <= 1) { // (0,1]
                    // Fade the page out.
                    view.setAlpha(1 - position);

                    // Counteract the default slide transition
                    view.setTranslationX(pageWidth * -position);

                    // Scale the page down (between MIN_SCALE and 1)
                    float scaleFactor = MIN_SCALE + (1 - MIN_SCALE)
                            * (1 - Math.abs(position));
                    view.setScaleX(scaleFactor);
                    view.setScaleY(scaleFactor);

                } else { // (1,+Infinity]
                    // This page is way off-screen to the right.
                    view.setAlpha(0);
                }
            }

        });

        viewPager_guide.setOnPageChangeListener(new OnPageChangeListener() {

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < imageViews.length; i++) {
                    imageViews[i]
                            .setBackgroundResource(R.drawable.point_nomal);
                }
                imageViews[position]
                        .setBackgroundResource(R.drawable.point_focured_green);
            }

            @Override
            public void onPageScrolled(int arg0, float arg1, int arg2) {

            }

            @Override
            public void onPageScrollStateChanged(int arg0) {

            }
        });
    }

    private void initImageView() {
        imageViews = new ImageView[3];
        for (int i = 0; i < 3; i++) {
            ImageView imageView = new ImageView(mContext);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            params.rightMargin = 20;
            imageView.setLayoutParams(params);
            imageView.setBackgroundResource(R.drawable.point_nomal);
            imageView.setTag(i);
            imageView.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    viewPager_guide.setCurrentItem((Integer) v.getTag());
                }
            });
            imageViews[i] = imageView;
            linearLayout_guide.addView(imageView);
        }
        imageViews[0].setBackgroundResource(R.drawable.point_focured_green);
    }

}
