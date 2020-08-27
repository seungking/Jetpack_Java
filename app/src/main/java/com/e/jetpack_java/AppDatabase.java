package com.e.jetpack_java;

import androidx.room.Database;
import androidx.room.RoomDatabase;

//데이터베이스 객체

@Database(entities = {Todo.class}, version=1)
public abstract class AppDatabase extends RoomDatabase {

    //객체가 접근하는 data access object (dao) 필요
    public abstract TodoDao todoDao();

}
