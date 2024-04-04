package org.ait.project.guideline.example.config.security.filter;

import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.security.SignatureException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.ait.project.guideline.example.modules.permission.service.core.UserSessionService;
import org.ait.project.guideline.example.shared.constant.enums.TokenTypeEnum;
import org.ait.project.guideline.example.shared.dto.data.JwtData;
import org.ait.project.guideline.example.shared.exception.AppExpiredJwtException;
import org.ait.project.guideline.example.shared.exception.InvalidJwtException;
import org.ait.project.guideline.example.shared.utils.response.security.JwtUtils;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class JwtFilter extends OncePerRequestFilter {

  private final JwtUtils jwtUtils;

  private final HandlerExceptionResolver handlerExceptionResolver;

  private final UserSessionService userSessionService;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                  FilterChain filterChain) throws ServletException, IOException {
    String jwtToken = getTokenAuthorization(request.getHeader("Authorization"));

    if (StringUtils.hasLength(jwtToken)) {
      try {
        JwtData jwtData = jwtUtils.parseToken(jwtToken);
        if (jwtData.getType().equals(TokenTypeEnum.ACCESS_TOKEN.name())) {
          userSessionService.upsertUserSession(jwtData.getUserId());
          setAuthenticationPrincipal(String.valueOf(jwtData.getUserId()), jwtData.getAuthorities());
        }
      } catch (MalformedJwtException | SignatureException e) {
        log.error(e.getMessage());
        handlerExceptionResolver.resolveException(request, response, null,
            new InvalidJwtException());
        return;
      } catch (ExpiredJwtException e) {
        log.error(e.getMessage());
        handlerExceptionResolver.resolveException(request, response, null,
            new AppExpiredJwtException());
        return;
      }
    }
    filterChain.doFilter(request, response);
  }


  private void setAuthenticationPrincipal(String principalName, List<String> authorities) {
    List<SimpleGrantedAuthority> simpleGrantedAuthorities = new ArrayList<>();
    for (String authority : authorities) {
      simpleGrantedAuthorities.add(new SimpleGrantedAuthority(authority));
    }
    Authentication authentication =
        new UsernamePasswordAuthenticationToken(principalName, null, simpleGrantedAuthorities);
    SecurityContextHolder.getContext().setAuthentication(authentication);
  }

  private String getTokenAuthorization(String authorization) {
    if (authorization != null) {
      return authorization.replace("Bearer ", "");
    }
    return null;
  }
}
