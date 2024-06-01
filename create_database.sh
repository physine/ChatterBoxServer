#!/bin/bash

# source the .env file
set -a
source .env
set +a

# check if the database exists
check_db_exists() {
  local DB_NAME=$1
  local db_password=$2
  PGPASSWORD="$PASSWORD" psql -h localhost -p 5432 -U "$USERNAME" -d postgres -tc "SELECT 1 FROM pg_database WHERE datname = '$DB_NAME'" | grep -q 1
}

# create the database
create_db(){
  local DB_NAME=$1
  PGPASSWORD="$PASSWORD" psql -h localhost -p 5432 -U "$USERNAME" -d postgres <<EOSQL
CREATE DATABASE $DB_NAME
WITH
OWNER = $USERNAME
ENCODING = 'UTF8'
LC_COLLATE = 'en_US.UTF-8'
LC_CTYPE = 'en_US.UTF-8'
TEMPLATE = template0
TABLESPACE = pg_default
CONNECTION LIMIT = -1;
EOSQL
}

create_db_if_not_exists(){
  local DB_NAME=$1
  if check_db_exists $DB_NAME; then
    echo "Database $DB_NAME already exists."
  else
    create_db $DB_NAME
    echo "Database $DB_NAME created."
  fi
}

if $PRODUCTION_MODE; then
  USERNAME=$POSTGRES_USERNAME_PROD
  PASSWORD=$POSTGRES_PASSWORD_PROD
else
  USERNAME=$POSTGRES_USERNAME_DEV
  PASSWORD=$POSTGRES_PASSWORD_DEV
fi

if $PRODUCTION_MODE; then
  echo "Production Mode On. (Prod DB Only)."
  create_db_if_not_exists $POSTGRES_DB_PROD
else
  echo "Production Mode Off. (Dev & Test DBs)."
  create_db_if_not_exists $POSTGRES_DB_TEST
  create_db_if_not_exists $POSTGRES_DB_DEV
fi