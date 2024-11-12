<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html>
<html lang="en">
	<head>
        <%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
        <title>Sign Up Page</title>
          <!-- page specific plugin styles -->
        <link rel="stylesheet" href="assets/css/jquery-ui.custom.min.css" />
        <link rel="stylesheet" href="assets/css/chosen.min.css" />
        <link rel="stylesheet" href="assets/css/bootstrap-datepicker3.min.css" />
        <link rel="stylesheet" href="assets/css/bootstrap-timepicker.min.css" />
        <link rel="stylesheet" href="assets/css/daterangepicker.min.css" />
        <link rel="stylesheet" href="assets/css/bootstrap-datetimepicker.min.css" />
        <link rel="stylesheet" href="assets/css/bootstrap-colorpicker.min.css" />

	<script type="text/javascript" src='assets/js/comm.js'></script>
	<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>
	
	<script language="javascript">
	$(document).ready(function(){

		$("#USERID").keyup(function(event){
			if (!(event.keyCode >=37 && event.keyCode<=40)) {
				var inputVal = $(this).val();
				$(this).val(inputVal.replace(/[^a-z0-9]/gi,''));
			}
		});

		$( "#USERPW2" ).keyup(function() {
			if($("#USERPW").val() != $("#USERPW2").val()){
				  $("#confirm").text('비밀번호 불일치');
				  $('#confirm').attr('style','color:red');
			}else{
				 $("#confirm").text('비밀번호 일치');
				 $('#confirm').attr('style','color:blue');
			}
		});

		$('#btn_In').click(function() {

			if($("#UserPW").val()==""){alert('비밀번호를 입력해세요.'); $("#UserPW").focus(); return false;}
			if($("#ComNameHan").val()==""){alert('회사명을 입력해세요.'); $("#ComNameHan").focus(); return false;}
			if($("#ComName").val()==""){alert('영문회사명을 입력해세요.'); $("#ComName").focus(); return false;}
			//if($("#ComZipCode").val()==""){alert('우편번호를 입력해세요.'); $("#ComZipCode").focus(); return false;}
			if($("#ComAddrHan").val()==""){alert('주소를 입력해세요.'); $("#ComAddr").focus(); return false;}
			if($("#ComAddr").val()==""){alert('영문 주소를 입력해세요.'); $("#ComAddr").focus(); return false;}
			if($("#ex_file1").val()==""){alert('사업자 등록증을 등록해주세요.');   return false;  }
			if($("#RprsnEmail").val()==""){alert('대표자 이메일을 입력해주세요.');   return false;  }
			if($("#RprsnNateID").val()==""){alert('대표자 네이트온 아이디를 등록해주세요.');  return false; }
			//if($("#RprsnKakaoID").val()==""){alert('대표자 카카오톡 아이디를 등록해주세요.'); }

		   if($("#UserPW").val() !=$("#UserPW2").val()){
			   	alert('비밀번호가 서로 상이 합니다. 비밀번호를 확인해주세요.');
				$("#UserPW").focus();
				return false;
		   }

			$.ajax({url: "sign_up_chek.php",
				   type: "post",
				   data:{UserID:$("#UserID").val()} ,
			 }).done(function(data) {
				   if(data=='Y'){
					   alert('중복된 아이디 입니다.');
					   $("#UserID").focus();
					   return false;
				   }else{
					   $('#frmSign').submit();
				   }
			 });
		 });

	});

	function fnLogIn(){
			if($('#ChkAdmin').val()!='A'){
				if($('#Station').val()==""){alert('Station Input !!');$('#Station').focus();return false;}
			}
			if($('#UserID').val()==""){alert('User ID Input !!');$('#UserID').focus();return false;}
			if($('#UserPW').val()==""){alert('Passworld Input !!');$('#UserPW').focus();return false;}
			$('#frmLogin').submit();
	}
	</script>
	</head>
	<body >
			<div class="main-content">
				<div class="row">
						<div id="login-box" class="login-box visible widget-box no-border" style="box-shadow: none; border-bottom:0px; background-color: initial;">
							<div class="space-6"></div>
							<div class="position-relative">
								<form class="form-horizontal" action="">
									<input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
									<div class="widget-box visible" style="width:900px;margin-left:auto;margin-right:auto;">
										<div class="widget-header widget-header-small">
											<h4 class="widget-title lighter">회원정보</h4>
										</div>
										<div class="widget-body">
											<div class="widget-main">
												<div class="form-group">
													<label class="col-sm-2 control-label no-padding-right" > UserID </label>
													<div class="col-sm-4">
														<input type="text" id="USERID" placeholder="Username" class="form-control">
													</div>
												</div>

												<div class="form-group">
													<label class="col-sm-2 control-label no-padding-right" > Password </label>
													<div class="col-sm-4">
														<input type="text" id="USERPW" name="USERPW" placeholder="Password" class="form-control">
													</div>
													<div class="col-sm-4">
														<input type="text" id="USERPW2" placeholder="Password Confirm" class="form-control">
													</div>
												</div>

												<div class="form-group">
													<label class="col-sm-2 control-label no-padding-right" > </label>
													<div class="col-sm-4">
														<label id="confirm"></label>
													</div>
												</div>

												<div class="form-group">
													<label class="col-sm-2 control-label no-padding-right"> 회사명 </label>
													<div class="col-sm-4">
														<input type="text" class="form-control">
													</div>

													<label class="col-sm-1 control-label no-padding-right"> 전화번호 </label>
													<div class="col-sm-4">
														<input type="text" class="form-control">
													</div>
												</div>

												<div class="form-group">
													<label class="col-sm-2 control-label no-padding-right" > 주소 </label>
													<div class="col-sm-10">
														<input type="text" id="form-field-1" placeholder="" class="form-control">
													</div>
												</div>

												<div class="form-group">
													<label class="col-sm-2 control-label no-padding-right" > 상세주소 </label>
													<div class="col-sm-10">
														<input type="text" id="form-field-1" placeholder="" class="form-control">
													</div>
												</div>

												<div class="form-group">
													<label class="col-sm-2 control-label no-padding-right" > 로고이미지 </label>
													<div class="col-sm-4">
													    <input type="file" id="id-input-file-2" class="form-control" />
													</div>
												</div>

												<div class="form-group">
                                                    <label class="col-sm-2 col-xs-10 control-label no-padding-right" >고객센터업무시간</label>
                                                    <div class="col-sm-4">
                                                        <div class="input-group">
                                                            <input id="timepicker1" type="text" class="form-control" />
                                                            <span class="input-group-addon">
                                                                <i class="fa fa-clock-o bigger-110"></i>
                                                            </span>
                                                            <input id="timepicker2" type="text" class="form-control" />
                                                            <span class="input-group-addon">
                                                                <i class="fa fa-clock-o bigger-110"></i>
                                                            </span>
                                                        </div>
                                                    </div>
                                                </div>

											</div>
										</div>
									</div>
								
									<div class="widget-box visible" style="width:900px;margin-left:auto;margin-right:auto;">
										<div class="widget-header widget-header-small">
											<h4 class="widget-title lighter">정산정보</h4>
										</div>
										<div class="widget-body">
											<div class="widget-main">

												<div class="form-group">
													<label class="col-sm-2 control-label no-padding-right" >정산일</label>
													<div class="col-sm-4">
														<div class="input-group">
															<input class="form-control date-picker" id="id-date-picker-1" type="text" data-date-format="dd" />
															<span class="input-group-addon">
																<i class="fa fa-calendar bigger-110"></i>
															</span>
														</div>
													</div>
													<label class="col-sm-1 control-label no-padding-right" > 결제통화 </label>
													<div class="col-sm-4">
														<select class="chosen-select form-control" id="form-field-select-3" data-placeholder="Choose a State...">
															<option value="">  </option>
															<option value="AED">AED</option>
															<option value="USD">USD</option>
															<option value="EUR">EUR</option>
														</select>
													</div>
												</div>

												<div class="form-group">
                                                    <label class="col-sm-2 control-label no-padding-right" > 은행명 </label>
                                                    <div class="col-sm-4">
                                                        <input type="text" id="form-field-1-1" class="form-control" />
                                                    </div>
													<label class="col-sm-1 control-label no-padding-right" > 계좌번호 </label>
                                                    <div class="col-sm-4">
                                                        <input type="text" id="form-field-1-1" class="form-control" />
                                                    </div>
                                                </div>

												<div class="form-group">
														<label class="col-sm-2 control-label no-padding-right" > 사업자등록증 </label>
														<div class="col-sm-4">
															<input type="file" id="id-input-file-2" class="form-control" />
														</div>
														<label class="col-sm-1 control-label no-padding-right" > 통장사본 </label>
														<div class="col-sm-4">
															<input type="file" id="id-input-file-2" class="form-control" />
														</div>
												</div>


											</div>
										</div>
									</div>
								

									<div class="widget-box visible" style="width:900px;margin-left:auto;margin-right:auto;">
										<div class="widget-header widget-header-small">
											<h4 class="widget-title lighter">담당자 정보</h4>
										</div>
										<div class="widget-body">
												<div class="widget-main">

												<div class="form-group">
													<label class="col-sm-2 control-label no-padding-right"> 성함 </label>
													<div class="col-sm-4">
														<input type="text" id="form-field-1-1" placeholder="" class="form-control" />
													</div>
													<label class="col-sm-1 control-label no-padding-right">이메일 </label>
													<div class="col-sm-4">
														<input type="text" id="form-field-1-1" placeholder="" class="form-control" />
													</div>
												</div>
												<div class="form-group">
													<label class="col-sm-2 control-label no-padding-right" for="form-field-1-1">연락처1 </label>
													<div class="col-sm-4">
														<input type="text" id="form-field-1-1" placeholder="" class="form-control" />
													</div>
													<label class="col-sm-1 control-label no-padding-right" for="form-field-1-1">연락처2 </label>
													<div class="col-sm-4">
														<input type="text" id="form-field-1-1" placeholder="" class="form-control" />
													</div>
												</div>
											</div>
										</div>
									</div>
									</form>
								</div>
								
								<div style="width:900px;margin-left:auto;margin-right:auto;">
									<div class="space-6"></div>
									<div class="space-6"></div>

									<div class="col-sm-12">
									  <div class="row" style="padding:left:12px;padding:right:12px;">
											<div class="btn-group pull-right">
												<button id="btn_In" class="btn btn-sm btn-primary ">회원가입</button>
											</div>
									  </div>
									</div>
								</div>
						</div>
					</div>
				</div><!-- /.row -->
		<!-- basic scripts -->
		<!--[if !IE]> -->
        <!-- basic scripts -->

        <!--[if !IE]> -->
        <script src="assets/js/jquery-2.1.4.min.js"></script>

        <!-- <![endif]-->

        <!--[if IE]>
<script src="assets/js/jquery-1.11.3.min.js"></script>
<![endif]-->
        <script type="text/javascript">
            if('ontouchstart' in document.documentElement) document.write("<script src='assets/js/jquery.mobile.custom.min.js'>"+"<"+"/script>");
        </script>
        <script src="assets/js/bootstrap.min.js"></script>

        <!-- page specific plugin scripts -->

        <!--[if lte IE 8]>
          <script src="assets/js/excanvas.min.js"></script>
        <![endif]-->
        <script src="assets/js/jquery-ui.custom.min.js"></script>
        <script src="assets/js/jquery.ui.touch-punch.min.js"></script>
        <script src="assets/js/chosen.jquery.min.js"></script>
        <script src="assets/js/spinbox.min.js"></script>
        <script src="assets/js/bootstrap-datepicker.min.js"></script>
        <script src="assets/js/bootstrap-timepicker.min.js"></script>
        <script src="assets/js/moment.min.js"></script>
        <script src="assets/js/daterangepicker.min.js"></script>
        <script src="assets/js/bootstrap-datetimepicker.min.js"></script>
        <script src="assets/js/bootstrap-colorpicker.min.js"></script>
        <script src="assets/js/jquery.knob.min.js"></script>
        <script src="assets/js/autosize.min.js"></script>
        <script src="assets/js/jquery.inputlimiter.min.js"></script>
        <script src="assets/js/jquery.maskedinput.min.js"></script>
        <script src="assets/js/bootstrap-tag.min.js"></script>

        <!-- ace scripts -->
        <script src="assets/js/ace-elements.min.js"></script>
        <script src="assets/js/ace.min.js"></script>

        <!-- inline scripts related to this page -->
        <script type="text/javascript">
            jQuery(function($) {
                $('#id-disable-check').on('click', function() {
                    var inp = $('#form-input-readonly').get(0);
                    if(inp.hasAttribute('disabled')) {
                        inp.setAttribute('readonly' , 'true');
                        inp.removeAttribute('disabled');
                        inp.value="This text field is readonly!";
                    }
                    else {
                        inp.setAttribute('disabled' , 'disabled');
                        inp.removeAttribute('readonly');
                        inp.value="This text field is disabled!";
                    }
                });

                if(!ace.vars['touch']) {
                	$('.chosen-select').chosen({allow_single_deselect:true, search_contains: true });
                    //resize the chosen on window resize

                    $(window)
                    .off('resize.chosen')
                    .on('resize.chosen', function() {
                        $('.chosen-select').each(function() {
                             var $this = $(this);
                             $this.next().css({'width': $this.parent().width()});
                        })
                    }).trigger('resize.chosen');
                    //resize chosen on sidebar collapse/expand
                    $(document).on('settings.ace.chosen', function(e, event_name, event_val) {
                        if(event_name != 'sidebar_collapsed') return;
                        $('.chosen-select').each(function() {
                             var $this = $(this);
                             $this.next().css({'width': $this.parent().width()});
                        })
                    });

                    $('#chosen-multiple-style .btn').on('click', function(e){
                        var target = $(this).find('input[type=radio]');
                        var which = parseInt(target.val());
                        if(which == 2) $('#form-field-select-4').addClass('tag-input-style');
                         else $('#form-field-select-4').removeClass('tag-input-style');
                    });
                }


                $('[data-rel=tooltip]').tooltip({container:'body'});
                $('[data-rel=popover]').popover({container:'body'});

                autosize($('textarea[class*=autosize]'));

                $('textarea.limited').inputlimiter({
                    remText: '%n character%s remaining...',
                    limitText: 'max allowed : %n.'
                });

                $.mask.definitions['~']='[+-]';
                $('.input-mask-date').mask('99/99/9999');
                $('.input-mask-phone').mask('(999) 999-9999');
                $('.input-mask-eyescript').mask('~9.99 ~9.99 999');
                $(".input-mask-product").mask("a*-999-a999",{placeholder:" ",completed:function(){alert("You typed the following: "+this.val());}});



                $( "#input-size-slider" ).css('width','200px').slider({
                    value:1,
                    range: "min",
                    min: 1,
                    max: 8,
                    step: 1,
                    slide: function( event, ui ) {
                        var sizing = ['', 'input-sm', 'input-lg', 'input-mini', 'input-small', 'input-medium', 'input-large', 'input-xlarge', 'input-xxlarge'];
                        var val = parseInt(ui.value);
                        $('#form-field-4').attr('class', sizing[val]).attr('placeholder', '.'+sizing[val]);
                    }
                });

                $( "#input-span-slider" ).slider({
                    value:1,
                    range: "min",
                    min: 1,
                    max: 12,
                    step: 1,
                    slide: function( event, ui ) {
                        var val = parseInt(ui.value);
                        $('#form-field-5').attr('class', 'col-xs-'+val).val('.col-xs-'+val);
                    }
                });



                //"jQuery UI Slider"
                //range slider tooltip example
                $( "#slider-range" ).css('height','200px').slider({
                    orientation: "vertical",
                    range: true,
                    min: 0,
                    max: 100,
                    values: [ 17, 67 ],
                    slide: function( event, ui ) {
                        var val = ui.values[$(ui.handle).index()-1] + "";

                        if( !ui.handle.firstChild ) {
                            $("<div class='tooltip right in' style='display:none;left:16px;top:-6px;'><div class='tooltip-arrow'></div><div class='tooltip-inner'></div></div>")
                            .prependTo(ui.handle);
                        }
                        $(ui.handle.firstChild).show().children().eq(1).text(val);
                    }
                }).find('span.ui-slider-handle').on('blur', function(){
                    $(this.firstChild).hide();
                });


                $( "#slider-range-max" ).slider({
                    range: "max",
                    min: 1,
                    max: 10,
                    value: 2
                });

                $( "#slider-eq > span" ).css({width:'90%', 'float':'left', margin:'15px'}).each(function() {
                    // read initial values from markup and remove that
                    var value = parseInt( $( this ).text(), 10 );
                    $( this ).empty().slider({
                        value: value,
                        range: "min",
                        animate: true

                    });
                });

                $("#slider-eq > span.ui-slider-purple").slider('disable');//disable third item


                $('#id-input-file-1 , #id-input-file-2').ace_file_input({
                    no_file:'No File ...',
                    btn_choose:'Choose',
                    btn_change:'Change',
                    droppable:false,
                    onchange:null,
                    thumbnail:false //| true | large
                    //whitelist:'gif|png|jpg|jpeg'
                    //blacklist:'exe|php'
                    //onchange:''
                    //
                });
                //pre-show a file name, for example a previously selected file
                //$('#id-input-file-1').ace_file_input('show_file_list', ['myfile.txt'])


                $('#id-input-file-3').ace_file_input({
                    style: 'well',
                    btn_choose: 'Drop files here or click to choose',
                    btn_change: null,
                    no_icon: 'ace-icon fa fa-cloud-upload',
                    droppable: true,
                    thumbnail: 'small'//large | fit
                    //,icon_remove:null//set null, to hide remove/reset button
                    /**,before_change:function(files, dropped) {
                        //Check an example below
                        //or examples/file-upload.html
                        return true;
                    }*/
                    /**,before_remove : function() {
                        return true;
                    }*/
                    ,
                    preview_error : function(filename, error_code) {
                        //name of the file that failed
                        //error_code values
                        //1 = 'FILE_LOAD_FAILED',
                        //2 = 'IMAGE_LOAD_FAILED',
                        //3 = 'THUMBNAIL_FAILED'
                        //alert(error_code);
                    }

                }).on('change', function(){
                    //console.log($(this).data('ace_input_files'));
                    //console.log($(this).data('ace_input_method'));
                });


                //$('#id-input-file-3')
                //.ace_file_input('show_file_list', [
                    //{type: 'image', name: 'name of image', path: 'http://path/to/image/for/preview'},
                    //{type: 'file', name: 'hello.txt'}
                //]);




                //dynamically change allowed formats by changing allowExt && allowMime function
                $('#id-file-format').removeAttr('checked').on('change', function() {
                    var whitelist_ext, whitelist_mime;
                    var btn_choose
                    var no_icon
                    if(this.checked) {
                        btn_choose = "Drop images here or click to choose";
                        no_icon = "ace-icon fa fa-picture-o";

                        whitelist_ext = ["jpeg", "jpg", "png", "gif" , "bmp"];
                        whitelist_mime = ["image/jpg", "image/jpeg", "image/png", "image/gif", "image/bmp"];
                    }
                    else {
                        btn_choose = "Drop files here or click to choose";
                        no_icon = "ace-icon fa fa-cloud-upload";

                        whitelist_ext = null;//all extensions are acceptable
                        whitelist_mime = null;//all mimes are acceptable
                    }
                    var file_input = $('#id-input-file-3');
                    file_input
                    .ace_file_input('update_settings',
                    {
                        'btn_choose': btn_choose,
                        'no_icon': no_icon,
                        'allowExt': whitelist_ext,
                        'allowMime': whitelist_mime
                    })
                    file_input.ace_file_input('reset_input');

                    file_input
                    .off('file.error.ace')
                    .on('file.error.ace', function(e, info) {
                        //console.log(info.file_count);//number of selected files
                        //console.log(info.invalid_count);//number of invalid files
                        //console.log(info.error_list);//a list of errors in the following format

                        //info.error_count['ext']
                        //info.error_count['mime']
                        //info.error_count['size']

                        //info.error_list['ext']  = [list of file names with invalid extension]
                        //info.error_list['mime'] = [list of file names with invalid mimetype]
                        //info.error_list['size'] = [list of file names with invalid size]


                        /**
                        if( !info.dropped ) {
                            //perhapse reset file field if files have been selected, and there are invalid files among them
                            //when files are dropped, only valid files will be added to our file array
                            e.preventDefault();//it will rest input
                        }
                        */


                        //if files have been selected (not dropped), you can choose to reset input
                        //because browser keeps all selected files anyway and this cannot be changed
                        //we can only reset file field to become empty again
                        //on any case you still should check files with your server side script
                        //because any arbitrary file can be uploaded by user and it's not safe to rely on browser-side measures
                    });


                    /**
                    file_input
                    .off('file.preview.ace')
                    .on('file.preview.ace', function(e, info) {
                        console.log(info.file.width);
                        console.log(info.file.height);
                        e.preventDefault();//to prevent preview
                    });
                    */

                });

                $('#spinner1').ace_spinner({value:0,min:0,max:200,step:10, btn_up_class:'btn-info' , btn_down_class:'btn-info'})
                .closest('.ace-spinner')
                .on('changed.fu.spinbox', function(){
                    //console.log($('#spinner1').val())
                });
                $('#spinner2').ace_spinner({value:0,min:0,max:10000,step:100, touch_spinner: true, icon_up:'ace-icon fa fa-caret-up bigger-110', icon_down:'ace-icon fa fa-caret-down bigger-110'});
                $('#spinner3').ace_spinner({value:0,min:-100,max:100,step:10, on_sides: true, icon_up:'ace-icon fa fa-plus bigger-110', icon_down:'ace-icon fa fa-minus bigger-110', btn_up_class:'btn-success' , btn_down_class:'btn-danger'});
                $('#spinner4').ace_spinner({value:0,min:-100,max:100,step:10, on_sides: true, icon_up:'ace-icon fa fa-plus', icon_down:'ace-icon fa fa-minus', btn_up_class:'btn-purple' , btn_down_class:'btn-purple'});

                //$('#spinner1').ace_spinner('disable').ace_spinner('value', 11);
                //or
                //$('#spinner1').closest('.ace-spinner').spinner('disable').spinner('enable').spinner('value', 11);//disable, enable or change value
                //$('#spinner1').closest('.ace-spinner').spinner('value', 0);//reset to 0


                //datepicker plugin
                //link
                $('.date-picker').datepicker({
                    autoclose: true,
                    todayHighlight: true
                })
                //show datepicker when clicking on the icon
                .next().on(ace.click_event, function(){
                    $(this).prev().focus();
                });

                //or change it into a date range picker
                $('.input-daterange').datepicker({autoclose:true});


                //to translate the daterange picker, please copy the "examples/daterange-fr.js" contents here before initialization
                $('input[name=date-range-picker]').daterangepicker({
                    'applyClass' : 'btn-sm btn-success',
                    'cancelClass' : 'btn-sm btn-default',
                    locale: {
                        applyLabel: 'Apply',
                        cancelLabel: 'Cancel',
                    }
                })
                .prev().on(ace.click_event, function(){
                    $(this).next().focus();
                });


                $('#timepicker1').timepicker({
                    minuteStep: 1,
                    showSeconds: true,
                    showMeridian: false,
                    disableFocus: true,
                    icons: {
                        up: 'fa fa-chevron-up',
                        down: 'fa fa-chevron-down'
                    }
                }).on('focus', function() {
                    $('#timepicker1').timepicker('showWidget');
                }).next().on(ace.click_event, function(){
                    $(this).prev().focus();
                });


                $('#timepicker2').timepicker({
                    minuteStep: 1,
                    showSeconds: true,
                    showMeridian: false,
                    disableFocus: true,
                    icons: {
                        up: 'fa fa-chevron-up',
                        down: 'fa fa-chevron-down'
                    }
                }).on('focus', function() {
                    $('#timepicker2').timepicker('showWidget');
                }).next().on(ace.click_event, function(){
                    $(this).prev().focus();
                });




                if(!ace.vars['old_ie']) $('#date-timepicker1').datetimepicker({
                 //format: 'MM/DD/YYYY h:mm:ss A',//use this option to display seconds
                 icons: {
                    time: 'fa fa-clock-o',
                    date: 'fa fa-calendar',
                    up: 'fa fa-chevron-up',
                    down: 'fa fa-chevron-down',
                    previous: 'fa fa-chevron-left',
                    next: 'fa fa-chevron-right',
                    today: 'fa fa-arrows ',
                    clear: 'fa fa-trash',
                    close: 'fa fa-times'
                 }
                }).next().on(ace.click_event, function(){
                    $(this).prev().focus();
                });


                $('#colorpicker1').colorpicker();
                //$('.colorpicker').last().css('z-index', 2000);//if colorpicker is inside a modal, its z-index should be higher than modal'safe

                $('#simple-colorpicker-1').ace_colorpicker();
                //$('#simple-colorpicker-1').ace_colorpicker('pick', 2);//select 2nd color
                //$('#simple-colorpicker-1').ace_colorpicker('pick', '#fbe983');//select #fbe983 color
                //var picker = $('#simple-colorpicker-1').data('ace_colorpicker')
                //picker.pick('red', true);//insert the color if it doesn't exist


                $(".knob").knob();


                var tag_input = $('#form-field-tags');
                try{
                    tag_input.tag(
                      {
                        placeholder:tag_input.attr('placeholder'),
                        //enable typeahead by specifying the source array
                        source: ace.vars['US_STATES'],//defined in ace.js >> ace.enable_search_ahead
                        /**
                        //or fetch data from database, fetch those that match "query"
                        source: function(query, process) {
                          $.ajax({url: 'remote_source.php?q='+encodeURIComponent(query)})
                          .done(function(result_items){
                            process(result_items);
                          });
                        }
                        */
                      }
                    )

                    //programmatically add/remove a tag
                    var $tag_obj = $('#form-field-tags').data('tag');
                    $tag_obj.add('Programmatically Added');

                    var index = $tag_obj.inValues('some tag');
                    $tag_obj.remove(index);
                }
                catch(e) {
                    //display a textarea for old IE, because it doesn't support this plugin or another one I tried!
                    tag_input.after('<textarea id="'+tag_input.attr('id')+'" name="'+tag_input.attr('name')+'" rows="3">'+tag_input.val()+'</textarea>').remove();
                    //autosize($('#form-field-tags'));
                }


                /////////
                $('#modal-form input[type=file]').ace_file_input({
                    style:'well',
                    btn_choose:'Drop files here or click to choose',
                    btn_change:null,
                    no_icon:'ace-icon fa fa-cloud-upload',
                    droppable:true,
                    thumbnail:'large'
                })

                //chosen plugin inside a modal will have a zero width because the select element is originally hidden
                //and its width cannot be determined.
                //so we set the width after modal is show
                $('#modal-form').on('shown.bs.modal', function () {
                    if(!ace.vars['touch']) {
                        $(this).find('.chosen-container').each(function(){
                            $(this).find('a:first-child').css('width' , '210px');
                            $(this).find('.chosen-drop').css('width' , '210px');
                            $(this).find('.chosen-search input').css('width' , '200px');
                        });
                    }
                })

                /**
                //or you can activate the chosen plugin after modal is shown
                //this way select element becomes visible with dimensions and chosen works as expected
                $('#modal-form').on('shown', function () {
                    $(this).find('.modal-chosen').chosen();
                })
                */

                $(document).one('ajaxloadstart.page', function(e) {
                    autosize.destroy('textarea[class*=autosize]')
                    $('.limiterBox,.autosizejs').remove();
                    $('.daterangepicker.dropdown-menu,.colorpicker.dropdown-menu,.bootstrap-datetimepicker-widget.dropdown-menu').remove();
                });

            });
        </script>
        <!-- inline scripts related to this page -->
		<script type="text/javascript">
				$('body').attr('class', 'login-layout light-login');
				$('#id-text2').attr('class', 'grey');
				$('#id-company-text').attr('class', 'blue');

		</script>
	</body>
</html>
