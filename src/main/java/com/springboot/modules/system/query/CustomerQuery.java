package com.springboot.modules.system.query;

import com.springboot.modules.system.entity.Customer;
import com.springboot.utils.BaseQuery;

public class CustomerQuery extends BaseQuery<Customer> {

    private String name;

    private Long principalId;

    public String getName() {
        return name;
    }

    public CustomerQuery setName(String name) {
        this.name = name;
        return this;
    }

    public Long getPrincipalId() {
        return principalId;
    }

    public CustomerQuery setPrincipalId(Long principalId) {
        this.principalId = principalId;
        return this;
    }
}
