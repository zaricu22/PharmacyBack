-- Pharmacy — sample seed data
-- Requires pharmacy_init.sql to have been applied first.
-- 2 manufacturers · 10 products
--
-- Target:  CockroachDB / PostgreSQL
--          MySQL:  replace UUID literals with CHAR(36) string values (no cast needed).

-- ============================================================
-- WIPE ALL EXISTING DATA (uncomment if re-seeding)
-- ============================================================
-- TRUNCATE TABLE pharmacy.public.tokens,
--               pharmacy.public.products,
--               pharmacy.public.manufacturers,
--               pharmacy.public.users CASCADE;

-- ============================================================
-- Users
-- role: 'USER' | 'ADMIN'
-- password: BCrypt-hashed; plain-text shown in comment
-- ============================================================

INSERT INTO pharmacy.public.users
    (id, username, password, email, firstname, lastname, role)
VALUES
    ('a1b2c3d4-e5f6-7890-abcd-ef1234567890', 'user1', '$2a$10$008/Eu9dmOK4Is6QQ1jXEOtj3uzmzh8htPoInxkOwrDa2sgDyQLZa', 'user1@pharmacy.com', 'Marko',  'Markovic', 'USER'),   -- password: password
    ('b2c3d4e5-f6a7-8901-bcde-f12345678901', 'admin1', '$2a$10$008/Eu9dmOK4Is6QQ1jXEOtj3uzmzh8htPoInxkOwrDa2sgDyQLZa', 'admin1@pharmacy.com', 'Ana', 'Anic',     'ADMIN');  -- password: password

-- ============================================================
-- Tokens
-- token_type: 'BEARER_ACCESS' | 'BEARER_REFRESH'
-- expired/revoked: true = invalid, false = valid
-- ============================================================

INSERT INTO pharmacy.public.tokens
    (id, token, token_type, expired, revoked, user_id)
VALUES
    ('c3d4e5f6-a7b8-9012-cdef-123456789012', 'sample-access-token-user1',  'BEARER_ACCESS',  true, true, 'a1b2c3d4-e5f6-7890-abcd-ef1234567890'),
    ('d4e5f6a7-b8c9-0123-defa-234567890123', 'sample-refresh-token-user1', 'BEARER_REFRESH', true, true, 'a1b2c3d4-e5f6-7890-abcd-ef1234567890'),
    ('e5f6a7b8-c9d0-1234-efab-345678901234', 'sample-access-token-admin1', 'BEARER_ACCESS',  true, true, 'b2c3d4e5-f6a7-8901-bcde-f12345678901');

-- ============================================================
-- Manufacturers
-- ============================================================

INSERT INTO pharmacy.public.manufacturers (id, name) VALUES
    ('41451a7d-5df1-493c-a803-2004b50ff766', 'Galenika'),
    ('eb2649a0-b056-4c51-93b7-242a70a757d5', 'Hemofarm');

-- ============================================================
-- Products
-- manufacturer: Galenika = 41451a7d-5df1-493c-a803-2004b50ff766
--               Hemofarm  = eb2649a0-b056-4c51-93b7-242a70a757d5
-- price in INT8 (RSD); expiry_date in ISO 8601 (YYYY-MM-DD)
-- ============================================================

INSERT INTO pharmacy.public.products (id, name, price, expiry_date, manufacturer_id) VALUES
    ('1fc852f7-d261-405a-8618-cdab4a8eab48', 'Oligovit',   800, '2024-05-26', '41451a7d-5df1-493c-a803-2004b50ff766'),
    ('4ed32c77-b213-42b6-9fc1-b6f38f6aa04c', 'Beviplex',   240, '2024-07-19', '41451a7d-5df1-493c-a803-2004b50ff766'),
    ('ef7232ca-0af3-4e77-8103-730087dd4fa1', 'Pantenol',   450, '2025-08-26', '41451a7d-5df1-493c-a803-2004b50ff766'),
    ('d6a1929d-1e38-4805-8543-e514eb851e0f', 'Defrinol',   360, '2026-03-05', '41451a7d-5df1-493c-a803-2004b50ff766'),
    ('4c20a43e-3bf7-4055-beb3-92d2159b2ba6', 'Flonivin',   730, '2026-04-13', '41451a7d-5df1-493c-a803-2004b50ff766'),
    ('ff980565-c784-4fe8-840c-dcb4b31d8e48', 'Magnetrans', 390, '2025-05-21', 'eb2649a0-b056-4c51-93b7-242a70a757d5'),
    ('53398ca0-886c-4121-a277-db321f6a516e', 'Febricet',   940, '2026-02-07', 'eb2649a0-b056-4c51-93b7-242a70a757d5'),
    ('2ff257d4-2d6e-48cc-b862-cab121819109', 'Midol',      670, '2025-08-30', 'eb2649a0-b056-4c51-93b7-242a70a757d5'),
    ('f5413ba8-2869-41cb-a3ff-5c69f0c781d2', 'Panlax',     790, '2027-03-02', 'eb2649a0-b056-4c51-93b7-242a70a757d5'),
    ('9cec6db9-8aaa-4bc8-8352-8ac6c65b1e15', 'Pressing',   560, '2026-06-07', 'eb2649a0-b056-4c51-93b7-242a70a757d5');
