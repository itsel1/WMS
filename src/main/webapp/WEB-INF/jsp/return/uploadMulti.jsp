<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.*" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
	</head>
	<body class="no-skin">
		<form method="POST" action="/uploadMulti" enctype="multipart/form-data">
		<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
		    <input type="file" name="files" /><br/>
		    <input type="file" name="files" /><br/>
		    <input type="file" name="files" /><br/>
		    <input type="submit" value="Submit" />
		</form>
			<a href="http://localhost:8080/downloadMultiPage">다운로드</a>
	</body>
</html>