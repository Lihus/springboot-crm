package com.springboot.modules.system.service;

import com.springboot.modules.system.entity.Customer;
import com.springboot.modules.system.entity.CustomerFlow;
import com.springboot.modules.system.entity.User;
import com.springboot.modules.system.query.CustomerQuery;
import com.springboot.modules.system.query.FlowQuery;
import com.springboot.utils.PageResultSet;

import java.util.List;

public interface CustomerService {

    /**
     * 查询客户
     *
     * @param customerQuery
     * @return
     */
    PageResultSet<Customer> findByPage(CustomerQuery customerQuery);

    /**
     * 通过ids查询
     *
     * @param principle
     * @return
     */
    List<Customer> findByPrinciple(Long principle);

    /**
     * 查询所有
     *
     * @return
     */
    List<Customer> findAll();

    /**
     * 查询单个
     *
     * @param customerId
     * @return
     */
    Customer findOne(Long customerId);

    /**
     * 创建用户组
     *
     * @param customer
     */
    void createCustomer(Customer customer);

    /**
     * 更新用户组
     *
     * @param customer
     */
    void updateCustomer(Customer customer);

    /**
     * 删除用户组
     *
     * @param customerId
     */
    void deleteCustomer(Long customerId);

    /**
     * 查询
     *
     * @param flowQuery
     * @return
     */
    PageResultSet<CustomerFlow> findFlowPage(FlowQuery flowQuery);

    /**
     * 新增
     *
     * @param flow
     * @return
     */
    void addFlow(CustomerFlow flow);

    /**
     * 删除
     *
     * @param id
     * @return
     */
    void delFlow(Long id);

    /**
     * 通过名字查询
     *
     * @param username
     * @return
     */
    Customer findByName(String username);
}
