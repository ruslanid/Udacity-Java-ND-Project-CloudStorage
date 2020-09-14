CREATE TABLE IF NOT EXISTS USERS (
  user_id INT PRIMARY KEY auto_increment,
  username VARCHAR(20),
  salt VARCHAR,
  password VARCHAR,
  first_name VARCHAR(20),
  last_name VARCHAR(20)
);

CREATE TABLE IF NOT EXISTS NOTES (
    note_id INT PRIMARY KEY auto_increment,
    note_title VARCHAR(20),
    note_description VARCHAR (1000),
    user_id INT,
    foreign key (user_id) references USERS(user_id)
);

CREATE TABLE IF NOT EXISTS FILES (
    file_id INT PRIMARY KEY auto_increment,
    file_name VARCHAR,
    content_type VARCHAR,
    file_size INT,
    user_id INT,
    file_data BLOB,
    foreign key (user_id) references USERS(user_id)
);

CREATE TABLE IF NOT EXISTS CREDENTIALS (
    credential_id INT PRIMARY KEY auto_increment,
    url VARCHAR(100),
    username VARCHAR (30),
    key VARCHAR,
    password VARCHAR,
    user_id INT,
    foreign key (user_id) references USERS(user_id)
);
