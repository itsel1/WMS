<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
</head>
<body class="no-skin">
	<table id="dynamic-table" class="table table-bordered table-hover">
		<thead> 
			<tr>
				<th>
					국가명
				</th>
				<th>	
					기본 할인율
				</th>
				<th>	
					부피무게 할인율
				</th>
				<th>
					적용 요율표 명
				</th>
				<th>
					수기등록 여부
				</th>
				<th>
					검품 비용
				</th>
				<th>
					건당 수수료
				</th>
				<th>
					통관 수수료
				</th>
				<th>
					수출 면장 비용
				</th>
				<th>
					유류 할증료 적용 방식 / 유류 할증료
				</th>
				<th>surcharge 적용 방식 / surcharge</th>
				<th>초기화
				</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach items="${dlvChgInfo}" var="dlvChg" varStatus="status">
				<tr>
					<td>
						${dlvChg.dstnNation}
					</td>
					<td>
						${dlvChg.actualDiscount}
					</td>
					<td>
						${dlvChg.volumeDiscount}
					</td>
					<td>
						${dlvChg.dlvPriceName}
					</td>
					<td>
						${dlvChg.dvlChgType}
					</td>
					<td>
						검품비용
					</td>
					<td>
						${dlvChg.caseFee} ${dlvChg.currency}
					</td>
					<td>
						${dlvChg.clearanceFee} ${dlvChg.currency}
					</td>
					<td>
						${dlvChg.exportDeclFee} ${dlvChg.currency}
					</td>
					<td>
					${dlvChg.surchargeType} / ${dlvChg.fuelSurcharge} <c:if test="${dlvChg.surchargeType eq 'per'}">%</c:if> <c:if test="${dlvChg.surchargeType eq 'WT'}">(${dlvChg.fuelWtUnit} Kg)</c:if>
					</td>
					<td>
						${dlvChg.surchargeType2} / ${dlvChg.surcharge} <c:if test="${dlvChg.surchargeType2 eq 'per'}">%</c:if> <c:if test="${dlvChg.surchargeType2 eq 'WT'}">(${dlvChg.surWtUnit} Kg)</c:if>
					</td>
					<td>
						<c:if test="${dlvChg.dvlChgType ne 'Menual'}"><span onclick="fn_targetReset('${dlvChg.dstnNation}')" style="cursor: pointer; color:red;">초기화</span></c:if>
					</td>
				</tr>
			</c:forEach>
				
		</tbody>
	</table>
</body>
</html>