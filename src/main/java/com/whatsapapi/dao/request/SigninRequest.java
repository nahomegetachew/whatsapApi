package com.whatsapapi.dao.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SigninRequest {
	
	  @NotBlank(message = "The username is required.")
	  @Size(min = 3, max = 20, message = "The username must be from 3 to 20 characters.")
	  private String username;
	  
	  @NotBlank(message = "The password is required.")
	  @Size(min = 3, max = 20, message = "The username must be from 3 to 20 characters.")
    private String password;
}
