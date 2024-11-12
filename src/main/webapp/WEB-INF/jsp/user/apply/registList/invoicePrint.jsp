<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<script src="https://html2canvas.hertzen.com/dist/html2canvas.min.js"></script>
    <!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.5.3/jspdf.debug.js"></script> -->
    <script src="https://cdnjs.cloudflare.com/ajax/libs/jspdf/1.0.272/jspdf.debug.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/0.4.1/html2canvas.js"></script>
	<style type="text/css">
	@font-face {
	font-family: 'Ms gothic';
	src: url(/fonts/msgothic.ttf);
}
	
		.fixdiv{position:absolute;word-break:break-all;line-height:1em;}
		.bold {font-weight: bold;}
		.Arial {font-family: Arial;}
		.MSG {font-family: Ms gothic;}
	 </style>
	<!-- basic scripts -->
	</head> 
	<body class="no-skin"  style="margin:0px !important">
		<c:forEach var="i" begin="0" end="0" varStatus="status">
			<div style="width:280pt;height:578pt;position:relative;" id="test${status.index}" name="test" class="ffsfs">
				<div style="top:21pt; left:20pt;" class="fixdiv" ><img style="height:10mm;width:22.5mm;"src="/image/INSP_IN_1800.JPEG"/></div> <!-- NM7 바코드 1 delivery branch barcode-->
				<div style="top:21pt; left:90pt;font-size:6pt;" class="fixdiv MSG">発送日20年6月30日</div> <!-- 선적 일자 2-->
				<div style="top:43pt; left:90pt;font-size:7pt;" class="fixdiv MSG bold">便種 : ${status.count}便種</div>  <!-- 선적 종류 3-->
				<!-- <div style="top:31pt; left:85pt;font-size:7pt;" class="fixdiv MSG">【配達指定】 07 月 03 日</div> 요청 배송일 4
				<div style="top:39pt; left:85pt;font-size:8pt;" class="fixdiv MSG bold">【時間带指定】 12時 ~ 14時</div> 요청 배송 시간 5 -->
				<div style="top:21pt; left:160pt;font-size:6pt;" class="fixdiv MSG">個数 3</div> <!-- 개수 6-->
				<div style="top:26pt; left:160pt;font-size:30pt; width:85pt; text-align: right" class="fixdiv Arial bold">41555</div> <!-- delivery branch barcode 7-->
				<div style="top:35pt; left:245pt;font-size:18pt;" class="fixdiv Arial bold">683</div> <!-- local코드 8-->
				<div style="top:51pt; left:23pt;font-size:6pt;" class="fixdiv MSG">干 8104</div> <!-- receiver zip code 9-->
				<div style="top:51pt; left:73pt;font-size:6pt;" class="fixdiv MSG">TEL 075-661-8675</div> <!-- receiver zip code 10-->
				<div style="top:59pt; left:23pt;font-size:6pt; width:125pt;" class="fixdiv MSG">東京都江東区亀戸1-1-131</div> <!-- receiver addr 11-->
				<div style="top:70pt; left:23pt;font-size:9pt; width:125pt;" class="fixdiv MSG">東京都江東区亀戸1-1-13</div> <!-- receiver addr detail 12-->
				<div style="top:83pt; left:23pt;font-size:9pt; width:125pt;" class="fixdiv MSG">合也送合也送合也送</div> <!-- receiver name 13-->
				<div style="top:54pt; left:140pt;" class="fixdiv"><img style="height:13.5mm;width:43mm;"src="/image/INSP_IN_1800.JPEG"/></div> <!-- NM7 바코드 14 tracking number barcode-->
				<div style="top:97pt; left:150pt;font-size: 7pt;" class="fixdiv MSG">お問合也送リ状: d123456789012d</div> <!-- tracking number 15-->
				<div style="top:106pt; left:186pt;font-size: 6pt;" class="fixdiv MSG">お問 </div> <!-- pickup branch 16-->
				<div style="top:106pt; left:213pt;font-size: 6pt;" class="fixdiv MSG">TEL 0120-333-803</div> <!-- pickup branch 16-->
				<div style="top:97pt; left:20pt;font-size: 9pt; width:125pt;" class="fixdiv MSG">보내는 사람 주문정보 - 이름</div> <!-- sender's name/ 17-->
				<div style="top:111pt; left:20pt;font-size: 9pt; width:125pt;" class="fixdiv MSG">보내는 사람 주문정보 - 주소</div> <!-- sender's addr/ 17-->
				<div style="top:118pt; left:152pt;font-size: 6pt;" class="fixdiv MSG">Tel. 0300000000000000000</div> <!-- sender's phone 18-->
				<div style="top:170pt; left:140pt;font-size: 7pt;" class="fixdiv MSG">発送日20年6月30日 </div> <!-- sender's phone 2-2-->
				<div style="top:178pt; left:140pt;font-size: 7pt;" class="fixdiv MSG">お問合也送リ状: d123456789012d</div> <!-- tracking number 2-3-->
				<div style="top:172pt; left:250pt;font-size: 7pt;" class="fixdiv MSG">個数: 3</div> <!-- tracking number 2-4-->
				<div style="top:186pt; left:20pt;font-size:6pt;" class="fixdiv MSG">干 8104</div> <!-- receiver zip code 2-5-->
				<div style="top:186pt; left:70pt;font-size:6pt;" class="fixdiv MSG">TEL 075-661-8675</div> <!-- receiver zip code 2-6-->
				<div style="top:192pt; left:20pt;font-size:6pt; width:118pt;" class="fixdiv MSG">東京都江東区亀戸1-1-13</div> <!-- receiver addr 2-7-->
				<div style="top:204pt; left:20pt;font-size:9pt; width:118pt;" class="fixdiv MSG">東京都江東区亀戸1-1-13</div> <!-- receiver addr detail 2-8-->
				<div style="top:220pt; left:20pt;font-size:9pt; width:118pt;" class="fixdiv MSG">合也送合也送合也ssss送</div> <!-- receiver name 2-9-->
				<div style="top:233pt; left:20pt;font-size:6pt; width:118pt;" class="fixdiv MSG">東京都江東区亀戸1-1-13 東京都江東区亀東京都</div> <!-- receiver addr 2-10-->
				<div style="top:248pt; left:20pt;font-size:9pt; width:118pt;" class="fixdiv MSG">東京都江東区亀戸1-1-13</div> <!-- receiver addr detail 2-11-->
				<div style="top:264pt; left:20pt;font-size:9pt; width:118pt;" class="fixdiv MSG">合也送合也送合也ssss送</div> <!-- receiver name 2-9-->
				<div style="top:186pt; left:140pt;font-size:10pt;" class="fixdiv MSG bold">***************</div> <!-- receiver name 2-13-->
				<div style="top:193pt; left:140pt;font-size:10pt;" class="fixdiv MSG bold">***************</div> <!-- receiver name 2-14-->
				<div style="top:200pt; left:140pt;font-size:10pt;" class="fixdiv MSG bold">***************</div> <!-- receiver name 2-15-->
				<div style="top:207pt; left:140pt;font-size:10pt;" class="fixdiv MSG bold">***************</div> <!-- receiver name 2-16-->
				<div style="top:214pt; left:140pt;font-size:10pt;" class="fixdiv MSG bold">***************</div> <!-- receiver name 2-17-->
				<div style="top:186pt; left:140pt;font-size:10pt;" class="fixdiv MSG bold">********</div> <!-- receiver name 2-19-->
				<div style="top:233pt; left:140pt;font-size:10pt;" class="fixdiv MSG bold">********</div> <!-- receiver name 2-19-->
				<div style="top:240pt; left:140pt;font-size:10pt;" class="fixdiv MSG bold">********</div> <!-- receiver name 2-19-->
				<div style="top:247pt; left:140pt;font-size:10pt;" class="fixdiv MSG bold">********</div> <!-- receiver name 2-19-->
				<div style="top:254pt; left:140pt;font-size:10pt;" class="fixdiv MSG bold">********</div> <!-- receiver name 2-19-->
				<div style="top:261pt; left:140pt;font-size:10pt;" class="fixdiv MSG bold">********</div> <!-- receiver name 2-19-->
				<div style="top:268pt; left:140pt;font-size:10pt;" class="fixdiv MSG bold">********</div> <!-- receiver name 2-19-->
				
				<div style="top:196pt; left:220pt;" class="fixdiv"><img style="height:5.5mm;width:12.5mm;"src="/image/INSP_IN_1800.JPEG"/></div> <!-- NM7 바코드 20 tracking number barcode-->
				<div style="top:213pt; left:220pt;font-size: 7pt;width:12.5mm; text-align: center;" class="fixdiv MSG">2KG</div> <!-- tracking number 21-->
				<div style="top:221pt; left:220pt;" class="fixdiv"><img style="height:5.5mm;width:12.5mm;"src="/image/INSP_IN_1800.JPEG"/></div> <!-- NM7 바코드 20 tracking number barcode-->
				<div style="top:238pt; left:220pt;font-size: 7pt; width:12.5mm; text-align: center;" class="fixdiv MSG">5KG</div> <!-- tracking number 21-->
				<div style="top:246pt; left:220pt;" class="fixdiv"><img style="height:5.5mm;width:12.5mm;"src="/image/INSP_IN_1800.JPEG"/></div> <!-- NM7 바코드 20 tracking number barcode-->
				<div style="top:263pt; left:220pt;font-size: 7pt; width:12.5mm; text-align: center;" class="fixdiv MSG">10KG</div> <!-- tracking number 21-->
				
				
				<div style="top:269pt; left:190pt;font-size:10pt;" class="fixdiv MSG bold">********</div> <!-- receiver name 2-26-->
				<div style="top:276pt; left:190pt;font-size:10pt;" class="fixdiv MSG bold">********</div> <!-- receiver name 2-26-->
				<div style="top:283pt; left:190pt;font-size:10pt;" class="fixdiv MSG bold">********</div> <!-- receiver name 2-26-->
				<div style="top:290pt; left:190pt;font-size:10pt;" class="fixdiv MSG bold">********</div> <!-- receiver name 2-26-->
				<div style="top:297pt; left:190pt;font-size:10pt;" class="fixdiv MSG bold">********</div> <!-- receiver name 2-26-->
				<div style="top:304pt; left:190pt;font-size:10pt;" class="fixdiv MSG bold">********</div> <!-- receiver name 2-26-->
				<div style="top:311pt; left:190pt;font-size:10pt;" class="fixdiv MSG bold">********</div> <!-- receiver name 2-26-->
				<div style="top:318pt; left:190pt;font-size:10pt;" class="fixdiv MSG bold">********</div> <!-- receiver name 2-26-->
				
				<div style="top:269pt; left:235pt;font-size:10pt;" class="fixdiv MSG bold">********</div> <!-- receiver name 2-29-->
				<div style="top:276pt; left:235pt;font-size:10pt;" class="fixdiv MSG bold">********</div> <!-- receiver name 2-29-->
				<div style="top:283pt; left:235pt;font-size:10pt;" class="fixdiv MSG bold">********</div> <!-- receiver name 2-29-->
				<div style="top:290pt; left:235pt;font-size:10pt;" class="fixdiv MSG bold">********</div> <!-- receiver name 2-29-->
				<div style="top:297pt; left:235pt;font-size:10pt;" class="fixdiv MSG bold">********</div> <!-- receiver name 2-29-->
				<div style="top:304pt; left:235pt;font-size:10pt;" class="fixdiv MSG bold">********</div> <!-- receiver name 2-29-->
				<div style="top:311pt; left:235pt;font-size:10pt;" class="fixdiv MSG bold">********</div> <!-- receiver name 2-29-->
				<div style="top:318pt; left:235pt;font-size:10pt;" class="fixdiv MSG bold">********</div> <!-- receiver name 2-29-->
				
				
				<div style="top:334pt; left:15pt;font-size:7pt;" class="fixdiv MSG bold">**************</div> <!-- receiver name 2-29-->
				<div style="top:334pt; left:140pt;font-size: 6pt;" class="fixdiv MSG">お問 </div> <!-- pickup branch 16-->
				<div style="top:334pt; left:160pt;font-size: 6pt;" class="fixdiv MSG">TEL 0120-333-803</div> <!-- pickup branch 16-->
				<div style="top:342pt; left:140pt;font-size:6pt;" class="fixdiv MSG">発送日20年6月30日</div> <!-- 선적 일자 2-->
				<div style="top:342pt; left:265pt;font-size:11pt;" class="fixdiv MSG">3</div> <!-- 개수 6-->
				            
				<div style="top:351pt; left:48pt;font-size:9pt;" class="fixdiv MSG">干 8104</div> <!-- receiver zip code 9-->
				<div style="top:351pt; left:88pt;font-size:9pt;" class="fixdiv MSG">TEL 075-661-8675</div> <!-- receiver zip code 10-->
				<div style="top:361pt; left:48pt;font-size:13pt; width:220pt;" class="fixdiv MSG">東京都江東区亀戸1-1-131 東京都江東区亀戸1-1-131 東京都江東区亀戸1-1-131</div> <!-- receiver addr 11-->
				<div style="top:389pt; left:48pt;font-size:13pt; width:220pt;" class="fixdiv MSG">東京都江東区亀戸1-1-13</div> <!-- receiver addr detail 12-->
				<div style="top:411pt; left:48pt;font-size:13pt; width:220pt;" class="fixdiv MSG">合也送合也送合也送</div> <!-- receiver name 13-->
				            
				<div style="top:431pt; left:35pt;" class="fixdiv"><img style="height:8.5mm;width:53mm;"src="/image/INSP_IN_1800.JPEG"/></div> <!-- NM7 바코드 14 tracking number barcode-->
				<div style="top:463pt; left:43pt;font-size:13pt;" class="fixdiv MSG">830011</div> <!-- receiver name 13-->
				<div style="top:463pt; left:88pt;font-size:13pt;" class="fixdiv MSG">400026269241</div> <!-- receiver name 13-->
				<div style="top:463pt; left:170pt;font-size:13pt;" class="fixdiv MSG">119720230001</div> <!-- receiver name 13-->
				            
				<div style="top:483pt; left:20pt;font-size: 7pt; width:125pt;" class="fixdiv MSG">보내는 사람 주문정보 - 이름</div> <!-- sender's name/ 17-->
				<div style="top:503pt; left:20pt;font-size: 9pt; width:125pt;" class="fixdiv MSG">보내는 사람 주문정보 - 주소</div> <!-- sender's addr/ 17-->
				<div style="top:483pt; left:155pt;font-size: 7pt;" class="fixdiv MSG">Tel. 030000000000</div> <!-- sender's phone 18-->
				<div style="top:543pt; left:235pt;font-size:7pt;" class="fixdiv MSG bold">便種 :  便種</div>  <!-- 선적 종류 3-->
			</div>
		</c:forEach>
	
	<script src="/assets/js/jquery-2.1.4.min.js"></script>
	<script type="text/javascript">
	var imgData = "";
	var imgWidth = "";
	var pageHeight = "";
	var imgHeight = "";
	
	var doc = new jsPDF({
		'orientation': 'p',
		'unit': 'mm',
		'format': 'a4'
	});
	 jQuery(function($) {
		 var pdf = new jsPDF({
				'orientation': 'p',
				'unit': 'mm',
				'format': 'a4'
			});
	        var pdfName = 'test.pdf';

	        var options = {
				format:'png'
	    	        };

	        var $divs = $('.ffsfs')                //jQuery object of all the myDivClass divs
	        var numRecursionsNeeded = $divs.length -1;     //the number of times we need to call addHtml (once per div)
	        var currentRecursion=0;

	        //Found a trick for using addHtml more than once per pdf. Call addHtml in the callback function of addHtml recursively.
	        function recursiveAddHtmlAndSave(currentRecursion, totalRecursions){
	            //Once we have done all the divs save the pdf
	            if(currentRecursion==totalRecursions){
	                pdf.save(pdfName);
	            }else{
	                currentRecursion++;
	                pdf.addPage();
	                //$('.myDivClass')[currentRecursion] selects one of the divs out of the jquery collection as a html element
	                //addHtml requires an html element. Not a string like fromHtml.
	                pdf.addHTML($('.ffsfs')[currentRecursion], 15, 20, options, function(){
	                    console.log(currentRecursion);
	                    recursiveAddHtmlAndSave(currentRecursion, totalRecursions)
	                });
	            }
	        }

	        pdf.addHTML($('.ffsfs')[currentRecursion], 15, 20, options, function(){
	            recursiveAddHtmlAndSave(currentRecursion, numRecursionsNeeded);
	        });

		 
//	var targetNum = 0;
//	var endNum = $("div[name*='test']").length;
//
//
//	var pdf = new jsPDF('p', 'pt', 'a4');
//
//    var pdfName = 'sample.pdf';
//
//    var options = {
//        format: 'JPEG',
//          pagesplit: true,
//        "background": '#000',
//    };
//
//    /* var fullPage = $('#Printout_21571')[0],
//        firstPartPage = $('#part-1')[0],
//        secondPartPage = $('#part-2')[0]; */
//    var firstPartPage = $("#test0")[0],
//    	secondPartPage = $("#test0")[0];
//
//    pdf.html(document.body, {pagesplit: true}, {
//        callback: function(pdf){
//        	pdf.save('web.pdf');
//            }
//    	});
//
		/* doc.addHTML(firstPartPage, 15, 20, options, function(){ pdf.addPage() });
        doc.addHTML(secondPartPage, 15, 20, options, function(){}); */
        /* doc.save(pdfName); */




		
		/* html2canvas(document.querySelector("#test1")).then(function(canvas) {
			var imgData = canvas.toDataURL('image/png');
			var imgWidth = 210;
			var pageHeight = imgWidth * 1.414;
			var imgHeight = canvas.height * imgWidth / canvas.width;
			
			doc.addImage(imgData, 'PNG', 0, 0, imgWidth, imgHeight);
			doc.addPage();
			window.open(doc.output('bloburl'), '_blank');
			window.close();
		}); */
		
		/* window.close(); */
				
	});
	 /* window.open(doc.output('bloburl'), '_blank'); */
	</script>
	</body>
</html>
