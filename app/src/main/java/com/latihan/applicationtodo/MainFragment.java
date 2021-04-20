package com.latihan.applicationtodo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;


public class MainFragment extends Fragment {
    List<Todo> list = new ArrayList<Todo>();
    TodoAdapter toDoAdapter;
    DataBaseHelper myDb;
    RecyclerView rvTodo;
    Button fabAdd;


    public MainFragment() {
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
        View view = inflater.inflate(R.layout.fragment_main2, container, false);

        myDb = new DataBaseHelper(getActivity());
        rvTodo = view.findViewById(R.id.rvTodo);
return  view;
    }

}