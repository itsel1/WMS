<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<meta charset="UTF-8">
<title>반품 접수 내역</title>
<head>
<link rel="stylesheet" href="/assets/css/jquery-ui.min.css" />
<link rel="stylesheet" href="/assets/css/chosen.min.css" />
<link rel="stylesheet" href="/assets/css/orderIn.css"/>
<script src='https://kit.fontawesome.com/a076d05399.js' crossorigin='anonymous'></script>
<style type="text/css">
.modal.modal-center {
	  text-align: center;
}

.modal { 
   background-color: rgba(69, 69, 69, 0.79) !important; 
}

@media screen and (min-width: 768px) { 
  .modal.modal-center:before {
    display: inline-block;
    vertical-align: middle;
    content: " ";
    height: 100%;
  }
}

.modal-dialog.modal-center {
  display: inline-block;
  text-align: left;
  vertical-align: middle; 
 }
 
 #checkModal {
 	width: 50%;
 }

#agreeChk {
	position: relative;
	top: 4px;
}

#btn-row {
	text-align: center;
	witdh: 100%;
	margin-top: 20px;
	margin-bottom: 20px;
}


.buttons {
	padding: 10px 50px;
	text-align: center;
	font-size: 14px;
	font-weight: 500;
	color: #fff;
	curosr: pointer;
	margin: 10px;
	border: none;
	background-size: 300% 100%;
	border-radius: 5px;
	moz-transition: all .4s ease-in-out;
	-o-transition: all .4s ease-in-out;
	-webkit-transition: all .4s ease-in-out;
	transition: all .4s ease-in-out; 
	background-image: linear-gradient(to right, #29323c, #485563, #2b5876, #4e4376);
	box-shadow: 0 4px 15px 0 rgba(45, 54, 65, 0.75);
}

.buttons:hover {
	background-position: 100% 0;
	moz-transition: all .4s ease-in-out;
	-o-transition: all .4s ease-in-out;
	-webkit-transition: all .4s ease-in-out;
	transition: all .4s ease-in-out;
	box-shadow: 0 1px 6px 0 rgba(45, 54, 65, 0.75);
}

.buttons:focus {
	outline: none;
}


</style>
<%@ include file="/WEB-INF/jsp/importFile/user/head.jsp" %>
</head>
<body>
 	<div class="site-wrap">
		<%@ include file="/WEB-INF/jsp/importFile/user/headMenu.jsp" %>
    </div>
    <form name="form" id="form">
    	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
    	<div class="container">
    		<div class="page-content">
    			<div class="modal modal-center fade" id="myModal" role="dialog" data-backdrop="static" data-keyboard="false">
					<div class="modal-dialog modal-center" id="checkModal">
						<div class="modal-content">
							<div class="modal-header">
								<button type="button" class="close" data-dismiss="modal">&times;</button>
								<h4 id="modal-title" class="modal-titel" style="font-weight:bold;">반품 사용 안내</h4>
							</div>
							<div class="modal-body">
								<table class="noticeTable" style="width:100%;">
									<tr>
										<td style="border:1px solid #527d96; font-size:14px; width:100%; height:140px; padding:10px 14px;">
										ACI는 이용자의 개인정보를 사전 동의 없이 반품회수대행 업무 범위를 초과하여 이용하거나 원칙적으로 이용자의 개인정보를 외부에 공개하지 않습니다.
										<br/>
										다만 아래의 경우에는 예외로 합니다.
										<br/><br/>
										- 법령의 규정에 의거하거나, 수사 목적으로 법령에 정해진 절차와 방법에 따라 수사기관의 요구가 있는 경우
										<br/>
										- 반품회수대행 서비스를 위한 당사자간(ACI의 서비스 위탁 업체) 원활한 의사소통 및 배송, 상담 등 거래여행을 위하여 관련된 정보를 필요 범위 내에서 거래 당사자에게 제공에 동의한 경우
										</td>
									</tr>
									<tr>
										<td style="text-align:right;padding-top:5px;padding-right:5px;">
											<input type="checkbox" id="agreeChk">
											<label for="agreeChk"> 동의 합니다</label>
										</td>
									</tr>
								</table>
								<div id="btn-row">
									<button type="button" class="buttons" id="otherBtn">타사 배송<br>(신규)</button>
									<button type="button" class="buttons" id="wmsBtn">자사 배송<br>(WMS)</button>
									<button type="button" class="buttons" id="eshopBtn">자사 배송<br>(Eshop)</button>
								</div>
							</div>
						</div>
					</div>
				</div>
    		</div>
   		</div>
    </form>
    <%@ include file="/WEB-INF/jsp/importFile/footer.jsp" %>
	<script src="/assets/js/jquery-2.1.4.min.js"></script>
	<script src="https://cdn.jsdelivr.net/npm/sweetalert2@10"></script>
	<script type="text/javascript">
		if('ontouchstart' in document.documentElement) document.write("<script src='/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
	</script>
	<script src="/assets/js/bootstrap.min.js"></script>
	
	<script src="/assets/js/jquery-2.1.4.min.js"></script>
	<script src="/testView/js/jquery-ui.js"></script>
	<script src="/testView/js/popper.min.js"></script>
	<script src="/assets/js/bootstrap.min.js"></script>
	<script src="/testView/js/owl.carousel.min.js"></script>
	<script src="/testView/js/jquery.magnific-popup.min.js"></script>
	<script src="/testView/js/aos.js"></script>
	<script src="/testView/js/main.js"></script>
	
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
	<script src="/assets/js/ace-elements.min.js"></script>
	<script src="/assets/js/chosen.jquery.min.js"></script>
	<script src="/assets/js/ace.min.js"></script>
	
	<script type="text/javascript">
		jQuery(function($) {
			$(document).ready(function() {
				$('#myModal').modal("show");
			})

			$("#myModal").on('hidden.bs.modal', function(e) {
				location.href = "/cstmr/return/orderList";
			})

			$("#agreeChk").on('click', function() {
				$("#agreeChk").attr("disabled", true);
			})

			$("#otherBtn").on('click', function() {
				fn_checked('new');
			})
			
			$("#wmsBtn").on('click', function() {
				fn_checked('wms');
			})

			$("#eshopBtn").on('click', function() {
				fn_checked('eshop');
			})
		})
		
		function fn_checked(obj) {
			var checked = $("#agreeChk").is(":checked");
			if (!checked) {
				alert("반품 사용 안내서에 동의해 주세요.");
				return;
			}

			$("#checkModal").addClass('hide');
			Swal.fire({
				icon: 'success',
			  	title: '반품 사용 안내서에 동의 하셨습니다.',
				toast: true,
				position: 'center-center',
				showConfirmButton: false,
				timer: 800,
				timerProgressBar: true,
				didOpen: (toast) => {
					toast.addEventListener('mouseenter', Swal.resumeTimer)
					toast.addEventListener('mouseleave', Swal.resumeTimer)
				}
			})
			
			setTimeout(function() {
				$("#myModal").modal('hide');
				if (obj == 'new') {
					location.href = "/cstmr/return/orderIn";	
				} else if (obj == 'wms') {
					location.href = "/cstmr/return/orderInAci?type=w";
				} else {
					location.href = "/cstmr/return/orderInAci?type=e";	
				}
			}, 800);
		}
	</script>
</body>
</html>