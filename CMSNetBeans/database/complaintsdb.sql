create database complaintsdb;
use complaintsdb;
create table Customers
(
custID int not null,
fName varchar(30) not null, 
lName varchar(30) not null,
mobile varchar(10) not null,
email varchar(30) not null,
addr varchar(50) not null,
custType varchar(10) not null,
product varchar(10) not null,
primary key(custID)
);

create table Complaints
(
compID int not null auto_increment,
custID int,
serviceType varchar(10) not null,
problemDesc varchar(150) not null,
compDate date not null,
serviceNotes varchar(150) not null,
compStatus varchar(10) not null, 
primary key(compID), 
foreign key(custID) references Customers(custID)
);