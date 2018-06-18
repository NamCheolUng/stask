<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<link rel='stylesheet prefetch' href='http://netdna.bootstrapcdn.com/bootstrap/3.0.2/css/bootstrap.min.css'>
<script type="text/javascript" src="<c:url value='/js/tis/jquery.min.js'/>"></script>
<style>
body {
  background: #eee !important;
}

.wrapper {
  margin-top: 80px;
  margin-bottom: 80px;
}

.loginForm {
  max-width: 380px;
  padding: 15px 35px 45px;
  margin: 0 auto;
  background-color: #fff;
  border: 1px solid rgba(0, 0, 0, 0.1);
}
.loginForm .loginForm-heading{
  margin-bottom: 30px;
}
.loginForm .form-control {
  position: relative;
  font-size: 16px;
  height: auto;
  padding: 10px;
  -webkit-box-sizing: border-box;
  -moz-box-sizing: border-box;
  box-sizing: border-box;
}
.loginForm .form-control:focus {
  z-index: 2;
}
.loginForm input[type="text"] {
  margin-bottom: -1px;
  border-bottom-left-radius: 0;
  border-bottom-right-radius: 0;
}
.loginForm input[type="password"] {
  margin-bottom: 20px;
  border-top-left-radius: 0;
  border-top-right-radius: 0;
}
</style>
<script>

$(document).ready(function(){
	fnInit();
});
function fnInit(){
	if ("${msg}" ==1){
	    alert("로그인이 필요합니다.");
}
}
function actionLogin(){
    if (document.loginForm.uId2.value == ""){
        alert("아이디를 입력하세요");
        document.loginForm.uId2.focus();
        return;
}
        document.loginForm.uId.value = document.loginForm.uId2.value;
        document.loginForm.action="<c:url value='/com/login/uLogin.do'/>";
        document.loginForm.submit();
}
</script>
    <form name="loginForm" class="loginForm" onsubmit="actionLogin(); return false;" method="post">
    	<input name="method" type="hidden" value="login">
		<input name="refer" type="hidden" value="">
		<input name="userSe" type="hidden" value="GNR">
		<input name="j_username" type="hidden"> 
		     <input type="hidden" name="uId">
		  ID <input type="text" name="uId2" maxlength="10"/>
             <button onclick="javascript:actionLogin();">로그인</button>     
    </form>