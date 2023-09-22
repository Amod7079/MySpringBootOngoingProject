package com.coderXAmod.ElectronicStore.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name="categories")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Category {
    @Id
    @Column(name="id")
    private String CategoryId;
    @Column(name="category_title",length=60,nullable = false)
    private String title;
    @Column(name="category_desc",length = 500)
    private String description;
    private String coverImage;

}
//ervlet.service() for servlet [dispatcherServlet] in context with path [] threw exception [Request processing failed: org.springframework.orm.jpa.JpaSystemException: ids for this class must be manually assigned before calling save(): com.coderXAmod.ElectronicStore.entities.Category] with root cause