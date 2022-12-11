package com.ssafy.bootudy.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.bootudy.model.dto.Board;
import com.ssafy.bootudy.model.dto.BoardParameter;
import com.ssafy.bootudy.model.service.BoardService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

//http://localhost:9999/vue/swagger-ui.html
//@CrossOrigin(origins = { "*" }, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.POST} , maxAge = 6000)
@RestController
@RequestMapping("/board")
@CrossOrigin("*")
@Api("게시판 컨트롤러  API V1")
public class BoardController {

	private static final Logger logger = LoggerFactory.getLogger(BoardController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private BoardService boardService;
	
	@ApiOperation(value = "공지사항 글작성", notes = "새로운 공지사항 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping("/notice")
	public ResponseEntity<String> writeNotice(@RequestBody @ApiParam(value = "공지사항 정보.", required = true) Board board) throws Exception {
		logger.info("writeNotice - 호출");
		if (boardService.writeNotice(board)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}

	@ApiOperation(value = "게시판 글작성", notes = "새로운 게시글 정보를 입력한다. 그리고 DB입력 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PostMapping("/free")
	public ResponseEntity<String> writeArticle(@RequestBody @ApiParam(value = "게시글 정보.", required = true) Board board) throws Exception {
		logger.info("writeArticle - 호출");
		if (boardService.writeArticle(board)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "공지사항 글목록", notes = "모든 공지사항의 정보를 반환한다.", response = List.class)
	@GetMapping("/notice")
	public ResponseEntity<List<Board>> listNotice(@ApiParam(value = "공지사항을 얻기위한 부가정보.", required = true) BoardParameter boardParameter) throws Exception {
		logger.info("listNotice - 호출");
		return new ResponseEntity<List<Board>>(boardService.listNotice(boardParameter), HttpStatus.OK);
	}
	
	@ApiOperation(value = "게시판 글목록", notes = "모든 게시글의 정보를 반환한다.", response = List.class)
	@GetMapping("/free")
	public ResponseEntity<List<Board>> listArticle(@ApiParam(value = "게시글을 얻기위한 부가정보.", required = true) BoardParameter boardParameter) throws Exception {
		logger.info("listArticle - 호출");
		
		return new ResponseEntity<List<Board>>(boardService.listArticle(boardParameter), HttpStatus.OK);
	}
	
	@ApiOperation(value = "공지사항 글보기", notes = "글번호에 해당하는 공지사항의 정보를 반환한다.", response = Board.class)
	@GetMapping("/notice/{articleNo}")
	public ResponseEntity<Board> getNotice(@PathVariable("articleNo") @ApiParam(value = "얻어올 글의 글번호.", required = true) int articleNo) throws Exception {
		logger.info("getNotice - 호출 : " + articleNo);
		boardService.updateHit(articleNo);
		return new ResponseEntity<Board>(boardService.getNotice(articleNo), HttpStatus.OK);
	}
	
	@ApiOperation(value = "게시판 글보기", notes = "글번호에 해당하는 게시글의 정보를 반환한다.", response = Board.class)
	@GetMapping("/free/{articleNo}")
	public ResponseEntity<Board> getArticle(@PathVariable("articleNo") @ApiParam(value = "얻어올 글의 글번호.", required = true) int articleNo) throws Exception {
		logger.info("getArticle - 호출 : " + articleNo);
		boardService.updateHit(articleNo);
		return new ResponseEntity<Board>(boardService.getArticle(articleNo), HttpStatus.OK);
	}
	
	@ApiOperation(value = "공지사항 글수정", notes = "수정할 공지사항 정보를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping("/notice")
	public ResponseEntity<String> modifyNotice(@RequestBody @ApiParam(value = "수정할 공지사항정보.", required = true) Board board) throws Exception {
		logger.info("modifyNotice - 호출 {}", board);
		
		if (boardService.modifyNotice(board)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.OK);
	}
	
	@ApiOperation(value = "게시판 글수정", notes = "수정할 게시글 정보를 입력한다. 그리고 DB수정 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@PutMapping("/free")
	public ResponseEntity<String> modifyArticle(@RequestBody @ApiParam(value = "수정할 글정보.", required = true) Board board) throws Exception {
		logger.info("modifyArticle - 호출 {}", board);
		
		if (boardService.modifyArticle(board)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.OK);
	}
	
	@ApiOperation(value = "공지사항 글삭제", notes = "글번호에 해당하는 공지사항의 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("/notice/{articleNo}")
	public ResponseEntity<String> deleteNotice(@PathVariable("articleNo") @ApiParam(value = "삭제할 공지사항의 글번호.", required = true) int articleNo) throws Exception {
		logger.info("deleteArticle - 호출");
		if (boardService.deleteNotice(articleNo)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
	
	@ApiOperation(value = "게시판 글삭제", notes = "글번호에 해당하는 게시글의 정보를 삭제한다. 그리고 DB삭제 성공여부에 따라 'success' 또는 'fail' 문자열을 반환한다.", response = String.class)
	@DeleteMapping("/free/{articleNo}")
	public ResponseEntity<String> deleteArticle(@PathVariable("articleNo") @ApiParam(value = "삭제할 글의 글번호.", required = true) int articleNo) throws Exception {
		logger.info("deleteArticle - 호출");
		if (boardService.deleteArticle(articleNo)) {
			return new ResponseEntity<String>(SUCCESS, HttpStatus.OK);
		}
		return new ResponseEntity<String>(FAIL, HttpStatus.NO_CONTENT);
	}
}