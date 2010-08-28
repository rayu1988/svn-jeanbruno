<html>
<header>
	<title>ICPBravo - Sample</title>
	<META HTTP-EQUIV="Content-Type" CONTENT="text/html;charset=ISO-8859-1">
	<link href="icpbravo.css" rel="stylesheet" type="text/css" />
</header>
<body>
<table width='100%'><tr>
<td align='left'><a href="/ICPBravoJSP/index.jsp"><img src='/ICPBravoJSP/img/LogoGrande.png' border='0'></a></td>
<td align='right'><h1><a href='http://www.oaks.com.br'>OAK Soluções Empresariais</a></h1></td>
</tr></table>
<%
session.setAttribute("logged", null);
%>
<form action='/ICPBravoJSP/index.jsp' method='get'>
<table>
<tr><td align='right'>Login:</td><td><input type='text' name='usr'></td></tr>
<tr><td align='right'>Senha:</td><td><input type='password' name='pw'></td></tr>
<tr><td colspan='2' align='right'><input type='submit' value= 'Login'></td></tr>
</table>
</form>
</body>
</html>