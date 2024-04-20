package com.eshop.app.common.models.kafka;

import com.eshop.app.common.constants.Role;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@JsonDeserialize(builder = User.UserBuilder.class)
@JsonSerialize
@ToString
public class User implements Serializable {
    private static final long serialVersionUID = 9092608953833144843L;
    private Long id;
    private String username;
    private String password;
    private String name;
    private String email;
    private String loginId;
    private String role;
    private Long tenantId;
    private boolean isActive;
    private int version;

    @JsonPOJOBuilder(withPrefix = "")
    public static class UserBuilder {}
}
