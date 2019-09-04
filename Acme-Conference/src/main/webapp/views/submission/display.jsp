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

		<h3><spring:message code="submission.paper" /></h3>

		<b><spring:message code="submission.paper.title" /></b>:
		<jstl:out value="${submission.paper.title}"/><br/>
		
		<b><spring:message code="submission.paper.author" /></b>:
		<jstl:out value="${submission.paper.author}"/><br/>
		
		<b><spring:message code="submission.paper.summary" /></b>:
		<jstl:out value="${submission.paper.summary}"/><br/>
		
		<b><spring:message code="submission.paper.document" /></b>:
		<jstl:out value="${submission.paper.document}"/><br/>
		
		<!-- Reviewers -->
		
		<b><spring:message code="submission.reviewers" /></b>:
		<br/><ul>
		<jstl:forEach items="${submission.reviewers}" var="reviewer" >
			<jstl:if test="${reviewer != null}">
	        	<li><jstl:out value="${reviewer.name}"/></li>
	        </jstl:if>
		</jstl:forEach></ul>
		<!-- Reports -->
		<jstl:if test="${submission.decisionNotification}">
		<b><spring:message code="submission.reports" /></b>:
		<br/><ul>
		<jstl:forEach items="${reports}" var="report" >
			<jstl:if test="${report != null}">
	        	<li><a href="report/author/display.do?reportId=${report.id}"><jstl:out value="${report.decision}"/></a></li>
	        </jstl:if>
		</jstl:forEach></ul>
		</jstl:if>

<!--
<security:authorize access="hasRole('AUTHOR')">
<jstl:if test="${submission.author.userAccount.username == pageContext.request.userPrincipal.name}">
<br/>
	<a href="submission/author/edit.do?submissionId=${submission.id}"><spring:message code="submission.edit"/></a><br/>
</jstl:if>
</security:authorize>
-->

<!-- Para asignar revisores -->
<security:authorize access="hasRole('ADMIN')">
<jstl:if test="${submission.status == 'UNDER-REVIEW'}">
		<a href="submission/administrator/edit.do?submissionId=${submission.id}"><spring:message code="administrator.assignReviewers"/></a><br/>
</jstl:if>
</security:authorize>