package com.example.springboot.jwtAuthentication.Model;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString
public class JwtResponse {
    String token;
    String username;
    
}