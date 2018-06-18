<%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

    <script type="text/javascript" src="<c:url value='/js/tis/jquery.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/tis/jszip.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/tis/kendo.all.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/tis/bootstrap.min.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/tis/common.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/tis/common.tis.kendo.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/tis/common.map.js'/>"></script>
	<script type="text/javascript" src="<c:url value='/js/tis/common.currency.js'/>"></script>

<script type="text/javascript">
function fn_GotoWrite() {
		if (document.subForm.uId.value == "") {
			alert("ID를 입력해 주세요.");
			document.subForm.uId.focus();
			return;
		}
		if (document.subForm.uNm.value == "") {
			alert("이름을 입력해 주세요.");
			document.subForm.uNm.focus();
			return;
		}
 		document.subForm.action = "<c:url value='/com/user/uInsert.do'/>";
		document.subForm.submit();
}
function fn_refresh(){
		document.subForm.action = "<c:url value='${tmpPath}/com/usert/uInsertView.do'/>";
		document.subForm.submit();
}
</script>

     <form name="subForm" method="post">
	   <h1>사용자입력</h1>
	     ID <input type="text" name="uId" size="15" maxlength="10"/><br/>
	              이름 <input type="text" name="uNm" size="15" maxlength="10"/><br/>
	          <button type="button" onclick="javascript:fn_GotoWrite();">입력</button>
     </form>
     
     