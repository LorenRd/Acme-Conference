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
	<img src="${author.photo}" class="ui mini rounded image" >
</div>

<table class="ui celled table">
	<tbody>
		<tr>
			<td><spring:message code="author.name" />
			<td data-label="name"><jstl:out value="${author.name}" /></td>
		</tr>
		<tr>
			<td><spring:message code="author.middleName" />
			<td data-label="middleName"><jstl:out value="${author.middleName}" /></td>
		</tr>
		<tr>
			<td><spring:message code="author.surname" />
			<td data-label="surname"><jstl:out value="${author.surname}" /></td>
		</tr>	
		<tr>
			<td><spring:message code="author.email" />
			<td data-label="email"><jstl:out value="${author.email}" /></td>
		</tr>
		<tr>
			<td><spring:message code="author.phone" />
			<td data-label="phone"><jstl:out value="${author.phone}" /></td>
		</tr>
		<tr>
			<td><spring:message code="author.address" />
			<td data-label="address"><jstl:out value="${author.address}" /></td>
		</tr>
		
		<jstl:if test="${author.score != null }">
		<tr>
			<td><spring:message code="author.score" />
			<td data-label="score"><jstl:out value="${author.score}" /></td>
		</tr>
		</jstl:if>	
		
	</tbody>
</table>

<jstl:if test="${author.userAccount.username == pageContext.request.userPrincipal.name}">
	<security:authorize access="hasRole('AUTHOR')">
<br/>
<br/>
<input type="button" name="save" class="ui button"
	value="<spring:message code="author.edit" />"
	onclick="javascript: relativeRedir('author/edit.do');" />
	
</security:authorize>
</jstl:if>
<br/>
<br/>