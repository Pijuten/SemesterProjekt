CREATE TABLE userData(
                         username VARCHAR PRIMARY KEY UNIQUE,
                         password VARCHAR,
                         token varchar,
                         currency int DEFAULT 40,
                         score int DEFAULT 1000,
                         wins int DEFAULT 0,
                         draws int DEFAULT 0,
                         losses int DEFAULT 0,
                         displayName varchar,
                         bio varchar,
                         profileimage varchar
);
CREATE TABLE cards(
                      cardid varchar PRIMARY KEY UNIQUE,
                      cardname varchar,
                      damage FLOAT,
                      deck BOOLEAN DEFAULT FALSE,
                      username varchar,
                      packageid int,
                      CONSTRAINT FK_username_userData FOREIGN KEY(username)
                          REFERENCES userData(username)
);
