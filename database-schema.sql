CREATE TABLE Alue
(
    id int PRIMARY KEY,
    aikaleima datetime(3),
    otsikko varchar(200),
    teksti varchar(200)
);

CREATE TABLE Aihe
(
    id int PRIMARY KEY,
    aikaleima datetime(3),
    alue int,
    nimimerkki varchar(20),
    otsikko varchar(200),
    teksti varchar(200),
    FOREIGN KEY(alue) REFERENCES Alue(id)
);

CREATE TABLE Viesti
(
    id int PRIMARY KEY,
    aikaleima datetime(3),
    aihe int,
    nimimerkki varchar(20),
    teksti varchar(200),
    FOREIGN KEY(aihe) REFERENCES Aihe(id)
);
