package com.coderXAmod.ElectronicStore.helper;

import com.coderXAmod.ElectronicStore.dto.PagableResponse;
import com.coderXAmod.ElectronicStore.dto.UserDto;
import com.coderXAmod.ElectronicStore.entities.User;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.stream.Collectors;

public class Helper {
    public static <U, V> PagableResponse<V> getPageableResponse(Page<U> page, Class<V> type) {
        List<U> entity = page.getContent();
        List<V> dtoToList = entity.stream().map(object -> new ModelMapper().map(object, type)).collect(Collectors.toList());
        PagableResponse<V> response = new PagableResponse<>();
        response.setContent(dtoToList);
        response.setPageNumber(page.getNumber());
        response.setPageSize(page.getSize());
        response.setTotalElements(page.getTotalElements());
        response.setTotalPages(page.getTotalPages());
        response.setLastPage(page.isLast());
        return response;
    }

}
