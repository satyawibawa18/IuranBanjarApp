    package id.fabian.pembayaraniuranbanjar;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.app.AppCompatDelegate;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.switchmaterial.SwitchMaterial;

import java.util.Objects;

import id.fabian.pembayaraniuranbanjar.database.AppDatabase;
import id.fabian.pembayaraniuranbanjar.database.wargaEntity;

    public class MainActivity extends AppCompatActivity {

        private EditText EditNama, EditAlm, EditNIK;
        private Button BtnSave;
        private AppDatabase database;
        private RadioGroup EditJK;
        private RadioButton radiobtnchoose;
        private Integer jumlahanggota;
        private RadioButton choosenradiobtn;

        private int idWarga = 0;
        private boolean isEdit = false;

        @Override
        protected void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);

            //UBAH MODE GELAP//
            SwitchMaterial switchmodegelap = findViewById(R.id.switch_mode);
            switchmodegelap.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES);
                    } else {
                        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO);
                    }
                }
            });

            EditNama = findViewById(R.id.editTextName);
            EditAlm = findViewById(R.id.Alamatedit);
            EditNIK = findViewById(R.id.editTextNIK);
            EditJK = findViewById(R.id.radioGroupGender);
            BtnSave = findViewById(R.id.buttonSubmit);

            database = AppDatabase.getInstance(getApplicationContext());

            Intent intent = getIntent();
            idWarga = intent.getIntExtra("idWarga",0);
            if (idWarga>0){
                isEdit = true;
                choosenradiobtn = findViewById(EditJK.getCheckedRadioButtonId());
                //melakukan edit dengan idunik
                wargaEntity wargaEntity = database.wargaDao().get(idWarga);
                EditNama.setText(wargaEntity.fullname);
                EditAlm.setText(wargaEntity.alamat);
                EditNIK.setText(""+wargaEntity.NIK_KITAS);
                choosenradiobtn.setText(wargaEntity.jeniskelamin);
//                jumlahanggota
//                getCheckedBoxValue();
            }else {
                isEdit = false;
            }

            BtnSave.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                     radiobtnchoose = findViewById(EditJK.getCheckedRadioButtonId());
//                wargaEntity wargaentity = new wargaEntity();
//                wargaentity.fullname = EditNama.getText().toString();
//                wargaentity.alamat = EditAlm.getText().toString();
//                blm tau
//                wargaentity.NIK_KITAS = EditNIK.getText().length();
                    showDialog();
//                    Toast.makeText(getApplicationContext(),""+radiobtnchoose.getText().toString(),Toast.LENGTH_SHORT).show();
                }
            });


            Objects.requireNonNull(getSupportActionBar()).setTitle("Form Iuran Banjar");

            setupSeekBar();

//        Button submitButton = (Button) findViewById(R.id.buttonSubmit);
//
//        submitButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                showDialog();
//            }
//        });
        }

    private void showDialog() {
        EditText editTextnama = (EditText) findViewById(R.id.editTextName);
        EditText editTextNIK = (EditText) findViewById(R.id.editTextNIK);
        EditText editTextAlm = (EditText) findViewById(R.id.Alamatedit);
        RadioGroup radioGroupGenders = (RadioGroup) findViewById(R.id.radioGroupGender);
        RadioButton radioButtonChosen = (RadioButton) findViewById(radioGroupGenders.getCheckedRadioButtonId());
        TextView Jumlahangkel = (TextView) findViewById(R.id.jumlahAngkel);

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        dialogBuilder.setTitle("Konfirmasi Data");

        dialogBuilder.setMessage("Apakah anda sudah yakin dengan data berikut?\n\n" +
                "Nama: " + editTextnama.getText() + "\n" +
                "NIK/KITAS: " + editTextNIK.getText() + "\n" +
                "Jenis Kelamin: " + radioButtonChosen.getText() + "\n" +
                "Jumlah Anggota : " + Jumlahangkel.getText() + "\n" +
                "Alamat : " + editTextAlm.getText() + "\n" +
                "Jenis Iuran: " + getCheckedBoxValue() + "\n")
                .setPositiveButton("Ya", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                      Toast.makeText(getApplicationContext(), "Data diterima!", Toast.LENGTH_SHORT).show();
//                        Toast.makeText(getApplicationContext(),"" + editTextnama.getText().toString(), Toast.LENGTH_SHORT).show();

                        Intent pindahAct = new Intent(getApplicationContext(), checkdata.class);
                        pindahAct.putExtra("nama", editTextnama.getText().toString());
                        pindahAct.putExtra("NIK", editTextNIK.getText().toString());
                        pindahAct.putExtra("jeniskelamin", radioButtonChosen.getText().toString());
                        pindahAct.putExtra("almt", editTextAlm.getText().toString());
                        pindahAct.putExtra("jumlahkk", Jumlahangkel.getText());
                        pindahAct.putExtra("jenisIuran", getCheckedBoxValue());

                        if (isEdit){
                            database.wargaDao().update(idWarga, EditNama.getText().toString(), EditAlm.getText().toString(),  Integer.parseInt(EditNIK.getText().toString()), radiobtnchoose.getText().toString());
                        }else {
                            database.wargaDao().insertAll(EditNama.getText().toString(), EditAlm.getText().toString(),  Integer.parseInt(EditNIK.getText().toString()), radiobtnchoose.getText().toString(),getCheckedBoxValue(),jumlahanggota);
                        }

//                        database.wargaDao().insertAll(EditNama.getText().toString(), EditAlm.getText().toString(),  Integer.parseInt(EditNIK.getText().toString()), radiobtnchoose.getText().toString(),getCheckedBoxValue(),jumlahanggota);

//                        Intent pindahAct = new Intent(getApplicationContext(), listmenu.class);
                        startActivity(pindahAct);

                        dialogInterface.cancel();

                    }
                })
                .setNegativeButton("Tidak", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.cancel();
                    }
                })
                .setCancelable(true);

        AlertDialog confirmDialog = dialogBuilder.create();

        confirmDialog.show();
    }

        private String getCheckedBoxValue() {
            CheckBox checkBoxdesa = (CheckBox) findViewById(R.id.checkBoxDesa);
            CheckBox checkBoxsampah = (CheckBox) findViewById(R.id.checkBoxSampah);

            String checkedValue = "";

            if (checkBoxdesa.isChecked()) {
                checkedValue += checkBoxdesa.getText() + ", ";
            }
            if (checkBoxsampah.isChecked()) {
                checkedValue += checkBoxsampah.getText() + " ";
            }

            return checkedValue;
        }

        private void setupSeekBar() {
            int MIN = 1;
            int MAX = 20;
            int STEP = 1;

            SeekBar seekBarAngkel = (SeekBar) findViewById(R.id.seekBarjumlahAngkel);
            TextView textViewAngkelValue = (TextView) findViewById(R.id.jumlahAngkel);

            seekBarAngkel.setMax((MAX - MIN) / STEP);

            seekBarAngkel.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean b) {
//                float computeProgress = (MIN + ( progress * STEP )) * 0.1f;
//                float computeProgress = progress;
                    textViewAngkelValue.setText(String.format(progress + " Orang"));
                    jumlahanggota = progress;
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
        }
    }