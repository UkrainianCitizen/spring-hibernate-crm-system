# spring-hibernate-crm-system

A simple CRM system, which is based on pure Spring and Hibernate, incorporating Spring Security and Spring AOP.

![spring-hibernate-db-app-screenshot](https://raw.github.com/UkrainianCitizen/spring-hibernate-crm-system/master/screenshots/crm-image.png)

## Getting started
 1. Install [Apache Tomcat®](https://tomcat.apache.org/) and hook it up with IDE of your choice.
 
 2. Install [MySQL Server](https://dev.mysql.com/downloads/mysql/) on your machine
 or refer to either of these:      
 [MAMP Download Page](https://www.mamp.info/en/downloads/)  
 [XAMPP Download Page](https://www.apachefriends.org/download.html)
 
 3. Run SQL scripts on your server:
  
    [01-create-user.sql](/sql-scripts/main/01-create-user.sql)     
    [02-load-500-fake-customers.sql](sql-scripts/main/02-load-500-fake-customers.sql)    
    [03-setup-spring-security-bcrypt-demo-database.sql](sql-scripts/security/03-setup-spring-security-bcrypt-demo-database.sql)
 4. Deploy project on your local server.
