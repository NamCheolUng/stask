<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>


<script type="text/javascript">
function fn_select_task(tSeq){
	document.taskVO.tSeq.value = tSeq;
	document.taskVO.action = "<c:url value='/com/task/tUpdate.do'/>";
	document.taskVO.submit();
}
function fn_delete_task(tSeq){
	document.taskVO.tSeq.value = tSeq;
	document.taskVO.action = "<c:url value='/com/task/tDelete.do'/>";
	document.taskVO.submit();
}
</script>


   <h1>과제정보</h1>
	<form name="taskVO" method="post">
	 <input type="hidden" name="tSeq"/>
		과제명  <input type="text" name="tNm" size="15" maxlength="10" value="<c:out value='${tVO.tNm}'/>"/><br/>
	             기간  <input type="text" name="tSdate" size="15" value="<c:out value='${tVO.tSdate}'/>" maxlength="10"/> ~ <input type="text" name="tEdate" size="15" value="<c:out value='${tVO.tEdate}'/>" maxlength="10"/><br/>
	         <a href="#LINK" onclick="javascript:fn_select_task('<c:out value='${tVO.tSeq}'/>')"><button type="button">수정</button></a>
	         <a href="#LINK" onclick="javascript:fn_delete_task('<c:out value='${tVO.tSeq}'/>')"><button type="button">삭제</button></a>
	</form>
