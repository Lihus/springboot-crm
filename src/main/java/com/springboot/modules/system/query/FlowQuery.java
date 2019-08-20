package com.springboot.modules.system.query;

import com.springboot.modules.system.entity.Customer;
import com.springboot.utils.BaseQuery;

public class FlowQuery extends BaseQuery<Customer> {

    private Long customerId;

    public Long getCustomerId() {
        return customerId;
    }

    public FlowQuery setCustomerId(Long customerId) {
        this.customerId = customerId;
        return this;
    }
}
