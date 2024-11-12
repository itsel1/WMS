<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="ko">
<head>
<meta charset="UTF-8">
<title>출고 - 수출신고</title>
	<link rel="shortcut icon" href="#">
	<link rel="stylesheet" href="https://uicdn.toast.com/grid/latest/tui-grid.css" />
</head>
<body>
	<div id="grid"></div>
	<script src="https://uicdn.toast.com/grid/latest/tui-grid.js"></script>
	<script type="text/javascript">
	window.onload = function() {
		let httpRequest;
		httpRequest = new XMLHttpRequest();

		httpRequest.onreadystatechange = () => {
			if (httpRequest.readyState === XMLHttpRequest.DONE) {
				if (httpRequest.status === 200) {
					var result = httpRequest.response;
				}
			}
		}
	}

	
	/*
	const grid = new tui.Grid({
		el: document.getElementById('grid'),
		scrollX: false,
		scrollY: false,
		columns: [
			{
				header: 'Name',
				name: 'name'
			},
			{
				header: 'Artist',
				name: 'artist'
			},
			{
				header: 'Type',
				name: 'type'
			},
			{
				header: 'Release',
				name: 'release'
			},
			{
				header: 'Genre',
				name: 'genre'
			}
		]
	});
	*/
	</script>
</body>
</html>