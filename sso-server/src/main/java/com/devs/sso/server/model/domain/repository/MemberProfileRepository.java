package com.devs.sso.server.model.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devs.sso.server.model.domain.entity.MemberProfile;

@Repository
public interface MemberProfileRepository extends JpaRepository<MemberProfile, Integer> {

}
