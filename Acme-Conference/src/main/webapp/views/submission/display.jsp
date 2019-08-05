<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

		<b><spring:message code="submission.ticker" /></b>:
		<jstl:out value="${submission.ticker}"/><br/>

		<b><spring:message code="submission.moment" /></b>:
		<jstl:out value="${submission.moment}"/><br/>
		
		<b><spring:message code="submission.status" /></b>:
		<jstl:out value="${submission.status}"/><br/>

<security:authorize access="hasRole('AUTHOR')">
<jstl:if test="${submission.author.userAccount.username == pageContext.request.userPrincipal.name}">
<br/>
	<a href="submission/author/edit.do?submissionId=${submission.id}"><spring:message code="submission.edit"/></a><br/>
	<a href="submission/author/delete.do?submissionId=${submission.id}"><spring:message code="submission.delete"/></a><br/>
</jstl:if>
</security:authorize>