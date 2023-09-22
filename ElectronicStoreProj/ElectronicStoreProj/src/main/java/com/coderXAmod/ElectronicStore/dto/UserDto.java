package com.coderXAmod.ElectronicStore.dto;

import com.coderXAmod.ElectronicStore.validate.ImageNameValid;
import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserDto {

    private String User_Id;
    @Size(min = 2,max = 15,message = "Invalid Name!!")
    private String name;
    @Pattern(regexp =  "^[A-Za-z0-9+_.-]+@([A-Za-z0-9.-]+\\.[A-Za-z]{2,})$",message = "please provide valid email id")
    @NotBlank(message = "")
    private String email;
    @Size(min = 4,max = 10,message = "Not Blank!!")
    private String password;
    @Size(min=4,max = 10,message = "Not Valid Gender!!")
    private  String gender;
    @Size(min = 5 ,max = 100,message = "Please Give Some Information!!")
    @NotBlank(message = "Please write somethings:")
    private String about;
    @ImageNameValid
    private String image;
}
