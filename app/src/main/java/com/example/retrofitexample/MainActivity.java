package com.example.retrofitexample;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.logging.HttpLoggingInterceptor;
import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private TextView textViewResult;
    private ApiClient client;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textViewResult = findViewById(R.id.textViewResult);

        Gson gson = new GsonBuilder().serializeNulls().create();

        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit builder = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .client(okHttpClient)
                .build();

       client = builder.create(ApiClient.class);

        //getPosts();

        //getComments();
        //createPost();
        updatePost();
        //deletePost();


    }

    private void deletePost() {
        Call<Void> call = client.deletePost(5);

        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                textViewResult.setText("Code : "+ response.code());
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                textViewResult.setText(t.getMessage());
            }
        });

    }

    private void updatePost() {
        Post post = new Post(12,null,"TextText");

        Map<String,String> headers = new HashMap<>();
        headers.put("Map-Header1","def");
        headers.put("Map-Header2","ghi");

        Call<Post> call = client.patchPost(headers,5,post);

        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {

                if(!response.isSuccessful()){
                    textViewResult.setText("Code : "+ response.code());
                    return;
                }
                Post postResponse = response.body();

                String content="";
                content+="Code :"+response.code()+"\n";
                content+= "ID : "+postResponse.getId()+"\n";
                content+="Post ID : "+postResponse.getUserId()+"\n";
                content+="Name : "+postResponse.getTitle()+"\n";
                content+="Text : "+postResponse.getText();

                textViewResult.setText(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });


    }

    private void createPost() {
        //Post post =new Post(24,"Title", "Text");

       // Call<Post> call = client.createPost(23,"Title","Text");
        Map<String,String> fields = new HashMap<>();
        fields.put("userId","25");
        fields.put("title","Title123");
        fields.put("body","TextText");
        Call<Post> call = client.createPost(fields);


        call.enqueue(new Callback<Post>() {
            @Override
            public void onResponse(Call<Post> call, Response<Post> response) {
                if(!response.isSuccessful()){
                    textViewResult.setText("Code : "+ response.code());
                    return;
                }
                Post postResponse = response.body();

                String content="";
                content+="Code :"+response.code()+"\n";
                content+= "ID : "+postResponse.getId()+"\n";
                content+="Post ID : "+postResponse.getUserId()+"\n";
                content+="Name : "+postResponse.getTitle()+"\n";
                content+="Text : "+postResponse.getText();

                textViewResult.setText(content);
            }

            @Override
            public void onFailure(Call<Post> call, Throwable t) {

            }
        });
    }

    private void getComments() {

        //Call<List<Comment>> call = client.getComments(5);
        Call<List<Comment>> call = client.getComments("posts/3/comments");
        call.enqueue(new Callback<List<Comment>>() {
            @Override
            public void onResponse(Call<List<Comment>> call, Response<List<Comment>> response) {
                if(!response.isSuccessful()){
                    textViewResult.setText("Error Code : "+response.code());
                }
                List<Comment> comments =response.body();

                for(Comment comment : comments){
                    String content="";
                    content+= "ID : "+comment.getId()+"\n";
                    content+="Post ID : "+comment.getPostId()+"\n";
                    content+="Name : "+comment.getName()+"\n";
                    content+="E-mail : "+comment.getEmail()+"\n";
                    content+="Text : "+comment.getText()+"\n";

                    textViewResult.append(content);

                }


            }

            @Override
            public void onFailure(Call<List<Comment>> call, Throwable t) {

            }
        });

    }

    private void getPosts() {

        Map<String,String> parameters = new HashMap<>();
        parameters.put("userId","1");
        parameters.put("_sort","id");
        parameters.put("_order","desc");
        Call<List<Post>> call = client.getPosts(parameters);
        //Call<List<Post>> call = client.getPosts(new Integer[]{4,5},"id","desc");

        call.enqueue(new Callback<List<Post>>() {
            @Override
            public void onResponse(Call<List<Post>> call, Response<List<Post>> response) {

                if(!response.isSuccessful()) {
                    textViewResult.setText("Code : "+response.code());
                    return;
                }
                List<Post> posts = response.body();
                for(Post post : posts){
                    String content="";
                    content+= "ID : "+post.getId()+"\n";
                    content+="User ID : "+post.getUserId()+"\n";
                    content+="Title : "+post.getTitle()+"\n";
                    content+="Text : "+post.getText()+"\n";

                    textViewResult.append(content);


                }

            }

            @Override
            public void onFailure(Call<List<Post>> call, Throwable t) {
                textViewResult.setText(t.getMessage().toString());
            }
        });
    }
}
