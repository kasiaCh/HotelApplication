CREATE VIEW widok_pokojow AS
  SELECT nr_pokoju, pokoje.rodzaj_ID, czy_balkon, czy_widok_na_basen, czy_widok_na_morze
  FROM pokoje;

SELECT * FROM widok_pokojow;

CREATE VIEW widok_standardów AS
  SELECT standard_ID, nazwa, czy_TV, czy_WIFI, czy_klimatyzacja, czy_budzenie_na_życzenie,
         czy_suszarka_do_włosów, czy_żelazko, czy_zestaw_kosmetyków, czy_sejf
  FROM standard;

SELECT * FROM widok_standardów;

CREATE VIEW widok_rodzajów AS
  SELECT rodzaj_pokoju.rodzaj_ID, standard.nazwa, ilość_osób, ilość_łóżek, cena
  FROM rodzaj_pokoju
  INNER JOIN standard ON rodzaj_pokoju.standard_ID = standard.standard_ID;

SELECT * FROM widok_rodzajów;

CREATE VIEW oceny_pokojów AS
  SELECT pokoje.nr_pokoju, goscie.imie, goscie.nazwisko, oceny.ocena,  AVG(ocena) as średnia_ocena
  FROM pokoje
  INNER JOIN oceny ON pokoje.nr_pokoju = oceny.nr_pokoju
  INNER JOIN rezerwacje ON oceny.rezerwacja_ID = rezerwacje.rezerwacja_ID
  INNER JOIN goscie ON rezerwacje.login_goscia = goscie.login
  GROUP BY pokoje.nr_pokoju;

SELECT * FROM oceny_pokojów;
DROP VIEW oceny_pokojów;

CREATE VIEW rezerwacje_na_dziś AS
  SELECT rezerwacje.rezerwacja_ID, nr_pokoju, login_goscia, imie, nazwisko, poczatek, koniec, data_rezerwacji, czy_możliwość_odwołania,
    platnosci.metoda_platnosci
  FROM rezerwacje
  INNER JOIN goscie ON rezerwacje.login_goscia = goscie.login
    INNER JOIN platnosci ON rezerwacje.rezerwacja_ID = platnosci.rezerwacja_ID
  WHERE poczatek = DATE(NOW());

drop VIEW rezerwacje_na_dziś;
SELECT * FROM rezerwacje_na_dziś;

CREATE VIEW widok_obecnych_rezerwacji AS
  SELECT rezerwacje.rezerwacja_ID, nr_pokoju, login_goscia, imie, nazwisko, poczatek, koniec, data_rezerwacji, czy_możliwość_odwołania, platnosci.metoda_platnosci
  FROM rezerwacje
  INNER JOIN goscie ON rezerwacje.login_goscia = goscie.login
  INNER JOIN platnosci ON rezerwacje.rezerwacja_ID = platnosci.rezerwacja_ID
  WHERE DATE (NOW()) <= poczatek;

DROP VIEW widok_obecnych_rezerwacji;
SELECT * FROM widok_obecnych_rezerwacji;

CREATE VIEW widok_wszystkich_rezerwacji AS
  SELECT rezerwacje.rezerwacja_ID, nr_pokoju, login_goscia, imie, nazwisko, poczatek, koniec, data_rezerwacji, czy_możliwość_odwołania,
    platnosci.metoda_platnosci
  FROM rezerwacje
  INNER JOIN goscie ON rezerwacje.login_goscia = goscie.login
  INNER JOIN platnosci ON rezerwacje.rezerwacja_ID = platnosci.rezerwacja_ID;

DROP VIEW widok_wszystkich_rezerwacji;

CREATE VIEW widok_usług AS
  SELECT usluga_ID, nazwa, cena, opis
  FROM uslugi;

CREATE VIEW widok_obecnych_gości AS
  SELECT rezerwacje.rezerwacja_ID, login, imie, nazwisko, PESEL, nr_dowodu
  FROM goscie
  INNER JOIN rezerwacje ON goscie.login = rezerwacje.login_goscia
  WHERE DATE(NOW()) <= poczatek;

drop VIEW widok_obecnych_gości;
SELECT * from widok_obecnych_gości;

CREATE VIEW widok_wszystkich_gości AS
  SELECT login, imie, nazwisko, PESEL, nr_dowodu
  FROM goscie;

