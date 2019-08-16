package com.springboot.modules.system.service.impl;

import com.github.pagehelper.PageHelper;
import com.springboot.core.exception.CrmException;
import com.springboot.modules.system.entity.Customer;
import com.springboot.modules.system.entity.CustomerFlow;
import com.springboot.modules.system.entity.User;
import com.springboot.modules.system.mapper.CustomerFlowMapper;
import com.springboot.modules.system.mapper.CustomerMapper;
import com.springboot.modules.system.query.CustomerQuery;
import com.springboot.modules.system.service.CustomerService;
import com.springboot.utils.PageResultSet;
import com.springboot.utils.ResultCodeEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tk.mybatis.mapper.weekend.Weekend;
import tk.mybatis.mapper.weekend.WeekendCriteria;

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

        if (null != customerQuery.getPrincipleId()) {
            criteria.andEqualTo(Customer::getPrincipalId, customerQuery.getPrincipleId());
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
    @Transactional
    public void createCustomer(Customer customer) {
        Customer u = findByName(customer.getName());
        if (u != null) {
            throw new CrmException(ResultCodeEnum.FAILED_USER_ALREADY_EXIST);
        }
        // 加密密码
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
    public List<CustomerFlow> findFlow(Long customerId) {
        CustomerFlow flow = new CustomerFlow();
        flow.setCustomerId(customerId);
        return customerFlowMapper.select(flow);
    }

    @Override
    public void addFlow(CustomerFlow flow) {
        customerFlowMapper.insertSelective(flow);
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
