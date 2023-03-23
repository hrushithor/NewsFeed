package com.example.newsfeed;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsfeed.Adapter.CatagoryRVAdapter;
import com.example.newsfeed.Adapter.NewsRVAdapter;
import com.example.newsfeed.Interface.RetrofitAPI;
import com.example.newsfeed.Model.CatagoryRVModel;
import com.example.newsfeed.Model.NewsModel;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity implements CatagoryRVAdapter.CategoryClickInterface{

    //11ae44a31bed4108be1bd1934e5bc5c6

    private RecyclerView newRV, categoryRV;
    private ProgressBar loadingPB;
    private ArrayList<Articles>articlesArrayList;
    private CatagoryRVAdapter catagoryRVAdapter;
    private ArrayList<CatagoryRVModel> catagoryRVModelArrayList;
    private NewsRVAdapter newsRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        newRV= findViewById(R.id.rv_rvnews);
        categoryRV=findViewById(R.id.rv_categories);
        loadingPB= findViewById(R.id.pb_loading);
        articlesArrayList= new ArrayList<>();
        catagoryRVModelArrayList= new ArrayList<>();
        newsRVAdapter=new NewsRVAdapter(articlesArrayList,this);
        catagoryRVAdapter= new CatagoryRVAdapter(catagoryRVModelArrayList, this,this::onCategoryClick);
        newRV.setLayoutManager(new LinearLayoutManager(this));
        newRV.getRecycledViewPool().clear();
        newRV.setAdapter(newsRVAdapter);
        categoryRV.getRecycledViewPool().clear();
        categoryRV.setAdapter(catagoryRVAdapter);
        getCategories();
        getNews("All");
        newsRVAdapter.notifyDataSetChanged();
    }

    private void getNews(String category) {
        loadingPB.setVisibility(View.VISIBLE);
        articlesArrayList.clear();
        String categoryURL = "https://newsapi.org/v2/top-headlines?country=in&category=" + category + "&apikey=11ae44a31bed4108be1bd1934e5bc5c6";
        String url = "https://newsapi.org/v2/top-headlines?country=in&excludeDomains=stackoverflow.com&sortBy=publishedAt&language=en&apikey=11ae44a31bed4108be1bd1934e5bc5c6";
        String BASE_URL = "https://newsapi.org/";
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitAPI retrofitAPI=retrofit.create(RetrofitAPI.class);
        Call<NewsModel> call;
        if(category.equals("All")){
            call = retrofitAPI.getAllNews(url);
        }else{
            call = retrofitAPI.getNewsByCategory(categoryURL);
        }
        call.enqueue(new Callback<NewsModel>() {
            @Override
            public void onResponse(Call<NewsModel> call, Response<NewsModel> response) {
                NewsModel newsModel=response.body();
                loadingPB.setVisibility(View.GONE);
                ArrayList<Articles> articles= newsModel.getArticles();
                for(int i=0; i< articles.size();i++){
                    articlesArrayList.add(new Articles(articles.get(i).getTitle(), articles.get(i).getDescription(),articles.get(i).getUrlToImage(),
                            articles.get(i).getUrl(), articles.get(i).getContent()));
                }
                newsRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<NewsModel> call, Throwable t) {
                Toast.makeText(MainActivity.this,"Fail to get News",Toast.LENGTH_SHORT).show();
            }
        });
    }
    private void getCategories(){
        catagoryRVModelArrayList.add(new CatagoryRVModel("All","https://media.istockphoto.com/photos/explosion-of-a-traditional-electric-bulb-shot-taken-in-high-speed-picture-id1285957020?b=1&k=20&m=1285957020&s=170667a&w=0&h=LjKVp9aEQ7hSECmh-HiVjTi52GOezolB4Fax_oGJaes="));
        catagoryRVModelArrayList.add(new CatagoryRVModel("Science","https://media.istockphoto.com/photos/vaccine-in-laboratory-flu-shot-and-covid19-vaccination-picture-id1289345741?b=1&k=20&m=1289345741&s=170667a&w=0&h=oG8iaDNP4rOLSgXWfeSziU3Vyu6KJS9Hn2ORohzSsRg="));
        catagoryRVModelArrayList.add(new CatagoryRVModel("Sports","https://media.istockphoto.com/photos/various-sport-equipments-on-grass-picture-id949190736?b=1&k=20&m=949190736&s=170667a&w=0&h=f3ofVqhbmg2XSVOa3dqmvGtHc4VLA_rtbboRGaC8eNo="));
        catagoryRVModelArrayList.add(new CatagoryRVModel("General","https://media.istockphoto.com/photos/common-mistakes-motivational-words-quotes-concept-picture-id1145889040?b=1&k=20&m=1145889040&s=170667a&w=0&h=Q84kiWUr79k6QYrbHj_e7RechJD6hMtRLDg2O1FT3iY="));
        catagoryRVModelArrayList.add(new CatagoryRVModel("Business","https://images.unsplash.com/photo-1600880292203-757bb62b4baf?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NXx8YnVzaW5lc3N8ZW58MHx8MHx8&auto=format&fit=crop&w=500&q=60"));
        catagoryRVModelArrayList.add(new CatagoryRVModel("Entertainment","https://media.istockphoto.com/photos/excited-and-smiling-gamer-girl-in-cute-headset-with-mic-playing-an-picture-id1300833320?b=1&k=20&m=1300833320&s=170667a&w=0&h=x1HMh4pEmZiChuEJRiV9hE0TXChi3xj53ZCIcB3suhY="));
        catagoryRVModelArrayList.add(new CatagoryRVModel("Health","https://images.unsplash.com/photo-1505576399279-565b52d4ac71?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8NHx8aGVhbHRofGVufDB8fDB8fA%3D%3D&auto=format&fit=crop&w=500&q=60"));
        catagoryRVAdapter.notifyDataSetChanged();
    }

    @Override
    public void onCategoryClick(int position) {
        String catagory=  catagoryRVModelArrayList.get(position).getCategory();

        getNews(catagory);
    }
}