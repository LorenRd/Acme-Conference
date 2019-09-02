<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<security:authorize access="hasRole('ADMIN')">

	<form action="conference/administrator/list.do" method="get">

		<input type="radio" name="conferenceStatus" value="0" checked>
		<spring:message code="conference.status.all" />
		<input type="radio" name="conferenceStatus" value="1">
		<spring:message code="conference.status.submission5" />
		<input type="radio" name="conferenceStatus" value="2">
		<spring:message code="conference.status.notification5" />
		<input type="radio" name="conferenceStatus" value="3">
		<spring:message code="conference.status.camera5" />
		<input type="radio" name="conferenceStatus" value="4">
		<spring:message code="conference.status.organised5" />
		<br />
		<spring:message code="conference.status.choose" var="choose" />
		<input type="submit" value="${choose}">
	</form>

</security:authorize>
<br/><br/>

<!-- Buscar rutas por palabra clave -->
<form action="${requestURI }" method="get">
	<spring:message code="conference.keyword" var="searchHeader"/>
	<input type="text" name="keyword">
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
<security:authorize access="hasRole('ADMIN')">
		<acme:button url="conference/administrator/create.do" code="conference.create"/>
	
</security:authorize> 
