package org.ait.project.guideline.example.shared.feignclient.reqres.service.impl;

import lombok.RequiredArgsConstructor;
import org.ait.project.guideline.example.shared.feignclient.reqres.ReqresClient;
import org.ait.project.guideline.example.shared.feignclient.reqres.response.ReqresListUserResponse;
import org.ait.project.guideline.example.shared.feignclient.reqres.service.ReqresService;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class ReqresServiceImpl implements ReqresService {

    private final ReqresClient reqresClient;
    @Override
    public ReqresListUserResponse getListUser(String page) {
        return reqresClient.getListUser(page).orElseThrow();
    }
}
