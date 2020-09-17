package com.e.jetpack_java;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.room.Room;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.e.jetpack_java.databinding.ActivityMainBinding;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

//    @BindView(R.id.add_button) Button add;
//    @BindView(R.id.delete_button) Button delete;
//    @BindView(R.id.result_text) TextView result;
//    @BindView(R.id.input_todo) EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_main);
        //databinding 을 하면 xml에 데이터를 넘겨줄 수 있다.
        ActivityMainBinding binding = DataBindingUtil.setContentView(this,R.layout.activity_main);
        binding.setLifecycleOwner(this);

//        ButterKnife.bind(this);

        MainViewModel viewModel = new ViewModelProvider(this, new ViewModelProvider.AndroidViewModelFactory(getApplication())).get(MainViewModel.class);
        binding.setViewModel(viewModel);

        //UI 갱신
        //livedata obserce
        //setting에서 모듈 자바 1.8 실행하면 아래와 같이 표현하는 람다 사용가능
//        viewModel.getAll().observe(this, todos -> {
//            binding.resultText.setText(todos.toString());
//        });


//        //추가
//        binding.addButton.setOnClickListener(v->{
//            viewModel.insert(new Todo(binding.inputTodo.getText().toString()));
//        });

        //삭제 - title 같은 todo 찾아서 삭제
        binding.deleteButton.setOnClickListener(v->{
            viewModel.delete(binding.inputTodo.getText().toString());
        });

    }

}