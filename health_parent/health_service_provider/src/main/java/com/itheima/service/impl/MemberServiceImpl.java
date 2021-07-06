package com.itheima.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.dao.MemberDao;
import com.itheima.pojo.Member;
import com.itheima.service.MemberService;
import com.itheima.utils.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {
    @Autowired
    private MemberDao memberDao;

    @Override
    public Member findBytelephone(String telephone) {
        return memberDao.findBytelephone(telephone);
    }

    @Override
    public void add(Member member) {
        String password = member.getPassword();
        if (password != null) {
            String passwdmd5 = MD5Utils.md5(password);
            member.setPassword(passwdmd5);
        }
        memberDao.add(member);
    }

    @Override
    public List<Integer> findByMonths(List<String> months) {

        List<Integer> membercount = new ArrayList<>();
        for (String month : months) {
            Integer count = memberDao.findMemberCountBeforeDate(month);
            membercount.add(count);
        }


        return membercount;
    }
}
