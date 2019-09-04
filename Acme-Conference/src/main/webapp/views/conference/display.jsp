<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<img src="${banner}" width="500px" height="200px">
<p>
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
		
		<jstl:if test="${cookie['language'].getValue()=='en'}">
		<b><spring:message code="conference.category" /></b>:
		<jstl:out value="${conference.category.englishName}"> </jstl:out>
		</jstl:if>
		<jstl:if test="${cookie['language'].getValue()=='es'}">
		<b><spring:message code="conference.category" /></b>:
		<jstl:out value="${conference.category.spanishName}"> </jstl:out>
		</jstl:if>
		
		<br/>
		<jstl:if test="${conference.administrator.userAccount.username == pageContext.request.userPrincipal.name}">
			<b><a href="activity/administrator/list.do?conferenceId=${conference.id}"><spring:message code="conference.activity" /></a></b>
		</jstl:if>
		<jstl:if test="${conference.administrator.userAccount.username != pageContext.request.userPrincipal.name}">
			<b><a href="activity/list.do?conferenceId=${conference.id}"><spring:message code="conference.activity" /></a></b>
		</jstl:if>
		<br/><br/>
<!-- Administrator -->
<b><spring:message code="conference.administrator" /></b>:
<a href="administrator/display.do?administratorId=${conference.administrator.id}">
	<jstl:out value="${conference.administrator.userAccount.username}"/>
</a><br/>
<security:authorize access="hasRole('ADMIN')">
<jstl:if test="${conference.administrator.userAccount.username == pageContext.request.userPrincipal.name}">
<br/>
	<jstl:if test="${!conference.isFinal}">			
		<a href="conference/administrator/edit.do?conferenceId=${conference.id}"><spring:message code="conference.edit"/></a><br/>
	</jstl:if>
</jstl:if>
<jstl:if test="${conference.isFinal}">			
	<jstl:if test="${submissionDeadlineOver}">
			<input type="button" name="analyseSubmissions" value="<spring:message code="conference.analyseSubmissions" />" onclick="redirect: location.href = 'conference/administrator/analyseSubmissions.do?conferenceId=${conference.id}';" />	
	</jstl:if>
	<br />
	<input type="button" name="decisionNotification" value="<spring:message code="conference.decisionNotification" />" onclick="redirect: location.href = 'conference/administrator/decisionNotification.do?conferenceId=${conference.id}';" />	
</jstl:if>
</security:authorize>
		