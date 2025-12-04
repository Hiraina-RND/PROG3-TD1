CREATE DATABASE "product_management_db";

CREATE USER "product_manager_user" WITH PASSWORD '123456';

GRANT CREATE ON SCHEMA public TO "product_manager_user";
