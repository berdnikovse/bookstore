DROP TABLE IF EXISTS users CASCADE;
CREATE TABLE users (
  id SERIAL PRIMARY KEY,
  nickname text NOT NULL,
  password text NOT NULL,
  status text NOT NULL
);
COPY users(id, nickname, password, status)
FROM
  'C:\table_content\content_users.csv' WITH (FORMAT csv);
DROP TABLE IF EXISTS books CASCADE;
CREATE TABLE books (
  id SERIAL PRIMARY KEY,
  author text,
  name text NOT NULL,
  cover_type text,
  genre text,
  size integer,
  book_number integer NOT NULL,
  price integer,
  annotation text
);
COPY books(
  id,
  author,
  name,
  cover_type,
  genre,
  size,
  book_number,
  price,
  annotation
)
FROM
  'C:\table_content\content_books.csv' WITH (FORMAT csv);

DROP TABLE IF EXISTS orders CASCADE;
CREATE TABLE orders (
  id SERIAL PRIMARY KEY,
  user_id SERIAL references users(id) ON DELETE CASCADE,
  book_id SERIAL references books(id) ON DELETE CASCADE,
  book_number integer NOT NULL,
  delivery_place text NOT NULL,
  status text NOT NULL
);
COPY orders(
  id,
  user_id,
  book_id,
  book_number,
  delivery_place,
  status
)
FROM
  'C:\table_content\content_orders.csv' WITH (FORMAT csv);

SELECT
  setval(
    pg_get_serial_sequence('users', 'id'),
    (
      SELECT
        MAX(id)
      FROM
        users
    )
  );
  
  SELECT
  setval(
    pg_get_serial_sequence('books', 'id'),
    (
      SELECT
        MAX(id)
      FROM
        books
    )
  );
  SELECT
  setval(
    pg_get_serial_sequence('orders', 'id'),
    (
      SELECT
        MAX(id)
      FROM
        orders
    )
  );