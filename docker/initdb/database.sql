DO
$$
    BEGIN
        IF NOT EXISTS (SELECT FROM pg_database WHERE datname = 'river_knowledge_database') THEN
            CREATE DATABASE river_knowledge_database;
        END IF;
    END
$$;