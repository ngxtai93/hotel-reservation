drop database cs584project;

create database cs584project;

use cs584project;

create table transcation(
tid int(255) primary key,
roomNumber int(255) unique,
customerID int(255),
checkInDate varchar(255),
checkOutDate varchar(255)

);

create table room(
roomNumber int(255) primary key,
roomType varchar(255),
roomDescription varchar(255)
);

create table user(
uid int(255) primary key,
username varchar(255) unique,
password varchar(255),
email varchar(255)
);

create table customerProfile(

cID int(255) primary key,
name varchar(255),
gender varchar(255),
creditCard int(255),
creditCardExpDate varchar(255),
address varchar(255),
zipcode int(10),
uID int(255)
);


drop table staffProfile;
create table staffProfile (

sID int(255) primary key,
type varchar(255),
name varchar(255),
uID int(255)

);

insert into user values (1,'manager','manager','manager@website');
insert into user values (2,'staff','staff','staff@website');

insert into staffProfile values (1,'role manager','Manager',1);
insert into staffProfile values (2,'role staff','staff',2);


