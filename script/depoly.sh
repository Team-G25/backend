#!/usr/bin/env bash

REPOSITORY=/home/ubuntu/app
PORT=8088

echo "> 현재 구동 중인 애플리케이션 pid 확인"
CURRENT_PID=$(sudo lsof -t -i:$PORT)

echo "현재 구동 중인 애플리케이션 pid: $CURRENT_PID"

if [ -z "$CURRENT_PID" ]; then
  echo "현재 구동 중인 애플리케이션이 없으므로 종료하지 않습니다."
else
  echo "> kill -15 $CURRENT_PID"
  kill -15 $CURRENT_PID
  sleep 6
fi

echo "> 새 애플리케이션 배포"

# 파일명이 고정된 경우
JAR_NAME=JAR_NAME=$REPOSITORY/g25Server.jar

# 파일명이 바뀔 수 있는 경우 (ex. 빌드마다 g25Server-1.0.2.jar 등)
# JAR_NAME=$(ls -tr $REPOSITORY/*.jar | tail -n 1)

echo "> JAR NAME: $JAR_NAME"
echo "> $JAR_NAME 에 실행권한 추가"
chmod +x $JAR_NAME

echo "> $JAR_NAME 실행"
nohup java -Duser.timezone=Asia/Seoul \
  -jar $JAR_NAME \
  --spring.config.location=file:$REPOSITORY/application.yml \
  >> $REPOSITORY/nohup.out 2>&1 &