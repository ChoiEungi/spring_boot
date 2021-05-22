# 김영한 강사님 스프링 강의

***



```java
@Controller
public class HelloController {

    @GetMapping("hello") // hello라는 주소로 mapping ~/hello
    public String hello(Model model){
        model.addAttribute("data", "hello!!");
        return "hello"; //라우팅 주소 연결 templete의 hello.html을 연결  
    }
}
```



### static content

![image-20210522024447822](C:\Users\Choi Eun Gi\AppData\Roaming\Typora\typora-user-images\image-20210522024447822.png)



### MVC 탬플릿 엔진

timeleaf 장점: html 파일로도 소스 관찰 가능



### APi

![image-20210522050232941](C:\Users\Choi Eun Gi\AppData\Roaming\Typora\typora-user-images\image-20210522050232941.png)

- 실무에서도 비슷한 구조로 사용

```java
@Controller
public class HelloController {
    /* ~~ */
    @GetMapping("hello-api")
    @ResponseBody
    public Hello helloApi(@RequestParam("name") String name) {
        Hello hello = new Hello();
        hello.setName(name);
        return hello;
    }
    static class Hello {
        private String name;
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }

}
// json 형식으로 변환 
```



### 회원 관리 예제

### DB  테스트 

```java
@AfterEach
public void afterEach() { // 각 테스트가 독립이여야 하는데 객체를 만들면 다른 테스트에 영향을 준다. -> afterEach라는 매소드로 저장된 객체를 제거 (DB 저장 정보 제거)
    repository.clearStore();
}
```



### 멤버 테스트

```java
@BeforeEach
public void beforeEach() { //각 테스트 실행 전에 호출 
    memberRepository = new MemoryMemberRepository();
    memberService = new MemberService(memberRepository);
}
```



### DI, 스프링과 빈

- **new(여러 인스턴스)를 사용하지 않고 하나의 객체를 여러 클래스에서 사용할 수 있도록 의존관계를 설정해야한다.**

**Bean 등록법**

- 대부분 싱글톤으로 등록(유일하게 하나만 등록해서 공유)

1. 컴포넌트 스캔과 자동 의존관계 설정 -> `@Autowired` 사용 

   - HelloSpringApplication의 패키지 이외에는 빈을 등록하지 않는다. (component scan)

2. 자바 코드로 직접 스프링 빈 등록

   ```java
   // MemberService와 MemberRepository의 @Service, @Repository, @Autowired annotation을 제거
   @Configuration
   public class SpringConfig {
   
       @Bean
       public MemberService memberService(){
           return new MemberService(memberRepository());
       }
   
       @Bean
       public MemberRepository memberRepository() {
           return new MemoryMemberRepository();
       }
   
   
   }
   ```













## 용어 정리

### 스프링 컨테이너 

스프링의 빈을 생성하고 관리하는 컨테이너를 가지고 있다. 이를 통해서 스프링의 주개념인 IOC 나 AOP에 대해서 관리하곤 한다.



### IOC

- Spring에서 사용되는 IoC란 객체가 내부적으로 조작할 객체를 직접 생성하지 않고 외부로부터 주입받는 기법을 의미한다. 이때 객체를 외부로부터 주입해주는 작업을 DI(의존성 주입)이라고 부른다.

- IoC Container는 오브젝트의 생성과 관계설정, 사용, 제거 등의 작업을 대신 해준다하여 붙여진 이름이다. 이때, IoC Container에 의해 관리되는 오브젝트들은 Bean 이라고 부른다. IoC Container는 Bean을 저장한다고 하여, BeanFactory 라고도 불린다.

-  BeanFactory는 하나의 인터페이스이며, Application Context는 BeanFactory의 구현체를 상속받고 있는 인터페이스이다. 실제로 스프링에서 IoC Container 라고 불리는 것은 Application Context의 구현체이다.

