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

public class EditData extends AppCompatActivity {

    EditText edtName,edtDate;
    Button btnUpdate,btnDelete;
    RadioGroup edtStatus;
    RadioButton RbBelum, RbSudah;
    DatePickerDialog.OnDateSetListener date;
    Calendar myCalendar;
    DataBaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);

        edtName =  findViewById(R.id.edtName);
        edtStatus =  findViewById(R.id.Rd_edtstatus);
        RbBelum = findViewById(R.id.Rb_edtBelum);
        RbSudah = findViewById(R.id.Rb_edtSudah);
        edtDate =  findViewById(R.id.edtTgl);
        btnDelete =  findViewById(R.id.btndelete);
        btnUpdate=  findViewById(R.id.btnupdate);

        edtName.setText(getIntent().getStringExtra("titletodo"));
        String status = getIntent().getStringExtra("statustodo");
        edtDate.setText(getIntent().getStringExtra("datetodo"));

        myDb = new DataBaseHelper(this);
        myCalendar = Calendar.getInstance();

        if (status.equals("0")){
            RbBelum.setChecked(true);
        }else{
            RbSudah.setChecked(true);
        }
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
                new DatePickerDialog(EditData.this, date,
                        myCalendar.get(Calendar.YEAR),myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                String date = edtDate.getText().toString();
                String id = getIntent().getStringExtra("idtodo");
                String status = "";
                if (RbSudah.isChecked()){
                    status = "1";
                }else{
                    status = "0";
                }


                if (name.equals("")||date.equals("")){
                    if (name.equals("")){
                        edtName.setError("judul Harus di isi");
                    } if (date.equals("")){
                        edtDate.setError("tanggal Harus di isi");
                    }
                }else {
                    boolean isUpdate = myDb.updateData(name, status, date, id);
                    if (isUpdate){
                        Toast.makeText(EditData.this, "data berhasil diubah", Toast.LENGTH_SHORT).show();
                    }else {
                        Toast.makeText(EditData.this, "data gagal diubah", Toast.LENGTH_SHORT).show();
                    }
                    startActivity(new Intent(EditData.this,MainActivity.class));
                    finish();
                }
            }
        });

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = getIntent().getStringExtra("idtodo");
                Integer deletedRows = myDb.deleteData(id);
                if (deletedRows>0){
                    Toast.makeText(EditData.this, "data berhasil dihapus", Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(EditData.this, "data gagal dihapus", Toast.LENGTH_SHORT).show();
                }
                startActivity(new Intent(EditData.this,MainActivity.class));
                finish();
            }
        });

    }

    private  void updateLabel(){
        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.US);
        edtDate.setText(simpleDateFormat.format(myCalendar.getTime()));
    }
}