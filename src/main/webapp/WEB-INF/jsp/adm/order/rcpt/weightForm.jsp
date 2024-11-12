<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>

<div class="row">
	<div class="col-xs-12 col-sm-5">
		<div class="widget-box transparent" name="nomalField">
				<input type="hidden" id="blNno" value="${nno}">
			<div class="widget-header widget-header-small">
				<h2 class="widget-title smaller">
					<i class="ace-icon fa fa-check-square-o bigger-170"></i>
					box
				</h2>
			</div>
 
			<div class="widget-body">
				<div class="widget-main">
					<div class="row" >
						<div class="col-xs-12 col-sm-6" >
							<label class="bolder bigger-150 verticalM">box 개수</label>
							<input id="boxCnt" name="boxCnt" class="boxFont" readonly numberOnly size="5" type="text" value="1"/>
							<label class="bolder bigger-150">개</label>
						</div>
						<div class="col-xs-12 col-sm-6">
							<label class="bolder bigger-150 verticalM">실제 무게<br/>W/T(A)</label>
								<input id="boxWeight" name="boxWeight" class="boxFont" floatOnly size="5" type="text" tabindex="1" value="0"/>
							<label class="bolder bigger-180 light-blue">
							<select id="wtUnit" name="wtUnit" style="color:#93CBF9 !important;height:40px;">
								<option>
									KG
								</option>
								<option>
									LB
								</option>
							</select>
							</label>
						</div>
					</div>
					<br/>
					<br/>
					<div class="row">
						<div class="col-xs-12">
							<label style="vertical-align: middle;" class="bolder bigger-180">청구 W/T : </label><label id="" style="vertical-align: middle;" class="bolder bigger-280 real-red"></label>
							<input type="text" id="endWt" name="endWt" class="bolder bigger-280 real-red" style="background-color: none;border:none;" />
							<input type="hidden" id="wta" name="wta" class="bolder bigger-280 real-red" style="background-color: none;border:none;" value="0" />
							<input type="hidden" id="wtc" name="wtc" class="bolder bigger-280 real-red" style="background-color: none;border:none;" value="0" />
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<div class="col-xs-12 col-sm-7">
		<div class="widget-box transparent" name="nomalField">
			<div class="widget-header widget-header-small header-color-blue2">
				<h2 class="widget-title smaller">
					<i class="ace-icon fa fa-check-square-o bigger-170"></i>
					크기
				</h2>
			</div>
			
			
		
			
			<div class="widget-body">
				<div class="widget-main padding-16" id="volumns" name="volumns">
					<div class="row">
						<div class="col-xs-12 col-sm-12">
							<span class="col-xs-2">
								<label class="bolder bigger-130 verticalM">가로</label><br/>
								<label class="light-grey bolder bigger-130 verticalM ">width</label>
							</span>
							<span class="col-xs-2">
								<label class="bolder bigger-130 verticalM">세로</label><br/>
								<label class="light-grey bolder bigger-130 verticalM ">length</label>
							</span>
							<span class="col-xs-2">
								<label class="bolder bigger-130 verticalM">높이</label><br/>
								<label class="light-grey bolder bigger-130 verticalM ">height</label>
							</span>
							<span class="col-xs-2">
								<label class="bolder bigger-130 verticalM">PER</label><br/>
							</span>
							<span class="col-xs-2">
								<label class="bolder bigger-130 verticalM">값</label><br/>
							</span>
							<span class="col-xs-2">
								<select id="dimUnit" name="dimUnit" style="color:#93CBF9 !important;height:35px;">
									<option value="CM">
										CM
									</option>
									<option value="IN">
										INCH
									</option>
								</select>
								<button class="btn btn-primary btn-white" type="button" id="addBtn" name="addBtn">Add +</button><br/>
							</span>
						</div>
					</div>
					<div class="row" id="div1">
						<div class="col-xs-12 col-sm-12">
							<span class="col-xs-2">
								<input id="width1" name="width" type="text" class="sizeFont" floatOnly tabindex="1"/>*
							</span>
							<span class="col-xs-2">
								<input id="length1" name="length" type="text" class="sizeFont" floatOnly tabindex="1"/>*
							</span>
							<span class="col-xs-2">
								<input id="height1" name="height" type="text" class="sizeFont" floatOnly tabindex="1"/>/
							</span>
							<span class="col-xs-2">
								<input id="per1" name="per" type="text" class="sizeFont" floatOnly value="5000" tabindex="1"/>
							</span>
							<span class="col-xs-2">
								=<input id="result1" name="result" type="text" class="sizeFont" readOnly value="0"/><br/>
							</span>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>

<div class="row">
	<div class="col-xs-12" style="margin-left:40%;">
		<button type="button" id="cancleBtn" name="cancleBtn" class="btn btn-danger">취소</button>
		<button type="button" id="registBtn" name="registBtn" class="btn btn-primary">입고</button>
	</div>
</div>
<script type="text/javascript">
		$("#boxWeight").focus();
		var cnt = 2;
		$(document).on("keyup", "input:text[numberOnly]", function() {
            $(this).val($(this).val().replace(/[^0-9]/g,""));
        });

        $(document).on("keyup", "input:text[floatOnly]", function() {
            $(this).val($(this).val().replace(/[^0-9.]/g,""));
        });

        $(document).on('change', "input[name*='width']",function(e){
        	wtCalc();
        	maximum();
        });
        $(document).on('change', "input[name*='length']",function(){
        	wtCalc();
        	maximum();
        });
        $(document).on('change',"input[name*='height']",function(){
        	wtCalc();
        	maximum();
        });
        $(document).on('change', "input[name*='per']",function(){
        	wtCalc();
        	maximum();
        });
        $(document).on('change', "#boxWeight",function(){
        	maximum();
        });


        function maximum(){
        	var size = $("input[name*='result']").size();
        	var resultWta = parseFloat($("#boxWeight").val());
        	var resultWtc = 0;
			for(loop=0;loop<size;loop++){	
				resultWtc = parseFloat(resultWtc)+parseFloat($("input[name*='result']").get(loop).value)*1;
			}

			$("#wta").val(parseFloat(resultWta));
			$("#wtc").val(parseFloat(resultWtc));
			
			
	        if(parseFloat(resultWta) < parseFloat(resultWtc)){
	        	$("#endWt").val(parseFloat(resultWtc));
	        }else{
	        	$("#endWt").val(parseFloat(resultWta));
	        }
			
        };

        function wtCalc(){
            var width = 0;
            var length = 0;
            var height = 0;
            var per = 0;
            var result = 0;
        	var size = $("input[name*='width']").size();
        	for(loop=0;loop<size;loop++){
        		width = $("input[name*='width']").get(loop).value;
        		length = $("input[name*='length']").get(loop).value;
        		height = $("input[name*='height']").get(loop).value;
        		per = $("input[name*='per']").get(loop).value;
        		result = (width*length*height)/per;
        		result = result.toFixed(1);
        		if(result != 0){
        			$("input[name*='result']").get(loop).value = result;
          		}else{
          			$("input[name*='result']").get(loop).value = 0;
           		}
           	}
        };

        $("#addBtn").on('click',function(e){
			var addHtml ="";
			console.log(this);
			addHtml += 
			'<div class="row" id="div'+cnt+'">'+
				'<div class="col-xs-12 col-sm-12">'+
					'<span class="col-xs-2">'+
						'<input id="width'+cnt+'" name="width" type="text" class="sizeFont" floatOnly tabindex="1"/>*'+
					'</span>'+
					'<span class="col-xs-2">'+
						'<input id="length'+cnt+'" name="length" type="text" class="sizeFont" floatOnly tabindex="1"/>*'+
					'</span>'+
					'<span class="col-xs-2">'+
						'<input id="height'+cnt+'" name="height" type="text" class="sizeFont" floatOnly tabindex="1"/>/'+
					'</span>'+
					'<span class="col-xs-2">'+
						'<input id="per'+cnt+'" name="per" type="text" class="sizeFont" floatOnly value="6000" tabindex="1"/>'+
					'</span>'+
					'<span class="col-xs-2">'+
						'=<input id="result'+cnt+'" name="result" type="text" class="sizeFont" readOnly value="0"/>'+
						'<i class="bigger-190 red fa fa-minus-circle cancleWeight" role="button" onclick="test('+cnt+')" style="position:absolute; margin:10px;"></i>'+
					'</span>'+
				'</div>'+
			'</div>';
/* 
			<span class="input-group-addon" href="#modal-form" role="button" class="blue" data-toggle="modal">
				<i class="ace-icon fa fa-plus" id="hawbNoAdd" name="hawbNoAdd" style="font-size: 20px"></i>
			</span> */

            cnt++;
			$("#boxCnt").val($("#boxCnt").val()*1+1);
            $("#volumns").append(addHtml);

		});


		
        $("#boxWeight").keydown(function(key) {
        	if (key.keyCode == 13) {
        		if($(this).val() !=""){
        			$("#width1").focus();
            	}    	
        	}
        });

        $("#width1").keydown(function(key) {
        	if (key.keyCode == 13) {
        		$("#length1").focus();
        	}
        });

        $("#length1").keydown(function(key) {
        	if (key.keyCode == 13) {
        			$("#height1").focus();
        	}
        });

        $("#height1").keydown(function(key) {
        	if (key.keyCode == 13) {
        		var result = confirm('등록 하시겠습니까??');
				if(result){
					 $("#registBtn").focus();
				}
        	}
        });


        $("#registBtn").on('click',function(e){

        	var formData = $("#weightForm").serialize();
        	var targets = new Array();
        	
			targets.push($("#blNno").val());	
        	
        	$.ajax({
				url:'/mngr/order/weightForm',
				type: 'POST',
				data : formData,
				beforeSend : function(xhr)
				{   /*데이터를 전송하기 전에 헤더에 csrf값을 설정한다*/
				    xhr.setRequestHeader("${_csrf.headerName}", "${_csrf.token}");
				},
				success : function(data) {
					if(data.result=="S"){
						$("#test").empty();
			        	$("#hawbIn").attr("readonly", false);
			        	$("#hawbResult").addClass("blue");
						$("#hawbResult").removeClass("red");
						$("#hawbResult").val("등록 성공");
						count++;
						$("#currentIn").text(count);
						$("#hawbIn").val("");
						$("#hawbIn").focus();
	
						//window.open("/comn/printHawb?targetInfo="+targets+"&formType="+$("#formSelect").val(),"popForm","_blank");
						
					}else if(data.result=="F"){
						$("#hawbIn").attr("readonly", false);
			        	alert("등록 과정에 문제가 발생하였습니다. 관리자에게 문의해 주세요");
			        	$("#hawbResult").removeClass("blue");
						$("#hawbResult").addClass("red");
						$("#hawbResult").val("등록 실패");
					}else{
						$("#hawbIn").attr("readonly", false);
			        	$("#hawbResult").removeClass("blue");
						$("#hawbResult").addClass("red");
			        	$("#hawbResult").val(data.result);
					}
	            }, 		    
	            error : function(xhr, status) {
	                alert(xhr + " : " + status);
	                alert("페이지 로드에 실패 하였습니다.");
	            }
			});

        	

        });
        function test(target){
            $("#div"+target).remove();
            $("#boxCnt").val($("#boxCnt").val()*1-1);
		};

		$("#cancleBtn").on('click',function(e){
			location.reload();
		});

        
</script>
