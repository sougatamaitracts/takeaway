create database employee;
create database events;
use employee;
create table emp_hobbies (
       emp_id bigint not null,
        hobby_id bigint not null
    ) ;
create table employee_tab (
       emp_id bigint not null,
        date_of_birth datetime,
        email_id varchar(255),
        first_name varchar(255),
        last_name varchar(255),
        primary key (emp_id)
    );
create table hibernate_sequence (
       next_val bigint
    ) ;

create table hobbies (
       id bigint not null,
        hobby_name varchar(255),
        primary key (id)
    ) ;
alter table emp_hobbies  add constraint UK1	 unique (hobby_id);
alter table employee_tab add constraint UK2 unique (email_id);
alter table emp_hobbies  add constraint FK1  foreign key (hobby_id) references hobbies (id);
alter table emp_hobbies  add constraint FK2  foreign key (emp_id)   references employee_tab (emp_id);

insert into  hobbies value (1,"Football");
insert into hobbies value (2,"Tennis");

insert into hibernate_sequence values ( 3 );
insert into hibernate_sequence values ( 3 );
