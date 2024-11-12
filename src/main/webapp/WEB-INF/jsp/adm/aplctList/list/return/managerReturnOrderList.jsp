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
                     <li class="active">반품 접수 리스트</li>
                  </ul><!-- /.breadcrumb -->
               </div>
               
               <div class="page-content">
                  <div class="page-header">
                     <h1>
                        반품 접수 리스트
                     </h1>
                  </div>
                  <div id="inner-content-side" style="margin-top:20px;">
                     <form name="returnForm" id="returnForm">
                     	<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
	                     <div id="search-div">
	                     	<button type="submit" class="btn btn-sm btn-success btn-white" name="excelDown" id="excelDown">접수 자료 다운로드</button>
	                     	<div class="row" style="margin-top:8px;">
	                           <div class="col-xs-12 col-md-7 form-group">
	                              <h4>우체국 접수 자료 업로드</h4>
	                              <input type="file" id="excelFile" name="excelFile" style="display: initial; width: 50%; cursor: pointer; border: 1px solid #D5D5D5;"
	                                 accept="application/vnd.openxmlformats-officedocument.spreadsheetml.sheet, application/vnd.ms-excel" />
	                              <i class="fa fa-times bigger-110" id="delBtns" name="delBtns" style="cursor: pointer;"></i>
	                              <button type="button" style="margin-left:20px; margin-bottom:2px;" class="btn btn-sm btn-white" name="excelUp" id="excelUp" >업로드</button>
	                           </div>
	                        </div>
	                        <br/>
							<div class="row">
								<div class="col-xs-12 col-sm-11 col-md-10">
									<span class="input-group" style="display: inline-flex; width:100%">
										<select name="option" style="height: 34px;">
											<option <c:if test="${option eq 'all'}"> selected </c:if> value="all">ALL</option>
											<option <c:if test="${option eq 'koblNo'}"> selected </c:if> value="hawbNo">국내 송장번호</option>
											<option <c:if test="${option eq 'sellerId'}"> selected </c:if> value="sellerId">Seller ID</option>
										</select>
										<select name="select" id="select" style="height: 34px">
											<option <c:if test="${select eq 'selectAll'}"> selected </c:if> value="selectAll">::전체::</option>
											<option <c:if test="${select eq 'A000'}"> selected </c:if> value="A000">1차접수</option>
											<option <c:if test="${select eq 'A001'}"> selected </c:if> value="A001">접수신청</option>
											<option <c:if test="${select eq 'A002'}"> selected </c:if> value="A002">접수완료</option>
											<option <c:if test="${select eq 'B001'}"> selected </c:if> value="B001">수거중</option>
											<option <c:if test="${select eq 'C001'}"> selected </c:if> value="C001">입고</option>
										</select>
										<input type="text" class="form-control" name="keywords" value="${keywords}" style="width:100%; max-width: 200px"/>
										<button id="srchKeyword" class="btn btn-default no-border btn-sm">
											<i class="ace-icon fa fa-search icon-on-right bigger-110"></i>
										</button>
									</span>
								</div>
							</div>
							<!-- <div style="margin-bottom:8px; text-align:right;">
								<button type="submit" class="btn btn-sm btn-success btn-white" name="excelDown" id="excelDown">접수신청 다운로드</button>
	                        	<input type="button" id="delBtn" class="btn btn-sm btn-white" style="margin-left:4px;"value="접수 삭제" onclick="deleteOrder()">
	                        </div> -->
						</div>
	                    <div id="table-contents" class="table-contents" style="margin-top:10px;">
	                    	<table id="dynamic-table" class="table table-bordered table-hover" style="width:100%;">
	                    		<thead>
	                    			<tr>
	                    				<th class="center colorBlack" style="width:50px;">NO</th>
	                    				<th class="center colorBlack" style="width:100px;">진행 상황</th>
	                    				<th class="center colorBlack" style="width:180px;">원운송장번호</th>
	                    				<th class="center colorBlack" style="width:180px;">반송장번호</th>
	                    				<th class="center colorBlack" style="width:140px;">Seller ID</th>
	                    				<th class="center colorBlack" style="width:80px;">도착 국가</th>
	                    				<th class="center colorBlack" style="width:80px;">반품 유형</th>
	                    				<th class="center colorBlack" style="width:80px;">픽업 유형</th>
	                    				<th class="center colorBlack" style="width:160px;">등록일</th>
	                    				<th class="center colorBlack" style="width:120px;">환급 여부</th>
	                    				<th class="center colorBlack" style="width:170px;"></th>	
	                    			</tr>
	                    		</thead>
	                    		<tbody>
	                    			<c:forEach items="${vo}" var="returnList" varStatus="status">
	                    				<tr>
	                    					<td class="center">${status.count}</td>
	                    					<td class="center">${returnList.stateKr}</td>
	                    					<td class="center">
	                    						<input type="hidden" value="${returnList.state}" name="state"/>
	                    						<a style="cursor:pointer; font-weight:bold;" onclick="fn_update('${returnList.nno}')">${returnList.koblNo}</a>
	                    					</td>
	                    					<td class="center">
	                    						<a style="cursor:pointer; font-weight:bold;" onclick="fn_update('${returnList.nno}')">${returnList.reTrkNo}</a>
	                    					</td>
	                    					<td class="center">
	                    						<input type="hidden" name="sellerId" value="${returnList.sellerId}"/>
	                    						${returnList.sellerId}
	                    					</td>
	                    					<td class="center">${returnList.dstnNation}</td>
	                    					<td class="center">
	                    						<c:if test="${returnList.returnType eq 'N'}">
	                    							일반 발송
	                    						</c:if>
	                    						<c:if test="${returnList.returnType eq 'E'}">
	                    							긴급 발송
	                    						</c:if>
	                    					</td>
	                    					<td class="center">${returnList.pickType}</td>
	                    					<td class="center">${returnList.writeDate}</td>
	                    					<td class="center">
	                    						<c:if test="${returnList.taxType eq 'Y'}">
	                    							<a style="cursor:pointer; font-weight:bold;" onclick="fn_popupFileDownload('${returnList.nno}')">위약 반송</a>
	                    						</c:if>
	                    						<c:if test="${returnList.taxType ne 'Y'}">
	                    							일반 반송
	                    						</c:if>
	                    					</td>
	                    					<td class="center">
	                    						<input type="button" value="추가비용 등록" class="active2" onclick="registInCost('${returnList.state}', '${returnList.orderReference}')"/>
	                    						&nbsp;<input type="button" value="접수 삭제" class="active2" onclick="fn_delete('${returnList.nno}', '${returnList.state}', '${returnList.stateKr}')"/>
	                    					</td>
	                    				</tr>
	                    			</c:forEach>
	                    		</tbody>
	                    	</table>
	                    </div>
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
               window.open("/mngr/aplctList/return/excelDown");
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
         
         function fn_update(nno) {
             location.href = '/mngr/aplctList/returnInfo/'+nno;
         }
        /*  function fn_update(orderReference) {
             location.href = '/mngr/aplctList/return/info/'+orderReference;
         } */

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
         function fn_pod() {
             alert("해당 기능 준비중 입니다.");
         }
         var myTable = 
				$('#dynamic-table')
				.DataTable( {
					"dom" : "t",
					"paging":   false,
			        "ordering": false,
			        "info":     false,
			        "searching": false,
					select: {
						style: 'multi',
						selector: 'td:first-of-type'
					},
					"language":{
						"zeroRecords": "조회 결과가 없습니다.",
					}
			    } );
			
				
				myTable.on( 'select', function ( e, dt, type, index ) {
					if ( type === 'row' ) {
						$( myTable.row( index ).node() ).find('input:checkbox').prop('checked', true);
					}
				} );
				myTable.on( 'deselect', function ( e, dt, type, index ) {
					if ( type === 'row' ) {
						$( myTable.row( index ).node() ).find('input:checkbox').prop('checked', false);
					}
				} );
			
         $('th input[type=checkbox], td input[type=checkbox]').prop('checked', false);
			
			//select/deselect all rows according to table header checkbox
			$('#dynamic-table > thead > tr > th input[type=checkbox], #dynamic-table_wrapper input[type=checkbox]').eq(0).on('click', function(){
				var th_checked = this.checked;//checkbox inside "TH" table header
				
				$('#dynamic-table').find('tbody > tr').each(function(){
					var row = this;
					if(th_checked) myTable.row(row).select();
					else  myTable.row(row).deselect();
				});
			});
			
			//select/deselect a row when the checkbox is checked/unchecked
			$('#dynamic-table').on('click', 'td input[type=checkbox]' , function(){
				var row = $(this).closest('tr').get(0);
				if(this.checked) myTable.row(row).deselect();
				else myTable.row(row).select();
			});

			function fn_popupFileDownload(nno) {
				var _width = '480';
			    var _height = '500';
			    var _left = (screen.width/2)-(_width/2);
			    var _top = (screen.height/2)-(_height/2);
			    
			    window.open("/mngr/aplctList/returnInfo/taxReturn?nno="+nno, "mywindow", 'width='+ _width +', height='+ _height +', left=' + _left + ', top='+ _top);
			}
	 			
			
      </script>
      <!-- script addon end -->

   </body>
</html>