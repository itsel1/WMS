<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
	<style>
	.button {
		background-color: rgb(67,142,185); /* Green */
		border: none;
		color: white;
		padding: 0px;
		text-align: center;
		text-decoration: none;
		display: inline-block;
		font-size: 12px;
		margin: 4px 2px;
		cursor: pointer;
		-webkit-transition-duration: 0.4s; /* Safari */
		transition-duration: 0.4s;
		width: 100px;
		height: 30px;
	}
   
	.button2:hover {
		box-shadow: 0 12px 16px 0 rgba(0,0,0,0.24),0 17px 50px 0 rgba(0,0,0,0.19);
   	}
   	
   	.td-style {
   		background-color:#ffffff;
   	}

	.mailBtn {
		padding: 5px 10px;
		border: 1px solid #ccc;
		margin-bottom: 5px;
		box-shadow: 2px 2px 2px #ccc;
		font-weight: bold;
	}
	
	.mailBtn:active {
		box-shadow : none;
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
	<title>반품 재고 리스트</title>
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
							<li class="active">반품 검수 재고 리스트</li>
						</ul><!-- /.breadcrumb -->
					</div>
	               
					<div class="page-content">
						<div class="page-header">
							<h1>
								반품 검수 재고 리스트
							</h1>
						</div>
						<div id="inner-content-side" >
							<form name="returnForm" id="returnForm">
		                    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		                    	<div id="search-div">
									<div class="row">
										<span class="input-group" style="display: inline-flex; margin-left:12px;">
											<select name="searchType" id="searchType" style="height: 34px;">
												<option <c:if test="${searchType eq 'reTrkNo' }"> selected </c:if>value="reTrkNo">반송장번호</option>
												<option <c:if test="${searchType eq 'koblNo'}"> selected </c:if>value="koblNo">원송장번호</option>
												<option <c:if test="${searchType eq 'userId'}"> selected </c:if>value="userId">Seller ID</option>
											</select>
											<input type="text" class="form-control" name="keywords" value="${searchKeyword}" style="width:100%; max-width:300px;" />
											<button type="submit" class="btn btn-default no-border btn-sm">
												<i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
											</button>
										</span>
									</div>
								</div>
								<br/>
								<div>
									<input type="button" class="mailBtn" id="sendMail" value="용성 메일 전송"/>
									<!-- <input type="button" class="mailBtn" id="downExcel" value="용성 파일 다운로드"/>
									<input type="button" class="mailBtn" id="sendMail" style="margin-left:3px;" value="용성 메일 전송"/> -->
								</div>
		                    	<div id="table-contents" class="table-contents" style="width:100%; margin-top:5px;">
		                    		<table id="dynamic-table" class="table table-bordered table-hover containerTable">
		                    			<thead>
		                    				<tr style="border-bottom: 3px solid rgba(204, 204, 204, 0.8) !important;">
		                    					<th class="center colorBlack" >
		                    						<label class="pos-rel">
															<input type="checkbox" class="ace" id="check-all"/> <span class="lbl"></span>
													</label>
		                    					</th>
		                    					<th class="center colorBlack" style="width:130px;">상품 이미지</th>
		                    					<th class="center colorBlack" style="width:160px;">원송장번호</th>
		                    					<th class="center colorBlack" style="width:160px;">반송장번호</th>
		                    					<th class="center colorBlack" style="width:140px;">출발지 / 도착지</th>
		                    					<th class="center colorBlack" style="width:180px;">SELLER ID</th>
		                    					<th class="center colorBlack" style="width:120px;">주문 일자</th>
		                    					<th class="center colorBlack" style="width:100px;">환급여부</th>
		                    					<th class="center colorBlack" style="width:120px;">수취인 Tel</th>
		                    					<th class="center colorBlack" style="width:100px;">우편번호</th>
		                    					<th class="center colorBlack" colspan="4">수취인 주소</th>
		                    					<th class="center colorBlack" style="width:140px;"></th>
		                    				</tr>
		                    			</thead>
		                    			<tbody>
		                    				<c:choose>
		                    					<c:when test="${!empty returnInspList}">
				                    				<c:forEach items="${returnInspList}" var="inspList" varStatus="status">
				                    					<c:set var="listSize" value="${fn:length(inspList.orderItemList)}"/>
				                    					<tr>
				                    						<td class="center" rowspan="${listSize+2}" style="vertical-align:middle;">
				                    							<label class="pos-rel">
				                    								<c:if test="${inspList.STATE eq 'C003'}">
				                    								</c:if>
				                    								<c:if test="${inspList.STATE eq 'C004'}">
				                    									<input type="checkbox" class="ace chk" value="${inspList.NNO}"/>
				                    								</c:if>
																	<span class="lbl"></span>
																</label>
				                    						</td>
				                    						<td style="text-align:center;" rowspan="${listSize+2}">
				                    							<img class="urlImg" src="${inspItemList.ITEM_IMG_URL}" style="width:110px;" onerror="this.src='/image/No_image_available.png';" >		
				                    						</td>
				                    						<td style="text-align:center; vertical-align:middle;">
				                    							<a href="/mngr/aplctList/return/detail/${inspList.ORDER_REFERENCE}" style="font-weight:bold;">${inspList.KOBL_NO}</a>
				                    						</td>
				                    						<td style="text-align:center; vertical-align:middle;">
				                    							<a href="/mngr/aplctList/return/detail/${inspList.ORDER_REFERENCE}" style="font-weight:bold;">${inspList.RE_TRK_NO}</a>
				                    						</td>
				                    						<td style="text-align:center; vertical-align:middle;">
				                    							${inspList.STATION_NAME} / ${inspList.NATION_NAME}
				                    						</td>
				                    						<td style="text-align:center; vertical-align:middle;">
				                    							${inspList.SELLER_ID}
				                    						</td>
				                    						<td style="text-align:center; vertical-align:middle;">
				                    							${inspList.ORDER_DATE}
				                    						</td>
				                    						<td style="text-align:center; vertical-align:middle;">
				                    							${inspList.TAX_TYPE}
				                    						</td>
				                    						<td style="text-align:center; vertical-align:middle;">
				                    							${inspList.SENDER_TEL}
				                    						</td>
				                    						<td style="text-align:center; vertical-align:middle;">
				                    							${inspList.SENDER_ZIP}
				                    						</td>
				                    						<td colspan="4" style="text-align:center; vertical-align:middle;">
				                    							${inspList.SENDER_ADDR} ${inspList.SENDER_ADDR_DETAIL}
				                    						</td>
				                    						<td rowspan="${listSize+2}" style="text-align:center; vertical-align: middle;">
				                    							<c:if test="${inspList.STATE eq 'C003'}">
				                    								<input type="button" class="btn btn-sm btn-primary btn-white" onclick="javascript:fn_pdfPopup('${inspList.GROUP_IDX}','${inspList.NNO}')" value="재고출력" style="margin-bottom:4px;"/>
				                    								<br/>
				                    								<font color="red">승인 대기</font>
				                    							</c:if>
				                    							<c:if test="${inspList.STATE eq 'C004'}">
				                    								<input type="button" class="btn btn-sm btn-primary btn-white" onclick="javascript:fn_pdfPopup('${inspList.GROUP_IDX}','${inspList.NNO}')" value="재고출력" style="margin-bottom:4px;"/>
				                    								<br/>
				                    								<input class="btn btn-sm btn-success btn-white" id="btnReturnInsp" name="BtnReturnInsp" onclick="inspOut('${inspList.ORDER_REFERENCE}')" type="button" value="출고하기"/>
				                    								<%-- <input class="btn btn-sm btn-success btn-white" id="btnReturnInsp" name="BtnReturnInsp" onclick="inspOut('${inspList.ORDER_REFERENCE}')" type="button" value="출고하기"/> --%>
				                    							</c:if>
				                    						</td>
				                    					</tr>
				                    					<c:set var="total" value="0"/>
				                    					<c:forEach items="${inspList.orderItemList}" var="inspItemList" varStatus="varStatus">
					                    					<c:set var="unitList" value="${fn:split(inspItemList.UNIT_VALUE,'|')}" />
					                    					<c:set var="detailList" value="${fn:split(inspItemList.ITEM_DETAIL,'|')}" />
					                    					<c:set var="cntList" value="${fn:split(inspItemList.ITEM_CNT,'|')}" />
					                    					<c:set var="currency" value="${inspItemList.UNIT_CURRENCY}"/>
					                    					
					                    					<c:forEach items="${detailList}" var="detailInfo" varStatus="status">
				                    							<tr>
				                    								<td colspan="9" style="text-align:center; vertical-align:middle;">
				                    									<div class="row">
				                    										<div class="col-xs-4">
				                    											<div class="row">
				                    												<div class="col-xs-2" style="font-weight:bold;">
				                    													상품명 :
				                    												</div>
				                    												<div class="col-xs-10 text-left" style="white-space:nowrap; overflow:hidden; text-overflow:ellipsis;">
				                    													${detailInfo}
				                    												</div>
				                    											</div>
				                    										</div>
				                    										<div class="col-xs-2">
				                    											<div class="row">
				                    												<div class="col-xs-4" style="font-weight:bold;">
				                    													단가 :
				                    												</div>
				                    												<div class="col-xs-8 text-left">
				                    													${unitList[status.index]}
				                    												</div>
				                    											</div>
				                    										</div>
				                    										<div class="col-xs-2">
				                    											<div class="row">
				                    												<div class="col-xs-4" style="font-weight:bold;">
				                    													수량 :
				                    												</div>
				                    												<div class="col-xs-8 text-left">
				                    													${cntList[status.index]}
				                    												</div>
				                    											</div>
				                    										</div>
				                    										<div class="col-xs-2">
				                    											<div class="row">
				                    												<div class="col-xs-4" style="font-weight:bold;">
				                    													금액 :
				                    												</div>
				                    												<div class="col-xs-8 text-left">
				                    													${cntList[status.index]*unitList[status.index]}
				                    												</div>
				                    											</div>
				                    											<c:set var="total" value="${total+(cntList[status.index]*unitList[status.index])}"/>
				                    										</div>
				                    									</div>
				                    								</td>
				                    							</tr>
						                    				</c:forEach>
			                    						</c:forEach>
				                    					<tr>
		                    								<td colspan="9">
		                    									<div class="col-xs-12" style="text-align:right;">
		                   											<div class="row">
		                   												<div class="col-xs-12">
		                   													<font style="font-weight:bold;">총 금액&nbsp;&nbsp;:&nbsp;&nbsp;&nbsp;&nbsp;</font>
		                   													<fmt:formatNumber type="number" maxFractionDigits="3" value="${total}"/>
		                   													<c:out value="${currency}"/>
		                   												</div>
		                   											</div>
		                   										</div>
		                    								</td>
		                    							</tr>
				                    				</c:forEach>
				                    			</c:when>
				                    			<c:otherwise>
				                    				<tr>
				                    					<td colspan="12" class="center">
				                    						검색 결과가 없습니다
				                    					</td>
				                    				</tr>
				                    			</c:otherwise>
		                    				</c:choose>
		                    			</tbody>
		                    		</table>
		                    	</div>
							</form>
							<form id="targetForm" id="targetForm">
		                    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		                    	<input type="hidden" name="targetInfos" id="targetInfos" value="" />
							</form>
	               			<%@ include file="/WEB-INF/jsp/importFile/pagingNav.jsp" %>
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
      <!-- excel upload 추가 -->
         jQuery(function($) {
            $(document).ready(function() {
               $("#14thMenu-2").toggleClass('open');
               $("#14thMenu-2").toggleClass('active'); 
               $("#14thTwo-3-1").toggleClass('active');   
            });

            function LoadingWithMask() {
               var maskHeight = $(document).height();
               var maskWidth = window.document.body.clientWidth;

               var mask ="<div id='mask' style='position:absolute; z-index:9000; background-color:black; display:none; left:0; top:0;'></div>";
                 
                //화면에 레이어 추가
                $('body')
                    .append(mask)
                    
                //마스크의 높이와 너비를 화면 것으로 만들어 전체 화면을 채웁니다.
                $('#mask').css({
                        'width' : maskWidth
                        ,'height': maskHeight
                        ,'opacity' :'0.3'
                });
              
                //마스크 표시
				$('#mask').show();  
                //로딩중 이미지 표시
			}

           /*  $("#btnReturnInsp").on('click',function(e){
                alert("ASD");
                
				//location.href="/asd";
			}) */

			$("#check-all").on('click', function() {
				$(".chk").prop('checked', this.checked);
			})

			$("#downExcel").on('click', function() {
				var str = "";
				$("tbody input[type='checkbox']:checked").each(function(index) {
					str += $(this).val() + ",";
				});

				if (str == "") {
					alert("최소 1개의 반품을 선택해 주세요.");
					return false;
				}

				$("#targetInfos").val(str);
				$("#targetForm").attr("action", "/mngr/aplctList/return/japanExcelDown");
				$("#targetForm").attr("method", "get");
				$("#targetForm").submit();
			})

			$("#sendMail").on('click', function() {
				$("#returnForm").attr("action", "/mngr/aplctList/return/sendItemCode");
				$("#returnForm").attr("method", "get");
				$("#returnForm").submit();
			})
			
         });


         

         function inspOut(orderReference){
        	 location.href="/mngr/aplctList/return/inspListOut/"+orderReference;
         }

         function fn_pdfPopup(groupIdx, nno){
				$("#groupIdx").val(groupIdx);
				$("#nno").val(nno);
				window.open("/mngr/aplctList/return/pdfPopup?nno="+nno+"&groupIdx="+groupIdx,"_blank","toolbar=yes","resizable=no","directories=no","width=480, height=360");
			}

	

      </script>
      <!-- script addon end -->

   </body>
</html>