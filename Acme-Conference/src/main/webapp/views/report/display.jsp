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

		<b><spring:message code="report.originalityScore" /></b>:
		<jstl:out value="${report.originalityScore}"/><br/>

		<b><spring:message code="report.qualityScore" /></b>:
		<jstl:out value="${report.qualityScore}"/><br/>
		
		<b><spring:message code="report.readabilityScore" /></b>:
		<jstl:out value="${report.readabilityScore}"/><br/>

		<b><spring:message code="report.decision" /></b>:
		<jstl:out value="${report.decision}"/><br/>	
		
		<!-- Reviewers -->
		
		<b><spring:message code="report.comments" /></b>:
		<br/><ul>
		<jstl:forEach items="${report.comments}" var="comment" >
			<jstl:if test="${comment != null}">
	        	<li><jstl:out value="${comment}"/></li>
	        </jstl:if>
		</jstl:forEach></ul>
