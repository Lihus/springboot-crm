package com.springboot.modules.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.springboot.core.exception.CrmException;
import com.springboot.modules.system.entity.Customer;
import com.springboot.modules.system.entity.CustomerFlow;
import com.springboot.modules.system.mapper.CustomerFlowMapper;
import com.springboot.modules.system.mapper.CustomerMapper;
import com.springboot.modules.system.query.CustomerQuery;
import com.springboot.modules.system.query.FlowQuery;
import com.springboot.modules.system.service.CustomerService;
import com.springboot.utils.PageResultSet;
import com.springboot.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.weekend.Weekend;
import tk.mybatis.mapper.weekend.WeekendCriteria;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    @Autowired
    private CustomerFlowMapper customerFlowMapper;

    @Override
    public PageResultSet<Customer> findByPage(CustomerQuery customerQuery) {
        if (!StringUtils.isEmpty(customerQuery.getOrderBy())) {
            PageHelper.orderBy(customerQuery.getOrderBy());
        }

        Weekend<Customer> example = Weekend.of(Customer.class);
        WeekendCriteria<Customer, Object> criteria = example.weekendCriteria();

        if (!StringUtils.isEmpty(customerQuery.getName())) {
            criteria.andLike(Customer::getName, "%" + customerQuery.getName() + "%");
        }

        if (null != customerQuery.getPrincipalId()) {
            criteria.andEqualTo(Customer::getPrincipalId, customerQuery.getPrincipalId());
        }

        PageHelper.offsetPage(customerQuery.getOffset(), customerQuery.getLimit());
        List<Customer> dtoList = customerMapper.selectByExample(example);

        long total = customerMapper.selectCountByExample(example);
        PageResultSet<Customer> resultSet = new PageResultSet<>();
        resultSet.setRows(dtoList);
        resultSet.setTotal(total);
        return resultSet;
    }

    @Override
    public List<Customer> findByPrinciple(Long principle) {
        Customer customer = new Customer().setPrincipalId(principle);
        return customerMapper.select(customer);
    }

    @Override
    public List<Customer> findAll() {
        return customerMapper.selectAll();
    }

    @Override
    public Customer findOne(Long customerId) {
        return customerMapper.selectByPrimaryKey(customerId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void createCustomer(Customer customer) {
        Customer u = findByName(customer.getName());
        if (u != null) {
            throw new CrmException(ResultCodeEnum.FAILED_USER_ALREADY_EXIST);
        }
        customerMapper.insertSelective(customer);
    }

    @Override
    public void updateCustomer(Customer customer) {
        int i = customerMapper.updateByPrimaryKeySelective(customer);
        System.out.println(i);
    }

    @Override
    public void deleteCustomer(Long customerId) {
        customerMapper.deleteByPrimaryKey(customerId);
    }

    @Override
    public PageResultSet<CustomerFlow> findFlowPage(FlowQuery flowQuery) {
        if (!StringUtils.isEmpty(flowQuery.getOrderBy())) {
            PageHelper.orderBy(flowQuery.getOrderBy());
        }

        Weekend<CustomerFlow> example = Weekend.of(CustomerFlow.class);
        WeekendCriteria<CustomerFlow, Object> criteria = example.weekendCriteria();

        if (null != flowQuery.getCustomerId()) {
            criteria.andEqualTo(CustomerFlow::getCustomerId, flowQuery.getCustomerId());
        }

        PageHelper.offsetPage(flowQuery.getOffset(), flowQuery.getLimit());
        List<CustomerFlow> dtoList = customerFlowMapper.selectByExample(example);

        long total = customerFlowMapper.selectCountByExample(example);
        PageResultSet<CustomerFlow> resultSet = new PageResultSet<>();
        resultSet.setRows(dtoList);
        resultSet.setTotal(total);
        return resultSet;
    }

    @Override
    public void addFlow(CustomerFlow flow) {
        customerFlowMapper.insertUseGeneratedKeys(flow);
    }

    @Override
    public void delFlow(Long id) {
        customerFlowMapper.deleteByPrimaryKey(id);
    }

    @Override
    public Customer findByName(String username) {
        Customer customer = new Customer();
        customer.setName(username);
        List<Customer> customers = customerMapper.select(customer);
        if (customers == null) {
            return null;
        }
        if (customers.size() < 1) {
            return null;
        }
        return customers.get(0);
    }
}
