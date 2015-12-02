CREATE DATABASE berge
  WITH OWNER = berge_group
       ENCODING = 'UTF8'
       TABLESPACE = pg_default
       LC_COLLATE = 'de_DE.UTF-8'
       LC_CTYPE = 'de_DE.UTF-8'
       CONNECTION LIMIT = -1;

ALTER DATABASE berge
  SET search_path = "$user", public, topology;
  
CREATE EXTENSION postgis;