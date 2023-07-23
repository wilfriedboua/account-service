package org.sid.accountserviceaxonnew.configuration;


import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author Mark Paluch
 */
@ConfigurationProperties("account-service-new")
public class MyConfiguration {

  private String username;

  public String getUsername() {
    return username;
  }

  
}