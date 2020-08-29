package com.e.jetpack_java;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

//Dao : 데이터베이스에 엑서스하는데 사용되는 메소드들 가지고있다
@Dao
public interface TodoDao {

    //이 함수에서 Todo 관찰하고 바뀔때 확인 하고싶다.
    //livedata 감싸주면 관찰 가능한 객체
    @Query("SELECT * FROM Todo")
    LiveData<List<Todo>> getAll();

    @Insert
    void insert(Todo todo);

    @Update
    void update(Todo todo);

    @Delete
    void delete(Todo todo);

    @Query("SELECT * FROM TODO WHERE title = :target")
    Todo get(String target);
}
