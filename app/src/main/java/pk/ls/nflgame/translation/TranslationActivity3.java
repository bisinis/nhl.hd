package pk.ls.nflgame.translation;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.tedcoder.wkvideoplayer.view.MediaController;
import com.android.tedcoder.wkvideoplayer.view.SuperVideoPlayer;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;
import pk.ls.nflgame.R;
import pk.ls.nflgame.adapters.LiveChannelsTranslationAdapter;
import pk.ls.nflgame.adapters.ReplaysTranslationAdapter;
import pk.ls.nflgame.helper.LoopDatabase;
import pk.ls.nflgame.models.LiveChannel;
import pk.ls.nflgame.models.Replay;


public class TranslationActivity3 extends MvpAppCompatActivity implements TranslationView {

    private RecyclerView tweetsRecyclerView;

    private SuperVideoPlayer mSuperVideoPlayer1;
    private SuperVideoPlayer mSuperVideoPlayer2;
    private SuperVideoPlayer mSuperVideoPlayer3;
    private SuperVideoPlayer mSuperVideoPlayer4;

    private List<String> options = new ArrayList<>();

    private LinearLayout layoutPlayer1;
    private LinearLayout layoutPlayer2;
    private LinearLayout layoutPlayer3;
    private LinearLayout layoutPlayer4;

    private LinearLayout layoutTop;
    Thread thread = null;

    private LoopDatabase database;

    @InjectPresenter
    TranslationPresenter mTranslationPresenter;

    private int countTranslation = 0;
    private String nameTitle;

    private LinearLayout layoutM;
    private LinearLayout layoutSub1;
    private LinearLayout layoutSub2;

    private int clickTabsLiveChannels = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_translation3);
        View decorView = getWindow().getDecorView();
// Hide the status bar.
        int uiOptions = View.SYSTEM_UI_FLAG_FULLSCREEN;
        decorView.setSystemUiVisibility(uiOptions);
        getSupportActionBar().hide();

        layoutTop = (LinearLayout)findViewById(R.id.topLayout);

        layoutM = ((LinearLayout)findViewById(R.id.mainL));
        layoutSub1 = ((LinearLayout)findViewById(R.id.sub1));
        layoutSub2 = ((LinearLayout)findViewById(R.id.sub2));

        initPlayer();

        tweetsRecyclerView = (RecyclerView)findViewById(R.id.recyclerView);
        Intent intent = getIntent();
        String link = intent.getStringExtra("link");
        ArrayList<String> links= intent.getStringArrayListExtra("links");

        nameTitle = intent.getStringExtra("titleName");
        ((TextView)findViewById(R.id.textViewPlayGame)).setText(nameTitle);

        mTranslationPresenter.setLink(link,links);
        mTranslationPresenter.showList(nameTitle);

        ((TextView)findViewById(R.id.textViewExit)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFullScreen) {
                    isFullScreen = false;
                    tweetsRecyclerView.setVisibility(View.VISIBLE);
                    layoutTop.setVisibility(View.VISIBLE);
                    ((TextView) findViewById(R.id.textViewFull)).setVisibility(View.VISIBLE);
                    return;
                }
                mTranslationPresenter.back();
            }
        });

        database = new LoopDatabase(mTranslationPresenter, getPreferences(MODE_PRIVATE).getString("username", ""),
                getPreferences(MODE_PRIVATE).getString("password", ""));
        database.execute();

        ((TextView) findViewById(R.id.textViewFull)).setOnClickListener(fullScreen);

        SensorManager sm = (SensorManager)getSystemService(SENSOR_SERVICE);
        sm.registerListener(m_sensorEventListener, sm.getDefaultSensor(Sensor.TYPE_ROTATION_VECTOR), SensorManager.SENSOR_DELAY_NORMAL);
        changeOrient();

        thread = new Thread(runnable);
        thread.start();
    }

    private SuperVideoPlayer.VideoPlayCallbackImpl mVideoPlayCallback = new SuperVideoPlayer.VideoPlayCallbackImpl() {

        @Override
        public void onCloseVideo() {
            resetPageToPortrait();
        }

        @Override
        public void onSwitchPageType() {
//            if (MediaController.PageType.SHRINK==mSuperVideoPlayer1.getPageType()){
//                if (nameTitle.equals("Live Games")){
//
//                }else if(nameTitle.equals("Live Channels")||nameTitle.equals("Replays")){
//
//                }
//            }else{
//                if (nameTitle.equals("Live Games")){
//
//                }else if(nameTitle.equals("Live Channels")||nameTitle.equals("Replays")){
//
//                }
//            }
        }

        @Override
        public void onPlayFinish() {
            System.out.println("onPlayFinish");
        }
    };
    private SuperVideoPlayer.VideoPlayCallbackImpl mVideoPlayCallback2 = new SuperVideoPlayer.VideoPlayCallbackImpl() {

        @Override
        public void onCloseVideo() {
            resetPageToPortrait();
        }

        @Override
        public void onSwitchPageType() {
        }

        @Override
        public void onPlayFinish() {
            System.out.println("onPlayFinish");
        }
    };
    private SuperVideoPlayer.VideoPlayCallbackImpl mVideoPlayCallback3 = new SuperVideoPlayer.VideoPlayCallbackImpl() {

        @Override
        public void onCloseVideo() {
            resetPageToPortrait();
        }

        @Override
        public void onSwitchPageType() {
        }

        @Override
        public void onPlayFinish() {
            System.out.println("onPlayFinish");
        }
    };
    private SuperVideoPlayer.VideoPlayCallbackImpl mVideoPlayCallback4 = new SuperVideoPlayer.VideoPlayCallbackImpl() {

        @Override
        public void onCloseVideo() {
            resetPageToPortrait();
        }

        @Override
        public void onSwitchPageType() {
        }

        @Override
        public void onPlayFinish() {
            System.out.println("onPlayFinish");
        }
    };

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        changeOrient();
    }

    private void changeOrient(){
        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_LANDSCAPE) {
            System.out.println("ORIENTATION_LANDSCAPE");
            layoutM.setOrientation(LinearLayout.HORIZONTAL);
            layoutSub1.setOrientation(LinearLayout.VERTICAL);
            layoutSub2.setOrientation(LinearLayout.VERTICAL);
            clickTabsLiveChannels = 4;
        } else if (orientation == Configuration.ORIENTATION_PORTRAIT){
            System.out.println("ORIENTATION_PORTRAIT");
            clickTabsLiveChannels = 2;
            layoutM.setOrientation(LinearLayout.VERTICAL);
            layoutSub1.setOrientation(LinearLayout.HORIZONTAL);
            layoutSub2.setOrientation(LinearLayout.HORIZONTAL);
        }
    }

    private void initPlayer(){
        layoutPlayer1 = (LinearLayout)findViewById(R.id.layout1);
        layoutPlayer2 = (LinearLayout)findViewById(R.id.layout2);
        layoutPlayer3 = (LinearLayout)findViewById(R.id.layout3);
        layoutPlayer4 = (LinearLayout)findViewById(R.id.layout4);

        mSuperVideoPlayer1 = (SuperVideoPlayer) findViewById(R.id.video_player_item_1);
        mSuperVideoPlayer1.setVideoPlayCallback(mVideoPlayCallback);

        mSuperVideoPlayer2 = (SuperVideoPlayer) findViewById(R.id.video_player_item_2);
        mSuperVideoPlayer2.setVideoPlayCallback(mVideoPlayCallback2);

        mSuperVideoPlayer3 = (SuperVideoPlayer) findViewById(R.id.video_player_item_3);
        mSuperVideoPlayer3.setVideoPlayCallback(mVideoPlayCallback3);

        mSuperVideoPlayer4 = (SuperVideoPlayer) findViewById(R.id.video_player_item_4);
        mSuperVideoPlayer4.setVideoPlayCallback(mVideoPlayCallback4);

        mSuperVideoPlayer1.setAutoHideController(true);
        mSuperVideoPlayer2.setAutoHideController(true);
        mSuperVideoPlayer3.setAutoHideController(true);
        mSuperVideoPlayer4.setAutoHideController(true);
    }

    private boolean isFullScreen = false;

    private View.OnClickListener fullScreen = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            if (tweetsRecyclerView.getVisibility()==View.VISIBLE){
                isFullScreen = true;
                tweetsRecyclerView.setVisibility(View.GONE);
                layoutTop.setVisibility(View.GONE);
                ((TextView) findViewById(R.id.textViewFull)).setVisibility(View.GONE);
            }else{
                isFullScreen = false;
                tweetsRecyclerView.setVisibility(View.VISIBLE);
                layoutTop.setVisibility(View.VISIBLE);
                ((TextView) findViewById(R.id.textViewFull)).setVisibility(View.VISIBLE);
            }
        }
    };


    private void resetPageToPortrait() {
        System.out.println("reset "+ getRequestedOrientation());
        if (getRequestedOrientation() == ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE) {
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            mSuperVideoPlayer1.setPageType(MediaController.PageType.SHRINK);
            mSuperVideoPlayer2.setPageType(MediaController.PageType.SHRINK);
            mSuperVideoPlayer3.setPageType(MediaController.PageType.SHRINK);
            mSuperVideoPlayer4.setPageType(MediaController.PageType.SHRINK);
        }
    }


    @Override
    protected void onPause() {
        if (database != null) database.cancel(true);
        super.onPause();
    }

    @Override
    public void onBackPressed() {
//        if (nameTitle.equals("Live Channels")||nameTitle.equals("Replays")){
//            if (tweetsRecyclerView.getVisibility()==View.GONE){
//                tweetsRecyclerView.setVisibility(View.VISIBLE);
//                layoutTop.setVisibility(View.VISIBLE);
//            }else{
//                super.onBackPressed();
//            }
//        }else{\
        if (isFullScreen) {
            isFullScreen = false;
            tweetsRecyclerView.setVisibility(View.VISIBLE);
            layoutTop.setVisibility(View.VISIBLE);
            ((TextView) findViewById(R.id.textViewFull)).setVisibility(View.VISIBLE);
            return;
        }
        SharedPreferences.Editor ed = getPreferences(MODE_PRIVATE).edit();
        ed.putString("page", nameTitle);
        ed.apply();

        mTranslationPresenter.back();

//            super.onBackPressed();
//        }
    }



    @Override
    public void showTranslations(List<String> links) {
        if (links.size()==1){
            ((LinearLayout)findViewById(R.id.sub2)).setVisibility(View.GONE);
        }else{
            ((LinearLayout)findViewById(R.id.sub2)).setVisibility(View.VISIBLE);
        }
        tweetsRecyclerView.setVisibility(View.GONE);
        layoutTop.setVisibility(View.VISIBLE);
        ((TextView) findViewById(R.id.textViewFull)).setVisibility(View.GONE);

        layoutPlayer1.setVisibility(View.GONE);
        layoutPlayer2.setVisibility(View.GONE);
        layoutPlayer3.setVisibility(View.GONE);
        layoutPlayer4.setVisibility(View.GONE);

        countTranslation = links.size();
        for(int i=0;i<links.size();i++){
            switch (i){
                case 0:
                    layoutPlayer1.setVisibility(View.VISIBLE);
                    mSuperVideoPlayer1.loadAndPlay(Uri.parse(links.get(0)),0);
                    break;
                case 1:
                    layoutPlayer2.setVisibility(View.VISIBLE);
                    mSuperVideoPlayer2.loadAndPlay(Uri.parse(links.get(1)),0);
                    break;
                case 2:
                    layoutPlayer3.setVisibility(View.VISIBLE);
                    layoutPlayer4.setVisibility(View.INVISIBLE);
                    mSuperVideoPlayer3.loadAndPlay(Uri.parse(links.get(2)),0);
                    break;
                case 3:
                    layoutPlayer4.setVisibility(View.VISIBLE);
                    mSuperVideoPlayer4.loadAndPlay(Uri.parse(links.get(3)),0);
                    break;
            }
        }
    }

    @Override
    public void deleteTranslations(int position) {

    }

    @Override
    public void addTranslation(String url){
        ((LinearLayout)findViewById(R.id.sub2)).setVisibility(View.VISIBLE);
        if (countTranslation<4){
            switch (countTranslation){
                case 0:
                    layoutPlayer1.setVisibility(View.VISIBLE);
                    countTranslation=1;
                    break;
                case 1:
                    layoutPlayer2.setVisibility(View.VISIBLE);
                    countTranslation=2;
                    break;
                case 2:
                    layoutPlayer3.setVisibility(View.VISIBLE);
                    countTranslation=3;
                    break;
                case 3:
                    layoutPlayer4.setVisibility(View.VISIBLE);
                    countTranslation=4;
                    break;
            }
        }
    }


    @Override
    public void showError(int error) {
        Toast.makeText(this, getString(error), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void back() {
        finish();
    }

    SensorEventListener m_sensorEventListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent event) {
//            if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
//                if (nameTitle.equals("Live Channels")||nameTitle.equals("Replays")){
//                    tweetsRecyclerView.setVisibility(View.VISIBLE);
//                }
//            }
//            else {
//                tweetsRecyclerView.setVisibility(View.GONE);
//                layoutTop.setVisibility(View.GONE);
//            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {}
    };

    @Override
    public void showLiveChannels(List<LiveChannel> list) {
        tweetsRecyclerView.setVisibility(View.VISIBLE);
        layoutTop.setVisibility(View.VISIBLE);
        ((TextView) findViewById(R.id.textViewFull)).setVisibility(View.VISIBLE);
        LiveChannelsTranslationAdapter liveChannelsTranslationAdapter = new LiveChannelsTranslationAdapter(list, getApplicationContext(), mTranslationPresenter);
        tweetsRecyclerView.setLayoutManager(new GridLayoutManager(this, clickTabsLiveChannels));
        tweetsRecyclerView.setAdapter(liveChannelsTranslationAdapter);
    }

    @Override
    public void showReplays(List<Replay> list) {
        tweetsRecyclerView.setVisibility(View.VISIBLE);
        layoutTop.setVisibility(View.VISIBLE);
        ((TextView) findViewById(R.id.textViewFull)).setVisibility(View.VISIBLE);
        ReplaysTranslationAdapter liveChannelsTranslationAdapter = new ReplaysTranslationAdapter(list, getApplicationContext(), mTranslationPresenter);
        tweetsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        tweetsRecyclerView.setAdapter(liveChannelsTranslationAdapter);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode != KeyEvent.KEYCODE_BACK) {
            layoutTop.setVisibility(View.VISIBLE);
            if (nameTitle.equals("Live Channels")||nameTitle.equals("Replays")){
//                if (tweetsRecyclerView.getVisibility()==View.GONE){
//                    tweetsRecyclerView.setVisibility(View.VISIBLE);
//                }
            }
        }
        if (thread!=null||thread.isAlive()){
            try{
                thread.interrupt();
            }
            catch (Exception e){

            }
        }
        thread = new Thread(runnable);
        thread.start();

        return super.onKeyDown(keyCode, event);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        layoutTop.setVisibility(View.VISIBLE);
        if (nameTitle.equals("Live Channels")||nameTitle.equals("Replays")){
//            if (tweetsRecyclerView.getVisibility()==View.GONE){
//                tweetsRecyclerView.setVisibility(View.VISIBLE);
//            }
        }
        if (thread!=null||thread.isAlive()){
            try{
                thread.interrupt();
            }
            catch (Exception e){

            }
        }
        thread = new Thread(runnable);
        thread.start();
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        layoutTop.setVisibility(View.VISIBLE);
        if (nameTitle.equals("Live Channels")||nameTitle.equals("Replays")){
//            if (tweetsRecyclerView.getVisibility()==View.GONE){
//                tweetsRecyclerView.setVisibility(View.VISIBLE);
//            }
        }
        if (thread!=null||thread.isAlive()){
            try{
                thread.interrupt();
            }
            catch (Exception e){

            }
        }
        thread = new Thread(runnable);
        thread.start();
        return super.onTouchEvent(event);
    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            layoutTop.setVisibility(View.GONE);
//            tweetsRecyclerView.setVisibility(View.GONE);
        }
    };

    Runnable runnable = new Runnable() {
        public void run() {
            try {
                TimeUnit.SECONDS.sleep(3);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        handler.handleMessage(null);
                    }
                });
            } catch (InterruptedException e) {
//                e.printStackTrace();
            }
        }
    };
}


