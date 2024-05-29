#!/bin/bash

# Source the .env file
set -a
source .env
set +a

# Function to check if the database exists
check_db_exists() {
    psql -h localhost -p 5432 -U "$POSTGRES_USER" -d postgres -tc "SELECT 1 FROM pg_database WHERE datname = '$POSTGRES_DB'" | grep -q 1
}

# Check if the database exists
if check_db_exists; then
    echo "Database $POSTGRES_DB already exists."
else
    # Create the database if it doesn't exist
    psql -h localhost -p 5432 -U "$POSTGRES_USER" -d postgres <<EOSQL
CREATE DATABASE $POSTGRES_DB 
WITH 
OWNER = $POSTGRES_USER
ENCODING = 'UTF8'
LC_COLLATE = 'en_US.UTF-8'
LC_CTYPE = 'en_US.UTF-8'
TEMPLATE = template0
TABLESPACE = pg_default
CONNECTION LIMIT = -1;
EOSQL
    echo "Database $POSTGRES_DB created."
fi
