package com.example.lizzi.gakki;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

public class LoginActivity extends AppCompatActivity {
EditText username ;
    EditText useremail;
    private SharedPreferences pref;
    private CheckBox rememberPass;
    private SharedPreferences.Editor editor;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView lable2 = (TextView) findViewById(R.id.lablelogin2);
        TextView lable1 = (TextView) findViewById(R.id.lablelogin1);
        TextView remember_info = (TextView)findViewById(R.id.remember_info);
        username = (EditText) findViewById(R.id.login_username);
        useremail = (EditText) findViewById(R.id.login_email);
        pref = PreferenceManager.getDefaultSharedPreferences(this);
        rememberPass = (CheckBox)findViewById(R.id.remember_pass);

        AssetManager mgr=getAssets();//得到AssetManager
        Typeface tf= Typeface.createFromAsset(mgr, "font/car.ttf");//根据路径得到Typeface
        lable1.setTypeface(tf);//设置字体
        lable1.setTextSize(30);
        lable2.setTypeface(tf);
        lable2.setTextSize(30);
        remember_info.setTypeface(tf);
        remember_info.setTextSize(30);

        boolean isRemember = pref.getBoolean("remember_info",false);
        if (isRemember){
            String account = pref.getString("usernames","");
            String password = pref.getString("useremails","");
            username.setText(account);
            useremail.setText(password);
            rememberPass.setChecked(true);
        }

    }

    public void Login(View v){
        String usernames = username.getText().toString().trim();
        String useremails = useremail.getText().toString().trim();
        editor = pref.edit();
        if (rememberPass.isChecked()){
            editor.putBoolean("remember_info",true);
            editor.putString("usernames",usernames);
            editor.putString("useremails",useremails);
        }else {
            editor.clear();
        }
        editor.apply();

        Intent intent = new Intent(LoginActivity.this,MainActivity.class);
        intent.putExtra("usernames",usernames);
        intent.putExtra("useremails",useremails);



        startActivity(intent);
        finish();
    }


}
