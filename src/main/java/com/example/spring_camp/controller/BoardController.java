package com.example.spring_camp.controller;

import com.example.spring_camp.entity.Board;
import com.example.spring_camp.service.BoardService;
import jdk.swing.interop.SwingInterOpUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.multipart.MultipartFile;

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
    public String boardWritePro(Board board, Model model, MultipartFile file) throws Exception{
        boardService.writefile(board, file);

        model.addAttribute("message", "글 작성이 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");
        return "message";
    }

    @GetMapping("/board/list")
    public String boardlist(Model model,
                            @PageableDefault(page = 0, size=10, sort = "id", direction = Sort.Direction.DESC) Pageable pageable, //page는 0번부터, size, 정렬기준을 기본설정 가능
                            // board/list?page=page번호&size=크기
                            String searchKeyword){

        Page<Board> list = null;

        if(searchKeyword == null){//검색 단어가 안들어왔을 경우
            list = boardService.boardList(pageable);
        }
        else{//검색할 단어가 들어온 경우
            list = boardService.boardSearchList(searchKeyword, pageable);
        }

        int nowPage = list.getPageable().getPageNumber() + 1; //현재 페이지, pageable의 시작은 0번부터니까
        int startPage = Math.max(nowPage - 4, 1); //블럭에서 보여줄 시작 페이지, max함수: 매개변수를 비교해서 더 큰값을 return
        int endPage = Math.min(nowPage + 5, list.getTotalPages()); //블럭에서 보여줄 마지막 페이지, min함수: 매개변수를 비교해서 더 작은값을 return

        model.addAttribute("list", list);
        model.addAttribute("nowPage", nowPage);
        model.addAttribute("startPage", startPage);
        model.addAttribute("endPage", endPage);

        return "boardlist";
    }

    @GetMapping("/board/view")  // localhost:8080/board/view?id=1 -> id가 1인 게시글 불러옴
    public String boardView(Model model, Integer id) {
        model.addAttribute("board", boardService.boardView(id));
        return "boardview";
    }

    @GetMapping("/board/delete")
    public String boardDelete(Integer id, Model model){
        boardService.boardDelete(id);

        model.addAttribute("message", "글 삭제가 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");

        return "message"; //삭제하면 list로 넘어가도록
    }

    @GetMapping("/board/modify/{id}")
    public String boardModify(@PathVariable("id") Integer id, //PathVariable: url /(역슬래쉬) 뒤에 있는 id 부분이 인식이 되어서 Integer 형태의 id로 들어온다
                              Model model){

        model.addAttribute("board", boardService.boardView(id));

        return "boardmodify";
    }

    @PostMapping("/board/update/{id}")
    public String boardUpdate(@PathVariable("id") Integer id, Board board, Model model) throws Exception{

        Board boardTemp = boardService.boardView(id); //기존의 글이 담김
        boardTemp.setTitle(board.getTitle()); //수정된 제목
        boardTemp.setContent(board.getContent());//수정된 내용 덮어씌우기

        boardService.write(boardTemp);

        model.addAttribute("message", "글 수정이 완료되었습니다.");
        model.addAttribute("searchUrl", "/board/list");

        return "message";
    }
}
