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
<jstl:when test="${(quolet.conference.administrator.userAccount.username == pageContext.request.userPrincipal.name)}">
		<b><spring:message code="quolet.ticker" /></b>:
		<jstl:out value="${quolet.ticker}"/><br/>
	
		<b><spring:message code="quolet.publicationMoment" /></b>:
		<jstl:if test="${cookie['language'].getValue()=='es'}">
				<fmt:formatDate value="${quolet.publicationMoment}" pattern="dd-MM-yy HH:mm"/>
		</jstl:if>
		<jstl:if test="${cookie['language'].getValue()=='en'}">
				<fmt:formatDate value="${quolet.publicationMoment}" pattern="yy/MM/dd HH:mm"/>
		</jstl:if>
		<br>
		
		<b><spring:message code="quolet.title" /></b>:
		<jstl:out value="${quolet.title }"/><br/>
		
		<b><spring:message code="quolet.body" /></b>:
		<jstl:out value="${quolet.body }"/><br/>
	
		<b><spring:message code="quolet.picture" /></b>:
		<br><br><img width="250px" src="${quolet.picture }"/>

		<br/>
	
		<!-- Admin -->
		<b><spring:message code="quolet.administrator" /></b>:
		<a href="administrator/display.do?administratorId=${quolet.conference.administrator.id}">
			<jstl:out value="${quolet.conference.administrator.userAccount.username}"/>
		</a><br/>

		<!-- Conference -->
		<b><spring:message code="quolet.conference" /></b>:
			<a href="conference/display.do?conferenceId=${quolet.conference.id}"><spring:message code="quolet.conference.display" /></a>
		<br/>
		
<jstl:if test="${quolet.conference.administrator.userAccount.username == pageContext.request.userPrincipal.name}">
<br/>
	<jstl:if test="${quolet.isDraft == true}">
		<a href="quolet/administrator/edit.do?quoletId=${quolet.id}"><spring:message code="quolet.edit"/></a><br/>
		<a href="quolet/administrator/delete.do?quoletId=${quolet.id}"><spring:message code="quolet.delete"/></a><br/>
	</jstl:if>
</jstl:if>

</jstl:when>
<jstl:otherwise>
<spring:message code="quolet.notYours" />
</jstl:otherwise>
</jstl:choose>

	