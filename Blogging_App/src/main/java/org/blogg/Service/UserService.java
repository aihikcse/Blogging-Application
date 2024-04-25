package org.blogg.Service;

import org.blogg.Payloads.UserDto;

import java.util.List;

public interface UserService {
    public UserDto createUser(UserDto userDto);
    public UserDto updateUser(UserDto userDto, Integer userId);
    public void deleteUser(Integer userId);
    public UserDto findById(Integer userId);
    public List<UserDto> viewAll();
}
