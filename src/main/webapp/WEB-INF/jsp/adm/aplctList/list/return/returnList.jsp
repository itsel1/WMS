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
   </style>
   
   <head>
   <%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
   <style type="text/css">
     .colorBlack {color:#000000 !important;}
    </style>
   <!-- basic scripts -->
   <!-- ace scripts -->
   
   </head> 
   <title>반품 리스트</title>
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
                     <li class="active">반품 리스트</li>
                  </ul><!-- /.breadcrumb -->
               </div>
               
               <div class="page-content">
                  <div class="page-header">
                     <h1>
                        	반품 리스트
                     </h1>
                  </div>
                  <div id="inner-content-side" >
                     <form name="returnForm" id="returnForm">
                     <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                     <div id="search-div">
                     	<div class="row">
                           <div class="col-xs-12 col-md-7 form-group">
                              <h4>반품접수 엑셀 등록</h4>
                              <input type="file" id="excelFile" name="excelFile" style="display: initial; width: 50%; cursor: pointer; border: 1px solid #D5D5D5;"
                                 accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel" />
                              <i class="fa fa-times bigger-110" id="delBtns" name="delBtns" style="cursor: pointer;"></i>
                           </div>
                        </div>
                        <div>
                        	<font style="float: right;" color="red">접수 신청 대상만 다운로드 합니다.</font><br/>
                           <button type="button" class="btn btn-sm btn-primary" name="excelUp" id="excelUp" >엑셀 파일 업로드</button>
                           <input type="button" id="delBtn" class="btn btn-sm btn-danger" style="text-align:right; margin-left:4px;"value="삭제하기" onclick="deleteOrder()">
                           
                           <!-- <form action="/mngr/aplctList/return/excelDown" method="GET"> -->                           		
								<button type="submit" style="float: right;"class="btn btn-sm btn-secondary" name="excelDown" id="excelDown">엑셀 파일 다운로드</button>
                           <!-- </form> -->
                        </div>
                        <!-- <div id="delete-user" style="text-align: left">
                           <input type="button" id="delBtn" class="btn btn-sm btn-danger" value="삭제하기" onclick="deleteOrder()">
                        </div> -->
						<br>
						<div class="row">
							<div class="col-xs-12 col-sm-11 col-md-10">
								<span class="input-group" style="display: inline-flex; width:100%">
									<select name="option" style="height: 34px">
										<option <c:if test="${option eq 'all'}"> selected </c:if> value="all">ALL</option>
										<option <c:if test="${option eq 'koblNo'}"> selected </c:if> value="hawbNo">국내송장번호</option>
									</select>
									<select name="select" id="select" style="height: 34px">
										<option <c:if test="${select eq 'selectAll'}"> selected </c:if> value="selectAll">반송상태를 선택하세요</option>
										<option <c:if test="${select eq 'A000'}"> selected </c:if> value="A000">1차접수</option>
										<option <c:if test="${select eq 'A001'}"> selected </c:if> value="A001">접수신청</option>
										<option <c:if test="${select eq 'A002'}"> selected </c:if> value="A002">접수완료</option>
										<option <c:if test="${select eq 'B001'}"> selected </c:if> value="B001">수거중</option>
										<option <c:if test="${select eq 'C001'}"> selected </c:if> value="C001">입고</option>
										<option <c:if test="${select eq 'C002'}"> selected </c:if> value="C003">출고 대기</option>
										<option <c:if test="${select eq 'C002'}"> selected </c:if> value="C004">출고 승인</option>
										<option <c:if test="${select eq 'C002'}"> selected </c:if> value="C002">출고</option>
										<option <c:if test="${select eq 'D001'}"> selected </c:if> value="D001">취소</option>
										<option <c:if test="${select eq 'D002'}"> selected </c:if> value="D002">접수 반려</option>
										<option <c:if test="${select eq 'D002'}"> selected </c:if> value="D003">출고 반려</option>
										<option <c:if test="${select eq 'D002'}"> selected </c:if> value="D004">검수 불합격</option>
										<option <c:if test="${select eq 'D002'}"> selected </c:if> value="D005">폐기</option>
									</select>
									<input type="text" class="form-control" name="keywords" value="${keywords}" style="width:100%; max-width: 300px"/>
									<button id="srchKeyword" class="btn btn-default no-border btn-sm">
										<i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
									</button>
								</span>
							</div>
						</div>
						<br>
					</div>
                       
                       
                       <br>
                     <div id="table-contents" class="table-contents">
                        <div class="display">
                           <table id="dynamic-table" class="table table-bordered table-hover">
                                 <colgroup>
                                    <col width="1%"/> 
                                    <col width="9%"/>
                                    <col width="9%"/>
                                    <col width="9%"/>
                                    <col width="6%"/>
                                    <col width="8%"/>
                                    <col width="5%"/>
                                    <col width="5%"/>
                                    <col width="5%"/>
                                    <col width="5%"/>
                                    <col width="5%"/>
                                    <col width="5%"/>
                                    <col width="5%"/>
                                    <col width="5%"/>
                                    <col width="7%"/>
                                    <col width="7%"/>
                                    <col width="4%"/>
                                 </colgroup>
                                 <thead >
                                 <tr>
                                    <th class="center">
                                       <label class="pos-rel"> 
                                          <input type="checkbox" class="ace" id="chkAll" /> 
                                          <span class="lbl" ></span>
                                       </label>
                                    </th>
                                    <th class="center colorBlack">ORDER NO</th>
                                    <th class="center colorBlack">KOBL NO</th>
                                    <th class="center colorBlack">RETURN TRACNKING NO</th>
                                    <th class="center colorBlack">도착 국가</th>
                                    <th class="center colorBlack">USER ID</th>
                                    <th class="center colorBlack">ORDER TYPE</th>
                                    <th class="center colorBlack">ORDER DATE</th>
                                    <th class="center colorBlack">반송 상태</th>
                                    <th class="center colorBlack">반송사유서</th>
                                    <th class="center colorBlack">통장사본</th>
                                    <th class="center colorBlack">반품신청 캡쳐본</th>
                                    <th class="center colorBlack">반품메신저 캡쳐본</th>
                                    <th class="center colorBlack">반품 커머셜</th>
                                    <th class="center colorBlack">세관 검사 여부</th>
                                    <th class="center colorBlack">전달 방식</th>
                                    <th class="center colorBlack">추가 비용</th>
                                 </tr>
                              </thead>
                                 <tbody>
                                       <c:forEach items="${vo}" var="test" varStatus="voStatus">
                                       
                                          <tr>
                                             <td class="center" >
	                                             <label class="pos-rel"> 
	                                                <input type="checkbox" name="nno[]" class="form-field-checkbox" value="${test.nno}" /> 
	                                                <span class="lbl"></span>
	                                             </label>
                                             </td>
                                             <td class="center colorBlack" style="word-break:break-all;">
                                                <a href='/mngr/aplctList/return/${test.orderReference}'>${test.orderNo}</a>
                                             </td>
                                             <td class="center colorBlack" style="word-break:break-all;">
                                                <a href='/mngr/aplctList/return/${test.orderReference}'>${test.koblNo}</a>
                                             </td>
                                             <td class="center colorBlack" style="word-break:break-all;">
                                                <a href='/mngr/aplctList/return/${test.orderReference}'>${test.reTrkNo}</a>
                                             </td>
                              
                                             <td class="center colorBlack" style="word-break:break-all;">
                                                ${test.dstnNation}
                                             </td>
                                             <td class="center colorBlack" style="word-break:break-all;">
                                                ${test.sellerId}
                                             </td>
                                             
                                             <td class="center colorBlack" style="word-break:break-all;">
                                                ${test.returnType}
                                             </td>
                                             
                                             <td class="center colorBlack" style="word-break:break-all;">
                                                ${test.orderDate}
                                             </td>
                                             <td class="center colorBlack" style="word-break:break-all;">
                                                ${test.stateKr}
                                             </td> 
                                             <td class="center colorBlack" style="word-break:break-all;">
                                             	<c:if test="${!empty test.fileReason}">
                                             		<%-- <a href="${test.fileReason}" download> --%>
                                             		<a href='${test.fileReason}' onclick="window.open(this.href,'팝업','width=800, height=800');return false" download>
                                             			다운로드
                                             		</a>
                                           		</c:if>
                                             </td> 
                                             <td class="center colorBlack" style="word-break:break-all;">
                                                <c:if test="${!empty test.fileCopyBank}">
                                             		<%-- <a href="${test.fileReason}" download> --%>
                                             		<a href='${test.fileCopyBank}' onclick="window.open(this.href,'팝업','width=800, height=800');return false" download>
                                             			다운로드
                                             		</a>
                                           		</c:if>
                                             </td> 
                                             <td class="center colorBlack" style="word-break:break-all;">
                                                <c:if test="${!empty test.fileCapture}">
                                             		<%-- <a href="${test.fileReason}" download> --%>
                                             		<a href='${test.fileCapture}' onclick="window.open(this.href,'팝업','width=800, height=800');return false" download>
                                             			다운로드
                                             		</a>
                                           		</c:if>
                                             </td> 
                                             <td class="center colorBlack" style="word-break:break-all;">
                                                <c:if test="${!empty test.fileMessenger}">
                                             		<%-- <a href="${test.fileReason}" download> --%>
                                             		<a href='${test.fileMessenger}' onclick="window.open(this.href,'팝업','width=800, height=800');return false" download>
                                             			다운로드
                                             		</a>
                                           		</c:if>
                                             </td> 
                                             <td class="center colorBlack" style="word-break:break-all;">
                                                <c:if test="${!empty test.fileCl}">
                                             		<%-- <a href="${test.fileReason}" download> --%>
                                             		<a href='${test.fileCl}' onclick="window.open(this.href,'팝업','width=800, height=800');return false" download>
                                             			다운로드
                                             		</a>
                                           		</c:if>
                                             </td> 
                                              <td class="center colorBlack" style="word-break:break-all;">
                                                
                                             </td>
                                             <td class="center colorBlack" style="word-break:break-all;">
                                                ${test.pickType}
                                             </td>
                                             <td class="center colorBlack" style="word-break:break-all;">
                                             	<input type="button" id="registIn" name="registIn" onclick="registInCost('${test.state}','${test.orderReference}')" value="추가"/>
                                             
                                             	<%-- <c:if test="${test.state eq 'A002' }">
                                                	<input type="button" id="registIn" name="registIn" onclick="registInOrder('${test.state}','${test.orderReference}')" value="수거중"/>
                                             	</c:if>
                                                <c:if test="${test.state eq 'B001' }">
                                                	<input type="button" id="registIn" name="registIn" onclick="registInOrder('${test.state}','${test.orderReference}')" value="입고"/>
                                             	</c:if> --%>
                                             </td>  
                                          </tr>
                                       </c:forEach>
                                    
                                 </tbody>
                              </table>
                           </div>
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
               $("#14thOne-2").toggleClass('active');   
   
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
         
            
      </script>
      <!-- script addon end -->

   </body>
</html>