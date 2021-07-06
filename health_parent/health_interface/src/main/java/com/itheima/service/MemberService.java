package com.itheima.service;

import com.itheima.pojo.Member;

import java.util.List;

public interface MemberService {
    Member findBytelephone(String telephone);

    void add(Member bytelephone);

    List<Integer> findByMonths(List<String> months);
}
