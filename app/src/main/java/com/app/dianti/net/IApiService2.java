package com.app.dianti.net;

import com.app.dianti.net.entity.KResponse;
import com.app.dianti.refactor.net.entity.ElevatorList;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @user wangji
 * @date 16/78/24
 * @time 16:37
 */
public interface IApiService2 {

    @POST("http://121.40.89.242:80/Home/MEle/add_pic")
    Call<String> uploadImage(@Body RequestBody body);

    @FormUrlEncoded
    @POST("http://121.40.89.242:80/Home/MEle/eleFile")
    Call<String> getElevatorList(
            @Field("token") String token,
            @Field("code") String code,
            @Field("ele_name") String ele_name,
            @Field("use_dep_name") String use_dep_name,
            @Field("maintain_dep_name") String maintain_dep_name,
            @Field("pageNum") String page);

    @FormUrlEncoded
    @POST("http://121.40.89.242:80/Home/MEle/eleFile")
    Call<KResponse<ElevatorList>> getElevatorList(
            @Field("token") String token,
            @Field("pageNum") int page,
            @Field("code") String code,
            @Field("ele_name") String ele_name,
            @Field("use_dep_name") String use_dep_name,
            @Field("maintain_dep_name") String maintain_dep_name);

}
