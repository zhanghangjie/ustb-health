package com.ustb.mapper;

import com.ustb.pojo.Member;

public interface MemberMapper {
    Member findByPhone(String telephone);

    void add(Member member);

    String getNameById(Integer memberId);
}
