package nhl.hd.tv.login;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;


import moxy.MvpAppCompatActivity;
import moxy.presenter.InjectPresenter;
import nhl.hd.tv.R;
import nhl.hd.tv.menu.MenuActivity;


public class LoginActivity extends MvpAppCompatActivity implements LoginView {

    private EditText username;
    private EditText password;
    private Button login;

    private LinearLayout progress;

    private SharedPreferences sPref;

    @InjectPresenter
    LoginPresenter mLoginPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().hide();
        sPref = getPreferences(MODE_PRIVATE);

        Intent intent = getIntent();
        String auth = intent.getStringExtra("auth");

        if (auth!=null&&auth.equals("false")){
            SharedPreferences.Editor ed = sPref.edit();
            ed.putBoolean("auth", false);
            ed.commit();
        }


        System.out.println("login="+sPref.getBoolean("auth", false));

        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        username = (EditText) findViewById(R.id.editTextUsername);
        password = (EditText) findViewById(R.id.editTextPassword);
        login = (Button) findViewById(R.id.buttonLogin);
        progress = (LinearLayout) findViewById(R.id.process);
        TextView forgotPassw = (TextView) findViewById(R.id.textViewForgot);

        progress.setVisibility(View.GONE);
        login.setVisibility(View.VISIBLE);

        login.setOnClickListener(listenerLogin);
        forgotPassw.setOnClickListener(listenerForgot);
    }



    private View.OnClickListener listenerLogin = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            mLoginPresenter.authorization(username.getText().toString(), password.getText().toString());
        }
    };

    private View.OnClickListener listenerForgot = new View.OnClickListener() {
        @Override
        public void onClick(View v) {

        }
    };

    @Override
    public void showLoading(boolean status) {
        if (status){
            progress.setVisibility(View.VISIBLE);
            login.setVisibility(View.GONE);
        }else{
            progress.setVisibility(View.GONE);
            login.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void showErrorExpired(String error) {
        Toast.makeText(this, error, Toast.LENGTH_LONG).show();
        showLoading(false);
    }

    @Override
    public void showError(int error) {
        Toast.makeText(this, "Error: "+getString(error), Toast.LENGTH_SHORT).show();
        showLoading(false);
    }

    @Override
    public void showEntry(String username, String password) {
        System.out.println("showEntry");
        SharedPreferences sharedPreferences = getApplicationContext().getSharedPreferences("nhl-saved", MODE_PRIVATE);
        SharedPreferences.Editor ed = sharedPreferences.edit();
        ed.putString("aaaaaa", "test");
        ed.putString("bbbbbb", "pakistan");
        ed.putBoolean("auth", true);
        ed.apply();
        startActivity(new Intent(this, MenuActivity.class));
        finish();

    }
}
