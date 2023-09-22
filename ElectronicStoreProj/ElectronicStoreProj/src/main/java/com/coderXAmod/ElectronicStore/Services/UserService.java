package com.coderXAmod.ElectronicStore.Services;

import com.coderXAmod.ElectronicStore.dto.PagableResponse;
import com.coderXAmod.ElectronicStore.dto.UserDto;

import java.util.List;

public interface UserService {
    //create kr skta hu
    UserDto create(UserDto userDto );
    //delete kr skta hu
    void deleteUser(String userid);
    //update kr skta hu
    UserDto update(UserDto dto ,String UserId);
    //getAllUser
    PagableResponse<UserDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir);
    //get Single user
    UserDto getSingleUser(String user_Id);
    //get Single user By EMAIL
    UserDto getUserUsingemail(String email);
    //search user
    List<UserDto> searchUser(String keyword);
}
