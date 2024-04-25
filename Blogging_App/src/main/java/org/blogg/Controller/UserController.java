package org.blogg.Controller;

import java.util.List;

import org.blogg.Payloads.UserDto;
import org.blogg.Service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        super();
        this.userService = userService;
    }

    //Creating Rest API -->
    //Create Employee
    @PostMapping("/createUser")
    public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
        return new ResponseEntity<UserDto>(userService.createUser(userDto), HttpStatus.CREATED);
    }
    
    //Update Employee
    @PutMapping("/updateUser/{userId}")
    public ResponseEntity<UserDto> updateUser(@PathVariable("userId") Integer userId, @RequestBody UserDto userDto){
    	return new ResponseEntity<UserDto>(userService.updateUser(userDto, userId), HttpStatus.OK);
    }
    
    @DeleteMapping("/deleteUser/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable("userId") Integer userId){
    	userService.deleteUser(userId);
    	return new ResponseEntity<String>(" User with id: " +userId+" is deleted successfully", HttpStatus.OK);
    }
  //FindById Employee
    @GetMapping("/findUser/{userId}")
    public ResponseEntity<UserDto> FindById(@PathVariable("userId") Integer userId){
    	UserDto findUserById= userService.findById(userId);
    	return new ResponseEntity<UserDto>(findUserById, HttpStatus.OK);
    }
    
  //ViewAll Employee
    @GetMapping("/viewAllUser")
    public List<UserDto> viewAll(){
    	return userService.viewAll();
    }
}
