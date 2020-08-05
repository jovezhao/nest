package com.zhaofujun.nest.test;

import com.zhaofujun.nest.context.model.Entity;
import com.zhaofujun.nest.context.model.StringIdentifier;
import com.zhaofujun.nest.core.ValueObject;

import java.util.ArrayList;
import java.util.List;

public class Teacher extends Entity<StringIdentifier> {
    private User user;
    private int tid;
    private List<User> userList=new ArrayList<>();
    private Address address;
    private List<Address> addressList=new ArrayList<>();

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getTid() {
        return tid;
    }

    public void setTid(int tid) {
        this.tid = tid;
    }

    public List<User> getUserList() {
        return userList;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    public List<Address> getAddressList() {
        return addressList;
    }

    public void setAddressList(List<Address> addressList) {
        this.addressList = addressList;
    }
}
