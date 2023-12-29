create sequence calc_seq start with 1 increment by 50;
create table calc (
    calc_date timestamp(6),
    id bigint not null,
    number_a varchar(255),
    number_b varchar(255),
    operation varchar(255),
    system_a varchar(255),
    system_b varchar(255),
    primary key (id));