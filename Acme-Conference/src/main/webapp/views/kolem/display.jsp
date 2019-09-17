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
<jstl:when test="${(kolem.conference.administrator.userAccount.username == pageContext.request.userPrincipal.name)}">
		<b><spring:message code="kolem.ticker" /></b>:
		<jstl:out value="${kolem.ticker}"/><br/>
	
		<b><spring:message code="kolem.publicationMoment" /></b>:
		<jstl:if test="${cookie['language'].getValue()=='es'}">
				<fmt:formatDate value="${kolem.publicationMoment}" pattern="dd-MM-yy HH:mm"/>
		</jstl:if>
		<jstl:if test="${cookie['language'].getValue()=='en'}">
				<fmt:formatDate value="${kolem.publicationMoment}" pattern="yy/MM/dd HH:mm"/>
		</jstl:if>
		<br>
		
		<b><spring:message code="kolem.body" /></b>:
		<jstl:out value="${kolem.body }"/><br/>
	
		<b><spring:message code="kolem.picture" /></b>:
		<br><br><img width="250px" src="${kolem.picture }"/>

		<br/>
	
		<!-- Admin -->
		<b><spring:message code="kolem.administrator" /></b>:
		<a href="administrator/display.do?administratorId=${kolem.conference.administrator.id}">
			<jstl:out value="${kolem.conference.administrator.userAccount.username}"/>
		</a><br/>

		<!-- Conference -->
		<b><spring:message code="kolem.conference" /></b>:
			<a href="conference/display.do?conferenceId=${kolem.conference.id}"><jstl:out value="${kolem.conference.title}"/></a>
		<br/>
		
<jstl:if test="${kolem.conference.administrator.userAccount.username == pageContext.request.userPrincipal.name}">
<br/>
	<jstl:if test="${kolem.isDraft == true}">
		<a href="kolem/administrator/edit.do?kolemId=${kolem.id}"><spring:message code="kolem.edit"/></a><br/>
		<a href="kolem/administrator/delete.do?kolemId=${kolem.id}"><spring:message code="kolem.delete"/></a><br/>
	</jstl:if>
</jstl:if>

</jstl:when>
<jstl:otherwise>
<spring:message code="kolem.notYours" />
</jstl:otherwise>
</jstl:choose>

	