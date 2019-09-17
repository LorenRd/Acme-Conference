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

<display:table name="kolems" id="row" requestURI="${requestURI }"
	pagesize="5" class="displaytag">
	
	<!-- Display -->
	<display:column>
		<a href="kolem/administrator/display.do?kolemId=${row.id}"><spring:message code="kolem.display"/></a>
	</display:column>

	<!-- Attributes -->
	
	<jstl:choose>
		<jstl:when test="${kolem.publicationMoment >= dateOneMonth}">
			<jstl:set var="background" value="DarkOrange" />
		</jstl:when>
	
		<jstl:when test="${(kolem.publicationMoment < dateOneMonth) && (kolem.publicationMoment > dateTwoMonths)}">
			<jstl:set var="background" value="DarkMagenta" />
		</jstl:when>
		
		<jstl:otherwise>
			<jstl:set var="background" value="Thistle" />
		</jstl:otherwise>
	</jstl:choose>

	<spring:message code="kolem.ticker" var="tickerHeader" />
	<display:column property="ticker" title="${tickerHeader}"
		sortable="true" />
		
	<jstl:if test="${cookie['language'].getValue()=='es'}">
	<spring:message code="kolem.publicationMoment" var="publicationMomentHeader" />
    <display:column class="${background}" property="publicationMoment" format="{0,date, dd-MM-yy HH:mm}" title="${publicationMomentHeader}" />
	</jstl:if>
	<jstl:if test="${cookie['language'].getValue()=='en'}">
	<spring:message code="kolem.publicationMoment" var="publicationMomentHeader" />
    <display:column class="${background}" property="publicationMoment" format="{0,date, yy/MM/dd HH:mm}" title="${publicationMomentHeader}" />
	</jstl:if>
		
		
</display:table>
<br />