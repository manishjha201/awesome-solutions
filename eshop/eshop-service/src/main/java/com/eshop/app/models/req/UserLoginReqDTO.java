package com.eshop.app.models.req;

import com.eshop.app.common.constants.Role;
import com.eshop.app.utils.ValidateInputRequestHelper;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonPOJOBuilder;
import lombok.Builder;
import lombok.Data;
import org.apache.commons.lang3.ObjectUtils;

import javax.validation.constraints.NotBlank;

@Data
@Builder
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonDeserialize(builder = UserLoginReqDTO.UserLoginReqDTOBuilder.class)
public class UserLoginReqDTO extends HttpRequest {
    private static final long serialVersionUID = -1954680921158293727L;
    private Long id;
    @NotBlank(message = "User Name is a mandatory field. It should not be null/empty")
    private String username;
    @NotBlank(message = "Password is a mandatory field. It should not be null/empty")
    private String password;
    @NotBlank(message = "Name is a mandatory field. It should not be null/empty")
    private String name;
    @NotBlank(message = "Email Id is a mandatory field. It should not be null/empty")
    private String email;
    @NotBlank(message = "Login Id is a mandatory field. It should not be null/empty")
    private String loginId;
    @NotBlank(message = "Role is a mandatory field. It should not be null/empty")
    private Role role;
    @NotBlank(message = "Tenant Id is a mandatory field. It should not be null/empty")
    private Long tenantId;

    @Override
    public boolean validate() {
        return ObjectUtils.isNotEmpty(username) && ObjectUtils.isNotEmpty(password) && ValidateInputRequestHelper.validateUserReq(this);
    }

    @JsonPOJOBuilder(withPrefix = "")
    public static class UserLoginReqDTOBuilder {
    }
}
