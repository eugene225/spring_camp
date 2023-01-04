package com.example.spring_camp.controller;

import com.example.spring_camp.entity.Board;
import com.example.spring_camp.service.BoardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;

public class BoardController {

    @Autowired
    private BoardService boardService;
    //프로세스 처리
    @PostMapping("/board/writepro")
    public String boardWritePro(Board board){

        boardService.write(board);

        return "";
    }
}
