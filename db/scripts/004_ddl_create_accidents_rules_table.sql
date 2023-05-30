CREATE TABLE accidents_rules (
                                 id serial primary key,
                                 accidents_id int not null REFERENCES accidents(id),
                                 rules_id int not null REFERENCES rules(id)
);