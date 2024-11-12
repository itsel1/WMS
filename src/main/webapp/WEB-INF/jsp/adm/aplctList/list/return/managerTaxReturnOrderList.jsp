<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
   <style>
   .button {
     background-color: rgb(67,142,185); /* Green */
     border: none;
     color: white;
     padding: 0px;
     text-align: center;
     text-decoration: none;
     display: inline-block;
     font-size: 12px;
     margin: 4px 2px;
     cursor: pointer;
     -webkit-transition-duration: 0.4s; /* Safari */
     transition-duration: 0.4s;
     width: 100px;
     height: 30px;
   }
   
   .button2:hover {
     box-shadow: 0 12px 16px 0 rgba(0,0,0,0.24),0 17px 50px 0 rgba(0,0,0,0.19);
   }
   
   .active2 {
   	border:1px solid #dcdcdc;
   	background:white;
   }
   
   .active2:hover {
   	background:black;
   	color:white;
   }
   
   </style>
   
   <head>
   <%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
   <style type="text/css">
     .colorBlack {color:#000000 !important;}
    </style>
   <!-- basic scripts -->
   <!-- ace scripts -->
   
   </head> 
   <title>위약 반송 리스트</title>
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
                        Home
                     </li>
                     <li>
                        반품
                     </li>
                     <li class="active">위약 반송 리스트</li>
                  </ul><!-- /.breadcrumb -->
               </div>
               
               <div class="page-content">
                  <div class="page-header">
                     <h1>
                        위약 반송 리스트
                     </h1>
                  </div>
                  <div id="inner-content-side" >
                     <form name="returnForm" id="returnForm">
                     	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	                     <div id="search-div">
	                        <br/>
							<div class="row">
								<div class="col-xs-12 col-sm-11 col-md-10">
									<span class="input-group" style="display: inline-flex; width:100%">
										<select name="option" style="height: 34px;">
											<option <c:if test="${option eq 'all'}"> selected </c:if> value="all">ALL</option>
											<option <c:if test="${option eq 'koblNo'}"> selected </c:if> value="hawbNo">송장번호</option>
											<option <c:if test="${option eq 'sellerId'}"> selected </c:if> value="sellerId">Seller ID</option>
										</select>
										<input type="text" class="form-control" name="keywords" value="${keywords}" style="width:100%; max-width: 200px"/>
										<button id="srchKeyword" class="btn btn-default no-border btn-sm">
											<i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
										</button>
									</span>
								</div>
							</div>
						</div>
						<br/>
	                    <div id="table-contents" class="table-contents">
	                    	<table class="table table-bordered table-hover" style="width:box-sizing;">
	                    		<thead>
	                    			<tr>
	                    				<th class="center colorBlack" style="width:50px;">NO</th>
	                    				<th class="center colorBlack" style="width:80px;">진행상황</th>
	                    				<th class="center colorBlack" style="width:120px;">원운송장번호</th>
	                    				<th class="center colorBlack" style="width:100px;">Seller ID</th>
	                    				<th class="center colorBlack" style="width:100px;">발송인명</th>
	                    				<th class="center colorBlack" style="width:180px;">Tracking No</th>
	                    				<th class="center colorBlack" style="width:120px;">도착국가</th>
	                    				<th class="center colorBlack" style="width:100px;">반송사유서</th>
	                    				<th class="center colorBlack" style="width:100px;">통장사본</th>
	                    				<th class="center colorBlack" style="width:100px;">반품신청<br/> 캡쳐본</th>
	                    				<th class="center colorBlack" style="width:100px;">반품메신저<br/> 캡쳐본</th>
	                    				<th class="center colorBlack" style="width:100px;">반품 커머셜</th>
	                    				<th class="center colorBlack" style="width:100px;">수출신고 필증</th>
	                    				<th class="center colorBlack" style="width:160px;">등록일</th> 				
	                    			</tr>
	                    		</thead>
	                    		<tbody>
	                    			<c:forEach items="${vo}" var="returnList" varStatus="status">
	                    				<tr>
	                    					<td class="center">${status.count}</td>
	                    					<td class="center">${returnList.stateKr}</td>
	                    					<td class="center">${returnList.koblNo}</td>
	                    					<td class="center">${returnList.sellerId}</td>
	                    					<td class="center">${returnList.pickupName}</td>
	                    					<td class="center">${returnList.reTrkNo}</td>
	                    					<td class="center">${returnList.dstnNation}</td>
	                    					<td class="center">
	                    						<c:if test="${!empty returnList.fileReason}">
	                    							<a href="${returnList.fileReason}" download>
	                    								다운로드
	                    							</a>
	                    						</c:if>
	                    					</td>
	                    					<td class="center">
	                    						<c:if test="${!empty returnList.fileCopyBank}">
	                    							<a href="${returnList.fileCopyBank}" download>
	                    								다운로드
	                    							</a>
	                    						</c:if>
	                    					</td>
	                    					<td class="center">
	                    						<c:if test="${!empty returnList.fileCapture}">
	                    							<a href="${returnList.fileCapture}" download>
	                    								다운로드
	                    							</a>
	                    						</c:if>
	                    					</td>
	                    					<td class="center">
	                    						<c:if test="${!empty returnList.fileMessenger}">
	                    							<a href="${returnList.fileMessenger}" download>
	                    								다운로드
	                    							</a>
	                    						</c:if>
	                    					</td>
	                    					<td class="center">
	                    						<c:if test="${!empty returnList.fileCl}">
	                    							<a href="${returnList.fileCl}" download>
	                    								다운로드
	                    							</a>
	                    						</c:if>
	                    					</td>
	                    					<td class="center">
	                    						<c:if test="${!empty returnList.fileIc}">
	                    							<a href="${returnList.fileIc}" download>
	                    								다운로드
	                    							</a>
	                    						</c:if>
	                    					</td>
	                    					<td class="center">${returnList.writeDate}</td>
	                    				</tr>
	                    			</c:forEach>
	                    		</tbody>
	                    	</table>
	                    </div>
	                    <input type="hidden" value="" id="targetStatus" name="targetStatus"/>
                    </form>
                  </div>
               	</div>
              	<%@ include file="/WEB-INF/jsp/importFile/pagingNav.jsp" %>
            </div>
         </div><!-- /.main-content -->
      </div>
         
      <!-- Main container End-->
      
      <!-- Footer Start -->
      <%@ include file="/WEB-INF/jsp/importFile/footer.jsp" %>
      <!-- Footer End -->
      
      <!--[if !IE]> -->
      <script src="/assets/js/jquery-2.1.4.min.js"></script>
      <!-- <![endif]-->
      <!--[if IE]>
      <script src="/assets/js/jquery-1.11.3.min.js"></script>
      <![endif]-->
      <script type="text/javascript">
         if('ontouchstart' in document.documentElement) document.write("<script src='/assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
      </script>
      <script src="/assets/js/bootstrap.min.js"></script>
      
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
      <script src="/assets/js/ace-elements.min.js"></script>
      <script src="/assets/js/ace.min.js"></script>
      <!-- script on paging end -->
      

      
      <!-- script addon start -->
      <script type="text/javascript">
      <!-- excel upload 추가 -->
         jQuery(function($) {
            $(document).ready(function() {
               $("#14thMenu-2").toggleClass('open');
               $("#14thMenu-2").toggleClass('active'); 
               $("#14thOne-2-1").toggleClass('active');   
   
            });
         
            
            $("#chkAll").click(function() {
                     if($("#chkAll").prop("checked")){ //해당화면에 전체 checkbox들을 체크해준다
                         $("input[name='nno[]']").prop("checked",true); // 전체선택 체크박스가 해제된 경우
                     } else { //해당화면에 모든 checkbox들의 체크를해제시킨다.
                         $("input[name='nno[]'").prop("checked",false);
                     }
              });

            $('#excelDown').on('click', function(e) {
               window.open("/mngr/aplctList/return/excelDown")
            });

            $('#delBtns').on('click', function(e) {
               $('#excelFile').val("");
            }); 
            
            $('#excelUp').on('click', function(e) {
               if($('#excelFile').val() == ""){
                  alert("선택된 파일이 없습니다")
                  return
               }
               var datass = new FormData();
               datass.append("file", $("#excelFile")[0].files[0])
               LoadingWithMask();

               $.ajax({
                  type : "POST",
                  beforeSend : function(xhr)
                  {   //데이터를 전송하기 전에 헤더에 csrf값을 설정한다
                      xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
                  },
                  url : "/mngr/aplctList/return/excelUpload",
                  data : datass,
                  processData: false,
                     contentType: false,
                  error : function(){
                     alert("등록에 실패하였습니다.");
                     $("#searchIdx").trigger('change');
                     $('#mask').hide();
                     location.reload(true);
                  },
                  success : function(data){
                     if(data == "F"){
                        alert("등록중 오류가 발생했습니다.")
                        location.reload(true);
                     } else {
                        alert("등록되었습니다.");
                        $("#searchIdx").trigger('change');
                        $('#mask').hide();
                        location.reload(true);
                     }
                  }
               })
            });

            

            function LoadingWithMask() {
               var maskHeight = $(document).height();
               var maskWidth = window.document.body.clientWidth;

               var mask ="<div id='mask' style='position:absolute; z-index:9000; background-color:black; display:none; left:0; top:0;'></div>";
                 
                //화면에 레이어 추가
                $('body')
                    .append(mask)
                    
                //마스크의 높이와 너비를 화면 것으로 만들어 전체 화면을 채웁니다.
                $('#mask').css({
                        'width' : maskWidth
                        ,'height': maskHeight
                        ,'opacity' :'0.3'
                });
              
                //마스크 표시
                $('#mask').show();  
                //로딩중 이미지 표시
            }
            
         });

         function registInOrder(state,orderReference) {
             $("#targetStatus").val(state);
             $("#returnForm").attr("action","/mngr/aplctList/return/orderIn/"+orderReference);
             $("#returnForm").attr("method","POST");
             $("#returnForm").submit();
         };

         function registInCost(state,orderReference) {
        	 window.open("/mngr/aplctList/return/add/"+orderReference, "PopupWin", "width=1000,height=800");
         };
         
         
         function deleteOrder() {
             $("#returnForm").attr("action","/mngr/aplctList/return/deleteItem");
             $("#returnForm").attr("method","POST");
             $("#returnForm").submit();
            alert('삭제되었습니다.'); 
         }


         function fn_update(orderReference) {
             location.href = '/mngr/aplctList/return/info/'+orderReference;
         }

         function fn_delete(nno, state, stateKr) {
             if (state == 'A000' || state == 'A001' || state == 'A002') {
            	if(!confirm('정말 삭제 하시겠습니까?')) {
 					return false;
 				} else {
 					$("#returnForm").attr("action", "/mngr/aplctList/return/deleteItem/"+nno);
 		            $("#returnForm").attr("method", "post");
 		            $("#returnForm").submit();
 				}
             } else {
            	alert(stateKr+" 상태인 반품은 삭제할 수 없습니다.");
             }
             
         }
            
      </script>
      <!-- script addon end -->

   </body>
</html>