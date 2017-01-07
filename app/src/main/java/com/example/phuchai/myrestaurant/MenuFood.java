package com.example.phuchai.myrestaurant;

import android.app.Activity;
import android.app.LocalActivityManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import myclass.ChiTietHoaDon;
import myclass.HoaDon;
import myclass.Table;

import static com.example.phuchai.myrestaurant.ListTable.listHD;

public class MenuFood extends Activity {
    private TabHost tabHost;
    private LocalActivityManager mLocalActivityManager;//tab host
    private DatabaseReference mDatabase;
    public static int[] soluong;
    private Button btn_send, btn_cancel;
    //2 bien nay de chua gia tri ben kia
    private Table table;//lay ban de goi mon
    private String keyTable;//key cua ban
//    private List<HoaDon> listHD;
    private int max = 0;// de lay so hoa don
    private List<ChiTietHoaDon> listCTHD;//de lay danh sach chi tiet hoa don
    private ArrayList<String> keyListCTHD;//danh sach key cua chi tiet hoa don
    private int posisiton;//vi tri mon an bi trung

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//bo thanh menu
        setContentView(R.layout.activity_menu_food);

        addControl();
        loadTabHost(savedInstanceState);
        getValuesIntent();
        loadData();
        addEvent();
    }

    private void loadData() {
//        mDatabase.child("HoaDon").addChildEventListener(new ChildEventListener() {
//            @Override
//            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
//                HoaDon hd = dataSnapshot.getValue(HoaDon.class);
//                listHD.add(hd);
//            }
//
//            @Override
//            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onChildRemoved(DataSnapshot dataSnapshot) {
//
//            }
//
//            @Override
//            public void onChildMoved(DataSnapshot dataSnapshot, String s) {
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        });
        mDatabase.child("ChiTietHoaDon").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                ChiTietHoaDon cthd = dataSnapshot.getValue(ChiTietHoaDon.class);
                listCTHD.add(cthd);
                keyListCTHD.add(dataSnapshot.getKey());
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

    private void getValuesIntent() {
        Intent callerIntent = getIntent();
        table = (Table) callerIntent.getSerializableExtra("table");
        keyTable = callerIntent.getStringExtra("key");
    }

    private void addControl() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        tabHost = (TabHost) findViewById(R.id.tabHost);
        btn_send = (Button) findViewById(R.id.btn_send);
        btn_cancel = (Button) findViewById(R.id.btn_cancel);
//        listHD = new ArrayList<HoaDon>();
        listCTHD = new ArrayList<ChiTietHoaDon>();
        keyListCTHD = new ArrayList<String>();
    }

    public void loadTabHost(Bundle savedInstanceState) {
        mLocalActivityManager = new LocalActivityManager(this, false);
        mLocalActivityManager.dispatchCreate(savedInstanceState);
        tabHost.setup(mLocalActivityManager);
        //
        TabHost.TabSpec spec;
        //
        spec = tabHost.newTabSpec("tab1");
        Intent intentFood = new Intent(this, ListFood.class);
        spec.setContent(intentFood);
        spec.setIndicator("Food", getResources().getDrawable(R.drawable.icon_food));
        tabHost.addTab(spec);
        //
        spec = tabHost.newTabSpec("tab1");
        spec.setIndicator("Drink", getResources().getDrawable(R.drawable.icon_drink));
        Intent intentDrink = new Intent(this, MainActivity.class);
        spec.setContent(intentDrink);
        tabHost.addTab(spec);
        //
        tabHost.setCurrentTab(0);
    }

    private void eventCancel() {
        finish();
    }

    private void eventSend() {
        if (checkMon()) {
            String mahd = "";
            if (table.getStatus().compareTo("BT") == 0) {// da co hoa don
                for (HoaDon h : listHD)
                    //kiem tra trong danh sach hoa don
                    if (h.getIdban() == table.getNumberTable() && h.getTrangthai().equals("Chưa thanh toán")) {
                        for (int i = 0; i < soluong.length; i++)
                            if (soluong[i] > 0)
                                if (kiemtraTrungMonAn(ListFood.listFood.get(i).getNameFood()) == 0) {
                                    insertCTHD(h.getMahd(), i);
                                } else {
                                    updateCTHD(i);
                                }
                        mahd = h.getMahd();
                        break;
                    }
            } else {// chua co hoa don( ban moi )
                //them hoa don
                taoHD();
                mahd = "HD" + (max + 1);
                //---update trang thai table
                setTrangthaiTable();
                //insert chi tiet hoa don
                for (int i = 0; i < soluong.length; i++)
                    if (soluong[i] > 0)
                        insertCTHD("HD" + (max + 1), i);
            }
            Toast.makeText(MenuFood.this, "Gọi món thành công!", Toast.LENGTH_SHORT).show();

            finish();
            loadIntent(mahd);
        } else
            Toast.makeText(MenuFood.this, "Bạn chưa chọn món!", Toast.LENGTH_SHORT).show();
    }

    private int kiemtraTrungMonAn(String tenma) {
        for (int i = 0; i < listCTHD.size(); i++)
            if (listCTHD.get(i).getTenma().equals(tenma)) {
                posisiton = i;
                return 1;
            }
        return 0;
    }

    private void setTrangthaiTable() {
        mDatabase.child("Table").child(keyTable).child("status").setValue("BT");
        mDatabase.child("Table").child(keyTable).child("colorTable").setValue("mauxanh");
    }

    private void setSoHD() {
        if (listHD.size() > 0) {
            int n = Integer.parseInt(listHD.get(listHD.size() - 1).getMahd().substring(2));
            if (n > max) {
                max = n;
            }
        }
    }

    private void taoHD() {
        setSoHD();//set gia tri max
        HoaDon hd = new HoaDon("HD" + (max + 1), "Tấn Beo", table.getNumberTable());
        mDatabase.child("HoaDon").push().setValue(hd);
    }

    private void insertCTHD(String mahd, int i) {
        String tenma = ListFood.listFood.get(i).getNameFood();
        Calendar a = Calendar.getInstance();
        SimpleDateFormat ft = new SimpleDateFormat("dd/MM/yyyy hh:mm:ss a");
        ChiTietHoaDon cthd = new ChiTietHoaDon(mahd, tenma, soluong[i], 0, ft.format(a.getTime()));
        mDatabase.child("ChiTietHoaDon").push().setValue(cthd);
//        listCTHD.clear();
//        listHD.clear();
//        loadData();
    }

    private void updateCTHD(int i) {
        mDatabase.child("ChiTietHoaDon").child(keyListCTHD.get(posisiton)).child("soluong").setValue(listCTHD.get(posisiton).getSoluong() + soluong[i]);

    }

    private void loadIntent(String mahd) {
        Intent intent = new Intent(MenuFood.this, DetailOrder.class);
        intent.putExtra("mahd", mahd);
        intent.putExtra("table", table);
        intent.putExtra("key", keyTable);
        startActivity(intent);
    }

    public boolean checkMon() {
        for (int i = 0; i < soluong.length; i++) {
            if (soluong[i] > 0)
                return true;
        }
        return false;
    }

    private void addEvent() {
        btn_cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventCancel();
            }
        });
        btn_send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventSend();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mLocalActivityManager.dispatchResume();
    }

    @Override
    protected void onPause() {
        super.onPause();
        mLocalActivityManager.dispatchPause(isFinishing());
    }
}
