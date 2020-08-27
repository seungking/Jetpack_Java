package com.e.jetpack_java;

import androidx.appcompat.app.AppCompatActivity;
import androidx.room.Room;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

        //db 생성, 설정 이름의 파일이 실제로 생성된다
        AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, "todo-db")
                .allowMainThreadQueries() //그냥 빌드하면 백그라운드에서 작동하지 않으면 돌아가지 않지만
                                          // 이거 붙혀주면 메인 쓰레드에서 동작이 가능하다 / 실무에선 사용 x 연습할때 주로사용
                .build();

        result.setText(db.todoDao().getAll().toString());

        //추가
        add.setOnClickListener(v->{
            db.todoDao().insert(new Todo(input.getText().toString()));
            result.setText(db.todoDao().getAll().toString());
        });

        //삭제 - title 같은 todo 찾아서 삭제
        delete.setOnClickListener(v->{
            db.todoDao().delete(db.todoDao().get(input.getText().toString()));
            result.setText(db.todoDao().getAll().toString());
        });

    }
}