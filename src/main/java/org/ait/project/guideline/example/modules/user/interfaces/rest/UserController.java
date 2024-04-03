package org.ait.project.guideline.example.modules.user.interfaces.rest;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.ait.project.guideline.example.modules.user.dto.request.UserReq;
import org.ait.project.guideline.example.modules.user.dto.response.UserRes;
import org.ait.project.guideline.example.modules.user.service.core.UserCore;
import org.ait.project.guideline.example.shared.dto.template.ResponseCollection;
import org.ait.project.guideline.example.shared.dto.template.ResponseTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@Tag(name = "API User CRUD")
@RequestMapping("/user")
public class UserController {
    private final UserCore userCore;


    @Operation(summary = "API to get All Users")
    @GetMapping("/all")
    public ResponseEntity<ResponseTemplate<ResponseCollection<UserRes>>> getUsers (
    		@RequestParam(required = false, value = "search") String search,
    		 @RequestParam(required = false, value = "sort") String sort,
    		 @RequestParam(required = false, value = "page") Integer page,
    		 @RequestParam(required = false, value = "size") Integer size,
    		 @RequestBody (required=false) UserReq userReq){
    		
        return userCore.getUsers( search,  sort,  page,  size, userReq);
    }

}
