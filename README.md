# Spring-Boot-OAuth2-SSO

### *Spring Boot, Spring Security OAuth2를 이용한 Single Sign On server와 client 코드*

* * *

#### **server table (Oracle)**
```
create table oauth_client_details (
  client_id VARCHAR2(256) PRIMARY KEY,
  resource_ids VARCHAR2(256),
  client_secret VARCHAR2(256),
  scope VARCHAR2(256),
  authorized_grant_types VARCHAR2(256),
  web_server_redirect_uri VARCHAR2(256),
  logout_uri VARCHAR2(256),
  base_uri VARCHAR2(256),
  authorities VARCHAR2(256),
  access_token_validity number,
  refresh_token_validity number,
  autoapprove VARCHAR2(256),
  additional_information VARCHAR(4000)
);

create table oauth_access_token (
  token_id VARCHAR2(256),
  authentication_id VARCHAR2(256) PRIMARY KEY,
  user_name VARCHAR2(256),
  client_id VARCHAR2(256),
  refresh_token VARCHAR2(256),
  authentication LONG RAW,
  token RAW(2000)
);

create table oauth_refresh_token (
  token_id VARCHAR2(256),
  token RAW(2000),
  authentication LONG RAW
);

create table oauth_code (
  code VARCHAR2(256),
  authentication LONG RAW
);

create table oauth_approvals (
   userId VARCHAR2(256),
   clientId VARCHAR2(256),
   scope VARCHAR2(256),
   status VARCHAR2(10),
   expiresAt TIMESTAMP,
   lastModifiedAt TIMESTAMP
);

insert into oauth_client_details (client_id, client_secret, resource_ids, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove, logout_uri, base_uri) values ('DEVS_MAIN_id', 'DEVS_MAIN_secret', null, 'read', 'authorization_code', 'http://localhost:8787/oauthCallback', 'ROLE_YOUR_CLIENT', 36000, 2592000, null, 'true', 'http://localhost:8787/logout', 'http://localhost:8787/me');
insert into oauth_client_details (client_id, client_secret, resource_ids, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove, logout_uri, base_uri) values ('DEVS_GROUP_id', 'DEVS_GROUP_secret', null, 'read', 'authorization_code', 'http://localhost:8888/oauthCallback', 'ROLE_YOUR_CLIENT', 36000, 2592000, null, 'true', 'http://localhost:8888/logout', 'http://localhost:8888/me');
insert into oauth_client_details (client_id, client_secret, resource_ids, scope, authorized_grant_types, web_server_redirect_uri, authorities, access_token_validity, refresh_token_validity, additional_information, autoapprove, logout_uri, base_uri) values ('TEMP_id', 'TEMP_secret', null, 'read', 'authorization_code', 'http://localhost:8989/oauthCallback', 'ROLE_YOUR_CLIENT', 36000, 2592000, null, 'true', 'http://localhost:8989/logout', 'http://localhost:8989/me');
```
