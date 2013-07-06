<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="/struts-tags" prefix="s"%>
<%@ taglib prefix="sj" uri="/struts-jquery-tags"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
		<title>Insert title here</title>
		<sj:head jqueryui="true" locale="pt-BR" /> 
		<script type="text/javascript" src="/portalemprego/js/jquery.maskedinput.js"></script>
		<script type="text/javascript" src="/portalemprego/js/jquery.maskMoney.js"></script>
		<script type="text/javascript" src="/portalemprego/js/functions.js"></script>
		
		<script type="text/javascript">
			alert('before initializing bind form');
			$(document).ready(function(){
				alert('binding the form');
				 $("#introForm").submit(function(){
				  alert('invoking the action from submit form');
					 
				  var formInput=$(this).serialize();
				  var nameVal = $('#idName').val();
				  alert('got the nameVal value: ' + nameVal);
				  
				   $.ajax({
						url: 'testjean/sayHi.action',
						type: 'POST',
						data: { 'name':nameVal },
						dataType: 'json',
						success: function(data) {
						   alert('the data returned from server side: ' + data);
							  
						   $('#result').html('' + data.greeting + '');
						   $.each(data.countryList,function(index, value){
						    console.log("value " + value);
						   });
						   $.each(data.countryMap,function(key, value){
						    console.log("key "+ key + ", value " + value);
						   });
						  },
						error: function(xhr, ajaxOptions, thrownError){
							alert('An error occurred! ' + thrownError);
						}
					});
				  
				  
// 				  $.getJSON('testjean/sayHi.action', formInput,function(data) {
// 				   alert('the data returned from server side: ' + data);
// 				   $('#result').html('' + data.greeting + '');
// 				   $.each(data.countryList,function(index, value){
// 				    console.log("value " + value);
// 				   });
// 				   $.each(data.countryMap,function(key, value){
// 				    console.log("key "+ key + ", value " + value);
// 				   });
// 				  });
				  
				  
				  return false;
				 });
				});
			alert('after initializing bind form');
		</script>
	</head>
	<body>
		<form action="" id="introForm">
			<label for="name">Enter Your Name</label>
			<input name="name" id="idName">
			<input type="submit">
		</form>
		<div id="result">
		</div>
	</body>
</html>