-- create the databases
CREATE DATABASE IF NOT EXISTS db;
CREATE DATABASE IF NOT EXISTS keycloak_db;

-- create the users for each database
GRANT CREATE, ALTER, INDEX, LOCK TABLES, REFERENCES, UPDATE, DELETE, DROP, SELECT, INSERT ON `keycloak_db`.* TO 'user'@'%';

FLUSH PRIVILEGES;