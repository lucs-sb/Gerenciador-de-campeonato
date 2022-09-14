package api.championship.manager.dtos;

import lombok.Getter;

@Getter
public class UserDTO {
    private Long id;
    private String email;
    private String password;
    private String name;
}
