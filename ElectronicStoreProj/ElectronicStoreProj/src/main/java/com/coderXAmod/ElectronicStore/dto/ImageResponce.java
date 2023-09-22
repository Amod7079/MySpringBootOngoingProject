package com.coderXAmod.ElectronicStore.dto;

import lombok.*;
import org.springframework.http.HttpStatus;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ImageResponce {
private String imageName;
    private String message;
    private boolean success;
    private HttpStatus status;

}



