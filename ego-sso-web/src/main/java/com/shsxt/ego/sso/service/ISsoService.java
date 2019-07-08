package com.shsxt.ego.sso.service;

import com.shsxt.ego.common.model.EgoResult;

public interface ISsoService {

    public EgoResult userCheck(String param, Integer type);
}
