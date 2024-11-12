<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
	<style type="text/css">

        input[type=file] {
            display: none;
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
        
        .my_button2 {
            display: inline-block;
            width: 100px;
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
        }
        .profile-info-value{
        	word-break:break-all;
        }
        
        .verticalM{
        	vertical-align: middle !important;
        }
        
        .boxFont{
        	width: 80px !important;
        	border:1px solid #cccccc !important;
        	vertical-align:middle !important;
        	background-color:#F5F6CE !important;
        	font-size:40pt !important;
        	font:bold !important;
        	color:#000000 !important;
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
        
        .profile-info-name{
        	border-right: 1px dotted #D5E4F1;
        }
        
        .wfInput{
        	background-color: rgb(255,227,227);
        }
        
        .woInput{
        	background-color: rgb(227,255,227);
        	
        }

    </style>
	</head> 
	<body class="no-skin">
		<!-- JSTL로 받아서 동적으로 뿌리기, Event도 동적으로 적용 예정 -->
		<div id="detail-contents" class="detail-contents">
			<div id="home" class="tab-pane in active">
				<form id="orderInspForm" name="orderInspForm" enctype="multipart/form-data">
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<div class="row">
					<div class="col-xs-12 col-sm-12">
						<h2 class="pink2">
								<span class="bigger-140">${orderInspList.hawbNo}</span>
								<input type="hidden" id="nno" name="nno" value="${orderInspList.nno}"/>
						</h2>
						<div class="profile-user-info profile-user-info-striped bigger-140">
							<div class="profile-info-row">
								<div class="profile-info-name width-15">User ID</div>
	
								<div class="profile-info-value width-35">
									<span>${orderInspList.userId}</span>
									<input type="hidden" id="userId" name="userId" value="${orderInspList.userId}"/>
								</div>
								<div class="profile-info-name width-15">Order No</div>
	
								<div class="profile-info-value width-45">
									<span>${orderInspList.orderNo}</span>
								</div>
							</div>
							<div class="profile-info-row">
								<div class="profile-info-name width-15">Cnee Name</div>
								<div class="profile-info-value width-35">
									<span>${orderInspList.cneeName}</span>
									<input type="hidden" id="cneeName" name="cneeName" value="${orderInspList.cneeName}">
								</div>
								<div class="profile-info-name width-15">Cnee Tel</div>
								
								<div class="profile-info-value width-35">
									<span>${orderInspList.cneeTel}</span>
								</div>
							</div>
							<div class="profile-info-row">
								<div class="profile-info-name width-15">입고시 메모사항</div>
	
								<div class="profile-info-value width-35">
									<span class="light-red bigger-200" style="background-color: yellow;">
										<c:if test ="${empty orderInspList.whReqMsg }">
											주문자 입고 메모가 없습니다.
										</c:if>
										<c:if test ="${!empty orderInspList.whReqMsg }">
											${orderInspList.whReqMsg }
										</c:if>
									</span>
								</div>
								<div class="profile-info-name width-15">Cnee Address</div>
	
								<div class="profile-info-value width-35">
									<span>${orderInspList.cneeAddr}</span>
									<span>${orderInspList.cneeAddrDetail}</span>
								</div>
							</div>
						</div>
					</div>
				</div>
				<div class="space-20"></div>
				<input type="hidden" id="itemCnt" name="itemCnt" value="${fn:length(orderInspList.orderInspItem)}">
				<c:forEach items="${orderInspList.orderInspItem}" var="orderInspVO" varStatus="status">
					<div class="row">
						<div class="col-xs-12 col-sm-3 center">
							<span class="profile-picture">
							<a href="" target="_blank">
								<img class="editable img-responsive" alt="productImage" id="avatar2" src="${orderInspVO.itemImgUrl }" />
							</a>
							</span>
						</div><!-- /.col -->
	
						<div class="col-xs-12 col-sm-9">
							<c:if test="${orderInspVO.stockYn ne 0}">
									<label class="red h4" style="font-weight: bold;">추가입고 출고 대상</label>
							</c:if>
							<h4 class="green" style="cursor: pointer; "onclick="javascript:fn_test('${status.count}')">
								<input type="hidden" id="subNo${orderInspVO.subNo}" name="subNo" value="${orderInspVO.subNo}">
								
									<input type="checkbox" id="rgstChk${orderInspVO.subNo}"
										<c:choose>
											<c:when test="${orderInspVO.stockAllCol[0].whTotalCnt >= orderInspVO.itemCnt}">
												disabled	
											</c:when>
											<c:when test="${empty orderInspVO.stockAllCol[0].whTotalCnt}">
												checked
											</c:when>
											<c:otherwise>
												
											</c:otherwise>
										</c:choose>
											
									name="rgstChk" value="${orderInspVO.subNo}"/>
								
								<input type="hidden" id="rgstYN${orderInspVO.subNo}" name="rgstYN" 
								<c:choose>
									<c:when test="${orderInspVO.stockAllCol[0].whTotalCnt >= orderInspVO.itemCnt}">
										value=""
									</c:when>
									<c:when test="${empty orderInspVO.stockAllCol[0].whTotalCnt}">
										value="Y"
									</c:when>
									<c:otherwise>
										value=""
									</c:otherwise>
								</c:choose>
								/>
								<span class="middle label label-purple arrowed-in-right" >
									상품명${orderInspVO.subNo}
								</span>
								<span class="bigger-130">
								<%-- <input type="text" value="${orderInspVO.itemDetail}"> --%>
								${orderInspVO.itemDetail}  
								<input type="hidden" id="itemDetail${status.count}" name="itemDetail" value="${orderInspVO.itemDetail}">
								</span>
							</h4>
							
							<div class="profile-user-info " style="font-size:medium;">
								<div class="profile-info-row">
									<div class="profile-info-name"> Brand </div>
	
									<div class="profile-info-value">
										<span> ${orderInspVO.brand}  </span>
										<input type="hidden" id="brand${status.count}" name="brand" value="${orderInspVO.brand}">
									</div>
								</div>
								<div class="profile-info-row ">
									<div class="profile-info-name"> 입고수량 </div>
									<div class="profile-info-value" style="vertical-align: middle;">
									<input class="housCntFont" numberOnly type="text" name="housCnt" id="housCnt${status.count}" onchange="javascript:fn_containValue(this,${orderInspVO.itemCnt},${status.count})"
									<c:choose>
										<c:when test="${orderInspVO.stockAllCol[0].whTotalCnt >= orderInspVO.itemCnt}">
											readonly
											value="${orderInspVO.stockAllCol[0].whTotalCnt}"
										</c:when>
										<c:otherwise>
											<c:if test="${empty orderInspVO.stockAllCol[0].whTotalCnt}"> style="background-color: rgb(255,227,227)" placeholder="${orderInspVO.itemCnt}" value="" </c:if>
											<c:if test="${!empty orderInspVO.stockAllCol[0].whTotalCnt}"> style="background-color: rgb(227,255,227)" value="" </c:if>
										</c:otherwise>
									</c:choose>
									/>
										<%-- <span> 수량 / ${orderInspVO.housCnt} </span> --%>
									</div>
									<div class="profile-info-name">상품개수</div>
	
									<div class="profile-info-value">
										<h1> <label id="housCntActive" class="bigger-180 real-red">
										${orderInspVO.stockAllCol[0].whTotalCnt}
										</label> <label class="bigger-180 real-red">/ ${orderInspVO.itemCnt}</label> </h1>
										<input type="hidden" name="whTotalItemCnt" value="${orderInspVO.itemCnt}"/>
									</div>
								</div>
							</div>
							<div class="hr hr-8 dotted"></div>
							<div class="profile-user-info" style="font-size:medium; border-top:none;">
								<div class="profile-info-row ">
									<div class="profile-info-name "> Item 크기 </div>
									<div class="profile-info-value">
										<div class="row">
											<div class="col-xs-12">
												<div class="col-lg-4 col-xs-12">
													<div class="col-lg-4 col-xs-12">WIDTH  : </div>
													<div class="col-lg-8 col-xs-12">
														<c:if test="${!empty orderInspVO.stockAllCol[0].width}">
															<input type="text" floatOnly name="width" id="width${status.count}" value="${orderInspVO.stockAllCol[0].width}"/>
														</c:if>
														<c:if test="${empty orderInspVO.stockAllCol[0].width}">
															<input type="text" floatOnly name="width" id="width${status.count}" value="${orderInspVO.cusItemVO.width}"/>
														</c:if>
													</div>
												</div>
												<div class="col-xs-12 col-lg-4">
													<div class="col-xs-12 col-lg-4">HEIGHT  : </div>
													<div class="col-xs-12 col-lg-8">
														<c:if test="${!empty orderInspVO.stockAllCol[0].height}">
															<input type="text" floatOnly name="height" id="height${status.count}" value="${orderInspVO.stockAllCol[0].height}"/>
														</c:if>
														<c:if test="${empty orderInspVO.stockAllCol[0].width}">
															<input type="text" floatOnly name="height" id="height${status.count}" value="${orderInspVO.cusItemVO.height}"/>
														</c:if>
													</div>
												</div>
												<div class="col-xs-12 col-lg-4">
													<div class="col-xs-12 col-lg-4">LENGTH  : </div>
													<div class="col-xs-12 col-lg-8">
														<c:if test="${!empty orderInspVO.stockAllCol[0].length}">
															<input type="text" floatOnly name="length" id="length${status.count}" value="${orderInspVO.stockAllCol[0].length}"/>
														</c:if>
														<c:if test="${empty orderInspVO.stockAllCol[0].length}">
															<input type="text" floatOnly name="length" id="length${status.count}" value="${orderInspVO.cusItemVO.length}"/>
														</c:if>
													</div>
												</div>
											</div>
											<div class="col-xs-12">
												<div class="col-xs-12 col-lg-4">
													<div class="col-xs-12 col-lg-4">WTA/WTC : </div>
													<div class="col-xs-12 col-lg-8">
														<c:if test="${!empty orderInspVO.stockAllCol[0].wta}">
															<input type="text" floatOnly style="width:40%;" name="wta" id="wta${status.count}" value="${orderInspVO.stockAllCol[0].wta}"/> 
															/ 
															<input type="text" floatOnly readonly style="width:40%;" name="wtc" id="wtc${status.count}" value="${orderInspVO.stockAllCol[0].wtc}"/>
														</c:if>
														<c:if test="${empty orderInspVO.stockAllCol[0].wta}">
															<input type="text" floatOnly style="width:40%;" name="wta" id="wta${status.count}" value="${orderInspVO.cusItemVO.wta}"/> 
															/ 
															<input type="text" floatOnly readonly style="width:40%;" name="wtc" id="wtc${status.count}" value="${orderInspVO.cusItemVO.wtc}"/>
														</c:if>
													</div>
												</div>
												<div class="col-xs-12 col-lg-4">
													<div class="col-xs-12 col-lg-4">PER  : </div>
													<div class="col-xs-12 col-lg-8"><input type="text" floatOnly name="per" id="per${status.count}" 
													<c:choose>
														<c:when test="${empty orderInspVO.stockAllCol[0].per}">
															value="${stationDefault.per}"
														</c:when>
														<c:otherwise>
															value="${orderInspVO.stockAllCol[0].per}"
														</c:otherwise>
													</c:choose>
													/>
												</div>
												</div>
												<div class="col-xs-12 col-lg-4">
													<div class="col-xs-12 col-lg-6">
														길이 
														<select name="dimUnit" id="dimUnit${status.count}" >
															<c:choose>
																<c:when test="${empty orderInspVO.stockAllCol[0].dimUnit}">
																	<option 
																	<c:if test="${stationDefault.dimUnit eq 'CM'}">
																		selected
																	</c:if>
																	value="CM">CM</option>
																	<option 
																	<c:if test="${stationDefault.dimUnit eq 'IN'}">
																		selected
																	</c:if>
																	value="IN">INCH</option>
																</c:when>
																<c:otherwise>
																	<option 
																	<c:if test="${orderInspVO.stockAllCol[0].dimUnit eq 'CM'}">
																		selected
																	</c:if>
																	value="CM">CM</option>
																	<option 
																	<c:if test="${orderInspVO.stockAllCol[0].dimUnit eq 'IN'}">
																		selected
																	</c:if>
																	value="IN">INCH</option>
																</c:otherwise>
															</c:choose>
														</select>
													</div>
													<div class="col-xs-12 col-lg-6">
														무게
														<select name="wtUnit" id="wtUnit${status.count}" >
															<c:choose>
																<c:when test="${empty orderInspVO.stockAllCol[0].wtUnit}">
																	<option 
																	<c:if test="${stationDefault.wtUnit eq 'KG'}">
																		selected
																	</c:if>
																	value="KG">KG</option>
																	<option 
																	<c:if test="${stationDefault.wtUnit eq 'LB'}">
																		selected
																	</c:if>
																	value="LB">LB</option>
																</c:when>
																<c:otherwise>
																	<option 
																	<c:if test="${orderInspVO.stockAllCol[0].wtUnit eq 'KG'}">
																		selected
																	</c:if>
																	value="KG">KG</option>
																	<option 
																	<c:if test="${orderInspVO.stockAllCol[0].wtUnit eq 'LB'}">
																		selected
																	</c:if>
																	value="LB">LB</option>
																</c:otherwise>
															</c:choose>
														</select>
													</div>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
							<div class="hr hr-8 dotted"></div>
							<div class="profile-user-info" style="font-size:medium; border-top:none;">
								<div class="profile-info-row ">
									<div class="profile-info-name"> 택배회사 </div>
	
									<div class="profile-info-value">
										<span><input type="text" name="trackComName" id="trackComName${status.count}" 
										<c:choose>
											<c:when test="${empty orderInspVO.stockAllCol[0].trkCom}">
												value="${orderInspVO.trkCom}"
											</c:when>
											<c:otherwise>
												value="${orderInspVO.stockAllCol[0].trkCom}"
											</c:otherwise>
										</c:choose>
										/>
									</span>
									</div>
									<div class="profile-info-name"> 택배 번호 </div>
									<div class="profile-info-value">
										<span>
											<input type="text" name="comTrackNo" id="comTrackNo${status.count}" 
												<c:choose>
													<c:when test="${empty orderInspVO.stockAllCol[0].trkNo}">
														value="${orderInspVO.trkNo}"
													</c:when>
													<c:otherwise>
														value="${orderInspVO.stockAllCol[0].trkNo}"
													</c:otherwise>
												</c:choose>
											/>
										</span>
									</div>
								</div>
							</div>
							<div class="hr hr-8 dotted"></div>
							<div class="profile-user-info" style="font-size:medium; border-top:none;">
								<div class="profile-info-row ">
									<div class="profile-info-name"> 입고 메모 </div>
									<div class="profile-info-value">
										<span>
											<textarea name="orderMemo" id="orderMemo${status.count}" placeholder="입고메모" class="autosize-transition form-control woInput" style="width:100%;resize: none;"></textarea>
										</span>
									</div>
								</div>
								<div class="profile-info-row ">
									<div class="profile-info-name"> 입고 기록 </div>
									<div class="profile-info-value">
										<div class="row">
											<div class="col-lg-2">
												입고 날짜
											</div>
											<div class="col-lg-2">
												입고 상태
											</div>
											<div class="col-lg-2">
												수량
											</div>
											<div class="col-lg-2">
												랙번호
											</div>
											<div class="col-lg-4">
												입고 메모
											</div>
										</div>
										<div class="row">
											<c:forEach items="${orderInspVO.stockAllCol}" var="stockAll" varStatus="stockStatus">
												<div class="hr dotted"></div>
												<div class="col-lg-2">
													${stockAll.whInDate }
												</div>
												<div class="col-lg-2">
													${stockAll.whStatusName}
												</div>
												<div class="col-lg-2">
													${stockAll.whCnt}
												</div>
												<div class="col-lg-2">
													${stockAll.rackCode}
												</div>
												<div class="col-lg-4">
													<c:choose>
														<c:when test="${stockAll.whMemo eq null || stockAll.whMemo eq ''}">-</c:when>
														<c:otherwise>${stockAll.whMemo}</c:otherwise>
													</c:choose>
												</div>
											</c:forEach>
										</div>
									</div>
								</div>
							</div>
							
							<div class="hr hr-8 dotted"></div>
							<div class="profile-user-info hidden" id = "addInfo${status.count}" name = "addInfo" >
								<div class="profile-info-row">
									<div class="profile-info-name"> 추가입고 </div>
									<div class="profile-info-value">
										<span> 정상입고  </span>
										<input type="text" numberOnly name="addOkCnt" id="addOkCnt${status.count}" value=""/>
										<span> 검수 불합격 </span>
										<input type="text" numberOnly name="addFailCnt" id="addFailCnt${status.count}" value=""/>
									</div>
								</div>
								
								<div class="profile-info-row ">
									<div class="profile-info-name"> 추가입고 <br/>사진업로드  </div>
	
									<div class="profile-info-value" style="vertical-align: middle;">
										<div class="add_wrap col-md-12 col-lg-2" style="text-align: center;">
								            <div id="add_imgs_wrap${status.count}_1"class="add_imgs_wrap">
								            	<span class="red">* 재등록 시, 이미지 변경</span>
								            	<img id="img" />
								            </div>
											<a href="javascript:" class="my_button2 registImage" id="${status.count}_1">사진 업로드1</a>
											<br/>
								            <input type="file" id="add_imgs${status.count}_1" name="add_imgs${status.count}" />
								        </div>
										<div class="add_wrap col-md-12 col-lg-2" style="text-align: center;">
								            <div id="add_imgs_wrap${status.count}_2"class="add_imgs_wrap">
								           		<span class="red">* 재등록 시, 이미지 변경</span>
								            	<img id="img" />
								            </div>
											<a href="javascript:" class="my_button2 registImage" id="${status.count}_2">사진 업로드2</a>
											<br/>
								            <input type="file" id="add_imgs${status.count}_2" name="add_imgs${status.count}" />
								        </div>
										<div class="add_wrap col-md-12 col-lg-2" style="text-align: center;">
								            <div id="add_imgs_wrap${status.count}_3"class="add_imgs_wrap">
								            	<span class="red">* 재등록 시, 이미지 변경</span>
								            	<img id="img" />
								            </div>
											<a href="javascript:" class="my_button2 registImage" id="${status.count}_3">사진 업로드3</a>
											<br/>
								            <input type="file" id="add_imgs${status.count}_3" name="add_imgs${status.count}" />
								        </div>
										<div class="add_wrap col-md-12 col-lg-2" style="text-align: center;">
								            <div id="add_imgs_wrap${status.count}_4"class="add_imgs_wrap">
								            	<span class="red">* 재등록 시, 이미지 변경</span>
								            	<img id="img" />
								            </div>
											<a href="javascript:" class="my_button2 registImage" id="${status.count}_4">사진 업로드4</a>
											<br/>
								            <input type="file" id="add_imgs${status.count}_4" name="add_imgs${status.count}" />
								        </div>
										<div class="add_wrap col-md-12 col-lg-2" style="text-align: center;">
								            <div id="add_imgs_wrap${status.count}_5"class="add_imgs_wrap">
								            	<span class="red">* 재등록 시, 이미지 변경</span>
								            	<img id="img" />
								            </div>
											<a href="javascript:" class="my_button2 registImage" id="${status.count}_5">사진 업로드5</a>
											<br/>
								            <input type="file" id="add_imgs${status.count}_5" name="add_imgs${status.count}" />
								        </div>
									</div>
								</div>
							</div>
							
							<div class="profile-user-info hidden" name="failField" style="font-size:medium; border-top:none;">
								<div class="profile-info-row" >
									<div class="profile-info-name">
										<label class="bolder red bigger-150">이미지</label> 
									</div>
									<div class="profile-info-value">
										<span class="red"> *이미지 재업로드 시, 기존에 업로드한 이미지 자동 삭제. 이미지 다중 등록 시, Ctrl + 파일 클릭하여 등록</span>
										<div>
									        <div id="imgs_wrap${status.count}"class="imgs_wrap">
									            <img id="img" />
									        </div>
									    </div>
								        <div class="input_wrap">
								            <a href="javascript:" onclick="fileUploadAction(${status.count});" class="my_button">이미지 등록</a>
								            <input type="file" id="input_imgs${status.count}" name="input_imgs" multiple/>
								            <input type="hidden" id="itemImgCnt${status.count}" name="itemImgCnt" value="1"/>
								        </div>
									</div>
								</div>
							</div> 
						</div><!-- /.col -->
					</div><!-- /.row -->
					<div class="hr hr-8 dotted"></div>
					<div class="space-20"></div>
				</c:forEach>
				
				<div class="row">
					<div class="col-xs-12 col-sm-12">
						<div class="profile-user-info" style="font-size:medium;">
							<div class="profile-info-row">
								<div class="profile-info-name"> 
									<label class="bolder blue">검수상태</label>
								</div>
								<div class="profile-info-value">
									<div class="control-group">
										<div class="radio">
											<label>
												<input name="form-field-radio" type="radio" checked="true" class="ace input-lg" value="WO"/>
												<span class="lbl bigger-120"> 정상입고</span>
											</label>
											<label>
												<input name="form-field-radio" type="radio" class="ace input-lg" value="WP"/>
												<span class="lbl bigger-120"> 부분입고</span>
											</label>
											<label>
												<input name="form-field-radio" type="radio" class="ace input-lg" value="WF"/>
												<span class="lbl bigger-120"> 검수 불합격</span>
											</label>
										</div>
										<input type="hidden" name="whStatus" id="whStatus" >
									</div>										
								</div>
							</div>
						</div>
						<div class="hr hr-8 dotted"></div>
					</div>
				</div>
				<div class="row">
					<div class="col-xs-12 col-sm-4">
						<div class="widget-box transparent hidden" name="rackField">
							<div class="widget-header widget-header-small">
								<h2 class="widget-title smaller">
									<i class="ace-icon fa fa-check-square-o bigger-170"></i>
									Rack 정보
								</h2>
							</div>
							<div class="widget-body">
								<div class="widget-main">
									<input class="bigger-210 light-red" type="text" id="rack" name="rack" value="rack#1" style="background-color: yellow"/>
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
				</form>
			</div><!-- /#home -->
		</div>
			
		<!-- script addon start -->
		<script type="text/javascript">
			function fn_test(no){
				$("#rgstChk"+no).trigger('click');
				
			}
		
			jQuery(function($) {
				$(document).load(function() {
					$("#12thMenu").toggleClass('open');
					$("#12thMenu").toggleClass('active'); 
					$("#12thFor").toggleClass('active');
				});

				$(":input:radio[name=form-field-radio]").on('change',function(){
					if($(":input:radio[name=form-field-radio]:checked").val() == "WF"){
						$("[name=failField]").removeClass("hidden");
						$("[name=rackField]").removeClass("hidden");
						$("[name=nomalField]").addClass("hidden");
						$("[name=orderMemo]").each( function(){
				            if($(this).val()==""){
				            	$(this).removeClass("woInput");
				            	$(this).addClass("wfInput");
				            }
				        });
					}else if($(":input:radio[name=form-field-radio]:checked").val() == "WO"){
						$("[name=nomalField]").removeClass("hidden");
						$("[name=failField]").addClass("hidden");
						$("[name=rackField]").addClass("hidden");
						$("[name=orderMemo]").removeClass("wfInput");					
						$("[name=orderMemo]").addClass("woInput");
					}else{
						$("[name=nomalField]").addClass("hidden");
						$("[name=failField]").addClass("hidden");
						$("[name=rackField]").removeClass("hidden");
						$("[name=orderMemo]").each( function(){
				            if($(this).val()==""){
				            	$(this).removeClass("woInput");
				            	$(this).addClass("wfInput");
				            }
				        });
					}
				});
			})
			
			$("[name=rgstChk]").on('change',function(e){
				if(this.checked){
					$("#rgstYN"+this.value).val("Y");
				}else{
					$("#rgstYN"+this.value).val("N");
				}
				
			})
			
			
			$("[name=orderMemo]").on('change',function(e){
				if(this.value==""){
					this.classList.add("wfInput");
					this.classList.remove("woInput");
				}else{
					this.classList.add("woInput");
					this.classList.remove("wfInput");
				}
				
			})
			
			$("#backBtn").on("click",function(){
				/* alert("TEST"); */
				location.reload();
			})
			
			$("#saveBtn").on("click",function(e){
				var passChk = 0;
				$("#whStatus").val($(":input:radio[name=form-field-radio]:checked").val());
				if($("#whStatus").val() == "WO"){
					$("[name=housCnt]").each(function(e){
						if($("[name=rgstChk]").get(e).checked){
							if($(this).val()==""){
								/* if(confirm("입고수량이 입력되지 않았습니다. 상품개수를 입고수량으로 대체휴ㅏ")){
									$(this).val($(this).attr('placeholder'));
								}else{ */
									alert("입고수량이 비어있습니다.")
									$(this).focus();
									passChk = 1;
									return false;
								/* } */
								
							}else{
								if($(this).val() < $("[name=whTotalItemCnt]").get(e).value){
									alert("입고수량이 상품개수보다 작습니다.")
									passChk = 1;
									return false;
								}
								
							}
						}
						
					})
				}else{
					$("[name=housCnt]").each(function(e){
						if($("[name=rgstChk]").get(e).checked){
							if($(this).val()==""){
								alert("입고수량이 비어있습니다.")
								$(this).focus();
								passChk = 1;
								return false;
							}
						}

					})
				}
				if(passChk == 1){
					return false;
				}
				$('#orderInspForm').attr("action","/mngr/order/orderInspcWhProcess");
				$('#orderInspForm').attr("method","post");
				$('#orderInspForm').submit();
			})
		
		</script>
		<!-- script addon end -->
		
		
		<!-- file upload 미리보기 기능 등등 -->
	    <!-- <script type="text/javascript" src="./js/jquery-3.1.0.min.js" charset="utf-8"></script> -->
	    <script type="text/javascript">

        // 이미지 정보들을 담을 배열
        var sel_files = [];
        var targetsNum = "";
        var targetIdAdd = "";
        
        $(document).ready(function() {
        	$("[name=input_imgs]").on("change", handleImgFileSelect);
        	$("[name*=add_imgs]").on("change", handleImgFileSelectAdd);
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
                    var html = "<a href=\"javascript:void(0);\" onclick=\"deleteImageAction(img_id_"+index+")\" id=\"img_id_"+index+"\"><img src=\"" + e.target.result + "\" data-file='"+f.name+"' class='selProductFile' title='Click to remove'><i class='ace-icon fa fa-times red2'></i></a>";
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
        
        function handleImgFileSelectAdd(e) {

            // 이미지 정보들을 초기화
            sel_files = [];
            $("#add_imgs_wrap"+targetIdAdd).empty();
            var files = e.target.files;
            var filesArr = Array.prototype.slice.call(files);
            var index = 0;
            filesArr.forEach(function(f) {
                if(!f.type.match("image.*")) {
                    alert("확장자는 이미지 확장자만 가능합니다.");
                    return;
                }

                sel_files.push(f);

                var reader = new FileReader();
                reader.onload = function(e) {
                    var html = "<a href=\"javascript:void(0);\" onclick=\"deleteImageAction(img_id_"+targetIdAdd+")\" id=\"img_id_"+targetIdAdd+"\"><img src=\"" + e.target.result + "\" data-file='"+f.name+"' class='selProductFile' title='Click to remove'><i class='ace-icon fa fa-times red2'></i></a>";
                    $("#add_imgs_wrap"+targetIdAdd).append(html);
                    
                    index++;
                }
                reader.readAsDataURL(f);
                
            });
        }
        



        function deleteImageAction(index) {
            sel_files.splice(index, 1);

            //var img_id = "#img_id_"+index.id;
            $("#"+index.id).remove();
        }

        $("input:text[numberOnly]").on("keyup", function() {
            $(this).val($(this).val().replace(/[^0-9]/g,""));
        });

        $("input:text[floatOnly]").on("keyup", function() {
            $(this).val($(this).val().replace(/[^0-9.]/g,""));
        });

        $("input[id*='horiz']").change(function(){
        	wtCalc();
        	maximum();
        });
        $("input[id*='verti']").change(function(){
        	wtCalc();
        	maximum();
        });
        $("input[id*='height']").change(function(){
        	wtCalc();
        	maximum();
        });
        $("input[id*='value']").change(function(){
        	wtCalc();
        	maximum();
        });
        $("#boxWeight").change(function(){
        	maximum();
        });

        function maximum(){
        	var size = $("input[id*='result']").size();
        	var results = $("#boxWeight").val();
			for(loop=0;loop<size;loop++){	
				if(parseFloat(results) < parseFloat($("input[id*='result']").get(loop).value)){
					results = $("input[id*='result']").get(loop).value;
				}
			}
			$("#maxi").text(results);
        };

        function wtCalc(){
            var horiz = 0;
            var verti = 0;
            var height = 0;
            var value = 0;
            var result = 0;
        	var size = $("input[id*='horiz']").size();
        	for(loop=0;loop<size;loop++){
        		horiz = $("input[id*='horiz']").get(loop).value;
        		verti = $("input[id*='verti']").get(loop).value;
        		height = $("input[id*='height']").get(loop).value;
        		value = $("input[id*='value']").get(loop).value;
        		result = (horiz*verti*height)/value;
        		result = result.toFixed(1);
        		if(result != 0){
        			$("input[id*='result']").get(loop).value = result;
          		}else{
          			$("input[id*='result']").get(loop).value = "";
           		}
           	}
        };

        function fn_containValue(ids, totalVal,index){
			if($(ids).val() > totalVal){
				if(confirm("입력된 상품 개수가 많습니다. 추가 검수 상품이 맞습니까?")){
					if($("#addInfo"+index).hasClass("hidden")){
						$("#addInfo"+index).toggleClass("hidden");
						var temp = $(ids).val() - totalVal;
						$("#addOkCnt"+index).val(temp);
						$("#addFailCnt"+index).val(0);
					}
				}else{
					$(ids).val("");
				}
				
				
			}else{
				if(!$("#addInfo"+index).hasClass("hidden"))
					$("#addInfo"+index).toggleClass("hidden");
			}
        }
    </script>
		
	</body>
</html>
