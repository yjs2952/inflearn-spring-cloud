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

![spring cloud config](./img/01 spring cloud config.png)

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

### Actuator bus-refresh Endpoint
* 분산 시스템의 노드를 경량 메시지 브로커와 연결
* 상태 및 구성에 대한 변경 사항을 연결된 노드에게 전달(Broadcast)