<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<%@ include file="/WEB-INF/jsp/importFile/head.jsp"%>
<style type="text/css">
.container {
	max-width: 50%;
	margin: 0 auto;
}

.mm-survey {
	margin-top: 30px;
}

.mm-survey-container {
	width: 100%;
	min-height: 600px;
	background: #fafafa;
}

.mm-survey-results-container {
	width: 100%;
	min-height: 600px;
	background: #fafafa;
}

.mm-survey-page {
	display: none;
	font-family: 'Raleway';
	font-weight: 100;
	padding: 40px;
}

.mm-survey-page.active {
	display: block;
}

.mm-survey-controller {
	position: relative;
    height: 60px;
    background: #333;
    padding: 12px 14px;
}

.mm-survey-results-controller {
	position: relative;
    height: 60px;
    background: #333;
    padding: 12px 14px;
}

.mm-back-btn {
	display: inline-block;
    position: relative;
    float: left;
}

.mm-prev-btn {
	display: inline-block;
    position: relative;
    float: left;
}

.mm-next-btn {
	display: inline-block;
	opacity: 0.25;
    position: relative;
    float: right;
}

.mm-finish-btn {
	display: none;
    position: relative;
    float: right;
}

.mm-finish-btn button {
	background: #3DD2AF !important;
    color: #fff;
}

.mm-survey-controller button {
	background: #fff;
    border: none;
    padding: 8px 18px;
    font-family: 'Raleway';
    font-weight: 300;
}

.mm-survey-results-controller button {
	background: #fff;
	border: none;
	padding: 8px 18px;
}

.mm-survey-progress {
	width: 100%;
	height: 30px;
	background: #f5f5f5;
	overflow: hidden;
}

.mm-progress {
	transition: width 0.5s ease-in-out;
}

.mm-survey-progress-bar {
	height: 30px;
    width: 0%;
    background: linear-gradient(to left, #4CB8C4, #3CD3AD);
}

.mm-survey-q {
	list-style-type: none;
	padding: 0px;
}

.mm-survey-q li {
	display: block;
	/*padding: 20px 0px;*/
	margin-bottom: 10px;
	width: 100%;
	background: #fff;
}

.mm-survey-q li input {
	width: 100%;
}

.mm-survery-content label {
	width: 100%;
	padding: 10px 10px;
	margin: 0px !important;
}

.mm-survery-content label:hover {
	cursor: pointer;
}


.mm-survey-question p {
	font-size: 22px;
    font-weight: 200;
    margin-bottom: 20px;
    line-height: 40px;
}

.mm-survery-content label p {
	display: inline-block;
    position: relative;
    top: 2px;
    left: 5px;
    margin: 0px;
}

input[type="radio"] {
	display: none;
}

input[type="radio"] + label {
	color: #292321;
    font-family: 'Raleway';
    font-weight: 300;
    font-size: 16px;
}

input[type="radio"] + label span {
	display: inline-block;
	width: 30px;
	height: 30px;
	margin: 2px 4px 0 0;
	vertical-align: middle;
	cursor: pointer;
	-moz-border-radius: 50%;
	border-radius: 50%;
}

input[type="radio"] + label span {
 	background-color: #333;
}

input[type="radio"]:checked + label span {
	border: 2px solid #3DD2AF;
	background: transparent;
}

input[type="radio"] + label span,
input[type="radio"]:checked + label span {
	-webkit-transition: background-color 0.20s ease-in-out;
	-o-transition: background-color 0.20s ease-in-out;
	-moz-transition: background-color 0.20s ease-in-out;
	transition: background-color 0.20s ease-in-out;
}

.mm-survey-item {
	background: #fff;
	margin-bottom: 15px;
	border-bottom: 1px solid rgba(33,33,33,0.15);
    border-radius: 0px 0px 4px 4px;
}

.mm-prev-btn button:focus, .mm-next-btn button:focus, .mm-finish-btn button:focus {
	outline: none;
	border: none;
}

.mm-survey.okay .mm-next-btn {
	display: inline-block;
	opacity: 1;
}

.mm-finish-btn.active {
	display: inline-block;
}

.mm-survey-results {
	display: none;
}

.mm-survey-results-score {
	margin: 0px;
    padding: 0px;
    padding-top: 40px;
    padding-bottom: 40px;
    text-align: center;
    font-size: 80px;
    font-family: 'Raleway';
    font-weight: 600;
    letter-spacing: -6px;
}

.mm-survey-results-list {
	list-style-type: none;
	padding: 0px 15px;
    margin: 0px;
}

.mm-survey-results-item {
	color: #fff;
    margin-top: 10px;
    padding: 15px 15px 15px 0px;
    font-family: 'Raleway';
    font-weight: 300;
}

.mm-survey-results-item.correct {
	background: linear-gradient(to left, #4CB8C4, #3CD3AD);
}

.mm-survey-results-item.incorrect {
	background: linear-gradient(to left, #d33c62, #dc1144);
}

.mm-item-number {
	height: 40px;
	position: relative;
	padding: 17px;
	background: #333;
	color: #fff;
}

.mm-item-info {
	float: right;
}

*, h2 {
	font-family: 'Gowun Dodum', sans-serif;
}

.row {
	font-size: 16px;
	font-weight: bold;
}
</style>
<!-- basic scripts -->
<link rel="stylesheet" href="/assets/css/chosen.min.css" />
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Gowun+Dodum&display=swap" rel="stylesheet">
</head>
<title>회원 가입</title>
<body>
	<div class="main-container ace-save-state" id="main-container">
		<script type="text/javascript">
			try {
				ace.settings.loadState('main-container')
			} catch (e) {
			}
		</script>
		<div class="main-content">
			<form name="userInfoForm" id="userInfoForm" >
				<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
				<div class="main-content-inner">
					<div class="page-content">
						<div class="page-header">
							<h1>
								<i class="fa fa-leaf"></i>
								WMS
							</h1>
						</div>
						<div id="inner-content-side">
							<div style="text-align: center;">
								<h2>일반 회원</h2>
							</div>
							<div class="container">
								<div class="col-sm-12">
									<div class="mm-survey">
										<div class="mm-survey-progress">
											<div class="mm-survey-progress-bar mm-progress"></div>
										</div>
						
										<div class="mm-survey-results">
											<div class="mm-survey-results-container">
												<h3 class="mm-survey-results-score"></h3>
												<ul class="mm-survey-results-list"></ul>
											</div>
											<div class="mm-survey-results-controller">
												<div class="mm-back-btn">
													<button>Back</button>
												</div>
											</div>
										</div>
						
										<div class="mm-survey-bottom">
											<div class="mm-survey-container">
						
												<div class="mm-survey-page active" data-page="1">
													<div class="mm-survery-content">
														<div class="mm-survey-question">
															<p>기본 정보</p>
														</div>
														<div class="row">
															<div class="col-xs-12 col-lg-12" >
																<div class="col-xs-12 col-lg-12" style="border: 1px solid #dee2e6!important; padding: 3rem!important; background:#fff;">
																	<div class="row form-group hr-8">
																		<div class="col-lg-8 col-xs-12">
																			아이디<small class="red"> *</small><div class="hr-8"></div>
																			<input class="width-70" type="text" name="userId" id="userId" value="" />&nbsp;&nbsp;&nbsp;
																			<button id="checkId" class="btn btn-primary btn-sm" name="checkId" type="button">중복확인</button>
																			<div class="row" style="margin-left:8px; margin-top:5px;">
																				<small class="blue">아이디는 영문으로 시작하며 영문 또는 숫자를 포함한 6~20자리여야 합니다.</small>
																			</div>
																		</div>
																	</div>
																	<div class="row form-group hr-8">
																		<div class="col-lg-6 col-xs-12">
																			패스워드<small class="red"> *</small><div class="hr-8"></div>
																			<input class="width-100" type="password" name="password1" id="password1" value="" />
																		</div>
																		<div class="col-lg-6 col-xs-12">
																			패스워드 확인<small class="red"> *</small><div class="hr-8"></div>
																			<input class="width-100" type="password" name="password2" id="password2" value="" />
																			<span class="" style="display:block;" id="passwordMsg"></span>
																		</div>
																		<div class="row" style="margin-left:20px; margin-top:20px;">
																			<small class="blue">아이디는 영문으로 시작하며 영문 또는 숫자를 포함한 6~20자리여야 합니다.</small>
																		</div>
																	</div>
																	<input type="hidden" name="userPw" id="userPw"/>
																	<div class="row form-group hr-8">
																		<div class="col-lg-6 col-xs-12">
																			<label>담당자 명</label>
																			<input class="width-100" type="text" name="userName" id="userName" value="" />
																		</div>
																		<div class="col-lg-6 col-xs-12">
																			<label>상호명</label>
																			<input class="width-100" type="text" name="comName" id="comName" value="" />
																		</div>
																	</div> 
																	<div class="row form-group hr-8">
																		
																		<div class="col-lg-6 col-xs-12">
																			<label>Email</label>
																			<input class="width-100" type="text" name="userEmail" id="userEmail" value="" />
																		</div>
																	</div>
																	<div class="row form-group hr-8">
																		<div class="col-lg-12 col-xs-12">
																			<label class="bigger-110">우편번호</label><br/>
																			<input type="text" class="width-30 hr-8" name="userZip" id="userZip" value="" /> 
																			<button style="margin-left:10px;" id="zipBtn" type="button" class="btn btn-white btn-primary">주소검색
																				<i class="ace-icon glyphicon glyphicon-map-marker icon-on-right"></i>
																			</button>
																			<br>
																			<label class="bigger-110">주소</label><br/>
																			<input type="text" class="width-100 hr-8" name="userAddr" id="userAddr" value="" />
																			<label class="bigger-110">상세 주소</label><br/>
																			<input type="text" class="width-100 hr-8" name="userAddrDetail" placeholder="상세 주소" id="userAddrDetail" value="" />
																		</div>
																	</div>
																	
																</div>
															</div>
														</div>
													</div>
												</div>
												<div class="mm-survey-page" data-page="2">
													<div class="mm-survery-content">
														<div class="mm-survey-question">
															<p>세관 신고 정보</p>
														</div>
													</div>
												</div>
												<div class="mm-survey-page" data-page="3">
													<div class="mm-survery-content">
														<div class="mm-survey-question">
															<p>청구지 정보</p>
														</div>
													</div>
												</div>
												<div class="mm-survey-page" data-page="4">
													<div class="mm-survery-content">
														<div class="mm-survey-question">
															<p>설정 정보</p>
														</div>
													</div>
												</div>
											</div>
											<div class="mm-survey-controller">
												<div class="mm-prev-btn">
													<button>Prev</button>
												</div>
												<div class="mm-next-btn">
													<button>Next</button>
												</div>
												<div class="mm-finish-btn">
													<button>Submit</button>
												</div>
											</div>
										</div>
									</div>
								</div>
							</div>
						</div>
					</div>
				</div>
			</form>
		</div>
	</div>

	<!-- /.MultiStep Form -->

	<!-- Main container End-->


	<!--[if !IE]> -->
	
	<script src="/assets/js/jquery-2.1.4.min.js"></script>
	<script src="/testView/js/jquery-ui.js"></script>
	<script src="/testView/js/popper.min.js"></script>
	<script src="/assets/js/bootstrap.min.js"></script>
	<script src="/testView/js/owl.carousel.min.js"></script>
	<script src="/testView/js/jquery.magnific-popup.min.js"></script>
	<script src="/testView/js/aos.js"></script>
	
	<script src="/testView/js/main.js"></script>
	<!-- <![endif]-->
	<!--[if IE]>
		<script src="/assets/js/jquery-1.11.3.min.js"></script>
		<![endif]-->
	<script type="text/javascript">
		if ('ontouchstart' in document.documentElement)
			document.write("<script src='/assets/js/jquery.mobile.custom.min.js'>"
							+ "<"+"/script>");
	</script>
	<script src="/assets/js/bootstrap.min.js"></script>

	<script src="/assets/js/chosen.jquery.min2.js"></script>
	
	<!-- script on paging start -->

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
	<script src="//t1.daumcdn.net/mapjsapi/bundle/postcode/prod/postcode.v2.js"></script>
	<!-- ace scripts -->
	<script src="/assets/js/chosen.jquery.min.js"></script>
	
	<!-- script on paging end -->
	<script src="/assets/js/ace-elements.min.js"></script>
	<script src="/assets/js/ace.min.js"></script>
	<!-- script addon start -->
	<script type="text/javascript">
	jQuery('.mm-prev-btn').hide();

	var x;
	var count;
	var current;
	var percent;
	var z = [];

	init();
	getCurrentSlide();
	goToNext();
	goToPrev();
	getCount();
	// checkStatus();
	// buttonConfig();
	buildStatus();
	deliverStatus();
	submitData();
	goBack();

	function init() {
		
		jQuery('.mm-survey-container .mm-survey-page').each(function() {

			var item;
			var page;

			item = jQuery(this);
			page = item.data('page');

			item.addClass('mm-page-'+page);
			//item.html(page);

		});

	}

	function getCount() {

		count = jQuery('.mm-survey-page').length;
		return count;

	}

	function goToNext() {

		jQuery('.mm-next-btn').on('click', function() {
			goToSlide(x);
			getCount();
			current = x + 1;
			var g = current/count;
			buildProgress(g);
			var y = (count + 1);
			getButtons();
			jQuery('.mm-survey-page').removeClass('active');
			jQuery('.mm-page-'+current).addClass('active');
			getCurrentSlide();
			checkStatus();
			if( jQuery('.mm-page-'+count).hasClass('active') ){
				if( jQuery('.mm-page-'+count).hasClass('pass') ) {
					jQuery('.mm-finish-btn').addClass('active');
				}
				else {
					jQuery('.mm-page-'+count+' .mm-survery-content .mm-survey-item').on('click', function() {
						jQuery('.mm-finish-btn').addClass('active');
					});
				}
			}
			else {
				jQuery('.mm-finish-btn').removeClass('active');
				if( jQuery('.mm-page-'+current).hasClass('pass') ) {
					jQuery('.mm-survey-container').addClass('good');
					jQuery('.mm-survey').addClass('okay');
				}
				else {
					jQuery('.mm-survey-container').removeClass('good');
					jQuery('.mm-survey').removeClass('okay');
				}
			}
			buttonConfig();
		});

	}

	function goToPrev() {

		jQuery('.mm-prev-btn').on('click', function() {
			goToSlide(x);
			getCount();			
			current = (x - 1);
			var g = current/count;
			buildProgress(g);
			var y = count;
			getButtons();
			jQuery('.mm-survey-page').removeClass('active');
			jQuery('.mm-page-'+current).addClass('active');
			getCurrentSlide();
			checkStatus();
			jQuery('.mm-finish-btn').removeClass('active');
			if( jQuery('.mm-page-'+current).hasClass('pass') ) {
				jQuery('.mm-survey-container').addClass('good');
				jQuery('.mm-survey').addClass('okay');
			}
			else {
				jQuery('.mm-survey-container').removeClass('good');
				jQuery('.mm-survey').removeClass('okay');
			}
			buttonConfig();
		});

	}

	function buildProgress(g) {

		if(g > 1){
			g = g - 1;
		}
		else if (g === 0) {
			g = 1;
		}
		g = g * 100;
		jQuery('.mm-survey-progress-bar').css({ 'width' : g+'%' });

	}

	function goToSlide(x) {

		return x;

	}

	function getCurrentSlide() {

		jQuery('.mm-survey-page').each(function() {

			var item;

			item = jQuery(this);

			if( jQuery(item).hasClass('active') ) {
				x = item.data('page');
			}
			else {
				
			}

			return x;

		});

	}

	function getButtons() {

		if(current === 0) {
			current = y;
		}
		if(current === count) {
			jQuery('.mm-next-btn').hide();
		}
		else if(current === 1) {
			jQuery('.mm-prev-btn').hide();
		}
		else {
			jQuery('.mm-next-btn').show();
			jQuery('.mm-prev-btn').show();
		}

	}

	jQuery('.mm-survey-q li input').each(function() {

		var item;
		item = jQuery(this);

		jQuery(item).on('click', function() {
			if( jQuery('input:checked').length > 0 ) {
		    	// console.log(item.val());
		    	jQuery('label').parent().removeClass('active');
		    	item.closest( 'li' ).addClass('active');
			}
			else {
				//
			}
		});

	});

	percent = (x/count) * 100;
	jQuery('.mm-survey-progress-bar').css({ 'width' : percent+'%' });

	function checkStatus() {
		jQuery('.mm-survery-content .mm-survey-item').on('click', function() {
			var item;
			item = jQuery(this);
			item.closest('.mm-survey-page').addClass('pass');
		});
	}

	function buildStatus() {
		jQuery('.mm-survery-content .mm-survey-item').on('click', function() {
			var item;
			item = jQuery(this);
			item.addClass('bingo');
			item.closest('.mm-survey-page').addClass('pass');
			jQuery('.mm-survey-container').addClass('good');
		});
	}

	function deliverStatus() {
		jQuery('.mm-survey-item').on('click', function() {
			if( jQuery('.mm-survey-container').hasClass('good') ){
				jQuery('.mm-survey').addClass('okay');
			}
			else {
				jQuery('.mm-survey').removeClass('okay');	
			}
			buttonConfig();
		});
	}

	function lastPage() {
		if( jQuery('.mm-next-btn').hasClass('cool') ) {
			alert('cool');
		}
	}

	function buttonConfig() {
		if( jQuery('.mm-survey').hasClass('okay') ) {
			jQuery('.mm-next-btn button').prop('disabled', false);
		}
		else {
			jQuery('.mm-next-btn button').prop('disabled', true);
		}
	}

	function submitData() {
		jQuery('.mm-finish-btn').on('click', function() {
			collectData();
			jQuery('.mm-survey-bottom').slideUp();
			jQuery('.mm-survey-results').slideDown();
		});
	}

	function collectData() {
		
		var map = {};
		var ax = ['0','red','mercedes','3.14','3'];
		var answer = '';
		var total = 0;
		var ttl = 0;
		var g;
		var c = 0;

		jQuery('.mm-survey-item input:checked').each(function(index, val) {
			var item;
			var data;
			var name;
			var n;

			item = jQuery(this);
			data = item.val();
			name = item.data('item');
			n = parseInt(data);
			total += n;

			map[name] = data;

		});

		jQuery('.mm-survey-results-container .mm-survey-results-list').html('');

		for (i = 1; i <= count; i++) {

			var t = {};
			var m = {};
			answer += map[i] + '<br>';
			
			if( map[i] === ax[i]) {
				g = map[i];
				p = 'correct';
				c = 1;
			}
			else {
				g = map[i];
				p = 'incorrect';
				c = 0;
			}

			jQuery('.mm-survey-results-list').append('<li class="mm-survey-results-item '+p+'"><span class="mm-item-number">'+i+'</span><span class="mm-item-info">'+g+' - '+p+'</span></li>');

			m[i] = c;
			ttl += m[i];

		}

		var results;
		results = ( ( ttl / count ) * 100 ).toFixed(0);

		jQuery('.mm-survey-results-score').html( results + '%' );

	}

	function goBack() {
		jQuery('.mm-back-btn').on('click', function() {
			jQuery('.mm-survey-bottom').slideDown();
			jQuery('.mm-survey-results').slideUp();
		});
	}
	</script>
	<!-- script addon end -->
</body>
</html>