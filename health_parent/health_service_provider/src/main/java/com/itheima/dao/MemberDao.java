package com.itheima.dao;

import com.itheima.pojo.Member;

import java.util.List;

public interface MemberDao {
    Member findBytelephone(String telephone);

    void add(Member member);


    Integer findMemberCountBeforeDate(String month);

    Integer findMemberCountByDate(String today);

    Integer findMemberTotalCount();

    Integer findMemberCountAfterDate(String afterday);


}
