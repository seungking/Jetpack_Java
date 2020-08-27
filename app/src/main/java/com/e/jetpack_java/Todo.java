package com.e.jetpack_java;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

//룸에서 사용하는 엔티티로 설정해주기 위해서 추가
@Entity
public class Todo {
    //sql 기반 데이터기때문에 id 필요
    @PrimaryKey(autoGenerate = true)//알아서 하나씩 증가하는 기본키값
    private int id;
    private String title;

    public Todo(String title) {
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "Todo{" +
                "id=" + id +
                ", title='" + title + '\'' +
                '}';
    }
}
