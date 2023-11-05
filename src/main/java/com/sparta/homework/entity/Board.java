package com.sparta.homework.entity;

import com.sparta.homework.dto.BoardRequestDto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Board {

    private int id;             // 게시물 개수
    private String title;       // 제목
    private String writer;      // 작성자명
    private String password;    // 비밀번호
    private String content;     // 작성내용
    private Date date;          // 작성일

    public Board(BoardRequestDto boardRequestDto) {
        this.title = boardRequestDto.getTitle();
        this.writer = boardRequestDto.getWriter();
        this.password = boardRequestDto.getPassword();
        this.content = boardRequestDto.getContent();
        this.date = boardRequestDto.getDate();
    }

    public void updateBoard(BoardRequestDto boardRequestDto) {
        this.title = boardRequestDto.getTitle();
        this.writer = boardRequestDto.getWriter();
        this.content = boardRequestDto.getContent();
    }
}
