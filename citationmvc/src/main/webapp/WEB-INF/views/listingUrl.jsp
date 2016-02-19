<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
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
<div class="field"><label for="email">DOI:</label> <input type="text" name="doi"/> <input id="submitBtn" type="submit" value="Go"/></div>
</form>
</div>

<div id="urlList">
<p class="legend">${fn:length(citations)} citation(s) found for DOI: ${doi}</p>
<c:if test="${not empty citations}">
    <ul>
    <c:forEach var="citation" items="${citations}">
        <li><a href="${citation.url}">${citation.url}</a></li>
    </c:forEach>
    </ul>
</c:if>
</div>
</div>
</body>
</html>
