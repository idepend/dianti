package com.app.dianti.net;

import android.content.Context;
import android.support.annotation.NonNull;

import com.alibaba.fastjson.JSON;
import com.app.dianti.common.AppContext;
import com.app.dianti.net.entity.AnnualData;
import com.app.dianti.net.entity.InspectionAddData;
import com.app.dianti.net.entity.KResponse;
import com.app.dianti.net.entity.LoginUserEntity;
import com.app.dianti.net.entity.MaintenanceAddData;
import com.app.dianti.net.entity.MaintenanceAddEntity;
import com.app.dianti.net.entity.MaintenanceFinishData;
import com.app.dianti.net.entity.MaintenanceSignData;
import com.app.dianti.net.entity.MaintenanceTypeData;
import com.app.dianti.net.entity.RescueAddData;
import com.app.dianti.net.entity.ResureListEntity;
import com.app.dianti.net.entity.UploadImageEntity;
import com.app.dianti.net.entity.UserEntity;
import com.app.dianti.refactor.net.entity.ElevatorList;
import com.app.dianti.refactor.net.entity.MaintenanceTypeList;
import com.app.dianti.util.Logs;
import com.app.dianti.util.UserPreference;
import com.google.gson.Gson;

import java.io.File;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * 此类是调用汉传的接口
 * @user MycroftWong
 * @date 16/7/2
 * @time 12:23
 */
public final class NetService2 {

    private static NetService2 sNetService;
    private final IApiService mService;
    private final IApiService2 mService2;
    private final OkHttpClient mHttpClient;

    public static NetService2 getInstance(Context context) {
        if (sNetService == null) {
            synchronized (NetService2.class) {
                if (sNetService == null) {
                    sNetService = new NetService2(context.getApplicationContext());
                }
            }
        }
        return sNetService;
    }

    private final Context mAppContext;

    private NetService2(@NonNull Context appContext) {
        mAppContext = appContext;

        mHttpClient = new OkHttpClient.Builder().build();
        final Retrofit retrofit = new Retrofit.Builder()
                .client(mHttpClient)
                .baseUrl(AppContext.SERVER_URL_2)
                .addConverterFactory(StringConverterFactory.create())
                .addConverterFactory(GsonConverterFactory.create())
                .validateEagerly(true)
                .build();

        mService = retrofit.create(IApiService.class);
        mService2 = retrofit.create(IApiService2.class);
    }

    private static final Gson GSON = new Gson();

    public OkHttpClient getHttpClient() {
        return mHttpClient;
    }

    public boolean getHttp(){
        boolean isHttp;
        String localServerAddress2 = UserPreference.get("serverAddress2", "");
        if (localServerAddress2.equals("http://218.75.75.34:88")||localServerAddress2.equals("")) {
            //tip("服务器地址配置不正确");
//            AppContext.SERVER_URL_2 = "http://218.75.75.34:88";
            isHttp=false;
        } else {
            isHttp=true;
//            AppContext.SERVER_URL_2 = localServerAddress2;
        }
        return isHttp;
    }

    /**
     * 登陆接口
     *
     * @param username
     * @param pwd
     * @param listener
     */
    public void login(String username, String pwd, OnResponseListener<UserEntity> listener) {
        final Call<KResponse<UserEntity>> call = mService.login(GSON.toJson(new LoginUserEntity(username, pwd)));
        handleCall(call, listener);
    }

    /**
     * 获取应急列表
     *
     * @param token
     * @param type
     * @param status
     * @param pageNum
     * @param listener
     */
    public void getResureList(String token, int type, String status, int pageNum, OnResponseListener<ResureListEntity> listener) {
        final Call<KResponse<ResureListEntity>> call = mService.getResureList(token, type, status, pageNum);
        handleCall(call, listener);
    }

    /**
     * 增加维保单子
     *
     * @param token
     * @param type
     * @param qrcode
     * @param listener
     */
    public void addMaintenance(String token, int type, String qrcode, OnResponseListener<MaintenanceAddEntity> listener) {
        final MaintenanceAddData data = new MaintenanceAddData(token, type, System.currentTimeMillis() / 1000, qrcode);
        final Call<KResponse<MaintenanceAddEntity>> call = mService.addMaintenance(JSON.toJSONString(data));
        handleCall(call, listener);
    }

    public void addInspection(String token, String pic, String desc, String checkList, String qrcode, OnResponseListener<MaintenanceAddEntity> listener) {
        final InspectionAddData data = new InspectionAddData(token, pic, desc, checkList, qrcode);

        final String result = JSON.toJSONString(data);
        Logs.e(result);

        final Call<KResponse<MaintenanceAddEntity>> call = mService.addInspection(result);
        handleCall(call, listener);
    }

    public void finishRescue(String id, String token, String eleCode, int tirPeople, int rescuedPeople, int injuries, int isSuccess, long startTime, long endTime, String reason, String pic, final OnResponseListener<MaintenanceAddEntity> listener) {
        final RescueAddData data = new RescueAddData(id, token, eleCode, tirPeople, rescuedPeople, injuries, isSuccess, startTime, endTime, reason, pic);

        final String result = JSON.toJSONString(data);
        Logs.e(result);

        final Call<KResponse<MaintenanceAddEntity>> call = mService.finishRescue(result);
        handleCall(call, listener);
    }

    public void finishAnnual(String id, String token, String imageUrl, final OnResponseListener<Void> listener) {
        final AnnualData data = new AnnualData(token, id, imageUrl);

        final String result = JSON.toJSONString(data);
        Logs.e(result);

        final Call<KResponse<Void>> call = mService.finishAnnual(result);
        handleCall(call, listener);
    }

    public void getElevatorList(
            String token, String code, String ele_name,
            String use_dep_name, String maintain_dep_name,
            String page, final OnResponseListener<String> listener) {

        final Call<String> call = mService.getElevatorList(token, code, ele_name, use_dep_name, maintain_dep_name, page);
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    handleSuccess(response.body(), listener);
                } else {
                    handleError("网络错误,请重试!", listener);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                handleError("网络错误,请重试!", listener);
            }
        });
    }

    public void getElevatorList(
            String token,
            int page,
            String code,
            String ele_name,
            String use_dep_name,
            String maintain_dep_name,
            final OnResponseListener<ElevatorList> listener) {
        final Call<KResponse<ElevatorList>> call = mService.getElevatorList(token, page, code, ele_name, use_dep_name, maintain_dep_name);
        handleCall(call, listener);
    }

    public void getMaintenanceTypeList(@NonNull String token, int type, OnResponseListener<MaintenanceTypeList> listener) {
        final Call<KResponse<MaintenanceTypeList>> call = mService.getMaintenanceTypeList(GSON.toJson(new MaintenanceTypeData(token, type)));
        handleCall(call, listener);
    }

    public void sign(@NonNull String token, long id, String qrCode, String location, String picture, OnResponseListener<Void> listener) {
        final Call<KResponse<Void>> call = mService.sign(GSON.toJson(new MaintenanceSignData(token, id, qrCode, location, picture)));
        handleCall(call, listener);
    }

    public void finishMaintenance(@NonNull String token, long id, String desc, String prove, String checkList, OnResponseListener<Void> listener) {
        final Call<KResponse<Void>> call = mService.finishMaintenance(GSON.toJson(new MaintenanceFinishData(token, id, desc, prove, checkList)));
        handleCall(call, listener);
    }

    /**
     * 上传图片
     *
     * @param token
     * @param path
     * @param listener
     */
    public void uploadImage(@NonNull String token, @NonNull String path, final OnResponseListener<String> listener) {
        final RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("token", token)
                .addFormDataPart("file[0]", "Img_" + System.currentTimeMillis() + ".jpg", RequestBody.create(MediaType.parse("image/png"), new File(path)))
                .build();

        final Call<String> call;
        if (!getHttp()){
            call = mService.uploadImage(body);
        }else {
            call = mService2.uploadImage(body);
        }
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    final UploadImageEntity entity = GSON.fromJson(response.body(), UploadImageEntity.class);

                    if (entity.getCode() == 200) {
                        Logs.e(entity.toString());
                        handleSuccess(entity.getImg_urls().get(0), listener);
                    } else {
                        handleError(entity.getMsg(), listener);
                    }

                } else {
                    handleError("网络错误,请重试!", listener);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                handleError("网络错误,请重试!", listener);
            }
        });

    }

    /**
     * 上传图片
     *
     * @param token
     * @param imageData
     * @param listener
     */
    public void uploadImage(@NonNull String token, @NonNull byte[] imageData, final OnResponseListener<String> listener) {
        final RequestBody body = new MultipartBody.Builder()
                .setType(MultipartBody.FORM)
                .addFormDataPart("token", token)
                .addFormDataPart("file[0]", "Img_" + System.currentTimeMillis() + ".jpg", RequestBody.create(MediaType.parse("image/png"), imageData))
                .build();

        final Call<String> call;
        if (!getHttp()){
            call = mService.uploadImage(body);
        }else{
            call = mService2.uploadImage(body);
        }
        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    final UploadImageEntity entity = GSON.fromJson(response.body(), UploadImageEntity.class);

                    if (entity.getCode() == 200) {
                        Logs.e(entity.toString());
                        handleSuccess(entity.getImg_urls().get(0), listener);
                    } else {
                        handleError(entity.getMsg(), listener);
                    }

                } else {
                    handleError("网络错误,请重试!", listener);
                }
            }

            @Override
            public void onFailure(Call<String> call, Throwable t) {
                handleError("网络错误,请重试!", listener);
            }
        });

    }

    private <T> void handleCall(Call<KResponse<T>> call, final OnResponseListener<T> listener) {
        call.enqueue(new Callback<KResponse<T>>() {
            @Override
            public void onResponse(Call<KResponse<T>> call, Response<KResponse<T>> response) {
                if (response.isSuccessful()) {
                    final KResponse<T> kResponse = response.body();

                    Logs.e(GSON.toJson(kResponse));

                    if (kResponse.getCode() == 200) {
                        handleSuccess(kResponse.getData(), listener);
                    } else {
                        handleError(kResponse.getMsg(), listener);
                    }

                } else {
                    Logs.e("Unexpected code " + response.code());
                    handleError("网络错误,请重试!", listener);
                }
            }

            @Override
            public void onFailure(Call<KResponse<T>> call, Throwable t) {
                handleError("网络错误,请重试!", listener);

                Logs.e(t.getMessage());
            }
        });
    }

    private <T> void handleSuccess(T data, OnResponseListener<T> listener) {
        if (listener != null) {
            listener.onSuccess(data);
        }
    }

    private <T> void handleError(String error, OnResponseListener<T> listener) {
        if (listener != null) {
            listener.onFailure(error);
        }
    }

}
