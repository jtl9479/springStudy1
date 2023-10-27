# 스프링 AOP

    스프링의 의존성 주입을 이용하면 비즈니스 컴포넌트를 구성하는 객체들의 결합도를 떨어뜨릴 수 있어서 의존관계를 쉽게 변경할 수 있다.
    스프링의 IoC가 결합도와 관련된 기능이라면 AOP(Aspect Oriented Programming)는 응집도와 관련된 기능이라 할 수 있다.

## 1.1 AOP 이해하기

    새로운 메소드를 구현하는 가장 일반적인 방법은 기존에 잘 만들어진 메소드를 복사해서 구현하는 것이다. 이렇게 되면 결국 비즈니스 메소드에 부가적인 코드들이 반복해서 등장한다. 따라서 코드 분석과 유지보수를 어렵게 만든다. AOP는 이러한 부가적인 공통코드들을 효율적으로 관리하는 데 주목한다.

    AOP를 이해하는 데에 가장 중요한 핵심 개념이 바로 관심 분리(Separation of Concerns)이다.

    AOP에서는 메소드마다 공통으로 등장하는 로깅이나 예외, 트랜잭션 처리 같은 코드들을 횡단 관심(Crosscutting Concerns)이라고 한다.
    이에 반해 사용자의 요청에 따라 실제로 수행되는 핵심 비즈니스 로직을 핵심 관심(Core Concerns)이라고 한다.

    이 두 관심을 완벽하게 분리 할 수 있다면, 우리가 구현하는 메소드에는 실제 비즈니스로직만으로 구성할 수 있으므로 더욱 간결하고 응집도 높은 코드를 유지할 수있다. 문제는 기존의OOP(Object-Oriented-Programming)언어에서는 횡단 관심에 해당하는 공통 코드를 완벽하게 독립적인 모듈로 분리해내기가 어렵다.

    정리하면 OOP처럼 모듈화가 뛰어난 언어를 사용하여 개발하더라도 공통 모듈에 해당하는 클래스 객체를 생성하고 공통 메소드를 호출하는 코드가 비즈니스 메소드에 있다면 핵심 관심과 횡단 관심을 완벽하게 분리할 수는 없다.
    하지만 스프링의 AOP는 이런 OOP의 한계를 극복할 수 있도록 도와준다.

## 1.2 AOP 실행

    AOP 소스 추가 부분
    pom.xml dependency에 aop관련 라이브러리를 추가한다.
    ```
        <!-- aop관련 라이브러리 추가 -->
		<dependency>
			<groupId>org.aspectj</groupId> <!-- 부분적인 프로젝트나 조직에서의 라이브러리 집합을 식별하기 위해 제공한다. -->
			<artifactId>aspectjrt</artifactId> <!-- artifactId는 버전 정보를 생략한 jar 파일의 이름이다 -->
			<version>${org.aspectj-version}</version>
		</dependency>
		
		<dependency>
			<groupId>org.aspectj</groupId>
			<artifactId>aspectjweaver</artifactId>
			<version>1.8.8</version>
		</dependency>
    ```

    application.xml 부분

    namespace로 aop추가 후

    aop 관련 횡단 로직 소스 빈 추가
    ```
	<bean id="log" class="com.springbook.biz.common.LogAdvice"></bean>
	<bean id="log4j" class="com.springbook.biz.common.Log4jAdvice"></bean>
    ```

    aop 설정
    ```
	<aop:config>
		<aop:pointcut id="allPointcut" expression="execution(* com.springbook.biz..*Impl.*(..))" />
	
		<aop:aspect ref="log">
			<aop:before pointcut-ref="allPointcut" method="printLog" />
		</aop:aspect>
	
		<aop:aspect ref="log4j">
			<aop:after pointcut-ref="allPointcut" method="printLog4j"/>
		</aop:aspect>
	</aop:config>    
    ```

    설정을 추가하면 해당 메소드들이 실행됨을 확인할 수 있다.

    스프링의 aop는 클라이언트가 핵심 관심에 해당하는 비즈니스 메소드를 호출할 때, 횡단 관심에 해당하는 메소드를 실행해준다.
    이때 핵심 관심 메소드와 횡단 관심 메소드사이에서 소스상의 결합은 발생하지 않는다.

# 2 AOP 용어 및 기본 설정

## 2.1 AOP 용어 정리

## 2.1.1 조인포인트 (Joinpoint)

    조인포인트는 클라이언트가 호출하는 모든 비즈니스 메소드로써, 클래스의 모든 메소드를 조인포인트라고 한다. 조인포인트를 '포인트컷 대상' 또는 '포인트컷 부로'라고도 하는데, 이는 조인포인트 중에서 '포인트컷'이 선택되기 때문이다

## 2.1.2 포인트컷 (Pointcut)

    클라이언트가 호출하는 모든 비즈니스 메소드가 조인포인트라면, 포인트컷은 필터링된 조인포인트를 의미한다. 예를 들어, 트랜잭션을 처리하는 공통 기능을 만들었다고 가정하자. 이 횡단 관심 기능은 등록, 수정, 삭제 기능의 비즈니스 메소드에 대해서는 당연히 동작해야 하지만, 검색 기능의 메소드에 대해서는 트랜잭션과 무관하므로 동작할 필요가 없다. 이렇게 수많은 비즈니스 메소드 중에서 우리가 원하는 특정 메소드에서만 공통 기능을 수행시키기 위해서 포인트컷이 필요하다.
    포인트컷을 이용하면 메소드가 포함된 클래스와 패키지는 물론이고 메소드 시그니처까지 정확하게 지정할 수 있다.

    포인트컷은 <aop:pointcut> 엘리먼트로 선언하며, id 속성으로 포인트컷을 식별하기 위한 유일한 문자열을 선언한다.

    ex)
    ```
    <aop:pointcut id="allPointcut" expression="execution(* com.springbook.biz..*Impl.*(..))" />
    <aop:pointcut id="getPointcut" expression="execution(* com.springbook.biz..*Impl.get*(..)" />
    ```

    중요한 것은 expression 속성인데, 이 값을 설정하는것에 따라 필터링되는 메소드가 달라진다. 

    * com.springbook.biz..*Impl.get*(..)
    리턴타입, 패키지경로 클래스명 메소드명 및 매개변수

    첫 번째로 등록한 allPointcut은 리턴타입과 매개변수를 무시하고 com.springbook.biz 패키지로 시작하는 클래스 중에서 이름이 Impl로 끝나는 클래스의 모든 메소드를 포인트컷으로 설정한다.

    두 번째로 등록한 getPointcut은 allPointcut과 같지만 get으로 시작하는 메소드만 포인트 컷으로 설정한다.



[매개변수 지정방법](https://wpunch2000.tistory.com/22)


## 2.1.3 어드바이스(Advice)

    어드바이스는 횡단 관심에 해당하는 공통 기능의 코드를 의미하며, 독립된 클래스의 메소드로 작성된다. 그리고 어드바이스로 구현된 메소드가 언제 동작할지 스프링 설정파일을 통해서 지정할 수 있다.

    예를 들어, 트랜잭션 관리 기능의 어드바이스 메소드가 있다고 할때 이 어드바이스가 비즈니스 로직이 수행되기 전에 동적하는 것은 아무런 의미가 없다. 당연히 로직 수행 후에 트랜잭션을 커밋 또는 롤백 처리하면 된다.

    스프링에서는 어드바이스의 동작 시점을 'before','after', ;after-returning','after-throwing', 'around' 등 다섯 가지로 지정할 수 있다.


    ```
    <aop:before pointcut-ref="allPointcut" method="printLog" />
    ```

    aop:before 자리에 동작 시점을 넣는다.



## 2.1.4 위빙(Weaving)

    위빙은 포인트컷으로 지정한 핵심 관심 메소드가 호출될 때, 어드바이스에 해당하는 횡단 관심 메소드가 십입되는 과정을 의미한다.

    이 위빙을 통해서 비즈니스 메소드를 수정하지 않고도 횡단 관심에 해당하는 기능을 추가하거나 변경할 수 있다.

    횡단코드를 핵심코드에 적용되는 일련의 과정을 의미합니다

    스프링에서는 런타임 위빙 방식을 지원한다.
    
    런타임 위빙이란, Runtime때 실제 객체를 가상의 객체로 적용시키는 과정이다.

## 2.1.5 애스팩트(Aspect) 또는 어드바이저(Advisor)

#Stack Program
 
 모든 XML 문서는 단 하나의 루트 엘리먼트(root element)를 갖는다. 루트 엘리먼트는 자식 엘리먼트를 포함할 수 있으며, 자식 엘리먼트는 다시 그 지신의 자식 엘리먼트를 포함할 수 있다. 이러한 방식으로 엘리머트들은 트리 형태의 계층 구조를 이루게 된다.

    Aspect Oriented Programming이라는 이름에서 알 수 있듯이 AOP의 핵심은 Aspect이다. Aspect는 포인트컷과 어드바이스의 결합으로서, 어떤 포인트컷 메소드에 대해서 어떤 어드바이스 소스를 실행할지 결정한다. 이 Aspect 설정에 따라 AOP의 동작 방식이 결정되므로 AOP 용어중 가장 중요한 개념이라고 할 수 있다.

    ```
    <bean id="log" class="com.springbook.biz.common.LogAdvice"></bean>
	
	<aop:config>
		<aop:pointcut id="getPointcut" expression="execution(* com.springbook.biz..*Impl.get*(com.springbook.biz.board.BoardVO))" />
	
		<aop:aspect ref="log4j">
			<aop:after pointcut-ref="getPointcut" method="printLog4j"/> <!-- get으로 시작하는 메소드에만 해당 횡단관심이 작동한다. -->
		</aop:aspect>
	</aop:config>
	
    ```

    1. getPointcut으로 설정한 포인트컷 메소드가 호출된다.(expression으로 설정한 경로)
    2. log라는 어드바이스 객체의 printLog메소드가 실행된다 (bean 객체 설정)
    3. 메소드의 동작 시점(어드바이스)에 따라 실행되다.

## 2.1.6 AOP용어 종합


![Alt text](image.png)

1. 사용자는 비즈니스 컴포넌트의 여러 조인포인트를 호출한다,
2. 이때 특정 포인트 컷으로 지정한 메소드가 호출되는 순간
3. 어드바이스 객체의 어드바이스 메소드가 실행된다. \
-- 이떄 어드바이스 메소드의 동작 시점을 5가지로 지정할 수 있다.
4. 포인트컷으로 지정한 메소드가 호출될 때 어드비아스 메소드를 삽입하도록 하는 설정을 애스팩트라고 한다.

## 2.2 AOP 엘리먼트

    스프링은 AOP관련 설정을 XML방식과 어노테이션 방식으로 지원한다.
    우선 xml설정을 먼저 확인 후 어노테이션 설정을 살펴본다.

## 2.2.1 <aop:config> 엘리먼트     

    aop설정에서 <aop:config>는 루트 엘리먼트이다. 

[루브 엘리먼트란](#stack-program)







