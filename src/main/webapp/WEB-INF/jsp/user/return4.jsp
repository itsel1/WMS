<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>유저 정보 출력3 </title>
</head>
<body>
	
	name : ${users[0].userName }<br>
	tel : ${users[0].userTel }<br>
	
	<c:forEach var="users" items="${users }" varStatus = "status">
		<p>name: ${users.userName } <br> tel: ${users.userTel } <br>addr: ${users.userAddr } <br>userAddrDetail: ${users.userAddrDetail } <br> email : ${users.userEmail } <br>ETPR_YN:${users.etprYn }<br>
		APRV_YN : ${users.aprvYn }<br> HP:${users.userHp } <br> storeName : ${users.storeName } <br> storeUrl: ${users.storeUrl } <br>com_E_Name : ${users.comEName } <br>comName: ${users.comName }</p> 
	</c:forEach>
	
</body>
</html>