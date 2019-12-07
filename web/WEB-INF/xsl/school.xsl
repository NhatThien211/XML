<?xml version="1.0" encoding="UTF-8"?>

<!--
    Document   : shool.xsl
    Created on : December 6, 2019, 8:58 PM
    Author     : ASUS
    Description:
        Purpose of transformation follows.
-->

<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0"
                xmlns:t="thien.xsd"
                xmlns="thien.xsd"
                xmlns:xh="http://www.w3.org/1999/xhtml">
    <xsl:output method="xml" indent="yes" omit-xml-declaration="yes"/>

    <!-- TODO customize transformation rules 
         syntax recommendation http://www.w3.org/TR/xslt 
    -->
    <xsl:template match="t:school">
        <xsl:variable name="listDoc" select="document(@link)"/>
        <xsl:variable name="host" select="@link"/>
        <xsl:variable name="first" select="$listDoc//a[@class = 'nav-item ' and span = 'Tin tức' ] /@href"/>
        <xsl:variable name="second" select="document(concat($host,$first))"/>
        <xsl:variable name ="temp" select="$second//section[@class = 'card-columns has-1 mb-4']//a[. = 'Top 20 trường Đại học đắt đỏ nhất Việt Nam']/@href"/>
        <xsl:variable name = "schoolList" select="document(concat($host,$temp))"/>
        <xsl:element name="schools">
            <xsl:for-each select="$schoolList//h3">
                <xsl:element name="school">
                    <xsl:value-of select="."/>
                </xsl:element>
            </xsl:for-each>
        </xsl:element>
<!--      
        <xsl:element name="ab">
            <xsl:value-of select="$temp"/>
        </xsl:element>-->
    </xsl:template>

</xsl:stylesheet>
