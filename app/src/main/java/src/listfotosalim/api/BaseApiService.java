package src.listfotosalim.api;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import src.listfotosalim.model.ResponsePhoto;

public interface BaseApiService {

    @GET("photos")
    Call<List<ResponsePhoto>> getListFoto();
}
