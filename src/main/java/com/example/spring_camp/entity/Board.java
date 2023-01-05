package com.example.spring_camp.entity;

import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

//class가 table을 의미한다
@Entity
@Getter
@NoArgsConstructor
public class Board {

    //PK 의미
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column
    private String title;

    @Column
    private String content;

    @Builder
    public Board(String title, String content){
        this.title = title;
        this.content = content;
    }

}
