package com.example.boby.activity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.example.boby.MainActivity;
import com.example.boby.R;
import com.example.boby.bean.MyUser;
import com.example.boby.component.EditTextWithDel;
import com.example.boby.component.PaperButton;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.util.List;

import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.UpdateListener;

@ContentView(R.layout.activity_user_name)
public class UserNameActivity extends AppCompatActivity {
    @ViewInject(R.id.openbt)
    PaperButton openbt;
    @ViewInject(R.id.user)
    EditTextWithDel username;
    String phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        x.view().inject(this);
        initView();
    }

    private void initView() {
        SharedPreferences userinfo = getSharedPreferences("userinfo", 0);
        phone = userinfo.getString("username", null);
        openbt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final String name = username.getText().toString();
                BmobQuery<MyUser> query = new BmobQuery<MyUser>();
                query.addWhereEqualTo("mobilePhoneNumber", phone);
                query.findObjects(getApplicationContext(), new FindListener<MyUser>() {
                    @Override
                    public void onSuccess(List<MyUser> object) {
                        MyUser myUser = object.get(0);
                        myUser.setUsername(name);
                        myUser.update(getApplicationContext(), new UpdateListener() {
                            @Override
                            public void onSuccess() {

                            }

                            @Override
                            public void onFailure(int i, String s) {

                            }
                        });
                    }

                    @Override
                    public void onError(int i, String s) {

                    }
                });
                startActivity(new Intent(getApplicationContext(), MainActivity.class));
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onStop() {
        super.onStop();
    }

}
