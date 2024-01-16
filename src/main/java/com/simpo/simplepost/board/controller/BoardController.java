package com.simpo.simplepost.board.controller;

import com.simpo.simplepost.board.dto.BoardPatchDto;
import com.simpo.simplepost.board.dto.BoardPostDto;
import com.simpo.simplepost.board.entity.Board;
import com.simpo.simplepost.board.mapper.BoardMapper;
import com.simpo.simplepost.board.service.BoardService;
import com.simpo.simplepost.post.entity.Post;
import com.simpo.simplepost.post.service.PostService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@Controller
@RequestMapping("/boards")
public class BoardController {

    private final PostService postService;
    private final BoardService boardService;
    private final BoardMapper boardMapper;

    public BoardController(PostService postService, BoardService boardService, BoardMapper boardMapper) {
        this.postService = postService;
        this.boardService = boardService;
        this.boardMapper = boardMapper;
    }

    @GetMapping("")
    public String getBoards(Model model) {
        List<Board> boards = boardService.findAll();
        model.addAttribute("boards", boards);
        return "board/boards";
    }

    @GetMapping("/add")
    public String getAddView() {
        return "board/addBoardForm";
    }

    @PostMapping("/add")
    public String postBoard(@ModelAttribute BoardPostDto boardPostDto) {
        Board requestBoard = boardMapper.boardPostDtotoBoard(boardPostDto);
        boardService.createBoard(requestBoard);

        return "redirect:/boards";
    }

    @GetMapping("/edit/{boardId}")
    public String getEditView(@PathVariable Long boardId, Model model) {
        Board board = boardService.findById(boardId);
        model.addAttribute("board", board);
        return "board/editBoardForm";
    }

    @PostMapping("/edit/{boardId}")
    public String updateBoard(@PathVariable Long boardId, @ModelAttribute BoardPatchDto boardPatchDto) {
        boardPatchDto.setId(boardId);
        Board board = boardMapper.boardPatchDtoToboard(boardPatchDto);
        boardService.updateBoard(board);
        return "redirect:/boards";
    }

    @DeleteMapping("/{boardId}")
    public String deleteBoard(@PathVariable Long boardId) {
        boardService.deleteBoard(boardId);
        return "redirect:/boards";
    }

    @GetMapping("{boardId}")
    public String getBoardDetail(Model model, @PathVariable Long boardId,
                                 @RequestParam(defaultValue = "0") int page,
                                 @RequestParam(defaultValue = "10") int size,
                                 @RequestParam(required = false) String keyword
                                 ){
        Board board = boardService.findById(boardId);
        PageRequest pageRequest = PageRequest.of(page, size);
        Page<Post> postPage = postService.findPostsByBoardAndKeyword(board, keyword, pageRequest);

        model.addAttribute("board",board);
        model.addAttribute("keyword", keyword);
        model.addAttribute("postPage",postPage);
        return "board/board";
    }

}
