-- categories
alter table public.category
    add tenant_id varchar(255) not null;

-- categories
alter table public.product
    add tenant_id varchar(255) not null;

-- categories
alter table public.stock_mvts
    add tenant_id varchar(255) not null;