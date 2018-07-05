package com.example.lizzi.gakki;

import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.litepal.crud.DataSupport;

public class UpdateActivity extends AppCompatActivity {
    private Gson mGson = new Gson();
    private EditText uedttitle;
    private EditText uedtcontent;
    private Journal journal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        TextView utimeview = (TextView) findViewById(R.id.uedit_journal_time);
        TextView ulable = (TextView) findViewById(R.id.ulable);
        //TextView ulable2 = (TextView) findViewById(R.id.ulable2);
        uedttitle = (EditText) findViewById(R.id.uedit_journal_title);
        uedtcontent = (EditText) findViewById(R.id.uedit_journal_content);

        Intent intent = getIntent();
        String JournalId =  intent.getStringExtra("JournalId");
        Log.d("JournalId", "JournalId:"+JournalId);
        journal =  DataSupport.where("id = ?",JournalId).findFirst(Journal.class);

        uedttitle.setText(journal.getTitle());
        uedtcontent.setText(journal.getContent());
      //  final String updatesrc = journal.getJname();

        utimeview.setText(journal.getDate());
        AssetManager mgr=getAssets();//得到AssetManager
        Typeface tf= Typeface.createFromAsset(mgr, "font/car.ttf");//根据路径得到Typeface
        utimeview.setTypeface(tf);//设置字体
        utimeview.setTextSize(25);
        ulable.setTypeface(tf);
        ulable.setTextSize(25);
      //  ulable2.setTypeface(tf);
      //  ulable2.setTextSize(25);
        FloatingActionButton fab_save = (FloatingActionButton)findViewById(R.id.ufab_save);
        fab_save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

              //  new Thread(new Runnable() {
                 //   @Override
                //    public void run() {
               //         try{
                             String title =  uedttitle.getText().toString().trim();
                             String content = uedtcontent.getText().toString().trim();
              //  Journal journal_update = new Journal();
                journal.setTitle(title);
                journal.setContent(content);
                journal.save();
               // update_book.updateAll("id = ?", book_id + "");                         //根据id更新数据库信息
                Toast.makeText(UpdateActivity.this, "更新成功", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(UpdateActivity.this,MainActivity.class);
               // intent.putExtra("Journalupdate",(Journal)msg.obj);
                startActivity(intent);
                        //    journal.setJname(updatesrc);
                         //   journal.setTitle(title);
                        //    journal.setContent(content);
                           // String save = mGson.toJson(journal);
                          //  PrintWriter printWriter = new PrintWriter(new BufferedWriter(
                          //          new OutputStreamWriter(ClientThread.socket.getOutputStream(),"UTF-8")),true);
                            //   Log.d("run", journal.getTitle());
                            //   Log.d("run", journal.getContent());
                        //    printWriter.println(FileUtil.UPDATESAVEJOURNAL);
                        //    printWriter.println(save);
                        //    printWriter.flush();
                            // socket.close();
                            // Log.d("123", "run"+sc);
                          //  runOnUiThread(new Runnable() {
                         //       @Override
                         //       public void run() {
                    //    Toast.makeText(UpdateActivity.this,"修改成功", Toast.LENGTH_SHORT).show();
                       //         }
                      //      });
                    //    }catch (Exception e){
                    //        e.printStackTrace();

                   //     }
              //      }
             //   }).start();
            }
        });
    }
}
