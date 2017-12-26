package com.example.auser.musicinsdtest;

import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    Button btnprev,btnpause,btnstop,btnplay,btnnext;
    ListView lv;
    TextView textView;
//    String[] songname=new String[]{"A","B","C","D"};
//    String[] songfile=new String[]{"A.mp3","B.mp3","C.mp3","D.mp3"};…?
//    int[] songfile=new int[]{R.raw.A,R.raw.0941,R.raw.0942,R.raw.0943};

    String[] songname=new String[]{"001","0941","0942","0943"};
    String[] songfile=new String[]{"001.mp3","0941.mp3","0942.mp3","0943.mp3"};
    private int cListItem=0;
    private boolean flag=false;
    private final String songpath="/sdcard/";
    MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViews();
        lv.setOnItemClickListener(lisListener);
        btnpause.setOnClickListener(btnListener);
        btnprev.setOnClickListener(btnListener);
        btnplay.setOnClickListener(btnListener);
        btnstop.setOnClickListener(btnListener);
        btnnext.setOnClickListener(btnListener);

        mediaPlayer=new MediaPlayer();
        ArrayAdapter<String> adapter=new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,songname);
        lv.setAdapter(adapter);
    }
    //================================================================================
    private ListView.OnItemClickListener lisListener= new ListView.OnItemClickListener() {
        @Override
        public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
            cListItem=i;
            playsong(songpath+songfile[cListItem]);
            textView.setText(songfile[cListItem]);

        }
    };
    //================================================================================
    private Button.OnClickListener btnListener= new Button.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch ((view.getId())){
                case R.id.pause:
                    mediaPlayer.pause();
                    flag=true;
                    break;
                case R.id.play:
                    if (flag){
                        mediaPlayer.start();
                        flag=false;
                    }else{
                        playsong(songpath+songfile[cListItem]);
                        textView.setText(songfile[cListItem]);
                    }
                    break;
                case R.id.next:
                    nextsong();
                    break;
                case R.id.prev:
                    presong();
                    break;
                case R.id.stop:
                    mediaPlayer.stop();
//                    mediaPlayer.release();
                    break;


            }

        }
    };

    private void findViews(){
        btnprev=(Button)findViewById(R.id.prev);
        btnnext=(Button)findViewById(R.id.next);
        btnpause=(Button)findViewById(R.id.pause);
        btnplay=(Button)findViewById(R.id.play);
        btnstop=(Button)findViewById(R.id.stop);
        lv=(ListView)findViewById(R.id.listview);
        textView=(TextView)findViewById(R.id.textView);
    }

    private void playsong(String path){
        try{
            mediaPlayer.reset();
            mediaPlayer.setDataSource(path);
            mediaPlayer.prepare();
            mediaPlayer.start();
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    nextsong();
                }
            });
//            textView.setText(songfile[cListItem]);
        }catch(IOException e){

        }

    }

    private void nextsong(){
        cListItem++;
        if(cListItem>=lv.getCount()){
            cListItem=0;
        }
        playsong(songpath+songfile[cListItem]);
        textView.setText(songfile[cListItem]);
    }
//===================================
    private void presong(){
        cListItem--;
        if (cListItem<0){
            cListItem=lv.getCount()-1;
        }
        playsong(songpath+songfile[cListItem]);
        textView.setText(songfile[cListItem]);
    }
}
