package com.backendpractice.blog.payloads;

import java.util.HashSet;
import java.util.Set;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ProfileDto {

  private Long id;

  @NotEmpty(message = "Kyu field ko khali chhod rhe ho bhai")
  @Size(min = 4, message = "Bas itna hi chota naam hai tera 4 character lamba naam daal kamse kam")
  private String name;

  @Email(message = "Bhai sahi email enter karo")
  private String email;

  @NotEmpty(message = "Kyu field ko khali chhod rhe ho bhai")
  @Size(min = 8, message = "Password Lamba rakho yaar itna chota koi bhi guess kar lega")
  private String password;

  @NotEmpty(message = "Kyu field ko khali chhod rhe ho bhai")
  @Size(max = 200, message = "Bas kar bhai itna bada description")
  private String about;

  private Set<RoleDto> roles = new HashSet<>();
}
