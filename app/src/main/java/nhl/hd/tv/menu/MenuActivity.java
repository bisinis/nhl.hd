package nhl.hd.tv.menu;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;



import java.util.ArrayList;
import java.util.List;


import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;
import nhl.hd.tv.R;
import nhl.hd.tv.adapters.LiveChannelsAdapter;
import nhl.hd.tv.adapters.LiveGamesAdapter;
import nhl.hd.tv.adapters.ReplaysAdapter;
import nhl.hd.tv.helper.LoopDatabase;
import nhl.hd.tv.models.LiveChannel;
import nhl.hd.tv.models.LiveGame;
import nhl.hd.tv.models.Replay;
import nhl.hd.tv.translation.TranslationActivity3;


public class MenuActivity extends MvpAppCompatActivity implements MenuView {

    private RecyclerView tweetsRecyclerView;

    private LiveChannelsAdapter liveChannelsAdapter;
    private LiveGamesAdapter liveGamesAdapter;
    private ReplaysAdapter replaysAdapter;

    private Button liveGames;
    private Button showChannels;
    private Button replays;
    private Button addGrid;

    private EditText search;

    private String titleName = "Live Games";

    private int clickTabsLiveChannels = 2;
    private SharedPreferences sPref;

    @InjectPresenter
    MenuPresenter mMenuPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);
        sPref = getPreferences(MODE_PRIVATE);
        getSupportActionBar().hide();

        ((ImageView) findViewById(R.id.imageViewUser)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backToLogin();
            }
        });

        tweetsRecyclerView = findViewById(R.id.recyclerView);
        tweetsRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        liveGames = ((Button)findViewById(R.id.buttonLiveGames));
        showChannels = ((Button)findViewById(R.id.buttonLiveChannels));
        replays = ((Button)findViewById(R.id.buttonReplays));

        addGrid = (Button)findViewById(R.id.buttonAddToGrid);

        liveGames.setOnClickListener(buttonClick);
        showChannels.setOnClickListener(buttonClick);
        replays.setOnClickListener(buttonClick);

        search= (EditText)findViewById(R.id.editTextSearch);

        search.addTextChangedListener(tt);

        addGrid.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mMenuPresenter.showTranslations();
            }
        });

        setPage();

        changeOrient();
    }
    private void setPage(){
        addGrid.setVisibility(View.GONE);
        titleName = getPreferences(MODE_PRIVATE).getString("page", null);
        liveGames.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.bacground));
        showChannels.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.bacground));
        replays.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.bacground));

        if (titleName==null||titleName.equals("")){
            addGrid.setVisibility(View.VISIBLE);
            titleName = "Live Games";
            mMenuPresenter.clickTabs(1);
            liveGames.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_turn_on_tab));
        }else {
            switch (titleName){
                case "Live Channels":
                    mMenuPresenter.clickTabs(0);
                    showChannels.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_turn_on_tab));
                    break;
                case "Live Games":
                    addGrid.setVisibility(View.VISIBLE);
                    mMenuPresenter.clickTabs(1);
                    liveGames.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_turn_on_tab));
                    break;
                default:
                    mMenuPresenter.clickTabs(2);
                    replays.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_turn_on_tab));
            }
        }
    }

    private void changeOrient(){
        int orientation = this.getResources().getConfiguration().orientation;
        if (orientation == Configuration.ORIENTATION_PORTRAIT) {
            System.out.println("ORIENTATION_PORTRAIT");
            clickTabsLiveChannels = 2;
        } else if (orientation == Configuration.ORIENTATION_LANDSCAPE){
            System.out.println("ORIENTATION_LANDSCAPE");
            clickTabsLiveChannels = 4;
        }
        setPage();
    }



    TextWatcher tt = new TextWatcher() {
        public void afterTextChanged(Editable s){
            search.setSelection(s.length());
        }
        public void beforeTextChanged(CharSequence s,int start,int count, int after){}
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            if (liveChannelsAdapter != null){
                liveChannelsAdapter.getFilter().filter(search.getText().toString());
            }

            if (liveGamesAdapter != null){
                liveGamesAdapter.getFilter().filter(search.getText().toString());
            }

            if (replaysAdapter != null){
                replaysAdapter.getFilter().filter(search.getText().toString());
            }

        }
    };

    private View.OnClickListener buttonClick = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            liveGames.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.bacground));
            showChannels.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.bacground));
            replays.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.bacground));
            mMenuPresenter.resetLinks();
            addGrid.setVisibility(View.GONE);
            switch (v.getId()){
                case R.id.buttonLiveChannels:
                    mMenuPresenter.clickTabs(0);
                    showChannels.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_turn_on_tab));
                    titleName = "Live Channels";
                    break;
                case R.id.buttonLiveGames:
                    mMenuPresenter.clickTabs(1);
                    liveGames.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_turn_on_tab));
                    addGrid.setVisibility(View.VISIBLE);
                    titleName = "Live Games";
                    break;
                case R.id.buttonReplays:
                    mMenuPresenter.clickTabs(2);
                    replays.setBackgroundColor(ContextCompat.getColor(getApplicationContext(), R.color.color_turn_on_tab));
                    titleName = "Replays";
                    break;
            }
            SharedPreferences.Editor ed = getPreferences(MODE_PRIVATE).edit();
            ed.putString("page", titleName);
            ed.commit();
        }
    };

    @Override
    public void showLiveChannels(List<LiveChannel> liveChannels) {
        tweetsRecyclerView.setLayoutManager(new GridLayoutManager(this, clickTabsLiveChannels));
        liveChannelsAdapter = new LiveChannelsAdapter(liveChannels, this, mMenuPresenter);
        tweetsRecyclerView.setAdapter(liveChannelsAdapter);
    }

    @Override
    public void showLiveGames(List<LiveGame> livegames) {
        tweetsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        liveGamesAdapter = new LiveGamesAdapter(mMenuPresenter.updateGames(livegames), this, mMenuPresenter);
        tweetsRecyclerView.setAdapter(liveGamesAdapter);
    }

    @Override
    public void showReplays(List<Replay> replays) {
        tweetsRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        replaysAdapter = new ReplaysAdapter(replays, this, mMenuPresenter);
        replaysAdapter.notifyDataSetChanged();
        tweetsRecyclerView.setAdapter(replaysAdapter);
    }

    @Override
    public void showTranslations(ArrayList<String> links) {
        Intent intent = new Intent(getBaseContext(), TranslationActivity3.class);
        intent.putStringArrayListExtra("links", links);
        intent.putExtra("titleName", titleName);
        startActivity(intent);
    }
    @Override
    public void showTranslation(String link) {
        Intent intent = new Intent(getBaseContext(), TranslationActivity3.class);
        intent.putExtra("link", link);
        intent.putExtra("titleName", titleName);
        startActivity(intent);
    }

    @Override
    public void showError(int error) {
        Toast.makeText(this, getString(error), Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showErrorExpired(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void backToLogin() {
//        SharedPreferences.Editor ed = sPref.edit();
//        ed.putBoolean("auth", false);
//        ed.commit();
//        Intent intent = new Intent(this, LoginActivity.class);
//        intent.putExtra("auth", "false");
//        startActivity(intent);
//        finish();
    }

    private LoopDatabase database;

    @Override
    protected void onResume() {
        if (database==null||database.isCancelled()){
            SharedPreferences sharedPreferences = getSharedPreferences("nhl-saved", MODE_PRIVATE);
            String username = sharedPreferences.getString("aaaaaa", "");
            String password = sharedPreferences.getString("bbbbbb", "");
            database = new LoopDatabase(mMenuPresenter, username, password);
            database.execute();
        }
        super.onResume();
    }

    @Override
    protected void onPause() {
        if (database != null) database.cancel(true);
        super.onPause();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        changeOrient();
    }

    @Override
    public void onBackPressed() {
        SharedPreferences.Editor ed = getPreferences(MODE_PRIVATE).edit();
        ed.putString("page", "");
        ed.commit();
        super.onBackPressed();
    }
}
