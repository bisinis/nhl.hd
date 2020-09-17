package pk.ls.nflgame.login;

import android.annotation.SuppressLint;
import android.util.Log;

import moxy.InjectViewState;
import moxy.MvpPresenter;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import pk.ls.nflgame.R;
import pk.ls.nflgame.database.LocalDatabase;
import pk.ls.nflgame.helper.NetworkService;
import pk.ls.nflgame.models.FullData;


@InjectViewState
public class LoginPresenter extends MvpPresenter<LoginView> {


    @SuppressLint("StaticFieldLeak")
    public void authorization(final String username, final String password) {
        Log.d(username, password);
        getViewState().showLoading(true);
        NetworkService.getInstance()
                .getApi().getUpdateDatabase(username, password).enqueue(new Callback<FullData>() {
            @Override
            public void onResponse(Call<FullData> call, Response<FullData> response) {
                if (response.body().getStatus().equals("success")) {
                    LocalDatabase.fullData = response.body();
                    getViewState().showEntry(username, password);
                } else if (response.body().getStatus().equals("error")) {
                    getViewState().showErrorExpired(response.body().getMessage());
                } else {
                    getViewState().showError(R.string.error_auth);
                }
            }

            @Override
            public void onFailure(Call<FullData> call, Throwable t) {
                getViewState().showError(R.string.error_internet);
            }
        });

    }

    public void forgotPassword(String login) {
        //Nothing
    }
}