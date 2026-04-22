create table public.category
(
    deleted     boolean      not null,
    created_at  timestamp(6) not null,
    updated_at  timestamp(6),
    created_by  varchar(255) not null,
    description text,
    id          varchar(255) not null primary key,
    name        varchar(255) not null unique,
    updated_by  varchar(255)
);


create table public.product
(
    alert_threshold integer        not null,
    deleted         boolean        not null,
    price           numeric(38, 2) not null,
    quantity        integer        not null,
    reference       integer        not null
        unique,
    created_at      timestamp(6)   not null,
    updated_at      timestamp(6),
    category_id     varchar(255)
        constraint product_name_unique_constraint
            references public.category,
    created_by      varchar(255)   not null,
    id              varchar(255)   not null
        primary key,
    name            varchar(255)   not null,
    updated_by      varchar(255)
);



create table public.stock_mvts
(
    deleted     boolean      not null,
    quantity    integer      not null,
    created_at  timestamp(6) not null,
    date_mvt    timestamp(6),
    updated_at  timestamp(6),
    created_by  varchar(255) not null,
    description text,
    id          varchar(255) not null
        primary key,
    product_id  varchar(255)
        constraint fk_product_id
            references public.product,
    type_mvt    varchar(255) not null
        constraint stock_mvts_type_mvt_check
            check ((type_mvt)::text = ANY ((ARRAY ['IN'::character varying, 'OUT'::character varying])::text[])),
    updated_by  varchar(255)
);



