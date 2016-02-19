<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=utf-8" 
pageEncoding="utf-8"%>
<!DOCTYPE htm>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link rel="stylesheet" type="text/css" media="screen" href="<%=request.getContextPath()%>/static/css/citation.css"/>
<title>Citation MVC</title>
</head>
<body>
<div id="pageBody">

<div id="doiBox">
<form class="doiForm"  method="POST" action="doi">
<div class="field"><label for="email">DOI:</label> <input type="text" name="doi"/></div>


<div class="field"><input id="submitBtn" type="submit" value="Go"/></div>
</form>
</div>
</div>
</body>
</html>
