create table users
(
    id             bigint generated by default as identity,
    created_at     timestamp(6) not null,
    point          bigint       not null check (point >= 0),
    updated_at     timestamp(6) not null,
    email          varchar(255),
    nickname       varchar(255) not null,
    oauth_id       varchar(255) not null,
    oauth_provider varchar(255) not null check (oauth_provider in ('KAKAO', 'GOOGLE')),
    refresh_token  varchar(255),
    CONSTRAINT users_pkey PRIMARY KEY (id)
);

CREATE TABLE public.exchange_api_keys
(
    id         bigint NOT NULL,
    exchange   varchar NULL,
    api_key    varchar NULL,
    secret_key varchar NULL,
    created_at timestamptz NULL DEFAULT now(),
    updated_at timestamptz NULL,
    user_id    bigint NULL,
    CONSTRAINT exchange_api_keys_pkey PRIMARY KEY (id),
    CONSTRAINT exchange_api_keys_user_id_fk FOREIGN KEY (user_id) REFERENCES public.users (id)
);
CREATE INDEX ix_exchange_api_keys_id ON public.exchange_api_keys USING btree (id);

create table public.orders
(
    id          bigint generated by default as identity,
    canceled_at timestamp(6),
    created_at  timestamp(6) not null,
    total_price bigint       not null,
    updated_at  timestamp(6) not null,
    status      varchar(255) not null check (status in ('DONE', 'CANCELED')),
    user_id     bigint       not null,
    CONSTRAINT orders_pkey PRIMARY KEY (id),
    CONSTRAINT orders_user_id_fk FOREIGN KEY (user_id) REFERENCES public.users (id)
);

CREATE table strategy_items
(
    id          bigint generated by default as identity,
    price       bigint       not null,
    strategy_id bigint       not null unique,
    description varchar(255) not null,
    name        varchar(255) not null,
    CONSTRAINT strategy_items_pkey PRIMARY KEY (id),
    CONSTRAINT strategy_items_strategy_id_key UNIQUE (strategy_id),
    CONSTRAINT strategy_items_strategy_id_fk FOREIGN KEY (strategy_id) REFERENCES trade.strategies (id)
);

create table public.order_items
(
    id               bigint generated by default as identity,
    order_id         bigint       not null,
    strategy_item_id bigint       not null unique,
    created_at       timestamp(6) not null,
    updated_at       timestamp(6) not null,
    CONSTRAINT order_items_pkey PRIMARY KEY (id),
    CONSTRAINT order_items_strategy_item_id_key UNIQUE (strategy_item_id),
    CONSTRAINT order_items_order_id_fk FOREIGN KEY (order_id) REFERENCES public.orders (id),
    CONSTRAINT order_items_strategy_item_id_fk FOREIGN KEY (strategy_item_id) REFERENCES public.strategy_items (id)
);
CREATE INDEX ix_order_items_order_id ON public.order_items USING btree (order_id);

CREATE TABLE point_txns
(
    id         bigint generated by default as identity,
    created_at timestamp(6) not null,
    point      bigint       not null,
    updated_at timestamp(6) not null,
    user_id    bigint       not null,
    status     varchar(255) not null check (status in ('CHARGE', 'USE')),
    CONSTRAINT point_txns_pkey PRIMARY KEY (id),
    CONSTRAINT point_txns_user_id_fk FOREIGN KEY (user_id) REFERENCES public.users (id)
);
CREATE INDEX ix_point_txns_user_id ON public.point_txns USING btree (user_id);

CREATE TABLE payment_txns
(
    id           bigint generated by default as identity,
    amount       numeric(38, 2) not null,
    created_at   timestamp(6)   not null,
    point_txn_id bigint         not null,
    updated_at   timestamp(6)   not null,
    user_id      bigint         not null,
    payment_key  varchar(255)   not null,
    status       varchar(255)   not null check (status in ('DONE', 'CANCELED')),
    CONSTRAINT payment_txns_pkey PRIMARY KEY (id),
    CONSTRAINT payment_txns_point_txn_id_fk FOREIGN KEY (point_txn_id) REFERENCES public.point_txns (id),
    CONSTRAINT payment_txns_user_id_fk FOREIGN KEY (user_id) REFERENCES public.users (id)
);
CREATE INDEX ix_payment_txns_user_id ON public.payment_txns USING btree (user_id);

CREATE TABLE public.user_strategies
(
    id          bigint generated by default as identity,
    strategy_id bigint       NOT NULL,
    user_id     bigint       NOT NULL,
    created_at  timestamp(6) NOT NULL,
    updated_at  timestamp(6) NOT NULL,
    CONSTRAINT user_strategies_pkey PRIMARY KEY (id),
    CONSTRAINT user_strategies_strategy_id_fk FOREIGN KEY (strategy_id) REFERENCES trade.strategies (id),
    CONSTRAINT user_strategies_user_id_fk FOREIGN KEY (user_id) REFERENCES public.users (id)
);
CREATE INDEX ix_user_strategies_user_id ON public.user_strategies USING btree (user_id);
CREATE INDEX ix_user_strategies_strategy_id ON public.user_strategies USING btree (strategy_id);
