package id.fabian.pembayaraniuranbanjar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import id.fabian.pembayaraniuranbanjar.adapter.wargaAdapter;
import id.fabian.pembayaraniuranbanjar.database.AppDatabase;
import id.fabian.pembayaraniuranbanjar.database.wargaEntity;

public class listmenu extends AppCompatActivity {
    private RecyclerView recyclerView;
    private id.fabian.pembayaraniuranbanjar.adapter.wargaAdapter WargaAdapter;
    private AppDatabase database;
    private Button savebtn, backbtn;
    private List<wargaEntity> list = new ArrayList<>();
    private AlertDialog.Builder dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_listmenu);
        recyclerView = findViewById(R.id.recycler_view);
        if (getSupportActionBar() != null) {
            getSupportActionBar().hide();
        }

        database = AppDatabase.getInstance(getApplicationContext());
        list.clear();
        list.addAll(database.wargaDao().getAll());
        WargaAdapter = new wargaAdapter(getApplicationContext(), list);
        //popupcrud
        WargaAdapter.setDialog(new wargaAdapter.Dialog() {
            @Override
            public void onClick(int position) {
                //option button
                final CharSequence[] dialogItem = {"Edit", "Hapus"};

                dialog = new AlertDialog.Builder(listmenu.this);
                dialog.setItems(dialogItem, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        switch (which){
                            case 0:
                                //melakukan update/edit
                                Intent intent = new Intent(listmenu.this, MainActivity.class);
                                //menambhkan data dari listwarga
                                intent.putExtra("idWarga", list.get(position).idWarga);
                                startActivity(intent);
                                break;

                                //melakukan delete
                            case 1:
                                wargaEntity wargaEntity = list.get(position);
                                database.wargaDao().delete(wargaEntity);
                                //refresh
                                onStart();
                                break;
                        }
                    }
                });
                dialog.show();
            }
        });

        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(WargaAdapter);

        savebtn = findViewById(R.id.btnsimpan);
        backbtn = findViewById(R.id.btnexit);

//        TextView namakk = findViewById(R.id.nama);
//        TextView nik = findViewById(R.id.NIK);
//        TextView jenkel = findViewById(R.id.JK);
//        TextView anggotakk = findViewById(R.id.jumlahkel);
//        TextView jenisiuran = findViewById(R.id.jenisIuran);
//        TextView alamat = findViewById(R.id.alm);
//
//        Button savebtn = findViewById(R.id.btnsimpan);
//        Button editbtn = findViewById(R.id.btnubah);
//
//        Intent getiuran = getIntent();
//        String nama = getiuran.getStringExtra("nama");
//        namakk.setText(nama);
//        nik.setText(getiuran.getStringExtra("NIK"));
//        jenkel.setText(getiuran.getStringExtra("jeniskelamin"));
//        alamat.setText(getiuran.getStringExtra("almt"));
//        anggotakk.setText(getiuran.getStringExtra("jumlahkk"));
//        jenisiuran.setText(getiuran.getStringExtra("jenisIuran"));
//        Toast.makeText(this,"Selamat datang" + getiuran.getStringExtra("nama"),Toast.LENGTH_SHORT).show();
        savebtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Data Telah Disimpan " , Toast.LENGTH_SHORT).show();
                Intent pindahAct = new Intent(listmenu.this, home.class);
                startActivity(pindahAct);
            }
        });

        backbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finishAffinity();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        list.clear();
        list.addAll(database.wargaDao().getAll());
        WargaAdapter.notifyDataSetChanged();
    }
}