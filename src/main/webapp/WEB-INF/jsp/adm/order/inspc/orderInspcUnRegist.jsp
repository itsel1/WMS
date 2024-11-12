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



        .imgs_wrap {
            border: 2px solid #A8A8A8;
            margin-top: 30px;
            margin-bottom: 30px;
            padding-top: 10px;
            padding-bottom: 10px;
            max-width: 100%;
            min-height: 145px;

        }
        .imgs_wrap img {
        	max-width: 150px;
        	max-height: 120px;
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
					<table id="dynamic-table" class="table table-bordered table-dark" style="width:70%">
						<thead>
							<tr>
								<th colspan="3" style="width:20%; text-align: center;">Trk No</th>
								<th colspan="3" style="width:13%; text-align: center;">RACK NO</th>
								<th colspan="3" style="width:13%; text-align: center;">USER ID</th>
								<th colspan="8" style="width:31%; text-align: center;">입고 메모</th>
								<th colspan="2"></th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td colspan="3"><input type="text" style="width:100%" id="trkNo" name="trkNo" value="${trkNo}"></td>
								<td colspan="3"><input type="text" style="width:100%" id="rackNo" name="rackNo" value="RACK#1"></td>
								<td colspan="3">
									<input type="text" style="width:65%;" id="userId" name="userId"> 
									<button type="button" style="width:30%;" class="btn btn-sm btn-white btn-primary right"id="searchId" name="searchId">검색</button>
								</td>
								<td colspan="8"><input type="text" style="width:100%" id="whMemo" name="whMemo"></td>
								<td colspan="2" rowspan="3"><button type="button" id="registBtn" class="btn btn-primary" style="font-size: 12px; width:100%;">미확인 등록</button></td>
							</tr>
							<tr>
								<td colspan="16" style="text-align: center; font-size: 20px; font-weight: bold;">사진 업로드</td>
							</tr>
							<tr>
								<td colspan="3">
									<div class="input_wrap" style="text-align: center;">
							            <div id="imgs_wrap1"class="imgs_wrap">
							            	<span class="red">* 재등록 시, 이미지 변경</span>
							            	<img id="img" />
							            </div>
										<a href="javascript:" class="my_button registImage" id="1">사진 업로드1</a>
										<br/>
							            <input type="file" id="input_imgs1" name="input_imgs1" />
							        </div>
						        </td>
						        <td colspan="3">
									<div class="input_wrap" style="text-align: center;">
							            <div id="imgs_wrap2"class="imgs_wrap">
							           		<span class="red">* 재등록 시, 이미지 변경</span>
							            	<img id="img" />
							            </div>
										<a href="javascript:" class="my_button registImage" id="2">사진 업로드2</a>
										<br/>
							            <input type="file" id="input_imgs2" name="input_imgs2" />
							        </div>
						        </td>
						        <td colspan="3">
									<div class="input_wrap" style="text-align: center;">
							            <div id="imgs_wrap3"class="imgs_wrap">
							            	<span class="red">* 재등록 시, 이미지 변경</span>
							            	<img id="img" />
							            </div>
										<a href="javascript:" class="my_button registImage" id="3">사진 업로드3</a>
										<br/>
							            <input type="file" id="input_imgs3" name="input_imgs3" />
							        </div>
						        </td>
						        <td colspan="3">
									<div class="input_wrap" style="text-align: center;">
							            <div id="imgs_wrap4"class="imgs_wrap">
							            	<span class="red">* 재등록 시, 이미지 변경</span>
							            	<img id="img" />
							            </div>
										<a href="javascript:" class="my_button registImage" id="4">사진 업로드4</a>
										<br/>
							            <input type="file" id="input_imgs4" name="input_imgs4" />
							        </div>
						        </td>
						        <td colspan="3">
									<div class="input_wrap" style="text-align: center;">
							            <div id="imgs_wrap5"class="imgs_wrap">
							            	<span class="red">* 재등록 시, 이미지 변경</span>
							            	<img id="img" />
							            </div>
										<a href="javascript:" class="my_button registImage" id="5">사진 업로드5</a>
										<br/>
							            <input type="file" id="input_imgs5" name="input_imgs5" />
							        </div>
						        </td>
							</tr>
						</tbody>
					</table>
					
				</form>
			</div><!-- /#home -->
		</div>
			
		<!-- script addon start -->
		<script type="text/javascript">
		jQuery(function($) {
			var sel_files = [];
			var targetId = "";
			$(document).ready(function() {
				$("#12thMenu").toggleClass('open');
				$("#12thMenu").toggleClass('active'); 
				$("#12thFor").toggleClass('active');
				$("#input_imgs1").on("change", handleImgFileSelect);
				$("#input_imgs2").on("change", handleImgFileSelect);
				$("#input_imgs3").on("change", handleImgFileSelect);
				$("#input_imgs4").on("change", handleImgFileSelect);
				$("#input_imgs5").on("change", handleImgFileSelect);
			});

			$(".registImage").on('click',function(e){
				targetId= $(this).attr("id");
	            $("#input_imgs"+$(this).attr("id")).trigger('click');
			})

	        function handleImgFileSelect(e) {

	            // 이미지 정보들을 초기화
	            sel_files = [];
	            $("#imgs_wrap"+targetId).empty();

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
	                    var html = "<a href=\"javascript:void(0);\" onclick=\"deleteImageAction("+index+")\" id=\"img_id_"+index+"\"><img src=\"" + e.target.result + "\" data-file='"+f.name+"' class='selProductFile' style=\"width:100%;heigth:150px;\"title='Click to remove'></a>";
	                    $("#imgs_wrap"+targetId).append(html);
	                    index++;

	                }
	                reader.readAsDataURL(f);
	                
	            });
	        }

	        function deleteImageAction(index) {
	            console.log("index : "+index);
	            console.log("sel length : "+sel_files.length);

	            sel_files.splice(index, 1);

	            var img_id = "#img_id_"+index;
	            $(img_id).remove(); 
	        }
			

			function searchDetail(){
				var formData = $("#dtSearch").serialize();
				$.ajax({
					url:'/mngr/order/orderInspcDetail',
					type: 'POST',
					data: formData,
					success : function(data) {
						$('#table-contents').html(data);
						$('#boxWeight').focus();
		            }, 		    
		            error : function(xhr, status) {
		                alert(xhr + " : " + status);
		                alert("수정에 실패 하였습니다. 수정사항을 확인해 주세요.");
		            }
				}) 

			}; 

			$("#registBtn").on('click',function(e){
				$('#orderInspForm').attr("action","/mngr/order/unRegistWhProcess");
				$('#orderInspForm').attr("method","post");
				$('#orderInspForm').submit();

			})

			$("#searchId").on('click',function(e){
				window.open("/mngr/order/orderInspcSearchId?idArea="+$("#userId").val(),"_blank","toolbar=yes,resizable=no,directories=no,width=480, height=360");

			})
		})
    </script>
		
	</body>
</html>
