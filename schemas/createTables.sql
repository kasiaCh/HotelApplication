# Create tables

CREATE TABLE rezerwacje(
    rezerwacja_ID INT PRIMARY KEY AUTO_INCREMENT,
    login_goscia VARCHAR(20),
    poczatek DATE,
    koniec DATE,
    data_rezerwacji DATE,
    nr_pokoju INT
);
CREATE TABLE uzytkownicy
(
  login VARCHAR(20) PRIMARY KEY,
  haslo VARCHAR(20),
  imie VARCHAR(20),
  nazwisko VARCHAR(20),
  PESEL VARCHAR(20) UNIQUE,
  nr_dowodu VARCHAR(20) UNIQUE,
  typ VARCHAR(20) /*gość lub pracownik*/
);
INSERT INTO uzytkownicy (login, haslo, imie, nazwisko, PESEL, nr_dowodu, typ)
    VALUES ("kasiaPracownik", "kasia", "Kasia", "Cholewa", "96061112063", "AYD754744", "pracownik");

CREATE TABLE platnosci(
    rezerwacja_ID INT,
    metoda_platnosci VARCHAR(20),
    kwota INT,
    data DATE
);

CREATE TABLE goscie(
    login VARCHAR(20) PRIMARY KEY,
    imie VARCHAR(20),
    nazwisko VARCHAR(20),
    PESEL VARCHAR(20) UNIQUE,
    nr_dowodu VARCHAR(20) UNIQUE
);

CREATE TABLE uslugi(
    usluga_ID INT PRIMARY KEY AUTO_INCREMENT,
    nazwa VARCHAR(50),
    cena INT,
    opis VARCHAR(100)
);

CREATE TABLE uslugi_gosci(
    usluga_ID INT,
    login_goscia VARCHAR(30)
);

ALTER TABLE uslugi_gosci DROP COLUMN login_goscia;
ALTER TABLE uslugi_gosci ADD COLUMN rezerwacja_ID INTEGER;
ALTER TABLE uslugi_gosci ADD FOREIGN KEY (rezerwacja_ID) REFERENCES rezerwacje(rezerwacja_ID) ON DELETE SET NULL ;


CREATE TABLE pokoje
(
  nr_pokoju INT PRIMARY KEY AUTO_INCREMENT,
  rodzaj_ID INT,
  czy_balkon BOOLEAN,
  czy_widok_na_basen BOOLEAN,
  czy_widok_na_morze BOOLEAN
);

CREATE TABLE rodzaj_pokoju
(
  rodzaj_ID INT PRIMARY KEY AUTO_INCREMENT,
  standard_ID INT,
  ilość_osób INT,
  ilość_łóżek INT,
  cena INT
);

CREATE TABLE standard
(
  standard_ID INT PRIMARY KEY AUTO_INCREMENT,
  nazwa VARCHAR(20),
  czy_TV BOOLEAN,
  czy_WIFI BOOLEAN,
  czy_klimatyzacja BOOLEAN,
  czy_budzenie_na_życzenie BOOLEAN,
  czy_suszarka_do_włosów BOOLEAN,
  czy_żelazko BOOLEAN,
  czy_zestaw_kosmetyków BOOLEAN,
  czy_sejf BOOLEAN
);

CREATE TABLE oceny
(
  nr_pokoju INT,
  rezerwacja_ID INT,
  ocena INT
);

# Create FKs

ALTER TABLE rezerwacje
    ADD    FOREIGN KEY (login_goscia)
    REFERENCES goscie(login) ON DELETE SET NULL
;

ALTER TABLE uslugi_gosci
    ADD    FOREIGN KEY (usluga_ID)
    REFERENCES uslugi(usluga_ID) ON DELETE SET NULL
;

ALTER TABLE uslugi_gosci
    ADD    FOREIGN KEY (login_goscia)
    REFERENCES goscie(login) ON DELETE SET NULL
;

ALTER TABLE rezerwacje
    ADD    FOREIGN KEY (nr_pokoju)
    REFERENCES pokoje(nr_pokoju) ON DELETE SET NULL
;
ALTER TABLE rezerwacje ADD COLUMN czy_możliwość_odwołania BOOLEAN;

ALTER TABLE platnosci
    ADD    FOREIGN KEY (rezerwacja_ID)
    REFERENCES rezerwacje(rezerwacja_ID) ON DELETE SET NULL
;


ALTER TABLE rodzaj_pokoju
    ADD    FOREIGN KEY (standard_ID)
    REFERENCES standard(standard_ID) ON DELETE SET NULL
;

ALTER TABLE pokoje
    ADD    FOREIGN KEY (rodzaj_ID)
    REFERENCES rodzaj_pokoju(rodzaj_ID) ON DELETE CASCADE
;

ALTER TABLE oceny
    ADD    FOREIGN KEY (nr_pokoju)
    REFERENCES pokoje(nr_pokoju) ON DELETE CASCADE
;

ALTER TABLE oceny
    ADD    FOREIGN KEY (rezerwacja_ID)
    REFERENCES rezerwacje(rezerwacja_ID) ON DELETE SET NULL
;

/*DROP TABLE IF EXISTS rezerwacje;
DROP TABLE IF EXISTS platnosci;
DROP TABLE IF EXISTS goscie;
DROP TABLE IF EXISTS uslugi;
DROP TABLE IF EXISTS uslugi_gosci;
DROP TABLE IF EXISTS oceny;
DROP TABLE IF EXISTS pokoje;
DROP TABLE IF EXISTS rodzaj_pokoju;
DROP TABLE IF EXISTS standard;*/

# Create data

/*

INSERT INTO uslugi (nazwa, cena, opis) VALUE ("śniadanie",50,"Pyszne śniadanie w formie bufetu szwedzkiego w godzinach 6.00 - 12.00.");
INSERT INTO uslugi (nazwa, cena, opis) VALUE ("butelka szampana",80,"Butelka szampana na powitanie w pokoju.");
INSERT INTO uslugi (nazwa, cena, opis) VALUE ("kosz pyszności", 100,"Kosz pysznych słodkości i owoców czekający w pokoju.");
INSERT INTO uslugi (nazwa, cena, opis) VALUE ("bukiet czerwonych róż",90,"Bukiet świeżych, czerwonych róż da ukochanej.");

INSERT INTO standard (nazwa, czy_TV, czy_WIFI, czy_klimatyzacja, czy_budzenie_na_życzenie, czy_suszarka_do_włosów, czy_żelazko, czy_zestaw_kosmetyków, czy_sejf)
  VALUE ("Standard", TRUE, TRUE, FALSE, FALSE, FALSE, FALSE, FALSE, FALSE );
INSERT INTO standard (nazwa, czy_TV, czy_WIFI, czy_klimatyzacja, czy_budzenie_na_życzenie, czy_suszarka_do_włosów, czy_żelazko, czy_zestaw_kosmetyków, czy_sejf)
  VALUE ("Comfort", TRUE, TRUE, TRUE, FALSE, TRUE, TRUE, FALSE, FALSE);
INSERT INTO standard (nazwa, czy_TV, czy_WIFI, czy_klimatyzacja, czy_budzenie_na_życzenie, czy_suszarka_do_włosów, czy_żelazko, czy_zestaw_kosmetyków, czy_sejf)
  VALUE ("Superior", TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE, TRUE);

INSERT INTO rodzaj_pokoju (standard_ID, ilość_osób, ilość_łóżek, cena) VALUE (1, 1, 1, 140);
INSERT INTO rodzaj_pokoju (standard_ID, ilość_osób, ilość_łóżek, cena) VALUE (1, 2, 2, 240);
INSERT INTO rodzaj_pokoju (standard_ID, ilość_osób, ilość_łóżek, cena) VALUE (1, 2, 1, 240);
INSERT INTO rodzaj_pokoju (standard_ID, ilość_osób, ilość_łóżek, cena) VALUE (1, 3, 3, 340);
INSERT INTO rodzaj_pokoju (standard_ID, ilość_osób, ilość_łóżek, cena) VALUE (1, 3, 2, 340);
INSERT INTO rodzaj_pokoju (standard_ID, ilość_osób, ilość_łóżek, cena) VALUE (2, 1, 1, 200);
INSERT INTO rodzaj_pokoju (standard_ID, ilość_osób, ilość_łóżek, cena) VALUE (2, 2, 2, 340);
INSERT INTO rodzaj_pokoju (standard_ID, ilość_osób, ilość_łóżek, cena) VALUE (2, 2, 1, 340);
INSERT INTO rodzaj_pokoju (standard_ID, ilość_osób, ilość_łóżek, cena) VALUE (2, 3, 3, 440);
INSERT INTO rodzaj_pokoju (standard_ID, ilość_osób, ilość_łóżek, cena) VALUE (2, 3, 2, 440);
INSERT INTO rodzaj_pokoju (standard_ID, ilość_osób, ilość_łóżek, cena) VALUE (3, 1, 1, 280);
INSERT INTO rodzaj_pokoju (standard_ID, ilość_osób, ilość_łóżek, cena) VALUE (3, 2, 2, 440);
INSERT INTO rodzaj_pokoju (standard_ID, ilość_osób, ilość_łóżek, cena) VALUE (3, 2, 1, 440);
INSERT INTO rodzaj_pokoju (standard_ID, ilość_osób, ilość_łóżek, cena) VALUE (3, 3, 3, 540);
INSERT INTO rodzaj_pokoju (standard_ID, ilość_osób, ilość_łóżek, cena) VALUE (3, 3, 2, 540);


SELECT * FROM rodzaj_pokoju;
SELECT * FROM pokoje;
SELECT * FROM rezerwacje;
DELETE FROM rezerwacje WHERE rezerwacja_ID = 2;
DELETE FROM rezerwacje WHERE rezerwacja_ID = 4;

INSERT INTO pokoje (rodzaj_ID, czy_balkon, czy_widok_na_basen, czy_widok_na_morze )
    VALUES
      (1, TRUE, TRUE, FALSE),
      (2, TRUE, FALSE, TRUE),
      (3, TRUE, FALSE, FALSE),
      (4, FALSE, TRUE, FALSE),
      (5, FALSE, FALSE, FALSE),
      (6, FALSE, FALSE, FALSE),
      (7, FALSE, TRUE, FALSE),
      (8, FALSE, FALSE, TRUE),
      (9, TRUE, FALSE, FALSE),
      (10, TRUE, TRUE, TRUE),
      (11, TRUE, FALSE, TRUE),
      (12, TRUE, TRUE, FALSE),
      (13, TRUE, TRUE, TRUE),
      (14, FALSE, FALSE, FALSE),
      (15, FALSE, TRUE, TRUE);


INSERT INTO pokoje (rodzaj_ID, czy_balkon, czy_widok_na_basen, czy_widok_na_morze )
    VALUE
      (1, TRUE, TRUE, TRUE);

INSERT INTO pokoje (rodzaj_ID, czy_balkon, czy_widok_na_basen, czy_widok_na_morze )
    VALUE
      (1, TRUE, TRUE, FALSE);


SELECT * from pokoje;
SELECT * from oceny;
SELECT * FROM rezerwacje;
SELECT * FROM platnosci;

INSERT INTO platnosci (rezerwacja_ID, metoda_platnosci, kwota_za_pokój, data, kwota_za_usługi) VALUES
  (2, 'karta', 700, '2015-02-02', 50),
  (4, 'karta', 750, '2015-02-02', 50);
INSERT INTO oceny (nr_pokoju, rezerwacja_ID, ocena) VALUES (4,2,5);
INSERT INTO rezerwacje (login_goscia, poczatek, koniec, data_rezerwacji, nr_pokoju, czy_możliwość_odwołania)
    VALUES ('kasia', '2016-01-02', '2016-01-15', '2016-01-01', 4, 1 );

INSERT INTO rezerwacje (login_goscia, poczatek, koniec, data_rezerwacji, nr_pokoju, czy_możliwość_odwołania)
    VALUES ('kasia', '2016-01-02', '2016-01-15', '2016-01-01', 5, 1 );

INSERT INTO goscie (login, imie, nazwisko, PESEL, nr_dowodu) VALUES ('kasia', 'kasia', 'ch', '45555555555', '447555');

SELECT * FROM rezerwacje;
SELECT * FROM oceny;

INSERT INTO rezerwacje (login_goscia, poczatek, koniec, data_rezerwacji, nr_pokoju, czy_możliwość_odwołania)
    VALUES ("kasia", "2017-01-02", "2017-01-05", "2016-12-12", 3, 1);
INSERT INTO rezerwacje (login_goscia, poczatek, koniec, data_rezerwacji, nr_pokoju ,czy_możliwość_odwołania)
    VALUES ("kasia", "2017-01-02", "2017-01-11", "2016-12-15", 2,1);
INSERT INTO uslugi_gosci (usluga_ID, rezerwacja_ID) VALUES (1,7);

DELETE FROM rezerwacje WHERE rezerwacja_ID=7;
*/