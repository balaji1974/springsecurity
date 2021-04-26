

insert into user(first_name,last_name,username,password) values ('balaji','thiagarajan','bala','{BCrypt}$2a$10$sfZq0HmWSBRGEof2uSwli.P7yZJTCAwX.jx6nArttp6sWRhB1qlj2');
insert into user(first_name,last_name,username,password) values ('nizar','ahmed','nizar','{BCrypt}$2a$10$szZzG54cgQ7khjPpkzRsG.prL8C4VJRyQMCqWcP2ESlTY2uEpRReq');

insert into role values(1,'ROLE_ADMIN');
insert into role values(2,'ROLE_USER');

insert into user_role values(1,1);
insert into user_role values(2,2);

select * from user;
select * from role;
select * from user_role;
