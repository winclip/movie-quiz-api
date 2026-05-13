ALTER TABLE questions DROP CONSTRAINT IF EXISTS questions_category_id_fkey;
DROP INDEX IF EXISTS idx_questions_category;
ALTER TABLE questions DROP COLUMN IF EXISTS category_id;
DROP TABLE IF EXISTS categories;
