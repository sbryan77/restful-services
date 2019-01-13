<xsl:stylesheet version="2.0"
	xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
	xmlns:wsse="http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd"
	xmlns:soapenv="http://schemas.xmlsoap.org/soap/envelope/"
	xmlns:fin="http://www.viasat.com/XMLSchema/PublicWebService/v3/FinanceService">
	<xsl:template match="node() | @*">
		<xsl:copy>
			<xsl:apply-templates select="node() | @*" />
		</xsl:copy>
	</xsl:template>
	<xsl:template match="*[local-name() = 'role']/text() "> <xsl:text>******</xsl:text> 
		</xsl:template>
	<xsl:template
		match="/soapenv:Envelope/soapenv:Header/wsse:Security/wsse:UsernameToken/wsse:Password/text() ">
		<xsl:text>******</xsl:text>
	</xsl:template>
	<xsl:template
		match="/soapenv:Envelope/soapenv:Body/fin:updatePaymentMethod/fin:paymentMethod/fin:creditCard/fin:CVV/text() ">
		<xsl:text>******</xsl:text>
	</xsl:template>
	<xsl:template
		match="/soapenv:Envelope/soapenv:Body/fin:updatePaymentMethod/fin:paymentMethod/fin:eft/fin:bankAccountNumber/text() ">
		<xsl:text>******</xsl:text>
	</xsl:template>
	<xsl:template
		match="/soapenv:Envelope/soapenv:Body/fin:submitOneTimePayment/fin:paymentMethod/fin:creditCard/fin:CVV/text() ">
		<xsl:text>******</xsl:text>
	</xsl:template>
	<xsl:template
		match="/soapenv:Envelope/soapenv:Body/fin:submitOneTimePayment/fin:paymentMethod/fin:eft/fin:bankAccountNumber/text() ">
		<xsl:text>******</xsl:text>
	</xsl:template>
</xsl:stylesheet>