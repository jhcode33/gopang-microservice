# itemserver 이미지 생성
docker build -t gopang/itemserver:0.0.1-SNAPSHOT --build-arg JAR_FILE=build/libs/itemserver-0.0.1-SNAPSHOT.jar ./itemserver