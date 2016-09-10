package com.app.dianti.net;

import com.app.dianti.net.entity.KResponse;
import com.app.dianti.net.entity.MaintenanceAddEntity;
import com.app.dianti.net.entity.ResureListEntity;
import com.app.dianti.net.entity.UserEntity;
import com.app.dianti.refactor.net.entity.ElevatorList;
import com.app.dianti.refactor.net.entity.MaintenanceTypeList;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

/**
 * @user MycroftWong
 * @date 16/7/2
 * @time 12:17
 */
public interface IApiService {

    @FormUrlEncoded
    @POST("/app/user/auth.htm")
    Call<KResponse<UserEntity>> login(@Field("data") String data);

    @FormUrlEncoded
    @POST("/Home/MEle/rescueList")
    Call<KResponse<ResureListEntity>> getResureList(
            @Field("token") String token,
            @Field("status_class") int type,
            @Field("status") String status,
            @Field("pageNum") int pageNum);

    @POST("http://218.75.75.34:88/Home/MEle/add_pic")
    Call<String> uploadImage(@Body RequestBody body);

    @FormUrlEncoded
    @POST("/app/maintain/add.htm")
    Call<KResponse<MaintenanceAddEntity>> addMaintenance(@Field("data") String data);

    @FormUrlEncoded
    @POST("/app/patrol/add.htm")
    Call<KResponse<MaintenanceAddEntity>> addInspection(@Field("data") String data);

    @FormUrlEncoded
    @POST("/app/rescue/done.htm")
    Call<KResponse<MaintenanceAddEntity>> finishRescue(@Field("data") String data);

    @FormUrlEncoded
    @POST("/app/annual/done.htm")
    Call<KResponse<Void>> finishAnnual(@Field("data") String data);

    @FormUrlEncoded
    @POST("http://218.75.75.34:88/Home/MEle/eleFile")
    Call<String> getElevatorList(
            @Field("token") String token,
            @Field("code") String code,
            @Field("ele_name") String ele_name,
            @Field("use_dep_name") String use_dep_name,
            @Field("maintain_dep_name") String maintain_dep_name,
            @Field("pageNum") String page);

    @FormUrlEncoded
    @POST("http://218.75.75.34:88/Home/MEle/eleFile")
    Call<KResponse<ElevatorList>> getElevatorList(
            @Field("token") String token,
            @Field("pageNum") int page,
            @Field("code") String code,
            @Field("ele_name") String ele_name,
            @Field("use_dep_name") String use_dep_name,
            @Field("maintain_dep_name") String maintain_dep_name);

    @FormUrlEncoded
    @POST("/app/maintain/list/detail.htm")
    Call<KResponse<MaintenanceTypeList>> getMaintenanceTypeList(@Field("data") String data);

    @FormUrlEncoded
    @POST("/app/maintain/sign.htm")
    Call<KResponse<Void>> sign(@Field("data") String data);

    @FormUrlEncoded
    @POST("/app/maintain/done.htm")
    Call<KResponse<Void>> finishMaintenance(@Field("data") String data);

}
