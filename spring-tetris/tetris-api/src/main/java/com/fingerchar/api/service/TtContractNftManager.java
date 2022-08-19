package com.fingerchar.api.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.fingerchar.core.base.service.IBaseService;
import com.fingerchar.db.base.BaseEntity;
import com.fingerchar.db.domain.TtContractNft;
import com.fingerchar.db.domain.TtTransferLog;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class TtContractNftManager {
    @Autowired
    IBaseService baseService;

    @Autowired
    TtNftItemsManager nftItemsManager;

    @Autowired
    TtTransferLogManager transferLogManager;

    public void processTransfer(String transferLogList, String contractNfts) {
        if (StringUtils.isEmpty(transferLogList)) {
            return;
        }
        List<TtTransferLog> list = JSONArray.parseArray(transferLogList, TtTransferLog.class);
        List<TtContractNft> contractNftList = JSONArray.parseArray(contractNfts, TtContractNft.class);
        for (TtTransferLog log : list) {
            if (log.getFrom().equalsIgnoreCase("0x0000000000000000000000000000000000000000") &&
                    log.getTo().equalsIgnoreCase("0x0000000000000000000000000000000000000000")
            ) {
                // from and to is zero address
                return;
            }

            String tokenId = log.getTokenId();

            TtContractNft nft = this.get(log.getAddress(), tokenId);
            if (null == nft) {
//                if(!log.getFrom().equalsIgnoreCase("0x0000000000000000000000000000000000000000")){
//                    return ;
//                }
                // mint transfer
                nft = this.add(log, contractNftList);
            }
            if (!nft.getIsSync()) {
                nft.setIsSync(true);
            }

            Long amount = Long.valueOf(log.getAmount().toString());

            if (log.getFrom().equalsIgnoreCase("0x0000000000000000000000000000000000000000")) {
                // mint transfer
                nft.setQuantity(nft.getQuantity() + amount);
            } else if (log.getTo().equalsIgnoreCase("0x0000000000000000000000000000000000000000")) {
                // burn transfer
                Long leftQuanlity = nft.getQuantity() - amount;
                nft.setQuantity(leftQuanlity);
                if (leftQuanlity.compareTo(0L) <= 0) {
                    nft.setQuantity(0L);
                    nft.setDeleted(true);
                }
            }

            Long time = Long.valueOf(log.getBlockTimestamp().toString());

            this.nftItemsManager.decQuanlity(log.getAddress(), tokenId, amount, log.getFrom(), time);
            this.nftItemsManager.incQuanlity(log.getAddress(), tokenId, amount, log.getTo(), time);
            this.transferLogManager.save(log);
            this.update(nft);
        }


    }

    private TtContractNft get(String address, String tokenId) {
        QueryWrapper<TtContractNft> wrapper = new QueryWrapper<>();
        wrapper.eq(TtContractNft.ADDRESS, address)
                .eq(TtContractNft.TOKEN_ID, tokenId)
                .eq(BaseEntity.DELETED, false);
        return this.baseService.getByCondition(TtContractNft.class, wrapper);
    }

    public TtContractNft add(TtTransferLog log, List<TtContractNft> contractNftList) {
        TtContractNft nft = new TtContractNft();
        for (TtContractNft ttContractNft : contractNftList) {
            if (log.getAddress().equals(ttContractNft.getAddress()) && log.getTokenId().equals(ttContractNft.getTokenId())) {
                nft = ttContractNft;
            }
        }
        this.save(nft);
        return nft;
    }

    private Integer save(TtContractNft nft) {
        return this.baseService.save(nft);
    }

    public Integer update(TtContractNft nft) {
        return this.baseService.update(nft);
    }

    public TtContractNft getByAddressAndTokenId(String address, String tokenId) {
        QueryWrapper<TtContractNft> wrapper = new QueryWrapper<>();
        wrapper.eq(TtContractNft.ADDRESS, address)
                .eq(TtContractNft.TOKEN_ID, tokenId)
                .eq(TtContractNft.DELETED, false);
        return this.baseService.getByCondition(TtContractNft.class, wrapper);
    }
}
