package com.bytedance.videoplayer;

import android.content.Context;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.View;
import android.widget.ImageView;
import android.widget.SeekBar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.bytedance.videoplayer.player.VideoPlayerIJK;
import com.bytedance.videoplayer.player.VideoPlayerListener;

import tv.danmaku.ijk.media.player.IMediaPlayer;
import tv.danmaku.ijk.media.player.IjkMediaPlayer;

import static com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions.withCrossFade;

public class MainActivity extends AppCompatActivity {

    private int seekPosition;
    private boolean seekUsing = false;

    private VideoPlayerIJK ijkPlayer;
    private ImageView imageView;
    private SeekBar seekBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        seekBar = findViewById(R.id.seekBar);
        imageView = findViewById(R.id.imageView);
        ijkPlayer = findViewById(R.id.ijkPlayer);
        initImage();
        initVideo();
        initButton();
        initSeekBar();
    }

    private void initImage() {
        final String url = "https://ss0.bdstatic.com/70cFvHSh_Q1YnxGkpoWK1HF6hhy/it/u=1160144756,2481554560&fm=26&gp=0.jpg";
        RequestOptions options = new RequestOptions().centerCrop().optionalTransform(new RoundedCorners(30));
        Glide.with(this)
                .load(url)
                .apply(options)
                .placeholder(R.drawable.icon_progress_bar)
                .error(R.drawable.icon_failure)
                .fallback(R.drawable.ic_launcher_background)
                .transition(withCrossFade(3000))
                .into(imageView);
    }

    private void initVideo() {
        final String url = "http://qthttp.apple.com.edgesuite.net/1010qwoeiuryfg/sl.m3u8";
        ijkPlayer.setVisibility(View.VISIBLE);
        try {
            IjkMediaPlayer.loadLibrariesOnce(null);
            IjkMediaPlayer.native_profileBegin("libijkplayer.so");
        } catch (Exception e) {
            this.finish();
        }
        ijkPlayer.setListener(new VideoPlayerListener());
        ijkPlayer.setVideoPath(url);
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    if (!seekUsing) {
                        float percent = (float) ijkPlayer.getCurrentPosition() / (float) ijkPlayer.getDuration();
                        seekBar.setProgress((int) (percent * seekBar.getMax()));
                        Log.d("seekbar", "percent = " + percent);
                    }
                    try {
                        Thread.sleep(200);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }

    private void initButton() {
        findViewById(R.id.buttonPlay).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ijkPlayer.start();
            }
        });

        findViewById(R.id.buttonPause).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ijkPlayer.pause();
            }
        });
    }

    private void initSeekBar() {
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                seekPosition = progress;
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
                seekUsing = true;
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                float percent = (float) seekPosition / (float) seekBar.getMax();
                ijkPlayer.seekTo((long) (ijkPlayer.getDuration() * percent));
                seekUsing = false;
            }
        });
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (ijkPlayer.isPlaying()) {
            ijkPlayer.stop();
        }
        IjkMediaPlayer.native_profileEnd();
    }
}
