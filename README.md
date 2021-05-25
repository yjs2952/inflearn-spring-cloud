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