package org.ait.project.guideline.example.modules.user.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "API User CRUD")
@RequestMapping("/user")
public class UserController {
    private final UserCore userCore;

    @Operation(summary = "API to Add User")
    @PostMapping
    public ResponseEntity<ResponseTemplate<ResponseDetail<UserRes>>> addUser(@Valid @RequestBody UserReq userReq) {
        return userCore.addUser(userReq);
    }


    @Operation(summary = "API to get All Users")
    @GetMapping("/all")
    public ResponseEntity<ResponseTemplate<ResponseCollection<UserRes>>> getUsers() {
        return userCore.getUsers();
    }


    @Operation(summary = "API to get Fetch users")
    @GetMapping("/fetch")
    public ResponseEntity<ResponseTemplate<ResponseDetail<String>>> fetchUser() {
        return userCore.fetchUser();
    }


    @Operation(summary = "API to get detail user")
    @PutMapping("/{id}")
    public ResponseEntity<ResponseTemplate<ResponseDetail<UserRes>>> updateUser(@PathVariable("id") Integer id, @RequestBody UserReq userReq) {
        return userCore.updateUser(id, userReq);
    }


    @Operation(summary = "API to add balance user")
    @PutMapping("/addBalance/{id}")
    public ResponseEntity<ResponseTemplate<ResponseDetail<UserRes>>> addBalance(@PathVariable("id") Integer id, @RequestBody UserReq userReq) {
        return userCore.addBalance(id, userReq);
    }


    @Operation(summary = "API to delete user")
    @DeleteMapping("/{id}")
    public ResponseEntity<ResponseTemplate<ResponseDetail<String>>> deleteUser(@PathVariable("id") Integer id) {
        return userCore.deleteUser(id);
    }

}
