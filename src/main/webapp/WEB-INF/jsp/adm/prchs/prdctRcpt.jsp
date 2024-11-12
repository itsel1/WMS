<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<style type="text/css">
	.colorBlack {
		color: #000000 !important;
	}
	</style>
	<!-- basic scripts -->

		<!--[if !IE]> -->
		<script src="/assets/js/jquery-2.1.4.min.js"></script>

		<!-- <![endif]-->

		<!--[if IE]>
		<script src="assets/js/jquery-1.11.3.min.js"></script>
		<![endif]-->
		<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		<script src="/assets/js/bootstrap.min.js"></script>

		<!-- page specific plugin scripts -->

		<!-- ace scripts -->
		<script src="/assets/js/ace-elements.min.js"></script>
		<script src="/assets/js/ace.min.js"></script>
	<!-- basic scripts End-->
	</head> 
	<title>사입 상품 입고 관리</title>
	<body class="no-skin">
		<!-- headMenu Start -->
		<%@ include file="/WEB-INF/jsp/importFile/headMenu.jsp" %>
		<!-- headMenu End -->
		
		<!-- Main container Start-->
		<div class="main-container ace-save-state" id="main-container">
			<script type="text/javascript">
				try{ace.settings.loadState('main-container')}catch(e){}
			</script>
			<!-- SideMenu Start -->
			<%@ include file="/WEB-INF/jsp/importFile/managerSideMenu.jsp" %>
			<!-- SideMenu End -->
			
			<div class="main-content">
				<div class="main-content-inner">
					<div class="breadcrumbs ace-save-state" id="breadcrumbs">
						<ul class="breadcrumb">
							<li>
								<i class="ace-icon fa fa-home home-icon"></i>
								Home
							</li>
	
							<li>
								사입
							</li>
							<li class="active">사입 상품 입고 관리</li>
						</ul><!-- /.breadcrumb -->
					</div>
					<div class="page-content">
						<div class="page-header">
							<h1>
								사입 상품 입고 관리
							</h1>
						</div>
						<div id="inner-content-side">
							<div id="search-div">
								<br>
								<form class="hidden-xs hidden-sm" name="search-button" id="search-botton" method="get" action="/mngr/acnt/entrp">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
									<div class="row">
										<div class="col-xs-12 col-sm-11 col-md-12">
											<div class="row">
												<div class="col-xs-8 col-sm-11 col-md-12">
													<!-- <div class="col-xs-8 col-sm-11 col-md-2" style="padding:0px 0px 0px 15px!important">
														<div class="input-group">
															<span class="input-group-addon">
																<i class="fa fa-calendar bigger-110"></i>
															</span>
															<div class="input-group input-daterange">
																<input type="text" class=" form-control" name="start" />
																	<span class="input-group-addon">
																		to
																	</span>
																<input type="text" class=" form-control" name="end" />
															</div>
															
														</div>
													</div> -->
													<div class="col-md-10" style="padding:0px!important">
														<span class="input-group col-md-12" style="display: inline-flex;">
															<%-- <input type="text" class="form-control" name="keywords2" placeholder="${searchKeyword}" style="width:100%; max-width: 70px; text-align: center;" value="발송여부" readonly="true"/>
															<select style="height: 34px">
																<option>발송</option>
																<option>미발송</option>
															</select> --%>
															<input type="text" class="form-control" name="keywords2" placeholder="${searchKeyword}" style="width:100%; max-width: 60px; text-align: center;" value="업체ID" readonly="true"/>
															<input type="text" class="form-control" name="keywords2" placeholder="${searchKeyword}" style="width:100%; max-width: 150px"/>
															<input type="text" class="form-control" name="keywords2" placeholder="${searchKeyword}" style="width:100%; max-width: 75px; text-align: center;" value="상품 코드" readonly="true"/>
															<input type="text" class="form-control" name="keywords2" placeholder="${searchKeyword}" style="width:100%; max-width: 150px"/>
															<input type="text" class="form-control" name="keywords2" placeholder="${searchKeyword}" style="width:100%; max-width: 70px; text-align: center;" value="상품명" readonly="true"/>
															<input type="text" class="form-control" name="keywords2" placeholder="${searchKeyword}" style="width:100%; max-width: 150px"/>
															<input type="text" class="form-control" name="keywords2" placeholder="${searchKeyword}" style="width:100%; max-width: 70px; text-align: center;" value="TrackNo" readonly="true"/>
															<input type="text" class="form-control" name="keywords2" placeholder="${searchKeyword}" style="width:100%; max-width: 150px"/>
															<input type="text" class="form-control" name="keywords2" placeholder="${searchKeyword}" style="width:100%; max-width: 90px; text-align: center;" value="수취인 이름" readonly="true"/>
															<input type="text" class="form-control" name="keywords1" placeholder="${searchKeyword}" style="width:100%; max-width: 150px"/>
															<button type="submit" class="btn btn-default no-border btn-sm">
																<i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
															</button>
														</span>
													</div>
												</div>
											</div>
										</div>
									</div>
								</form>
								<div class="col-xs-12 col-sm-12 hidden-md hidden-lg">
									<button type="submit" class="btn btn-default no-border btn-sm" data-toggle="modal" data-target="#searchModal">
										<i class="ace-icon fa fa-search icon-on-right bigger-110">검색조건 입력하기</i>
									</button>
								</div>
								<br/>
								<form name="rgstr-button" id="search-botton" method="post" action="/test2.do">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
									<div id="rgstr-user" style="text-align: right">
										<button type="submit" class="btn btn-sm btn-primary">
										 등록하기
										</button>
									</div>
								</form>
							</div>
							<br/>
							<div id="table-contents" class="table-contents" style="width:100%; overflow: auto;">
								<div class="display" style="width:1800px;">
									<table id="dynamic-table" class="table table-bordered table-dark">
										<colgroup>
											<%-- <col /> --%>
										</colgroup>
										<thead>
											<tr>
												<th class="center">
													<label class="pos-rel">
															<input type="checkbox" class="ace" /> <span class="lbl"></span>
													</label>
												</th>
												<th class="center colorBlack">
													업체 ID
												</th>
												<th class="center colorBlack">
													상품 코드
												</th>
												<th class="center colorBlack">
													상품 사진
												</th>
												<th class="center colorBlack">
													상품명
												</th>
												<th class="center colorBlack">
													구매구분
												</th>
												<th class="center colorBlack">
													단가
												</th>
												<th class="center colorBlack">
													수량
												</th>
												<th class="center colorBlack">
													금액
												</th>
											</tr>
										</thead>
										<tbody>
											<tr>
												<td class="center" style="word-break:break-all;vertical-align: middle;">
													<label class="pos-rel">
														<input type="checkbox" class="ace" />
														<span class="lbl"></span>
													</label>
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST ID
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST CODE
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST IMAGE
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST NAME
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Division 
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Unit Price
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Count
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Price
												</td>
											</tr>
											<tr>
												<td class="center" style="word-break:break-all;vertical-align: middle;">
													<label class="pos-rel">
														<input type="checkbox" class="ace" />
														<span class="lbl"></span>
													</label>
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST ID
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST CODE
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST IMAGE
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST NAME
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Division 
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Unit Price
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Count
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Price
												</td>
											</tr>
											<tr>
												<td class="center" style="word-break:break-all;vertical-align: middle;">
													<label class="pos-rel">
														<input type="checkbox" class="ace" />
														<span class="lbl"></span>
													</label>
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST ID
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST CODE
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST IMAGE
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST NAME
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Division 
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Unit Price
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Count
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Price
												</td>
											</tr>
											<tr>
												<td class="center" style="word-break:break-all;vertical-align: middle;">
													<label class="pos-rel">
														<input type="checkbox" class="ace" />
														<span class="lbl"></span>
													</label>
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST ID
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST CODE
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST IMAGE
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST NAME
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Division 
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Unit Price
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Count
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Price
												</td>
											</tr>
											<tr>
												<td class="center" style="word-break:break-all;vertical-align: middle;">
													<label class="pos-rel">
														<input type="checkbox" class="ace" />
														<span class="lbl"></span>
													</label>
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST ID
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST CODE
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST IMAGE
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST NAME
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Division 
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Unit Price
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Count
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Price
												</td>
											</tr>
											<tr>
												<td class="center" style="word-break:break-all;vertical-align: middle;">
													<label class="pos-rel">
														<input type="checkbox" class="ace" />
														<span class="lbl"></span>
													</label>
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST ID
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST CODE
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST IMAGE
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST NAME
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Division 
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Unit Price
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Count
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Price
												</td>
											</tr>
											<tr>
												<td class="center" style="word-break:break-all;vertical-align: middle;">
													<label class="pos-rel">
														<input type="checkbox" class="ace" />
														<span class="lbl"></span>
													</label>
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST ID
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST CODE
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST IMAGE
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST NAME
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Division 
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Unit Price
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Count
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Price
												</td>
											</tr>
											<tr>
												<td class="center" style="word-break:break-all;vertical-align: middle;">
													<label class="pos-rel">
														<input type="checkbox" class="ace" />
														<span class="lbl"></span>
													</label>
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST ID
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST CODE
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST IMAGE
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST NAME
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Division 
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Unit Price
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Count
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Price
												</td>
											</tr>
											<tr>
												<td class="center" style="word-break:break-all;vertical-align: middle;">
													<label class="pos-rel">
														<input type="checkbox" class="ace" />
														<span class="lbl"></span>
													</label>
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST ID
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST CODE
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST IMAGE
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST NAME
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Division 
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Unit Price
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Count
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Price
												</td>
											</tr>
											<tr>
												<td class="center" style="word-break:break-all;vertical-align: middle;">
													<label class="pos-rel">
														<input type="checkbox" class="ace" />
														<span class="lbl"></span>
													</label>
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST ID
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST CODE
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST IMAGE
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST NAME
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Division 
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Unit Price
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Count
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Price
												</td>
											</tr>
											<tr>
												<td class="center" style="word-break:break-all;vertical-align: middle;">
													<label class="pos-rel">
														<input type="checkbox" class="ace" />
														<span class="lbl"></span>
													</label>
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST ID
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST CODE
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST IMAGE
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST NAME
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Division 
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Unit Price
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Count
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Price
												</td>
											</tr>
											<tr>
												<td class="center" style="word-break:break-all;vertical-align: middle;">
													<label class="pos-rel">
														<input type="checkbox" class="ace" />
														<span class="lbl"></span>
													</label>
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST ID
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST CODE
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST IMAGE
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST NAME
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Division 
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Unit Price
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Count
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Price
												</td>
											</tr>
											<tr>
												<td class="center" style="word-break:break-all;vertical-align: middle;">
													<label class="pos-rel">
														<input type="checkbox" class="ace" />
														<span class="lbl"></span>
													</label>
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST ID
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST CODE
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST IMAGE
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST NAME
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Division 
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Unit Price
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Count
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Price
												</td>
											</tr>
											<tr>
												<td class="center" style="word-break:break-all;vertical-align: middle;">
													<label class="pos-rel">
														<input type="checkbox" class="ace" />
														<span class="lbl"></span>
													</label>
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST ID
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST CODE
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST IMAGE
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST NAME
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Division 
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Unit Price
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Count
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Price
												</td>
											</tr>
											<tr>
												<td class="center" style="word-break:break-all;vertical-align: middle;">
													<label class="pos-rel">
														<input type="checkbox" class="ace" />
														<span class="lbl"></span>
													</label>
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST ID
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST CODE
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST IMAGE
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST NAME
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Division 
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Unit Price
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Count
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Price
												</td>
											</tr>
											<tr>
												<td class="center" style="word-break:break-all;vertical-align: middle;">
													<label class="pos-rel">
														<input type="checkbox" class="ace" />
														<span class="lbl"></span>
													</label>
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST ID
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST CODE
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST IMAGE
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST NAME
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Division 
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Unit Price
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Count
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Price
												</td>
											</tr>
											<tr>
												<td class="center" style="word-break:break-all;vertical-align: middle;">
													<label class="pos-rel">
														<input type="checkbox" class="ace" />
														<span class="lbl"></span>
													</label>
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST ID
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST CODE
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST IMAGE
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST NAME
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Division 
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Unit Price
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Count
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Price
												</td>
											</tr>
											<tr>
												<td class="center" style="word-break:break-all;vertical-align: middle;">
													<label class="pos-rel">
														<input type="checkbox" class="ace" />
														<span class="lbl"></span>
													</label>
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST ID
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST CODE
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST IMAGE
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST NAME
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Division 
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Unit Price
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Count
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Price
												</td>
											</tr>
											<tr>
												<td class="center" style="word-break:break-all;vertical-align: middle;">
													<label class="pos-rel">
														<input type="checkbox" class="ace" />
														<span class="lbl"></span>
													</label>
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST ID
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST CODE
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST IMAGE
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST NAME
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Division 
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Unit Price
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Count
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Price
												</td>
											</tr>
											<tr>
												<td class="center" style="word-break:break-all;vertical-align: middle;">
													<label class="pos-rel">
														<input type="checkbox" class="ace" />
														<span class="lbl"></span>
													</label>
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST ID
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST CODE
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST IMAGE
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST NAME
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Division 
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Unit Price
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Count
												</td>
												<td class="center" style="vertical-align: middle;">
													TEST Price
												</td>
											</tr>
										</tbody>
									</table>
								</div>
							</div>
						</div>
					</div>
				</div>
			</div><!-- /.main-content -->
		</div>
			
		<!-- Main container End-->
		
		<!-- Footer Start -->
		<%@ include file="/WEB-INF/jsp/importFile/footer.jsp" %>
		<!-- Footer End -->
		
		<!--[if !IE]> -->
		<script src="/assets/js/jquery-2.1.4.min.js"></script>
		<!-- <![endif]-->
		<!--[if IE]>
		<script src="/assets/js/jquery-1.11.3.min.js"></script>
		<![endif]-->
		<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		
		<!-- script on paging start -->
		
		<script src="/assets/js/jquery.dataTables.min.js"></script>
		<script src="/assets/js/jquery.dataTables.bootstrap.min.js"></script>
		<script src="/assets/js/fixedHeader/dataTables.fixedHeader.min.js"></script>
		<script src="/assets/js/fixedHeader/dataTables.fixedHeader.js"></script>
		<script src="/assets/js/dataTables.buttons.min.js"></script>
		<script src="/assets/js/buttons.flash.min.js"></script>
		<script src="/assets/js/buttons.html5.min.js"></script>
		<script src="/assets/js/buttons.print.min.js"></script>
		<script src="/assets/js/buttons.colVis.min.js"></script>
		<script src="/assets/js/dataTables.select.min.js"></script>
		<!-- script on paging end -->
		
		<script src="/assets/js/bootstrap-datepicker.min.js"></script>
		<script src="/assets/js/bootstrap-timepicker.min.js"></script>
		<script src="/assets/js/moment.min.js"></script>
		<script src="/assets/js/daterangepicker.min.js"></script>
		<!-- script addon start -->
		<script type="text/javascript">
		jQuery(function($) {
			$(document).ready(function() {
				$("#5thMenu").toggleClass('open');
				$("#5thMenu").toggleClass('active'); 
				$("#5thTwo").toggleClass('active');
			});

			//Table Function

			var myTable = 
				$('#dynamic-table')
				.DataTable( {
					"dom" : "t",
					"paging":   false,
			        "ordering": false,
			        "info":     false,
			        "searching": false,
			        "scrollY" : "500",
					select: {
						style: 'multi',
						selector: 'td:first-of-type'
					},
					"language":{
						"zeroRecords": "조회 결과가 없습니다.",
					}
			    } );
			
				
				myTable.on( 'select', function ( e, dt, type, index ) {
					if ( type === 'row' ) {
						$( myTable.row( index ).node() ).find('input:checkbox').prop('checked', true);
					}
				} );
				myTable.on( 'deselect', function ( e, dt, type, index ) {
					if ( type === 'row' ) {
						$( myTable.row( index ).node() ).find('input:checkbox').prop('checked', false);
					}
				} );
			
			
			
			
				/////////////////////////////////
				//table checkboxes
				$('th input[type=checkbox], td input[type=checkbox]').prop('checked', false);
				
				//select/deselect all rows according to table header checkbox
				$('#dynamic-table > thead > tr > th input[type=checkbox], #dynamic-table_wrapper input[type=checkbox]').eq(0).on('click', function(){
					var th_checked = this.checked;//checkbox inside "TH" table header
					
					$('#dynamic-table').find('tbody > tr').each(function(){
						var row = this;
						if(th_checked) myTable.row(row).select();
						else  myTable.row(row).deselect();
					});
				});
				
				//select/deselect a row when the checkbox is checked/unchecked
				$('#dynamic-table').on('click', 'td input[type=checkbox]' , function(){
					var row = $(this).closest('tr').get(0);
					if(this.checked) myTable.row(row).deselect();
					else myTable.row(row).select();
				});
		
			
		})
		</script>
		<!-- script addon end -->
		
	</body>
</html>
