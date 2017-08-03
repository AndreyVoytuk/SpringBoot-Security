 --
 -- Host: localhost    Database: ...
 
 --
 -- Table structure for table `role`
 --
 
 DROP TABLE IF EXISTS role;
 
 CREATE TABLE role (
   role_id serial PRIMARY KEY,
   role varchar(255) DEFAULT NULL
 );
 
 -- Table structure for table `user`
 --
 
 DROP TABLE IF EXISTS users;

 CREATE TABLE users (
   user_id serial PRIMARY KEY,
   active bit(1) DEFAULT NULL,
   email varchar(255) NOT NULL,
   last_name varchar(255) NOT NULL,
   name varchar(255) NOT NULL,
   password varchar(255) NOT NULL
 );
 
 
 -- Table structure for table `user_role`
 --
 
 DROP TABLE IF EXISTS user_role;
 
 CREATE TABLE user_role (
   user_id int REFERENCES users,
   role_id int REFERENCES role,
   PRIMARY KEY (user_id, role_id)
 );
 