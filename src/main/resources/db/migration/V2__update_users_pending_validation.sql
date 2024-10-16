-- Ensure this script only runs once (idempotent)
-- If using a database other than PostgreSQL, adjust the syntax accordingly
DO $$
BEGIN
IF NOT EXISTS (SELECT FROM pg_catalog.pg_tables WHERE schemaname = 'public' AND tablename = 'users') THEN
          -- Handle the case where the 'users' table doesn't exist
          -- You might want to log a warning or take other actions
          RAISE NOTICE 'The "users" table does not exist. Skipping migration step.';
ELSE
UPDATE users SET pending_validation = true WHERE pending_validation IS DISTINCT FROM true;
END IF;
END
  $$;