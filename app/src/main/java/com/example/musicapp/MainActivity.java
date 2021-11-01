package com.example.musicapp;

import androidx.appcompat.app.AppCompatActivity;

import android.animation.Animator;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.SeekBar;
import android.widget.TextView;

import com.example.musicapp.Model.MusicModel;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ImageView imgPre;
    private ImageView imgPlay;
    private ImageView imgNext;
    private TextView tvName;
    private SeekBar mSeekBar;
    private TextView tvTimeStart;
    private TextView tvTimeEnd;
    private List<MusicModel> musicList;
    private int position;
    private MediaPlayer mediaPlayer;
    private ImageView imageView;
    private Animation animation;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        imageView = (ImageView) findViewById(R.id.imageView);
        imgPre = (ImageView) findViewById(R.id.imgPre);
        imgPlay = (ImageView) findViewById(R.id.imgPlay);
        imgNext = (ImageView) findViewById(R.id.imgNext);
        tvName = (TextView) findViewById(R.id.tvName);
        mSeekBar = (SeekBar) findViewById(R.id.seekBar);
        tvTimeStart = (TextView) findViewById(R.id.tvTimeStart);
        tvTimeEnd = (TextView) findViewById(R.id.tvTimeEnd);
        position = 0;
        animation = AnimationUtils.loadAnimation(MainActivity.this,R.anim.run_music);
        addList();
        khoiTaoMediaPlayer();
        mSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int i, boolean b) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(mSeekBar.getProgress());
            }
        });
        imgPre.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.release();
                }
                position --;
                if (position<0){
                    position = musicList.size()-1;
                }
                khoiTaoMediaPlayer();
                mediaPlayer.start();
                setTime();
            }
        });
        imgNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (mediaPlayer.isPlaying()){
                    mediaPlayer.release();
                }
                position ++;
                if (position > musicList.size()-1){
                    position = 0;
                }
                khoiTaoMediaPlayer();
                mediaPlayer.start();
                setTime();
            }
        });

        imgPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mediaPlayer.isPlaying()){
                    mediaPlayer.pause();
                    imgPlay.setImageResource(R.drawable.ic_baseline_play_arrow_24);
                    imageView.clearAnimation();

                }else {
                    mediaPlayer.start();
                    imgPlay.setImageResource(R.drawable.ic_pause);
                    imageView.setAnimation(animation);
                }

                setTime();
                setStartTime();
            }
        });

    }
    void addList(){
        musicList = new ArrayList<>();
        musicList.add(new MusicModel("Perfect - Ed Sheeran",R.raw.perfect));
        musicList.add(new MusicModel("#3107 - Nâu ft Dương",R.raw.nau_3107));
        musicList.add(new MusicModel("Anh đếch cần gì nhiều ngoài em - Đen Vâu ft Thành Đồng ft Vũ",R.raw.adcgnne));
    }
    void khoiTaoMediaPlayer(){
        mediaPlayer = MediaPlayer.create(MainActivity.this,musicList.get(position).getLink());
        tvName.setText(musicList.get(position).getTitle());
    }
    void setTime(){
        SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
        String time = (sdf.format(mediaPlayer.getDuration()));
        tvTimeEnd.setText(time);
        mSeekBar.setMax(mediaPlayer.getDuration());
    }
    void setStartTime(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                SimpleDateFormat sdf = new SimpleDateFormat("mm:ss");
                tvTimeStart.setText(sdf.format(mediaPlayer.getCurrentPosition()));
                mSeekBar.setProgress(mediaPlayer.getCurrentPosition());

                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        if (mediaPlayer.isPlaying()){
                            mediaPlayer.release();
                        }
                        position ++;
                        if (position > musicList.size()-1){
                            position = 0;
                        }
                        khoiTaoMediaPlayer();
                        mediaPlayer.start();
                        setTime();
                    }
                });

                handler.postDelayed(this::run,500);
            }
        },1000);
    }
}