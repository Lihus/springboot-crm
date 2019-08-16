package com.springboot.modules.system.web;

import com.springboot.core.annotation.SystemLog;
import com.springboot.modules.system.entity.Customer;
import com.springboot.modules.system.entity.CustomerFlow;
import com.springboot.modules.system.entity.User;
import com.springboot.modules.system.query.CustomerQuery;
import com.springboot.modules.system.service.CustomerService;
import com.springboot.modules.system.service.UserService;
import com.springboot.utils.BaseController;
import com.springboot.utils.PageResultSet;
import com.springboot.utils.Result;
import com.springboot.utils.ResultCodeEnum;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/customer")
public class CustomerController extends BaseController {

    @Autowired
    private CustomerService customerService;

    @Autowired
    private UserService userService;

    @GetMapping
    @RequiresPermissions("customer:view")
    public String page(Model model) {
        setCommonData(model);
        return "system/customer";
    }

    @ResponseBody
    @RequestMapping("/list")
    @RequiresPermissions("customer:view")
    public PageResultSet<Customer> list(CustomerQuery customerQuery, HttpServletRequest request) {
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        User user = userService.findByUsername(username);
        if(!SecurityUtils.getSubject().hasRole("admin")) {
            customerQuery.setPrincipleId(user.getId());
        }
        return customerService.findByPage(customerQuery);
    }

    @ResponseBody
    @PostMapping("/create")
    @RequiresPermissions("customer:create")
    @SystemLog("用户管理创建客户")
    public Result<?> create(@Valid Customer customer) {
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        User user = userService.findByUsername(username);
        customer.setCreatorId(user.getId());
        customer.setCreatorName(user.getUsername());
        User principle = userService.findOne(customer.getPrincipalId());
        customer.setPrincipalName(principle.getUsername());
        customerService.createCustomer(customer);
        return Result.success();
    }

    @ResponseBody
    @PostMapping("/update")
    @RequiresPermissions("customer:update")
    @SystemLog("用户管理更新客户")
    public Result<?> update(Customer customer) {
        customerService.updateCustomer(customer);
        return Result.success();
    }

    @ResponseBody
    @PostMapping("/delete")
    @RequiresPermissions("customer:delete")
    @SystemLog("用户管理删除客户")
    public Result<?> delete(@RequestParam("id") Long[] ids, HttpServletRequest request) {
        // 当前用户
        String username = (String) SecurityUtils.getSubject().getPrincipal();
        User user = userService.findByUsername(username);
        List<Customer> customerList;
        customerList = customerService.findByPrinciple(user.getId());
        boolean isSelf = Arrays.stream(ids).anyMatch(id -> !customerList.stream().map(Customer::getId).anyMatch(cid -> cid.equals(id)));
        if (isSelf) {
            return Result.failure(ResultCodeEnum.FAILED_DEL_OWN);
        }
        Arrays.asList(ids).stream().filter(id -> customerList.stream().anyMatch(customer -> customer.getId().equals(id)))
                .forEach(id -> customerService.deleteCustomer(id));
        return Result.success();
    }

    @ResponseBody
    @RequiresPermissions("customer:update")
    @PostMapping("/{id}/flow")
    @SystemLog("更新客户成交情况")
    public Result<?> addFlow(@PathVariable("id") Long id, CustomerFlow flow) {
        flow.setCustomerId(id);
        customerService.addFlow(flow);
        return Result.success();
    }

    private void setCommonData(Model model) {
        model.addAttribute("userList", userService.findAll());
    }
}
