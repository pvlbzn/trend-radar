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

-- Indices for language column for individual queries such as data about Nim
create index if not exists idx_languages_language on languages(language);

-- Compound index, high cardinality fields first
create index if not exists idx_languages_compound on languages(
       language,
       language_type,
       region,
       year,
       quarter
);
