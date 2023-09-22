package com.coderXAmod.ElectronicStore.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@AllArgsConstructor
@Getter
@Setter
@NoArgsConstructor
@Entity
@Builder
@Table (name="users")
public class User {
    @Id
    private String User_Id;
    @Column(name="User_Name")
    private String name;
    @Column(name="User_Email")
    private String email;
    @Column(name="User_Password" ,length = 10)
    private String password;
    private  String gender;
    private String about;
    @Column(name="user_image_name")
    private String image;
    // upload User Image

    //serve image

}
