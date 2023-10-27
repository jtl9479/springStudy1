서블릿 컨테이너 작동 순서

1. WEB-INF/web.xml 파일을 로딩하여 구동
    -> 컨테이너는 자신이 관리할 클래스들이 등록된 xml 설정 파일을 로딩하여 구동
    -> 클라이언트요청이 들어오는 순간 xml 설정 파일을 참조하여 객체를 생성하고, 객체의 생명주기를 관리한다.
2. 브라우저로부터 /hello.do 요청 수신
3. hello.HelloServlet 클래스를 찾아 객체를 생성하고 doGet() 메소드 호출
4. doGet() 메소드 실행 결과를 클라이언트 브라우저로 전송


# 결합도가 높은 프로그래밍
    결합도란 하나의 클래스가 다른 클래스와 얼마나 많이 연결되어 있는지 나타내는 표현이며, 결합도가 높은 프로그램은 유지보수가 어렵다.

# 스프랑 컨테이너 및 설정파일
    대부분 IoC컨테이너는 각 건테이너에서 관리할 객체들을 위한 별도의 설정 파일이 있다.

    Servlet컨테이너는 web.xml파일에 해당 컨테이너가 생성하고 관리할 클래스들을 등록한다.

    스프링 프레임워크도 다른 컨테이너와 마찬가지로 자신이 관리할 클래스들이 등록된 설정파일이 필요하다.

    src/main/resources 경로에 spring bean configuration file을 생성하면 기본적으로 bean 루트 엘리먼트와 네임스페이스 관련 설정들이 추가되어 제공된다.

    빈(Bean)은 스프링 컨테이너에 의해 관리되는 재사용 가능한 소프트웨어 컴포넌트이다.

    즉, 스프링 컨테이너가 관리하는 자바 객체를 뜻하며, 하나 이상의 빈(Bean)을 관리한다.

    https://velog.io/@ehdrms2034/Spring-MVC-Application-Context.xml

## 스프링컨테이너 동작 순서
    1. 클라이언트가 스프링 설정 파일을 로딩하여 컨테이너 구동
    2. 스프링 설정 파일 (bean) 등록된 class 객체 생성
    3. getbean(String) 메소드로 이름이 String인 객체 요청
    4. class 객체 반환

## 스프링 컨테이너의 종료
    스프링에서는 BeanFactory와 이를 상속한 applicationContext 두 가지 유형의 컨테이너를 제공한다.

    먼저 BeanFactory는 스프링 설정 파일에 등록된 Bean 객체를 생성하고 관리하는 가장 기본적인 컨테이너 기능만 제공한다.
    그리고 컨테이너가 구동될 때 bean객체를 생성하는 것이 아니라, 클라이언트 요청에 의해서만 객체가 생성되는 지연 로딩(lazy loading)방식을 사용한다.
    
    지연로딩 : 필요한 시점에 연관된 데이터를 불러오기
    즉시로딩 : 연관된 데이터를 한 번에 불러오기

    따라서 일반적으로 BeanFactory를 사용하지 않는다.(갹체를 생성하고 관리하는 가장 기본적인 컨테이너 기능만 제공하기 떄문에)

    반면에 applicationContext는 BeanFactory가 제공하는  bean객체 관리 기능 외에도 트랜잭션 관리나 메시지 기반의 다국어 처리 등 다양한 기능을 지원한다.
    또한, 컨테이너가 구동되는 시점에서 bean등록된 클래스들을 객체 생성하는 즉시 로딩 방식(pre-loading)으로 동작한다.
    또한 웹 애플리케이션 개발도 지원하므로 대부분 스프링 프로젝트는 applicationContext방식을 사용한다.

    applicationContext 구현클래스

    GenericXmlApplicationContext : 파일 시스템이나 클래스 경로에 있는 xml 설정 파일을 로딩하여 구동하는 컨테이너이다.
    XmlWebApplicationContext : 웹 기반의 스프링 애플리케이션을 개발할 때 사용하는 컨테이너이다.


## 스프링 xml 설정

    beans 루트 엘리먼트

    스프링 컨테이너는 bean 저장소에 해당하는 xml설정 파일을 참조하여 bean의 생명주기를 관리하고 여러 가지 서비스를 제공한다.
        -> 스프링 프로젝트 전체에서 가장 주요한 역할을 담당한다.
    
    스프링 설정 파일 이름은 무엇을 사용하든 상관없지만 beans를 루트 엘리먼트로 사용해야한다.
    그리고 beans 엘리먼트 시작 태그에 네임스페이스를 비롯한 xml스키마 관련 정보가 설정된다.

    자식 엘리먼트로는 bean, description, alias, import... 가 존재한다.

    import 엘리먼트
    
        모든 설정을 하나의 파일로 모두 관리할 수 있지만, 그렇게 하면 스프링 설정 파일이 너무 길어지고 관리도 어렵기 때문에
        기능별 여러 xml파일로 나누어 설정하는 것이 더 효율적이다.
        이를 가능하게 해주는 것이 import엘리먼트 이다.


    <import resource="설정파일 명"/>    


    bean 엘리먼트

        스프링 설정 파일에 클래스를 동록하려면 bean엘리먼트를 사용한다.
        이때 id와 class속성을 사용하하는데 id 속성은 생략이 가능하지만 class 속성은 생략이 불가하다.
        class속성에 클래스 등록할 때는 정확한 패키지 경로와 클래스 이름을 지정해야 한다.

    bean 엘리먼트 속성
        init-method 속성
            Servlet컨테이너는 web.xml파일에 등록된 servlet클래스의 객체를 생성할 때 디폴트 생성자만 인식한다.
            따라서 생성자로 servlet객체의 멤버변수를 초기화할 수 없다, 그래서 서블릿은 init메소드를 오버라이딩(재정의)하여 멤버변수를 초기화한다.

            스프링 컨테이너 역시 스프링 설정 파일에 등록된 클래스를 객체 생성할 때 디폴트 생성자를 호출한다.
            따라서 객체를 생성한 후 멤버변수 초기화 작업이 필요하다면,  servlet의 init같은 메소드가 필요하다.

            이를 위해 스프링에서는 bean엘리먼트에 init-method속성을 지원한다.
        -> 기능 : Bean이 생성된 후 콜백으로 호출되는 메서드를 선언합니다.

        destory-method 속성
            init-method와 마찬가지로 destory-method속성을 이용하여 컨테이너가 객체를 삭제하기 직전에 호출될 임의 메소드를 지정할 수 있다.
            destroy-method 속성으로 지정한 메소드는 해당 클래스 객체가 삭제되기 직전에 호출된다.

        lazy-init 속성
            applicationContext를 이용하여 컨테이너를 구동하면 즉시 로딩 방식으로 동작한다. 하지만 어떤 bean은 자주 사용하지 않으면서 메모리를
            차지하여 시스템에 부담을 준다. 그런 경우를 대비하여 lazy-init속성을 이용하요 즉시동작이 아닌 지연로딩방식으로 bean객체를 호출할 수 있다.

        scope 속성
            컨테이너가 생성한 bean을 어느 범위에서 사용할 수 있는지를 지정할 수 있다. 이를 scope속성을 사용한다.
            빈 스코프는 말 그대로 빈이 존재할 수 있는 범위를 뜻한다. 스프링은 다음과 같은 다양한 스코프를 지원한다.

            싱글톤(Singleton) : 기본 스코프, 스프링 컨테이너의 시작과 종료까지 유지되는 가장 넓은 범위의 스코프이다. -> 객체 하나만 생성
            프로토타입(Prototype) : 스프링 컨테이너는 프로토타입 빈의 생성과 의존관계 주입까지만 관여하고 더는 관리하지 않는 매우 짧은 범위의 스코프이다. -> 호출시 매번 객체 생성
            request : 웹 요청이 들어오고 나갈 때까지 유지되는 스코프
            session : 웹 세션이 생성되고 종료될 때까지 유지되는 스코프
            application : 웹의 서블릿 컨텍스트와 같은 범위로 유지되는 스코프
            -> https://code-lab1.tistory.com/186

# 4.1 의존성관리 (dependency injection)

    스프링 프레임워크의 가장 중요한 특징은 객체의 생성과 의존관계를 컨테이너가 자동으로 관리한다는 점이다.

    스프링은 loc를 다음 두 가지 형태로 지원한다.
    * dependency Lookup
        -> 컨테이너가 애플리케이션 운용에 필요한 객체를 생성하고 클라리언트는 컨테이너가 생성한 객체를 검색하여 사용하는 방식이다.
        -> 실제 애플리케이션 개발 과정에서는 사용하지 않으며 대부분은 dependency injection을 사용한다.
    * dependency injection
        -> 객체 사이의 의존관계를 스프링 설정 파일에 등록된 정보를 바탕으로 컨테이너가 자동으로 처리해준다.
        -> 따라서 의존성 설정을 바꾸고 싶을 때 프로그램 코드를 수정하지 않고 스프링 설정 파일 수정만을 변경사항을 적용할 수 있다.

        컨테이너가 직접 객체들 사이에 의존관계를 처리하는 것을 의미하며, 이것은 다시 setter메소드를 기반으로 하는 세터 인젝션과 생성자를 기반으로하는
        생성자 인젝션으로 나뉜다.

## 의존성 관계
    의존성관계란 객체와 객체의 결합 관계이다.
        -> 하나의 객체에서 다른 객체의 변수나 메소드를 이용해야 한다면 이용하려는 객체에 대한 객체 생서와 생성된 객체의 레퍼런스 정보가 필요하다.

# 4.2 생성자 인젝션 이용하기
     스프링 컨테이너는  xml 설정 파일에 등록된 클래스를 찾아서 객체 생성할 때 기본적으로 기본 생성자를 호출한다.
     하지만 컨테이너가 기본 생성자 말고 매개변수를 가지는 다른 생성자를 호출하도록 설정할 수 있는데, 이 기능을 이용하여 생성자 인젝션을 처리한다.
     생성자 인젝션을 사용하면 생정자의 매개변수로 의존관계에 있는 객체의 주소 정보를 전달할 수 있다.

    생성자 인젝션을 위해서는 클래스 bean등록 설정에서 시작태그와 종료태그 사이에 <constructor-arg>엘리먼트를 추가하고 
    생성자 인자로 전달할 객체의 아이디를 ref속성으로 참조한다.

	<bean id="tv" class="생성자클래스호출클래스경로" >
		<constructor-arg ref="생성자클래스객체id"></constructor-arg>
	</bean>
	
	<bean id="생성자클래스객체id" class="생성자클래스경로" />    

## 4.2.1 다중 변수 매팽
    생성자 인젝션에서 초기화해야 할 멤버변수가 여러 개이면, 여러 개의 값을 한꺼번에 전달할 수 있다.
    스프링 설정 파일에 <construcor-arg>엘리먼트를 추가하고자하는 매개변수의 개수만큼 추가하면 된다.


	<bean id="tv" class="생성자클래스호출클래스경로" >
		<constructor-arg ref="생성자클래스객체1id"></constructor-arg>
		<constructor-arg ref="생성자클래스객체2id"></constructor-arg>
	</bean>
	
	<bean id="생성자클래스객체1id" class="생성자클래스1경로" />    
	<bean id="생성자클래스객체2id" class="생성자클래스2경로" />    

    constructor-arg엘리먼트에서 ref뿐 아니라 value 속성을 사용하여 생성자 매개변수로 값을 전달할 수 있다.
    ref = bean에 등록된 다른 객체를 생성자로 보낼때
    value = 고정된 문자열이나 정수 같은 기본형을 생성자로 보낼따

    constructor-arg엘리먼트에서 index속성을 지원하여 몇 번째 매개변수로 매핑되는지 지정할 수 있다.


## 4.3 setter 인젝션 이용하기
    setter메소드를 홏ㄹ하여 의존성 주입을 처리한다.
    생성자 인젝션과 setter인젝션 어느것을 사용해도 상관없지만 코딩 컨벤션에서는 setter인젝션을 많이 사용한다.

    스프링 설정 파일에서는 constructor-arg엘리먼트 대신 property엘리먼트를 사용한다.

 	<bean id="tv" class="polymorphism.SamsungTV" >
 		<property name="speaker" ref="apple"></property>
 		<property name="price" value="2700000"></property>
 	</bean>
 
	<bean id="sony" class="polymorphism.SonySpeaker" />
	<bean id="apple" class="polymorphism.AppleSpeaker" />

    name속성값이 호출하고 싶은 메소드 이름이다.

### 4.3.2 p 네임스페이스 사용하기

    setter인젝션을 사용할 때 p 네임스페이스를 이용하면 좀 더 효율적으로 의존성 주입을 처리할 수 있다.

    p:변수명-ref="참조할 객체의 이름이나 아이디"
    p:변수명="설정할 값"

## 4.4 컬렉션 객체 설정
    컬렉션 객체를 이용하여 데이터 집합을 사용해야 하는 경우 컬렉션 객체에 의존성 주입을 하면 되는데 이를 위해서 스프링에서는
    컬렉션 매핑과 관련된 엘리먼트를 지원한다.

    list 타입 매핑 (setter injection 기준)
	<bean id="list" class="loc.collection.CollectionBean" >
		<property name="list">
			<list>
				<value>1</value>			
				<value>2</value>			
				<value>3</value>			
				<value>4</value>			
				<value>5</value>			
			</list>
		</property>
	</bean>

    set 타입 매핑
	<bean id="set" class="loc.collection.CollectionBean" >
		<property name="set">
			<set value-type="java.lang.String">
				<value>a</value>			
				<value>d</value>			
				<value>b</value>			
				<value>c</value>			
				<value>a</value>			
			</set>
		</property>
	</bean>

    map 타입 매핑
	<bean id="map" class="loc.collection.CollectionBean" >
		<property name="map">
			<map>
				<entry>
					<key><value>고길동</value></key>
					<value>고길동1</value>
				</entry>
				<entry>
					<key><value>마이콜</value></key>
					<value>마이콜1</value>
				</entry>
			</map>
		</property>
	</bean>

    properties 타입 매핑
	<bean id="properties" class="loc.collection.CollectionBean" >
		<property name="properties">
			<props>
				<prop key="korea">k</prop>
				<prop key="korea1">k1</prop>
			</props>
		</property>
	</bean>        


# 5 어노테이션 기반 설정

## 5.1 어노테이션 설정 기초

### 5.1.1 context네임스페이스 추가

    어노테이션 설정을 추가하려면 스프링 설정 파일의 루트엘리먼트인 beans에 context관련 네임스페이스와 스키마 문서의 위치를 등록해야 한다.

### 5.1.2 컴포넌트 스캔(component-scan) 설정 
    * https://wpunch2000.tistory.com/18
    * https://velog.io/@hyun-jii/%EC%8A%A4%ED%94%84%EB%A7%81-component-scan-%EA%B0%9C%EB%85%90-%EB%B0%8F-%EB%8F%99%EC%9E%91-%EA%B3%BC%EC%A0%95

    스프링 설정 파일에 애플리케이션에서 사용할 객체들을 <bean>등록하지 않고 자동으로생성하려면 
    <context:component-scan/>엘리먼트를 정의해야 한다. 해당 설정을 추가하면 스프링 컨테이너는 클래스 패스에 있는 클래스들을 스캔하여 
    @Component가 설정된 클래스들을 자동으로 객체 생성한다.

    base-package속성이란 해당 경로 패키지로 시작하는 모든 패키지를 스캔하여 빈 객체 생성한다
    ex) <context:component-scan base-package="베이스 패키지 경로" ></context:component-scan>


### 5.1.3 @Component

    <context:component-scan/>을 설정했으면 스프링 설정 파일에 클래스들을 일일이 bean엘리먼트로 등록할 필요가 없다.

    @Component 어노테이션을 클래스 선언부 위에 설정하면 끝난다.

    두 설정 모두 해당 클래스에 기본 생성자가 존재야만 컨테이너가 객체를 생성할 수 있다.
    만일 컨테이너가 생성한 객체를 요청하려면 @Component() 어노테이션사이에 객체명을 설정해야 한다.

    ex)
        // spring 컨테이너로부터 필요한 객체(tv)를 요청한다.
        TV tv = (TV)factory.getbean("tv");

        //xml설정
        <bean id="tv" class="polymorphism.LgTV" />
        //Annotation 설정
        @Component('tv')

## 5.2 의존성 주입 설정

### 5.2.1 의존성 주입 어노테이션

    스프링에서 의존성 주입을 지원하는 어노테이션으로는
    
    Autowired : 주로 변수 위에 설정하여 해당 타입의 객체를 찾아서 자동으로 할당한다.
    Qualifier  : 특정 객체의 이름을 이용하여 의존성 주입할 때 사용한다.
    inject : autowired와 동일한 기능을 제공한다.
    Resource : autowired, Qualifier의 기능을 결합한 어노테이션이다.

    autowired, qualifier 스프링에서 제공하지만, 나머지 어노테이션은 스프링에서 제공하지 않는다.


### 5.2.2 @Autowired 

@Autowired은 생성자나 메소드, 멤버변수 위에 모두 사용할 수 있다.
어디에 사용하든 결과가 같아서 상관없지만, 대부분은 멤버변수 위에 선언하여 사용한다.

스프링 컨테이너는 멤버변수 위에 붙은 Autowired를 확인하는 순간 해당 변수의 타입을 체크한다. 그리고 그 타입의 객체가 메모리에 존재하는지를 확인한 후에, 그 객체를 변수에 주입한다.

만약 Autowired가 붙은 객체가 메모리가 없다면 컨테이너가 'NoSuchBeanDefinitionException'을 발생시킨다.

객체가 메모리에 없으면 에러가 발생하므로 반드시 객체가 메모리에 생성되어 있어야 한다.

객체를 생성하려면 다음과 같이 두 가지 방법 중 하나를 처리해야 한다.

xml 설정
<bean id="sony" class="polymorphism.SonySpeaker" />

annotation 설정
@Component(id)

두 가지중 어떤 방법을 사용해도 상관이 없다. 
의존성 주입 대상이 되는 객체가 메모리에 생성만 되면 autowired에 의해서 컨테이너가 객체를 변수에 자동으로 할당한다.


### 5.2.3 @Qualifier

 autowired의 문제점은 의존성 주입 대상이 되는 객체가 두 개 이상일 때 발생한다.
 만약 동일한 의존성을 가지는 객체가 2개 이상 메모리에 할당되어 있다면 컨테이너는 어떤 객체를 할당할지 판단을 할 수 없어서 에러가 발생한다.

이러한 문제를 해결하기 위해 스프링은 Qualifier 어노테이션을 제공한다.

autowired 어노테이션 아래 qualifier어노테이션을 선언하며 할당을 받고자하는 객체의 아이디를 선언한다.

	@Autowired
	@Qualifier(할당받고자하는객체ID)


### 5.2.4 @Resource

    Autowired는 변수의 타입을 기준으로 객체를 검색하여 의조성 주입을 처리했지만 Resource는 객체의 이름을 이용하여 의존성 주입을 처리한다.

    Resource는 name속성을 사용할 수 있어서(Qualifier 기능), 스프링 컨테이너가 해당 이름으로 생성된 객체를 검색하여 의존성 주입을 처리한다.

    @Resource(name = "apple")

    apple이라는 이름을 가진 객체에 의존성 주입을 한다.


### 5.2.5 어노테이션과 xml 설정 병행하여 사용하기

    xml방식의 장점은 자바소스를 수정하지 않고 xml파일의 설정만 변경하면 실행이 되어 유지보수가 편하다.
    하지만 자바 소스에 의존관계와 관련된 어떤 메타데이터도 없으므로 xml파일을 해석해야만 무슨 객체가 의존성 주입되는지를 확인할 수 있다.

    어노테이션방식은 xml설정에 대한 부담도 없으며 의존관계에 대한 정보가 자바 소스에 들어있어 사용하기는 편하다. 하지만 의존성 주입할 객체의 이름이 자바 소스에 명시가 되어있으므로 자바 소스를 수정하지 않고는 객체를 수정할 수 없다
    .

    이러한 문제들 때문에 어노테이션방식과 xml방식을 조합하여 사용을 한다.

    ex)
    의존성을 주입받을 클래스에 어노테이션방식 선언
    @Autowired

    어떠한 객체를 선언받을것에 대한 선언을 어노테이션 방식이 아닌 xml방식을 이용
        <context:component-scan base-package="polymorphism" ></context:component-scan>
        <bean id="sony" class="polymorphism.SonySpeaker" />

    해당 방식을 조합하여 사용할 경우 자바코드 없이 수정이 가능하다.

    변경되지 않는 객체는 어노테이션으로 설정하여 사용하고, 변경될 가능성이 있는 객체는 xml설정을 이용한다.

## 5.3 추가 어노테이션

    Controller 클래스는 사용자의 요청을 제어한다.
    ServiceImpl 클래스는 실질적인 비즈니스 로직을 처리한다.
    DAO(Data Access Object) 클래스는 데이터베이스 연동을 담당한다.



# 6 비즈니스 컴포넌트 실습

## 6.1 BoardService 컴포넌트 구조

    일반적으로 비즈니스 컴포넌트는 네 개의 자바 파일로 구성된다.

## 6.2 Value Object 클래스 작성

    VO(Value Object) 클래스는 레이어와 레이어 사이에서 관련된 데이터를 한꺼번에 주고받을 목적으로 사용하는 클래스이다.

    DTO(Data Transfer Object)라 하기도 하는데, 데이터 전달을 목적으로 사용하는 객체이므로 같은 의미의 용어이다.


## 6.3 DAO 클래스 작성
    DAO 클래스는 데이터베이스 연동을 담당하는 클래스이다.

    디비 드라이버는 pom.xml에서 dependencies를 추가한다.

    dao클래스에는 repository어노테이션을 추가한다. component를 사용해도 문제는 없다.

    
    


















