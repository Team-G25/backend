package com.g25.mailer.log;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.StackTraceElementProxy;
import ch.qos.logback.core.AppenderBase;
import ch.qos.logback.classic.spi.IThrowableProxy;
import org.springframework.beans.factory.annotation.Value;

import java.text.SimpleDateFormat;
import java.util.Date;

public class DiscordWebhookAppender  extends AppenderBase<ILoggingEvent> {

    @Value("${DISCORD_WEBHOOK_URL}")
    private String webhookUrl;

    @Override
    protected void append(ILoggingEvent event) {
        if (event.getLevel().isGreaterOrEqual(ch.qos.logback.classic.Level.ERROR)) {

            String timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(event.getTimeStamp()));
            String loggerName = event.getLoggerName();
            String message = escapeJson(event.getFormattedMessage());
            String stackTrace = extractStackTrace(event.getThrowableProxy());

            String payload = """
                    {
                      "username": "Server Alert Bot",
                      "embeds": [
                        {
                          "title": "🚨 서버 에러 발생",
                          "description": "%s%s",
                          "color": 16711680,
                          "fields": [
                            {
                              "name": "📍 클래스",
                              "value": "%s",
                              "inline": false
                            },
                            {
                              "name": "⏰ 발생 시간",
                              "value": "%s",
                              "inline": true
                            },
                            {
                              "name": "🖥 서버",
                              "value": "prod-ec2-01",
                              "inline": true
                            }
                          ]
                        }
                      ]
                    }
                    """.formatted(message, stackTrace, loggerName, timestamp);

        }
    }


    private String escapeJson(String s) {
        if (s == null) return "";
        return s.replace("\"", "\\\"").replace("\n", "\\n").replace("\r", "");
    }

    private String extractStackTrace(IThrowableProxy throwableProxy) {
        if (throwableProxy == null) return "";

        StringBuilder sb = new StringBuilder("\\n```");
        sb.append(throwableProxy.getClassName()).append(": ").append(throwableProxy.getMessage()).append("\\n");
        StackTraceElementProxy[] stackTraceElements = throwableProxy.getStackTraceElementProxyArray();
        int maxLines = Math.min(5, stackTraceElements.length); // 최대 5줄만 전송
        for (int i = 0; i < maxLines; i++) {
            sb.append("    at ").append(stackTraceElements[i].getSTEAsString()).append("\\n");
        }
        sb.append("```");
        return sb.toString();
    }
}
