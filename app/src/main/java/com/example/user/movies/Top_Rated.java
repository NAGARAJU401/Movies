package com.example.user.movies;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class Top_Rated extends AppCompatActivity {
RecyclerView recyclerView;
MyAdapter adapter;
List<Result> results;
Result result;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top__rated);
        results=new ArrayList<>();
        recyclerView=findViewById(R.id.top_rated);
        recyclerView.setLayoutManager(new LinearLayoutManager(Top_Rated.this,LinearLayoutManager.VERTICAL,false));
        adapter=new MyAdapter(Top_Rated.this,results);
        recyclerView.setAdapter(adapter);
        recyclerView.addItemDecoration(new DividerItemDecoration(Top_Rated.this,DividerItemDecoration.VERTICAL));
         ApiInterface apiService=ApiClient.getClient().create(ApiInterface.class);
        Call<Main>gettopratedmoviescall=apiService.gettopratedmovies("f47dd4de64c6ef630c2b0d50a087cc33",1);
        gettopratedmoviescall.enqueue(new Callback<Main>() {
            @Override
            public void onResponse(Call<Main> call, Response<Main> response) {
                for (int i=0;i<response.body().getResults().size();i++){
                    result=new Result();
                    result.setTitle(response.body().getResults().get(i).getTitle());
                    result.setId(response.body().getResults().get(i).getId());
                    result.setReleaseDate(response.body().getResults().get(i).getReleaseDate());
                    result.setVoteAverage(response.body().getResults().get(i).getVoteAverage());
                    result.setPosterPath(response.body().getResults().get(i).getPosterPath());
                    results.add(result);
                }
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(Call<Main> call, Throwable t) {
                Toast.makeText(Top_Rated.this, "request failed", Toast.LENGTH_SHORT).show();
                Log.d("throwable",t.toString());

            }
        });
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });
    }

}
