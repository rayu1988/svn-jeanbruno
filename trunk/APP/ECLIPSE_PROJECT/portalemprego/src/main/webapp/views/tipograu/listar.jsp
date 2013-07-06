<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="/struts-tags" prefix="s" %>    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
	<h3>Lista de Tipos de Grau</h3>
	<table>
		<tr>
			<td>Id</td>
			<td>Nome</td>
		</tr>
		<c:forEach items="${graus}" var="grau">
		<tr>
			<td>${grau.idTipoGrau}</td>
			<td>${grau.dsTipoGrau}</td>
		</tr>
		</c:forEach>



<s:form  namespace="/tipograu" action="salvar" >

		<s:select name="tipoGrau.idTipoGrau" list="graus" listKey="idTipoGrau" listValue="dsTipoGrau" headerKey="0" headerValue="Selecione..." label="Teste:" />

       <s:submit value="salvar"   />
</s:form>

		</table>
	<br>
	<h5><a href="novo">Novo</a></h5>
</body>
</html>