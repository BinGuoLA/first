package com.example.lizzi.gakki;

import android.content.Context;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import org.litepal.crud.DataSupport;

import java.util.List;

import static android.content.ContentValues.TAG;

/**
 * Created by LiZzi on 2018/6/27.
 */

public class JournalAdapter extends RecyclerView.Adapter<JournalAdapter.ViewHolder>{
   /* interface Listener {
        void itemClicked(int position);
    };*/
    private Context mContext;

    private Gson mGson = new Gson();
   // private Listener listener;
    // SimpleDateFormat sdf = new SimpleDateFormat("yyyy年-MM月-dd号");

    private List<Journal> mJournalList;

   // public static int[] images = {R.drawable.ban3,R.drawable.ban3,
  //          R.drawable.ban3,R.drawable.ban3,R.drawable.ban1,R.drawable.ban1
 //   };

    static class ViewHolder extends RecyclerView.ViewHolder{
        CardView cardView;
       // ImageView journalbg;
        //View JournalView;
        ImageView circleImag;
        ImageView btn_edit;
        ImageView collectImag;
        TextView journaltitle;
        TextView journaldate;
        TextView positionText;

        public ViewHolder(View itemView) {
            super(itemView);
            cardView =(CardView)itemView;
         //   cardView = (CardView)itemView;
          //  journalbg = (ImageView)itemView.findViewById(R.id.journal_bg);
            journaltitle = (TextView)itemView.findViewById(R.id.main_tv_title);
            journaldate = (TextView)itemView.findViewById(R.id.journal_date);
            btn_edit = (ImageView) itemView.findViewById(R.id.main_iv_edit);
            circleImag = (ImageView) itemView.findViewById(R.id.main_iv_circle);
            collectImag = (ImageView)itemView.findViewById(R.id.main_iv_collect);
            positionText =(TextView) itemView.findViewById(R.id.position_text);

        }
    }
    public JournalAdapter(List<Journal> journalList){
        mJournalList = journalList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if (mContext == null){
            mContext = parent.getContext();
           // listener = (Listener)parent.getContext();
        }

        View view = LayoutInflater.from(mContext).inflate(R.layout.item_journal,parent,false);
        final ViewHolder holder = new ViewHolder(view);


        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 int position = holder.getAdapterPosition();

                 int id = mJournalList.get(position).getId();
                Journal journal =  DataSupport.where("id = ?",id+" ").findFirst(Journal.class);
                 Intent intent = new Intent(mContext,JournalActivity.class);
                //  intent.putExtra(JournalActivity.JOURNAL_TITLE,(Journal)msg.obj);
                //  intent.putExtra(JournalActivity.JOURNAL_CONTENT,fruit.getImageId());
                 intent.putExtra("JournalQueryId",journal.getId()+"");
                mContext.startActivity(intent);
            //    new Thread(new Runnable() {
                //    @Override
                 //   public void run() {
                    //    String journalName = journal.getJname();
                        // Log.d("aaa", "run: "+journalName);
                  //      PrintWriter printWriter = null;
                  //      try {
                   //         printWriter = new PrintWriter(new BufferedWriter(
                  //                  new OutputStreamWriter(ClientThread.socket.getOutputStream(),"UTF-8")),true);
                           // printWriter = new PrintWriter(ClientThread.socket.getOutputStream());
                    //    } catch (IOException e) {
                    //        e.printStackTrace();
                   //     }

                        // Log.d("run", socket+toString());

                     //   printWriter.println(FileUtil.QUERYCONTENT);
                    //    printWriter.println(journalName);
                   //     printWriter.flush();
                //    }
           //     }).start();
              //  Intent intent = new Intent(mContext,FruitActivity.class);
              //  intent.putExtra(FruitActivity.FRUIT_NAME,fruit.getName());
              //  intent.putExtra(FruitActivity.FRUIT_IMAGE_ID,fruit.getImageId());
              //   mContext.startActivity(intent);
            }

        });
        holder.btn_edit.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();

                int id = mJournalList.get(position).getId();
                Journal journal =  DataSupport.where("id = ?",id+" ").findFirst(Journal.class);
                Intent intent = new Intent(mContext,UpdateActivity.class);
                //  intent.putExtra(JournalActivity.JOURNAL_TITLE,(Journal)msg.obj);
                //  intent.putExtra(JournalActivity.JOURNAL_CONTENT,fruit.getImageId());
                intent.putExtra("JournalId",journal.getId()+"");
                mContext.startActivity(intent);
            //   Log.d("itemClieked", "itemClieked:"+journal.getJname());
              //  new Thread(new Runnable() {
              //      @Override
             //       public void run() {
                       // String journalName = journal.getJname();
                       // String date = journal.getDate();
                       //  Log.d("aaa", "edit "+journalName);
                   //     PrintWriter printWriter = null;
                  //      try {
                  //          printWriter = new PrintWriter(new BufferedWriter(
                  //                  new OutputStreamWriter(ClientThread.socket.getOutputStream(),"UTF-8")),true);
                            // printWriter = new PrintWriter(ClientThread.socket.getOutputStream());
                  //      } catch (IOException e) {
                   //         e.printStackTrace();
                   //     }

                   //     // Log.d("run", socket+toString());
                  //      String updatequery = mGson.toJson(journal);
                  //      printWriter.println(FileUtil.UPDATEQUERYJOURNAL);
                  //      printWriter.println(updatequery);

                   //     printWriter.flush();
             //       }
            //    }).start();
            }
       });

        holder.collectImag.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int position = holder.getAdapterPosition();
                final  Journal journal = mJournalList.get(position);
                Log.d(TAG, "collect:"+journal.getIs_collect());
                if (journal.getIs_collect() == 0){  //未收藏
                    journal.setIs_collect(1);
                    journal.save();
                    holder.collectImag.setImageResource(R.drawable.collect_ed);
                    Toast.makeText(mContext,"已收藏~", Toast.LENGTH_SHORT).show();
                }else if (journal.getIs_collect() == 1){
                    journal.setIs_collect(0);
                    journal.save();
                    holder.collectImag.setImageResource(R.drawable.collect);
                    Toast.makeText(mContext,"取消收藏~", Toast.LENGTH_SHORT).show();
                }
            }
        });

        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {


        Journal journal = mJournalList.get(position);
        holder.journaltitle.setText(journal.getTitle());
//        Glide.with(mContext).load(getbg()).into(holder.journalbg);
        holder.journaldate.setText(journal.getDate());
       // holder.circleImag.setImageResource(R.drawable.circle_orange);
      //  holder.positionText.setText(journal.getPosition());

      //  Log.d(TAG, "onBindViewHolder: "+journal.getPosition());
        if (journal.getIs_collect() == 1){
            holder.collectImag.setImageResource(R.drawable.collect_ed);
        }

        String dateSystem = FileUtil.getDate();

        /**
         * 如果该日记是当天写的，则将日期左边的圆圈设置成橙色的
         */

        if(mJournalList.get(position).getDate().substring(0, 13).equals(dateSystem)){
            holder.circleImag.setImageResource(R.drawable.circle_orange);
        }

        AssetManager mgr=mContext.getAssets();//得到AssetManager
        Typeface tf= Typeface.createFromAsset(mgr, "font/car.ttf");//根据路径得到Typeface
        holder.journaltitle.setTypeface(tf);//设置字体
        holder.journaltitle.setTextSize(20);
        holder.journaldate.setTypeface(tf);
        holder.journaldate.setTextSize(20);
    }

   /* private int getbg(){
        Random random = new Random();
        int rand = random.nextInt(6);
        return images[rand];
    }*/

    @Override
    public int getItemCount() {
        return mJournalList.size();
    }


}
