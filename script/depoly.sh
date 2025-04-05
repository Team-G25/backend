#!/usr/bin/env bash

APP_DIR=/home/ubuntu/app
JAR_NAME=g25Server.jar
PORT=8088

CURRENT_PID=$(sudo lsof -t -i:$PORT)

echo "현재 구동 중인 애플리케이션 pid: $CURRENT_PID"

if [ -n "$PID" ]; then
  echo ">>> kill -15 $PID"
  kill -15 $PID
  sleep 5
fi

echo ">>> Starting $JAR_NAME"
cd $APP_DIR
chmod +x $JAR_NAME
nohup java -jar -Duser.timezone=Asia/Seoul $JAR_NAME > nohup.out 2>&1 &