package com.example.lizzi.gakki;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.litepal.crud.DataSupport;

public class JournalActivity extends AppCompatActivity {
    //public static final String JOURNAL_TITLE = "journal_title";
   // public static final String JOURNAL_CONTENT = "journal_content";
    private Journal journal;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_journal);
        Intent intent = getIntent();
        String JournalQueryId =  intent.getStringExtra("JournalQueryId");
        Log.d("JournalQueryId", "JournalQueryId:"+JournalQueryId);
      //  journal = DataSupport.where("id = ?",Journalqueryid).findFirst(Journal.class);
        journal =  DataSupport.where("id = ?",JournalQueryId).findFirst(Journal.class);
        TextView journal_content_text = (TextView) findViewById(R.id.journal_content_text);

        ImageView fruitImageView = (ImageView) findViewById(R.id.journal_image_view);
        journal_content_text.setText(journal.getContent());
        CollapsingToolbarLayout collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbarLayout.setTitle(journal.getTitle());
      //  Glide.with(this).load(getbg()).into(fruitImageView);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

      //  final String src = journal.getJname();
        final int id = journal.getId();
        FloatingActionButton journal_content_edit = (FloatingActionButton)findViewById(R.id.journal_content_edit);
        journal_content_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                deleteSure(id);
            }
        });
    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void deleteSure(final int src){
        //    通过AlertDialog.Builder这个类来实例化我们的一个AlertDialog的对象
        AlertDialog.Builder builder = new AlertDialog.Builder(JournalActivity.this);

        //    设置Title的内容
        builder.setTitle("警告");
        //    设置Content来显示一个信息
        builder.setMessage("确定删除吗？");
        //    设置一个PositiveButton
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                journal.delete();
                Toast.makeText(JournalActivity.this, "删除成功 " , Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(JournalActivity.this,MainActivity.class);
                startActivity(intent);
            }
        });
        //    设置一个NegativeButton
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener()
        {
            @Override
            public void onClick(DialogInterface dialog, int which)
            {
                Toast.makeText(JournalActivity.this, "取消删除 " , Toast.LENGTH_SHORT).show();
            }
        });

        //    显示出该对话框
        builder.show();


    }



   /* private int getbg() {
        Random random = new Random();
        int rand = random.nextInt(6);
        return JournalAdapter.images[rand];
    }*/
}
