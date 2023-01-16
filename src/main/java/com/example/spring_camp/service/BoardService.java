package com.example.spring_camp.service;

import com.example.spring_camp.entity.Board;
import com.example.spring_camp.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.util.List;
import java.util.UUID;

@Service
public class BoardService {
    @Autowired
    private BoardRepository boardRepository;

    // 글 작성 처리
    public void writefile(Board board, MultipartFile file) throws Exception{
        if(!file.isEmpty()){
            String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files"; //프로젝트 경로 담기

            UUID uuid = UUID.randomUUID(); //file 이름에 붙일 랜덤 이름 생성

            String fileName = uuid + "_" + file.getOriginalFilename(); //저장될 파일 이름 만들기

            File saveFile = new File(projectPath, fileName); //파일 생성해서 경로와 이름 설정

            file.transferTo(saveFile);

            board.setFilename(fileName);
            board.setFilepath("/files/" + fileName);
        }

        boardRepository.save(board);
    }

    public void write(Board board){


        boardRepository.save(board);
    }

    // 게시글 리스트 처리
    public Page<Board> boardList(Pageable pageable){

        return boardRepository.findAll(pageable); //page 정보에 따라서 보여줌
    }

    //특정 게시물 검색
    public Page<Board> boardSearchList(String searchKeyword, Pageable pageable){

        return boardRepository.findByTitleContaining(searchKeyword, pageable);
    }

    // 특정 게시글 불러오기
    public Board boardView(Integer id) {

        return boardRepository.findById(id).get();
    }

    //특정 게시글 삭제
    public void boardDelete(Integer id){


        boardRepository.deleteById(id);
    }

}


//글 작성 할때 file을 첨부 안했는데도 img랑 다운받기가 보임(filepath가 생성됨)