<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="ui" uri="http://egovframework.gov/ctl/ui"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
    <link rel="stylesheet" type="text/css" href="<c:url value='/css/stask/layout.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/stask/board.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/stask/common.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/stask/fontawesome-all.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/stask/kendo.common.min.css'/>">
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/stask/kendo.material.min.css'/>">
<script>
function fn_select_user(uSeq){
	document.frm.uSeq.value = uSeq;
	document.frm.action = "<c:url value='/com/user/uSelect.do'/>"; 
	document.frm.submit();
} 
function fn_egov_select_noticeList(pageNo) {
    document.searchForm.pageIndex.value = pageNo;
	document.searchForm.action = "<c:url value='${tmpPath}/com/user/uList.do'/>";
	document.searchForm.submit();
}
</script>
		<h1>사용자목록</h1>
		<div class = "fr">
		<button type="button" class="btn04" onclick="location.href='<c:url value="/com/task/tList.do"/>'">과제목록</button>
		<button type="button" class="btn05" onclick="location.href='<c:url value='/com/user/uInsertView.do'/>'">추가</button>
		</div>
		<form name="searchForm" method="post">
		<input type="hidden" name="pageIndex" value="1"/>
		</form>
		<a href = "http://localhost:8080/simpletask/com/user/uSelect.do?uSeq=11">scv1</a>
		<a href = "http://localhost:8080/simpletask/com/user/uSelect.do?uSeq=4">bbb</a>
		<a href = "http://localhost:8080/simpletask/com/user/uSelect.do?uSeq=26">john</a>
	
		
		
		<form name="frm" method="post">
		<input type="hidden" name="uSeq"/>
		<table class="tablelist">
			<thead>
				<tr>
					<th>NO</th>
					<th>ID</th>
					<th>사용자명</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="result" items="${resultList}" varStatus="status">
				<tr>
					<td>${(userSearchVO.pageIndex - 1) * 10 + status.count}</td>
					<td><a href = "<c:url value='/com/user/uSelect.do'/>?uSeq=<c:out value='${result.uSeq }'/>">
					<c:choose>
					  <c:when test="${result.uId == 'john'}">존</c:when>
					  <c:otherwise><c:out value="${result.uId}"/></c:otherwise>
					</c:choose>
					</a></td>
					<td><c:out value="${result.uNm}"/>,${result.uSeq }</td>
				</tr>
				</c:forEach>
			</tbody>
		</table>
		<div class="pageNum">
		 <div class="pagination">
			<ul class="pagination pagination-sm">
				<ui:pagination paginationInfo="${paginationInfo}" type="image" jsFunction="fn_egov_select_noticeList"/>
			</ul>
		 </div>
	    </div>
		</form>
