package com.example.recyclerviewexample;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    // define recycler View
    RecyclerView recyclerView;
    RecyclerAdapter recyclerAdapter;

    List<Car> carIdList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //find recyclerView
        recyclerView = findViewById(R.id.recyclerView);

        initData();
        initRecyclerView();
    }

    private void initRecyclerView() {
        recyclerAdapter = new RecyclerAdapter(carIdList);
        recyclerView.setAdapter(recyclerAdapter);
    }

    private void initData() {
        carIdList = new ArrayList<>();
        DividerItemDecoration dividerItemDecoration = new DividerItemDecoration(this, DividerItemDecoration.VERTICAL);
        recyclerView.addItemDecoration(dividerItemDecoration);

        carIdList.add(new Car("1D4AEU", "Violation"));
        carIdList.add(new Car("F3F43F", "Violation"));
        carIdList.add(new Car("F3FGT5", "Violation"));
        carIdList.add(new Car("G4G544", "Violation"));
        carIdList.add(new Car("1D4AEU", "Violation"));
        carIdList.add(new Car("F3F43F", "Violation"));
        carIdList.add(new Car("F3FGT5", "Violation"));
        carIdList.add(new Car("G4G544", "Violation"));
        carIdList.add(new Car("1D4AEU", "Violation"));
        carIdList.add(new Car("F3F43F", "Violation"));
        carIdList.add(new Car("F3FGT5", "Violation"));
        carIdList.add(new Car("G4G544", "Violation"));
        carIdList.add(new Car("1D4AEU", "Violation"));
        carIdList.add(new Car("F3F43F", "Violation"));
        carIdList.add(new Car("F3FGT5", "Violation"));
        carIdList.add(new Car("G4G544", "Violation"));
    }
}