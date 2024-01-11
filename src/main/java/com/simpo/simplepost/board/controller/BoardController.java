package com.simpo.simplepost.board.controller;

import com.simpo.simplepost.board.dto.BoardPatchDto;
import com.simpo.simplepost.board.dto.BoardPostDto;
import com.simpo.simplepost.board.dto.BoardResponseDto;
import com.simpo.simplepost.board.entity.Board;
import com.simpo.simplepost.board.service.BoardService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/boards")
@Controller
public class BoardController {

    private final BoardService boardService;

    public BoardController(BoardService boardService) {
        this.boardService = boardService;
    }

    @GetMapping("")
    public String getBoards(Model model) {
        List<Board> boards = boardService.findAll();
        model.addAttribute("boards", boards);
        return "boards";
    }

    @GetMapping("/add")
    public String getAddView() {
        return "addBoardForm";
    }

    @PostMapping("/add")
    public String postBoard(@ModelAttribute BoardPostDto boardPostDto){
        Board requestBoard = boardPostDto.toEntity();
        boardService.createBoard(requestBoard);

        return "redirect:/boards";
    }

    @GetMapping("/edit/{board_id}")
    public String getEditView(@PathVariable Long board_id, Model model){
        Board board = boardService.findById(board_id);
        model.addAttribute("board", board);
        return "editBoardForm";
    }

    @PostMapping("/edit/{board_id}")
    public String updateBoard(@PathVariable Long board_id, @ModelAttribute BoardPatchDto boardPatchDto){
        boardPatchDto.setId(board_id);
        Board board = boardPatchDto.toEntity();
        boardService.updateBoard(board);
        return "redirect:/boards";
    }

}
