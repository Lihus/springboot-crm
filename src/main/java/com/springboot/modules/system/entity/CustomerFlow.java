package com.springboot.modules.system.entity;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Table(name = "customer_flow")
public class CustomerFlow {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "客户不能为空")
    private Long customerId;

    @NotNull(message = "成交金额不能为空")
    private Double amount;

    private Date createTs;

    public Long getId() {
        return id;
    }

    public CustomerFlow setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getCustomerId() {
        return customerId;
    }

    public CustomerFlow setCustomerId(Long customerId) {
        this.customerId = customerId;
        return this;
    }

    public Double getAmount() {
        return amount;
    }

    public CustomerFlow setAmount(Double amount) {
        this.amount = amount;
        return this;
    }

    public Date getCreateTs() {
        return createTs;
    }

    public CustomerFlow setCreateTs(Date createTs) {
        this.createTs = createTs;
        return this;
    }
}
