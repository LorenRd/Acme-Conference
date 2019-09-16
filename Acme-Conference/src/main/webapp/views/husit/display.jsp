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
<jstl:when test="${(husit.conference.administrator.userAccount.username == pageContext.request.userPrincipal.name)}">
		<b><spring:message code="husit.ticker" /></b>:
		<jstl:out value="${husit.ticker}"/><br/>
	
		<b><spring:message code="husit.publicationMoment" /></b>:
		<jstl:if test="${cookie['language'].getValue()=='es'}">
				<fmt:formatDate value="${husit.publicationMoment}" pattern="dd-MM-yy HH:mm"/>
		</jstl:if>
		<jstl:if test="${cookie['language'].getValue()=='en'}">
				<fmt:formatDate value="${husit.publicationMoment}" pattern="yy/MM/dd HH:mm"/>
		</jstl:if>
		<br>
		
		<b><spring:message code="husit.body" /></b>:
		<jstl:out value="${husit.body }"/><br/>
	
		<b><spring:message code="husit.picture" /></b>:
		<br><br><img width="250px" src="${husit.picture }"/>

		<br/>
	
		<!-- Admin -->
		<b><spring:message code="husit.administrator" /></b>:
		<a href="administrator/display.do?administratorId=${husit.conference.administrator.id}">
			<jstl:out value="${husit.conference.administrator.userAccount.username}"/>
		</a><br/>

		<!-- Conference -->
		<b><spring:message code="husit.conference" /></b>:
			<a href="conference/display.do?conferenceId=${husit.conference.id}"><jstl:out value="${husit.conference.title}"/></a>
		<br/>
		
<jstl:if test="${husit.conference.administrator.userAccount.username == pageContext.request.userPrincipal.name}">
<br/>
	<jstl:if test="${husit.isDraft == true}">
		<a href="husit/administrator/edit.do?husitId=${husit.id}"><spring:message code="husit.edit"/></a><br/>
		<a href="husit/administrator/delete.do?husitId=${husit.id}"><spring:message code="husit.delete"/></a><br/>
	</jstl:if>
</jstl:if>

</jstl:when>
<jstl:otherwise>
<spring:message code="husit.notYours" />
</jstl:otherwise>
</jstl:choose>
