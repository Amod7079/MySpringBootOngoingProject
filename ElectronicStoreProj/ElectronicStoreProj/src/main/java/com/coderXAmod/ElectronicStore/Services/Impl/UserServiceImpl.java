package com.coderXAmod.ElectronicStore.Services.Impl;

import com.coderXAmod.ElectronicStore.Exception.ResourseNotFoundException;
import com.coderXAmod.ElectronicStore.Services.UserService;
import com.coderXAmod.ElectronicStore.dto.PagableResponse;
import com.coderXAmod.ElectronicStore.dto.UserDto;
import com.coderXAmod.ElectronicStore.entities.User;
import com.coderXAmod.ElectronicStore.repositories.UserRepositories;
import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    @Autowired
    private UserRepositories userRepositories ;
    @Autowired
    private ModelMapper mapper;
    @Value("${user.profile.image.path}")
    private String imagePath;
Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);
    @Override
    public UserDto create(UserDto userDto) {
        String userId = UUID.randomUUID().toString();
        userDto.setUser_Id(userId);
        //dto to entity
     User user=   dtoToEntity(userDto);
        User saveUser = userRepositories.save(user);
        //entity to dto pass karni hai
      UserDto newDto=  entityToDto(saveUser);
      return newDto;
    }



    @Override
    public void deleteUser(String userid) {
        User user = userRepositories.findById(userid).orElseThrow(() -> new ResourseNotFoundException("User Not found on specified id"));
        String fullpath = imagePath + user.getImage();
        try {
            Path path = Paths.get(fullpath);
            Files.delete(path);
        }
        catch (NoSuchFileException ex)
        {
            logger.info("user image not found in folder:");
            ex.printStackTrace();

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        userRepositories.delete(user);
    }

    @Override
    public UserDto update(UserDto dto, String UserId) {
        User user = userRepositories.findById(UserId).orElseThrow(() -> new ResourseNotFoundException("Sorry User Not found with given id"));
        user.setName(dto.getName());
        user.setAbout(dto.getAbout());
        user.setGender(dto.getGender());
        user.setPassword(dto.getPassword());
        user.setImage(dto.getImage());
        //hmko email ko update nhi krani hai
        User updatedUser = userRepositories.save(user);
        UserDto updatedDto = entityToDto(updatedUser);
        return updatedDto;
    }

    @Override
    public PagableResponse<UserDto> getAll(int pageNumber, int pageSize, String sortBy, String sortDir) {
        Sort sort=(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());
        Pageable pagable = PageRequest.of(pageNumber, pageSize,sort);
        Page<User> page  = userRepositories.findAll(pagable);
        List<User> users = page.getContent();
        //chuki User aaya hmko return krna hai dto me so we chaged into dto
        List<UserDto> dtoToList = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
       PagableResponse<UserDto> response=new PagableResponse<>();
       response.setContent(dtoToList);
       response.setPageNumber(page.getNumber());
       response.setPageSize(page.getSize());
       response.setTotalElements(page.getTotalElements());
       response.setTotalPages(page.getTotalPages());
       response.setLastPage(page.isLast());

        return response;
    }

      @Override
      public UserDto getSingleUser(String user_Id) {
        User user = userRepositories.findById(user_Id).orElseThrow(() -> new ResourseNotFoundException("User Not Found with your specified id"));
       return  entityToDto(user);
      }

    @Override
    public UserDto getUserUsingemail(String email) {
        User user = userRepositories.findByEmail(email).orElseThrow(() -> new ResourseNotFoundException("User Not Found With Given Email Id or passwoerd"));
       return entityToDto(user);
    }

    @Override
    public List<UserDto> searchUser(String keyword) {
        List<User> users = userRepositories.findByNameContaining(keyword);
        List<UserDto> dtoToList = users.stream().map(user -> entityToDto(user)).collect(Collectors.toList());
        return dtoToList;
    }
    private UserDto entityToDto(User saveUser) {
//        UserDto userDto = UserDto.builder()
//                .User_Id(saveUser.getUser_Id())
//                .name(saveUser.getName())
//                .email(saveUser.getEmail())
//                .password(saveUser.getPassword())
//                .about(saveUser.getAbout())
//                .gender(saveUser.getGender())
//                .image(saveUser.getImage())
//                .build();
        return mapper.map(saveUser,UserDto.class);
    }

    private User dtoToEntity(UserDto userDto) {
//        User user = User.builder().
//                User_Id(userDto.getUser_Id())
//                .name(userDto.getName())
//                .email(userDto.getEmail())
//                .password(userDto.getPassword())
//                .about(userDto.getAbout())
//                .gender(userDto.getGender())
//                .image(userDto.getImage())
//                .build();
//        return user;
        return mapper.map(userDto,User.class);
    }
}
