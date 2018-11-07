# 설정

JavaMailSender의 구현체 JavaMailSenderImpl 객체를 생성해서 host, port, protocol, encoding, username, password, smtp auth, smtp starttls enable 등의 설정을 아래와 같이 한다.

~~~java
@Configuration
public class MailConfig {

	@Bean
	public JavaMailSender getJavaMailSenderGmail() {

		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		mailSender.setHost("smtp.gmail.com");
		mailSender.setPort(587);
		mailSender.setProtocol("smtp");
		mailSender.setDefaultEncoding("UTF-8");
		mailSender.setUsername(""); // 메일 주소
		mailSender.setPassword(""); // 메일 비번

		Properties prop = new Properties();
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.debug", "true");

		mailSender.setJavaMailProperties(prop);

		return mailSender;
	}
	
	
	@Bean
	public JavaMailSender getJavaMailSenderNaver() {
		
		JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
		
		mailSender.setHost("smtp.naver.com");
		mailSender.setPort(587);
		mailSender.setProtocol("smtp");
		mailSender.setDefaultEncoding("UTF-8");
		mailSender.setUsername(""); // 메일 주소
		mailSender.setPassword(""); // 메일 비번
		
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.debug", "true");
		
		mailSender.setJavaMailProperties(prop);

		return mailSender;
	}
}
~~~


스프링부트에서는 이 설정을 application.yml에 (혹은 application.property 에) 아래와 같이 설정할 수 있다.
다만 여러 계정을 사용하려면 Google mail에서 외부메일 사용에 메일을 추가하거나  
위에서 처럼 java config 파일을 생성해서 설정해야 한다. (yml 파일에 host 여러개 설정하는 법이 있다면 알려주세요.)

~~~yml
spring:
    mail:
      host: smtp.gmail.com
      username: # 메일주소
      password: # 비밀번호
      port: 587
      protocol: smtp
      default-encoding: UTF-8
      properties:
        mail:
          smtp:
            auth: true
            starttls:
              enable: true
~~~



The spring-boot-starter-mail adds the following dependent libraries to your project,

- javax.mail
- spring-context
- spring-context-support
- spring-boot-starter


      <dependency>
				<groupId>org.springframework.boot</groupId>
				<artifactId>spring-boot-starter-mail</artifactId>
				<version>${revision}</version>
			</dependency>



      <javax-mail.version>1.6.2</javax-mail.version>
      
      <sun-mail.version>${javax-mail.version}</sun-mail.version>
      
			<dependency>
				<groupId>com.sun.mail</groupId>
				<artifactId>javax.mail</artifactId>
				<version>${sun-mail.version}</version>
			</dependency>
      
      
      <dependency>
				<groupId>javax.mail</groupId>
				<artifactId>javax.mail-api</artifactId>
				<version>${javax-mail.version}</version>
			</dependency>
      
      
      
Spring Framework provides you with a API to send an email. It includes some interfaces and classes, 
all of which are located in the two packages such as  org.springframework.mail & org.springframework.mail.javamail.
To use the  Spring Mail in the  Spring Boot application, let's add  the following dependencies to the  pom.xml:




The Spring Mail  also has many classes or interfaces. Basically, below is the list of main classes and interfaces:

org.springframework.mail.MailSender
org.springframework.mail.SimpleMailMessage
org.springframework.mail.MailMessage
org.springframework.mail.javamail.JavaMailSender
org.springframework.mail.javamail.JavaMailSenderImpl
org.springframework.mail.javamail.MimeMessagePreparator
org.springframework.mail.javamail.MimeMessageHelper
org.springframework.mail.javamail.MimeMailMessage

![](https://o7planning.org/en/11145/cache/images/i/20740934.png)



MIME (Multi-Purpose Internet Mail Extensions):
MIME (Multi-Purpose Internet Mail Extensions): an extension of initial Internet Email. It allows sending the emails attached with different kinds of data in the Internet such as  audio, video, image,.., and supports emails in  HTML format.

Class/Interface	Description
MailSender	This is a top-level interface, which provides functions to send a simple email.
JavaMailSender	This is the subinterface of MailSender, which supports MIME -typed messages, It is usualy used with MimeMessageHelper class to create  MimeMessage. An advice is to use  the MimeMessagePreparator interface with this interface.
JavaMailSenderImpl	means a class implementing the JavaMailSender interface. It supports to send MimeMessage  and SimpleMailMessage messages.
MailMessage	Means an interface representing for a simple message. It includes basic information of an email such as sender, recipient, subject and content of message.
SimpleMailMessage	This is a class implementing the MailMessage interface, used to create a simple message.
MimeMailMessage	This is a class implementing the MailMessage interface, used to create a message supporting MIME.
MimeMessagePreparator	This interface provides callback method called when preparing a MIME message.
MimeMessageHelper	means a supporting class to create a MIME message. It supports image, and attached files and creates HTML-typed messages.




Spring-boot-starter-mail 없이 javax.mail-api 만 설정했을 때의 에러 메세지

Field error in object 'mail' on field 'bcc': rejected value [medicc900@gmail.com]; 
codes [typeMismatch.mail.bcc,typeMismatch.bcc,typeMismatch.javax.mail.internet.InternetAddress,typeMismatch]; 
arguments [org.springframework.context.support.DefaultMessageSourceResolvable: 
codes [mail.bcc,bcc]; arguments []; default message [bcc]]; 
default message [Failed to convert property value of type 
'java.lang.String' to required type 'javax.mail.internet.InternetAddress' for property 'bcc'; 
nested exception is org.springframework.core.convert.ConversionFailedException: 
Failed to convert from type [java.lang.String] to type [javax.mail.internet.InternetAddress] 
for value 'medicc900@gmail.com'; nested exception is java.lang.NoClassDefFoundError: com/sun/mail/util/PropUtil]


Field error in object 'mail' on field 'cc': rejected value [rhep0820@gmail.com]; codes [typeMismatch.mail.cc,typeMismatch.cc,typeMismatch.javax.mail.internet.InternetAddress,typeMismatch]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [mail.cc,cc]; arguments []; default message [cc]]; default message [Failed to convert property value of type 'java.lang.String' to required type 'javax.mail.internet.InternetAddress' for property 'cc'; nested exception is org.springframework.core.convert.ConversionFailedException: Failed to convert from type [java.lang.String] to type [javax.mail.internet.InternetAddress] for value 'rhep0820@gmail.com'; nested exception is java.lang.NoClassDefFoundError: Could not initialize class javax.mail.internet.InternetAddress]
Field error in object 'mail' on field 'to': rejected value [ryanhan@cloudcash.kr]; codes [typeMismatch.mail.to,typeMismatch.to,typeMismatch.javax.mail.internet.InternetAddress,typeMismatch]; arguments [org.springframework.context.support.DefaultMessageSourceResolvable: codes [mail.to,to]; arguments []; default message [to]]; default message [Failed to convert property value of type 'java.lang.String' to required type 'javax.mail.internet.InternetAddress' for property 'to'; nested exception is org.springframework.core.convert.ConversionFailedException: Failed to convert from type [java.lang.String] to type [javax.mail.internet.InternetAddress] for value 'ryanhan@cloudcash.kr'; nested exception is java.lang.NoClassDefFoundError: Could not initialize class javax.mail.internet.InternetAddress]]





* Reference
- https://o7planning.org/en/11145/spring-email-tutorial
