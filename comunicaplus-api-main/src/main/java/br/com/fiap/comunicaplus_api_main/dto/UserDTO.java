package br.com.fiap.comunicaplus_api_main.dto;

import lombok.Data;

@Data
public class UserDTO {
    private Long id;
    private String name;
    private String email;
    private String role; // Ex: "ADMIN", "USER", etc.
}
