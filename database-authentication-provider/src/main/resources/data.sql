CREATE TABLE USER 
(
ID INT NOT NULL AUTO_INCREMENT,
FIRST_NAME VARCHAR(20),
LAST_NAME VARCHAR(20),
USER_NAME VARCHAR(20),
PASSWORD VARCHAR(256), 
PRIMARY KEY (ID),
UNIQUE KEY (USER_NAME)
);

CREATE TABLE ROLE 
(
ID INT NOT NULL AUTO_INCREMENT,
NAME VARCHAR(20),
PRIMARY KEY (ID)
);

CREATE TABLE USER_ROLE(
USER_ID int,
ROLE_ID int,
FOREIGN KEY (user_id)
REFERENCES user(id),
FOREIGN KEY (role_id)
REFERENCES role(id)
);

insert into user(first_name,last_name,user_name,password) values ('balaji','thiagarajan','bala','$2a$10$J6A5kyP8iepwAEJXTcrnXO.bdzjbJR0WSfTlIdJ8LhcoPQHAb08Ma');
insert into user(first_name,last_name,user_name,password) values ('nizar','ahmed','nizar','$2a$10$1cjDEbA5EYHgPOIEAdtn4O89uq.eE/ms8HKWSbRNJzPvwoUyPoBSu');

insert into role values(1,'ROLE_ADMIN');
insert into role values(2,'ROLE_USER');

insert into user_role values(1,1);
insert into user_role values(2,2);

select * from user;
select * from role;
select * from user_role;
