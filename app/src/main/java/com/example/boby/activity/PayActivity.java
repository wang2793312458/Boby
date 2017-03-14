package com.example.boby.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.boby.R;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

@ContentView(R.layout.activity_pay)
public class PayActivity extends AppCompatActivity implements View.OnClickListener {
    @ViewInject(R.id.rela_weixin)
    RelativeLayout rela_weixin;
    @ViewInject(R.id.rela_zhifubao)
    RelativeLayout rela_zhifubao;
    @ViewInject(R.id.right_weixin)
    ImageView right_weixin;
    @ViewInject(R.id.right_zhifubao)
    ImageView right_zhifubao;
    @ViewInject(R.id.payprice)
    TextView payprice;
    @ViewInject(R.id.paynum)
    TextView paynum;
    @ViewInject(R.id.payedit_price)
    TextView payedit_price;
    @ViewInject(R.id.pay_title)
    TextView pay_title;
    @ViewInject(R.id.ll_left)
    LinearLayout ll_left;
    @ViewInject(R.id.pay)
    LinearLayout pay;
    private String mPrice;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        initView();
    }

    private void initView() {
        ll_left.setOnClickListener(this);
        Intent intent = getIntent();
        String goodnum = intent.getStringExtra("goodnum");
        String title = intent.getStringExtra("title");
        mPrice = intent.getStringExtra("price");
        paynum.setText(goodnum);
        payprice.setText(mPrice);
        payedit_price.setText(mPrice);
        pay_title.setText(title);
        rela_weixin.setOnClickListener(this);
        rela_zhifubao.setOnClickListener(this);
        pay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.rela_zhifubao:
                right_weixin.setVisibility(View.INVISIBLE);
                right_zhifubao.setVisibility(View.VISIBLE);
                break;

            case R.id.rela_weixin:
                right_weixin.setVisibility(View.VISIBLE);
                right_zhifubao.setVisibility(View.INVISIBLE);
                break;
            case R.id.ll_left:
                finish();
                break;
            case R.id.pay:

                if (right_weixin.getVisibility() == View.VISIBLE) {
                    Toast.makeText(this, "微信" + mPrice, Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(this, "支付宝" + mPrice, Toast.LENGTH_SHORT).show();
                }

                break;

        }
    }
}
