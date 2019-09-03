<%--
 * 
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<jstl:if test="${permission }">
<form:form action="sponsorship/sponsor/edit.do" modelAttribute="sponsorship">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="sponsor"/>
	<form:hidden path="conference"/>

	<form:label path="banner">
		<spring:message code="sponsorship.banner" />:
	</form:label>
	<form:input path="banner" />
	<form:errors cssClass="error" path="banner" />
	<br/>

	<form:label path="targetURL">
		<spring:message code="sponsorship.targetURL" />:
	</form:label>
	<form:input path="targetURL" />
	<form:errors cssClass="error" path="targetURL" />
	<br/>
	
<fieldset>
	
<legend>  <form:label path="creditCard">  <spring:message code="sponsorship.creditCard" />: </form:label>  </legend>
	
	<form:label path="creditCard.holderName">
		<spring:message code="sponsorship.creditCard.holderName" />:
	</form:label>
	<form:input path="creditCard.holderName" />
	<form:errors cssClass="error" path="creditCard.holderName" />
	<br />
	
	<form:label path="creditCard.brandName">
		<spring:message code="sponsorship.creditCard.brandName" />:
	</form:label>
	<form:input path="creditCard.brandName" />
	<form:errors cssClass="error" path="creditCard.brandName" />
	<br />
	
	<form:label path="creditCard.number">
		<spring:message code="sponsorship.creditCard.number" />:
	</form:label>
	<form:input path="creditCard.number" placeholder = "4024007148621138" />
	<form:errors cssClass="error" path="creditCard.number" />
	<br />
	
	<form:label path="creditCard.expirationMonth">
		<spring:message code="sponsorship.creditCard.expirationMonth" />:
	</form:label>
	<form:input path="creditCard.expirationMonth" placeholder = "08"/>
	<form:errors cssClass="error" path="creditCard.expirationMonth" />
	<br />
	
	<form:label path="creditCard.expirationYear">
		<spring:message code="sponsorship.creditCard.expirationYear" />:
	</form:label>
	<form:input path="creditCard.expirationYear" placeholder = "21"/>
	<form:errors cssClass="error" path="creditCard.expirationYear" />
	<br />
	
	<form:label path="creditCard.CVV">
		<spring:message code="sponsorship.creditCard.CVV" />:
	</form:label>
	<form:input path="creditCard.CVV" placeholder = "422" />
	<form:errors cssClass="error" path="creditCard.CVV" />
	<br />
	
	</fieldset>
	<br/>
	
	<spring:message code="sponsorship.save" var="saveSponsorship"  />
	<spring:message code="sponsorship.delete" var="deleteSponsorship"  />
	<spring:message code="sponsorship.confirm.delete" var="confirmDeleteSponsorship"  />
	<spring:message code="sponsorship.cancel" var="cancelSponsorship"  />
	
	<input type="submit" name="save"
		value="${saveSponsorship}" />&nbsp; 
	<jstl:if test="${sponsorship.id != 0}">
		<input type="submit" name="delete"
			value="${deleteSponsorship}"
			onclick="return confirm('${confirmDeleteSponsorship}')" />&nbsp;
	</jstl:if>
	<input type="button" name="cancel"
		value="${cancelSponsorship}"
		onclick="javascript: relativeRedir('sponsorship/sponsor/list.do');" />
	<br />

</form:form>

</jstl:if>

<jstl:if test="${!permission }">
<h3><spring:message code="sponsorship.nopermission"   /></h3>
</jstl:if>