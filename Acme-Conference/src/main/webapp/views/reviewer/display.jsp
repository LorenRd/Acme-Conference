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

<div class="content">
	<img src="${reviewer.photo}" class="ui mini rounded image" >
</div>

<table class="ui celled table">
	<tbody>
		<tr>
			<td><spring:message code="reviewer.name" />
			<td data-label="name"><jstl:out value="${reviewer.name}" /></td>
		</tr>
		<tr>
			<td><spring:message code="reviewer.middleName" />
			<td data-label="middleName"><jstl:out value="${reviewer.middleName}" /></td>
		</tr>
		<tr>
			<td><spring:message code="reviewer.surname" />
			<td data-label="surname"><jstl:out value="${reviewer.surname}" /></td>
		</tr>	
		<tr>
			<td><spring:message code="reviewer.email" />
			<td data-label="email"><jstl:out value="${reviewer.email}" /></td>
		</tr>
		<tr>
			<td><spring:message code="reviewer.phone" />
			<td data-label="phone"><jstl:out value="${reviewer.phone}" /></td>
		</tr>
		<tr>
			<td><spring:message code="reviewer.address" />
			<td data-label="address"><jstl:out value="${reviewer.address}" /></td>
		</tr>
		
		<tr>
		<spring:message code="reviewer.expertises" />
		<jstl:forEach items="${reviewer.expertises}" var="expertise" >
			<jstl:if test="${expertise != null}">
	        	<jstl:out value="${expertise}"/>
	        </jstl:if>
		</jstl:forEach>
		</tr>
		
	</tbody>
</table>

<jstl:if test="${reviewer.userAccount.username == pageContext.request.userPrincipal.name}">
	<security:authorize access="hasRole('REVIEWER')">
<br/>
<br/>
<input type="button" name="save" class="ui button"
	value="<spring:message code="reviewer.edit" />"
	onclick="javascript: relativeRedir('reviewer/edit.do');" />
	
</security:authorize>
</jstl:if>
<br/>
<br/>