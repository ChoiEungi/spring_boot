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



### DI

정리할 것.