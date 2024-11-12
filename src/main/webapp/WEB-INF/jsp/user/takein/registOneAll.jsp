<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
	
<div class="row">
	<div class="col-xs-12 col-lg-12" >
		<div class="col-xs-12 col-lg-12 " style="font-size: 5;">
			<h2>주문정보</h2>
		</div>
		<!-- style="border:1px solid red" -->
		<div class="col-xs-12 col-lg-12" style="border: 1px solid #dee2e6!important; padding: 3rem!important">
			<div class="col-xs-12 col-lg-12 " style="font-size: 3;">
				<h4 >발송 정보</h4>
				<c:if test="${(!empty expressOrderVO.shipperNameYnExpress) or (!empty expressOrderVO.shipperTelYnExpress) or (!empty expressOrderVO.shipperHpYnExpress)}"> 
					<div class="row hr-8">
					<c:if test="${!empty expressOrderVO.shipperNameYnExpress}"> 
						<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.shipperNameYn}"> shipperNm </c:if>">이름</div>
						<div class="col-xs-3 center">
							<input class="col-xs-12 <c:if test="${!empty optionOrderVO.shipperNameYn}"> shipperVal </c:if>" type="text" name="shipperName" id="shipperName" value="${userInfo.comEName}" />
						</div>
					</c:if>
					<c:if test="${!empty expressOrderVO.shipperTelYnExpress}">
						<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.shipperTelYn}"> shipperNm </c:if>">전화번호</div>
						<div class="col-xs-3 center">
							<input class="col-xs-12 <c:if test="${!empty optionOrderVO.shipperTelYn}"> shipperVal </c:if>" placeholder="숫자만 입력 가능합니다." numberOnly type="text" name="shipperTel" id="shipperTel" value="${userInfo.userTel}" />
						</div>
					</c:if>
					<c:if test="${!empty expressOrderVO.shipperHpYnExpress}">
						<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.shipperHpYn}"> shipperNm </c:if>">휴대전화 번호</div>
						<div class="col-xs-3 center">
							<input class="col-xs-12 <c:if test="${!empty optionOrderVO.shipperHpYn}"> shipperVal </c:if>" placeholder="숫자만 입력 가능합니다." numberOnly type="text" name="shipperHp" id="shipperHp" value="${userInfo.userHp}" />
						</div>
					</c:if>
					</div>
					<div class="hr dotted"></div>
				</c:if>
				
				<c:if test="${(!empty expressOrderVO.shipperCntryYnExpress) or (!empty expressOrderVO.shipperCityYnExpress) or (!empty expressOrderVO.shipperStateYnExpress)}">
					<div class="row form-group hr-8">
					<c:if test="${!empty expressOrderVO.shipperCntryYnExpress}">
						<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.shipperCntryYn}"> shipperNm </c:if>">발송 국가</div>
						<div class="col-xs-3 center">
							<select class="chosen-select-one form-control  tag-input-style width-100 pd-1rem <c:if test="${!empty optionOrderVO.shipperCntryYn}"> shipperVal </c:if> " id="shipperCntry" name="shipperCntry">
								<option value="">  </option>
								<c:forEach items="${nationList}" var="nation">
									<option value="${nation.nationCode}">${nation.nationEName}(${nation.nationName })</option>
								</c:forEach>
							</select>
						</div>
					</c:if>
					<c:if test="${!empty expressOrderVO.shipperCityYnExpress}">
						<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.shipperCityYn}"> shipperNm </c:if>">발송 도시</div>
						<div class="col-xs-3 center">
							<input class="col-xs-12 <c:if test="${!empty optionOrderVO.shipperCityYn}"> shipperVal </c:if>" type="text" name="shipperCity" id="shipperCity" value="" />
						</div>
					</c:if>
					<c:if test="${!empty expressOrderVO.shipperStateYnExpress}">
						<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.shipperStateYn}"> shipperNm </c:if>">발송 State</div>
						<div class="col-xs-3 center ">
							<input class="col-xs-12 <c:if test="${!empty optionOrderVO.shipperStateYn}"> shipperVal </c:if>" type="text" name="shipperState" id="shipperState" value="" />
						</div>
					</c:if>
					</div>
					<div class="hr dotted"></div>
				</c:if>
				
				<c:if test="${(!empty expressOrderVO.shipperZipYnExpress) or (!empty expressOrderVO.shipperAddrYnExpress) or (!empty expressOrderVO.shipperAddrDetailYnExpress)}">
					<div class="row hr-8">
					<c:if test="${!empty expressOrderVO.shipperZipYnExpress}">
						<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.shipperZipYn}"> shipperNm </c:if>">
							<label>우편주소</label>
						</div>
						<div class="col-xs-3 center">
							<input class="col-xs-12 <c:if test="${!empty optionOrderVO.shipperZipYn}"> shipperVal </c:if>" type="text" name="shipperZip" id="shipperZip" value="${userInfo.userZip}" />
						</div>
					</c:if>
					<c:if test="${!empty expressOrderVO.shipperAddrYnExpress}">
						<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.shipperAddrYn}"> shipperNm </c:if>">
							<label>주소</label>
						</div>
						<div class="col-xs-3 center">
							<input class="col-xs-12 <c:if test="${!empty optionOrderVO.shipperAddrYn}"> shipperVal </c:if>" type="text" name="shipperAddr" id="shipperAddr" value="${userInfo.userEAddr}" />
						</div>
					</c:if>
					<c:if test="${!empty expressOrderVO.shipperAddrDetailYnExpress}">
						<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.shipperAddrDetailYn}"> shipperNm </c:if>">
							<label>상세주소</label>
						</div>
						<div class="col-xs-3 center ">
							<input class="col-xs-12 <c:if test="${!empty optionOrderVO.shipperAddrDetailYn}"> shipperVal </c:if>" type="text" name="shipperAddrDetail" id="shipperAddrDetail" value="${userInfo.userEAddrDetail}" />
						</div>
					</c:if>
					</div>
					<div class="hr dotted"></div>
				</c:if>
				<c:if test="${(!empty expressOrderVO.userEmailYnExpress) or (!empty expressOrderVO.whReqMsgYnExpress)}">
					<div class="row hr-8">
						<c:if test="${!empty expressOrderVO.userEmailYnExpress}">
							<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.userEmailYn}"> shipperNm </c:if>">
								<label>Email</label>
							</div>
							<div class="col-xs-3">
								<input class="col-xs-12 <c:if test="${!empty optionOrderVO.userEmailYn}"> shipperVal </c:if>" type="text" name="userEmail" id="userEmail" value="${userInfo.userEmail}" />
							</div>
						</c:if>
						<c:if test="${!empty expressOrderVO.whReqMsgYnExpress}">
							<div class="col-xs-2 center mp07 <c:if test="${!empty optionOrderVO.whReqMsgYn}"> shipperNm </c:if>">
								<label>입고 메모</label>
							</div>
							<div class="col-xs-6">
								<input class="col-xs-12 <c:if test="${!empty optionOrderVO.whReqMsgYn}"> shipperVal </c:if>" type="text" name="whReqMsg" id="whReqMsg" value="${userOrder.whReqMsg}"  />
							</div>
						</c:if>
					</div>
					<div class="hr dotted"></div>
				</c:if>
				<c:if test="${(!empty expressOrderVO.shipperReferenceYnExpress)}">
					<div class="row hr-8">
						<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.shipperReferenceYn}"> shipperNm </c:if>">
							<label>Reference</label>
						</div>
						<div class="col-xs-5">
							<input class="col-xs-12 <c:if test="${!empty optionOrderVO.shipperReferenceYn}"> shipperVal </c:if>" type="text" name="shipperReference" id="shipperReference" value="${userOrder.shipperReference}" />
						</div>
					</div>
					<div class="hr dotted"></div>
				</c:if>
				
			</div>
			<div class="col-xs-12 col-lg-12 " style="font-size: 3;">
				<h4 style="margin-top: 20px;">수취 정보</h4>
				<c:if test="${(!empty expressOrderVO.cneeNameYnExpress) or (!empty expressOrderVO.cneeTelYnExpress) or (!empty expressOrderVO.cneeHpYnExpress)}">
					<div class="row hr-8">
					<c:if test="${!empty expressOrderVO.cneeNameYnExpress}">
						<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.cneeNameYn}"> cneeNm </c:if>">이름</div>
						<div class="col-xs-3 center">
							<input class="col-xs-12 <c:if test="${!empty optionOrderVO.cneeNameYn}"> cneeVal </c:if>" type="text" name="cneeName" id="cneeName" value="" />
						</div>
					</c:if>
					<c:if test="${!empty expressOrderVO.cneeTelYnExpress}">
						<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.cneeTelYn}"> cneeNm </c:if>">전화번호</div>
						<div class="col-xs-3 center">
							<input class="col-xs-12 <c:if test="${!empty optionOrderVO.cneeTelYn}"> cneeVal </c:if>" placeholder="숫자만 입력 가능합니다." numberOnly type="text" name="cneeTel" id="cneeTel" value="" />
						</div>
					</c:if>
					<c:if test="${!empty expressOrderVO.cneeHpYnExpress}">
						<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.cneeHpYn}"> cneeNm </c:if>">휴대전화 번호</div>
						<div class="col-xs-3 center">
							<input class="col-xs-12 <c:if test="${!empty optionOrderVO.cneeHpYn}"> cneeVal </c:if>" placeholder="숫자만 입력 가능합니다." numberOnly type="text" name="cneeHp" id="cneeHp" value="" />
						</div>
					</c:if>
					</div>
					<div class="hr dotted"></div>
				</c:if>
				<c:if test="${(!empty expressOrderVO.cneeCntryYnExpress) or (!empty expressOrderVO.cneeCityYnExpress) or (!empty expressOrderVO.cneeStateYnExpress) or(!empty expressOrderVO.cneeDistrictYnExpress)}">
					<div class="row hr-8">
					<c:if test="${!empty expressOrderVO.cneeCntryYnExpress}"> 
						<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.cneeCntryYn}"> cneeNm </c:if>">수취 국가</div>
						<div class="col-xs-2 center">
							<select class="chosen-select-one form-control tag-input-style width-100 pd-1rem <c:if test="${!empty optionOrderVO.cneeCntryYn}"> cneeVal </c:if>" id="cneeCntry" name="cneeCntry">
								<option value="">  </option>
								<c:forEach items="${nationList}" var="nation">
									<option value="${nation.nationCode}" <c:if test="${nation.nationCode eq userOrder.cneeCntry }"> selected </c:if>>${nation.nationEName}(${nation.nationName })</option>
								</c:forEach>
							</select>
						</div>
					</c:if>
					<c:if test="${!empty expressOrderVO.cneeStateYnExpress}">
						<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.cneeStateYn}"> cneeNm </c:if>">수취 State</div>
						<div class="col-xs-2 center">
							<input class="col-xs-12 <c:if test="${!empty optionOrderVO.cneeStateYn}"> cneeVal </c:if>" type="text" name="cneeState" id="cneeState" value="${userOrder.cneeState}" />
						</div>
					</c:if>
					
					<c:if test="${!empty expressOrderVO.cneeCityYnExpress}">
						<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.cneeCityYn}"> cneeNm </c:if>">수취 도시</div>
						<div class="col-xs-2 center">
							<input class="col-xs-12 <c:if test="${!empty optionOrderVO.cneeCityYn}"> cneeVal </c:if>" type="text" name="cneeCity" id="cneeCity" value="${userOrder.cneeCity }" />
						</div>
					</c:if>
					
					<c:if test="${!empty expressOrderVO.cneeDistrictYnExpress}">
						<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.cneeDistrictYn}"> cneeNm </c:if>">수취 동</div>
						<div class="col-xs-2 center">
							<input class="col-xs-12 <c:if test="${!empty optionOrderVO.cneeDistrictYn}"> cneeVal </c:if>" type="text" name="cneeDistrict" id="cneeDistrict" value="${userOrder.cneeDistrict}" />
						</div>
					</c:if>
					</div>
				<div class="hr dotted"></div>
				</c:if>
				<c:if test="${(!empty expressOrderVO.cneeZipYnExpress) or (!empty expressOrderVO.cneeAddrYnExpress) or (!empty expressOrderVO.cneeAddrDetailYnExpress)}">
					<div class="row hr-8">
					<c:if test="${!empty expressOrderVO.cneeZipYnExpress}">
						<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.cneeZipYn}"> cneeNm </c:if>">
							<label>우편주소</label>
						</div>
						<div class="col-xs-3 center">
							<input class="col-xs-12 <c:if test="${!empty optionOrderVO.cneeZipYn}"> cneeVal </c:if>" type="text" name="cneeZip" id="cneeZip" value="" />
						</div>
					</c:if>
					<c:if test="${!empty expressOrderVO.cneeAddrYnExpress}">
						<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.cneeAddrYn}"> cneeNm </c:if>">
							<label>주소</label>
						</div>
						<div class="col-xs-3 center">
							<input class="col-xs-12 <c:if test="${!empty optionOrderVO.cneeAddrYn}"> cneeVal </c:if>" type="text" name="cneeAddr" id="cneeAddr" value="" />
						</div>
					</c:if>
					<c:if test="${!empty expressOrderVO.cneeAddrDetailYnExpress}">
						<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.cneeAddrDetailYn}"> cneeNm </c:if>">
							<label>상세주소</label>
						</div>
						<div class="col-xs-3 center">
							<input class="col-xs-12 <c:if test="${!empty optionOrderVO.cneeAddrDetailYn}"> cneeVal </c:if>" type="text" name="cneeAddrDetail" id="cneeAddrDetail" value="" />
						</div>
					</c:if>
					</div>
				<div class="hr dotted"></div>
				</c:if>
				
				<c:if test="${(!empty expressOrderVO.nativeCneeNameYnExpress) or (!empty expressOrderVO.nativeCneeAddrYnExpress) or (!empty expressOrderVO.nativeCneeAddrDetailYnExpress)}">
					<div class="row hr-8">
					<c:if test="${!empty expressOrderVO.nativeCneeNameYnExpress}">
						<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.nativeCneeNameYn}"> cneeNm </c:if>">
							<label>현지 이름</label>
						</div>
						<div class="col-xs-3 center">
							<input class="col-xs-12 <c:if test="${!empty optionOrderVO.nativeCneeNameYn}"> cneeVal </c:if>" type="text" name="nativeCneeName" id="nativeCneeName" value="${userOrder.nativeCneeName}" />
						</div>
					</c:if>
					<c:if test="${!empty expressOrderVO.nativeCneeAddrYnExpress}">
						<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.nativeCneeAddrYn}"> cneeNm </c:if>">
							<label>현지 주소</label>
						</div>
						<div class="col-xs-3 center">
							<input class="col-xs-12 <c:if test="${!empty optionOrderVO.nativeCneeAddrYn}"> cneeVal </c:if>" type="text" name="nativeCneeAddr" id="nativeCneeAddr" value="${userOrder.nativeCneeAddr}" />
						</div>
					</c:if>
					<c:if test="${!empty expressOrderVO.nativeCneeAddrDetailYnExpress}">
						<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.nativeCneeAddrDetailYn}"> cneeNm </c:if>">
							<label>현지 상세주소</label>
						</div>
						<div class="col-xs-3 center">
							<input class="col-xs-12 <c:if test="${!empty optionOrderVO.nativeCneeAddrDetailYn}"> cneeVal </c:if>" type="text" name="nativeCneeAddrDetail" id="nativeCneeAddrDetail" value="${userOrder.nativeCneeAddrDetail}" />
						</div>
					</c:if>
					</div>
				<div class="hr dotted"></div>
				</c:if>
				<c:if test="${(!empty expressOrderVO.cneeEmailYnExpress) or (!empty expressOrderVO.customsNoYnExpress) or (!empty expressOrderVO.cneeBirthYnExpress)}">
					<div class="row hr-8">
						<c:if test="${!empty expressOrderVO.cneeEmailYnExpress}">
							<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.cneeEmailYn}"> cneeNm </c:if>">
								<label>Email</label>
							</div>
							<div class="col-xs-3 center">
								<input  class="col-xs-12 <c:if test="${!empty optionOrderVO.cneeEmailYn}"> cneeVal </c:if>" type="text" name="cneeEmail" id="cneeEmail" value="${userOrder.cneeEmail}" />
							</div>
						</c:if>
						<c:if test="${!empty expressOrderVO.customsNoYnExpress}">
							<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.customsNoYn}"> cneeNm </c:if>">
								<label>
								<c:if test="${userOrder.dstnNation eq  'KR'}">
									개인통관부호
								</c:if>
								<c:if test="${userOrder.dstnNation ne  'KR'}">
									수취인 ID 번호
								</c:if>
								</label>
							</div>
							<div class="col-xs-2 center">
								<input  class="col-xs-12 <c:if test="${!empty optionOrderVO.customsNoYn}"> cneeVal </c:if>" type="text" name="customsNo" id="customsNo" value="${userOrder.customsNo}" />
							</div>
						</c:if>
						<c:if test="${!empty expressOrderVO.cneeBirthYnExpress}">
							<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.cneeBirthYn}"> cneeNm </c:if>">
								<label>수취인 생년월일 (day/month/year)</label>
							</div>
							<div class="col-xs-2 center">
								<input  class="col-xs-12 <c:if test="${!empty optionOrderVO.cneeBirthYn}"> cneeVal </c:if>" type="text" name="cneeBirth" id="cneeBirth" value="${userOrder.cneeBirth}" />
							</div>
						</c:if>
					</div>
					<div class="hr dotted"></div>
				</c:if>
				<c:if test="${(!empty expressOrderVO.nationalIdDateYnExpress) or (!empty expressOrderVO.nationalIdAuthorityYnExpress) or (!empty expressOrderVO.taxNoYnExpress)}">
					<div class="row hr-8">
						<c:if test="${!empty expressOrderVO.nationalIdDateYnExpress}">
							<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.nationalIdDateYn}"> cneeNm </c:if>">
								<label>수취인 ID번호 발급일 (day/month/year)</label>
							</div>
							<div class="col-xs-3 center">
								<input  class="col-xs-12 <c:if test="${!empty optionOrderVO.nationalIdDateYn}"> cneeVal </c:if>" type="text" name="nationalIdDate" id="nationalIdDate" value="${userOrder.nationalIdDate}" />
							</div>
						</c:if>
						<c:if test="${!empty expressOrderVO.nationalIdAuthorityYnExpress}">
							<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.nationalIdAuthorityYn}"> cneeNm </c:if>">
								<label>수취인 ID번호 발급기관 이름/주소</label>
							</div>
							<div class="col-xs-3 center">
								<input  class="col-xs-12 <c:if test="${!empty optionOrderVO.nationalIdAuthorityYn}"> cneeVal </c:if>" type="text" name="nationalIdAuthority" id="nationalIdAuthority" value="${userOrder.nationalIdAuthority}" />
							</div>
						</c:if>
						<c:if test="${!empty expressOrderVO.taxNoYnExpress}">
							<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.taxNoYn}"> cneeNm </c:if>">
								<label>수취인 TAX 번호</label>
							</div>
							<div class="col-xs-3 center">
								<input  class="col-xs-12 <c:if test="${!empty optionOrderVO.taxNoYn}"> cneeVal </c:if>" type="text" name="taxNo" id="taxNo" value="${userOrder.taxNo}" />
							</div>
						</c:if>
					</div>
					<div class="hr dotted"></div>
				</c:if>
				<c:if test="${(!empty expressOrderVO.dlvReqMsgYnExpress)}">
					<div class="row hr-8">
						<c:if test="${!empty expressOrderVO.dlvReqMsgYnExpress}">
							<div class="col-xs-2 center mp07 <c:if test="${!empty optionOrderVO.dlvReqMsgYn}"> cneeNm </c:if>">
								<label>수취인 요구사항</label>
							</div>
							<div class="col-xs-6 center">
								<input  class="col-xs-12 <c:if test="${!empty optionOrderVO.dlvReqMsgYn}"> cneeVal </c:if>" type="text" name="dlvReqMsg" id="dlvReqMsg" value="${userOrder.dlvReqMsg}" />
							</div>
						</c:if>
					</div>
					<div class="hr dotted"></div>
				</c:if>
				<c:if test="${(!empty expressOrderVO.cneeReference1YnExpress) or (!empty expressOrderVO.cneeReference2YnExpress)}">
					<div class="row hr-8">
						<c:if test="${!empty expressOrderVO.cneeReference1YnExpress}">
							<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.cneeReference1Yn}"> cneeNm </c:if>">
								<label>Reference1</label>
							</div>
							<div class="col-xs-4 center">
								<input  class="col-xs-12 <c:if test="${!empty optionOrderVO.cneeReference1Yn}"> cneeVal </c:if>" type="text" name="cneeReference1" id="cneeReference1" value="${userOrder.cneeReference1}" />
							</div>
						</c:if>
						<c:if test="${!empty expressOrderVO.cneeReference2YnExpress}">
							<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.cneeReference2Yn}"> cneeNm </c:if>">
								<label>Reference2</label>
							</div>
							<div class="col-xs-4 center">
								<input  class="col-xs-12 <c:if test="${!empty optionOrderVO.cneeReference2Yn}"> cneeVal </c:if>" type="text" name="cneeReference2" id="cneeReference2" value="${userOrder.cneeReference2}" />
							</div>
						</c:if>
					</div>
				</c:if>
			</div>
			
			<div class="col-xs-12 col-lg-12 " style="font-size: 3;">
				<h4 style="margin-top: 20px;">운송장 정보</h4>
				<c:if test="${(!empty expressOrderVO.orderNoYnExpress) or (!empty expressOrderVO.orderDateYnExpress) or (!empty expressOrderVO.buySiteYnExpress) or (!empty expressOrderVO.getBuyYnExpress)}">
					<div class="row hr-8">
						<c:if test="${!empty expressOrderVO.orderNoYnExpress}">
							<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.orderNoYn}"> orderNm </c:if>">
								주문 번호
							</div>
							<div class="col-xs-2 center">
								<input class="col-xs-12 <c:if test="${!empty optionOrderVO.orderNoYn}"> orderVal </c:if>" type="text" name="orderNo" id="orderNo" value="" />
							</div>
						</c:if>
						<c:if test="${!empty expressOrderVO.orderDateYnExpress}">
							<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.orderDateYn}"> orderNm </c:if>">
								주문 날짜
							</div>
							<div class="col-xs-2 center">
								<div class="col-lg-12">
									<div class="input-group input-group-sm">
										<span class="input-group-addon">
											<i class="ace-icon fa fa-calendar"></i>
										</span>
										<input type="text" id="orderDate" name="orderDate" class="form-control <c:if test="${!empty optionOrderVO.orderDateYn}"> orderVal </c:if>" value="${userOrder.orderDate}"/>
									</div>
								</div>
								<%-- <input class="col-lg-12" type="text" name="orderDate" id="orderDate" value="${userOrder.orderDate}" placeholder="ex):20201231" /> --%>
							</div>
						</c:if>
						<c:if test="${!empty expressOrderVO.buySiteYnExpress}">
							<div class="col-xs-1 center mp07 <c:if test="${!empty optionOrderVO.buySiteYn}"> orderNm </c:if>">
								구매 사이트
							</div>
							<div class="col-xs-2 center">
								<input class="col-xs-12 <c:if test="${!empty optionOrderVO.buySiteYn}"> orderVal </c:if>" type="text" name="buySite" id="buySite" value="http://" />
							</div>
						</c:if>
					</div>
					<div class="hr dotted"></div>
				</c:if>

				
				
			</div>
		</div>
	</div>
	<!-- 기본정보 End -->
	<!-- 설정정보 Start -->
	<br/>
	<br/>
	<div class="col-xs-12 col-lg-12" >
		<div class="col-xs-12 col-lg-3 form-group" style="font-size: 5;">
			<h2>상품정보</h2>
			<c:if test="${!empty expressOrderVO.foodYnExpress}">
				<div class="col-xs-12 col-lg-4 center mp07 <c:if test="${!empty optionOrderVO.foodYn}"> orderNm </c:if>">
					음식 포함 여부
				</div>
				<div class="col-xs-12 col-lg-3 center">
					<select class="col-xs-12 <c:if test="${!empty optionOrderVO.foodYn}"> orderVal </c:if>" id="food" name="food">
						<option <c:if test="${userOrder.food eq 'Y'}">selected</c:if> value="Y">Y</option>
						<option <c:if test="${userOrder.food ne 'Y'}">selected</c:if> value="N" >N</option>
					</select>
				</div>
			</c:if>
		</div>
		<!-- style="border:1px solid red" -->
		<div class="col-xs-12 col-lg-12" style="border: 1px solid #dee2e6!important; padding: 3rem!important">
			<div style="text-align: right">
				<!-- <button class="btn btn-white btn-danger btn-bold" type="button" id="delBtns" name="delBtns">
					삭제
				</button> -->
				<button class="btn btn-white btn-info btn-bold" type="button" id="addBtns" name="addBtns">
					추가
				</button>
			</div>
			<br/>
			
			
			<div id="accordion" class="accordion-style1 panel-group">
				<div class="panel panel-default panels1">
					<div class="panel-heading">
						<h4 class="panel-title">
							<span class="accordion-toggle" >
								<span data-toggle="collapse" href="#collapseOne" >
								&nbsp;Item #1
								<i class="ace-icon fa fa-angle-double-down bigger-110" data-icon-hide="ace-icon fa fa-angle-double-down" data-icon-show="ace-icon fa fa-angle-right"></i>
								</span>
								<i class="fa fa-times bigger-110" style="float: right;" id="delBtns" name="delBtns" onclick="javascript:fn_delete(this);"></i>
							</span>
						</h4>
					</div>

					<div class="panel-collapse collapse multi-collapse in" id="collapseOne">
						<div class="panel-body">
							<c:if test="${(!empty expressItemVO.trkComYnExpress) or (!empty expressItemVO.trkDateYnExpress)}">
							<div class="row  hr-8">
							<c:if test="${!empty expressItemVO.trkComYnExpress}">
								<div class="col-md-6 col-xs-12">
									<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.trkComYn}"> itemNm </c:if>" >국내 택배 회사</div>
									<div class="col-md-9 col-xs-12">
										<input class="width-100 checkItem <c:if test="${!empty optionItemVO.trkComYn}"> itemVal </c:if>" type="text" name="trkCom" id="trkCom1" value="" />
									</div>
								</div>
							</c:if>
							<c:if test="${!empty expressItemVO.trkDateYnExpress}">
								<div class="col-md-6 col-xs-12">
									<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.trkDateYn}"> itemNm </c:if>" >운송날짜</div>
									<div class="col-md-9 col-xs-12">
										<div class="input-group input-group-sm">
											<span class="input-group-addon">
												<i class="ace-icon fa fa-calendar"></i>
											</span>
											<input type="text" id="trkDate1" name="trkDate" class="width-100 checkItem <c:if test="${!empty optionItemVO.trkDateYn}"> itemVal </c:if>" onclick="javascript:test(this);" value=""/>
										</div>
									</div>
								</div>
							</c:if>
							</div>
							</c:if>
							<c:if test="${(!empty expressItemVO.trkNoYnExpress) or (!empty expressItemVO.hsCodeYnExpress)}">
							<div class="row  hr-8">
							<c:if test="${!empty expressItemVO.trkNoYnExpress}">
								<div class="col-md-6 col-xs-12">
									<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.trkNoYn}"> itemNm </c:if>" >송장 번호</div>
									<div class="col-md-9 col-xs-12">
										<input class="width-100 checkItem <c:if test="${!empty optionItemVO.trkNoYn}"> itemVal </c:if>" type="text" name="trkNo" id="trkNo1" value="" />
										<%-- <div class="input-group input-group-sm">
											
											<span class="input-group-addon" href="#modal-form" role="button" class="blue" data-toggle="modal">
												<i class="ace-icon fa fa-plus" id="hawbNoAdd" name="hawbNoAdd" style="font-size: 20px"></i>
											</span>
										</div> --%>
									</div>
								</div>
								</c:if>
								<c:if test="${!empty expressItemVO.hsCodeYnExpress}">
								<div class="col-md-6 col-xs-12">
									<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.hsCodeYn}"> itemNm </c:if>" >HS CODE</div>
									<div class="col-md-9 col-xs-12">
										<input class="width-100 checkItem <c:if test="${!empty optionItemVO.hsCodeYn}"> itemVal </c:if>" type="text" name="hsCode" id="hsCode1" value="" />
									</div>
								</div>
								</c:if>
							</div>
							</c:if>
							<c:if test="${(!empty expressItemVO.trkComYnExpress) or (!empty expressItemVO.trkDateYnExpress) or (!empty expressItemVO.trkNoYnExpress) or (!empty expressItemVO.hsCodeYnExpress)}"> 
							<div class="hr dotted"></div>
							</c:if>
							
							<div class="row  hr-8">
							<c:if test="${!empty expressItemVO.itemDetailYnExpress}">
								<div class="col-md-6 col-xs-12 hr-8">
									<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.itemDetailYn}"> itemNm </c:if>" >상품명</div>
									<div class="col-lg-9 col-xs-12">
										<input class="width-100 checkItem <c:if test="${!empty optionItemVO.itemDetailYn}"> itemVal </c:if>" type="text" name="itemDetail" id="itemDetail1" value="" />
									</div>
								</div>
							</c:if>
							<c:if test="${!empty expressItemVO.nativeItemDetailYnExpress}">
								<div class="col-md-6 col-xs-12 hr-8">
									<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.nativeItemDetailYn}"> itemNm </c:if>" >현지 상품명</div>
									<div class="col-md-9 col-xs-12">
										<input class="width-100 checkItem <c:if test="${!empty optionItemVO.nativeItemDetailYn}"> itemVal </c:if>" type="text" name="nativeItemDetail" id="nativeItemDetail1" value="" />
									</div>
								</div>
							</c:if>
							<c:if test="${!empty expressItemVO.itemExplanYnExpress}">
								<div class="col-md-6 col-xs-12 hr-8">
									<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.itemExplanYn}"> itemNm </c:if>" >상품 상세설명</div>
									<div class="col-md-9 col-xs-12">
										<input class="width-100 checkItem <c:if test="${!empty optionItemVO.itemExplanYn}"> itemVal </c:if>" type="text" name="itemExplan" id="itemExplan1" value="" />
									</div>
								</div>
								
								<div class="col-md-6 col-xs-12 hr-8">
									<div class="col-md-3 col-xs-12 pd-1rem center mp07 itemNm " >BOX 번호</div>
									<div class="col-md-9 col-xs-12">
										<input class="width-100 checkItem itemVal" type="text" name="inBoxNum" id="inBoxNum${status.count}" value="${orderItem.inBoxNum}" />
									</div>
								</div>
							</c:if>
							<c:if test="${!empty expressItemVO.itemCntYnExpress}">	
								<div class="col-md-6 col-xs-12 hr-8">
									<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.itemCntYn}"> itemNm </c:if>" >상품 개수</div>
									<div class="col-md-9 col-xs-12">
										<input class="width-100 checkItem <c:if test="${!empty optionItemVO.itemCntYn}"> itemVal </c:if>" type="text" numberOnly name="itemCnt" id="itemCnt1" value="" />
									</div>
								</div>
							</c:if>
							<c:if test="${!empty expressItemVO.qtyUnitYnExpress}">	
								<div class="col-md-6 col-xs-12 hr-8">
									<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.qtyUnitYn}"> itemNm </c:if>" >상품 단위</div>
									<div class="col-md-9 col-xs-12">
										<input class="width-100 checkItem <c:if test="${!empty optionItemVO.qtyUnitYn}"> itemVal </c:if>" type="text" name="qtyUnit" id="qtyUnit1" value="" />
									</div>
								</div>
							</c:if>
							<c:if test="${!empty expressItemVO.brandYnExpress}">	
								<div class="col-md-6 col-xs-12 hr-8">
									<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.brandYn}"> itemNm </c:if>" >BRAND</div>
									<div class="col-md-9 col-xs-12">
										<input class="width-100 checkItem <c:if test="${!empty optionItemVO.brandYn}"> itemVal </c:if>" type="text" name="brandItem" id="brand1" value="" />
									</div>
								</div>
							</c:if>
							<c:if test="${!empty expressItemVO.unitValueYnExpress}">		
								<div class="col-md-6 col-xs-12 hr-8">
									<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.unitValueYn}"> itemNm </c:if>">상품 단가</div>
									<div class="col-md-9 col-xs-12">
										<input class="width-100 checkItem <c:if test="${!empty optionItemVO.unitValueYn}"> itemVal </c:if>" type="text" floatOnly name="unitValue" id="unitValue1" value="" />
									</div>
								</div>
							</c:if>
							<c:if test="${!empty expressItemVO.itemDivYnExpress}">	
								<div class="col-md-6 col-xs-12 hr-8">
									<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.itemDivYn}"> itemNm </c:if>" >상품 종류</div>
									<div class="col-md-9 col-xs-12">
										<input class="width-100 checkItem <c:if test="${!empty optionItemVO.itemDivYn}"> itemVal </c:if>" type="text" name="itemDiv" id="itemDiv1" value="" />
									</div>
								</div>
							</c:if>
							<c:if test="${!empty expressItemVO.cusItemCodeYnExpress}">	
								<div class="col-md-6 col-xs-12 hr-8">
									<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.cusItemCodeYn}"> itemNm </c:if>" >상품 코드</div>
									<div class="col-md-9 col-xs-12">
										<input class="width-100 checkItem <c:if test="${!empty optionItemVO.cusItemCodeYn}"> itemVal </c:if>" type="text" name="cusItemCode" id="cusItemCode1" value="" />
									</div>
								</div>
							</c:if>
							<c:if test="${!empty expressItemVO.itemMeterialYnExpress}">	
								<div class="col-md-6 col-xs-12 hr-8">
									<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.itemMeterialYn}"> itemNm </c:if>" >상품재질</div>
									<div class="col-md-9 col-xs-12">
										<input class="width-100 checkItem <c:if test="${!empty optionItemVO.itemMeterialYn}"> itemVal </c:if>" type="text" name="itemMeterial" id="itemMeterial1" value="" />
									</div>
								</div>
							</c:if>
							<div class="col-md-6 col-xs-12 hr-8">
								<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.itemUserWtaYn}"> itemNm </c:if>" >상품 무게</div>
								<div class="col-md-9 col-xs-12">
									<input class="width-100 checkItem <c:if test="${!empty optionItemVO.itemUserWtaYn}"> itemVal </c:if>" type="text" name="itemUserYn" id="itemUserYn1" value="" />
								</div>
							</div>
							<%-- <c:if test="${!empty expressItemVO.hsCodeYnExpress}">
								<div class="col-md-6 col-xs-12 hr-8">
									<div class="col-md-3 col-xs-12 pd-1rem center mp07"" >상품 부피무게</div>
									<div class="col-md-9 col-xs-12">
										<input class="width-100 checkItem" type="text" name="userWtcItem" id="userWtc1" value="" />
									</div>
								</div>
							</c:if> --%>
							<c:if test="${!empty expressItemVO.itemWtUnitYnExpress}">
								<div class="col-md-6 col-xs-12 hr-8">
									<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.itemWtUnitYn}"> itemNm </c:if>" >무게 단위</div>
									<div class="col-md-9 col-xs-12">
										<input class="width-100 checkItem <c:if test="${!empty optionItemVO.itemWtUnitYn}"> itemVal </c:if>" type="text" name="wtUnitItem" id="wtUnitItem1" value="" />
									</div>
								</div>
							</c:if>
							<c:if test="${!empty expressItemVO.chgCurrencyYnExpress}">
								<div class="col-md-6 col-xs-12 hr-8">
									<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.chgCurrencyYn}"> itemNm </c:if>" >통화 단위</div>
									<div class="col-md-9 col-xs-12">
										<input class="width-100 checkItem <c:if test="${!empty optionItemVO.chgCurrencyYn}"> itemVal </c:if>" type="text" name="chgCurrency" id="chgCurrency1" value="" />
									</div>
								</div>
							</c:if>
							
							<c:if test="${!empty expressItemVO.itemBarcodeYnExpress}">
								<div class="col-md-6 col-xs-12 hr-8">
									<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.itemBarcodeYn}"> itemNm </c:if>" >상품 바코드</div>
									<div class="col-md-9 col-xs-12">
										<input class="width-100 checkItem <c:if test="${!empty optionItemVO.itemBarcodeYn}"> itemVal </c:if>" type="text" name="itemBarcode" id="itemBarcode1" value="" />
									</div>
								</div>
							</c:if>
								
							</div>
							
							<div class="hr dotted"></div>
								
							<c:if test="${(!empty expressItemVO.makeCntryYnExpress) or (!empty expressItemVO.makeComYnExpress)}">
							<div class="row  hr-8">
							<c:if test="${!empty expressItemVO.makeCntryYnExpress}">
								<div class="col-md-6 col-xs-12">
									<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.makeCntryYn}"> itemNm </c:if>" >제조국</div>
									<div class="col-md-9 col-xs-12">
										<input class="width-100 checkItem <c:if test="${!empty optionItemVO.makeCntryYn}"> itemVal </c:if>" type="text" name="makeCntry" id="makeCntry1" value="" />
									</div>
								</div>
							</c:if>
							<c:if test="${!empty expressItemVO.makeComYnExpress}">
								<div class="col-md-6 col-xs-12">
									<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.makeComYn}"> itemNm </c:if>" >제조 회사</div>
									<div class="col-md-9 col-xs-12">
										<input class="width-100 checkItem <c:if test="${!empty optionItemVO.makeComYn}"> itemVal </c:if>" type="text" name="makeCom" id="makeCom1" value="" />
									</div>
								</div>
							</c:if>
							</div>
							</c:if>
							<c:if test="${(!empty expressItemVO.itemUrlYnExpress) or (!empty expressItemVO.itemImgUrlYnExpress)}">
							<div class="row  hr-8">
							<c:if test="${!empty expressItemVO.itemUrlYnExpress}">	
								<div class="col-md-6 col-xs-12 hr-8">
									<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.itemUrlYn}"> itemNm </c:if>">상품 URL</div>
									<div class="col-md-9 col-xs-12">
										<input class="width-100 checkItem <c:if test="${!empty optionItemVO.itemUrlYn}"> itemVal </c:if>" type="text" name="itemUrl" id="itemUrl1" value="" />
									</div>
								</div>
							</c:if>
							<c:if test="${!empty expressItemVO.itemImgUrlYnExpress}">
								<div class="col-md-6 col-xs-12 hr-8">
									<div class="col-md-3 col-xs-12 pd-1rem center mp07 <c:if test="${!empty optionItemVO.itemImgUrlYn}"> itemNm </c:if>">상품 이미지 URL</div>
									<div class="col-md-9 col-xs-12">
										<input class="width-100 checkItem <c:if test="${!empty optionItemVO.itemImgUrlYn}"> itemVal </c:if>" type="text" name="itemImgUrl" id="itemImgUrl1" value="" />
									</div>
								</div>
							</c:if>
							</div>
							</c:if>
						</div>
					</div>
				</div>
				
			</div>
		</div>
	</div>
	<!-- 설정정보 End -->
	
	<div class="col-lg-12 col-xs-12">
	<br />
		<div class="col-lg-1 col-xs-6 col-lg-offset-5">
			<div id="button-div">
				<div id="backPage" style="text-align: center">
					<button id="backToList" type="button" class="btn btn-sm btn-danger">취소</button>
				</div>
			</div>
		</div>
		<div class="col-lg-1 col-xs-6">
			<div id="button-div">
				<div id="rgstrUser" style="text-align: center">
					<button id="rgstrInfo" type="button" class="btn btn-sm btn-primary">등록</button>
				</div>
			</div>
		</div>
	</div>
</div>
	
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
		<script src="/assets/js/jquery.ui.touch-punch.min.js"></script>
		<script src="/assets/js/jquery-ui.min.js"></script>
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
		
		<script src="/assets/js/bootstrap-tag.min.js"></script>
		<!-- ace scripts -->
		<script src="/assets/js/ace-elements.min.js"></script>
		<script src="/assets/js/chosen.jquery.min2.js"></script>
		<script src="/assets/js/ace.min.js"></script>
		<!-- script on paging end -->
		
		<!-- script addon start -->
		
		
		<script type="text/javascript">



		</script>
