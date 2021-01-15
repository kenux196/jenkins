# JPA SHOP 샘플
ORM JPA 책 예제임.

1. Spring framework + 순수 JPA
2. Spring Framework + Spring Data JPA

* Spring Data JPA
  * Spring Framework에서 JPA를 편리하게 사용할 수 있도록 지원하는 프로젝트
  * CRUD를 처리하기 위한 공통 인터페이스 제공
  * Repository 개발할 때, 인터페이스만 작성하면 Spring Data JPA가 구현체를 동적으로 생성해서 주입.

### 참고 키워드
* 도메인 모델 패턴
  * 엔티티가 비지니스 로직을 가지고 객체지향의 특성을 적극 활용하는 방식
  * 서비스 계층은 단순히 엔티티에 필요한 요청을 위임하는 역할만 한다.
* 트랜잭션 스크립트 패턴
  * 엔티티에는 비지니스 로직이 거의 없고, 서비스 계층에서 대부분의 비지니스 로직을 처리하는 방식