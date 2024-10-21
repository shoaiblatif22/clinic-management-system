#!/bin/bash

# Wait for PostgreSQL to start (you may adjust the sleep duration as necessary)
sleep 10

# Check if the database exists
if psql -h db -U myuser -d postgres -c '\l' | grep -q clinical_management_db; then
    # Drop the existing database if it exists
    echo "Dropping existing database: clinical_management_db"
    psql -h db -U myuser -d postgres -c "DROP DATABASE clinical_management_db;"
else
    echo "Database clinical_management_db does not exist, no need to drop."
fi

# Create the new database
echo "Creating new database: clinical_management_db"
psql -h db -U myuser -d postgres -c "CREATE DATABASE clinical_management_db;"

