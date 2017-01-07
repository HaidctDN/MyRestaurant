package com.example.phuchai.myrestaurant;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import myclass.NhanVien;

public class MainActivity extends Activity {
    private DatabaseReference mDatabase;
    private EditText editID, editPassword;
    private Button btnLogin, btnExit;
    private NhanVien nv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//bo thanh menu
        setContentView(R.layout.activity_main);

        addControl();
        addEvent();

    }

    private void addEvent() {
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventLogin();
            }
        });
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventExit();
            }
        });
    }

    private void addControl() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        editID = (EditText) findViewById(R.id.editID);
        editPassword = (EditText) findViewById(R.id.editPassword);
        btnLogin = (Button) findViewById(R.id.buttonLogin);
        btnExit = (Button) findViewById(R.id.buttonQuit);
    }

    private void eventExit() {
        Toast.makeText(getApplicationContext(), "Exit!",
                Toast.LENGTH_SHORT).show();
        finish();
    }

    private void eventLogin() {
        if (TextUtils.isEmpty(editID.getText().toString().trim()) == false) {
            if (TextUtils.isEmpty(editPassword.getText().toString()) == false) {
                if (editID.getText().toString().trim().matches("\\d+")) {
                    int id = Integer.parseInt(editID.getText().toString()
                            .trim());
                    String pass = editPassword.getText().toString();

                    getData(id, pass);
                } else {
                    Toast.makeText(MainActivity.this,
                            "ID là dữ liểu kiểu số!", Toast.LENGTH_SHORT)
                            .show();
                }
            } else
                Toast.makeText(MainActivity.this,
                        "Bạn chưa nhập mật khẩu !", Toast.LENGTH_SHORT)
                        .show();
        } else
            Toast.makeText(MainActivity.this, "Bạn chưa nhập ID !",
                    Toast.LENGTH_SHORT).show();
    }

    private void getData(final int id, final String pass) {
        mDatabase.child("NhanVien").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                NhanVien nhanvien = dataSnapshot.getValue(NhanVien.class);
                if (nhanvien.getIdNV() == id && nhanvien.getPass().equals(pass)) {
                    nv = nhanvien;
                    xulyLogin();
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

    private void xulyLogin() {
        if (nv != null) {
            Toast.makeText(
                    MainActivity.this,
                    "              >>Login succses!\nWelcome "
                            + nv.getTenNV() + "!",
                    Toast.LENGTH_SHORT).show();
            phanQuyen(nv.getVitri());
        } else
            Toast.makeText(MainActivity.this, ">>Login fail!",
                    Toast.LENGTH_SHORT).show();
    }

    private void phanQuyen(int n) {
        switch (n) {
            case 1:
                break;
            case 2:

                break;
            case 3:
                loadIntent(ListTable.class);
                break;
            case 4:
                loadIntent(Payment.class);
                break;

            default:
                break;
        }
    }

    private void loadIntent(Class c) {
        Intent intent = new Intent(MainActivity.this, c);
        startActivity(intent);
    }
}
//        Food food = new Food("BC01", "Bánh cuốn", "https://firebasestorage.googleapis.com/v0/b/myrestaurant-411a7.appspot.com/o/banhcuon.jpg?alt=media&token=0e42a005-a1a8-4337-9c1d-db376133e812", 50000);
//        mDatabase.child("Food").push().setValue(food);
//        Food food1 = new Food("BT01", "Bao tử trộn", "https://firebasestorage.googleapis.com/v0/b/myrestaurant-411a7.appspot.com/o/baotutron.jpg?alt=media&token=d5cdc210-87a2-4795-921e-67292aad114f", 40000);
//        mDatabase.child("Food").push().setValue(food1);
//        Food food2 = new Food("KTC", "Khoai tây chiên", "https://firebasestorage.googleapis.com/v0/b/myrestaurant-411a7.appspot.com/o/khoaitaychien.jpg?alt=media&token=2fc3dba4-ef80-4485-bf0d-c2c59e1674ce", 15000);
//        mDatabase.child("Food").push().setValue(food2);
//        Food food3 = new Food("MC01", "Mì cay", "https://firebasestorage.googleapis.com/v0/b/myrestaurant-411a7.appspot.com/o/khoaitaychien.jpg?alt=media&token=2fc3dba4-ef80-4485-bf0d-c2c59e1674ce", 50000);
//        mDatabase.child("Food").push().setValue(food3);
//        NhanVien nv[] = new NhanVien[]{new NhanVien(1, "Nguyễn Hoàng Phúc Hải", "0969.643.624", "201756836", 3, "123"), new NhanVien(2, "Tấn Beo", "0963.666.333", "201411222", 4, "123"), new NhanVien(3, "Sơn Tùng", "0969.666.999", "201211122", 5, "123")};
//        for (NhanVien n:nv){
//            mDatabase.child("NhanVien").push().setValue(n, new DatabaseReference.CompletionListener() {
//                @Override
//                public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
//                    if (databaseError == null)
//                        Toast.makeText(getApplicationContext(), "Insert thành công!", Toast.LENGTH_SHORT).show();
//                    else
//                        Toast.makeText(getApplicationContext(), "Insert thất bại!", Toast.LENGTH_SHORT).show();
//                }
//            });
//        }
//        HoaDon hd = new HoaDon("HD1", "Tan beo", 1);
//        mDatabase.child("HoaDon").push().setValue(hd);