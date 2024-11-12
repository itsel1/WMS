<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<%@ include file="/WEB-INF/jsp/importFile/head.jsp"%>
<script src="/assets/js/jquery-2.1.4.min.js"></script>
<script src="/assets/js/bootstrap.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-lite.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/summernote-lite.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/summernote@0.8.18/dist/lang/summernote-ko-KR.js"></script>
<style type="text/css">
.flex-div {
	display: flex;
	width: 100%;
	justify-content: flex-start;
	align-items: center;
	align-content: center;
	flex-direction: row;
	flex-wrap: nowrap;
	border: 1px solid #ddd;
	border-radius: 5px;
}

.element {
	text-align: center;
}

.element:nth-child(1n) {
	padding: 5px 20px;
	width: 10%;
	height: 30px;
}

.element:nth-child(1), .element:nth-child(3) {
	background: #F5F5F5;
	font-weight: 600;
}

.element:nth-child(2n) {
	padding: 0;
}

.element:nth-child(2) {
	width: 15%;
}

.element:nth-child(4) {
	width: 65%;
}

.element input[type=text] {
	width: 98%;
	height: 100%;
	border: none;
	outline: none;
}

.element select {
	width: 100%;
	height: 100%;
	border: none;
}

.flex-div2 {
	display: flex;
	width: 100%;
	justify-content: flex-start;
	align-items: center;
	align-content: center;
	flex-direction: row;
	flex-wrap: nowrap;
	border: 1px solid #ddd;
	border-radius: 5px;
}

.element2 {
	text-align: center;
}

.element2:nth-child(1) {
	background: #F5F5F5;
	font-weight: 600;
	padding: 5px 20px;
	width: 20%;
	height: 30px;
}

.element2:nth-child(2) {
	width: 70%;
	padding-left: 5px;
}

.element2:nth-child(3) {
	text-align: center;
	width: 10%;
	background: #F5F5F5;
	height: 30px;
}

#upBtn {
	border: none;
	outline: none;
	background: none;
	width: 100%;
	height: 30px;
	font-weight: 600;
}

#file-contents {
	display: flex;
	width: 100%;
	flex-direction: column;
}

#file-contents div:first-child {
	width: 100%;
	font-size: 13px;
	font-weight: 600;
	padding: 5px 10px;
	background: #F5F5F5;
}

#fileList {
	padding-left: 10px;
	padding-top: 5px;
	border: 1px solid #F5F5F5;
}

.file-remove {
	font-weight: 700;
	border: none;
	background: none;
	color: #CD0000;
	margin-left: 5px;
	font-size: 14px;
}

.file-del {
	font-weight: 600;
	border: none;
	background: none;
	color: #CD0000;
	margin-left: 5px;
	font-size: 12px; 
}

#reply-btn {
	width: 70px;
	height: 28px;
    cursor: pointer;
    color: #fff;
    background-color: rgba(10, 110, 205, 1);
    border: none;
    border-radius: 3px;
    box-shadow: 0 4px 4px rgba(82, 125, 150, 0.3);
    transition: 0.3s;
    text-align: center;
}

#reply-btn:hover {
    background-color: rgba(10, 110, 205, 0.7);
    box-shadow: 0 2px 4px rgba(82, 125, 150, 0.6);
}

.btns {
    width: 50px;
    height: 28px;
    cursor: pointer;
    color: #fff;
    background-color: rgba(105, 105, 105, 0.8);
    border: none;
    border-radius: 3px;
    box-shadow: 0 4px 4px rgba(82, 125, 150, 0.3);
    transition: 0.3s;
    text-align: center;
}

.btns:hover {
    background-color: rgba(105, 105, 105, 0.63);
    box-shadow: 0 2px 4px rgba(82, 125, 150, 0.6);
}

</style>
</head>
<title>게시판</title>
<body class="no-skin">
	<%@ include file="/WEB-INF/jsp/importFile/headMenu.jsp"%>
	<div class="main-container ace-save-state" id="main-container">
		<script type="text/javascript">
			try {ace.settings.loadState('main-container')} catch (e) {}
		</script>
		<%@ include file="/WEB-INF/jsp/importFile/managerSideMenu.jsp"%>
		<div class="main-content">
			<div class="main-content-inner">
				<div class="breadcrumbs ace-save-state" id="breadcrumbs">
					<ul class="breadcrumb">
						<li><i class="ace-icon fa fa-home home-icon"></i> Home</li>
						<li>게시판</li>
						<li class="active">출고지시</li>
					</ul>
				</div>
				<div class="page-content">
					<div class="page-header">
						<h1>출고 지시</h1>
					</div>
					<div id="inner-content-side">
						<form name="whoutForm" id="whoutForm" enctype="multipart/form-data">
							<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
							<input type="hidden" id="idx" name="idx" value="${noticeDetail.idx}">
							<input type="hidden" id="groupIdx" name="groupIdx" value="${noticeDetail.groupIdx}">
							<input type="hidden" name="indent" value="${noticeDetail.indent}">
							<input type="hidden" name="step" value="${noticeDetail.step}">
							<div style="width:60%;">
								<div id="">
									<div class="flex-div">
										<div class="element">분류</div>
										<div class="element">
											<select id="category" name="category">
												<option value="">::선택::</option>
												<option value="A" <c:if test="${noticeDetail.category eq 'A'}">selected</c:if>>출하지시</option>
												<option value="B" <c:if test="${noticeDetail.category eq 'B'}">selected</c:if>>입고안내</option>
											</select>
										</div>
										<div class="element">제목</div>
										<div class="element">
											<input type="text" name="title" id="title" value="${noticeDetail.title}">
										</div>
									</div>		
								</div>
								<textarea id="summernote" name="content"></textarea>
								<div>
								<c:if test="${userId eq noticeDetail.WUserId}">
									<div class="flex-div2">
										<div class="element2">첨부파일 (최대 5개)</div>
										<div class="element2">
											<input type="file" id="addFiles" name="addFiles" multiple="multiple">
										</div>
									</div>
								</c:if>
								<c:if test="${userId ne noticeDetail.WUserId}">
									<div class="flex-div2" style="display:none;">
										<div class="element2">첨부파일 (최대 5개)</div>
										<div class="element2">
											<input type="file" id="addFiles" name="addFiles" multiple="multiple">
										</div>
									</div>
								</c:if>
								</div>
								<div id="file-contents">
									<div>파일 리스트</div>
									<div id="fileList">
										<c:if test="${!empty fileList}">
											<c:forEach items="${fileList}" var="fileList" varStatus="status">
												<p id="file${fileList.idx}" class="fileList">
													<a href="${fileList.fileDir}">${fileList.fileName}</a>
													<c:if test="${userId eq noticeDetail.WUserId}">
														<button data-index="file${fileList.idx}" class="file-del" onclick="fn_delFile('${fileList.idx}', '${fileList.noticeIdx}')" type="button">삭제</button>
													</c:if>
												</p>
											</c:forEach>
										</c:if>
									</div>
								</div>
								<br>
								<div style="width:100%;">
									<div style="width:50%;float:left;text-align:left;">
										<input type="button" id="backToList" class="btns" value="목록">
									</div>
									<div style="width:50%;float:right;text-align:right;">
										<input type="button" id="reply-btn" class="btns" value="답글쓰기">
										<c:if test="${userId eq noticeDetail.WUserId}">
											<input type="button" id="delete-btn" class="btns" value="삭제">
											<input type="button" id="update-btn" class="btns" value="수정">
										</c:if>
									</div>
								</div>
							</div>
						</form>
					</div>
				</div>
			</div>
		</div>
	</div>
	<%@ include file="/WEB-INF/jsp/importFile/footer.jsp"%>
	<script type="text/javascript">
		if ('ontouchstart' in document.documentElement)
			document.write("<script src='/assets/js/jquery.mobile.custom.min.js'>"+ "<"+"/script>");
	</script>
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

	<script type="text/javascript">
		var fileCnt = 0;
		var totalCnt = 5;
		var nowCnt = 0;
		nowCnt = $(".fileList").length;
		if (nowCnt > 0) {
			fileCnt = nowCnt;
		}

		jQuery(function($) {
			$(document).ready(function() {
				$("#11thMenu").toggleClass('open');
				$("#11thMenu").toggleClass('active');
				$("#whoutOrder").toggleClass('active');
				var toolbar = [
				    // 글꼴 설정
				    ['fontname', ['fontname']],
				    // 글자 크기 설정
				    ['fontsize', ['fontsize']],
				    // 굵기, 기울임꼴, 밑줄,취소 선, 서식지우기
				    ['style', ['bold', 'italic', 'underline','strikethrough', 'clear']],
				    // 글자색
				    ['color', ['forecolor','color']],
				    // 표만들기
				    ['table', ['table']],
				    // 글머리 기호, 번호매기기, 문단정렬
				    ['para', ['ul', 'ol', 'paragraph']],
				    // 줄간격
				    ['height', ['height']],
				    // 코드보기, 확대해서보기, 도움말
				    ['view', ['codeview', 'help']]
				  ];
				
				<!-- value 를 먼저 넣고 초기화 한다. -->  
				$("#summernote").val("${fn:replace(noticeDetail.content,'\"','\\"')}");
				$('#summernote').summernote({
			    	placeholder: '내용을 작성하세요',
			        height: 300,
			        lang: 'ko-KR',
		            focus : true,
		            lang : 'ko-KR',
		            toolbar : toolbar,
		            callbacks : { //여기 부분이 이미지를 첨부하는 부분
		               onImageUpload : function(files, editor, welEditable) {
			               for (var i = files.length - 1; i >= 0; i--) {
			               uploadSummernoteImageFile(files[i],
			               this);
			               }
		               }
	                }
				});

			})
		})

        const handler = {
            init() {
                const fileInput = document.querySelector('#addFiles');
                const preview = document.querySelector('#fileList');
                fileInput.addEventListener('change', () => {  
                    const files = Array.from(fileInput.files);
                    if (fileCnt + files.length > totalCnt) {
                        alert("파일은 최대 "+totalCnt+"개까지 업로드 가능합니다.");
                        $("#addFiles").val("");
                        return;
                    } else {
						fileCnt = fileCnt + files.length;
                    }
                    files.forEach(file => {
	                    preview.innerHTML += '<p id="'+file.lastModified+'" class="fileList">'+file.name+
	                    	'<button data-index="'+file.lastModified+'" class="file-remove">x</button></p>';
                    });
                });
            },

            removeFile: () => {
                document.addEventListener('click', (e) => {
	                if(e.target.className !== 'file-remove') return;
	                const removeTargetId = e.target.dataset.index;
	                const removeTarget = document.getElementById(removeTargetId);
	                const files = document.querySelector('#addFiles').files;
	                const dataTranster = new DataTransfer();

	                Array.from(files)
	                    .filter(file => file.lastModified != removeTargetId)
	                    .forEach(file => {
	                    dataTranster.items.add(file);
	                 });
	    
	                document.querySelector('#addFiles').files = dataTranster.files;

	                removeTarget.remove();
	                fileCnt--;
            	})
            }
        };

        handler.init();
        handler.removeFile();


		$("#update-btn").on('click', function() {
			$("#whoutForm").attr("action", "/mngr/board/noticeUp");
			$("#whoutForm").attr("method", "post");
			$("#whoutForm").submit();
		})
		
		$("#backToList").on('click', function() {
			location.href = '/mngr/board/noticeList';
		})
		
		$("#reply-btn").on('click', function() {
			location.href = '/mngr/board/noticeReplyIn?idx='+$("#idx").val()+'&groupIdx='+$("#groupIdx").val();
		})
		
		$("#delete-btn").on('click', function() {
			if (confirm("게시글을 삭제 하시겠습니까?")) {
				$.ajax({
					url : '/mngr/board/noticeDel',
					type : 'POST',
					beforeSend : function(xhr)
					{
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					data : $("#whoutForm").serialize(),
					success : function(data) {
						if (data.rstCode == 'S10') {
							location.href = '/mngr/board/noticeList';
						} else {
							alert(data.rstMsg);
						}
					},
					error : function(xhr, status) {
						alert("내부 처리 중 오류가 발생 하였습니다. 관리자에게 문의하세요.");
					}
				})
				
			} else {
				return;
			}
		})

		function fn_delFile(idx, noticeIdx) {
			if(confirm("정말 삭제 하시겠습니까?")) {
				$.ajax({
					url : '/mngr/board/noticeFileDel',
					type : 'POST',
					data : {idx: idx, noticeIdx: noticeIdx},
					beforeSend : function(xhr)
					{
					    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
					},
					success : function(data) {
						if (data.status == 'S') {
							alert(data.msg);
							$("#file"+idx).remove();
							fileCnt--;
							console.log(fileCnt);
						} else {
							alert(data.msg);
						}
					},
					error: function(xhr, status) {
						alert("내부 처리 중 오류가 발생 하였습니다. 관리자에게 문의하세요.");
					}
				})	
			} else {
				return false;
			}
		} 

	
	</script>
</body>
</html>