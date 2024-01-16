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
import org.springframework.validation.BindingResult;
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

    // 게시판 목록
    @GetMapping("")
    public String getBoards(Model model) {
        List<Board> boards = boardService.findAll();
        model.addAttribute("boards", boards);
        return "board/boards";
    }

    // 게시판 생성폼
    @GetMapping("/add")
    public String getAddView(Model model) {
        BoardPostDto boardPostDto = new BoardPostDto();
        model.addAttribute("boardPostDto", boardPostDto);
        return "board/addBoardForm";
    }

    // 게시판 생성
    @PostMapping("/add")
    public String postBoard(@ModelAttribute("boardPostDto") BoardPostDto boardPostDto, BindingResult bindingResult, Model model) {
        if (boardService.findByTitle(boardPostDto.getTitle())) {
            bindingResult.rejectValue("title", "error.title", "이미 존재하는 게시판입니다.");
            model.addAttribute("boardPostDto", boardPostDto);
            return "board/addBoardForm";
        }
        Board requestBoard = boardMapper.boardPostDtotoBoard(boardPostDto);
        boardService.createBoard(requestBoard);
        return "redirect:/boards";
    }

    // 게시판 수정폼
    @GetMapping("/edit/{boardId}")
    public String getEditView(@PathVariable Long boardId, Model model) {
        Board board = boardService.findById(boardId);
        model.addAttribute("board", board);
        return "board/editBoardForm";
    }

    // 게시판 수정
    @PostMapping("/edit/{boardId}")
    public String updateBoard(@PathVariable Long boardId, @ModelAttribute("boardPatchDto") BoardPatchDto boardPatchDto, BindingResult bindingResult, Model model) {
        if (boardService.findByTitle(boardPatchDto.getTitle())) {
            Board board = boardService.findById(boardId);
            bindingResult.rejectValue("title", "error.title", "이미 존재하는 게시판입니다.");
            model.addAttribute("board", board);
            return "board/editBoardForm";
        }
        boardPatchDto.setId(boardId);
        Board board = boardMapper.boardPatchDtoToboard(boardPatchDto);
        boardService.updateBoard(board);
        return "redirect:/boards";
    }

    // 게시판 삭제
    @DeleteMapping("/{boardId}")
    public String deleteBoard(@PathVariable Long boardId) {
        boardService.deleteBoard(boardId);
        return "redirect:/boards";
    }

    // 게시물 목록
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
