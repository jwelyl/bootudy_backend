package com.ssafy.bootudy.model.service;

import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ssafy.bootudy.model.dto.Board;
import com.ssafy.bootudy.model.dto.BoardParameter;
import com.ssafy.bootudy.model.mapper.BoardMapper;
import com.ssafy.bootudy.util.PageNavigation;

@Service
public class BoardServiceImpl implements BoardService {
	
	@Autowired
	private SqlSession sqlSession;

	@Override
	public boolean writeNotice(Board board) throws Exception {
		if(board.getTitle() == null || board.getContent() == null) {
			throw new Exception();
		}
		return sqlSession.getMapper(BoardMapper.class).writeNotice(board) == 1;
	}
	
	@Override
	public boolean writeArticle(Board board) throws Exception {
		if(board.getTitle() == null || board.getContent() == null) {
			throw new Exception();
		}
		return sqlSession.getMapper(BoardMapper.class).writeArticle(board) == 1;
	}
	
	@Override
	public List<Board> listNotice(BoardParameter boardParameter) throws Exception {
		int start = boardParameter.getPg() == 0 ? 0 : (boardParameter.getPg() - 1) * boardParameter.getSpp();
		boardParameter.setStart(start);
		return sqlSession.getMapper(BoardMapper.class).listNotice(boardParameter);
	}

	@Override
	public List<Board> listArticle(BoardParameter boardParameter) throws Exception {
		int start = boardParameter.getPg() == 0 ? 0 : (boardParameter.getPg() - 1) * boardParameter.getSpp();
		boardParameter.setStart(start);
		return sqlSession.getMapper(BoardMapper.class).listArticle(boardParameter);
	}

	@Override
	public PageNavigation makePageNavigation(BoardParameter boardParameter) throws Exception {
		int naviSize = 5;
		PageNavigation pageNavigation = new PageNavigation();
		pageNavigation.setCurrentPage(boardParameter.getPg());
		pageNavigation.setNaviSize(naviSize);
		int totalCount = sqlSession.getMapper(BoardMapper.class).getTotalCount(boardParameter);//총글갯수  269
		pageNavigation.setTotalCount(totalCount);  
		int totalPageCount = (totalCount - 1) / boardParameter.getSpp() + 1;//27
		pageNavigation.setTotalPageCount(totalPageCount);
		boolean startRange = boardParameter.getPg() <= naviSize;
		pageNavigation.setStartRange(startRange);
		boolean endRange = (totalPageCount - 1) / naviSize * naviSize < boardParameter.getPg();
		pageNavigation.setEndRange(endRange);
		pageNavigation.makeNavigator();
		return pageNavigation;
	}
	
	@Override
	public Board getNotice(int articleno) throws Exception {
		return sqlSession.getMapper(BoardMapper.class).getNotice(articleno);
	}

	@Override
	public Board getArticle(int articleno) throws Exception {
		return sqlSession.getMapper(BoardMapper.class).getArticle(articleno);
	}
	
	@Override
	public void updateHit(int articleno) throws Exception {
		sqlSession.getMapper(BoardMapper.class).updateHit(articleno);
	}
	
	@Override
	@Transactional
	public boolean modifyNotice(Board board) throws Exception {
		return sqlSession.getMapper(BoardMapper.class).modifyNotice(board) == 1;
	}

	@Override
	@Transactional
	public boolean modifyArticle(Board board) throws Exception {
		return sqlSession.getMapper(BoardMapper.class).modifyArticle(board) == 1;
	}

	@Override
	@Transactional
	public boolean deleteNotice(int articleno) throws Exception {
		return sqlSession.getMapper(BoardMapper.class).deleteNotice(articleno) == 1;
	}
	
	@Override
	@Transactional
	public boolean deleteArticle(int articleno) throws Exception {
		return sqlSession.getMapper(BoardMapper.class).deleteArticle(articleno) == 1;
	}
}