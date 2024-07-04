# 1. 베이스 이미지 선택
FROM openjdk:17-jdk-slim

# 2. 애플리케이션을 실행할 디렉토리 생성
WORKDIR /app

# 3. Gradle 빌드 파일 및 소스 복사
COPY build.gradle settings.gradle /app/
COPY src /app/src

# 4. Gradle Wrapper 복사 및 빌드 수행
COPY gradlew /app/gradlew
COPY gradle /app/gradle
RUN chmod +x gradlew
RUN ./gradlew build -x test

# 5. 빌드된 JAR 파일을 컨테이너로 복사
COPY build/libs/*.jar app.jar

# 6. 애플리케이션 실행
ENTRYPOINT ["java", "-jar", "app.jar"]

# 7. /fileUpload를 가리키는 볼륨 추가
VOLUME /fileUpload

# 8. 애플리케이션이 사용할 포트 노출
EXPOSE 8080