<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<%@ include file="/WEB-INF/jsp/importFile/user/head.jsp" %>
	<style type="text/css">
	  .colorBlack {color:#000000 !important;}
	  
	  .noneArea {
			margin:0px !important;
			padding:0px !important;
			border-bottom : none;
		}
		
		.containerTable {
			width: 100% !important;
		}
		
		table ,tr td{
			word-break:break-all;
		}
		table ,tr td div {
			background-color: rgba(255,255,255,0) !important;border-right:1px solid #e0e0e0; 
		}
		
		table ,tr td div .rightBorder{
			background-color: rgba(255,255,255,0) !important;border-right:none;
		}
		table ,tr th div {
			background-color: rgba(255,255,255,0) !important;border-bottom:none; border-right:none;
		}
		
		table ,tr th div .rightBorder{
			background-color: rgba(255,255,255,0) !important;border-right:none;
		}
	 </style>
	<!-- basic scripts -->
	</head> 
	<title>검품배송 신청서</title>
	<body class="no-skin">
		<!-- headMenu Start -->
		<div class="site-wrap">
			<%@ include file="/WEB-INF/jsp/importFile/user/headMenu.jsp" %>
		<!-- headMenu End -->
		
		<!-- Main container Start-->
		<div class="toppn">
	      <div class="container">
	        <div class="row">
	          <div class="col-md-12 mb-0" style="font-size:14px !important;"><a>배송대행 신청</a> <span class="mx-2 mb-0">/</span> <strong class="text-black">검품배송 신청서</strong></div>
	        </div>
	      </div>
	    </div>
	    <!-- Main container Start-->
	     <div class="container">
		       <div class="page-content noneArea">
						<div class="page-header noneArea">
							<h3>
								검품배송 발송 완료
							</h3>
						</div>
						<form name="uploadForm" id="uploadForm" enctype="multipart/form-data" >
							<div id="inner-content-side" >
								<div id="search-div">
									<br>
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
									<div class="form-group">
										<div class="col-xs-12 col-md-3 form-group">
											<select id="formSelect" style="height: 34px;width:100%;">
												<%-- <c:forEach items="${trkComList}" var="trkList" varStatus="status">
													<option value="${trkList.transCode}"<c:if test = "${trkList.transCode eq selTransCode}"> selected </c:if>>${trkList.transName}</option>
												</c:forEach> --%>
												<option value="ARA">ARAMEX</option>
												<option selected value="YES">YESG2M</option>
											</select>
										</div>
										<div class="col-xs-12 col-md-9 form-group" style="text-align: right;">
											<!-- <button type="button" class="btn btn-sm btn-primary" name="printCheck" id="printCheck">
											 	송장 출력
											 ajax로 select 된거 값 확인 후 해당 jsp로 excel파일 읽어서 model에 담아서 return, return후 Data jstl로 표출
											</button> -->
											<button type="button" class="btn btn-sm btn-danger" name="delCheck" id="delCheck">
											 	삭제
											 <!-- ajax로 select 된거 값 확인 후 해당 jsp로 excel파일 읽어서 model에 담아서 return, return후 Data jstl로 표출 -->
											</button>
										</div>
									</div>
								</div>
								<div id="table-contents" class="table-contents">
									<div class="hr hr-8 dotted"></div>
									<div class="space-20"></div>
									
										<table id="dynamic-table" class="table table-bordered table-hover containerTable">
											<thead>
												<tr>
													<th class="center colorBlack">
														<label class="pos-rel">
															<input type="checkbox" class="ace"/>
															<span class="lbl"></span>
														</label>
													</th>
													<th class="colorBlack">
														<div class="row center no-padding" style="border-bottom:none;">
															<div class="col-xs-3  no-padding">
																상품 이미지
															</div>
															<div class="col-xs-9  no-padding" style="border-bottom:none;">
																<div class="col-xs-2">
																	주문 번호 / HAWB NO.
																</div>
																	
																<div class="col-xs-2">
																	출발지/도착지
																</div>
																<div class="col-xs-1">
																	주문 일자
																</div>
																<div class="col-xs-2">
																	수취인
																</div>
																<div class="col-xs-1">
																	Tel
																</div>
																<div class="col-xs-1">
																	우편번호
																</div>
																<div class="col-xs-3">
																	수취인 주소
																</div>
															</div>
														</div>
													</th>
												</tr>
											</thead>
											<tbody>
												<c:forEach items="${inspList}" var="inspAllList" varStatus="status">
													<tr	>
														<td class="center no-padding">
															<label class="pos-rel">																
																<input type="checkbox" class="form-field-checkbox" value="${inspAllList.value[0].nno}"/>
																<span class="lbl"></span>
															</label>
														</td>
														<td class="center colorBlack no-padding">
															<div class="row no-padding" style="background-color: rgba(255,255,255,0) !important;border-top:1px solid black; border-bottom:1px solid #e0e0e0; display: flex;">
																<div class="col-xs-3  no-padding">
																	<c:forEach items="${inspAllList.value}" var="inspList" varStatus="status">
																		<img src="${inspList.itemImgUrl}" style="width:150px;"><br/>
																	</c:forEach>
																</div>
																<div class="col-xs-9 no-padding">
																	<div class="row rightBorder no-padding" style="background-color: rgba(255,255,255,0) !important; border-bottom:1px solid #e0e0e0; display: flex;">
																		<div class="col-xs-2 ">
																			<a href="/cstmr/apply/modifyInfoInsp?nno=${inspAllList.value[0].nno}">${inspAllList.value[0].orderNo}</a> <br/>
																			<a href="/cstmr/apply/modifyInfoInsp?nno=${inspAllList.value[0].nno}">${inspAllList.value[0].hawbNo}</a>
																		</div>
																		<div class="col-xs-2">
																			${inspAllList.value[0].orgStationName}/${inspAllList.value[0].dstnNationName}
																		</div>
																		<div class="col-xs-1">
																			${inspAllList.value[0].orderDate}
																		</div>
																		<div class="col-xs-2">
																			${inspAllList.value[0].cneeName}
																		</div>
																		<div class="col-xs-1">
																			${inspAllList.value[0].cneeTel}
																		</div>
																		<div class="col-xs-1">
																			${inspAllList.value[0].cneeZip}
																		</div>
																		<div class="col-xs-3">
																			${inspAllList.value[0].cneeAddr} ${inspAllList.value[0].cneeAddrDetail}
																		</div>
																	</div>
																	<div class="row rightBorder no-padding" style="background-color: rgba(255,255,255,0) !important; border-bottom:1px solid #e0e0e0; display: flex;">
																		<div class="col-xs-2" style="font-weight:bold;">
																			HSCODE
																		</div>
																		<div class="col-xs-4" style="font-weight:bold;">
																			상품명 
																		</div>
																		<div class="col-xs-2" style="font-weight:bold;">
																			단가 
																		</div>
																		<div class="col-xs-2" style="font-weight:bold;">
																			수량
																		</div>
																		<div class="col-xs-2" style="font-weight:bold;">
																			금액
																		</div>
																	</div>
																	<c:forEach items="${inspAllList.value}" var="inspList" varStatus="status">
																		<div class="row rightBorder no-padding" style="background-color: rgba(255,255,255,0) !important; border-bottom:1px solid #e0e0e0; display: flex;">
																			<div class="col-xs-2 " >
																				${inspList.hsCode} 
																			</div>
																			<div class="col-xs-4" >
																				${inspList.itemDetail} 
																			</div>
																			<div class="col-xs-2" >
																				${inspList.unitValue} 
																			</div>
																			<div class="col-xs-2" >
																				${inspList.itemCnt}
																			</div>
																			<div class="col-xs-2" >
																				${inspList.itemValue}
																				<c:set var="itemTotalVal" value="${itemTotalVal+inspList.itemValue }"/>
																			</div>
																		</div>
																	</c:forEach>
																	<div class="row rightBorder no-padding" style="background-color: rgba(255,255,255,0) !important;border-top:1px solid #e0e0e0;border-bottom:1px solid #e0e0e0; display: flex;">
																		<div class="col-xs-2 col-xs-offset-8" >
																			총 금액
																		</div>
																		<div class="col-xs-2" >
																				${itemTotalVal}
																		</div>
																	</div>
																	<%-- <div class="row rightBorder no-padding" style="background-color: rgba(255,255,255,0) !important;border-top:1px solid #e0e0e0;border-bottom:1px solid #e0e0e0; display: flex;">
																		<div class="col-xs-12" >
																			오류 내역
																		</div>
																	</div>
																	<div class="row rightBorder no-padding" style="text-align:left; rgba(255,255,255,0) !important;border-top:1px solid #e0e0e0;border-bottom:1px solid #e0e0e0; display: flex;">
																		<div class="col-xs-12" >
																			<c:if test="${fn:contains(inspAllList.value[0].status,'TMP09')}">
																				<span class="red errorCol"> 이미 등록된 주문번호 입니다. <br /></span>
																			</c:if>
																			<c:if test="${fn:contains(inspAllList.value[0].status,'TMP01')}">
																				<span class="red errorCol"> 주문번호가 너무 깁니다.<br /></span>
																			</c:if>
																			<c:if test="${fn:contains(inspAllList.value[0].status,'TMP02')}">
																				<span class="red errorCol"> 송하인 우편번호가 너무 깁니다.(최대 8자리) <br /></span>
																			</c:if>
																			<c:if test="${fn:contains(inspAllList.value[0].status,'TMP03')}">
																				<span class="red errorCol"> 송하인 전화번호 양식을 확인해 주세요 <br /></span>
																			</c:if>
																			<c:if test="${fn:contains(inspAllList.value[0].status,'TMP04')}">
																				<span class="red errorCol"> 송하인 핸드폰번호 양식을 확인해 주세요 <br /></span>
																			</c:if>
																			<c:if test="${fn:contains(inspAllList.value[0].status,'TMP05')}">
																				<span class="red errorCol"> 수취인 연락처1을 확인해 주세요 <br /></span>
																			</c:if>
																			<c:if test="${fn:contains(inspAllList.value[0].status,'TMP06')}">
																				<span class="red errorCol"> 수취인 연락처2을 확인해 주세요 <br /></span>
																			</c:if>
																			<c:if test="${fn:contains(inspAllList.value[0].status,'TMP07')}">
																				<span class="red errorCol"> 수취인 우편주소 양식을 확인해 주세요.(최대 8자리)<br /></span>
																			</c:if> 
																			<c:if test="${!empty inspAllList.value[0].errName}">
																				<c:if test="${inspAllList.value[0].status ne 'A'}">
																					<span class="red errorCol">${inspAllList.value[0].errName}<br /></span>
																				</c:if> 
																			</c:if> 
																			<c:if test="${empty inspAllList.value[0].nativeCneeAddr}">
																				<span class="red errorCol"> 
																					수취인 주소(Jpn)를 확인해 주세요.<br />
																				</span>
																			</c:if> 
																			<c:if test="${empty inspAllList.value[0].cneeAddr}">
																				<span class="red errorCol"> 
																					수취인 주소를 확인해 주세요.<br />
																				</span>
																			</c:if>
																			<c:if test="${empty inspAllList.value[0].orgStationName}">
																				<span class="red errorCol"> 
																					출발지를 확인해 주세요.<br />
																				</span>
																			</c:if>
																			<c:if test="${empty inspAllList.value[0].dstnNationName}">
																				<span class="red errorCol"> 
																					도착지를 확인해 주세요.<br />
																				</span>
																			</c:if>
																			<c:if test="${empty inspAllList.value[0].nativeCneeName}">
																				<span class="red errorCol"> 
																					수취인(Jpn)를 확인해 주세요<br />
																				</span>
																			</c:if> 
																			<c:if test="${empty inspAllList.value[0].cneeName}">
																				<span class="red errorCol"> 
																					수취인 명을 확인해 주세요<br />
																				</span>
																			</c:if> 
																			<c:if test="${empty inspAllList.value[0].cneeTel}">
																				<span class="red errorCol"> 
																					Tel를 확인해 주세요<br />
																				</span>
																			</c:if> 
																			<c:if test="${empty inspAllList.value[0].cneeZip}">
																				<span class="red errorCol"> 	
																					우편번호 를 확인해 주세요<br />
																				</span>
																			</c:if> 
																			<c:if test="${inspAllList.value[0].boxCnt eq '0'}">
																				<span class="red errorCol"> 
																					BoxQty 를 확인해 주세요<br />
																				</span>
																			</c:if> 
																			<c:if test="${inspAllList.value[0].userWidth eq '0.0'}">
																				<span class="red errorCol"> 
																					width(Cm)를 확인해 주세요<br />
																				</span>
																			</c:if> 
																			<c:if test="${inspAllList.value[0].userLength eq '0.0'}">
																				<span class="red errorCol"> 
																					Length(Cm)를 확인해 주세요<br />
																				</span>
																			</c:if> 
																			<c:if test="${inspAllList.value[0].userHeight eq '0.0'}">
																				<span class="red errorCol"> 
																					Height(Cm)를 확인해 주세요<br />
																				</span>
																			</c:if> 
																			<c:if test="${inspAllList.value[0].userWta eq '0.0'}">
																				<span class="red errorCol"> 
																					실 무게를 확인해 주세요<br />
																				</span>
																			</c:if> 
																			<c:if test="${inspAllList.value[0].deliveryYn eq '0'}">
																				<span class="red errorCol"> 
																					운송 가능 국가가 아닙니다.<br />
																				</span>
																			</c:if> 
																			<c:forEach items="${inspAllList.value}" var="inspList" varStatus="status">
																				<c:if test="${empty inspList.itemDetail}">
																					<span class="red errorCol"> ${status.count}번 상품의 상품명를 확인해 주세요<br />
																					</span>
																				</c:if>
																				<c:if test="${empty inspList.itemMeterial}">
																					<span class="red errorCol"> 상품 재질를 확인해 주세요<br />
																					</span>
																				</c:if>
																				<c:if test="${inspList.itemCnt eq '0'}">
																					<span class="red errorCol"> ${status.count}번 상품의 상품 개수를 확인해 주세요<br />
																					</span>
																				</c:if>
																				<c:if test="${inspList.unitValue eq '0.0'}">
																					<span class="red errorCol"> ${status.count}번 상품의 단가를 확인해 주세요<br />
																					</span>
																				</c:if>
																				<c:if
																					test="${inspList.itemValue ne (inspList.itemCnt * inspList.unitValue)}">
																					<span class="red errorCol"> ${status.count}번 상품의 Total value가 다릅니다.<br />
																					</span>
																				</c:if>
																				<c:if test="${empty inspList.makeCntry}">
																					<span class="red errorCol"> 제조국를 확인해 주세요<br />
																					</span>
																				</c:if>
																			</c:forEach>
																		</div>
																	</div> --%>
																</div>
															</div>
															
														</td>
													</tr>
												</c:forEach>
											</tbody>
										</table>
									
										<%-- <table id="dynamic-table" class="table table-bordered table-hover containerTable">
											<colgroup>
												<col width="50"/>
											</colgroup>
											<thead>
												<tr>
													<th class="center">
														<label class="pos-rel">
															<input type="checkbox" class="ace"/>
															<span class="lbl"></span>
														</label>
													</th>
													<th class="center colorBlack">
														비고
													</th>
													<th class="center colorBlack">
														User ID
													</th>
													<th class="center colorBlack">
														주문 번호
													</th>
													<th class="center colorBlack">
														운송장 번호
													</th>
													<th class="center colorBlack">
														거래처 명
													</th>
													<th class="center colorBlack">
														주문 일자
													</th>
													<th class="center colorBlack">
														수취인
													</th>
													<th class="center colorBlack">
														Tel
													</th>
													<th class="center colorBlack">
														우편번호
													</th>
													<th class="center colorBlack">
														수취인 주소
													</th>
													<th class="center colorBlack">
														상세
													</th>
													<th class="center colorBlack">
														HS CODE
													</th>
													<th class="center colorBlack">
														상품명
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
												<c:forEach items="${inspList}" var="inspAllList" varStatus="status">
													<tr>
														<td class="center">
															<label class="pos-rel">																
																<input type="checkbox" class="form-field-checkbox" value="${inspAllList.value[0].nno}"/>
																<span class="lbl"></span>
															</label>
														</td>
														<td class="center colorBlack">
														<c:choose>
															<c:when test = "${inspAllList.value[0].status eq 'A'}">
																<span class="blue">
																	이미 입고된 주문 입니다.<br/>삭제가 불가능 합니다.
																</span>
															</c:when>
															<c:otherwise>
																
															</c:otherwise>
														</c:choose>
														
														</td>
														<td class="center colorBlack" >
															<c:choose>
																	<c:when test = "${inspAllList.value[0].status eq 'A'}">
																		<span class="red">${inspAllList.value[0].userId}</span>
																	</c:when>
																	<c:otherwise>
																		${inspAllList.value[0].userId}
																	</c:otherwise>
																</c:choose>
														</td>
														<td class="center colorBlack" >
															<a href="/cstmr/apply/modifyRegistInfoInsp?types=insp&nno=${inspAllList.value[0].nno}">${inspAllList.value[0].orderNo}</a>
														</td>
														<td class="center colorBlack" >
															${inspAllList.value[0].hawbNo}
														</td>
														<td class="center colorBlack" >
															${inspAllList.value[0].companyName}
														</td>
														<td class="center colorBlack" >
															${inspAllList.value[0].orderDate}
														</td>
														<td class="center colorBlack" >
															${inspAllList.value[0].cneeName}
														</td>
														<td class="center colorBlack" >
															${inspAllList.value[0].cneeTel}
														</td>
														<td class="center colorBlack" >
															${inspAllList.value[0].cneeZip}
														</td>
														<td class="center colorBlack" >
															${inspAllList.value[0].cneeAddr}
														</td>
														<td class="center colorBlack" >
															${inspAllList.value[0].cneeAddrDetail}
														</td>
														<td class="center colorBlack" >
															<c:forEach items="${inspAllList.value}" var="inspList" varStatus="status">
																${inspList.hsCode}<br/>
															</c:forEach>
														</td>
														<td class="center colorBlack">
															<c:forEach items="${inspAllList.value}" var="inspList" varStatus="status">
																${inspList.itemDetail}<br/>
															</c:forEach>
														</td>
														<td class="center colorBlack">
															<c:forEach items="${inspAllList.value}" var="inspList" varStatus="status">
																${inspList.unitValue}<br/>
															</c:forEach>
														</td>
														<td class="center colorBlack">
															<c:forEach items="${inspAllList.value}" var="inspList" varStatus="status">
																${inspList.itemCnt}<br/>
															</c:forEach>
														</td>
														<td class="center colorBlack">
															<c:forEach items="${inspAllList.value}" var="inspList" varStatus="status">
																${inspList.itemValue}<br/>
															</c:forEach>
														</td>
													</tr>	
												</c:forEach>
											</tbody>
										</table> --%>
									</div>
								</div>
							</form>
						</div>
							<input type="hidden" name="TEST" id="TEST" >
						
			</div>
		</div>
		
		
			
		
		<!-- Footer Start -->
		<%@ include file="/WEB-INF/jsp/importFile/footer.jsp" %>
		<!-- Footer End -->
		<script src="/assets/js/jquery-2.1.4.min.js"></script>
		<!-- <script src="/testView/js/jquery-3.3.1.min.js"></script> -->
		<script src="/testView/js/jquery-ui.js"></script>
		<script src="/testView/js/popper.min.js"></script>
		<!-- <script src="/testView/js/bootstrap.min.js"></script> -->
		<script src="/assets/js/bootstrap.min.js"></script>
		<script src="/testView/js/owl.carousel.min.js"></script>
		<script src="/testView/js/jquery.magnific-popup.min.js"></script>
		<script src="/testView/js/aos.js"></script>
		
		<script src="/testView/js/main.js"></script>
		
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
		<!-- ace scripts -->
		<script src="/assets/js/ace-elements.min.js"></script>
		<script src="/assets/js/ace.min.js"></script>
		<!-- script on paging end -->
		
		
		<script type="text/javascript">
			jQuery(function($) {
				$(document).ready(function() {
					$("#forthMenu").toggleClass('open');
					$("#forthMenu").toggleClass('active'); 
					$("#4thOne").toggleClass('active');
				});
				//select target form change
				$('#formSelect').on('change', function(){
						location.href="/cstmr/apply/inspSendAfter?transCode="+$(this).val();
				});

				function LoadingWithMask() {
				    //화면의 높이와 너비를 구합니다.
				    var maskHeight = $(document).height();
				    var maskWidth  = window.document.body.clientWidth;
				     
				    //화면에 출력할 마스크를 설정해줍니다.
				    var mask       ="<div id='mask' style='position:absolute; z-index:9000; background-color:black; display:none; left:0; top:0;'></div>";
				  
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

				$('#printCheck').on('click',function(e){
					var targets = new Array();
					$('#dynamic-table').find('tbody > tr > td input[type=checkbox]').each(function(){
						var row = $(this).closest('tr').get(0);
						var td_checked = this.checked;
						if(td_checked){
							targets.push($(this).val());
						}
					});
					$("#TEST").val(targets);
					window.open("/comn/printHawb?targetInfo="+targets+"&formType="+$("#formSelect").val(),"popForm","_blank");
					/* window.open("/comn/printHawb?targetInfo="+$("#TEST").val()+"&formType="+$("#formSelect").val(),"popForm","_blank"); */
				})
				
				$('#delCheck').on('click',function(e){
					LoadingWithMask();
					var targets = new Array();
					$('#dynamic-table').find('tbody > tr > td input[type=checkbox]').each(function(){
						var row = $(this).closest('tr').get(0);
						var td_checked = this.checked;
						if(td_checked){
							targets.push($(this).val());
						}
					});
					if(targets.length==0){
						alert("삭제 대상이 없습니다.");
						$('#mask').hide();  
						return false;
					}
					$.ajax({
						url:'/cstmr/apply/delOrderList',
						type: 'POST',
						beforeSend : function(xhr)
						{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
						    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
						},
						traditional : true,
						data: {
							targets : targets
						},
						success : function(data) {
							if(data == "S"){
								alert("삭제 되었습니다.")
								location.href="/cstmr/apply/inspSendAfter?transCode=ARA";
							}else if(data == "F"){
								alert("삭제중 오류가 발생하였습니다. 관리자에게 문의해 주세요.")
							}
			            }, 		    
			            error : function(xhr, status) {
			                alert(xhr + " : " + status);
			                alert("삭제에 실패 하였습니다. 입력정보를 확인해 주세요.");
			            }
					})
					
				})
				

				var myTable = 
					$('#dynamic-table')
					.DataTable( {
						"dom": 'lt',
						"paging":   false,
				        "ordering": false,
				        "info":     false,
				        "searching": false,
				        "autoWidth": false,
				        "scrollY": 500,
						select: {
							style: 'multi',
							selector:'td:first-child'
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
							if(th_checked) {
								if(!row.children[0].firstElementChild.firstElementChild.disabled)
									myTable.row(row).select();
							}
							else  myTable.row(row).deselect();
						});
					});
			})
		</script>
		<!-- script addon end -->
	</body>
</html>