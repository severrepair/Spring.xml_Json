package com.bitc.xmltest.service;

import com.bitc.xmltest.dto.PharmacyFullDateItemDto;

import java.util.List;

public interface PharmacyFullDataService {
    List<PharmacyFullDateItemDto> getItemList() throws Exception;

    List<PharmacyFullDateItemDto> getItemListUrl(String strUrl) throws Exception;
}
