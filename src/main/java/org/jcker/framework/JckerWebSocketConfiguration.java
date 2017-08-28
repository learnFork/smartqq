package org.jcker.framework;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;

@Configuration
@EnableWebSocket
public class JckerWebSocketConfiguration
  implements WebSocketConfigurer
{
  public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry)
  {
    webSocketHandlerRegistry.addHandler(jckerWebSocketHandler(), new String[] { "/websocket/qqmessage" });
  }

  @Bean
  public JckerWebSocketHandler jckerWebSocketHandler() {
    return new JckerWebSocketHandler();
  }
}
