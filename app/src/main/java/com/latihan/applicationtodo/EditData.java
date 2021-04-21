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
    //untuk mendeklarasikan semua variable yang dibutuhkan
    EditText edtName, edtDate;
    Button btnUpdate, btnDelete;
    RadioGroup edtStatus;
    RadioButton RbBelum, RbSudah;
    DatePickerDialog.OnDateSetListener date;
    Calendar myCalendar;
    DataBaseHelper myDb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_data);

        edtName = findViewById(R.id.editName);
        edtStatus = findViewById(R.id.RdEdtstatus);
        RbBelum = findViewById(R.id.RbEdtBelum);
        RbSudah = findViewById(R.id.RbEdtSudah);
        edtDate = findViewById(R.id.editTanggal);
        btnDelete = findViewById(R.id.btnDelete);
        btnUpdate = findViewById(R.id.btnUpdate);

        edtName.setText(getIntent().getStringExtra("titletodo"));
        String status = getIntent().getStringExtra("statustodo");
        edtDate.setText(getIntent().getStringExtra("datetodo"));

        myDb = new DataBaseHelper(this);
        myCalendar = Calendar.getInstance();

        if (status.equals("0")) {
            RbBelum.setChecked(true);
        } else {
            RbSudah.setChecked(true);
        }
        //untuk menyesuaikan kalender pada device yang di gunakan
        date = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                myCalendar.set(Calendar.YEAR, year);
                myCalendar.set(Calendar.MONTH, month);
                myCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
                updateLabel();//memanggil fungsi updateLable
            }
        };
        //agar saat edittext di klik dua kali dapat muncul from tanggal
        edtDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new DatePickerDialog(EditData.this, date,
                        myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });
        //button update untuk mengudate data
        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                String date = edtDate.getText().toString();
                String id = getIntent().getStringExtra("idtodo");
                String status = "";
                if (RbSudah.isChecked()) {
                    status = "1";
                } else {
                    status = "0";
                }

                    //untuk menampilkan peringatan saat edit tect tidak di isi
                if (name.equals("") || date.equals("")) {
                    if (name.equals("")) {
                        edtName.setError("nama tugas tidak boleh kosong");
                    }
                    if (date.equals("")) {
                        edtDate.setError("tanggal tugas tidak boleh kosong");
                    }
                } else {
                    //untuk menampilkan toast saat button update di klik
                    boolean isUpdate = myDb.updateData(name, status, date, id);
                    if (isUpdate) {
                        Toast.makeText(EditData.this, "Tugas berhasil diubah", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(EditData.this, "Tugas gagal diubah", Toast.LENGTH_SHORT).show();
                    }
                    startActivity(new Intent(EditData.this, MainActivity.class));
                    finish();
                }
            }
        });
        //button delete untuk menghapus data
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String id = getIntent().getStringExtra("idtodo");
                Integer deletedRows = myDb.deleteData(id);
                if (deletedRows > 0) {
                    //untuk menampilkan toast saat button delete di klik
                    Toast.makeText(EditData.this, "Tugas berhasil dihapus", Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(EditData.this, "Tugas gagal dihapus", Toast.LENGTH_SHORT).show();
                }
                startActivity(new Intent(EditData.this, MainActivity.class));
                finish();
            }
        });

    }
    //untuk mengupdate tanggal
    private void updateLabel() {
        String myFormat = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(myFormat, Locale.US);
        edtDate.setText(simpleDateFormat.format(myCalendar.getTime()));
    }
}