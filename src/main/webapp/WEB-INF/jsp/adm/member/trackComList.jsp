<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<c:forEach items="${trkMap}" var="trkMap">
	<c:forEach items="${trkMap.value}" var="value" varStatus="status">
	<c:if test="${(status.index eq 0) or (value.dstnNation ne trkMap.value[status.index-1].dstnNation) }">
		<div class="col-lg-12 col-xs-12 hr-8" style="border-bottom: 1px solid #BBB; margin-top:10px;" name="${trkMap.key}" id="${trkMap.key}">
			${value.orgNation} - ${value.dstnNation}  
		</div>
	</c:if>
	
		<div class="col-lg-12 col-xs-12" style="" name="${value.orgNation}${value.dstnNation}_trkComs" id="${value.orgNation}${value.dstnNation}_trkComs">
		<div class="control-group">
			<div class="checkbox">
				<label>
					<input name="${value.transCode}" id="${value.transCode}" 
					<c:forEach items="${userTrkCode}" var="userTrk">
						<c:if test="${userTrk eq value.targetCode}" >checked</c:if>
					</c:forEach>
					type="checkbox" class="ace" value="${value.orgNation}${value.dstnNation}${value.transCode}"/>
					<span class="lbl"> ${value.transCode}</span>
				</label>
			</div>
		</div>
	</div>
	</c:forEach>
	<%-- <div class="col-lg-12 col-xs-12 hr-8" style="border-bottom: 1px solid #BBB; margin-top:10px;" name="${trkMap.key}" id="${trkMap.key}">
		${trkMap.value.dstnNation}
	</div> --%>
	<%-- <div class="col-lg-12 col-xs-12" style="" name="${trkMap.key}_trkComs" id="${trkMap.key}_trkComs">
		<div class="control-group">
			<div class="checkbox">
				<c:forEach items="${trkMap.value}" var="trkCom">
					<label>
						<input name="${trkMap.transCode}" id="${trkMap.transCode}" 
						<c:forEach items="${userTrkCode}" var="userTrk">
							<c:if test="${userTrk eq trkCom.trkCode}" >checked</c:if>
						</c:forEach>
						type="checkbox" class="ace" value="${trkCom.trkCode}"/>
						<span class="lbl"> ${trkCom.trkName}</span>
					</label>
				</c:forEach>
			</div>
		</div>
	</div> --%>
	
		<%-- 
		<c:forEach items="${userTrkCode}" var="userTrk">
							<c:if test="${userTrk.value eq trkCom.trkCode}" >checked</c:if>
						</c:forEach>
			${trkCom.key}<br/>
			${trkCom.value[0].nationName}<br/>
			<c:forEach items="${trkCom.value}" var="trkCom2">
				${trkCom2.nationName}
			</c:forEach>
		 --%>	
			
</c:forEach>
