package com.wipro.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AuthResponse {

	private String token;

	private String message;

	private Long id;
	private String fullName;
	private String email;
}
