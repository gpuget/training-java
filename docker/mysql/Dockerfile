FROM mysql:5.5.55
MAINTAINER Gaetan <gpuget@excilys.com>

ENV MYSQL_ROOT_PASSWORD=root

COPY ./scripts/1-SCHEMA.sql /docker-entrypoint-initdb.d/1-SCHEMA.sql
COPY ./scripts/2-PRIVILEGES.sql /docker-entrypoint-initdb.d/2-PRIVILEGES.sql
COPY ./scripts/3-ENTRIES.sql /docker-entrypoint-initdb.d/3-ENTIES.sql
