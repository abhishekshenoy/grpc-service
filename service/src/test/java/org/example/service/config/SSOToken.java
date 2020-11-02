package org.example.service.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SSOToken implements Authentication {

  private static final long serialVersionUID = 1L;

  private String principalId, clientId, consumerId;
  private Long issueTime, expiryTime;
  private String state, issuer, username, loginId, email;
  private boolean valid;
  private String accessToken;

  private Collection<? extends GrantedAuthority> authorities;

  @Override
  public String getName() {
    return this.username;
  }

  @Override
  public Object getCredentials() {
    return this.accessToken;
  }

  @Override
  public Object getDetails() {
    return this.issuer;
  }

  @Override
  public Object getPrincipal() {
    return this.principalId;
  }

  @Override
  public boolean isAuthenticated() {
    return this.valid;
  }

  @Override
  public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
    this.valid = isAuthenticated;
  }
}
