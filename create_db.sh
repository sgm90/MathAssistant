#!/bin/bash
sudo -u postgres psql -c "CREATE USER myuser WITH ENCRYPTED PASSWORD 'mypassword';"
sudo -u postgres createdb -O myuser mydatabase


