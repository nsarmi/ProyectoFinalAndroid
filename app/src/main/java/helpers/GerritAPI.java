package helpers;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface GerritAPI {

    @POST("home/Test/")
    Call<List<Change>> loadChanges();
}