<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE HTML >
<html>

<head>
<title>Guia do Emprego</title>
<meta name="description" content="Guia do Emprego">
<meta name="keywords" content="sectec; vagas; bolsa; emprego;">
<meta http-equiv="content-type" content="text/html; charset=UTF-8"/> 
<meta name="robots" content="index, follow">
<meta name="revisit-after" content="7 days">


<sj:head jqueryui="true" locale="pt-BR" /> 
<script type="text/javascript" src="/portalemprego/js/jquery.maskedinput.js"></script>
<script type="text/javascript" src="/portalemprego/js/jquery.maskMoney.js"></script>
<script type="text/javascript" src="/portalemprego/js/functions.js"></script>

<!-- Inserir as folhas de estilo necessárias ao template -->
<link rel=stylesheet type="text/css" href="/portalemprego/css/base.css">
<link rel=stylesheet type="text/css" href="/portalemprego/css/conteudo.css">
<link rel=stylesheet type="text/css" href="/portalemprego/css/css.css">
<link rel=stylesheet type="text/css" href="/portalemprego/css/footer.css">
<link rel=stylesheet type="text/css" href="/portalemprego/css/header.css">
<link rel=stylesheet type="text/css" href="/portalemprego/css/home.css">


<style type="text/css">
.errors {
	background-color: #FFCCCC;
	border: 1px solid #CC0000;
	width: 400px;
	margin-bottom: 8px;
}

.errors li {
	list-style: none;
}

.welcome {
	background-color: #DDFFDD;
	border: 1px solid #009900;
	width: 200px;
}

.welcome li {
	list-style: none;
}
</style>


</head>

<body>
	<div id="all">

		<div id="cabecalho"></div>

		<div id="content">
		
		   <div style="margin: 10px 0 0 10px;">
		        	<s:if test="hasActionMessages()">
						<div class="welcome" style="width:99%;font-weight:bold;border-radius: 3px;padding-top: 5px;padding-bottom: 5px; ">
							<s:actionmessage cssStyle="font-size: 11px;margin-left: 10px;font-family: inherit;font-weight: bold;" />
						</div>
					</s:if>
 

					<s:if test="hasActionErrors()">
						<div class="errors" style="width:99%;font-weight:bold; border-radius: 3px; padding-top: 5px;padding-bottom: 5px;" >
							<s:actionerror cssStyle="font-size: 11px;margin-left: 10px;font-family: inherit;font-weight: bold;"  />
						</div>
					</s:if>
		   </div>
				
				<tiles:insertAttribute name="formulario" />

		</div>
	</div>
</body>
</html>