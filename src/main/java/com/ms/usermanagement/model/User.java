package com.ms.usermanagement.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.util.UUID;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User extends BaseModel {



    @NotEmpty(message = "Please enter password")
    @ApiModelProperty(required = true)
    private String password;

    @ApiModelProperty(notes = "Email must be a valid email according to given format",required = true)
    @NotEmpty(message = "Please enter your email address")
    @Email
    @Pattern(regexp = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$", message = "Invalid Email Format!")
    private String email;

    @NotEmpty(message = "Please enter role")
    @ApiModelProperty(required = true)
    private String roles;

    public User(UUID randomUUID, String userName, String password, String email,String roles) {
        super.setId(randomUUID);
        this.password = password;
        this.email = email;
        this.roles=roles;
    }
}
