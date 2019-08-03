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

		<b><spring:message code="conference.title" /></b>:
		<jstl:out value="${conference.title}"/><br/>
	
		<b><spring:message code="conference.acronym" /></b>:
		<jstl:out value="${conference.acronym }"/><br/>	
	
		<b><spring:message code="conference.venue" /></b>:
		<jstl:out value="${conference.venue }"/><br/>
	
		<b><spring:message code="conference.submissionDeadline" /></b>:
		<jstl:out value="${conference.submissionDeadline }"/><br/>
		
		<b><spring:message code="conference.notificationDeadline" /></b>:
		<jstl:out value="${conference.notificationDeadline }"/><br/>
		
		<b><spring:message code="conference.cameraReadyDeadline" /></b>:
		<jstl:out value="${conference.cameraReadyDeadline }"/><br/>
		
		<b><spring:message code="conference.startDate" /></b>:
		<jstl:out value="${conference.startDate }"/><br/>
		
		<b><spring:message code="conference.endDate" /></b>:
		<jstl:out value="${conference.endDate }"/><br/>
		
		<b><spring:message code="conference.summary" /></b>:
		<jstl:out value="${conference.summary }"/><br/>
		
		<b><spring:message code="conference.fee" /></b>:
		<jstl:out value="${conference.fee }"/><br/>
		
		<jstl:if test="${conference.administrator.userAccount.username == pageContext.request.userPrincipal.name}">
		<b><spring:message code="conference.isFinal" /></b>:
		<jstl:out value="${conference.isFinal }"/><br/>
		</jstl:if>
		
		<!-- Administrator -->
		<b><spring:message code="conference.administrator" /></b>:
		<a href="administrator/display.do?administratorId=${conference.administrator.id}">
			<jstl:out value="${conference.administrator.userAccount.username}"/>
		</a><br/>
