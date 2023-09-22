package com.coderXAmod.ElectronicStore.Controllers;

import com.coderXAmod.ElectronicStore.Services.FileService;
import com.coderXAmod.ElectronicStore.Services.UserService;
import com.coderXAmod.ElectronicStore.dto.ApiResponceMessage;
import com.coderXAmod.ElectronicStore.dto.ImageResponce;
import com.coderXAmod.ElectronicStore.dto.PagableResponse;
import com.coderXAmod.ElectronicStore.dto.UserDto;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.awt.*;
import java.io.IOException;
import java.io.InputStream;
import java.io.StreamCorruptedException;
import java.util.List;


@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private FileService fileService;
    @Value("${user.profile.image.path}")
    private String imageUpload;
    private Logger logger= LoggerFactory.getLogger(UserController.class);
    //create
    @PostMapping
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto)
    {
        UserDto userDto1 = userService.create(userDto);
        return new ResponseEntity<>(userDto1, HttpStatus.CREATED);
    }
    //update
    @PutMapping("/{userId}")
    public  ResponseEntity<UserDto> updateUser(
            @PathVariable("userId") String userId,
               @RequestBody UserDto userDto
    )
    {
        UserDto updateUserDto = userService.update(userDto, userId);
        return new ResponseEntity<>(updateUserDto,HttpStatus.OK);

    }
    //delete
    @DeleteMapping("/{userId}")
    public ResponseEntity<ApiResponceMessage> deleteUser(@PathVariable String userId)
    {
userService.deleteUser(userId);
        ApiResponceMessage message = ApiResponceMessage.builder().message("User Delete Sucessfully!!").success(true).status(HttpStatus.OK).build();
        return new ResponseEntity<>(message,HttpStatus.OK);
    }
    //get all
    @GetMapping
    public ResponseEntity<PagableResponse<UserDto>> getAllUser(
            @RequestParam(value ="pageNumber", defaultValue ="0", required = false) int pageNumber,
            @RequestParam(value ="pageSize", defaultValue ="10", required =false) int pageSize,
            @RequestParam(value ="sortBy", defaultValue ="name", required = false) String sortBy,
            @RequestParam(value ="sortDir", defaultValue ="asc", required =false) String sortDir
    ) {
        return new ResponseEntity<>(userService.getAll(pageNumber, pageSize, sortBy, sortDir), HttpStatus.OK);
    }

    //get single
    @GetMapping("/{userId}")
public ResponseEntity<UserDto> getUser(@PathVariable String userId)
{
    return new ResponseEntity<>(userService.getSingleUser(userId),HttpStatus.OK);
}
    //getByEmail
    @GetMapping("/email/{email}")
    public ResponseEntity<UserDto> getUserByEmail(@PathVariable String email)
    {
        return new ResponseEntity<>(userService.getUserUsingemail(email),HttpStatus.OK);
    }
    //search user
    @GetMapping("/search/{keywords}")
    public ResponseEntity<List<UserDto>> searchUser(@PathVariable String keywords)
    {
        return new ResponseEntity<>(userService.searchUser(keywords),HttpStatus.OK);
    }
    //ab hm yaha pe upload image krenge
    @PostMapping("/image/{userId}")
    public ResponseEntity<ImageResponce> uploadUserImage(@RequestParam("userImage") MultipartFile image,
     @PathVariable String userId ) throws IOException {
        String imageName = fileService.uploadFile(image,imageUpload);
        UserDto user = userService.getSingleUser(userId);
        user.setImage(imageName);
        UserDto userDto = userService.update(user, userId);
        ImageResponce imageResponce=ImageResponce.builder().imageName(imageName).success(true).status(HttpStatus.CREATED).build();
        return new ResponseEntity<>(imageResponce,HttpStatus.CREATED);
    }
    //serve user images
    @GetMapping("/image/{userId}")
    public void serveUserImage(@PathVariable String userId, HttpServletResponse response) throws IOException {
        UserDto user = userService.getSingleUser(userId);
        logger.info("User Image Name:{}",user.getName());
        InputStream resourse = fileService.getResourse(imageUpload, user.getImage());
        response.setContentType(MediaType.IMAGE_JPEG_VALUE);
        StreamUtils.copy(resourse,response.getOutputStream());
    }
}
