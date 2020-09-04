package com.e.jetpack_java;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.add_button) Button add;
    @BindView(R.id.delete_button) Button delete;
    @BindView(R.id.result_text) TextView result;
    @BindView(R.id.input_todo) EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        MainViewModel viewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(MainViewModel.class);

        //UI 갱신
        //livedata obserce
        //setting에서 모듈 자바 1.8 실행하면 아래와 같이 표현하는 람다 사용가능
        viewModel.getAll().observe(this, todos -> {
            result.setText(todos.toString());
        });


        //추가
        add.setOnClickListener(v->{
            viewModel.insert(new Todo(input.getText().toString()));
        });

        //삭제 - title 같은 todo 찾아서 삭제
        delete.setOnClickListener(v->{
            viewModel.delete(input.getText().toString());
        });

    }

}