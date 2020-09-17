package com.e.jetpack_java;

import android.app.Application;
import android.os.AsyncTask;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import androidx.room.Room;

import java.util.List;

//main activity 뷰모델
//context 가 필요할때 androidviewmodel로 상속
public class MainViewModel extends AndroidViewModel {

    AppDatabase db;
    public LiveData<List<Todo>> todos;
    public String newTodo;

    public MainViewModel(@NonNull Application application) {
        super(application);
        //db 생성, 설정 이름의 파일이 실제로 생성된다
        db = Room.databaseBuilder(application, AppDatabase.class, "todo-db")
                .allowMainThreadQueries() //그냥 빌드하면 백그라운드에서 작동하지 않으면 돌아가지 않지만
                // 이거 붙혀주면 메인 쓰레드에서 동작이 가능하다 / 실무에선 사용 x 연습할때 주로사용
                //main에서 돌리면 성능이 느려질 수 있다.
                .build();
        todos = getAll();
    }

    //db를 직접 다루지 못하도록 한번 더 감싸주기
    public LiveData<List<Todo>> getAll(){
        return db.todoDao().getAll();
    }

    public void insert(Todo todo){
        //비동기 실행
        //new안에 들어가는게 인자로 넘겨주고
        //execute안에 들어가는게 doinbackground로 들어가는 인자
        new InsertAsyncTask(db.todoDao()).execute(todo);
    }
    public void delete(String todo){
        //비동기 실행
        //new안에 들어가는게 인자로 넘겨주고
        //execute안에 들어가는게 doinbackground로 들어가는 인자
        db.todoDao().delete(db.todoDao().get(todo));
    }

    //AsynTask<넘겨받을 객체, 중간에 처리할 데이터, 프로그래스에서 처리할 값, 결과값>
    private static class InsertAsyncTask extends AsyncTask<Todo, Void, Void> {

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
