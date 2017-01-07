package com.example.phuchai.myrestaurant;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import classadapter.DetailOrderAdapter;
import myclass.ChiTietHoaDon;
import myclass.Table;

public class DetailOrder extends Activity {
    private List<ChiTietHoaDon> listData;
    private ListView listview;
    private String mahd;
    private Table table;
    private DetailOrderAdapter adapter;
    private DatabaseReference mDatabase;
    private TextView ban;
    private Button goitiep, them, giam, ok, huy;
    private int position = -1;
    private String keyTable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//bo thanh menu
        setContentView(R.layout.activity_detail_order);

        getData();
        addControl();
        loadData();
        addEvent();
    }

    private void getData() {
        Intent callerIntent = getIntent();
        mahd = callerIntent.getStringExtra("mahd");
        table = (Table) callerIntent.getSerializableExtra("table");
        keyTable = callerIntent.getStringExtra("key");
    }

    private void addControl() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        listData = new ArrayList<ChiTietHoaDon>();
        listview = (ListView) findViewById(R.id.listView);
        adapter = new DetailOrderAdapter(this, R.layout.activity_item_detail_order, listData);
        listview.setAdapter(adapter);
        ban = (TextView) findViewById(R.id.ban);
        goitiep = (Button) findViewById(R.id.button_goitiep);
        them = (Button) findViewById(R.id.button_them);
        giam = (Button) findViewById(R.id.button_giam);
        ok = (Button) findViewById(R.id.button_ok);
        huy = (Button) findViewById(R.id.button_huy);
    }

    private void addEvent() {
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                position = i;
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        huy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (position > -1) {
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                            DetailOrder.this);
                    alertDialogBuilder.setTitle("Hủy món");
                    alertDialogBuilder
                            .setMessage("Bạn có chắc muốn hủy món này!");
                    alertDialogBuilder.setPositiveButton("Đồng ý",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0,
                                                    int arg1) {
//                                    //huy mon trong from chitiethoadon
//                                    db.QueryData("Delete from ChiTietHoaDon where _MaHD='"
//                                            + array.get(position).getHd()
//                                            .getMaHD()
//                                            + "' and _MaMA='"
//                                            + array.get(position).getMa()
//                                            .getIdFood() + "'");
//                                    //kiem tra xem ban do con mon nao khong
//                                    refeshLayout();
                                }
                            });
                    alertDialogBuilder.setNegativeButton("Không", null);
                    AlertDialog alertDialog = alertDialogBuilder.create();
                    alertDialog.show();
                } else
                    Toast.makeText(DetailOrder.this, "Bạn chưa chọn món để hủy",
                            Toast.LENGTH_SHORT).show();
            }
        });
        goitiep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
                loadIntent(table, keyTable);
            }
        });

    }

    public void loadIntent(Table table, String key) {
        Intent intent = new Intent(DetailOrder.this, MenuFood.class);
        intent.putExtra("table", table);
        intent.putExtra("key", key);
        startActivity(intent);
    }

    private void loadData() {
        ban.append(" " + table.getNumberTable());
        mDatabase.child("ChiTietHoaDon").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChiTietHoaDon chiTietHoaDon = dataSnapshot.getValue(ChiTietHoaDon.class);
                if (chiTietHoaDon.getMahd().equals(mahd)) {
                    listData.add(chiTietHoaDon);
                    adapter.notifyDataSetChanged();
                }
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
