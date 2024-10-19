package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;

    @GetMapping
    public List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                          .stream()
                          .map(userMapper::toDto)
                          .toList();
    }

    @GetMapping("/{id}")
    public UserDto getUserById(@PathVariable long id) {
        return userService.getUser(id).stream().map(userMapper::toDto).findFirst().orElse(null);
    }

    @GetMapping("/simple")
    public List<SimpleUserDto> getAllUsersSimple() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toSimpleDto)
                .toList();
    }

    @GetMapping("/email")
    public List<EmailSimpleUserDto> getUserByEmail(@RequestParam String email) {
        System.out.println(email);
        return userService.getUserBySimillarEmail(email).stream()
                .map(userMapper::toEmailSimpleDto)
                .collect(Collectors.toList());
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public User addUser(@RequestBody UserDto userDto) throws InterruptedException {

        // Demonstracja how to use @RequestBody
        System.out.println("User with e-mail: " + userDto.email() + "passed to the request");
        User MappedUser = userMapper.toEntity(userDto);

        return userService.createUser(MappedUser);
    }

    @PostMapping("/delete")
    @ResponseStatus(HttpStatus.I_AM_A_TEAPOT)
    public boolean removeUser(@RequestParam long Id) throws InterruptedException {

        System.out.println("User with e-mail: " + Id + "passed to the request");


        return userService.deleteUser(Id);
    }

}