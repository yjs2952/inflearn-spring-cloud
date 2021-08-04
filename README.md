### Spring Cloud Netflix Eureka
* 모든 마이크로스 서비스는 Spring Cloud Netflix Eureka 에 등록한다.   
* Service Discovery 
    - 외부에서 다른 서비스들이 마이크로 서비스를 검색하기 위해서 사용하는 개념 (전화번호부 같은 역할)
    - Eureka 가 하는 역할
* 각 마이크로 서비스가 자신의 위치 정보를 Eureka 서버에 등록해서 사용한다.
    - 클라이언트는 자신이 필요한 요청 정보를 API Gateway 에 전달한다.
    - 요청 정보는 Service Discovery 에 전달이 되어 필요한 마이크로 서비스의 위치 정보(ip address, port 등)를 반환한다.

### API Gateway Service
* 인증 및 권한 부여
* 마이크로 서비스 검색 통합
* 응답 캐싱
* 정책, 회로 차단기 및 QoS 다시 시도
* 속도 제한
* 부하 분산
* 로깅, 추적, 상관 관계
* 헤더, 쿼리 문자열 및 청구 변환
* IP 허용 목록에 추가

### Netflix Ribbon
* Spring Cloud 에서의 MSA 간의 통신
    1. RestTemplate
    2. Feign Client
* Ribbon: **Client side** Load Balancer
    - 서비스 이름으로 호출
    - Health Check
    - 비동기 지원이 안됨
    - Spring Boot 2.4 버전에서 maintenance 상태

### Netflix Zuul 
* Routing, Api Gateway 역할
* Spring Boot 2.4 버전에서 maintenance 상태
    - zuul 2.0 으로 업데이트 되면서 비동기를 지원하긴 하지만    
      다른 Spring Boot 라이브러리와의 호환성 문제로 Spring Cloud Gateway 로 대체
      
### Spring Cloud Gateway 
#### Filter
![spring cloud Gateway Filter](./img/03%20spring%20cloud%20gateway%20filter.png)

### Eureka 연동
![spring cloud Gateway Filter](./img/04%20spring%20cloud%20gateway%20-%20Eureka.png)

### port 번호 변경하는 방법
1. Edit Configurations -> VM Option -> -Dserver.port=9001
2. 터미널에서 mvn (또는 프로젝트 루트 경로에서 ./mvnw) spring-boot:run -Dspring-boot-run.jvmArguments='-Dserver.port=9001'
3. mvn clean compile package   
   java -jar -Dserver.port=9001 **./target/user-service-0.0.1-SNAPSHOT.jar (jar 파일 경로)** 
   
### Users Microservice - Security
* http.headers().frameOptions().disable();
    - h2-console 접근하기 위한 옵션
* BCryptPasswordEncoder
    - Password 를 해싱하기 위해 Bcrypt 알고리즘 사용
    - 랜덤 Salt 를 부여하여 여러번 Hash 를 적용한 암호화 방식 
    
### Spring Cloud Config
* 분산 시스템에서 서버, 클라이언트 구성에 필요한 설정 정보(application.yml)를 외부 시스템에서 관리
* 하나의 중앙화 된 저장소에서 구성요소 관리 가능
* 각 서비스를 다시 빌드하지 않고, 바로 적응 가능
* 애플리케이션 배포 파이프라인을 통해 DEV-UAT-PROD 환경에 맞는 구성 정보 사용

![spring cloud config](./img/01%20spring%20cloud%20config.png)

* 각 마이크로 서비스에서 설정정보 가져올 시
    - bootstrap.xml 을 설정하면 application.xml 보다 높은 우선순위로 설정값을 가져올 수 있다.
        + 단 spring-cloud-starter-bootstrap 의존성이 있어야 한다.
    - profile 을 native 로 설정할 시 native 로 설정한 경로에서 정보를 가져오고    
      profile 설정이 없는 경우 github 경로가 설정되어 있다면 native 경로를 무시한다

### Spring Cloud Bus
* 분산 시스템의 노드(Microservice)를 경량 메시지 브로커(RabbitMQ)와 연결
    + 상태 및 구성에 대한 변경 사항을 연결된 노드에게 전달(Broadcast)
* AMQP(Advanced Message Queuing Protocol) 메시지 지향 미들웨어를 위한 개방형 표준 응용 계층 프로토콜
    + 메시지 지향, 큐잉, 라우팅(P2P, Publisher-Subscriber), 신뢰성, 보안 
    + Erlang, RabbitMQ 에서 사용
* Kafka 프로젝트
    + Apache Software Foundation 이 Scalar 언어로 개발한 오픈소스 메시지 브로커 프로젝트
    + 분산형 스트리밍 플랫폼
    + 대용량의 데이터를 처리 가능한 메시지 시스템
* RabbitMQ VS Kafka
    + RabbitMQ  
        - 메시지 브로커
        - 초당 20+ 메시지를 소비자에게 전달
        - 메시지 전달 보장, 시스템 간 메시지 전달
        - 브로커, 소비자 중심
    + Kafka
        - 초당 100k+ 이상의 이벤트 처리
        - Pub/Sub, Topic 에 메시지 전달
        - Ack 를 기다리지 않고 전달 가능
        - 생산자 중심
    
![Kafka VS RabbitMQ](./img/02%20spring%20cloud%20bus%20kafka%20vs%20rabbitmq.png)

* rabbitmq 설치
    + brew install rabbitmq
    + export PATH=$PATH:/usr/local/sbin
    + rabbitmq-server

### Actuator bus-refresh Endpoint
* 분산 시스템의 노드를 경량 메시지 브로커와 연결
* 상태 및 구성에 대한 변경 사항을 연결된 노드에게 전달(Broadcast)

### RabbitMQ 설치 - Windows 10
1. Erlang 설치
    + https://www.erlang.org/downloads/23.1
    + 설치 후 환경변수 등록
        - C:\Program Files\erl-23.1
2. RabbitMQ 설치
    + https://www.rabbitmq.com/download.html

### Encryption types
* Symmetric Encryption (shared)
    - Using the same key
* Asymmetric Encryption (RSA Keypair)
    - Private and Public Key
    - Using Java keytool

### Java Cryptography Extension (JCE)
* JCE 설치
    - https://www.oracle.com/java/technologies/javase-jce-all-downloads.html
    - security 폴더에 복사
* Windows
    - Oracle JDK : {user.home}\Program Files\Java\jdk-13.0.2\conf\security
* MacOS
    - Oracle JDK : {user.home}/Library/Java/JavaVirtualMachines/jdk1.8.0_191.jdk/Contents/Home/jre/lib/security
    - Open JDK : {user.home}/Library/Java/JavaVirtualMachines/openjdk-14.0.2/Contents/Home/conf/security

### 비대칭 키를 이용한 암호화
* keytool 시용
    - private key 생성 : keytool -genkeypair -alias apiEncryptionKey -keyalg RSA -dname "CN=Jason Yoon, OU=API Development, O=joneconsulting.co.kr, L=Seoul, C=KR" -keypass "test1234" -keystore apiEncryptionKey.jks -storepass "test1234"
    - private key 에서 인증서 추출 : keytool -export -alias apiEncryptionKey -keystore apiEncryptionKey.jks -rfc -file trustServer.cer
    - 인증서로 public key 생성 : keytool -import -alias trustServer -file trustServer.cer -keystore publicKey.jks
    - key 정보 보기 : keytool -list -keystore apiEncryptionKey.jks -v

### Feign Web Service Client
* FeignClient -> HTTP Client
    - REST Call 을 추상화 한 Spring Cloud Netflix 라이브러리
* 사용방법
    - 호출하려는 HTTP Endpoint 에 대한 interface 를 생성
    - @FeignClient 선언
* Load balanced 지원

### Apache Kafka
* Apache Software Foundation 의 Scalar 언어로 된 오픈 소스 메시지 브로커 프로젝트
    - Open Source Message Broker Project
* 링크드인(Linked-in)에서 개발, 2011년 오픈 소스화
    - 2014년 11월 링크드인에서 kafka 를 개발하던 엔지니어들이 kafka 개발에 집중하기 위해 confluent 라는 회사 창립
* 실시간 데이터 피드를 관리하기 위해 통일된 높은 처리량, 낮은 지연 시간을 지닌 플랫폼 제공
* Apple, Netflix, Shopify, Yelp, Kakao, New York Times, Cisco, Ebay, Paypal, Hyperledger Fabric, Uber, Salesforce.com 등이 사용

#### 카프카 사용 전
![Apache kafka 사용 전](./img/05%20Apache%20kafka%20사용%20전.png)
#### 카프카 탄생 배경
* 모든 시스템으로 데이터를 실시간으로 전송하여 처리할 수 있는 시스템 필요
* 데이터가 많아지더라도 확장이 용이한 시스템 필요

#### 카프카 데이터 처리 흐름
![Apache kafka 데이터 처리 흐름](./img/06%20Apache%20kafka%20데이터%20처리%20흐름.png)

#### Kafka Broker
* 실행 된 kafka 애플리케이션 서버
* 3대 이상의 broker cluster 구성
* zookeeper 연동
    - 역할: 메타데이터(Broker ID, Controller ID 등) 저장
    - Controller 정보 저장
      ![Apache kafka 데이터 처리 흐름](./img/07%20Apache%20kafka%20-%20zookeeper.png)
    - Broker 들을 중재
* n개 Broker 중 1대는 Controller 기능 수행
    - Controller 역할
        + 각 Broker 에게 담당 파티션 할당 수행
        + Broker 정상 동작 모니터링 관리

    