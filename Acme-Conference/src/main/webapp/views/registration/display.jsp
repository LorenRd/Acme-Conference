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

		<h3><spring:message code="registration.creditCard" /></h3>

		<b><spring:message code="registration.creditCard.holderName" /></b>:
		<jstl:out value="${registration.creditCard.holderName}"/><br/>
		
		<b><spring:message code="registration.creditCard.brandName" /></b>:
		<jstl:out value="${registration.creditCard.brandName}"/><br/>
		
		<b><spring:message code="registration.creditCard.number" /></b>:
		<jstl:out value="${registration.creditCard.number}"/><br/>
		
		<b><spring:message code="registration.creditCard.expirationMonth" /></b>:
		<jstl:out value="${registration.creditCard.expirationMonth}"/><br/>
		
		<b><spring:message code="registration.creditCard.expirationYear" /></b>:
		<jstl:out value="${registration.creditCard.expirationYear}"/><br/>
		
		<b><spring:message code="registration.creditCard.CVV" /></b>:
		<jstl:out value="${registration.creditCard.CVV}"/><br/>


<security:authorize access="hasRole('AUTHOR')">
<jstl:if test="${registration.author.userAccount.username == pageContext.request.userPrincipal.name}">
<br/>
	<a href="registration/author/edit.do?registrationId=${registration.id}"><spring:message code="registration.edit"/></a><br/>
</jstl:if>
</security:authorize>