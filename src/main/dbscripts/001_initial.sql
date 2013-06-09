SET statement_timeout = 0;
SET client_encoding = 'UTF8';
SET standard_conforming_strings = on;
SET check_function_bodies = false;
SET client_min_messages = warning;

CREATE EXTENSION IF NOT EXISTS plpgsql WITH SCHEMA pg_catalog;

SET default_with_oids = false;

CREATE TABLE setting (
    id character varying(255) NOT NULL,
    value character varying(255)
);

CREATE TABLE language (
    id bigint NOT NULL,
    iso_639_1 character varying(2) NOT NULL,
    iso_639_2 character varying(3) NOT NULL
);

CREATE TABLE users (
    id bigint NOT NULL,
    email character varying(255) NOT NULL,
    firstname character varying(255) NOT NULL,
    lastname character varying(255) NOT NULL,
    password_digest character varying(255) NOT NULL,
    registration_date timestamp without time zone NOT NULL,
    active boolean NOT NULL,
    role character varying(255) NOT NULL,
    client_language CHARACTER VARYING(2) NOT NULL DEFAULT 'en',
    country_region_id BIGINT NOT NULL
);

CREATE TABLE user_activation_token (
    token character varying(255) NOT NULL,
    user_id bigint NOT NULL
);

CREATE TABLE user_remind_password_token (
    token character varying(255) NOT NULL,
    user_id bigint NOT NULL,
    generation_date timestamp without time zone NOT NULL
);

CREATE TABLE remember_me_token (
  id bigint NOT NULL,
  created timestamp without time zone NOT NULL,
  last_use timestamp without time zone NOT NULL,
  remote_addr character varying(63),
  token character varying(255) NOT NULL,
  user_agent_hash character varying(255),
  user_id bigint NOT NULL
);

CREATE SEQUENCE remember_me_token_id_sequence
  START WITH 1
  INCREMENT BY 1
  NO MINVALUE
  NO MAXVALUE
  CACHE 1;

CREATE SEQUENCE users_id_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

CREATE SEQUENCE language_id_sequence
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;

    CREATE TABLE country (id BIGINT NOT NULL, name CHARACTER VARYING(52) NOT NULL);



CREATE TABLE country_region (id BIGINT NOT NULL, name CHARACTER VARYING(255) NOT NULL, country_id BIGINT NOT NULL);


ALTER TABLE ONLY setting
    ADD CONSTRAINT setting_pkey PRIMARY KEY (id);

ALTER TABLE ONLY language
    ADD CONSTRAINT language_pkey PRIMARY KEY (id);

ALTER TABLE ONLY users
    ADD CONSTRAINT unique___users___email UNIQUE (email);

ALTER TABLE ONLY users
    ADD CONSTRAINT users_pkey PRIMARY KEY (id);

ALTER TABLE ONLY user_activation_token
    ADD CONSTRAINT user_activation_token_pkey PRIMARY KEY (user_id);

ALTER TABLE ONLY user_activation_token
    ADD CONSTRAINT fk___user_activation_token___user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE ONLY user_remind_password_token
    ADD CONSTRAINT user_remind_password_token_pkey PRIMARY KEY (user_id);

ALTER TABLE ONLY user_remind_password_token
    ADD CONSTRAINT fk___user_remind_password_token___user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE;

ALTER TABLE ONLY remember_me_token
ADD CONSTRAINT remember_me_token_pkey PRIMARY KEY (id);

ALTER TABLE ONLY remember_me_token
ADD CONSTRAINT fk___remember_me_token___user FOREIGN KEY (user_id) REFERENCES users(id);

ALTER TABLE ONLY country
ADD CONSTRAINT country_name_key UNIQUE (name);


ALTER TABLE ONLY country
ADD CONSTRAINT country_pkey PRIMARY KEY (id);

ALTER TABLE ONLY country_region
ADD CONSTRAINT country_region_pkey PRIMARY KEY (id);

ALTER TABLE ONLY country_region
ADD CONSTRAINT fk___country___country_region FOREIGN KEY (country_id) REFERENCES country (id);

ALTER TABLE ONLY users
ADD CONSTRAINT fk___user___country_region FOREIGN KEY (country_region_id) REFERENCES country_region (id);

CREATE SEQUENCE country_id_sequence START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
CREATE SEQUENCE country_region_id_sequence START WITH 1 INCREMENT BY 1 NO MINVALUE NO MAXVALUE CACHE 1;
