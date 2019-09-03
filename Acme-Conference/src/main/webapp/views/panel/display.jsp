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

		<b><spring:message code="activity.title" /></b>:
		<jstl:out value="${panel.activityTitle}"/><br/>

		<b><spring:message code="activity.speakers" /></b>:
		<br/><ul>
		<jstl:forEach items="${panel.speakers}" var="speaker" >
			<jstl:if test="${speaker != null}">
	        	<li><jstl:out value="${speaker}"/></li>
	        </jstl:if>
		</jstl:forEach></ul>
				
		<b><spring:message code="activity.schedule" /></b>:
		<jstl:out value="${panel.schedule}"/><br/>

		<b><spring:message code="activity.startDate" /></b>:
		<jstl:out value="${panel.startMoment}"/><br/>	

		<b><spring:message code="activity.duration" /></b>:
		<jstl:out value="${panel.duration}"/><br/>	
				
		<b><spring:message code="activity.room" /></b>:
		<jstl:out value="${panel.room}"/><br/>	

		<b><spring:message code="activity.summary" /></b>:
		<jstl:out value="${panel.activitySummary}"/><br/>	
		
		<b><spring:message code="activity.attachments" /></b>:
		<br/><ul>
		<jstl:forEach items="${panel.attachments}" var="attachment" >
			<jstl:if test="${attachment != null}">
	        	<li><jstl:out value="${attachment}"/></li>
	        </jstl:if>
		</jstl:forEach></ul>
		
		