package com.coderXAmod.ElectronicStore.dto;

import jakarta.persistence.Column;
import jakarta.persistence.Id;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CategoryDtos {
    private String CategoryId;
    @NotBlank(message = "Title Is Required!!")
    @Size(min =4,message = "Title Must Be Minimum 4 Characters")
    private String title;
    @NotBlank(message = "Description required!!")
    private String description;
    private String coverImage;

}
