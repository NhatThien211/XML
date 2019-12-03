<?xml version="1.0" encoding="UTF-8" standalone='yes'?>

<!--
    Document   : ultimate.xsl
    Created on : November 28, 2019, 8:30 PM
    Author     : ASUS
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" 
                xmlns:xh="http://www.w3.org/1999/xhtml"
                xmlns:t="test"
                xmlns="test"
                version="1.0">
    <xsl:output method="xml" omit-xml-declaration="yes" indent="yes"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="t:houses">
        <xsl:variable name="listDoc" select="document(@link)"/>
        <xsl:value-of select="$listDoc//xh:article"/>
    </xsl:template>

</xsl:stylesheet>
