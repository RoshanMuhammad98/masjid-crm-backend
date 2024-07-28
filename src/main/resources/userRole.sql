--insert values in role table
Insert into role(id, name) values(1, 'SUPER_ADMIN');
Insert into role(id, name) values(2, 'ADMIN');
Insert into role(id, name) values(3, 'FACILITY');
Insert into role(id, name) values(4, 'CENTER');
Insert into role(id, name) values(5, 'SALES_HEAD');
Insert into role(id, name) values(6, 'SALES_AGENT');
Insert into role(id, name) values(7, 'PARTNERSHIP_HEAD');
Insert into role(id, name) values(8, 'INSURANCE_AGENT');

-----------------
insert into role(id, name) values(9, 'CATEGORY_MANAGER');
insert into role(id, name) values(10, 'LEAD_EXECUTIVE');
INSERT INTO role(id, name) VALUES (11, 'DIGITAL_HEAD');
INSERT INTO role(id, name) VALUES (12, 'MEDICAL_OPS');
INSERT INTO role(id, name) VALUES (13, 'INSURANCE_HEAD');

------NEW USER ROLE----------
INSERT INTO role(id, name) VALUES (14, 'FINANCE_MANAGER');
INSERT INTO role(id, name) VALUES (15, 'FINANCE_EXECUTIVE');

------NEW USER ROLE----------
INSERT INTO role(id, name) VALUES (16, 'DIGITAL_EXECUTIVE');

INSERT INTO role(id, name) VALUES (17, 'DATA_TEAM');
INSERT INTO role(id, name) VALUES (18, 'INTL_SALES_HEAD');


------UPDATE ROLE----------
UPDATE role SET prefix= 'MYKAD' WHERE id = 2;
UPDATE role SET prefix= 'MYKSA' WHERE id = 6;
UPDATE role SET prefix= 'MYKPA' WHERE id = 7;
UPDATE role SET prefix= 'MYKCM' WHERE id = 9;
UPDATE role SET prefix= 'MYKIA' WHERE id = 8;
UPDATE role SET prefix= 'MYKSH' WHERE id = 5;
UPDATE role SET prefix= 'MYKMO' WHERE id = 12;
UPDATE role SET prefix= 'MYKDH' WHERE id = 11;
UPDATE role SET prefix= 'MYKDA' WHERE id = 17;
UPDATE role SET prefix= 'MYKFM' WHERE id = 14;
UPDATE role SET prefix= 'MYKFE' WHERE id = 15;
UPDATE role SET prefix= 'MYKDE' WHERE id = 16;


========================
UPDATED
DB Name: mykare_dashboard
update url for role table

31/08/2023
@Author Basil Eldhose
========================

update role set kareflow_url = '/home' where id = 1;
update role set kareflow_url = '/home' where id = 2;
update role set kareflow_url = '/home' where id = 3;
update role set kareflow_url = '/home' where id = 4;
update role set kareflow_url = '/enquiry' where id = 5;
update role set kareflow_url = '/enquiry' where id = 6;
update role set kareflow_url = '/partnership' where id = 7;
update role set kareflow_url = '/enquiry' where id = 8;
update role set kareflow_url = '/enquiry' where id = 9;
update role set kareflow_url = '/home' where id = 10;
update role set kareflow_url = '/enquiry' where id = 11;
update role set kareflow_url = '/enquiry' where id = 12;
update role set kareflow_url = '/enquiry' where id = 13;
update role set kareflow_url = '/enquiry' where id = 14;
update role set kareflow_url = '/enquiry' where id = 15;
update role set kareflow_url = '/enquiry' where id = 16;
update role set kareflow_url = '/enquiry' where id = 17;
update role set kareflow_url = '/kareTripEnquiry' where id = 18;
update role set kareflow_url = '/kareTripEnquiry' where id = 19;
update role set kareflow_url = '/enquiry' where id = 20;
update role set kareflow_url = '/home' where id = 21;
update role set kareflow_url = '/enquiry' where id = 22;
update role set kareflow_url = '/home' where id = 23;
update role set kareflow_url = '/kareTripEnquiry' where id = 24;
update role set kareflow_url = '/enquiry' where id = 26;
update role set kareflow_url = '/enquiry' where id = 27;


