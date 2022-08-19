package com.fingerchar.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fingerchar.core.base.service.IBaseService;
import com.fingerchar.core.util.ResponseUtil;
import com.fingerchar.db.domain.TtPaytoken;

@Service
public class TtPayTokenService {

    @Autowired
    IBaseService baseService;

    public Object list() {
        List<TtPaytoken> ttPaytokenList = new ArrayList<>();
        ttPaytokenList = baseService.findByCondition(TtPaytoken.class, null);

        return ResponseUtil.ok(ttPaytokenList);
    }


    public TtPaytoken getPayToken(Class<TtPaytoken> ttPaytokenClass, QueryWrapper<TtPaytoken> wrapper) {
        TtPaytoken byCondition = baseService.getByCondition(ttPaytokenClass, wrapper);
        return byCondition;
    }
}
