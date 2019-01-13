/**
 * The purpose of this package is to add remote call invocation times to every CXF WebService call
 * Here is sample output:
 <pre>
+++++++++++++++++++++++++++++++++++++++++++++++++++++++
     Method Name: createHotspotUserAccount
     Number of remote calls: 11
     Remote calls:
     ---DCPCommonPort.logon : 187 msecs
     ---BusinessTransaction.addFacadeTransaction : 1313 msecs
     ---BusinessTransaction.getServiceAgreementByInternalReference : 39 msecs
     ---ReferenceDataServiceInterface.getServiceProviderBySalesChannelAndExternalSystemAndOrganizationName : 19 msecs
     ---DCPPort.getAccount : 64 msecs
     ---BusinessTransaction.updateFacadeTransaction : 25 msecs
     ---BusinessTransaction.getServiceAgreementByInternalReference : 30 msecs
     ---ReferenceDataServiceInterface.getServiceProviderBySalesChannelAndExternalSystemAndOrganizationName : 14 msecs
     ---DCPPort.createAccount : 1492 msecs
     ---BusinessTransaction.updateFacadeTransaction : 39 msecs
     ---DCPCommonPort.logoff : 14 msecs
     Total Method Execution Time: 3912 msecs
+++++++++++++++++++++++++++++++++++++++++++++++++++++++
 </pre>
 *
 * The package uses CXF Interceptors to record call durations. Interceptors must be configured via Spring configuration files.
 * Here are examples of configuration:
 <pre>

	<jaxws:endpoint id="MobilityService"
		implementorClass="com.viasat.facade.mobility.MobilityServiceImpl"
		implementor="#MobilityServiceProxy" address="/MobilityService">

		<jaxws:inInterceptors>
			<bean class="com.viasat.wildblue.common.performance.LastStatisticsInterceptor">
				<constructor-arg type="java.lang.String" value="com.viasat.wildblue.facade.common.volubill.interceptor.LogoutInterceptor" />
			</bean>
		</jaxws:inInterceptors>
	</jaxws:endpoint>

	<bean id="timeInbound" class="com.viasat.wildblue.common.performance.PreInvokeStatisticsInterceptor">
		<property name="implClassName" value="com.viasat.facade.mobility.MobilityServiceImpl"/>
	</bean>
	<bean id="timeOutbound" class="com.viasat.wildblue.common.performance.SetupStatisticsInterceptor">
		<property name="implClassName" value="com.viasat.facade.mobility.MobilityServiceImpl"/>
	</bean>

	<cxf:bus>
		<cxf:inInterceptors>
			<ref bean="logInbound" />
			<ref bean="timeInbound" />
		</cxf:inInterceptors>
		<cxf:outInterceptors>
			<ref bean="logOutbound" />
			<ref bean="timeOutbound" />
		</cxf:outInterceptors>
		<cxf:outFaultInterceptors>
			<ref bean="logOutbound" />
		</cxf:outFaultInterceptors>
	</cxf:bus>
 </pre>
 *
 */
package com.viasat.wildblue.common.performance;

