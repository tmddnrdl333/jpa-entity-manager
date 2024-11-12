# 만들면서 배우는 JPA

## 1. SQL 쿼리 빌더

### 1-1. Reflection

- [x] 클래스 정보 출력
- [x] test로 시작하는 메소드 실행
- [x] @PrintView 애노테이션 메소드 실행
- [x] private field에 값 할당
- [x] 인자를 가진 생성자의 인스턴스 생성

### 1-2. QueryBuilder DDL

- [x] @Entity 애너테이션으로 테이블 스캔하는 스캐너 구현
- [x] DDL Query를 구성하는 열 정보, 제약조건 정보 컴포넌트화
- [x] DDL Query 컴포넌트 조합하는 Query Builder 구현
- [x] 테이블 스캐너에서 DDL Query 빌드하는 로직 구현
- [x] @Id, @GeneratedValue, @Column(name, nullable), @Transient 적용
- [x] CREATE, DROP 쿼리 빌더 구현

### 1-3. QueryBuilder DML

- [x] Insert query의 columnsClause, valueClause 구현
- [x] InsertQueryBuilder 구현
- [x] SelectQueryBuilder 구현
- [x] findAll 기능 구현
- [x] findById 기능 구현
- [x] deleteQueryBuilder 구현

### 1-4. Simple Entity Object

- [x] EntityManager 인터페이스 및 구현체 작성
- [x] find 구현
- [x] persist 구현
- [x] remove 구현
- [x] update 구현

## 2. Entity 구현

### 2-1. EntityPersister 구현

- [x] PersistenceContext, EntityManager 추상화 설계 및 인터페이스 작성
- [x] PersistenceContext 구현
- [x] EntityPersister 구현
  - [x] 관리할 매핑 정보 작성
  - [x] update 구현
  - [x] insert 구현
  - [x] delete 구현

### 2-2. EntityLoader 구현

- [x] EntityLoader 구현

### 2-3. Persistence Context, Dirty Check

- [ ] data snapshot 구현
- [ ] dirty check 로 변경 사항 쿼리 생성 구현
