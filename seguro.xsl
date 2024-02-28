<?xml version="1.0" encoding="UTF-8"?>
<xsl:stylesheet xmlns:xsl="http://www.w3.org/1999/XSL/Transform" version="1.0">
    <xsl:template match="envio">
    <html>
      <head>
        <title>Información de Seguros</title>
      </head>
        <body>
          <xsl:apply-templates select="envio"/>
        </body>
    </html>
    </xsl:template>
    
    <xsl:template match="envio">
        <h1>Información de Seguros</h1>
        <h2>Accidentes</h2>
        <table border="1">
          <tr>
            <th>Nº de póliza</th>
            <th>Capital</th>
            <th>Vigor</th>
          </tr>
          <xsl:for-each select="//asegurado[garantia/tipo='Accidentes']">
            <xsl:for-each select="garantia[tipo='Accidentes']">
              <tr>
                <td><xsl:value-of select="../../numero"/></td>
                <td><xsl:value-of select="capital"/></td>
                <td><xsl:value-of select="@vigor"/></td>
              </tr>
            </xsl:for-each>
          </xsl:for-each>
        </table>

        <h2>Vida</h2>
        <table border="1">
          <tr>
            <th>Nº de póliza</th>
            <th>Capital</th>
            <th>Vigor</th>
          </tr>
          <xsl:for-each select="//asegurado[garantia/tipo='Vida']">
            <xsl:for-each select="garantia[tipo='Vida']">
              <tr>
                <td><xsl:value-of select="../../numero"/></td>
                <td><xsl:value-of select="capital"/></td>
                <td><xsl:value-of select="@vigor"/></td>
              </tr>
            </xsl:for-each>
          </xsl:for-each>
        </table>

        <h2>Dental</h2>
        <table border="1">
          <tr>
            <th>Nº de póliza</th>
            <th>Capital</th>
            <th>Vigor</th>
          </tr>
          <xsl:for-each select="//asegurado[garantia/tipo='Dental']">
            <xsl:for-each select="garantia[tipo='Dental']">
              <tr>
                <td><xsl:value-of select="../../numero"/></td>
                <td><xsl:value-of select="capital"/></td>
                <td><xsl:value-of select="@vigor"/></td>
              </tr>
            </xsl:for-each>
          </xsl:for-each>
        </table>
    </xsl:template>
</xsl:stylesheet>
