<%@ page  contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<html>
<head>
<title></title>
</head>
<body style="font-size:30px;">
	<form action="login.do" method="post">
		<fieldset>
			<legend>登录</legend>
			用户名:<input name="username"/>
			<span style="color:red;">${login_failed}</span>
			<br/>
			密码:<input type="password" name="pwd"/><br/>
			<input type="submit" value="确定"/>
		</fieldset>
	</form>
</body>
</html>

