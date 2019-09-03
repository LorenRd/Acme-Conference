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
	<img src="${sponsor.photo}" class="ui mini rounded image" >
</div>

<table class="ui celled table">
	<tbody>
		<tr>
			<td><spring:message code="sponsor.name" />
			<td data-label="name"><jstl:out value="${sponsor.name}" /></td>
		</tr>
		<tr>
			<td><spring:message code="sponsor.middleName" />
			<td data-label="middleName"><jstl:out value="${sponsor.middleName}" /></td>
		</tr>
		<tr>
			<td><spring:message code="sponsor.surname" />
			<td data-label="surname"><jstl:out value="${sponsor.surname}" /></td>
		</tr>	
		<tr>
			<td><spring:message code="sponsor.email" />
			<td data-label="email"><jstl:out value="${sponsor.email}" /></td>
		</tr>
		<tr>
			<td><spring:message code="sponsor.phone" />
			<td data-label="phone"><jstl:out value="${sponsor.phone}" /></td>
		</tr>
		<tr>
			<td><spring:message code="sponsor.address" />
			<td data-label="address"><jstl:out value="${sponsor.address}" /></td>
		</tr>
		
	</tbody>
</table>

<jstl:if test="${sponsor.userAccount.username == pageContext.request.userPrincipal.name}">
	<security:authorize access="hasRole('SPONSOR')">
<br/>
<br/>
<input type="button" name="save" class="ui button"
	value="<spring:message code="sponsor.edit" />"
	onclick="javascript: relativeRedir('sponsor/edit.do');" />
	
</security:authorize>
</jstl:if>
<br/>
<br/>