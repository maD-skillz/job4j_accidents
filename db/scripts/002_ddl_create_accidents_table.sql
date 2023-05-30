CREATE TABLE IF NOT EXISTS accidents (
                           id serial primary key,
                           name text not null,
                           text text not null,
                           address text not null,
                           types_id int not null references accident_types(id)
);