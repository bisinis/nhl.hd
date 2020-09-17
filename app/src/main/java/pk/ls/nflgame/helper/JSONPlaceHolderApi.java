package pk.ls.nflgame.helper;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;
import pk.ls.nflgame.models.FullData;

public interface JSONPlaceHolderApi {
    @GET("api/api.php")
    Call<FullData> getUpdateDatabase(@Query("u")String username, @Query("p")String password);
}
