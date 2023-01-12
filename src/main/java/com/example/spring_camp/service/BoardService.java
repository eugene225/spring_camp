package com.example.spring_camp.service;

import com.example.spring_camp.entity.Board;
import com.example.spring_camp.repository.BoardRepository;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void write(Board board, MultipartFile file) throws Exception{
        String projectPath = System.getProperty("user.dir") + "/src/main/resources/static/files"; //프로젝트 경로 담기

        UUID uuid = UUID.randomUUID(); //file 이름에 붙일 랜덤 이름 생성

        String fileName = uuid + "_" + file.getOriginalFilename(); //저장될 파일 이름 만들기

        File saveFile = new File(projectPath, fileName); //파일 생성해서 경로와 이름 설정

        file.transferTo(saveFile);

        board.setFilename(fileName);
        board.setFilepath("/files/" + fileName);
        boardRepository.save(board);
    }

    // 게시글 리스트 처리
    public List<Board> boardList(){
        return boardRepository.findAll();
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
