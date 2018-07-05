package com.example.lizzi.gakki;

import android.Manifest;
import android.app.NotificationManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class AddActivity extends AppCompatActivity {
    private EditText edttitle;
    private EditText edtcontent;
   // public LocationClient mLocationClient;

    private String position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
      //  mLocationClient = new LocationClient(getApplicationContext());
      //  mLocationClient.registerLocationListener(new MyLocationListener());
        setContentView(R.layout.activity_add);

        TextView timeview = (TextView) findViewById(R.id.edit_journal_time);
        TextView lable = (TextView) findViewById(R.id.lable);
        edttitle = (EditText) findViewById(R.id.edit_journal_title);
        edtcontent = (EditText) findViewById(R.id.edit_journal_content);



        NotificationManager manager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        manager.cancel(1);

        timeview.setText(FileUtil.getDate());
        AssetManager mgr=getAssets();//得到AssetManager
        Typeface tf= Typeface.createFromAsset(mgr, "font/car.ttf");//根据路径得到Typeface
        timeview.setTypeface(tf);//设置字体
        timeview.setTextSize(30);
        lable.setTypeface(tf);
        lable.setTextSize(30);
      //  queryPermissions();
        FloatingActionButton fab_save = (FloatingActionButton)findViewById(R.id.fab_save);
        fab_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addJournal();
            }
        });




    }
   /* private void requestLocation(){
        initLocation();
        mLocationClient.start();
    }*/

   /* private void initLocation() {
        LocationClientOption option = new LocationClientOption();
        option.setIsNeedAddress(true);
        //option.setScanSpan(1000);
        mLocationClient.setLocOption(option);
    }*/

    public void queryPermissions(){
        List<String> permissionList = new ArrayList<>();
        if (ContextCompat.checkSelfPermission(AddActivity.this, Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.ACCESS_FINE_LOCATION);
        }
        if (ContextCompat.checkSelfPermission(AddActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.WRITE_EXTERNAL_STORAGE);
        }
        if (ContextCompat.checkSelfPermission(AddActivity.this, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED){
            permissionList.add(Manifest.permission.READ_PHONE_STATE);
        }
        if (!permissionList.isEmpty()){
            String[] permissions = permissionList.toArray(new String[permissionList.size()]);
            ActivityCompat.requestPermissions(AddActivity.this,permissions,1);
        }else {
         //   requestLocation();
        }
    }

    private void addJournal() {

                    final String title =  edttitle.getText().toString().trim();
                    final String content = edtcontent.getText().toString().trim();
                    Journal journal = new Journal();
                    journal.setTitle(title);
                    journal.setContent(content);
                    journal.setDate(FileUtil.getDate());
                    journal.setIs_collect(0);
                    journal.save();
                   // String save = mGson.toJson(journal);
                 //   PrintWriter printWriter = new PrintWriter(new BufferedWriter(
                 //           new OutputStreamWriter(ClientThread.socket.getOutputStream(),"UTF-8")),true);

                //    printWriter.println(FileUtil.SAVEFILE);
                //    printWriter.println(save);
               //     printWriter.flush();
                    // socket.close();
                    // Log.d("123", "run"+sc);

                  Toast.makeText(AddActivity.this,"保存成功", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(AddActivity.this,MainActivity.class);
        startActivity(intent);


    }

   /* public void onRequestPermissionsResult(int requestCode,String[] permissions,int[] grantResults){
        switch (requestCode){
            case 1:
                if (grantResults.length>0){
                    for (int result:grantResults){
                        if (result != PackageManager.PERMISSION_GRANTED){
                            Toast.makeText(this,"不同意权限就无法使用定位哦",Toast.LENGTH_SHORT).show();
                            return;
                        }
                    }
                    requestLocation();
                }else {
                    Toast.makeText(this,"发生未知的错误",Toast.LENGTH_SHORT).show();
                    finish();
                }
                break;
            default:
        }
    }*/

   /* public class MyLocationListener implements BDLocationListener {

        @Override
        public void onReceiveLocation(final BDLocation bdLocation) {
            StringBuilder currentPosition = new StringBuilder();
            currentPosition.append(bdLocation.getCity());
           // currentPosition.append(bdLocation.getDistrict());
            position = currentPosition.toString();
            Log.d("position", "onReceiveLocation: "+position);
            //  if (bdLocation.getLocType() == BDLocation.TypeGpsLocation || bdLocation.getLocType() == BDLocation.TypeNetWorkLocation){
            //   navigateTo(bdLocation);
            //  }
            *//*runOnUiThread(new Runnable() {
                @Override
                public void run() {
                  *//**//*  StringBuilder currentPsition = new StringBuilder();
                    currentPsition.append(bdLocation.getCity());
                    positionText.setText(currentPsition.toString());*//**//*
                }
            });*//*
        }
    }*/

}
