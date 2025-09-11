create table t_employee
(
    id            bigint not null auto_increment,
    name          varchar(255),
    age           integer,
    gender        varchar(255),
    salary        float(53),
    active_status boolean,
    company_id    bigint,
    primary key (id),
    foreign key (company_id) references t_company (id)
);