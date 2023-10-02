package org.ait.project.guideline.example.modules.user.interfaces.rest;

import lombok.RequiredArgsConstructor;
import org.ait.project.guideline.example.modules.user.dto.request.UserReq;
import org.ait.project.guideline.example.modules.user.dto.response.UserRes;
import org.ait.project.guideline.example.modules.user.service.core.UserCore;
import org.ait.project.guideline.example.shared.dto.template.ResponseCollection;
import org.ait.project.guideline.example.shared.dto.template.ResponseDetail;
import org.ait.project.guideline.example.shared.dto.template.ResponseTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController implements UserCore {
    private final UserCore userCore;
    @Override
    @PostMapping
    public ResponseEntity<ResponseTemplate<ResponseDetail<UserRes>>> addUser(@Valid @RequestBody UserReq userReq) {
        return userCore.addUser(userReq);
    }

    @Override
    @GetMapping("/all")
    public ResponseEntity<ResponseTemplate<ResponseCollection<UserRes>>> getUsers() {
        return userCore.getUsers();
    }

    @Override
    @GetMapping("/fetch")
    public ResponseEntity<ResponseTemplate<ResponseDetail<String>>> fetchUser() {
        return userCore.fetchUser();
    }

    @Override
    @PutMapping("/{id}")
    public ResponseEntity<ResponseTemplate<ResponseDetail<UserRes>>> updateUser(@PathVariable("id") Integer id, @RequestBody UserReq userReq) {
        return userCore.updateUser(id, userReq);
    }

    @Override
    @PutMapping("/addBalance/{id}")
    public ResponseEntity<ResponseTemplate<ResponseDetail<UserRes>>> addBalance(@PathVariable("id") Integer id, @RequestBody UserReq userReq) {
        return userCore.addBalance(id, userReq);
    }

    @Override
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseTemplate<ResponseDetail<String>>> deleteUser(@PathVariable("id") Integer id) {
        return userCore.deleteUser(id);
    }

}
