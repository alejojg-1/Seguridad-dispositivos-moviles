package co.com.personacliente2021.service.login;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface LoginClient {

    @FormUrlEncoded
    @POST("/oauth/token")
    Call<ResponseBody> login(@Header("Authorization") String credentials, @Field("username") String username, @Field("password") String password, @Field("grant_type") String grantType );

}
