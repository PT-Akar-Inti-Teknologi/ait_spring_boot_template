package org.ait.project.guideline.example.shared.feignclient.reqres.service;

import org.ait.project.guideline.example.shared.feignclient.reqres.dto.response.ReqresListUserResponse;

public interface ReqresService{

    ReqresListUserResponse getListUser(String page);
}
