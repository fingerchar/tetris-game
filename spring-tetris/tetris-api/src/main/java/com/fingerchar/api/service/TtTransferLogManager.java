package com.fingerchar.api.service;

import com.fingerchar.core.base.service.IBaseService;
import com.fingerchar.db.domain.TtTransferLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TtTransferLogManager {
    @Autowired
    private IBaseService baseService;

    public void save(TtTransferLog ttTransferLog) {
        this.baseService.save(ttTransferLog);
    }


}
