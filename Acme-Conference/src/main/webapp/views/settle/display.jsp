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
<jstl:choose>
<jstl:when test="${(settle.conference.administrator.userAccount.username == pageContext.request.userPrincipal.name)}">
		<b><spring:message code="settle.ticker" /></b>:
		<jstl:out value="${settle.ticker}"/><br/>
	
		<b><spring:message code="settle.publicationMoment" /></b>:
		<jstl:if test="${cookie['language'].getValue()=='es'}">
				<fmt:formatDate value="${settle.publicationMoment}" pattern="dd-MM-yy HH:mm"/>
		</jstl:if>
		<jstl:if test="${cookie['language'].getValue()=='en'}">
				<fmt:formatDate value="${settle.publicationMoment}" pattern="yy/MM/dd HH:mm"/>
		</jstl:if>
		<br>
		
		<b><spring:message code="settle.body" /></b>:
		<jstl:out value="${settle.body }"/><br/>
	
		<b><spring:message code="settle.picture" /></b>:
		<br><br><img width="250px" src="${settle.picture }"/>

		<br/>
	
		<!-- Admin -->
		<b><spring:message code="settle.administrator" /></b>:
		<a href="administrator/display.do?administratorId=${settle.conference.administrator.id}">
			<jstl:out value="${settle.conference.administrator.userAccount.username}"/>
		</a><br/>

		<!-- Conference -->
		<b><spring:message code="settle.conference" /></b>:
			<a href="conference/display.do?conferenceId=${settle.conference.id}"><jstl:out value="${settle.conference.title}"/></a>
		<br/>
		
<jstl:if test="${settle.conference.administrator.userAccount.username == pageContext.request.userPrincipal.name}">
<br/>
	<jstl:if test="${settle.isDraft == true}">
		<a href="settle/administrator/edit.do?settleId=${settle.id}"><spring:message code="settle.edit"/></a><br/>
		<a href="settle/administrator/delete.do?settleId=${settle.id}"><spring:message code="settle.delete"/></a><br/>
	</jstl:if>
</jstl:if>

</jstl:when>
<jstl:otherwise>
<spring:message code="settle.notYours" />
</jstl:otherwise>
</jstl:choose>

	