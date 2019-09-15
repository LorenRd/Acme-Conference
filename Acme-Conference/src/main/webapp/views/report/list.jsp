<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>
<!-- Listing grid -->

<display:table name="reports" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">
	
	<!-- Display -->
	<display:column>
		<a href="report/reviewer/display.do?reportId=${row.id}"><spring:message code="report.display"/></a>
	</display:column>

	<!-- Attributes -->
	<spring:message code="report.originalityScore" var="originalityScoreHeader" />
	<display:column property="originalityScore" title="${originalityScoreHeader}"
		sortable="true" />
		
	<spring:message code="report.qualityScore" var="qualityScoreHeader" />
	<display:column property="qualityScore" title="${qualityScoreHeader}"
		sortable="true" />	
		
	<spring:message code="report.readabilityScore" var="readabilityScoreHeader" />
	<display:column property="readabilityScore" title="${readabilityScoreHeader}"
		sortable="true" />
		
	<spring:message code="report.decision" var="decisionHeader" />
	<display:column property="decision" title="${decisionHeader}"
		sortable="true" />
		
</display:table>

<security:authorize access="hasRole('REVIEWER')">
	<acme:button url="report/reviewer/create.do" code="report.create"/>
</security:authorize> 

