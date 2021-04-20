package com.latihan.applicationtodo;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class TambahData extends AppCompatActivity {

    EditText edtName,edtStatus,edtDate;
    Button btnSubmit,btnCancel;

    DataBaseHelper myDb;
    DatePickerDialog.OnDateSetListener date;
    Calendar myCalendar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tambah_data);

        edtName= findViewById(R.id.edtName);
        edtStatus= findViewById(R.id.edtStatus);
        edtDate= findViewById(R.id.edtTgl);

        btnCancel = findViewById(R.id.btncancleke);
        btnSubmit = findViewById(R.id.btn_buatKegiatan);

        myDb = new DataBaseHelper(this);
        myCalendar = Calendar.getInstance();

        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();//memanggil fungsi updateLable
            }
        };

        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(TambahData.this, date,
                        myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                String status = edtStatus.getText().toString();
                String date = edtDate.getText().toString();
                if (name.equals("")||status.equals("")||date.equals("")){
                    if (name.equals("")){
                        edtName.setError("judul Harus di isi");
                    } if (status.equals("")){
                        edtStatus.setError("deskripsi Harus di isi");
                    } if (date.equals("")){
                        edtDate.setError("tanggal Harus di isi");
                    }

                }else {
                    boolean isInserted = myDb.insertData(name,status,date);
                    if (isInserted){
                        Toast.makeText(TambahData.this, "data berhasil di tambahkan", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(TambahData.this, "data gagal di tambahkan ", Toast.LENGTH_SHORT).show();
                    }
                    startActivity(new Intent(TambahData.this,MainActivity.class));
                    finish();
                }

            }
        });

        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(TambahData.this,MainActivity.class));
                finish();
            }
        });


    }

    private void AddData(){

    }

    //untuk mengupdate tanggal
    private  void updateLabel(){
        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.US);
        edtDate.setText(simpleDateFormat.format(myCalendar.getTime()));
    }
}