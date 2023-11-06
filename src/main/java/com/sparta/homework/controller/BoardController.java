package com.sparta.homework.controller;

import com.sparta.homework.dto.BoardRequestDto;
import com.sparta.homework.dto.BoardResponseDto;
import com.sparta.homework.entity.Board;
import com.sparta.homework.service.BoardService;

import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api")
public class BoardController {

    private final Map<Integer, Board> boardList = new HashMap<>();

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("/board")           // 게시글 전체 조회
    public List<BoardResponseDto> getBoard() {
        // Map To List
        List<BoardResponseDto> boardResponseDtoList = boardList.values().stream()
                .map(BoardResponseDto::new).toList();
        return boardResponseDtoList;
    }

    @GetMapping("/board/{id}")     // 선택한 게시글 조회
    public Board selectBoard(@PathVariable int id) {
        if (boardList.containsKey(id)) {
            // 해당 게시글 가져오기
            Board board = boardList.get(id);
            return board;

        } else {
            throw new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.");
        }
    }

    @PostMapping("/board")          // 게시글 작성
    public BoardResponseDto createBoard(@RequestBody BoardRequestDto boardRequestDto) {
        // RequestDto -> Entity
        Board board = new Board(boardRequestDto);

        // Board Max ID Check
        int maxId = boardList.size() > 0 ? Collections.max(boardList.keySet()) + 1 : 1;
        board.setId(maxId);

        // DB 저장
        boardList.put(board.getId(), board);

        // Entity -> ResponseDto
        BoardResponseDto boardResponseDto = new BoardResponseDto(board);
        return boardResponseDto;
    }

    @PutMapping("/board/{id}")      // 게시글 수정
    public int updateBoard(@PathVariable int id, @RequestBody BoardRequestDto boardRequestDto) {
        // 해당 메모가 DB에 존재하는지 확인
        if (boardList.containsKey(id)) {
            // 해당 게시글 가져오기
            Board board = boardList.get(id);

            // 가져온 메모 수정
            board.updateBoard(boardRequestDto);
            return board.getId();
        } else {
            throw new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.");
        }
    }

    @DeleteMapping("/board/{id}")   // 게시글 삭제
    public int deleteBoard(@PathVariable int id) {
        // 해당 메모가 DB에 존재하는지 확인
        if (boardList.containsKey(id)) {
            // 해당 메모를 삭제
            boardList.remove(id);
            return id;
        } else {
            throw new IllegalArgumentException("선택한 게시글은 존재하지 않습니다.");
        }
    }
}
