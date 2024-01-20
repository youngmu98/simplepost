package com.simpo.simplepost.board.controller;

import com.simpo.simplepost.board.dto.BoardPatchDto;
import com.simpo.simplepost.board.dto.BoardPostDto;
import com.simpo.simplepost.board.entity.Board;
import com.simpo.simplepost.board.mapper.BoardMapper;
import com.simpo.simplepost.board.service.BoardService;
import com.simpo.simplepost.post.entity.Post;
import com.simpo.simplepost.post.service.PostService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
public class BoardControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private BoardService boardService;

    @MockBean
    private PostService postService;

    @MockBean
    private BoardMapper boardMapper;

    @DisplayName("게시판 목록 테스트")
    @Test
    public void getBoardsTest() throws Exception {
        // given
        Board board1 = new Board();
        Board board2 = new Board();
        List<Board> boards = Arrays.asList(board1, board2);
        when(boardService.findAll()).thenReturn(boards);

        // when and then
        mockMvc.perform(get("/boards"))
                .andExpect(status().isOk())
                .andExpect(view().name("board/boards"))
                .andExpect(model().attributeExists("boards"))
                .andExpect(model().attribute("boards", boards));

        verify(boardService).findAll();
    }

    @DisplayName("게시판 추가 View로 이동 테스트")
    @Test
    public void getAddViewTest() throws Exception {
        // given
        BoardPostDto boardPostDto = new BoardPostDto();
        // when and then
        mockMvc.perform(get("/boards/add"))
                .andExpect(status().isOk())
                .andExpect(view().name("board/addBoardForm"))
                .andExpect(model().attributeExists("boardPostDto"))
                .andExpect(model().attribute("boardPostDto", boardPostDto));

    }

    @DisplayName("게시판 추가 테스트")
    @Test
    public void postBoardTest() throws Exception {
        // given
        BoardPostDto boardPostDto = new BoardPostDto();
        boardPostDto.setTitle("게시판1");
        Board board = new Board();

        when(boardService.findByTitle(anyString())).thenReturn(false);
        when(boardMapper.boardPostDtotoBoard(any(BoardPostDto.class))).thenReturn(board);

        // when and then
        mockMvc.perform(post("/boards/add")
                .flashAttr("boardPostDto", boardPostDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/boards"));

        verify(boardService).findByTitle(anyString());
        verify(boardMapper).boardPostDtotoBoard(any(BoardPostDto.class));
        verify(boardService).createBoard(any(Board.class));

    }

    @DisplayName("게시판 수정 View로 이동 테스트")
    @Test
    public void getEditViewTest() throws Exception {
        Board board = new Board();
        when(boardService.findById(any(Long.class))).thenReturn(board);

        // when and then
        mockMvc.perform(get("/boards/edit/1"))
                .andExpect(status().isOk())
                .andExpect(view().name("board/editBoardForm"))
                .andExpect(model().attributeExists("board"))
                .andExpect(model().attribute("board",board));

        verify(boardService).findById(any(Long.class));

    }

    @DisplayName("게시판 수정 테스트")
    @Test
    public void updateBoardTest() throws Exception {
        BoardPatchDto boardPatchDto = new BoardPatchDto();
        Board board = new Board();
        when(boardMapper.boardPatchDtoToboard(any(BoardPatchDto.class))).thenReturn(board);

        // when and then
        mockMvc.perform(post("/boards/edit/1")
                        .flashAttr("boardPatchDto", boardPatchDto))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/boards"));

        verify(boardMapper).boardPatchDtoToboard(any(BoardPatchDto.class));
        verify(boardService).updateBoard(any(Board.class));
    }

    @DisplayName("게시판 삭제 테스트")
    @Test
    public void deleteBoardTest() throws Exception {
        // when and then
        mockMvc.perform(delete("/boards/1"))
                .andExpect(status().is3xxRedirection())
                .andExpect(view().name("redirect:/boards"));

        verify(boardService).deleteBoard(any(Long.class));
    }

    @DisplayName("게시판 상세 페이지 이동 테스트")
    @Test
    public void getBoardDetailTest() throws Exception {
        String keyword = "검색어";
        Board board = new Board();
        when(boardService.findById(any(Long.class))).thenReturn(board);

        Page<Post> postPage = new PageImpl<>(Collections.emptyList());
        when(postService.findPostsByBoardAndKeyword(any(Board.class), anyString(), any(PageRequest.class))).thenReturn(postPage);

        mockMvc.perform(get("/boards/1").param("keyword",keyword))
                .andExpect(status().isOk())
                .andExpect(view().name("board/board"))
                .andExpect(model().attributeExists("board", "keyword", "postPage"))
                .andExpect(model().attribute("board", board))
                .andExpect(model().attribute("keyword",keyword))
                .andExpect(model().attribute("postPage",postPage));

        verify(boardService).findById(any(Long.class));
        verify(postService).findPostsByBoardAndKeyword(any(Board.class), anyString(), any(PageRequest.class));
    }
}

