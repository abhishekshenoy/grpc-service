package org.example.service.security;

import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.security.EnableGrpcSecurity;
import org.lognet.springboot.grpc.security.GrpcSecurity;
import org.lognet.springboot.grpc.security.GrpcSecurityConfigurerAdapter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;

@Slf4j
@Configuration
@EnableGrpcSecurity
public class GrpcSecurityConfig extends GrpcSecurityConfigurerAdapter {

  @Bean
  AuthenticationProvider getWalmartSSOAuthenticationProvider() {
    return new AuthenticationProvider() {
      @Override
      public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        return authentication;
      }

      @Override
      public boolean supports(Class<?> authentication) {
        return BearerTokenAuthenticationToken.class.isAssignableFrom(authentication);
      }
    };
  }

  @Override
  public void configure(GrpcSecurity builder) throws Exception {
    builder.authorizeRequests()
      .withSecuredAnnotation()
      .authenticationProvider(getWalmartSSOAuthenticationProvider());
  }
}