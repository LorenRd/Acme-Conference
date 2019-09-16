<%--
 * header.jsp
 *
 * Copyright (C) 2019 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="jstl"  uri="http://java.sun.com/jsp/jstl/core"%>

<div>
	<a href="#"><img width="300px" src="${bannerWelcome }" alt="Acme Conference Co., Inc." /></a>
</div>

<div>
	<ul id="jMenu">
		<!-- Do not forget the "fNiv" class for the first level links !! -->
		<security:authorize access="hasRole('ADMIN')">
			<li><a class="fNiv"><spring:message	code="master.page.administrator" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="administrator/register.do"><spring:message code="master.page.administrator.register"/></a></li>
					<li><a href="dashboard/administrator/display.do"><spring:message code="master.page.administrator.dashboard"/></a></li>
					<li><a href="customisation/administrator/display.do"><spring:message code="master.page.administrator.customisation"/></a></li>
					<li><a href="conference/administrator/list.do"><spring:message code="master.page.administrator.conference"/></a></li>
					<li><a href="submission/administrator/list.do"><spring:message code="master.page.administrator.submission"/></a></li>
					<li><a href="category/administrator/list.do"><spring:message code="master.page.administrator.category" /></a></li>
					<li><a href="settle/administrator/list.do"><spring:message code="master.page.settle.list" /></a></li>
				</ul>
			</li>
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.authors" /> 
				</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="author/list.do"><spring:message code="master.page.authorList" /></a></li>
				</ul>
			</li>
		</security:authorize>
		<security:authorize access="isAnonymous()">
			<li><a class="fNiv" href="security/login.do"><spring:message code="master.page.login" /></a>
			<ul>
					<li class="arrow"></li>
					<li><a href="author/register.do"><spring:message
								code="master.page.register.author" /></a></li>
					<li class="arrow"></li>
					<li><a href="reviewer/register.do"><spring:message
								code="master.page.register.reviewer" /></a></li>
								<li class="arrow"></li>
					<li><a href="sponsor/register.do"><spring:message
								code="master.page.register.sponsor" /></a></li>
								
				</ul></li>
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.conferences" /> 
				</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="conference/listForthcoming.do"><spring:message code="master.page.listForthcoming" /></a></li>
					<li><a href="conference/listPast.do"><spring:message code="master.page.listPast" /></a></li>
					<li><a href="conference/listRunning.do"><spring:message code="master.page.listRunning" /></a></li>					
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('REVIEWER')">
			<li><a class="fNiv"><spring:message	code="master.page.reviewer" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="report/reviewer/list.do"><spring:message code="master.page.reviewer.report" /></a></li>							
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('AUTHOR')">
			<li><a class="fNiv"><spring:message	code="master.page.author" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="submission/author/list.do"><spring:message code="master.page.author.submissions" /></a></li>
					<li><a href="cameraReadyPaper/author/list.do"><spring:message code="master.page.author.cameraReadyPapers" /></a></li>	
					<li><a href="registration/author/list.do"><spring:message code="master.page.author.registrations" /></a></li>									
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="hasRole('SPONSOR')">
			<li><a class="fNiv"><spring:message	code="master.page.sponsor" /></a>
				<ul>
					<li class="arrow"></li>
					<li><a href="sponsorship/sponsor/list.do"><spring:message code="master.page.sponsor.sponsorships" /></a></li>
				</ul>
			</li>
		</security:authorize>
		
		<security:authorize access="isAuthenticated()">
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.profile" /> 
			        (<security:authentication property="principal.username" />)
				</a>
				<ul>
					<li class="arrow"></li>
					<security:authorize access="hasRole('AUTHOR')">
					<li><a href="author/display.do"><spring:message code="master.page.profile" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('REVIEWER')">
					<li><a href="reviewer/display.do"><spring:message code="master.page.profile" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('SPONSOR')">
					<li><a href="sponsor/display.do"><spring:message code="master.page.profile" /></a></li>
					</security:authorize>
					<security:authorize access="hasRole('ADMIN')">
					<li><a href="administrator/display.do"><spring:message code="master.page.profile" /></a></li>
					</security:authorize>
					<li><a href="message/actor/list.do"><spring:message code="master.page.profile.messages" /></a></li>					
					<li><a href="j_spring_security_logout"><spring:message code="master.page.logout" /> </a></li>
				</ul>
			</li>
			<li>
				<a class="fNiv"> 
					<spring:message code="master.page.conferences" /> 
				</a>
				<ul>
					<li class="arrow"></li>
					<li><a href="conference/listForthcoming.do"><spring:message code="master.page.listForthcoming" /></a></li>
					<li><a href="conference/listPast.do"><spring:message code="master.page.listPast" /></a></li>
					<li><a href="conference/listRunning.do"><spring:message code="master.page.listRunning" /></a></li>					
				</ul>
			</li>
		</security:authorize>
	</ul>
</div>

<div>
	<a href="?language=en">en</a> | <a href="?language=es">es</a>
</div>

