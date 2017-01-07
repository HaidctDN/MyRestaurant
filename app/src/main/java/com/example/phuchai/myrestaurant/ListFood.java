package com.example.phuchai.myrestaurant;

import android.app.Activity;
import android.os.Bundle;
import android.view.Window;
import android.widget.ListView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import classadapter.FoodAdapter;
import myclass.Food;

public class ListFood extends Activity {
    private ListView listView;
    public static List<Food> listFood;
    private FoodAdapter adapter;
    private DatabaseReference mDatabase;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//bo thanh menu
        setContentView(R.layout.activity_list_food);

        addControl();
        loadListview();
    }

    private void addControl() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        listView = (ListView) findViewById(R.id.listView_food);
        listFood = new ArrayList<Food>();
        adapter = new FoodAdapter(this, R.layout.activity_item_food, listFood);
        listView.setAdapter(adapter);
    }

    private void loadListview() {
        mDatabase.child("Food").addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Food food = dataSnapshot.getValue(Food.class);
                listFood.add(food);
                adapter.notifyDataSetChanged();
                MenuFood.soluong = new int[listFood.size()];
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
