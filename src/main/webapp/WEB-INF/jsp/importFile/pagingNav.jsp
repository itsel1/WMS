<nav style="text-align: center;heigth:80px;padding:0px;">
	<ul class="pagination" style="padding:0px;margin:0px;">
		<c:if test="${paging.prevNum > 0}">
			<li class="page-item">
				<%-- <a class="page-link" href="?page=${paging.prevNum}<c:if test = "${searchKeyword ne null}">&keywords=${searchKeyword}</c:if>"> Prev </a> --%>
				<a onclick="fn_prv('${paging.prevNum}')">Prev</a>
			</li>
		</c:if>
		<c:forEach var="num" begin="${paging.pagingStart}" end="${paging.pagingEnd }">
			<li class="page-item <c:if test = "${num eq paging.currentPage}"> active	</c:if>">
				<%-- <a href="?page=${num}<c:if test = "${searchKeyword ne null}">&keywords=${searchKeyword}</c:if>">${num}</a> --%>
				<a onclick="fn_num('${num}')">${num}</a>
			</li>
		</c:forEach>
		<c:if test="${paging.endPage eq false}">
			<li class="page-item">
				<%-- <a href="?page=${paging.nextNum}<c:if test = "${searchKeyword ne null}">&keywords=${searchKeyword}</c:if>"> Next </a> --%>
				<a onclick="fn_next('${paging.nextNum}')">Next</a>
			</li>
		</c:if>
	</ul>
</nav>

<script type="text/javascript">
	function fn_prv(prvNum){
		var para = location.href.split("?");
		location.href=para[0]+"?page="+prvNum+"&"+$("form").serialize();
	}
	function fn_num(num){
		var para = location.href.split("?");
		location.href=para[0]+"?page="+num+"&"+$("form").serialize();
	}
	function fn_next(nextNum){
		var para = location.href.split("?");
		location.href=para[0]+"?page="+nextNum+"&"+$("form").serialize();
	}
</script>
