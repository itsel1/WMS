<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	
	<!-- basic scripts -->

		<!--[if !IE]> -->
		<script src="/assets/js/jquery-2.1.4.min.js"></script>

		<!-- <![endif]-->

		<!--[if IE]>
		<script src="assets/js/jquery-1.11.3.min.js"></script>
		<![endif]-->
		<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
		</script>
		<script src="/assets/js/bootstrap.min.js"></script>

		<!-- page specific plugin scripts -->

		<!-- ace scripts -->
		<script src="/assets/js/ace-elements.min.js"></script>
		<script src="/assets/js/ace.min.js"></script>
		<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/codemirror/5.48.4/codemirror.min.css"/>
		  <!-- Editor's Style -->
		  <link rel="stylesheet" href="https://uicdn.toast.com/editor/2.0.0/toastui-editor.min.css" />
	<!-- basic scripts End-->
	</head> 
	<title>사용자 관리</title>
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
								<a href="#">대 메뉴</a>
							</li>
	
							<li>
								<a href="#">중 메뉴</a>
							</li>
							<li class="active">소 메뉴</li>
						</ul><!-- /.breadcrumb -->
					</div>
					
					<div id="inner-content-side">
						<div id='editor' name='editor'>
		
						</div>
						<input type="hidden" id="testssss" value='${testssss }'/>
						<button id="test">asd</button>
					</div>
				</div>
			</div><!-- /.main-content -->
		</div>
			
		<!-- Main container End-->
		
		<!-- Footer Start -->
		<%@ include file="/WEB-INF/jsp/importFile/footer.jsp" %>
		<!-- Footer End -->
	</body>
	<script src="https://uicdn.toast.com/editor/latest/toastui-editor-all.min.js"></script>
	<script>
	var imagename;
	var urls = "http://localhost:8080/";
	$(document).ready(function() {
		editor.setHtml($("#testssss").val());
	});
	const editor = new toastui.Editor({
	    el: document.querySelector('#editor'),
	    hooks:{
		    addImageBlobHook: function(blob, callback,){
		    	var pullname;
		    	var form=blob;
		    	var test;
				var formData = new FormData();
				formData.append('files',blob);
				$.ajax({
					url:'/testImageUp',
					data : formData,
					contentType : false,
			        processData : false, 
					type: 'POST',
					success : function(data) {
						alert(data);
						imagename=data;
						pullname = urls+'image/'+imagename;
						callback(pullname,'alt text');
		            }, 		    
		            error : function(xhr, status) {
		                alert(xhr + " : " + status);
		                alert("수정에 실패 하였습니다. 수정사항을 확인해 주세요.");
		            }
				}) 
				
		    }
	    },
	    previewStyle: 'vertical',
	    initialEditType: "wysiwyg",
	    height: '500px',
	    /* hideModeSwitch : true, */
	});


	$("#test").on("click",function(){
		$.ajax({
			url:'/testT',
			type: 'POST',
			data: {testTS:editor.getHtml()} ,
			success : function(data) {
				alert("SS")
            }, 		    
            error : function(xhr, status) {
                alert(xhr + " : " + status);
                alert("수정에 실패 하였습니다. 수정사항을 확인해 주세요.");
            }
		}) 
	})
	</script>
</html>
