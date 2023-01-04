package com.example.spring_camp.controller;

import com.example.spring_camp.entity.Board;
import com.example.spring_camp.service.BoardService;
import jdk.swing.interop.SwingInterOpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class BoardController {

    @Autowired
    private BoardService boardService;

    @GetMapping("/board/write")
    public String boardWriteForm(){
        return "boardwrite";
    }
    //프로세스 처리
    @PostMapping("/board/writepro")
    public String boardWritePro(Board board){
        boardService.write(board);

        return "";
    }
}
