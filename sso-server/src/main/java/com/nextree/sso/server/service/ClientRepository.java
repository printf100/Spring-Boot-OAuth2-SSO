package com.nextree.sso.server.service;

import org.springframework.data.repository.CrudRepository;

import com.nextree.sso.server.domain.Client;

public interface ClientRepository extends CrudRepository<Client, String> {
	//
}
