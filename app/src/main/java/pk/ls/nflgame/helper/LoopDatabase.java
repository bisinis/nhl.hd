package pk.ls.nflgame.helper;

import android.os.AsyncTask;

import java.util.concurrent.TimeUnit;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import pk.ls.nflgame.models.FullData;

public class LoopDatabase extends AsyncTask<Void, Void, Void> {
    private UpdateDatabse databse;
    private String username;
    private String password;

    public LoopDatabase(UpdateDatabse databse, String username, String password) {
        this.databse = databse;
        this.username = username;
        this.password = password;
    }

    @Override
    protected Void doInBackground(Void... voids) {
        while (true){
            NetworkService.getInstance()
                    .getApi().getUpdateDatabase(username, password).enqueue(new Callback<FullData>() {
                @Override
                public void onResponse(Call<FullData> call, Response<FullData> response) {
                    databse.updateDatabase(response.body());
                }

                @Override
                public void onFailure(Call<FullData> call, Throwable t) {

                }
            });

            try {
                TimeUnit.SECONDS.sleep(60);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }


}
