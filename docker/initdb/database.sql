DO $$ BEGIN
    IF NOT EXISTS (SELECT FROM pg_database WHERE datname = 'backend') THEN
        CREATE DATABASE backend;
    END IF;
END $$;

grant all privileges on database backend to postgres;
