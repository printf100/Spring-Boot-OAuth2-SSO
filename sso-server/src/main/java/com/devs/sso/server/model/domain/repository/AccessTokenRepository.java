package com.devs.sso.server.model.domain.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.devs.sso.server.model.domain.entity.AccessToken;

public interface AccessTokenRepository extends CrudRepository<AccessToken, String> {
	//
	AccessToken findByTokenIdAndClientId(String tokenId, String clientId);
	
	int deleteByUserName(String userName);
	
	List<AccessToken> findByUserName(String userName);
}
