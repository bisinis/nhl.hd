package pk.ls.nflgame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;

import androidx.appcompat.app.AppCompatActivity;

import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;
import pk.ls.nflgame.helper.LoopDatabase;
import pk.ls.nflgame.login.LoginActivity;
import pk.ls.nflgame.login.LoginPresenter;
import pk.ls.nflgame.login.LoginView;
import pk.ls.nflgame.menu.MenuActivity;
import pk.ls.nflgame.menu.MenuPresenter;

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
public class SplashActivity extends MvpAppCompatActivity  implements LoginView{
    @InjectPresenter
    LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_splash);
        getSupportActionBar().hide();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                gotoNextView();
            }
        }, 3000);
    }

    private void gotoNextView() {
        SharedPreferences sharedPreferences = getSharedPreferences("nhl-saved", MODE_PRIVATE);
        if (sharedPreferences.getBoolean("auth", false)) {  // if logged in
            String username = sharedPreferences.getString("aaaaaa", "");
            String password = sharedPreferences.getString("bbbbbb", "");
            mLoginPresenter.authorization(username, password);
        } else {
            SplashActivity.this.startActivity(new Intent(SplashActivity.this, LoginActivity.class));
            SplashActivity.this.finish();
        }
    }

    @Override
    public void showLoading(boolean status) {

    }

    @Override
    public void showError(int error) {
        SplashActivity.this.startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        SplashActivity.this.finish();
    }

    @Override
    public void showEntry(String username, String password) {

        startActivity(new Intent(this, MenuActivity.class));
        finish();

    }

    @Override
    public void showErrorExpired(String error) {
        SplashActivity.this.startActivity(new Intent(SplashActivity.this, LoginActivity.class));
        SplashActivity.this.finish();
    }
}
