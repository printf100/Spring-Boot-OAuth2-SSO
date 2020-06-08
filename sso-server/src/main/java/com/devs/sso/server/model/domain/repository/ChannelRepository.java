package com.devs.sso.server.model.domain.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.devs.sso.server.model.domain.entity.Channel;

@Repository
public interface ChannelRepository extends JpaRepository<Channel, Integer> {

}
