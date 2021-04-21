package com.latihan.applicationtodo;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

public class AddFragment extends Fragment {
    EditText edtName, edtDate;
    Button btnSubmit, btnCancel;
    DataBaseHelper myDb;
    DatePickerDialog.OnDateSetListener date;
    Calendar myCalendar;

    public AddFragment() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_add2, container, false);
        edtName = view.findViewById(R.id.addName);
        edtDate = view.findViewById(R.id.addtTgl);

        btnCancel = view.findViewById(R.id.btncancleke);
        btnSubmit = view.findViewById(R.id.btn_buatKegiatan);

        myDb = new DataBaseHelper(getActivity());
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
                new DatePickerDialog(getActivity(), date,
                        myCalendar.get(Calendar.YEAR), myCalendar.get(Calendar.MONTH),
                        myCalendar.get(Calendar.DAY_OF_MONTH)).show();
            }
        });

        final RadioGroup rgkegiatan =(RadioGroup)view.findViewById(R.id.Rd_addstatus);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name = edtName.getText().toString();
                String status = "";

                int id = rgkegiatan.getCheckedRadioButtonId();
                switch (id){
                    case R.id.sudah :
                        status = "0";
                        break;
                    case R.id.belum :
                        status = "1";
                        break;
                }
                String date = edtDate.getText().toString();
                if (name.equals("") || date.equals("")) {
                    if (name.equals("")) {
                        edtName.setError("judul Harus di isi");
                    }
                    if (date.equals("")) {
                        edtDate.setError("tanggal Harus di isi");
                    }

                } else {
                    boolean isInserted = myDb.insertData(name, status, date);
                    if (isInserted) {
                        Toast.makeText(getActivity(), "data berhasil di tambahkan", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getActivity(), "data gagal di tambahkan ", Toast.LENGTH_SHORT).show();
                    }
                    startActivity(new Intent(getActivity(), MainActivity.class));
                    getActivity();
                }

            }
        });



        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), MainActivity.class));
                getActivity().finish();
            }
        });
        return view;


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