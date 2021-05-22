# 김영한 강사님 스프링 강의

1. 자바 코드로 직접 스프링 빈 등록

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



