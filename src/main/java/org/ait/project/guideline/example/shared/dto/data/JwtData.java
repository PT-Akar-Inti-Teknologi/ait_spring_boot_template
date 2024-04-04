package org.ait.project.guideline.example.shared.dto.data;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class JwtData {
  private Integer userId;
  private String type;
  private String name;
  private String email;
  private List<String> authorities;
}
