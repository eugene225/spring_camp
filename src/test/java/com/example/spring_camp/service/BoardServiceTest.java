package com.example.spring_camp.service;

import com.example.spring_camp.entity.Board;
import com.example.spring_camp.repository.BoardRepository;
import org.aspectj.lang.annotation.After;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardServiceTest {

    @Autowired
    BoardRepository boardRepository;

    @BeforeEach
    public void cleanUp(){
        boardRepository.deleteAll();
    }

    @Test
    void write() {
        //given
        String title = "test title";
        String content = "test content";

        Board board = Board.builder()
                .title(title)
                .content(content).build();

        //when
        boardRepository.save(board);
        List<Board> boardList = boardRepository.findAll();

        //then
        Board board1 = boardList.get(0);

        assertThat(board1.getTitle()).isEqualTo("test title");
        assertThat(board1.getContent()).isEqualTo("test content");
    }
}