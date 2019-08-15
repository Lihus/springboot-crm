package com.springboot.modules.system.entity;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.Date;

@Table(name = "customer")
public class Customer {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "客户名不能为空")
    private String name;

    private String companyName;

    private String business;

    @NotBlank(message = "信息来源不能为空")
    private String informationSource;

    private Byte score;

    @NotBlank(message = "地址不能为空")
    private String address;

    @NotNull(message = "负责人不能为空")
    private Long principalId;

    private String principalName;

    private Long creatorId;

    private String creatorName;

    private Date createTs;

    private Date updateTs;

    private String remark;

    public Long getId() {
        return id;
    }

    public Customer setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public Customer setName(String name) {
        this.name = name;
        return this;
    }

    public String getCompanyName() {
        return companyName;
    }

    public Customer setCompanyName(String companyName) {
        this.companyName = companyName;
        return this;
    }

    public String getBusiness() {
        return business;
    }

    public Customer setBusiness(String business) {
        this.business = business;
        return this;
    }

    public String getInformationSource() {
        return informationSource;
    }

    public Customer setInformationSource(String informationSource) {
        this.informationSource = informationSource;
        return this;
    }

    public Byte getScore() {
        return score;
    }

    public Customer setScore(Byte score) {
        this.score = score;
        return this;
    }

    public String getAddress() {
        return address;
    }

    public Customer setAddress(String address) {
        this.address = address;
        return this;
    }

    public Long getPrincipalId() {
        return principalId;
    }

    public Customer setPrincipalId(Long principalId) {
        this.principalId = principalId;
        return this;
    }

    public String getPrincipalName() {
        return principalName;
    }

    public Customer setPrincipalName(String principalName) {
        this.principalName = principalName;
        return this;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public Customer setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
        return this;
    }

    public String getCreatorName() {
        return creatorName;
    }

    public Customer setCreatorName(String creatorName) {
        this.creatorName = creatorName;
        return this;
    }

    public Date getCreateTs() {
        return createTs;
    }

    public Customer setCreateTs(Date createTs) {
        this.createTs = createTs;
        return this;
    }

    public Date getUpdateTs() {
        return updateTs;
    }

    public Customer setUpdateTs(Date updateTs) {
        this.updateTs = updateTs;
        return this;
    }

    public String getRemark() {
        return remark;
    }

    public Customer setRemark(String remark) {
        this.remark = remark;
        return this;
    }
}
