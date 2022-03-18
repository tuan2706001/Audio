package com.example.myaudio;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
TextView txtTitle, txtTimeSong, txtTimeTotal;
SeekBar skSong;
ImageButton btnPrev;
    ImageButton btnNext;
    ImageButton btnPlay;
    ImageButton btnStop;
    ArrayList<Song> arraySong;
    int position = 0;
    MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnhXa();
        AddSong();
        KhoiTaoMediaPlayer();
        mediaPlayer = MediaPlayer.create(MainActivity.this,arraySong.get(position).getFile());
        txtTitle.setText(arraySong.get(position).getTitle());

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position++;
                if(position > arraySong.size() - 1){
                    position = 0;
                }
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                KhoiTaoMediaPlayer();
                mediaPlayer.start();
                btnPlay.setImageResource(R.drawable.pause);
                setTimeTotal();
                UpdateTimeSong();
            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                position--;
                if(position < 0){
                    position = arraySong.size() - 1;
                }
                if(mediaPlayer.isPlaying()){
                    mediaPlayer.stop();
                }
                KhoiTaoMediaPlayer();
                mediaPlayer.start();
                btnPlay.setImageResource(R.drawable.pause);
                setTimeTotal();
                UpdateTimeSong();
            }
        });

        btnStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mediaPlayer.stop();
                btnPlay.setImageResource(R.drawable.play);
                KhoiTaoMediaPlayer();
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        if(mediaPlayer.isPlaying()){
                            mediaPlayer.pause();
                            btnPlay.setImageResource(R.drawable.play);
                        }else{
                            mediaPlayer.start();
                            btnPlay.setImageResource(R.drawable.pause);
                        }
                        setTimeTotal();
                        UpdateTimeSong();
                    }
                });

        skSong.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                mediaPlayer.seekTo(skSong.getProgress());
            }
        });
    }

    private void UpdateTimeSong(){
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
               SimpleDateFormat dang = new SimpleDateFormat("mm:ss");
               txtTimeSong.setText(dang.format(mediaPlayer.getCurrentPosition()));
               //update progress
                skSong.setProgress(mediaPlayer.getCurrentPosition());
                // check timeSong ->next->cham ve dau 00
                mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                    @Override
                    public void onCompletion(MediaPlayer mp) {
                        position++;
                        if(position > arraySong.size() - 1){
                            position = 0;
                        }
                        if(mediaPlayer.isPlaying()){
                            mediaPlayer.stop();
                        }
                        KhoiTaoMediaPlayer();
                        mediaPlayer.start();
                        btnPlay.setImageResource(R.drawable.pause);
                        setTimeTotal();
                        UpdateTimeSong();
                    }
                });
               handler.postDelayed(this, 500);
            }
        },100);
    }

    private void setTimeTotal(){
        SimpleDateFormat dang = new SimpleDateFormat("mm:ss");
        txtTimeTotal.setText(dang.format(mediaPlayer.getDuration()));
        // get max skSong = mediaPlayer.getDuration
        skSong.setMax(mediaPlayer.getDuration());
    }

    private void KhoiTaoMediaPlayer(){
        mediaPlayer = MediaPlayer.create(MainActivity.this,arraySong.get(position).getFile());
        txtTitle.setText(arraySong.get(position).getTitle());
    }

    private void AddSong(){
        arraySong = new ArrayList<>();
        arraySong.add(new Song("JustWannaBeWithU",R.raw.chimuondenbenemthatgan));
        arraySong.add(new Song("FirstDay",R.raw.ngaydautien));
    }

    private void  AnhXa(){
        txtTimeSong  = (TextView) findViewById(R.id.txtTimeSong);
        txtTimeTotal = (TextView) findViewById(R.id.txtTimeTotal);
        txtTitle     = (TextView) findViewById(R.id.txtTitle);
        skSong       = (SeekBar) findViewById(R.id.skSong);
        btnNext      = (ImageButton) findViewById(R.id.btnNext);
        btnPrev      = (ImageButton) findViewById(R.id.btnPrev);
        btnPlay      = (ImageButton) findViewById(R.id.btnPlay);
        btnStop      = (ImageButton) findViewById(R.id.btnStop);
    }
}