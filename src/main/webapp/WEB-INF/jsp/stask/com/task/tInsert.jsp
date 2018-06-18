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
	
	<script type="text/javascript" src="<c:url value='/js/tis/jquery-ui.min.js'/>"></script>
	

	
	
    
	
	<link rel="stylesheet" type="text/css" href="<c:url value='/css/stask/jquery-ui.css'/>">

      
	 

<script type="text/javascript">
function fn_GotoWrite(){
	  var msg = $('.message');
		if ($('#tNm').val() == "") {
			alert("과제명을 입력해 주세요.");
		    msg.text("과제명을 입력해 주세요.");
			$('#tNm').focus();
			return;
		}
		if ($('#tSdate').val() == "") {
			alert("시작기간을 입력해 주세요.");
			msg.text("시작기간을 입력해 주세요.");
			$('#tSdate').focus();
			return;
		}
		if ($('#tEdate').val() == "") {
			alert("종료기간을 입력해 주세요.");
			msg.text("종료기간을 입력해 주세요.");
			$('#tEdate').focus();
			return;
		}
 		document.subForm.action = "<c:url value='/com/task/tInsert.do'/>";
		document.subForm.submit();
		
}
function fn_refresh(){
		document.subForm.action = "<c:url value='${tmpPath}/com/task/tInsertView.do'/>";
		document.subForm.submit();
}

$(function(){
 $(".date").datepicker({
	 dateFormat : "yy/mm/dd",
	 nextText : "다음 달",
	 prevText: "이전 달",
	 changeMonth: true,
	 changeYear: true, 
	 showButtonPanel: true,
	 currentText: '오늘 날짜', 
     closeText: '닫기',
     dayNamesMin: ["일", "월", "화", "수", "목", "금", "토"],
     dayNames: ['일요일', '월요일', '화요일', '수요일', '목요일', '금요일', '토요일'],
     monthNamesShort: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월'],
     showOn: "both",
     buttonImage: "<c:url value='/images/stask/date.gif'/>",
     buttonImageOnly: true 
 });
});


</script>

  <form name="subForm" method="post">
	<h1>과제입력</h1>
	    과제명 <input type="text" name="tNm" id="tNm" size="15" maxlength="10"/><br/>
	       기간 <input  name="tSdate" id="tSdate" size="15" class="date" maxlength="10"/> ~ <input type="text" name="tEdate" id="tEdate" size="15"  class="date" maxlength="10"/><br/>
	       <button type="button" onclick="javascript:fn_GotoWrite();">입력</button><br>
	       <div class="message">유효성체크 결과</div>
  </form>

