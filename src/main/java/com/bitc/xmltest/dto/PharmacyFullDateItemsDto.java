package com.bitc.xmltest.dto;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.util.List;

@XmlRootElement(name = "items")
public class PharmacyFullDateItemsDto {
    private List<PharmacyFullDateItemDto> itemList;

    @XmlElement(name = "item")
    public List<PharmacyFullDateItemDto> getItemList() {
        return itemList;
    }

    public void setItemList(List<PharmacyFullDateItemDto> itemList) {
        this.itemList = itemList;
    }
}
