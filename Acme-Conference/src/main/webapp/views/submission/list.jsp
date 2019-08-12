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

<display:table name="submissions" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">
	
	<!-- Display -->
	<display:column>
		<a href="submission/author/display.do?submissionId=${row.id}"><spring:message code="submission.display"/></a>
	</display:column>

	<!-- Attributes -->
	<spring:message code="submission.conference" var="conferenceTitleHeader" />
	<display:column property="conference.title" title="${conferenceTitleHeader}"
		sortable="true" />
		
	<spring:message code="submission.ticker" var="tickerHeader" />
	<display:column property="ticker" title="${tickerHeader}"
		sortable="true" />	
		
	<spring:message code="submission.moment" var="momentHeader" />
	<display:column property="moment" title="${momentHeader}"
		sortable="true" />
		
</display:table>

<acme:button url="submission/author/create.do" code="submission.create"/>