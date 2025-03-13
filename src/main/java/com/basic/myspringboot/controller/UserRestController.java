package com.basic.myspringboot.controller;

import com.basic.myspringboot.entity.User;
import com.basic.myspringboot.exception.BusinessException;
import com.basic.myspringboot.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicUpdate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@Slf4j
@RequiredArgsConstructor
public class UserRestController {
    private final UserRepository userRepository;

    //Constructor Injection
    //    public UserRestController(UserRepository userRepository) {
    //        this.userRepository = userRepository;
    //        log.info("UserRepository 객체 {}",userRepository.getClass().getName());
    //    }

    @PostMapping
    public User create(@RequestBody User user) {
        return userRepository.save(user);
    }

    @GetMapping
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public List<User> getUsers() {
        return userRepository.findAll();
    }

    //{} 는 동적인 변수라는 뜻
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public User getUser(@PathVariable Long id) {
        return getUserNotFound(id);

    }

    private User getUserNotFound(Long id) {
        return userRepository.findById(id) //Optional<user>
                .orElseThrow(
                        () -> new BusinessException("User Not Found", HttpStatus.NOT_FOUND));
    }


    //단건만 바꿀땐 put 보단 patch
    @PatchMapping("/{email}")
    public User updateUser(@PathVariable String email, @RequestBody User userDetail) {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new BusinessException("User Not Found", HttpStatus.NOT_FOUND));
        //setter 호출 - name 수정
        user.setName(userDetail.getName());
        //user.setEmail(userDetail.getEmail());
        //@Transactional 사용하지 않으므로 반드시 save() 호출해야 db에 반영됨
        User updatedUser = userRepository.save(user);
        return updatedUser;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        User user = getUserNotFound(id);
        userRepository.delete(user);
        String msg = String.format("Id = %d User 삭제 되었습니다. ",id);
        return ResponseEntity.ok(msg);
    }

    @GetMapping("/welcome")
    public String welcome() {
        return "Welcome this endpoint is not secure";
    }


}