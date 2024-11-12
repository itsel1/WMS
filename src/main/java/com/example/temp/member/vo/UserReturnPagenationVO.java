package com.example.temp.member.vo;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserReturnPagenationVO {

	public UserReturnPagenationVO() {
		pageSize = 10;
		pageNo = 1;
	}
	private int pageSize;
	private int firstPageNo;
	private int prevPageNo;
	private int startPageNo;
	private int pageNo;
	private int endPageNo;
	private int nextPageNo;
	private int finalPageNo;
	private int totalCount;
	
	
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
		this.paging();
	}
	
	
	private void paging() {
		if(this.totalCount==0) 
			return;
		/*if(this.pageNo==0) 
			this.setPageNo(1);
		if(this.pageSize==0)
			this.setPageSize(10);*/
		
		int finalPage = (totalCount + (pageSize - 1)) / pageSize;
		if(this.pageNo>finalPage)
			this.setPageNo(finalPage);
		if(this.pageNo<0 || this.pageNo>finalPage)
			this.pageNo = 1;
		
		boolean isNowFirst = pageNo == 1 ? true : false; 
        boolean isNowFinal = pageNo == finalPage ? true : false; 

        int startPage = ((pageNo - 1) / 10) * 10 + 1; 
        int endPage = startPage + 10 - 1; 

        if (endPage > finalPage) { 
            endPage = finalPage;
        }

        this.setFirstPageNo(1); 

        if (isNowFirst) {
            this.setPrevPageNo(1); 
        } else {
            this.setPrevPageNo(((pageNo - 1) < 1 ? 1 : (pageNo - 1)));
        }

        this.setStartPageNo(startPage); 
        this.setEndPageNo(endPage); 

        if (isNowFinal) {
            this.setNextPageNo(finalPage); 
        } else {
            this.setNextPageNo(((pageNo + 1) > finalPage ? finalPage : (pageNo + 1))); 
        }

        this.setFinalPageNo(finalPage);

		
	}
}

