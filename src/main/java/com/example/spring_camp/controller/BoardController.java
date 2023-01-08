package com.example.spring_camp.controller;

import com.example.spring_camp.entity.Board;
import com.example.spring_camp.service.BoardService;
import jdk.swing.interop.SwingInterOpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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

    @GetMapping("/board/list")
    public String boardlist(Model model){
        model.addAttribute("list", boardService.boardList());
        return "boardlist";
    }

    @GetMapping("/board/view")  // localhost:8080/board/view?id=1 -> id가 1인 게시글 불러옴
    public String boardView(Model model, Integer id) {
        model.addAttribute("board", boardService.boardView(id));
        return "boardview";
    }

    @GetMapping("/board/delete")
    public String boardDelete(Integer id){
        boardService.boardDelete(id);

        return "redirect:/board/list"; //삭제하면 list로 넘어가도록
    }
}
