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
</xsl:stylesheet>