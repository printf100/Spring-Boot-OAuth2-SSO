package com.devs.sso.server.model.domain.repository;

import org.springframework.data.repository.CrudRepository;

import com.devs.sso.server.model.domain.entity.Client;

public interface ClientRepository extends CrudRepository<Client, String> {
	//
}
