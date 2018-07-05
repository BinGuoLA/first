package com.example.lizzi.gakki;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.v4.app.NotificationCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v4.widget.SimpleCursorAdapter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SearchView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import org.litepal.crud.DataSupport;
import org.litepal.tablemanager.Connector;

import java.util.List;

import static com.example.lizzi.gakki.R.id.nav_view;


public class MainActivity extends AppCompatActivity {
    private DrawerLayout mDrawerLayout;

    private List<Journal> JournalList;
    private JournalAdapter journalAdapter;
    private RecyclerView recyclerView;
    private Handler handler;
    private SwipeRefreshLayout swipeRefreshLayout;
    private SearchView msearchView;
    private ActionBar actionBar;
    private NavigationView navView;
    private boolean is_collect_page = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer_layout);



        navView = (NavigationView) findViewById(nav_view);
        actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setDisplayHomeAsUpEnabled(true);               //设置左上角的menu
            actionBar.setHomeAsUpIndicator(R.drawable.menu);

        }





        Connector.getDatabase();
        JournalList = DataSupport.findAll(Journal.class);

        recyclerView = (RecyclerView)findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        journalAdapter = new JournalAdapter(JournalList);
        recyclerView.setAdapter(journalAdapter);

        Intent intent = getIntent();
        View headerView = navView.getHeaderView(0);
        TextView textusermail = (TextView) headerView.findViewById(R.id.mail);
        TextView textusername= (TextView) headerView.findViewById(R.id.username);

        // Log.d("user", "onCreate: "+intent.getStringExtra("usernames"));
        // Log.d("user", "onCreate: "+intent.getStringExtra("useremails"));

        String usermail = intent.getStringExtra("useremails");
        final String username = intent.getStringExtra("usernames");

        textusermail.setText(usermail);
        textusername.setText(username);




        Intent addintent = new Intent(MainActivity.this,AddActivity.class);
        PendingIntent pi = PendingIntent.getActivity(MainActivity.this,0,addintent,0);
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        Notification notification = new NotificationCompat.Builder(MainActivity.this)
                .setContentTitle("亲爱的"+username+",欢迎回来!")
                .setContentText("你一共写了"+JournalList.size()+"篇日记,点击开始写日记吧!")
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.journal)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.bg))
                .setContentIntent(pi)
                .setAutoCancel(true)
                .setDefaults(NotificationCompat.DEFAULT_ALL)
                .setPriority(NotificationCompat.PRIORITY_MAX)
                .build();
        notificationManager.notify(1,notification);




        navView.setCheckedItem(R.id.journal_list);
        navView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.journal_edit:
                        Intent intent = new Intent(MainActivity.this,AddActivity.class);
                        startActivity(intent);
                        is_collect_page = false;
                        break;
                    case R.id.journal_list:
                        /*JournalList.clear();
                        JournalList.addAll(DataSupport.findAll(Journal.class));
                        journalAdapter.notifyDataSetChanged();*/
                        JournalList.clear();
                        JournalList.addAll(DataSupport.findAll(Journal.class));
                        journalAdapter = new JournalAdapter(JournalList);
                        recyclerView.setAdapter(journalAdapter);
                        is_collect_page = false;
                        mDrawerLayout.closeDrawers();
                        return true;
                    case R.id.journal_collect:
                        JournalList.clear();
                        JournalList.addAll(DataSupport.where("is_collect = ?","1").find(Journal.class));
                        journalAdapter.notifyDataSetChanged();
                        mDrawerLayout.closeDrawers();
                        is_collect_page = true;
                       /* Intent intentcollect = new Intent(MainActivity.this,MainActivity.class);
                        startActivity(intentcollect);*/
                        return true;
                }
                return true;
            }
        });

        final FloatingActionButton fab = (FloatingActionButton)findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,AddActivity.class);
                startActivity(intent);
            }//
        });

        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipe_refresh);
        swipeRefreshLayout.setColorSchemeResources(R.color.colorPrimary);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {

                refreshFruits();
            }
        });

     /*   handler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if(msg.what == 123){   //保存成功
                    Intent intent = new Intent(MainActivity.this,MainActivity.class);
                    startActivity(intent);

                }else if (msg.what == 456){    //查看日记内容
                   *//* Intent intent = new Intent(MainActivity.this,JournalContent.class);
                    intent.putExtra("Journal",(Journal)msg.obj);
                    startActivity(intent);*//*

                    Intent intent = new Intent(MainActivity.this,JournalActivity.class);
                    //  intent.putExtra(JournalActivity.JOURNAL_TITLE,(Journal)msg.obj);
                    //  intent.putExtra(JournalActivity.JOURNAL_CONTENT,fruit.getImageId());
                    intent.putExtra("Journalquery",(Journal)msg.obj);
                    startActivity(intent);
                }else if (msg.what == 789){       //删除日记
                    //   Intent intent = new Intent(MainActivity.this,QueryJournal.class);
                    // intent.putExtra("DeleteJName",(String)msg.obj);
                    //     Log.d("handleMessage: jnale", "handleMessage: jnale"+(String)msg.obj);
                    DataSupport.deleteAll(Journal.class,"jname = ?",(String)msg.obj);
                    //  startActivity(intent);
                    Intent intent = new Intent(MainActivity.this,MainActivity.class);
                    startActivity(intent);

                }else if (msg.what == 147){           //更新日记
                    Intent intent = new Intent(MainActivity.this,UpdateActivity.class);
                    intent.putExtra("Journalupdate",(Journal)msg.obj);
                    startActivity(intent);
                }else if (msg.what == 159){             //保存更新日记
                    Journal journal = (Journal) msg.obj;
                    journal.updateAll("jname = ?", journal.getJname());
                    // journalAdapter.notifyDataSetChanged();
                    Intent intent = new Intent(MainActivity.this,MainActivity.class);
                    startActivity(intent);
                }
            }
        };*/






       /* new Thread(new Runnable() {
            @Override
            public void run() {
                ClientThread clientThread = new ClientThread(handler);
            }
        }).start(); //开启线程 socket连接*/





    }

    public boolean onOptionsItemSelected(MenuItem item){
        switch (item.getItemId()){
            case android.R.id.home:
                mDrawerLayout.openDrawer(GravityCompat.START);
                break;
            default:
        }
        return true;
    }

    private void refreshFruits() {
        /*new Thread(new Runnable() {
            @Override
            public void run() {

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {*/
                       /* JournalList.clear();
                        JournalList.addAll(DataSupport.findAll(Journal.class));
                        journalAdapter.notifyDataSetChanged();
                        swipeRefreshLayout.setRefreshing(false);
                         navView.setCheckedItem(R.id.journal_list);*/
        if (is_collect_page) {
            JournalList.clear();
            JournalList.addAll(DataSupport.where("is_collect = ?", "1").find(Journal.class));
            journalAdapter = new JournalAdapter(JournalList);
            recyclerView.setAdapter(journalAdapter);
            swipeRefreshLayout.setRefreshing(false);
            navView.setCheckedItem(R.id.journal_collect);
        } else {
            JournalList.clear();
            JournalList.addAll(DataSupport.findAll(Journal.class));
            journalAdapter = new JournalAdapter(JournalList);

            recyclerView.setAdapter(journalAdapter);
            swipeRefreshLayout.setRefreshing(false);
            navView.setCheckedItem(R.id.journal_list);
          /*          }
                });
            }
        }).start();*/
        }
    }
    protected void onResume() {
        super.onResume();
        // Log.d("onResume", "onResume: 执行");
        JournalList.clear();
        JournalList.addAll(DataSupport.findAll(Journal.class));
        journalAdapter = new JournalAdapter(JournalList);
        recyclerView.setAdapter(journalAdapter);

     /*   new Thread(new Runnable() {
            @Override
            public void run() {
                ClientThread clientThread = new ClientThread(handler);
            }
        }).start();*/
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_search_view, menu);

        //找到searchView
        MenuItem searchItem = menu.findItem(R.id.action_search);
        msearchView = (SearchView) MenuItemCompat.getActionView(searchItem);
        // searchView.setIconified(false);//设置searchView处于展开状态
        // searchView.onActionViewExpanded();// 当展开无输入内容的时候，没有关闭的图标
        // searchView.setIconifiedByDefault(false);//默认为true在框内，设置false则在框外
        //设置触发查询的最少字符数（默认2个字符才会触发查询）
        SearchView.SearchAutoComplete mSearchAutoComplete = (SearchView.SearchAutoComplete) msearchView.findViewById(R.id.search_src_text);
        mSearchAutoComplete.setThreshold(1);
        msearchView.setQueryHint("输入日记标题");


        msearchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                //提交按钮的点击事件
                // Toast.makeText(MainActivity.this, query, Toast.LENGTH_SHORT).show();

                JournalList.clear();
                JournalList.addAll(DataSupport.where("title like ?", "%" + query + "%").find(Journal.class));
                journalAdapter.notifyDataSetChanged();
                return true;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Cursor cursor = TextUtils.isEmpty(newText) ? null : queryData(newText);
                // 不要频繁创建适配器，如果适配器已经存在，则只需要更新适配器中的cursor对象即可。
                Log.d("cursor", "cursor: "+cursor);
                if (msearchView.getSuggestionsAdapter() == null) {
                    msearchView.setSuggestionsAdapter(new SimpleCursorAdapter(MainActivity.this, R.layout.search_item_layout, cursor, new String[]{"_id","title"}, new int[]{R.id.search_text1,R.id.search_text1}));
                } else {
                    msearchView.getSuggestionsAdapter().changeCursor(cursor);
                }

                return false;
            }

        });
        return super.onCreateOptionsMenu(menu);
    }

    private Cursor queryData(String key) {
        Cursor cursor = null;
        cursor = DataSupport.findBySQL("select  id as _id,title from Journal where title like ?","%" + key+ "%");
        return cursor;
    }




  /*  @Override
    public void itemClicked(int position) {
      Journal journal = JournalList.get(position);
        Log.d("itemClieked", "itemClieked:"+journal.getJname());
    }*/
}
