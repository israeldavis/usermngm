package com.buildingblocks.base.dtos;

public class OrderDTO {

    private Long orderid;
    private String orderdescription;

    public OrderDTO() {}

    public OrderDTO(String orderdescription) {
        this.orderdescription = orderdescription;
    }

    public Long getOrderid() {
        return orderid;
    }

    public void setOrderid(Long orderid) {
        this.orderid = orderid;
    }

    public String getOrderdescription() {
        return orderdescription;
    }

    public void setOrderdescription(String orderdescription) {
        this.orderdescription = orderdescription;
    }
}
