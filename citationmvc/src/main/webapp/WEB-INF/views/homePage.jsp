<%@ page trimDirectiveWhitespaces="true" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
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

<div id="doiList">
<c:if test="${not empty citationCounts}">
    <p class="legend"> DOIs with most citations:</p>
    <table>
        <tr>
        <th>DOI</th>   <th>Citation Count</th>
        </tr>
    <c:forEach var="citationCount" items="${citationCounts}">
        <tr>
        <td class="label">${citationCount.doi}</td>   <td class="value">${citationCount.count}</td>
        </tr>
    </c:forEach>
    </table>
</c:if>
</div>
</div>
</body>
</html>
