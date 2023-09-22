package com.coderXAmod.ElectronicStore.repositories;

import com.coderXAmod.ElectronicStore.entities.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category,String> {

}
