package com.shsxt.ego.rpc.service;

import com.shsxt.ego.common.model.EgoResult;

public interface ISmsService {

    public EgoResult sendSmsToPhone(String phone, Integer type);
}
