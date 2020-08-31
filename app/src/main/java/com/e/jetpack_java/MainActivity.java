package com.e.jetpack_java;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
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

        //db 생성, 설정 이름의 파일이 실제로 생성된다
        AppDatabase db = Room.databaseBuilder(this, AppDatabase.class, "todo-db")
                .allowMainThreadQueries() //그냥 빌드하면 백그라운드에서 작동하지 않으면 돌아가지 않지만
                                          // 이거 붙혀주면 메인 쓰레드에서 동작이 가능하다 / 실무에선 사용 x 연습할때 주로사용
                                            //main에서 돌리면 성능이 느려질 수 있다.
                .build();

        //UI 갱신
        //livedata obserce
        //setting에서 모듈 자바 1.8 실행하면 아래와 같이 표현하는 람다 사용가능
        db.todoDao().getAll().observe(this, todos -> {
            result.setText(todos.toString());
        });



        //추가
        add.setOnClickListener(v->{
//            db.todoDao().insert(new Todo(input.getText().toString()));
//            result.setText(db.todoDao().getAll().toString());

            //비동기 실행
            new InsertAsyncTask(db.todoDao()).execute(new Todo(input.getText().toString()));
        });

        //삭제 - title 같은 todo 찾아서 삭제
        delete.setOnClickListener(v->{
            db.todoDao().delete(db.todoDao().get(input.getText().toString()));
            //livedata 추가해서 주석처리
            //result.setText(db.todoDao().getAll().toString());
        });

    }

    //AsynTask<넘겨받을 객체, 중간에 처리할 데이터, 프로그래스에서 처리할 값, 결과값>
    private static class InsertAsyncTask extends AsyncTask<Todo, Void, Void>{

        //Dao처리를 위해서 생성자에서 dao 받와아서 지정
        private TodoDao mTodoDao;

        public InsertAsyncTask(TodoDao todoDao) {
            this.mTodoDao = todoDao;
        }

        //여기에서 비동기 처리
        @Override
        protected Void doInBackground(Todo... todos) {
            mTodoDao.insert(todos[0]);
            return null;
        }
    }
}