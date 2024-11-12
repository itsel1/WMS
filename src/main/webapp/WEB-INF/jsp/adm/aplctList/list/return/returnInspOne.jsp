<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<style>
		input[type=file] {
            display: none !important;
        }
        
		.my_button {
            display: inline-block;
            width: 200px;
            text-align: center;
            padding: 10px;
            background-color: #006BCC;
            color: #fff;
            text-decoration: none;
            border-radius: 5px;
        }
		.imgs_wrap {

            border: 2px solid #A8A8A8;
            margin-top: 30px;
            margin-bottom: 30px;
            padding-top: 10px;
            padding-bottom: 10px;

        }
        .imgs_wrap img {
            max-width: 150px;
            margin-left: 10px;
            margin-right: 10px;
        }
        
        .add_imgs_wrap {

            border: 2px solid #A8A8A8;
            margin-top: 30px;
            margin-bottom: 30px;
            padding-top: 10px;
            padding-bottom: 10px;

        }
        .add_imgs_wrap img {
            max-width: 150px;
            margin-left: 10px;
            margin-right: 10px;
        }
		
		.profile-info-name{
        	font-weight: bold;
        	border-right: 1px dotted #D5E4F1;
        	border-bottom: 1px dotted #D5E4F1;
        	border-top: none;
        	vertical-align: middle;
        	width:150px !important;
        }
        .profile-info-value{
        	word-break:break-all;
        	border-right: 1px dotted #D5E4F1;
        	border-bottom: 1px dotted #D5E4F1;
        	border-top: none;
        	vertical-align: middle;
        }
        .sizeFont{
        	width:60px !important;
        	border:1px solid #cccccc !important;
        	vertical-align:middle !important;
        	font-size:15pt !important; 
        	background-color:#F5F6CE !important;
        	font:bold !important;
        	color:#000 !important;
        }
        .housCntFont{
        	width:40px !important;
        	border:1px solid #cccccc !important;
        	vertical-align:middle !important;
        	font-size:20pt !important; 
        	color:#000 !important;
        	text-align: center;
        }
        
        .real-red{
        	color:red !important;
        }
        
        #atag {
        	pointer-events : none;
        }
        

	</style>
   
	<head>
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<style type="text/css">
     .colorBlack {color:#000000 !important;}
    </style>
	<!-- basic scripts -->
	<!-- ace scripts -->
   
	</head> 
	<title>반품 리스트</title>
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
								반품
							</li>
							<li class="active">반품 검수 리스트</li>
						</ul><!-- /.breadcrumb -->
					</div>
	               
					<div class="page-content">
						<div class="page-header">
							<h1>
								반품 검수 리스트
							</h1>
						</div>
						<div id="inner-content-side" >
							<form name="returnForm" id="returnForm" enctype="multipart/form-data">
								<input type="hidden" name="orderReference" id="orderReference" value="${returnInspOne.ORDER_REFERENCE}">
								<input type="hidden" name="nno" id="nno" value="${returnInspOne.NNO}">
		                    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		                    	<div id="table-contents" class="table-contents"">
									<table id="dynamic-table" class="table table-bordered table-hover containerTable" style="vertical-align: middle;">
										<colgroup>
											<col width="12%"/> 
											<col width="15%"/>
											<col width="11%"/>
											<col width="15%"/>
											<col width="21%"/>
											<col width="26%"/>
										</colgroup>
										<tr>
											<td colspan="6" style="text-align:center; background:#EBF5FF; font-weight:bold;">반송 정보</td>
										</tr>
										<tr>
			                    	 		<td>
			                    	 			KOBL NO : ${returnInspOne.KOBL_NO} 
			                    	 		</td>
			                    	 		<td>
			                    	 			ORDER_NO : ${returnInspOne.ORDER_NO }
			                    	 		</td>
			                    	 		<td>출발지/도착지 : KR/${returnInspOne.DSTN_NATION }</td>
			                    	 		<td>주문일자 : ${returnInspOne.ORDER_DATE}</td>
			                    	 		<td>
			                    	 			RETURN TRK COM : ${returnInspOne.RE_TRK_COM}
		                    	 			</td>
			                    	 		<td>
			                    	 			RETURN TRK NO  : ${returnInspOne.RE_TRK_NO}
			                    	 		</td>
		                    	 		</tr>
		                    	 		<tr>
			                    	 		<td>
			                    	 			위약반송 여부 : ${returnInspOne.TAX_TYPE }
			                    	 		</td>
			                    	 		<td>
			                    	 			발송 구분 : 주기 발송<%-- ${returnInspOne.RETURN_TYPE } --%> 
			                    	 		</td>
			                    	 		<td>
			                    	 			반품 사유 : 단순 변심<%-- ${returnInspOne.RETURN_REASON } --%>
			                    	 		</td>
			                    	 		<td>
			                    	 			반품 상세 사유 : ${returnInspOne.RETURN_REASON_DETAIL }
		                    	 			</td>
			                    	 		<%-- <td>
			                    	 			배송 메시지: ${returnInspOne.DELIVERY_MSG}
			                    	 		</td> --%>
			                    	 		<td colspan="2">
			                    	 			창고 입고 메시지 : ${returnInspOne.WARE_HOUSE_MSG }
			                    	 		</td>
			                    	 	</tr>
			                    	 	<tr>
			                    	 		<td colspan="6" style="text-align: center; background:#EBF5FF; font-weight:bold;">
			                    	 			반송 신청인 정보
			                    	 		</td>
			                    	 	</tr>
			                    	 	<tr>
			                    	 		<td>
			                    	 			반송인 : ${returnInspOne.PICKUP_NAME }
			                    	 		</td>
			                    	 		<td>
			                    	 			연락처1: ${returnInspOne.PICKUP_TEL }<br/>
			                    	 			연락처2: ${returnInspOne.PICKUP_MOBILE }
			                    	 		</td>
			                    	 		<td>
			                    	 			우편번호: ${returnInspOne.PICKUP_ZIP }
			                    	 		</td>
			                    	 		<td>
			                    	 			
			                    	 		</td>
			                    	 		<td>
			                    	 			주소: ${returnInspOne.PICKUP_ADDR }
			                    	 		</td>
			                    	 		<td>
			                    	 			영문 주소: ${returnInspOne.PICKUP_ENG_ADDR }
			                    	 		</td>
			                    	 	</tr>
			                    	 	<tr>
			                    	 		<td colspan="6" style="text-align: center; background:#EBF5FF; font-weight:bold;">
			                    	 			수취 업체 정보
			                    	 		</td>
			                    	 	</tr>
			                    	 	<tr>
			                    	 		<td>
			                    	 			업체명(수취인 명) : ${returnInspOne.SENDER_NAME }
			                    	 		</td>
			                    	 		
			                    	 		<td>
			                    	 			연락처1: ${returnInspOne.SENDER_TEL }<br/>
			                    	 			연락처2: ${returnInspOne.SENDER_HP }
			                    	 		</td>
			                    	 		<td>
			                    	 			우편번호: ${returnInspOne.SENDER_ZIP }
			                    	 		</td>
			                    	 		<td>E-MAIL : ${returnInspOne.SENDER_EMAIL}</td>
			                    	 		<td>
			                    	 			STATE : ${returnInspOne.SENDER_STATE }<br/>
			                    	 			CITY : ${returnInspOne.SENDER_CITY }
			                    	 		</td>
			                    	 		<td>
			                    	 			주소: ${returnInspOne.SENDER_ADDR } ${returnInspOne.SENDER_BUILD_NM}
			                    	 		</td>
			                    	 	</tr>
			                    	 	<tr>
			                    	 		<td colspan="6" style="text-align: center; background:#EBF5FF; font-weight:bold;">
			                    	 			상품 정보
			                    	 		</td>
			                    	 	</tr>
			                    	 	<tr>
			                    	 		<td colspan="6" style="vertical-align: middle;">
			                    	 			<div class="row">
			                    	 				<div class="col-xs-12">
					                    	 			<table class="table table-bordered table-hover containerTable">
					                    	 				<colgroup>
																<col width="25%"/>
															</colgroup>
															<input type="hidden" id="itemCnt" name="itemCnt" value="${fn:length(returnInspOne.orderItemList)}">
					                    	 				<c:forEach items="${returnInspOne.orderItemList}" var="inspItemList" varStatus="itemStatus">
						                    	 				<tr>
						                    	 					<td style="text-align: center;">
						                    	 						<span class="profile-picture">
						                    	 							<a href="${inspItemList.ITEM_URL }" target=”_blank” id="atag">
						                    	 								<%-- <img class="editable img-responsive" src="${inspItemList.ITEM_IMG_URL }" loading="lazy" onerror="this.style.display='none';"> --%>
						                    	 								<img class="editable img-responsive urlImg" src="${inspItemList.ITEM_IMG_URL }" loading="lazy" onerror="onImage()" >
						                    	 							</a>
					                    	 							</span>
						                    	 					</td>
						                    	 					<td  style="font-size: x-large;">
						                    	 						<div class="profile-user-info" style="font-size:x-large;">
						                    	 							<div class="profile-info-row">
						                    	 								<div class="profile-info-name">상품명 #${itemStatus.count }</div>
						                    	 								<div class="profile-info-value bigger-140">
						                    	 									${inspItemList.ITEM_DETAIL }
						                    	 								</div>
						                    	 							</div>
						                    	 							<div class="profile-info-row">
						                    	 								<div class="profile-info-name">Brand</div>
						                    	 								<div class="profile-info-value">
						                    	 									${inspItemList.BRAND }
						                    	 								</div>
						                    	 							</div>
					                    	 							</div>
					                    	 							<div class="profile-user-info " style="font-size:x-large;">
						                    	 							<div class="profile-info-row ">
						                    	 								<div class="profile-info-name">상품 수량</div>
						                    	 								<div class="profile-info-value">
					                    	 										<h1><label id="housCntActive" class="bigger-180 real-red">${inspItemList.ITEM_CNT }</label></h1>
					                    	 										<input type="hidden" name="whTotalItemCnt" value="${inspItemList.ITEM_CNT}"/>
						                    	 								</div>
						                    	 								<div class="profile-info-name">입고 수량</div>
						                    	 								<div class="profile-info-value">
					                    	 										<input class="housCntFont " numberOnly type="text" name="housCnt" id="housCnt" style="background-color: rgb(255,227,227); margin: auto;" placeholder="${inspItemList.ITEM_CNT}" value=""/>
						                    	 								</div>
						                    	 							</div>
					                    	 							</div>
					                    	 							<div class="profile-user-info ">
						                    	 							<div class="profile-info-row">
						                    	 								<div class="profile-info-name" style="font-size:x-large;">Item 크기</div>
						                    	 									<div class="profile-info-value" style="font-size: medium;">
						                    	 										<div class="row">
																							<div class="col-xs-12">
																								<div class="col-lg-4 col-xs-12">
																									<div class="col-lg-4 col-xs-12">WIDTH  : </div>
																									<div class="col-lg-8 col-xs-12">
																											<input type="text" floatOnly name="width" id="width${itemStatus.count}" value=""/>
																									</div>
																								</div>
																								<div class="col-xs-12 col-lg-4">
																									<div class="col-xs-12 col-lg-4">HEIGHT  : </div>
																									<div class="col-xs-12 col-lg-8">
																											<input type="text" floatOnly name="height" id="height${itemStatus.count}" value=""/>
																									</div>
																								</div>
																								<div class="col-xs-12 col-lg-4">
																									<div class="col-xs-12 col-lg-4">LENGTH  : </div>
																									<div class="col-xs-12 col-lg-8">
																										<input type="text" floatOnly name="length" id="length${itemStatus.count}" value=""/>
																									</div>
																								</div>
																							</div>
																							<div class="col-xs-12">
																								<div class="col-xs-12 col-lg-4">
																									<div class="col-xs-12 col-lg-4">WTA : </div>
																									<div class="col-xs-12 col-lg-8">
																											<input type="text" floatOnly name="wta" id="wta${itemStatus.count}" value=""/>
																									</div>
																								</div>
																								<div class="col-xs-12 col-lg-4">
																									<div class="col-xs-12 col-lg-4">PER  : </div>
																									<div class="col-xs-12 col-lg-8"><input type="text" floatOnly name="per" id="per${itemStatus.count}" value="5000"/>
																								</div>
																								</div>
																								<div class="col-xs-12 col-lg-4">
																									<div class="col-xs-12 col-lg-6">
																										길이 
																									<select name="dimUnit" id="dimUnit${itemStatus.count}" >
																										<option selected value="CM">CM</option>
																										<option value="IN">INCH</option>
																									</select>
																								</div>
																								<div class="col-xs-12 col-lg-6">
																										무게
																									<select name="wtUnit" id="wtUnit${status.count}" >
																												<option selected value="KG">KG</option>
																												<option value="LB">LB</option>
																									</select>
																								</div>
																							</div>
																						</div>
																					</div>
					                    	 									</div>
					                    	 								</div>
						                    	 						</div>
					                    	 							<div class="profile-user-info " style="font-size:x-large;">
						                    	 							<div class="profile-info-row">
						                    	 								<div class="profile-info-name">제조국</div>
						                    	 								<div class="profile-info-value">
						                    	 									${inspItemList.MAKE_CNTRY }
						                    	 								</div>
						                    	 							</div>
						                    	 							<div class="profile-info-row">
						                    	 								<div class="profile-info-name">제조사</div>
						                    	 								<div class="profile-info-value">
						                    	 									${inspItemList.MAKE_COM }
						                    	 								</div>
						                    	 							</div>
						                    	 						</div>
						                    	 						<div class="profile-user-info" name="fileFIled" style="font-size:medium; border-top:none;">
																			<div class="profile-info-row" >
																				<div class="profile-info-name">
																					<label class="bolder red bigger-150">이미지</label> 
																				</div>
																				<div class="profile-info-value">
																					<span class="red"> *미리보기</span>
																					<div>
																				        <div id="imgs_wrap${itemStatus.count}"class="imgs_wrap">
																				            <img id="img" />
																				        </div>
																				    </div>
																			        <div class="input_wrap">
																			            <a href="javascript:" onclick="fileUploadAction(${itemStatus.count});" class="my_button">이미지 등록</a>
																			            <input type="file" id="input_imgs${itemStatus.count}" name="input_imgs" multiple onChange="fileSize(this)"/>
																			            <input type="hidden" id="itemImgCnt${itemStatus.count}" name="itemImgCnt" value="1"/>
																			        </div>
																				</div>
																			</div>
																		</div>
																	</td>
																</tr>
															</c:forEach>
														</table>
													</div>
		                    	 					<div class="profile-user-info" style="font-size:large;">
														<div class="profile-info-row">
															<div class="profile-info-name"> 
																<label class="blue" style="font-size: large;">검수상태</label>
															</div>
															<div class="profile-info-value">
																<div class="control-group">
																	<div class="radio">
																		<label>
																			<input name="form-field-radio" type="radio" checked="true" class="ace input-lg" value="WO"/>
																			<span class="lbl bigger-120"> 정상입고</span>
																		</label>
																		<label>
																			<input name="form-field-radio" type="radio" class="ace input-lg" value="WF"/>
																			<span class="lbl bigger-120"> 검수 불합격</span>
																		</label>
																	</div>
																	<input type="hidden" name="whStatus" id="whStatus" >
																	<br/>
																</div>										
															</div>
														</div>
													</div>
		                    	 					<div class="profile-user-info hidden" name="failField" style="font-size:large;">
														<div class="profile-info-row">
															<div class="profile-info-name"> 
																<label class="blue" style="font-size: large;">검수 불합격 사유</label>
															</div>
															<div class="profile-info-value">
																<div class="control-group">
																	<div class="radio">
																		<label>
																			<input name="form-field-reason-radio" type="radio" class="ace input-lg" value="WF1"/>
																			<span class="lbl bigger-120">부분입고</span>
																		</label>
																		<label>
																			<input name="form-field-reason-radio" type="radio" class="ace input-lg" value="WF2"/>
																			<span class="lbl bigger-120">상품 파손</span>
																		</label>
																		<label>
																			<input name="form-field-reason-radio" type="radio" class="ace input-lg" value="WF3"/>
																			<span class="lbl bigger-120">기타</span>
																			<input class="hidden" type="text" id="failReason" name="failReason" value=""/>
																		</label>
																	</div>
																	<input type="hidden" name="whDetailStatus" id="whDetailStatus" >
																	<br/>
																</div>	
																									
															</div>
														</div>
													</div>
													
													<div class="profile-user-info" style="font-size:large;">
														<div class="profile-info-row">
															<div class="widget-box transparent" name="rackField">
																<div class="widget-header widget-header-small">
																	<h2 class="widget-title smaller">
																		<i class="ace-icon fa fa-check-square-o bigger-170"></i>
																		Rack 정보
																	</h2>
																</div>
																<div class="widget-body">
																	<div class="widget-main">
																		<input class="bigger-210 light-red" type="text" id="rack" name="rack" value="" placeholder="rack#1" style="background-color: yellow"/>
																	</div>
																</div>
															</div>
														</div>
													</div>
													
			                    	 				<div class="row">
														<div class="col-xs-12 col-sm-3 col-sm-offset-3">
															<button type="button" class="btn btn-lg" id="backBtn" name="backBtn">처음으로</button>
														</div>
														<div class="col-xs-12 col-sm-3">
															<button type="button" class="btn btn-lg btn-success" id="saveBtn" name="saveBtn">저장하기</button>
														</div>
													</div>
												</div>
											</td>
										</tr>
		                    	 	</table>
		                    	 </div>
							</form>
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
      <script src="/assets/js/bootstrap.min.js"></script>
      
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
      <script src="/assets/js/ace-elements.min.js"></script>
      <script src="/assets/js/ace.min.js"></script>
      <!-- script on paging end -->
      
      <!-- script addon start -->
		<script type="text/javascript">
			jQuery(function($) {
            	$(document).ready(function() {
               		$("#14thMenu-2").toggleClass('open');
               		$("#14thMenu-2").toggleClass('active'); 
               		$("#14thTwo-2").toggleClass('active');   
           		});

            	$(":input:radio[name=form-field-radio]").on('change',function(){
					if($(":input:radio[name=form-field-radio]:checked").val() == "WF"){
						$("[name=failField]").removeClass("hidden");
					}else if($(":input:radio[name=form-field-radio]:checked").val() == "WO"){
						$("[name=failField]").addClass("hidden");
					}
				});

            	$(":input:radio[name=form-field-reason-radio]").on('change',function(){
					if($(":input:radio[name=form-field-reason-radio]:checked").val() == "WF3"){
						$("#failReason").removeClass("hidden");
					}else if($(":input:radio[name=form-field-reason-radio]:checked").val() != "WF3"){
						$("#failReason").addClass("hidden");
					}
				});



            	function LoadingWithMask() {
               		var maskHeight = $(document).height();
               		var maskWidth = window.document.body.clientWidth;
               		var mask ="<div id='mask' style='position:absolute; z-index:9000; background-color:black; display:none; left:0; top:0;'></div>";
               		//화면에 레이어 추가
                	$('body').append(mask)
	                //마스크의 높이와 너비를 화면 것으로 만들어 전체 화면을 채웁니다.
    	            $('#mask').css({
						'width' : maskWidth,
						'height': maskHeight,
						'opacity' :'0.3'
                	});
	                //마스크 표시
					$('#mask').show();  
        	        //로딩중 이미지 표시
				}
         	});
	         $("#saveBtn").on("click",function(e){
	        	var passChk = 0;
				$("#whStatus").val($(":input:radio[name=form-field-radio]:checked").val());
				if($("#whStatus").val() == "WO"){
					$("[name=housCnt]").each(function(e){
						if($(this).val()==""){
							alert("입고수량이 비어있습니다.")
							$(this).focus();
							passChk = 1;
							return false;
						}else if ($(this).val() < $("[name=whTotalItemCnt]").get(e).value){
							alert("입고수량이 상품개수보다 작습니다.")
							$(this).focus();
							passChk = 1;
							return false;
						}else if ($(this).val() > $("[name=whTotalItemCnt]").get(e).value){
							alert("입고수량이 상품개수보다 많습니다.")
							$(this).focus();
							passChk = 1;
							return false;
						}
					})
					
					if($("#rack").val()==""){
						alert("rack 정보가 비어있습니다.")
						$(this).focus();
						passChk = 1;
					}
				}else{
					$("#whDetailStatus").val($(":input:radio[name=form-field-reason-radio]:checked").val());
					if($("#whDetailStatus").val() == ""){
						alert("검수 불합격 사유를 선택하세요.")
						$(this).focus();
						passChk = 1;
						return false;
					}
				}
				if(passChk == 1){
					return false;
				}
				$('#returnForm').attr("action","/mngr/aplctList/return/inspList/"+$("#orderReference").val());
				$('#returnForm').attr("method","post");
				$('#returnForm').submit();
			});
      </script>
      <!-- script addon end -->
		<script type="text/javascript">
			var sel_files = [];
      		var targetsNum = "";
      		var targetIdAdd = "";
      
			$(document).ready(function() {
				$("[name=input_imgs]").on("change", handleImgFileSelect);
			}); 

			function fileUploadAction(targetId) {
				targetsNum = targetId;
				/* $("#input_imgs"+targetId).val(""); */
			    $("#input_imgs"+targetId).trigger('click');
			}
    
			function handleImgFileSelect(e) {
			
			    // 이미지 정보들을 초기화
			    sel_files = [];
			    $("#imgs_wrap"+targetsNum).empty();
			    var files = e.target.files;
			    var filesArr = Array.prototype.slice.call(files);
				$("#itemImgCnt"+targetsNum).val(filesArr.length);
			    var index = 0;
			    filesArr.forEach(function(f) {
			        if(!f.type.match("image.*")) {
			            alert("확장자는 이미지 확장자만 가능합니다.");
			            return;
			        }
			
			        sel_files.push(f);
			
			        var reader = new FileReader();
			        reader.onload = function(e) {
			        	$("#itemImgName"+targetsNum).val($("#itemImgName"+targetsNum).val()+f.name+",");
			            var html = "<a href=\"javascript:void(0);\" id=\"img_id_"+index+"\"><img src=\"" + e.target.result + "\" data-file='"+f.name+"' class='selProductFile'/>";
			            $("#imgs_wrap"+targetsNum).append(html);
			            
			            index++;
			        }
			        reader.readAsDataURL(f);
			        
			    });
			}

			$(".registImage").on('click',function(e){
				targetIdAdd= $(this).attr("id");
			    $("#add_imgs"+$(this).attr("id")).trigger('click');
			})


			function deleteImageAction(index) {
			    sel_files.splice(index, 1);
			    //var img_id = "#img_id_"+index.id;
			    $("#"+index.id).remove();
			}

			function onImage() {
				$(".urlImg").attr("src", "/image/no-image-icon-0.jpg");
			}

      </script>
   </body>
</html>