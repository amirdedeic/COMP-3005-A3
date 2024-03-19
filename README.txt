Author: Amir Dedeic
Student #: 101266477

1) Use pgAdmin or a similar tool to create a new database named COMP_3005_A3.
Create a students table using the following SQL command:

CREATE TABLE students (
    student_id SERIAL PRIMARY KEY,
    first_name TEXT NOT NULL,
    last_name TEXT NOT NULL,
    email TEXT NOT NULL UNIQUE,
    enrollment_date DATE
);



2) To get started with your database, execute this querry to enter your starting data: 

INSERT INTO students (first_name, last_name, email, enrollment_date) VALUES
('John', 'Doe', 'john.doe@example.com', '2023-09-01'),
('Jane', 'Smith', 'jane.smith@example.com', '2023-09-01'),
('Jim', 'Beam', 'jim.beam@example.com', '2023-09-02');



3) In "Referenced Libraries", add the specified JAR library to your project classpath: "postgresql-42.7.2"



4) To compile the application, navigate to the project's root directory in your terminal and run the following command: 
javac PostgreSQLJDBCConnection.java



5) Run it by selecting "Run Java" in your code editor of choice. 


#####################################################################
YOUTUBE VIDEO LINK: https://youtu.be/b4MSSPFAIjI