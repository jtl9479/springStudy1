# DAY3

# Model 1 아키텍처로 게시판 개발

## 1.1 Model 1 아키텍쳐 구조

![Alt text](day3_img/image-25.png)

    Model1아키텍처는 JSP와 JavaBeans만 사용하여 웹 개발을 하는 것이다

    Model1 아키텍처에서 살펴볼 것은 Model기능의 JavaBeans다.

    Model의 정확한 의미는 데이터베이스 연동 로직을 제공하면서 DB에서 검색한 데이터가 저장되는 자바 객체다. 
    VO, DAO클래스가 Model기능의 자바 객체이다.

    Model1 아키텍처에서 jsp파일은 Controller와 View 기능을 모두 처리한다.

    Controller는 사용자의 요청 처리와 관련된 자바 코드를 의미한다.

    그리고 JSP에서는 Model을 사용하여 검색한 데이터를 사용자가 원하는 화면으로 제공하기 위해 마크업을 사용한다. 
    대표적으로 HTML, CSS를 사용하며 마크업이 View 기능을 담당한다.    

    Model1 구조는 JSP파일에서 Controller, View 기능을 모두 처리한다는 특징을 가진다.

    Model1 구조는 jsp파일에 자바 코드, 마크업 코드들이 뒤섞여 있어서 역할 구분이 어려우며, JSP파일에 대한 디버깅과 유지보수가 어려운 단점이 있다.
    
    Model2, MVC(Model View Controller)아키텍처가 등장하였으며 
    Model2는 Model, View, Controller 요소로 기능을 분리하고 개발을 진행한다.


## 1.2 로그인 기능 구현

    포워드(Forward)와 리다이렉트(Redirect) 차이

    포워드 방식 RequestDispatcher를 이용하여 응답으로 사용할 jsp화면으로 넘겨서,
    포워드된 화면이 클라이언트에 전송되는 방식이다.
    이 방식은 한 번의 요청과 응답으로 처리되므로 실행 속도는 빠르지만,
    클라이언트 브라우저에서 url이 바뀌지 않아 응답이 어디에서 들어왔는지 확인할 수 없다.

![Alt text](day3_img/image-26.png)

    리다이렉트는  요청된 JSP에서 일단 브라우저로 응답 메시지를 보냈다가 다시 서버로 재요청하는 방식이다.

    즉, 포위드 방식과 다르게 일단 응답이 브라우저로 들어간 다음, 재요청하는 방식이다. 따라서 응답이 들어온 파일로 브라우저의 url이 변경되지만, 두 번의 요청과 응답으로 처리되므로 실행 속도는 포워드 방식보다 느리다.

![Alt text](day3_img/image-27.png)

    

# 3 Model2 아키텍처로 게시판 개발

## 3.1 Model2 아키텍처 구조

    Model2아키텍처에서 가장 중요한 특징은 Controller의 등장이며
    Controller는 서블릿 클래스를 중심으로 구현한다.

![Alt text](day3_img/image-28.png)


    Model아키텍처에서는 기존에 JSP가 담당했던 Controller로직이 별도의 Controller 기능의 서블릿으로 옮겨졌다.
    따라서 기존에 Model1 아키텍처로 개발한 프로그램에서 JSP파일에 있는 자바 코드만 Controller로 이동하면 Model2 아키텍처가 된다.

    결과적으로 Controller로직이 사라진 JSP에서는 View와 관련된 다지안만 남게 된다.

![Alt text](day3_img/image-29.png)    


# 4 MVC 프레임워크 개발

## 4.1 MVC 구조

    DispatcherServlet 클래스 하나로 Controller 기능을 구현할 경우
    클라이언트의 모든 요청을 하나의 서블릿이 처리하게 된다. 
    그럴 경우 수많은 분기처리 로직을 가질 수 밖에 없고, 이는 오히려 개발과 유지보수를 어렵게 만든다.

MVC 프레임워크  구조
![Alt text](day3_img/image-30.png)    


MVC 프레임워크 클래스 역활
![Alt text](day3_img/image-31.png)


## 4.2 MVC 프레임워크 구현

    1) Controller 인터페이스 작성
    Controller를 구성하는 요소 중에서 DispatcherServlet은 클라이언트의 요청을 가장 먼저 받아들이는 Front Controller이다.

    1. 클라이언트의 요청을 받은 DispatcherServlet은 
    2. HandlerMapping을 통해 Controller객체를 검색한다.
    3. 검색된 Controller 실행한다.    

    2) Controller 구현


    3) HandlerMapping 클래스 작성
    HandlerMapping은 모든 Controller객체들을 저장하고 있다고, 클라이언트의 요청이 들어오면 Controller를 검색하는 기능을 제공한다.
    HandlerMapping객체는 DispatcherServlet이 사용하는 객체이다.
    따라서 DispatcherServlet이 생성되고 init() 메소드가 호출될 때 단 한 번 생성된다.


    4) ViewResolver 클래스 작성
    ViewResolver 클래스는 Controller가 리턴한 View 이름에 접두사(prepix), 접미사(suffix)를 결합하여 최종으로 실행될 View 경로와 파일명을 완성한다.(servlet-context.xml의 기능)
    HandlerMapping과 마찬가지로 DispatcherServlet의 init() 메소드가 호출될 때 생성된다.

    5) DispathcerServlet 수정
    
![Alt text](day3_img/image-31.png)


1. 클라이언트가 로그인 버튼을 클릭하여 'login.do' 요청하면 dispatcherServlet이 요청을 받는다.
2. dispatcherServlet은 handlerMapping 객체를 통해 로그인 요청을 처리할 Controller를 검색하고
3. 검색된 Controller의 handlerRequest()메소드를 호출하면 로그인 로직이 처리된다
4. 로그인 처리 후 화면 정보가 리턴되면
5. dispathcerServlet은 viewResolver를 통해 접두사와 접미사가 붙은 jsp파일의 이름과 경로를 리턴받는다
6. 최종적으로 jsp실행 후 브라우저에 응답한다.

# 5 Spring MVC 구조

## 5.1 Spring MVC 수행 흐름

![Alt text](img/../day3_img/image-33.png)

1. 클라이언트로부터의 모든 ".do" 요청을 DispatcherServlet이 받는다.
2. DispatcherServlet은 HandlerMapping을 통해 요청을 처리할 Controller를 검색한다.
3. DispatcherServlet은 검색된 Controller를 실행하여 클라이언트의 요청을 처리한다.
4. Controller는 비지니스 로직으 수행 결과로 얻어낸 Model 정보와 Model을 보여줄 View 정보를 ModelAndView객체에 저장하여 리턴한다.
5. DispatcherServlet은 ModelAndView로부터 View정보를 추출하고, ViewResolver를 이용하여 응답으로 사용할 View를 얻어낸다.
6. DispatcherServlet은 ViewResolver를 통해 찾아낸 View를 실행하여 응답을 전송한다.

## 5.2 DispatcherServlet등록 및 스프링 컨테이너 구동

### 5.2.1 DispatcherServlet등록

    spring mvc에서 가장 중요한 요소가 모든 클라이언트의 요청을 가장 먼저 받아들이는 DispathcerServlet이다.

    따라서 Spring MVC적용에서 가장 먼저 해야할 일은 WEB-INF/web.xml 파일에 등록된 DispatcherServlet 클래스를 스프링 프레임워크에서 제공하는 DispathcerServlet으로 변경하는 것이다.

web.xml 설정
```xml
	<servlet>
		<servlet-name>appServlet</servlet-name>
		<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
	</servlet>

	<servlet-mapping>
		<servlet-name>appServlet</servlet-name>
		<url-pattern>*.do</url-pattern>
	</servlet-mapping>    
```

    서블릿 컨테이너는 클라이언트의 ".do"의 요청이 있어야 DispatcherServlet 객체를 생성한다.
    클라이언트가 ".do" 요청을 서버에 전달하면 서블릿 컨테이너는 web.xml파일에 appServlet이라는 이름으로 등록된 DispatcherServlet클래스의 객체를 생성한다.


### 5.2.2 스프링 컨테이너 구동

    클라이언트의 요청으로 DispatcherServlet객체가 생성되고 나면 DispathcerServlet클래스에 재정의된 init()메소드가 자동으로 실행되어 XmlWebApplicationContext라는 스프링컨테이너가 구동된다. 

    Spring mvc의 구성 요소 중에서 DispacherServlet클래스가 유일한 서블릿이다. 따라서 서블릿 컨테이너는 web.xml 파일에 등록된 DispacherServlet만 생성해준다. 
    하지만 DispacherServlet객체 혼자서는 클라이언트의 요청을 처리할 수 없고, 반드시 HandlerMapping, Controller, ViewResolver객체들과 상호작용해야 한다.
    이 객체들을 메모리에 생성하기 위해서 DispacherServlet은 스프링컨테이너를 구동하는 것이다.

    결국 DispacherServlet은 클라이언트의 요청처리에 필요한 HandlerMapping, Controller, ViewResolver객체들을 생성하기 위해 스프링 컨테이너를 구동한다.

스프링컨테이너 구동과정
![Alt text](day3_img/image-34.png)

    서블릿 컨테이너가 DispacherServlet객체를 생성하고 나면 재정의한 init() 메소드가 자동으로 실행된다.

    init()메소드는 스프링 설정파일을 로딩하여 XmlWebApplictionContext를 생성한다.
    즉 DispacherServlet이 사용할 HandlerMapping, Controller, ViewResolver클래스를 <bean>등록하면 스프링 컨테이너가 해당 객체들을 생성해준다.

## 5.3 스프링 설정 파일 변경

    DispacherServlet은 자신이 사용할 객체들을 생성하기 위해서 스프링 컨테이너를 구동한다.
    이때 스프링 컨테이너를 위한 설정 파일의 이름과 위치는 서블릿 이름을 기준으로 자동으로 결정된다.
    하지만 필요에 따라서는 설정 파일의 이름을 바꾸거나 위치를 변경할 수도 있다.
    이때 서블릿 초기화 파라미터를 이용하면 된다.

서블릿 초기화 파라미터
```xml
	<servlet>
		<servlet-name>appServlet</servlet-name>
		<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
		<init-param><!--서블릿 초기화 파라미터-->
			<param-name>contextConfigLocation</param-name>
			<param-value>/WEB-INF/spring/appServlet/servlet-context.xml</param-value>
		</init-param>
	</servlet>
```

    DispacherServlet클래스를 등록한 곳에 <init-param>설정을 추가한다. 이떄, <param-name>엘리먼트로 지정한 contextConfigLocation은 대소문자를 구분하므로 정확하게 등록해야 한다.

## 5.4 인코딩 설정

    스프링이서는 인코딩 처리를 위해 CharacterEncodingFilter클래스를 제공하며 web.xml 파일에 CharacterEncodingFilter를 등록하면 모든 클라이언트의 요총에 대해서 일괄적으로 인코딩을 처리할 수 있다.

 ```xml
 	<filter> 
	    <filter-name>encodingFilter</filter-name> 
	    <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class> 
	    <init-param> 
	       <param-name>encoding</param-name> 
	       <param-value>UTF-8</param-value> 
	    </init-param> 
	    <init-param> 
	       <param-name>forceEncoding</param-name> 
	       <param-value>true</param-value> 
	    </init-param> 
	 </filter> 
	 <filter-mapping> 
	    <filter-name>encodingFilter</filter-name> 
	    <url-pattern>/do</url-pattern> 
	 </filter-mapping>
 ```        

# 6-7 spring mvc 적용

## 6.1 Spring MVC 적용준비

    





### DispatcherServlet이란

HTTP 프로토콜로 들어오는 모든 요청을 가장 먼저 받아 적합한 컨트롤러에 위임해주는 프론트 컨트롤러