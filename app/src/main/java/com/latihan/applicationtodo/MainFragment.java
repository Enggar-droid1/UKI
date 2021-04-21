package com.latihan.applicationtodo;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;


public class MainFragment extends Fragment {
    List<Todo> list = new ArrayList<Todo>();
    TodoAdapter todoAdapter;
    DataBaseHelper myDb;
    RecyclerView rvTodo;
    EditText editSearch;


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
        editSearch = view.findViewById(R.id.searchView);
        myDb = new DataBaseHelper(getActivity());
        rvTodo = view.findViewById(R.id.rvTodo);
        todoAdapter = new TodoAdapter(getActivity(), list);

        list.addAll(myDb.getAllData());
        todoAdapter.notifyDataSetChanged();
        rvTodo.setLayoutManager(new LinearLayoutManager(getActivity()));
        rvTodo.setAdapter(todoAdapter);

        editSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence S, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence S, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (editSearch.length() != 0){
                    List<Todo> todoSearch = myDb.search(editSearch.getText().toString().trim());
                    if (todoSearch != null) {
                        rvTodo.setAdapter(new TodoAdapter(getActivity(), todoSearch));
                    }
                }else{
                    List<Todo> todoSearch = myDb.getAllData();
                    if (todoSearch != null) {
                        rvTodo.setAdapter(new TodoAdapter(getActivity(), todoSearch));
                    }
                }
            }
        });

        return view;

//            @Override
//            public void afterTextChanged(Editable S) {
//                if (editSearch.length() != 0) {
//                    List<Todo> todoSearch = myDb.search(editSearch.getText().toString().trim());
//                    if (todoSearch != null) {
//                        rvTodo.setAdapter(new TodoAdapter(getActivity(), todoSearch));
//                    }
//                } else {
//                    List<Todo> todoSearch = myDb.getAllData();
//                    if (todoSearch != null) {
//                        rvTodo.setAdapter(new TodoAdapter(getActivity(), todoSearch));
//                    }
//                }
//            }
//        });
//        return view;
    }

}