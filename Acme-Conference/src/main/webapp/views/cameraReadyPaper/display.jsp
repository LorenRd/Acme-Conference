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

		<b><spring:message code="cameraReadyPaper.title" /></b>:
		<jstl:out value="${cameraReadyPaper.title}"/><br/>
		
		<b><spring:message code="cameraReadyPaper.author" /></b>:
		<jstl:out value="${cameraReadyPaper.author}"/><br/>
		
		<b><spring:message code="cameraReadyPaper.summary" /></b>:
		<jstl:out value="${cameraReadyPaper.summary}"/><br/>
		
		<b><spring:message code="cameraReadyPaper.document" /></b>:
		<jstl:out value="${cameraReadyPaper.document}"/><br/>

		<h3><spring:message code="cameraReadyPaper.submission" /></h3>
		
		<b><spring:message code="cameraReadyPaper.submission.ticker" /></b>:
		<jstl:out value="${cameraReadyPaper.submission.ticker}"/><br/>

		<b><spring:message code="cameraReadyPaper.submission.moment" /></b>:
		<jstl:out value="${cameraReadyPaper.submission.moment}"/><br/>
		
		<b><spring:message code="cameraReadyPaper.submission.status" /></b>:
		<jstl:out value="${cameraReadyPaper.submission.status}"/><br/>

<security:authorize access="hasRole('AUTHOR')">
<jstl:if test="${cameraReadyPaper.submission.author.userAccount.username == pageContext.request.userPrincipal.name}">
<br/>
	<a href="cameraReadyPaper/author/edit.do?cameraReadyPaperId=${cameraReadyPaper.id}"><spring:message code="cameraReadyPaper.edit"/></a><br/>
	<a href="cameraReadyPaper/author/delete.do?cameraReadyPaperId=${cameraReadyPaper.id}"><spring:message code="cameraReadyPaper.delete"/></a><br/>
</jstl:if>
</security:authorize>