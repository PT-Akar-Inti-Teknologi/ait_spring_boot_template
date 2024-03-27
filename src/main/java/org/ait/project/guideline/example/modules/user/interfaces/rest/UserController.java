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
    		@RequestParam(required = false, value = "searchBy") String searchBy,
    		 @RequestParam(required = false, value = "sortBy") String sortBy,
    		 @RequestParam(required = false, value = "sortField") String sortField,
    		 @RequestParam(required = false, value = "pageNumber") Integer pageNumber,
    		 @RequestParam(required = false, value = "pageSize") Integer pageSize,
    		 @RequestBody (required=false) UserReq userReq){
    		
        return userCore.getUsers( searchBy,  sortBy,  sortField,  pageNumber,  pageSize, userReq);
    }

}
