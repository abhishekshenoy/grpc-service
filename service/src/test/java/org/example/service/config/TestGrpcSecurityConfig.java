package org.example.service.config;

import lombok.extern.slf4j.Slf4j;
import org.lognet.springboot.grpc.security.EnableGrpcSecurity;
import org.lognet.springboot.grpc.security.GrpcSecurity;
import org.lognet.springboot.grpc.security.GrpcSecurityConfigurerAdapter;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Arrays;

@Slf4j
@TestConfiguration
@EnableGrpcSecurity
public class TestGrpcSecurityConfig extends GrpcSecurityConfigurerAdapter {

  @Override
  public void configure(GrpcSecurity builder) throws Exception {
    builder
        .authorizeRequests()
        .withSecuredAnnotation()
        .authenticationProvider(
            new AuthenticationProvider() {
              @Override
              public Authentication authenticate(Authentication authentication)
                  throws AuthenticationException {
                UsernamePasswordAuthenticationToken user =
                    (UsernamePasswordAuthenticationToken) authentication;
                return getSSOToken(user);
              }

              @Override
              public boolean supports(Class<?> authentication) {
                return UsernamePasswordAuthenticationToken.class.isAssignableFrom(authentication);
              }
            });
  }

  private SSOToken getSSOToken(UsernamePasswordAuthenticationToken user){
    SSOToken token=null;
    String name=(String)user.getPrincipal();
    switch (name){
      case "abhishek": token=SSOToken.builder()
        .principalId("e58aeeae-e97f-4818-9866-db7f55e69866")
        .clientId("752a6c99-e040-4e97-a035-97a0f406c5b3")
        .consumerId("752a6c99-e040-4e97-a035-97a0f406c5b3")
        .issueTime(1603544744000L)
        .expiryTime(2603544744000L)
        .state("SUCCESS")
        .issuer("https://dummy-id-sso/")
        .username(user.getName())
        .loginId(user.getName())
        .email(user.getName())
        .accessToken("eyJraWQiOiJjYmQ2NzNhMC05Y2M2LTQ1NzMtOTM4NC1iMDk1Z")
        .valid(true)
        .authorities(Arrays.asList(new SimpleGrantedAuthority("ROLE_EDITOR")))
        .build();
        break;
      case "anuj":
        token=SSOToken.builder()
          .principalId("e58aeeae-e97f-4818-9866-db7f55e69866")
          .clientId("752a6c99-e040-4e97-a035-97a0f406c5b3")
          .consumerId("752a6c99-e040-4e97-a035-97a0f406c5b3")
          .issueTime(1603544744000L)
          .expiryTime(2603544744000L)
          .state("SUCCESS")
          .issuer("https://dummy-id-sso/")
          .username(user.getName())
          .loginId(user.getName())
          .email(user.getName())
          .accessToken("eyJraWQiOiJjYmQ2NzNhMC05Y2M2LTQ1NzMtOTM4NC1iMDk1Z")
          .valid(true)
          .build();
        break;
      default :
        token=SSOToken.builder()
          .principalId("e58aeeae-e97f-4818-9866-db7f55e69866")
          .clientId("752a6c99-e040-4e97-a035-97a0f406c5b3")
          .consumerId("752a6c99-e040-4e97-a035-97a0f406c5b3")
          .issueTime(1603544744000L)
          .expiryTime(2603544744000L)
          .state("FAILED")
          .issuer("https://dummy-id-sso/")
          .username(user.getName())
          .loginId(user.getName())
          .email(user.getName())
          .accessToken("eyJraWQiOiJjYmQ2NzNhMC05Y2M2LTQ1NzMtOTM4NC1iMDk1Z")
          .valid(false)
          .build();
        break;
    }
    return token;
  }
}
