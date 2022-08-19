package com.fingerchar.api.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.fingerchar.api.constant.SysConfConstant;
import com.fingerchar.core.base.service.IBaseService;
import com.fingerchar.db.domain.TtNftItems;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TtNftItemsManager {
    @Autowired
    IBaseService baseService;

    public Integer decQuanlity(
            String address,
            String tokenId,
            Long quanlity,
            String owner,
            Long time){
        if(owner.equalsIgnoreCase("0x0000000000000000000000000000000000000000")){
            return 0;
        }
        TtNftItems nftItems = this.get(address, tokenId, owner);
        if(null == nftItems){
            return 0;
        }
        Long leftQuanlity = nftItems.getQuantity() - quanlity;
        if(leftQuanlity.compareTo(0L) <= 0){
            nftItems.setQuantity(0L);
            nftItems.setDeleted(true);
        }else{
            Long sellQuanlity = nftItems.getSellQuantity();
            if(leftQuanlity.compareTo(sellQuanlity) <= 0){
                nftItems.setSellQuantity(leftQuanlity);
            }
            nftItems.setQuantity(leftQuanlity);
        }
        nftItems.setUpdateTime(time);
        return this.update(nftItems);
    }

    public Integer incQuanlity(
            String address,
            String tokenId,
            Long quanlity,
            String owner,
            Long time){
        if(address.equalsIgnoreCase("0x0000000000000000000000000000000000000000")){
            return 0;
        }
        TtNftItems nftItems = this.get(address, tokenId, owner);
        if(null == nftItems){
            nftItems = new TtNftItems();
            nftItems.setAddress(address);
            nftItems.setTokenId(tokenId);
            nftItems.setItemOwner(owner);
            nftItems.setQuantity(quanlity);
            nftItems.setIsSync(true);
            nftItems.setCreateTime(time);
            nftItems.setUpdateTime(time);
            return this.save(nftItems);
        }else{
            nftItems.setQuantity(nftItems.getQuantity() + quanlity);
            if(!nftItems.getIsSync()){
                nftItems.setIsSync(true);
            }
            nftItems.setUpdateTime(time);
            return this.update(nftItems);
        }
    }

    public TtNftItems get(String address, String tokenId, String owner){
        QueryWrapper<TtNftItems> wrapper = new QueryWrapper<>();
        wrapper.eq(TtNftItems.ADDRESS, address)
                .eq(TtNftItems.TOKEN_ID,tokenId)
                .eq(TtNftItems.ITEM_OWNER, owner)
                .eq(TtNftItems.DELETED, false);
        return this.baseService.getByCondition(TtNftItems.class, wrapper);
    }

    public Integer update(TtNftItems nftItems){
        return this.baseService.update(nftItems);
    }

    public Integer save(TtNftItems nftItems){
        return this.baseService.save(nftItems);
    }

    public IPage<TtNftItems>  getByItemOwner(String owner, IPage<TtNftItems> pageInfo) {
        QueryWrapper<TtNftItems> wrapper = new QueryWrapper<>();
        wrapper.eq(TtNftItems.ITEM_OWNER, owner)
                .eq(TtNftItems.DELETED, false);
        return this.baseService.findByPage(TtNftItems.class,wrapper,pageInfo);
    }
}
