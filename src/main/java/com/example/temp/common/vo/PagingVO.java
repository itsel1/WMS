package com.example.temp.common.vo;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PagingVO {
	private int pivotCount; //목록 pivot
	private int pivotBoardCount; //게시글 pivot
	private int pagingEnd; //목록 끝 번호
	private int pagingStart; //목록시작 번호
	private int boardStart; //게시글 시작 번호
	private int boardEnd; //게시글 끝 번호
	private int realEnd; //실제 목록 끝번호 
	private int totalCount; //전체 게시글 수
	private int prevNum; //이전 목록
	private int nextNum; //다음 목록
	private int currentPage;
	private boolean endPage; // 마지막 목록
	
	
	/**
	 * PagingVO 초기화
	 * Default : pivotCount = 10
	 * 			 pivoitBoardCount = 10
	 * 	현재 페이지에 대응하여 pagination 진행.
	 * */
	public PagingVO(int curPage, int totalCount, int pivotCounts, int pivotBoardCounts) {
		currentPage = curPage;
		pivotCount = pivotCounts;
		pivotBoardCount = pivotBoardCounts;
		endPage = false;
		
		pagingStart = ((((curPage-1)/pivotCount)+1)*pivotCount)-(pivotCount-1); //paging start 18
		pagingEnd = (((curPage-1)/pivotCount)+1)*pivotCount; //paging end 9
		
		boardStart = (curPage*pivotBoardCount)-(pivotBoardCount-1); //board list start
		boardEnd = curPage*pivotBoardCount; // board list end
		
		realEnd = setRealEnd(totalCount);//paging 필요없는 부분 제거하기 위해
		
		if(pagingEnd >= realEnd) {
			pagingEnd = realEnd;
			endPage = true;
		}
		
		nextNum = pagingStart+pivotCount;
		prevNum = pagingStart-pivotCount;
	}
	
	public int setRealEnd(int totalCount) {
		return (int)Math.ceil((double)totalCount/(double)pivotBoardCount);
	}
	

}
