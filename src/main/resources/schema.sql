create table if not exists languages(
    id uuid primary key not null default gen_random_uuid(),

    contributors int not null,
    language varchar(32) not null,
    language_type varchar(32) not null,
    region varchar(2) not null,
    year int not null,
    quarter int not null,

    created_at timestamptz default now(),
    updated_at timestamptz default now()
);
