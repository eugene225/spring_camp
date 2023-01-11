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
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class BoardServiceTest {

    @Autowired
    BoardRepository boardRepository;

    @Autowired
    BoardService boardService;

    MultipartFile file;

    @BeforeEach
    public void cleanUp(){
        boardRepository.deleteAll();
    }

    @Test
    void write() throws Exception{
        //given
        String title = "test title";
        String content = "test content";

        Board board = Board.builder()
                .title(title)
                .content(content).build();

        //when
        boardService.write(board, file);
        List<Board> boardList = boardRepository.findAll();

        //then
        Board board1 = boardList.get(0);

        assertThat(board1.getTitle()).isEqualTo("test title");
        assertThat(board1.getContent()).isEqualTo("test content");
    }

    @Test
    void 삭제기능테스트(){
        //given
        String title1 = "title1";
        String content1 = "content1";

        Board board1 = Board.builder()
                .title(title1)
                .content(content1).build();

        String title2 = "title2";
        String content2 = "content2";

        Board board2 = Board.builder()
                .title(title2)
                .content(content2).build();

        //when
        boardRepository.save(board1);
        boardRepository.save(board2);

        boardService.boardDelete(board1.getId());

        //then
        List<Board> boardList = boardRepository.findAll();
        Board board = boardList.get(0);

        assertThat(board.getTitle()).isEqualTo("title2");
        assertThat(board.getContent()).isEqualTo("content2");
    }
}