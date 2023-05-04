## Sample Data Prompts

###  Customers table
Generate a MySQL script to populate the table below with sample data. The addresses are addresses in Australia. The options for the ‘custType’ field are: ‘Business’ and ‘Domestic’. The options for the ‘product’ field are: ‘Internet’, ‘Phone’, and ‘Billing’. Create 20 records for this table. "Customers
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
);"

###  Complaints table
Generate a MySQL script to populate the table below with sample data. The addresses are addresses in Australia. The options for the ‘compStatus’ field are: ‘Received’, ‘Allocated’, ‘Started’, ‘Resolved’, ‘Pending’, and ‘Cancelled’. The options for the ‘serviceType’ field are: ‘Internet’, ‘Phone’, and ‘Billing’. Create 10 records for this table. 
" Complaints
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
