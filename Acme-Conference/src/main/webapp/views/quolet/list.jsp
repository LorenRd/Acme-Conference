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

<display:table name="quolets" id="row" requestURI="${requestURI }"
	pagesize="5" class="displaytag">
	
	<!-- Display -->
	<display:column>
		<a href="quolet/administrator/display.do?quoletId=${row.id}"><spring:message code="quolet.display"/></a>
	</display:column>

	<!-- Attributes -->
	
	<jstl:choose>
		<jstl:when test="${quolet.publicationMoment >= dateOneMonth}">
			<jstl:set var="background" value="MediumSlateBlue" />
		</jstl:when>
	
		<jstl:when test="${(quolet.publicationMoment < dateOneMonth) && (quolet.publicationMoment > dateTwoMonths)}">
			<jstl:set var="background" value="DarkGoldenRod" />
		</jstl:when>
		
		<jstl:otherwise>
			<jstl:set var="background" value="SandyBrown" />
		</jstl:otherwise>
	</jstl:choose>

	<spring:message code="quolet.ticker" var="tickerHeader" />
	<display:column property="ticker" title="${tickerHeader}"
		sortable="true" />
		
	<jstl:if test="${cookie['language'].getValue()=='es'}">
	<spring:message code="quolet.publicationMoment" var="publicationMomentHeader" />
    <display:column class="${background}" property="publicationMoment" format="{0,date, dd-MM-yy HH:mm}" title="${publicationMomentHeader}" />
	</jstl:if>
	<jstl:if test="${cookie['language'].getValue()=='en'}">
	<spring:message code="quolet.publicationMoment" var="publicationMomentHeader" />
    <display:column class="${background}" property="publicationMoment" format="{0,date, yy/MM/dd HH:mm}" title="${publicationMomentHeader}" />
	</jstl:if>
		
	<spring:message code="quolet.title" var="titleHeader" />
	<display:column property="title" title="${titleHeader}"
		sortable="true" />
		
</display:table>
<br />