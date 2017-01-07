package com.example.phuchai.myrestaurant;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import classadapter.TableAdapter;
import myclass.Table;

public class Payment extends Activity {
    private GridView gridview;
    private List<Table> list;
    private TableAdapter adapter;
    private DatabaseReference mDatabase;
    private Button btn_thanhtoan;
    private int position = -1;
    private ArrayList<String> key;//mang chua key
    private boolean kt;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//bo thanh menu
        setContentView(R.layout.activity_thanh_toan);

        addControl();
        loadGridView();
        addEvent();
    }

    private void addEvent() {
        eventGridView();
        btn_thanhtoan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventThanhToan();
            }
        });
    }

    private void eventGridView() {
        gridview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                position = i;
            }
        });
    }

    private void eventThanhToan() {
        if (position > -1) {
            mDatabase.child("Table").child(key.get(position)).child("status").setValue("T");
            mDatabase.child("Table").child(key.get(position)).child("colorTable").setValue("mauxam");


            Toast.makeText(getApplicationContext(), "Thanh toán thành công!", Toast.LENGTH_SHORT).show();
            position = -1;
        } else
            Toast.makeText(Payment.this, "Bạn chưa chọn bàn!", Toast.LENGTH_SHORT).show();
    }

    private void addControl() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        gridview = (GridView) findViewById(R.id.gridView_table);
        list = new ArrayList<Table>();
        adapter = new TableAdapter(this, list);
        gridview.setAdapter(adapter);
        btn_thanhtoan = (Button) findViewById(R.id.button_thanhtoan);
        key = new ArrayList<String>();
    }

    private void loadGridView() {
        mDatabase.child("Table").addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Table table = dataSnapshot.getValue(Table.class);
                if (table.getStatus().compareTo("BT") == 0) {
                    list.add(table);
                    adapter.notifyDataSetChanged();
                    key.add(dataSnapshot.getKey());
                }
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Table table = dataSnapshot.getValue(Table.class);
                if (table.getStatus().compareTo("BT") == 0 && table.getColorTable().compareTo("mauxanh") == 0) {
                    list.add(table);
                    adapter.notifyDataSetChanged();
                    key.add(dataSnapshot.getKey());
                } else {
                    if (table.getStatus().compareTo("T") == 0)
                        for (int i = 0; i < list.size(); i++)
                            if (list.get(i).getNumberTable() == table.getNumberTable()) {
                                list.remove(i);
                                key.remove(i);
                                adapter.notifyDataSetChanged();
                                break;
                            }
                }
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
