package com.example.temp.manager.vo;

import java.util.ArrayList;

import org.springframework.security.core.context.SecurityContextHolder;

import com.example.temp.common.vo.MemberVO;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class OrderInspVO{
	public OrderInspVO() {
		MemberVO member = (MemberVO)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		nno="";
		subNo="";
		orgStation="";
		userId = member.getUsername();
		wtUnit="";
		takeInCode="";
		wUserId="";
		wUserIp="";
		wDate="";
		trkCom="";
		trkNo="";
		nationCode="";
		hsCode="";
		itemDetail="";
		itemCnt="";
		unitValue="";
		brand="";
		makeCntry="";
		makeCom="";
		itemDiv="";
		qtyUnit="";
		packageUnit="";
		exchangeRate="";
		chgCurrency="";
		chgAmt="";
		chnItemDetail="";
		itemMeterial="";
		userWta="";
		userWtc="";
		itemUrl="";
		itemImgUrl="";
		status = "F";
		trkDate="";
		stockNo="";
		stockType="";
		rackCode="";
		whInDate="";
		whStatus="";
		wta="";
		wtc="";
		outNno="";
		groupIdx="";
		idx="";
		dstnStation="";
		fileDir="";
		uid="";
		whMemo="";
		adminYn="";
		whInCnt="";
		whStatusName="";
		whCnt="";
		width="";
		height="";
		length="";
		per="";
		dimUnit="";
		whOutCnt = "";
	}
	//COMMON
	private String nno;
	private String subNo;
	private String orgStation;
	private String userId;
	private String wtUnit;
	private String takeInCode;
	private String wUserId;
	private String wUserIp;
	private String wDate;
	private String trkCom;
	private String trkNo;
	private String cusItemCode;
	
	//ITEM
	private String nationCode;
	private String hsCode;
	private String itemDetail;
	private String itemCnt;
	private String unitValue;
	private String brand;
	private String makeCntry;
	private String makeCom;
	private String itemDiv;
	private String qtyUnit;
	private String packageUnit;
	private String exchangeRate;
	private String chgCurrency;
	private String chgAmt;
	private String chnItemDetail;
	private String itemMeterial;
	private String userWta;
	private String userWtc;
	private String itemUrl;
	private String itemImgUrl;
	private String status;
	private String trkDate;
	private String stockYn;

	//StockAllVO
	private String stockNo;
	private String stockType;
	private String rackCode;
	private String whInDate;
	private String whStatus;
	private String wta;
	private String wtc;
	private String outNno;
	private String groupIdx;
	private String idx;
	private String dstnStation;
	private String fileDir;
	private String uid;
	private String whMemo;
	private String adminYn;
	private String whInCnt;
	private String whStatusName;
	private String whCnt;
	private String width;
	private String height;
	private String length;
	private String per;
	private String dimUnit;
	private String whOutCnt;
	
	private ArrayList<StockAllVO> stockAllCol;
	private CusItemVO cusItemVO;
}
