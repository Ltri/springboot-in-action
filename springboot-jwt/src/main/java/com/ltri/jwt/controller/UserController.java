package com.ltri.jwt.controller;


import com.ltri.jwt.biz.UserBiz;
import com.ltri.jwt.dto.UserDTO;
import com.ltri.jwt.vo.UserVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;


@RestController
public class UserController {
    @Autowired
    private UserBiz userBiz;

    @GetMapping("/user")
    @PreAuthorize("hasRole('role004')")
    public List<UserVO> list() {
        return userBiz.listUserDTO();
    }

    @GetMapping("/user/{id}")
    public UserVO list(@PathVariable Long id) {
        return userBiz.getUserDTO(id);
    }

    @PostMapping("/user")
    public void save(@RequestBody @Valid UserDTO userDTO) {
        userBiz.saveUser(userDTO);
    }
}
