

# MyBatis Generator(MBG)
- 해당 db의 테이블들을 기준으로 하여 persistence 레이어에 필요한 기본적인 object와 맵핑파일을 생성해주는 툴이다.
vo(model), mapper(dao), xml(query)을 생성해준다.

# xml 설정 파일 예시

~~~xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE generatorConfiguration
  PUBLIC "-//mybatis.org//DTD MyBatis Generator Configuration 1.0//EN"
  "http://mybatis.org/dtd/mybatis-generator-config_1_0.dtd">

<generatorConfiguration>
  <classPathEntry location="/Program Files/IBM/SQLLIB/java/db2java.zip" />

  <context id="DB2Tables" targetRuntime="MyBatis3">
    <jdbcConnection driverClass="COM.ibm.db2.jdbc.app.DB2Driver"
        connectionURL="jdbc:db2:TEST"
        userId="db2admin"
        password="db2admin">
    </jdbcConnection>

    <javaTypeResolver >
      <property name="forceBigDecimals" value="false" />
    </javaTypeResolver> //forceBigDecimal 설정은 DB column의 타입이 DECIMAL 혹은 NUMERIC일때 항상 java.math.BigDecimal을 사용하는 설정

   <javaModelGenerator targetPackage="kr.cashcloud.model" targetProject="cashcloud">
    	<property name="trimStrings" value="true"/> //trimStrings는 DB로부터 반환된 character field의 공백을 제거하는 코드를 삽입해준다.
    </javaModelGenerator> //해당 테이블의 primary key 클래스, record 클래스, Query by Example 클래스를 생성하는 설정
    
    <sqlMapGenerator targetPackage="kr.cashcloud.mapper.sql" targetProject="cashcloud/src/main/resources">
    	<property name="enableSubPackages" value="true"/>
      //enableSubPackages는 해당 테이블이 존재하는 스키마 명으로(ex com.mycompany.myschema)패키지를 생성한다.
    </sqlMapGenerator> // 해당 table의 SQL Mapper XML파일을 생성하는 설정 
    
    <javaClientGenerator targetPackage="kr.cashcloud.mapper" targetProject="cashcloud" type="XMLMAPPER">
    </javaClientGenerator> // 생성된 Model과 XML Mapper을 위한 인터페이스와 클래스파일을 생성하는 설정
    
    // 1.회사에서 사용중인 방법
    <table tableName="t_code"
        domainObjectName="Code" 
        enableCountByExample="false"
        enableDeleteByExample="false"
        enableSelectByExample="false"
        enableUpdateByExample="false"></table>
    // 2.공식문서 예시      
    <table schema="DB2ADMIN" tableName="ALLTYPES" domainObjectName="Customer" >
      <property name="useActualColumnNames" value="true"/>
      <generatedKey column="ID" sqlStatement="DB2" identity="true" />
      <columnOverride column="DATE_FIELD" property="startDate" />
      <ignoreColumn column="FRED" />
      <columnOverride column="LONG_VARCHAR_FIELD" jdbcType="VARCHAR" />
    </table>

  </context>
</generatorConfiguration>
~~~


# Reference
- http://www.mybatis.org/generator/
- http://www.mybatis.org/generator/configreference/xmlconfig.html
- http://www.mybatis.org/generator/configreference/javaTypeResolver.html
- http://www.mybatis.org/generator/configreference/javaModelGenerator.html
- http://www.mybatis.org/generator/configreference/sqlMapGenerator.html
- http://www.mybatis.org/generator/configreference/javaClientGenerator.html
