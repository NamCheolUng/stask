 <%@ page contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<script type="text/javascript" src="<c:url value='/js/tis/jquery.min.js'/>"></script>
 

<script type="text/javascript">
function fn_select_user(uSeq){
	document.userManageVO.uSeq.value = uSeq;
	document.userManageVO.action = "<c:url value='/com/user/uUpdate.do'/>";
	document.userManageVO.submit();
}

function fn_delete_user(uSeq){
	document.userManageVO.uSeq.value = uSeq;
	document.userManageVO.action = "<c:url value='/com/user/uDelete.do'/>";
	document.userManageVO.submit();
}
<c:choose>
<c:when test ="${userVO.uId == 'john'}">
  alert('john');
</c:when> 
</c:choose> 

/* <c:if test="${userVO.uId == 'john'}">
	alert('john');
</c:if>
 */

</script>

   <h1>사용자정보</h1>
	<form name="userManageVO" method="post">
	 <input type="hidden" name="uSeq" />
		ID <input type="text" name="uId" size="15" maxlength="20" value="<c:out value='${userVO.uId}'/>"/><br/>
	           이름 <input type="text" name="uNm" size="15" maxlength="10" value="<c:out value='${userVO.uNm}'/>"/><br/>
	      <a href="#LINK" onclick="javascript:fn_select_user('<c:out value='${userVO.uSeq}'/>')"><button type="button">수정</button></a>  
	      <a href="#LINK" onclick="javascript:fn_delete_user('<c:out value='${userVO.uSeq}'/>')"><button type="button">삭제용</button></a>   
	    <%--   <a href="<c:url value = '/com/user/uDelete.do'/>?uSeq=<c:out value = '${userVO.uSeq }'/>"><button type = "button">삭제</button></a> --%>
	         
	</form>


	

