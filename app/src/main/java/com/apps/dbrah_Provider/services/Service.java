package com.apps.dbrah_Provider.services;


import com.apps.dbrah_Provider.model.NotificationDataModel;
import com.apps.dbrah_Provider.model.OrderDataModel;
import com.apps.dbrah_Provider.model.PlaceGeocodeData;
import com.apps.dbrah_Provider.model.ReviewDataModel;
import com.apps.dbrah_Provider.model.StatusResponse;
import com.apps.dbrah_Provider.model.UserModel;

import io.reactivex.Observable;
import io.reactivex.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Response;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

public interface Service {

    @GET("geocode/json")
    Single<Response<PlaceGeocodeData>> getGeoData(@Query(value = "latlng") String latlng,
                                                  @Query(value = "language") String language,
                                                  @Query(value = "key") String key);


    @FormUrlEncoded
    @POST("api/provider/login")
    Single<Response<UserModel>> login(@Field("phone_code") String phone_code,
                                      @Field("phone") String phone,
                                      @Field("email") String email,
                                      @Field("password") String password,
                                      @Field("type") String type);

//    @FormUrlEncoded
//    @POST("api/login")
//    Single<Response<UserModel>> loginWithEmail(@Header("AUTHORIZATION") String token,
//                                               @Field("email") String email,
//                                               @Field("password") String password);

    @FormUrlEncoded
    @POST("api/client-register")
    Single<Response<UserModel>> signUp(@Field("api_key") String api_key,
                                       @Field("name") String name,
                                       @Field("phone_code") String phone_code,
                                       @Field("phone") String phone,
                                       @Field("software_type") String software_type


    );


    @Multipart
    @POST("api/client-register")
    Observable<Response<UserModel>> signUpwithImage(@Part("api_key") RequestBody api_key,
                                                    @Part("name") RequestBody name,
                                                    @Part("phone_code") RequestBody phone_code,
                                                    @Part("phone") RequestBody phone,
                                                    @Part("software_type") RequestBody software_type,
                                                    @Part MultipartBody.Part logo


    );


    @FormUrlEncoded
    @POST("api/logout")
    Single<Response<StatusResponse>> logout(@Header("AUTHORIZATION") String token,
                                            @Field("api_key") String api_key,
                                            @Field("phone_token") String phone_token


    );

    @FormUrlEncoded
    @POST("api/firebase-tokens")
    Single<Response<StatusResponse>> updateFirebasetoken(@Header("AUTHORIZATION") String token,
                                                         @Field("api_key") String api_key,
                                                         @Field("phone_token") String phone_token,
                                                         @Field("user_id") String user_id,
                                                         @Field("software_type") String software_type


    );

    @FormUrlEncoded
    @POST("api/contact-us")
    Single<Response<StatusResponse>> contactUs(@Field("api_key") String api_key,
                                               @Field("name") String name,
                                               @Field("email") String email,
                                               @Field("subject") String phone,
                                               @Field("message") String message


    );


    @GET("api/notifications")
    Single<Response<NotificationDataModel>> getNotifications(@Header("AUTHORIZATION") String token,
                                                             @Query(value = "api_key") String api_key,
                                                             @Query(value = "user_id") String user_id
    );

    @GET("api/provider/reviews")
    Single<Response<ReviewDataModel>> getReviews(@Query(value = "provider_id") String provider_id);
    @GET("api/provider/orders")
    Single<Response<OrderDataModel>> getOrders(@Query(value = "provider_id") String provider_id);

}