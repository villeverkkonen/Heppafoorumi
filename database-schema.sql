CREATE TABLE Alue
(
    id int PRIMARY KEY,
    aikaleima date,
    otsikko varchar(200),
    teksti varchar(200)
);

CREATE TABLE Aihe
(
    id int PRIMARY KEY,
    aikaleima date,
    alue int,
    nimimerkki varchar(20),
    otsikko varchar(200),
    teksti varchar(200),
    FOREIGN KEY(alue) REFERENCES Alue(id),
);

CREATE TABLE Viesti
(
    id int PRIMARY KEY,
    aikaleima date,
    aihe int,
    nimimerkki varchar(20),
    otsikko varchar(200),
    teksti varchar(200),
    FOREIGN KEY(aihe) REFERENCES Aihe(id)
);
