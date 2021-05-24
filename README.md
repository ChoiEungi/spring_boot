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
- 의존관계가 동적으로 변하는 경우는 적응므로 생성자 주입을 주로 사용한다..

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

### 웹 MVC

컨트롤러가 정적 파일보다 우선순위가 높다. -> home에서 GetMapping("/")로 된 home.html이 index.html보다 우선순위가 높다.

**Form Controller**

```java
package hello.hellospring.controller;

public class MemberForm {
    private String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
```

- post시 postmapping이 호출된다. html에서 name에 해당하는 부분의 정보를 받게됨.

```java
@PostMapping("/members/new") //method ="post"에 상응돼 post시 호출
public String create(MemberForm form){
    Member member = new Member();
    member.setName(form.getName());

    //System.out.println("member"+member.getName());
    memberService.join(member);

    return "redirect:/";
}
```



- member라는 domain에서 setter로 private값인 name으로 설정. 이후 join method를 이용해 db에 저장 



### DB

`@SpringBootTest `: 스프링 컨테이너와 테스트를 함께 실행한다.
`@Transactional `: 테스트 케이스에 이 애노테이션이 있으면, 테스트 시작 전에 트랜잭션을 시작하고,
테스트 완료 후에 항상 롤백한다. 이렇게 하면 DB에 데이터가 남지 않으므로 다음 테스트에 영향을 주지
않는다.

JDBC Template 메뉴얼을 통해 사용법 확인 가능.

### JPA

`show-sql` : JPA가 생성하는 SQL을 출력한다.
`ddl-auto `: JPA는 테이블을 자동으로 생성하는 기능을 제공하는데 none 를 사용하면 해당 기능을 끈다.
`create` 를 사용하면 엔티티 정보를 바탕으로 테이블도 직접 생성해준다. 



### AOP

Problem: 모든 메소드의 호출 시간을 측정하고 싶다면?

공통 관심 사항(cross-cutting concern) vs 핵심 관심 사항(core concern) 분리

build에서 `implementation 'org.springframework.boot:spring-boot-starter-aop'` 사용해야함 

```java
package hello.hellospring.aop;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

// bean에 등록
@Aspect
@Component
public class TimeTraceAop {

    @Around("execution(* hello.hellospring..*(..))") //원하는 적용 대상을 선택할 수 있다.
    public Object execute(ProceedingJoinPoint joinPoint) throws Throwable {
        long start = System.currentTimeMillis();
        System.out.println("START: " + joinPoint.toString());
        try {
            return joinPoint.proceed();
        } finally {
            long finish = System.currentTimeMillis();
            long timeMs = finish - start;
            System.out.println("END: " + joinPoint.toString()+ " " + timeMs +
                    "ms");
        }
    }
}
```



## 용어 정리

### 스프링 컨테이너 

스프링의 빈을 생성하고 관리하는 컨테이너를 가지고 있다. 이를 통해서 스프링의 주개념인 IOC 나 AOP에 대해서 관리하곤 한다.

alt + insert: getter and setter

### IOC

- Spring에서 사용되는 IoC란 객체가 내부적으로 조작할 객체를 직접 생성하지 않고 외부로부터 주입받는 기법을 의미한다. 이때 객체를 외부로부터 주입해주는 작업을 DI(의존성 주입)이라고 부른다.
- IoC Container는 오브젝트의 생성과 관계설정, 사용, 제거 등의 작업을 대신 해준다하여 붙여진 이름이다. 이때, IoC Container에 의해 관리되는 오브젝트들은 Bean 이라고 부른다. IoC Container는 Bean을 저장한다고 하여, BeanFactory 라고도 불린다.
-  BeanFactory는 하나의 인터페이스이며, Application Context는 BeanFactory의 구현체를 상속받고 있는 인터페이스이다. 실제로 스프링에서 IoC Container 라고 불리는 것은 Application Context의 구현체이다.



### Model

- Spring에서는 직접 모델을 생성하지 않고 파라미터로 선언만 해주면 된다. 이후 `addAttribute("attriubtename", object)`를 해줘 orm을 사용 
- 





## Reference

https://velog.io/@msriver/Spring-Model-%EA%B0%9D%EC%B2%B4