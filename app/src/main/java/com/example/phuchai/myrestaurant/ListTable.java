package com.example.phuchai.myrestaurant;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

import classadapter.TableAdapter;
import myclass.HoaDon;
import myclass.Table;

public class ListTable extends Activity {
    private GridView gridView;
    private TableAdapter adapter;
    private List<Table> listTable;
    private DatabaseReference mDatabase;
    private Button btnGoimon, btnDatban, btnXem, btnChuyen, btnGhep, btnTach;
    private int position = -1;// vi tri ban duoc chon
    private ArrayList<String> key;//mang chua key cua danh sach ban
    static List<HoaDon> listHD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);//bo thanh menu
        setContentView(R.layout.activity_list_table);

        addControl();
        loadData();
        addEvent();
    }

    private void loadData() {
        mDatabase.child("Table").addChildEventListener(new ChildEventListener() {

            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                Table table = dataSnapshot.getValue(Table.class);
                listTable.add(table);
                adapter.notifyDataSetChanged();
                key.add(dataSnapshot.getKey());
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {
                Table table = dataSnapshot.getValue(Table.class);
                int i;
                for (i = 0; i < listTable.size(); i++)
                    if (listTable.get(i).getNumberTable() == table.getNumberTable()) {
                        listTable.remove(i);
                        adapter.notifyDataSetChanged();
                        break;
                    }
                listTable.add(i, table);
                adapter.notifyDataSetChanged();
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
        mDatabase.child("HoaDon").addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {
                HoaDon hd = dataSnapshot.getValue(HoaDon.class);
                listHD.add(hd);
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

    private void addControl() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        gridView = (GridView) findViewById(R.id.gridView_table);
        listTable = new ArrayList<Table>();
        adapter = new TableAdapter(this, listTable);
        gridView.setAdapter(adapter);
        btnXem = (Button) findViewById(R.id.button_xem);
        btnGoimon = (Button) findViewById(R.id.button_goimon);
        btnDatban = (Button) findViewById(R.id.button_dat);
        key = new ArrayList<String>();
        listHD = new ArrayList<HoaDon>();
        btnChuyen = (Button) findViewById(R.id.button_chuyen);
        btnGhep = (Button) findViewById(R.id.button_ghep);
        btnTach = (Button) findViewById(R.id.button_tach);
    }

    private void addEvent() {
        eventGridview();
        btnDatban.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                bookEvent();
            }
        });
        btnGoimon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                orderEvent();
            }
        });
        btnXem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                lookEvent();
            }
        });
        btnChuyen.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventMoveTable();
            }
        });
        btnGhep.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                eventJoinTable();
            }
        });
    }

    private void eventGridview() {
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                position = i;
            }
        });
    }

    private void bookEvent() {
        if (selected()) {
            uio
            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
            alertDialogBuilder.setIcon(R.drawable.ok);
            if (listTable.get(position).getStatus().equals("T")) {
                alertDialogBuilder.setTitle("Đặt bàn");
                alertDialogBuilder
                        .setMessage("Bạn có chắc muốn đặt bàn này!");
                alertDialogBuilder.setPositiveButton("Đồng ý",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface arg0,
                                                int arg1) {

                                mDatabase.child("Table").child(key.get(position)).child("status").setValue("DD");
                                mDatabase.child("Table").child(key.get(position)).child("colorTable").setValue("mauvang");
                                position = -1;//set lai vi tri
                                Toast.makeText(ListTable.this, "Đặt bàn thành công!", Toast.LENGTH_SHORT).show();
                            }
                        });
            } else {
                if (listTable.get(position).getStatus().equals("DD")) {
                    alertDialogBuilder.setTitle("Hủy đặt");
                    alertDialogBuilder
                            .setMessage("Bạn có chắc muốn hủy đặt bàn này!");
                    alertDialogBuilder.setPositiveButton("Đồng ý",
                            new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface arg0,
                                                    int arg1) {
                                    mDatabase.child("Table").child(key.get(position)).child("status").setValue("T");
                                    mDatabase.child("Table").child(key.get(position)).child("colorTable").setValue("mauxam");
                                    position = -1;//set lai vi tri
                                    Toast.makeText(ListTable.this, "Hủy đặt bàn thành công!", Toast.LENGTH_SHORT).show();
                                }
                            });
                } else {
                    Toast.makeText(ListTable.this, "Bàn này đã có người rồi", Toast.LENGTH_SHORT).show();
                    return;
                }
            }
            alertDialogBuilder.setNegativeButton("Không", null);
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        } else
            Toast.makeText(ListTable.this, "Bạn chưa chọn bàn",
                    Toast.LENGTH_SHORT).show();
    }

    private void orderEvent() {
        if (selected()) {
            loadIntent(listTable.get(position), key.get(position));
            position = -1;//set lai vi tri
        } else
            Toast.makeText(ListTable.this, "Bạn chưa chọn bàn", Toast.LENGTH_SHORT).show();
    }

    private void lookEvent() {
        if (selected()) {
            if (listTable.get(position).getStatus().equals("BT")) {
                for (HoaDon h : listHD)
                    if (h.getIdban() == listTable.get(position).getNumberTable() && h.getTrangthai().equals("Chưa thanh toán"))
                        loadIntent2(listTable.get(position), h.getMahd(), key.get(position));
            } else
                Toast.makeText(ListTable.this, "Bàn này chưa gọi món!", Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(ListTable.this, "Bạn chưa chọn bàn!", Toast.LENGTH_SHORT).show();
    }

    private void loadIntent(Table table, String key) {
        Intent intent = new Intent(ListTable.this, MenuFood.class);
        intent.putExtra("table", table);
        intent.putExtra("key", key);
        startActivity(intent);
    }

    private void loadIntent2(Table table, String mahd, String key) {
        Intent intent = new Intent(ListTable.this, DetailOrder.class);
        intent.putExtra("table", table);
        intent.putExtra("mahd", mahd);
        intent.putExtra("key", key);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        adapter.notifyDataSetChanged();
    }

    private void eventMoveTable() {
        if (selected()) {
            if (listTable.get(position).getStatus().equals("BT"))
                displayAlertDialog("Chuyển bàn");
            else
                Toast.makeText(ListTable.this, "Bàn chưa gọi món",
                        Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(ListTable.this, "Bạn chưa chọn bàn",
                    Toast.LENGTH_SHORT).show();
    }

    private void eventJoinTable() {
        if (selected()) {
            if (listTable.get(position).getStatus().equals("BT"))
                displayAlertDialog("Ghép bàn");
            else
                Toast.makeText(ListTable.this, "Bàn chưa gọi món",
                        Toast.LENGTH_SHORT).show();
        } else
            Toast.makeText(ListTable.this, "Bạn chưa chọn bàn",
                    Toast.LENGTH_SHORT).show();
    }

    private void displayAlertDialog(final String title) {
        LayoutInflater inflater = getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_move_table, null);
        final EditText numTable = (EditText) view.findViewById(R.id.numberTable);
        final TextView textAction = (TextView) view.findViewById(R.id.textAction);
        switch (title) {
            case "Chuyển bàn":
                textAction.setText("Chuyển đến bàn");
                break;
            default:
                textAction.setText("Ghép với bàn");
        }
//        if (title.equals("Chuyển bàn")) {
//            textAction.setText("Chuyển đến bàn:");
//        } else
//            textAction.setText("Ghép với bàn:");
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setIcon(R.drawable.icon_switch);
        builder.setTitle(title + " ---- " + listTable.get(position).getNumberTable());
        builder.setView(view);
        builder.setCancelable(false);
        builder.setNegativeButton("Quay lại", null);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                if (!TextUtils.isEmpty(numTable.getText().toString().trim())) {
                    int editTable = Integer.parseInt(numTable.getText().toString());
                    int num = listTable.get(position).getNumberTable();

                    if (editTable != num) {
                        switch (title) {
                            case "Chuyển bàn":
                                Toast.makeText(getBaseContext(), "Bàn đi:" + num, Toast.LENGTH_SHORT).show();
                                Toast.makeText(getBaseContext(), "Bàn đến:" + editTable, Toast.LENGTH_SHORT).show();
                                break;
                            default:
                                Toast.makeText(getBaseContext(), "Bàn " + num + " ghép với bàn:" + editTable, Toast.LENGTH_SHORT).show();
                        }
                    } else
                        Toast.makeText(ListTable.this, "Bạn đang ở bàn đó.",
                                Toast.LENGTH_SHORT).show();
                } else
                    Toast.makeText(ListTable.this, "Bạn chưa nhập số bàn.",
                            Toast.LENGTH_SHORT).show();
            }
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    private boolean selected() {
        if (position != -1)
            return true;
        else
            return false;
    }
}
