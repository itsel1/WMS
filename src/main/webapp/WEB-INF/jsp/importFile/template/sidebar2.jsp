<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<div class="sidebar-menu">
	<div class="menu-toggle" onclick="fn_toggleSidebar();">
		<i class="fa-solid fa-chevron-right arrow"></i>
	</div>
	<div class="sidebar">
		<div class="menu-links">
			
			<div class="menu-link">
				<a href="#">
					<i class="fa-sharp fa-solid fa-house menu-icon"></i>
					Dashboard
				</a>
			</div>
			
			<div class="menu-link">
				<a href="#">
					<i class="fa-sharp fa-solid fa-truck menu-icon"></i>
					일반 배송
				</a>
				<div class="sub-menu">
					<a href="#">배송 등록</a>
					<a href="#">송장 출력</a>
					<a href="#">발송 대기 목록</a>
				</div>
			</div>
			
			<div class="menu-link">
				<a href="#">
					<i class="fa-solid fa-box-open menu-icon"></i>
					검품 배송
				</a>
				<div class="sub-menu">
					<a href="#">배송 등록</a>
					<a href="#">등록 목록</a>
					<a href="#">입출고 목록</a>
				</div>
			</div>
			
		</div>
	</div>
</div>
<script>

function fn_toggleSidebar() {
	document.querySelector(".sidebar-menu").classList.toggle("active");
	document.querySelectorAll(".sidebar-menu .sidebar .menu-links .menu-link a").forEach(function(obj) {
		obj.addEventListener("click", function(e) {
			if (e.target.parentNode.children.length > 1) {
				e.target.parentNode.classList.toggle("active");
			}
		});
	});
}

</script>