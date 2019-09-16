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

<display:table name="husits" id="row" requestURI="${requestURI }"
	pagesize="5" class="displaytag">
	
	<!-- Display -->
	<display:column>
		<a href="husit/administrator/display.do?husitId=${row.id}"><spring:message code="husit.display"/></a>
	</display:column>

	<!-- Attributes -->
	
	<jstl:choose>
		<jstl:when test="${husit.publicationMoment >= dateOneMonth}">
			<jstl:set var="background" value="ForestGreen" />
		</jstl:when>
	
		<jstl:when test="${(husit.publicationMoment < dateOneMonth) && (husit.publicationMoment > dateTwoMonths)}">
			<jstl:set var="background" value="GreenYellow" />
		</jstl:when>
		
		<jstl:otherwise>
			<jstl:set var="background" value="FloralWhite" />
		</jstl:otherwise>
	</jstl:choose>

	<spring:message code="husit.ticker" var="tickerHeader" />
	<display:column property="ticker" title="${tickerHeader}"
		sortable="true" />
		
	<jstl:if test="${cookie['language'].getValue()=='es'}">
	<spring:message code="husit.publicationMoment" var="publicationMomentHeader" />
    <display:column class="${background}" property="publicationMoment" format="{0,date, dd-MM-yy HH:mm}" title="${publicationMomentHeader}" />
	</jstl:if>
	<jstl:if test="${cookie['language'].getValue()=='en'}">
	<spring:message code="husit.publicationMoment" var="publicationMomentHeader" />
    <display:column class="${background}" property="publicationMoment" format="{0,date, yy/MM/dd HH:mm}" title="${publicationMomentHeader}" />
	</jstl:if>
		
</display:table>
<br />