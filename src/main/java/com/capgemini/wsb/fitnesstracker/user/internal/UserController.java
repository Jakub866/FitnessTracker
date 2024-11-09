package com.capgemini.wsb.fitnesstracker.user.internal;

import com.capgemini.wsb.fitnesstracker.user.api.User;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
class UserController {

    private final UserServiceImpl userService;

    private final UserMapper userMapper;

    @GetMapping("/older/{time}")
    private List<UserDto> getAllUserOlderThan(@PathVariable LocalDate time){
        return userService.findAllUsersOlderThan(time)
                .stream()
                .map(userMapper::toDto)
                .toList();
    }

    @GetMapping
    private List<UserDto> getAllUsers() {
        return userService.findAllUsers()
                          .stream()
                          .map(userMapper::toDto)
                          .toList();
    }

    @GetMapping("/{id}")
    private UserDto getUserById(@PathVariable long id) {
        return userService.getUser(id).stream().map(userMapper::toDto).findFirst().orElse(null);
    }


//    @DeleteMapping{"/{id}"}
//    private boolean deleteUserById(@PathVariable long id){
//        return userService.deleteUser(id);
//    }

    @PutMapping("/{id}")
    private Optional<User> updateUserById(@PathVariable long id, @RequestBody UserDto userDto) {
        return userService.updateUser(id, userMapper.toEntity(userDto));
    }


    @GetMapping("/simple")
    private List<SimpleUserDto> getAllUsersSimple() {
        return userService.findAllUsers()
                .stream()
                .map(userMapper::toSimpleDto)
                .toList();
    }

    @GetMapping("/email")
    private List<EmailSimpleUserDto> getUserByEmail(@RequestParam String email) {
        System.out.println(email);
        return userService.getUserBySimillarEmail(email).stream()
                .map(userMapper::toEmailSimpleDto)
                .collect(Collectors.toList());
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    private User addUser(@RequestBody UserDto userDto) throws InterruptedException {

        // Demonstracja how to use @RequestBody
        System.out.println("User with e-mail: " + userDto.email() + "passed to the request");
        User MappedUser = userMapper.toEntity(userDto);

        return userService.createUser(MappedUser);
    }

    @DeleteMapping("/{Id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    private boolean removeUser(@PathVariable long Id) throws InterruptedException {

        System.out.println("User with e-mail: " + Id + "passed to the request");


        return userService.deleteUser(Id);
    }

}