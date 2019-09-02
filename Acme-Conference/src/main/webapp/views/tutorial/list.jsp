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

<display:table name="tutorials" id="row" requestURI="${requestURI }"
	pagesize="5" class="displaytag">
	
	<!-- Display -->
	<display:column>
		<a href="tutorial/display.do?tutorialId=${row.id}"><spring:message code="tutorial.display"/></a>
	</display:column>

	<!-- Attributes -->

	<spring:message code="tutorial.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}"
		sortable="true" />
		
	<spring:message code="tutorial.startMoment" var="startMomentHeader" />
	<display:column property="startMoment" title="${startMomentHeader}"
		sortable="true" />

</display:table>
