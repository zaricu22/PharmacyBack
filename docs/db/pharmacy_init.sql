-- Pharmacy — database initialisation script
-- Creates the database, schema, tables, and constraints from scratch.
--
-- Target:  CockroachDB / PostgreSQL — uses three-part names (pharmacy.public.table).
--          MySQL:  replace three-part names with two-part (pharmacy.table),
--                  replace UUID with CHAR(36), wrap identifiers in backticks.
--
-- Run in two steps separately to avoid errors:
--   1. CREATE DATABASE (connect as superuser)
--   2. Everything else (connect to the new database)

-- ============================================================
-- Database
-- ============================================================

CREATE DATABASE IF NOT EXISTS pharmacy;

-- ============================================================
-- Schema
-- ============================================================

CREATE SCHEMA IF NOT EXISTS public;

-- ============================================================
-- Safe drop order (children first)
-- ============================================================

DROP TABLE IF EXISTS pharmacy.public.tokens;
DROP TABLE IF EXISTS pharmacy.public.products;
DROP TABLE IF EXISTS pharmacy.public.manufacturers;
DROP TABLE IF EXISTS pharmacy.public.users;

-- ============================================================
-- Tables
-- ============================================================

CREATE TABLE IF NOT EXISTS pharmacy.public.users (
    id         UUID         NOT NULL,
    username   VARCHAR(255) NOT NULL,
    password   VARCHAR(255) NOT NULL,
    email      VARCHAR(255) NOT NULL,
    firstname  VARCHAR(255) NOT NULL,
    lastname   VARCHAR(255) NOT NULL,
    role       VARCHAR(50)  NOT NULL,  -- 'USER' | 'ADMIN'

    CONSTRAINT pk_users PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS pharmacy.public.tokens (
    id          UUID         NOT NULL,
    token       VARCHAR(512) NOT NULL,
    token_type  VARCHAR(50)  NOT NULL,  -- 'BEARER_ACCESS' | 'BEARER_REFRESH'
    expired     BOOLEAN      NOT NULL,
    revoked     BOOLEAN      NOT NULL,
    user_id     UUID         NULL,

    CONSTRAINT pk_tokens PRIMARY KEY (id),
    CONSTRAINT fk_tokens_user
        FOREIGN KEY (user_id)
        REFERENCES pharmacy.public.users (id)
        ON DELETE SET NULL
);

CREATE TABLE IF NOT EXISTS pharmacy.public.manufacturers (
    id    UUID         NOT NULL,  -- MySQL: CHAR(36)
    name  VARCHAR(255) NOT NULL,

    CONSTRAINT pk_manufacturers PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS pharmacy.public.products (
    id            UUID         NOT NULL,  -- MySQL: CHAR(36)
    name          VARCHAR(255) NOT NULL,
    price         INT8         NOT NULL,  -- price in RSD; MySQL: BIGINT
    expiry_date   DATE         NOT NULL,
    manufacturer_id  UUID         NOT NULL,  -- FK → manufacturers.id; MySQL: CHAR(36)

    CONSTRAINT pk_products PRIMARY KEY (id),
    CONSTRAINT fk_products_manufacturer
        FOREIGN KEY (manufacturer_id)
        REFERENCES pharmacy.public.manufacturers (id)
        ON DELETE RESTRICT
);

-- ============================================================
-- Constraints
-- ============================================================

ALTER TABLE pharmacy.public.products
    ADD CONSTRAINT chk_price_positive
    CHECK (price > 0);
