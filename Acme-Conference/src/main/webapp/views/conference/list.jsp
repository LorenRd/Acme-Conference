<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<!-- Buscar rutas por palabra clave -->
<form action="${requestURI }" method="get">
	<spring:message code="conference.keyword" var="searchHeader"/>
	<spring:message code="conference.keywordSearch"/>
	<input type="text" name="keyword">
	<br>
	<spring:message code="conference.feeSearch"/>
	<input type="text" name="fee">
	<br>
	<spring:message code="conference.categorySearch"/>
	<input type="text" name="category">
	<br>
	<spring:message code="conference.dateMinSearch"/>
	<input type="text" name="minDate">
	<br>
	<spring:message code="conference.dateMaxSearch"/>
	<input type="text" name="maxDate">
	<br>
	<input type="submit" value="${searchHeader}">
	<input type="hidden" name="keywordBool" value="true">
</form>

<!-- Listing grid -->

<display:table name="conferences" id="row" requestURI="${requestURI }"
	pagesize="5" class="displaytag">
	
	<!-- Display -->
	<display:column>
		<a href="conference/display.do?conferenceId=${row.id}"><spring:message code="conference.display"/></a>
	</display:column>

	<!-- Attributes -->

	<spring:message code="conference.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}"
		sortable="true" />
		
	<spring:message code="conference.acronym" var="acronymHeader" />
	<display:column property="acronym" title="${acronymHeader}"
		sortable="true" />

</display:table>

<!-- Create conference -->
<security:authorize access="hasRole('ADMINISTRATOR')">
		<acme:button url="conference/administrator/create.do" code="conference.create"/>
	
</security:authorize> 
