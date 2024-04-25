package org.blogg.Service.Impl;

import org.blogg.Exception.RecourseNotFoundException;
import org.blogg.Model.User;
import org.blogg.Payloads.UserDto;
import org.blogg.Repository.UserRepository;
import org.blogg.Service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import jakarta.validation.Valid;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class userServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Override
    public UserDto createUser(@Valid @RequestBody UserDto userDto) {
        User user = userDtoToUser(userDto);
        User savedUser = userRepository.save(user);
        return userToUserDto(savedUser);
    }

    @Override
    public UserDto updateUser(@Valid @RequestBody UserDto userDto,@PathVariable("userId") Integer userId) {
    	User existingUser = userRepository.findById(userId).orElseThrow(() ->
                new RecourseNotFoundException("user", "userId", userId));
    	existingUser.setName(userDto.getName());
    	existingUser.setEmail(userDto.getEmail());
    	existingUser.setPassword(userDto.getPassword());
    	existingUser.setAbout(userDto.getAbout());
        User updatedUser = userRepository.save(existingUser);
        return  userToUserDto(updatedUser);
    }

    @Override
    public void deleteUser(@PathVariable("userId") Integer userId) {
    	userRepository.findById(userId).orElseThrow(() -> new RecourseNotFoundException("user", "userId", userId));
        userRepository.deleteById(userId);
    }

    @Override
    public UserDto findById(@PathVariable("userId") Integer userId) {
//    	Optional<User> user = userRepository.findById(id);
//    	if(user.isPresent()) {
//    		user.get();
//    	}
//    	else {
//    		throw new RecourseNotFoundException("User", "id", id);
//    	}
        return userToUserDto(userRepository.findById(userId).orElseThrow(()->
        				new RecourseNotFoundException("User", "userId", userId)));
    }

    @Override
    public List<UserDto> viewAll() {
        List<User> users = userRepository.findAll();
        List<UserDto> userDtos = users.stream().map(user->userToUserDto(user)).collect(Collectors.toList());
        return userDtos;
    }



    public User userDtoToUser(UserDto userDto){
        return modelMapper.map(userDto, User.class);
    }
    public UserDto userToUserDto(User user){
        return modelMapper.map(user, UserDto.class);
    }
}
