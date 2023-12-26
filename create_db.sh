#!/bin/bash
#!/bin/bash
sudo -u postgres psql -c "CREATE USER myuser WITH ENCRYPTED PASSWORD 'mypassword';"
sudo -u postgres psql -c "CREATE DATABASE mydatabase WITH OWNER myuser;"
sudo -u postgres psql -c "GRANT ALL PRIVILEGES ON DATABASE mydatabase TO myuser;"
sudo -u postgres psql -c "\c mydatabase"
sudo -u postgres psql -c "CREATE TABLE equations (id SERIAL PRIMARY KEY, equation VARCHAR(255)
NOT NULL, root DOUBLE PRECISION NOT NULL);"
sudo -u postgres psql -c "GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO myuser;"


