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
function fn_select_task(tSeq){
	document.frm.tSeq.value = tSeq;
	document.frm.action = "<c:url value='/com/task/tSelect.do'/>"; 
	document.frm.submit();
} 
function fn_egov_select_noticeList(pageNo){
    document.searchForm.pageIndex.value = pageNo;
	document.searchForm.action = "<c:url value='${tmpPath}/com/task/taskList.do'/>";
	document.searchForm.submit();
}
</script>
	
		<h1>과제목록</h1>
		<div class = "fr">
		<button type="button" class="btn" onclick="location.href='<c:url value='/com/user/uList.do'/>'">사용자목록</button>
		<button type="button" class="btn01" onclick="location.href='<c:url value='/com/task/tInsertView.do'/>'">추가</button>
		</div>
		<form name="searchForm" method="post">
		<input type="hidden" name="pageIndex" value="1"/>
		</form>
		<form name="frm" method="post">
		<input type="hidden" name="tSeq"/>
		<table class="tablelist">
			<thead>
				<tr>
					<th>NO</th>
					<th>과제명</th>
					<th>기간</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="result" items="${resultList}" varStatus="status">
				<tr>
					<td>${(userSearchVO.pageIndex - 1) * 10 + status.count}</td>
					<td><a href="#LINK" onclick="javascript:fn_select_task('<c:out value='${result.tSeq}'/>')"><c:out value="${result.tNm}"/></a></td>
					<td><c:out value="${result.tSdate}"/> ~ <c:out value="${result.tEdate}"/></td>
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
