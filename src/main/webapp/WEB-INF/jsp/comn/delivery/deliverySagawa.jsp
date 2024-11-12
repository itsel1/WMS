<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="en">
	<head>
	<%@ include file="/WEB-INF/jsp/importFile/head.jsp" %>
	<style type="text/css">
	  body {
		background-color:#ffffff;
		color:#696969;
		margin:0px;
		padding:2px;
		font-size:9px;
		font-family:"Microsoft Yahei", "PingHei", Calibri, "Open Sans", Candara, Segoe, "Segoe UI", Optima, Arial, sans-serif;
		position:relative;
		scrollbar-face-color: #FFFFFF;
		scrollbar-shadow-color: #D0D0D0;
		scrollbar-highlight-color: #FFFFFF;
		scrollbar-3dlight-color: #D0D0D0;
		scrollbar-darkshadow-color: #D0D0D0;
		scrollbar-track-color: #F7F7F7;
		scrollbar-arrow-color: #D0D0D0;
		padding-bottom: 6em;
		min-width: 30em; /* for the tabs, mostly */
		background-color: #FEFEFE;
		/*background-image:url('/img/bgt_body.gif');*/
		background-image:url('/img/bodybg.gif');
		background-repeat:repeat-x;
	}
	.subtitle {
		font-size: 20px;
		text-align: left;
		line-height: 1.3;
		padding: 1px;
		font-weight: bold;
		vertical-align: top;
		width: 300px;
		border-left: 5px solid #369;
		border-bottom: 1px solid #ccc;
	
	
	}
	table,td {
		
		font-size:11px;
		color:#333333;
		letter-spacing:0;
		line-height:110%;
	}
	.htmltable {
		border: 1px solid #BBBBBB;
		margin-left:2px;
		border-top:1px solid #BBBBBB;
		border-bottom:1px solid #BBBBBB;
		bottom: 1px;
		border-collapse:collapse;
		margin:1px 0 0 1px;
	}
	.htmltable table {
		border-collapse:collapse;
		margin:1px 0 0 1px;
	}
	.htmltable thead {
		background-color:#f5f5f5;
	}
	.htmltable td {
		height:25px;
		/*text-align:left;*/
		vertical-align:middle;
		border-width:1px;
		border-color:#cccccc;
		border-style:solid;
	
	}
	.htmltable img {
		float:none;
		margin:auto;
	}
	 </style>
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
	<!-- basic scripts End-->
	</head> 
	<title>Tracking </title>
	<body class="no-skin">
		<h1 class="subtitle"> ${hawbNo}</h1>
		
	<table width="600" style="margin:auto;">
		<tr>
			<td><p align=center><b/>Track Shipments Detailed Results</p>
				<table  class='htmltable' width='100%'>
					<tr>
						<td bgcolor="#DEE9EF" height=25 width="30%">
							<div align="right">Tracking Number</div>
						</td>
						<td width="70%">
							&nbsp;<b>${hawbNo}            </b> &nbsp; 
						</td>
					</tr>
					<tr>
						<td bgcolor="#DEE9EF" height=25>
							<div align="right">Receiver Name</div>
						</td>
						<td width="246"> 
							${deliverVO.cneeName} 
						</td>
					</tr>
					<tr>
						<td bgcolor="#DEE9EF" height=25>
							<div align="right">Receiver Address</div>
						</td>
						<td > 
							${deliverVO.nativeCneeAddr }
						</td>
					</tr>
				</table>
			</td>
		</tr>
		<tr>
			<td>
				&nbsp;
			</td>
		</tr>
		<tr>
			<td>
				<table class='htmltable' width='100%'>
					<tr bgcolor=#DEE9EF>
						<td height=22  align=center>
							Date
						</td>
						<td height=22  align=center>
							Time
						</td>
						<td  align=center>
							Area
						</td>
						<td  align=center>
							Detail
						</td>
					</tr>
					<c:if test = "${deliverVO.orderInDate ne '' }">
						<tr bgcolor=#FFFFFF>
							<td height=22  align=center>
								${fn:substring(deliverVO.orderInDate,0,8) }
							</td>
							<td  align=center>
							
							</td>
							<td  align=center>
								${deliverVO.area}
							</td>
							<td  align=center>
								配送の準備中。<br>
								<font color='#595655'>Preparing for delivery.</font>
							</td>
						</tr>
						<c:if test = "${deliverVO.hawbInTime ne '' }">
							<tr bgcolor=#FFFFFF>
								<td height=22  align=center>
									${fn:replace(fn:substringBefore(deliverVO.hawbInTime, ' '),'-','')}
								</td>
								<td  align=center>
									${fn:replace(fn:substring(fn:substringAfter(deliverVO.hawbInTime, ' '),0,5),':','')}
								</td>
								<td  align=center>
									${deliverVO.area}
								</td>
								<td  align=center>
									ACI倉庫に商品入荷<br>
									<font color='#595655'>ACI(Local) warehouse receiving</font>
								</td>
							</tr>
							<c:if test = "${deliverVO.mawbInTime ne '' }">
								<tr bgcolor=#FFFFFF>
									<td height=22  align=center>
										${fn:replace(fn:substringBefore(deliverVO.mawbInTime, ' '),'-','')}
									</td>
									<td  align=center>
										${fn:replace(fn:substring(fn:substringAfter(deliverVO.mawbInTime, ' '),0,5),':','')}
									</td>
									<td  align=center>
										${deliverVO.area}
									</td>
									<td  align=center>
										LHR空港に移動中<br>
										<font color='#595655'>Shipping to LHR Airport</font>
									</td>
								</tr>
								<c:if test = "${deliverVO.depDate ne '' }">
									<tr bgcolor=#FFFFFF>
										<td height=22  align=center>
											${deliverVO.depDate}
										</td>
										<td  align=center>
											${deliverVO.depTime}
										</td>
										<td  align=center>
											${deliverVO.area}
										</td>
										<td  align=center>
											LHRから発送 <br>
											<font color='#595655'>Dispatch from LHR Airport</font>
										</td>
									</tr>
									<c:if test = "${deliverVO.arrDate ne '' }">
										<tr bgcolor=#FFFFFF>
											<td height=22  align=center>
												${deliverVO.arrDate}
											</td>
											<td  align=center>
												${deliverVO.arrTime}
											</td>
											<td  align=center>
												OSA
											</td>
											<td  align=center>
												[日本(KIX)に到着 <br>
												<font color='#595655'> Arrived at (KIX) Airport
											</td>
										</tr>
									</c:if>
								</c:if> 
							</c:if>
						</c:if>
					</c:if>
				</table>
				<div align=center><br>*表示された時間は各サービス地域の現在時間です。</div><br>
				通関手続中<br>
				*通関処理のため、日本到着後通関代行会社からお客様が記載していただいたご連絡先（お電話及びＳＭＳ）に直接連絡が行く予定です。<br>
				ご連絡がとれない場合は、通関手続きを行うことができませんでの、必ずお電話をお取りいただきご確認頂けます様お願い申し上げます。<br>
				お問い合わせ窓口 : 会社名：SCORE JAPAN / 担当者： 関空カスタマーML  / 連絡先：050-5434-9991 <br>
				<br>
				<br>
				>>Sagawa <a href='http://k2k.sagawa-exp.co.jp/p/web/okurijosearch.do?okurijoNo=${hawbNo}' target='_sagawa'>[go]</a><br>
				通関手続き完了後SAGAWA追跡が可能になります。上記「go」ブータンをクリックしてください<br>
				
				
				<table width="100%" class='htmltable' >
					<tr>
						<td  height="30" align="center" bgcolor="#F7F2F1">
							<strong>日付</strong>
						</td>
						<td  align="center" bgcolor="#F7F2F1">
							<strong>区分</strong>
						</td>
						<td align="center" bgcolor="#F7F2F1">
							<strong>詳細説明(配達結果) </strong>
						</td>
					</tr>
					<c:forEach items="${rtnList}" var="rtn" varStatus="status">
						<tr bgcolor="#ffffff">
							<td style="height:30px;text-align: center">
								${rtn.DATE_EVENT}
							</td>
							<td style="height:30px;text-align: center">
								${rtn.TRACE_STATUS}
							</td>
							<td style="height:30px;text-align: center">
								${rtn.POSITION}
							</td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
	</table>
</body>
</html>
