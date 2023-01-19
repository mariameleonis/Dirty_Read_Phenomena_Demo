DROP TABLE IF EXISTS student;

CREATE TABLE student (
                         id SERIAL PRIMARY KEY,
                         firstname VARCHAR(255) NOT NULL,
                         surname VARCHAR(255) NOT NULL,
                         birthdate DATE NOT NULL,
                         phone_number VARCHAR(255) NOT NULL,
                         primary_skill VARCHAR(255) NOT NULL,
                         created_dt TIMESTAMP NOT NULL,
                         updated_dt TIMESTAMP NOT NULL
);

INSERT INTO student (firstname, surname, birthdate, phone_number, primary_skill, created_dt, updated_dt)
VALUES ('Wyatt', 'Dicki', '1992-05-12 12:50:20.59', '(948) 618-7291 x59975', 'Manufacturing', '2022-03-02 10:56:14.393', '2022-04-04 06:19:23.61');