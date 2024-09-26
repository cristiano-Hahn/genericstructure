create table "user"(
    id uuid not null primary key,
    email text not null,
    password text not null
);

create unique index user_email_unique_idx on "user"(email);