package com.example.retrofitexample;

import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.HeaderMap;
import retrofit2.http.Headers;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;
import retrofit2.http.QueryMap;
import retrofit2.http.Url;

public interface ApiClient {


    @GET("posts")
    Call<List<Post>> getPosts(
            @Query("userId") Integer[] userId,
            @Query("_sort") String sort,
            @Query("_order") String order);

    @GET("posts")
    Call<List<Post>> getPosts(
            @QueryMap Map<String, String>parameters);

    @GET("posts/{id}/comments")
    Call<List<Comment>> getComments(@Path("id") int postId);

    @GET("posts/{id}/comments")
    Call<List<Comment>> getComments(@Url String string);

    @POST("posts")
    Call<Post> createPost(@Body Post post);

    //userId=25&title=Title&body=Text şeklinde

    @FormUrlEncoded
    @POST("posts")
    Call<Post> createPost(
            @Field("userId") int userId,
            @Field("title") String title,
            @Field("body") String text );

    @FormUrlEncoded
    @POST("posts")
    Call<Post> createPost(@FieldMap Map<String,String> fields);

    @Headers({"Static-Header1: 123","Static-Header2:456"})
    @PUT("posts/{id}") //tüm kayıt güncellenecekse put kullanılcak
    Call<Post> putPost(@Header ("Dynamic-Header")String header,
                       @Path("id") int id, @Body Post post);

    @PATCH("posts/{id}") //sadece belli bir kısım güncellenecekse
    Call<Post> patchPost(@HeaderMap Map<String,String> headers, @Path("id") int id, @Body Post post);

    @DELETE("posts/{id}")
    Call<Void> deletePost(@Path("id")int id);

}
