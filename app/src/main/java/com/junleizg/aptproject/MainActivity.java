package com.junleizg.aptproject;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.junleizg.annotations.CheckField;
import com.junleizg.annotations.EnableField;
import com.junleizg.annotations.EnablerInjector;
import com.junleizg.annotations.CheckMethod;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;


public class MainActivity extends AppCompatActivity {

    @EnableField(value = "sad", group = 1)
    protected TextView mView1 ;

    @EnableField(value = "happy", group = 1)
    protected TextView mView2 ;

    @EnableField(value = "sorrow", group = 2)
    protected TextView mView3 ;

    protected RecyclerView mRecyclerView ;
    protected List<MockData> mMockData;
    private Random  mRandom = new Random();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        EnablerInjector.inject(this);
        checker2(new TextView(this), "123");
        checker1(null, "111");

        findViews();
        createMockData();
        buildList();
    }

    private void findViews() {
        mView1 = findViewById(R.id.editText1);
        mView2 = findViewById(R.id.editText2);
        mView3 = findViewById(R.id.editText3);
        mRecyclerView = findViewById(R.id.recyclerView);
    }

    private void buildList() {
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        mRecyclerView.setAdapter(new RecyclerView.Adapter<VHolder>() {
            @NonNull
            @Override
            public VHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                return new VHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_mock,parent,false));
            }

            @Override
            public void onBindViewHolder(@NonNull VHolder holder, int position) {
                MockData mockData = mMockData.get(position);
                holder.editName.setText(mockData.name);
                holder.editYear.setText(String.valueOf(mockData.year));
            }

            @Override
            public int getItemCount() {
                System.out.println("mMock size = " + mMockData.size());
                return mMockData==null?0:mMockData.size();
            }
        });
    }

    String[] names = new String[]{
            "tom",
            "manson",
            "joy",
            "john",
            "marry",
            "hill",
    };
    private void createMockData() {
        if (mMockData ==null)
            mMockData =new ArrayList<>();
        for (String name : names) {
            MockData mockData = new MockData();
            mockData.name = name;
            mockData.year = mRandom.nextInt(30) + 1970;

            mMockData.add(mockData);
        }
    }
    class MockData{
        public String name;
        public int year;
    }
    class VHolder extends RecyclerView.ViewHolder {
        public TextView editName;
        public TextView editYear;
        public VHolder(View itemView) {
            super(itemView);
            editName=itemView.findViewById(R.id.editTextName);
            editYear=itemView.findViewById(R.id.editTextYear);
        }
    }

    @CheckMethod(name = "checker1")
    public boolean checker1(TextView textView, String fieldName) {
        return false;
    }

    @CheckMethod(name = "checker2")
    public static boolean checker2(TextView textView, String fieldName) {
        return false;
    }
}
