DELIMITER $$
CREATE PROCEDURE wyszukaj_pokoje(IN sortuj_wg TEXT, IN standard TEXT, IN ilość_osób TEXT, IN ilość_łóżek TEXT,
  dodatkowe_cechy TEXT, IN data_poczatku DATE, IN data_zakonczenia DATE)

  BEGIN
    DECLARE wyszukaj TEXT;
    DECLARE sortuj TEXT DEFAULT " ORDER BY ";
    DECLARE i TEXT DEFAULT " AND ";
    DECLARE grupuj TEXT DEFAULT " GROUP BY pokoje.nr_pokoju ";
    DECLARE data_rozp TEXT DEFAULT "' BETWEEN rezerwacje.poczatek AND rezerwacje.koniec OR '";
    DECLARE data_zak TEXT DEFAULT "' BETWEEN  rezerwacje.poczatek AND rezerwacje.koniec OR ( '";
    DECLARE rozp_i_zak1 TEXT DEFAULT "' < rezerwacje.poczatek AND '";
    DECLARE rozp_i_zak2 TEXT DEFAULT "' > rezerwacje.koniec ) )";



    SET wyszukaj = "SELECT pokoje.nr_pokoju  , standard.nazwa, rodzaj_pokoju.ilość_osób,
     rodzaj_pokoju.ilość_łóżek, rodzaj_pokoju.cena, pokoje.czy_balkon, pokoje.czy_widok_na_basen, pokoje.czy_widok_na_morze,
     AVG(oceny.ocena) as Ocena, (SELECT COUNT(rezerwacje.nr_pokoju) FROM rezerwacje WHERE rezerwacje.nr_pokoju = pokoje.nr_pokoju) AS ilość_rezerwacji
     FROM pokoje
     INNER JOIN rodzaj_pokoju ON pokoje.rodzaj_ID = rodzaj_pokoju.rodzaj_ID
     INNER JOIN standard ON rodzaj_pokoju.standard_ID = standard.standard_ID
     LEFT OUTER JOIN oceny ON oceny.nr_pokoju = pokoje.nr_pokoju

     WHERE
     pokoje.nr_pokoju NOT IN( SELECT pokoje.nr_pokoju FROM pokoje INNER JOIN rezerwacje ON pokoje.nr_pokoju = rezerwacje.nr_pokoju
                              WHERE '";

    SET wyszukaj = CONCAT(wyszukaj,data_poczatku);
    SET wyszukaj = CONCAT(wyszukaj,data_rozp);
    SET wyszukaj = CONCAT(wyszukaj,data_zakonczenia);
    SET wyszukaj = CONCAT(wyszukaj,data_zak);
    SET wyszukaj = CONCAT(wyszukaj,data_poczatku);
    SET wyszukaj = CONCAT(wyszukaj,rozp_i_zak1);
    SET wyszukaj = CONCAT(wyszukaj,data_zakonczenia);
    SET wyszukaj = CONCAT(wyszukaj,rozp_i_zak2);
    SET wyszukaj = CONCAT(wyszukaj, i);
    SET wyszukaj = CONCAT(wyszukaj,standard);
    SET wyszukaj = CONCAT(wyszukaj,i );
    SET wyszukaj = CONCAT(wyszukaj, ilość_osób);
    SET wyszukaj = CONCAT(wyszukaj,i);
    SET wyszukaj = CONCAT(wyszukaj, ilość_łóżek);
    SET wyszukaj = CONCAT(wyszukaj,i);
    SET wyszukaj = CONCAT(wyszukaj, dodatkowe_cechy);

    SET wyszukaj = CONCAT(wyszukaj, grupuj);
    SET wyszukaj = CONCAT(wyszukaj, sortuj);
    SET wyszukaj = CONCAT(wyszukaj, sortuj_wg);


    SET @zmienna = wyszukaj;

    PREPARE stmt FROM @zmienna;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;


  END $$

DELIMITER ;


DROP PROCEDURE wyszukaj_pokoje;
call wyszukaj_pokoje("pokoje.nr_pokoju", "standard.nazwa = 'Standard'", "rodzaj_pokoju.ilość_osób = 1", "rodzaj_pokoju.ilość_łóżek = 1",
"pokoje.czy_balkon = TRUE", "2017-01-02", "2017-01-06");
call wyszukaj_pokoje("pokoje.nr_pokoju", "standard.nazwa = 'Standard'", "rodzaj_pokoju.ilość_osób = 1", "rodzaj_pokoju.ilość_łóżek = 1",
"pokoje.czy_balkon = TRUE AND pokoje.czy_widok_na_morze = FALSE", "2017-01-02", "2017-01-06");
SELECT * FROM pokoje;



DELIMITER $$
CREATE PROCEDURE pokaż_wykorzystane_usługi(IN nr_rezerwacji INTEGER)
  BEGIN
    SELECT uslugi.nazwa, uslugi.cena, uslugi.opis
      FROM uslugi
        INNER JOIN uslugi_gosci
          ON uslugi.usluga_ID = uslugi_gosci.usluga_ID
          WHERE uslugi_gosci.rezerwacja_ID = nr_rezerwacji;

  END $$
DELIMITER ;

DROP PROCEDURE pokaż_wykorzystane_usługi;




DELIMITER $$
CREATE PROCEDURE odwołaj_rezerwację(IN nr_rezerwacji INTEGER, OUT do_odwołania BOOLEAN)
  BEGIN
    DECLARE data_początku DATE;
    SET data_początku = (SELECT rezerwacje.poczatek FROM rezerwacje WHERE rezerwacje.rezerwacja_ID = nr_rezerwacji);

    if(data_początku < NOW())
       THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT  = 'Rezerwacja nieaktualna!';
    END IF;

    SELECT rezerwacje.czy_możliwość_odwołania INTO do_odwołania FROM rezerwacje WHERE rezerwacje.rezerwacja_ID = nr_rezerwacji;

    DELETE FROM rezerwacje
      WHERE rezerwacje.rezerwacja_ID = nr_rezerwacji;
  END $$
DELIMITER ;

DROP PROCEDURE odwołaj_rezerwację;



DELIMITER $$
CREATE PROCEDURE oceń_pokój(IN ocena DOUBLE, IN nr_rezerwacji INTEGER)
  BEGIN
  DECLARE pokój INTEGER;
    if(ocena <> 1 AND ocena <> 2 AND ocena <> 3 AND ocena <> 4 AND ocena <> 5)
      THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT  = 'Ocena musi być liczbą z zakresu 1-5!';
    END IF;
  SET pokój = (SELECT pokoje.nr_pokoju FROM pokoje INNER JOIN rezerwacje ON pokoje.nr_pokoju = rezerwacje.nr_pokoju
  WHERE rezerwacje.rezerwacja_ID = nr_rezerwacji);
    INSERT INTO oceny VALUES (pokój, nr_rezerwacji, ocena);
  END $$
DELIMITER ;

CALL oceń_pokój(3,7);
DROP PROCEDURE oceń_pokój;

CALL oceń_pokój(5,2);


DELIMITER $$
CREATE PROCEDURE dodaj_rezerwacje(IN login VARCHAR(30), IN pokój INTEGER, IN data_przyjazdu DATE, IN data_wyjazdu DATE,
                                    czy_odwołanie BOOLEAN, metoda_płatności VARCHAR(20))
  BEGIN

    DECLARE nr_rezerwacji INTEGER;
    DECLARE cena_za_pokój INTEGER;
    DECLARE data_płatności DATE;

    SET cena_za_pokój = (SELECT rodzaj_pokoju.cena FROM rodzaj_pokoju INNER JOIN pokoje
    ON rodzaj_pokoju.rodzaj_ID = pokoje.rodzaj_ID WHERE pokoje.nr_pokoju = pokój) * DATEDIFF(data_wyjazdu,data_przyjazdu);

    INSERT INTO rezerwacje (login_goscia, poczatek, koniec, data_rezerwacji, nr_pokoju, czy_możliwość_odwołania)
      VALUES (login, data_przyjazdu, data_wyjazdu, CURDATE(), pokój, czy_odwołanie);

    SET nr_rezerwacji= (SELECT rezerwacje.rezerwacja_ID FROM rezerwacje WHERE rezerwacje.login_goscia = login AND rezerwacje.poczatek = data_przyjazdu
      AND rezerwacje.koniec = data_wyjazdu AND rezerwacje.nr_pokoju = pokój);



    IF(czy_odwołanie = TRUE )
      THEN SET cena_za_pokój = cena_za_pokój+80;
    END IF ;

    IF(metoda_płatności = "karta")
      THEN
      SET data_płatności = CURDATE();

      ELSE
      SET data_płatności = data_przyjazdu;
    END IF;


    INSERT INTO platnosci (rezerwacja_ID, metoda_platnosci, kwota_za_pokój, data)
      VALUES (nr_rezerwacji, metoda_płatności, cena_za_pokój, data_płatności);

  END $$
DELIMITER ;

DROP PROCEDURE dodaj_rezerwacje;
CALL dodaj_rezerwacje("kasia", 7,'2015-01-11','2017-01-03', TRUE , "karta");


DELIMITER $$
CREATE PROCEDURE zobacz_rezerwacje_gościa(IN login VARCHAR(20))
  BEGIN
    SELECT rezerwacje.rezerwacja_ID,rezerwacje.data_rezerwacji, rezerwacje.poczatek, rezerwacje.koniec, rezerwacje.nr_pokoju, standard.nazwa,
      rodzaj_pokoju.ilość_osób, rodzaj_pokoju.ilość_łóżek, rezerwacje.czy_możliwość_odwołania ,platnosci.kwota_za_pokój,
      platnosci.kwota_za_usługi, oceny.ocena
        FROM rezerwacje
          LEFT OUTER JOIN pokoje ON rezerwacje.nr_pokoju = pokoje.nr_pokoju
          LEFT OUTER JOIN rodzaj_pokoju ON pokoje.rodzaj_ID = rodzaj_pokoju.rodzaj_ID
          LEFT OUTER JOIN platnosci ON rezerwacje.rezerwacja_ID = platnosci.rezerwacja_ID
          LEFT OUTER JOIN standard ON rodzaj_pokoju.standard_ID = standard.standard_ID
          LEFT OUTER JOIN oceny ON oceny.rezerwacja_ID = rezerwacje.rezerwacja_ID
    WHERE rezerwacje.login_goscia = login;

  END $$
DELIMITER ;

DROP PROCEDURE zobacz_rezerwacje_gościa;

CALL zobacz_rezerwacje_gościa("kasia");

DELIMITER $$
CREATE PROCEDURE dodaj_pokoj(IN numer_pokoju INT,IN rodzajID INT, IN balkon BOOLEAN, IN widok_na_basen BOOLEAN,
                             IN widok_na_morze BOOLEAN)
  BEGIN
    IF (numer_pokoju = ANY (SELECT pokoje.nr_pokoju FROM pokoje)) THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT  = 'Pokój o wpisanym numerze już istnieje!';
    END IF;

    IF (rodzajID NOT IN (SELECT rodzaj_ID FROM rodzaj_pokoju)) THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT  = 'Nie istnieje rodzaj pokoju o wpisanym ID!';
    END IF;

    INSERT INTO pokoje (nr_pokoju, rodzaj_ID, czy_balkon, czy_widok_na_basen, czy_widok_na_morze)
                 VALUE (numer_pokoju, rodzajID, balkon, widok_na_basen, widok_na_morze);

  END $$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE dodaj_standard(IN nazwa_standardu VARCHAR(20), IN TV BOOLEAN, IN WIFI BOOLEAN, IN klimatyzacja BOOLEAN,
                                IN budzenie BOOLEAN, IN suszarka BOOLEAN, IN żelazko BOOLEAN, IN kosmetyki BOOLEAN, IN sejf BOOLEAN)
  BEGIN
    IF (nazwa_standardu IN (SELECT nazwa FROM standard)) THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT  = 'Standard o wpisanej nazwie już istnieje!';
    END IF;

    INSERT INTO standard (nazwa, czy_TV, czy_WIFI, czy_klimatyzacja, czy_budzenie_na_życzenie, czy_suszarka_do_włosów, czy_żelazko, czy_zestaw_kosmetyków, czy_sejf)
                   VALUE (nazwa_standardu, TV, WIFI, klimatyzacja, budzenie, suszarka, żelazko, kosmetyki, sejf);
  END $$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE dodaj_rodzaj(IN nazwa_standardu VARCHAR(20), IN osoby INT, IN łóżka INT, IN kwota INT)
  BEGIN
    IF (nazwa_standardu NOT IN (SELECT nazwa FROM standard)) THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT  = 'Nie istnieje standard o wpisanej nazwie!';
    END IF;

    INSERT INTO rodzaj_pokoju (standard_ID, ilość_osób, ilość_łóżek, cena) VALUE ((SELECT standard_ID
                                                                                   FROM standard
                                                                                   WHERE nazwa = nazwa_standardu), osoby, łóżka, kwota);
  END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE dodaj_gościa(IN login_gościa VARCHAR(20), IN imie_gościa VARCHAR(20), IN nazwisko_gościa VARCHAR(20), IN PESEL_gościa VARCHAR(20), IN nr_dowodu_gościa VARCHAR(20))
  BEGIN
    IF (login_gościa = ANY (SELECT login FROM goscie)) THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT  = 'Podany login jest zajęty!';
    END IF;

    IF (PESEL_gościa = ANY (SELECT PESEL FROM goscie) || nr_dowodu_gościa = ANY (SELECT nr_dowodu FROM goscie)) THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT  = 'Gość o podanych danych jest już w bazie hotelu!';
    END IF;

    INSERT INTO goscie (login, imie, nazwisko, PESEL, nr_dowodu) VALUE (login_gościa, imie_gościa, nazwisko_gościa, PESEL_gościa, nr_dowodu_gościa);
  END $$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE usun_pokoj(IN numerpokoju INT)
  BEGIN
    DELETE FROM pokoje WHERE nr_pokoju = numerpokoju;
  END $$
DELIMITER ;

DELIMITER $$
CREATE PROCEDURE usun_rezerwacje(IN ID INT)
  BEGIN
    DELETE FROM rezerwacje
      WHERE rezerwacja_ID = ID;
  END $$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE dodaj_usluge(IN nazwa_uslugi VARCHAR(30), IN cena_uslugi INT, IN opis_uslugi VARCHAR(100))
  BEGIN
    IF (nazwa_uslugi IN (SELECT nazwa FROM uslugi)) THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT  = 'Usługa o wpisanej nazwie już istnieje!';
    END IF;

    INSERT INTO uslugi (nazwa, cena, opis) VALUE (nazwa_uslugi, cena_uslugi, opis_uslugi);
  END $$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE usun_usluge(IN nazwa_uslugi VARCHAR(20))
  BEGIN
    DELETE FROM uslugi
      WHERE nazwa = nazwa_uslugi;
  END $$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE usun_rodzaj (IN ID INT)
  BEGIN
    DELETE FROM rodzaj_pokoju
      WHERE rodzaj_ID = ID;
  END $$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE usun_standard (IN nazwa_standardu VARCHAR(20))
  BEGIN
    DELETE FROM standard
      WHERE nazwa = nazwa_standardu;
  END $$
DELIMITER ;


DELIMITER &&
CREATE PROCEDURE dodaj_usluge_dla_goscia (IN nazwa_uslugi VARCHAR(20), IN gosc_login VARCHAR(20))
  BEGIN
    INSERT INTO uslugi_gosci (usluga_ID, rezerwacja_ID) VALUE ((SELECT usluga_ID FROM uslugi WHERE nazwa = nazwa_uslugi),(SELECT rezerwacja_ID
                                                                                                                          FROM rezerwacje
                                                                                                                          INNER JOIN goscie ON rezerwacje.login_goscia = goscie.login
                                                                                                                          WHERE login = gosc_login
                                                                                                                          AND DATE(NOW()) BETWEEN rezerwacje.poczatek
                                                                                                                          AND rezerwacje.koniec));
  END &&
DELIMITER ;
DROP PROCEDURE dodaj_usluge_dla_goscia;


DELIMITER $$
CREATE PROCEDURE edytuj_pokoj(IN numer_pokoju INT, IN ID_rodzaju INT, IN balkon BOOLEAN, IN widok_na_basen BOOLEAN, IN widok_na_morze BOOLEAN)
  BEGIN
    IF (ID_rodzaju NOT IN (SELECT rodzaj_ID FROM rodzaj_pokoju)) THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT  = 'Nie istnieje rodzaj pokoju o wpisanym ID!';
    END IF;
    UPDATE pokoje
      SET pokoje.rodzaj_ID = ID_rodzaju, pokoje.czy_balkon = balkon, pokoje.czy_widok_na_basen = widok_na_basen, pokoje.czy_widok_na_morze = widok_na_morze
      WHERE pokoje.nr_pokoju = numer_pokoju;
  END $$
DELIMITER ;
DROP PROCEDURE edytuj_pokoj;


DELIMITER $$
CREATE PROCEDURE edytuj_rodzaj_pokoju(IN ID_rodzaju INT, IN standard_nazwa VARCHAR(20), IN osoby INT, IN łóżka INT, IN cena_rodzaju INT)
  BEGIN
    UPDATE rodzaj_pokoju
      SET rodzaj_pokoju.standard_ID = (SELECT standard_ID FROM standard WHERE standard.nazwa = standard_nazwa), rodzaj_pokoju.ilość_osób = osoby,
          rodzaj_pokoju.ilość_łóżek = łóżka, rodzaj_pokoju.cena = cena_rodzaju
      WHERE rodzaj_pokoju.rodzaj_ID = ID_rodzaju;
  END $$
DELIMITER ;
DROP PROCEDURE edytuj_rodzaj_pokoju;


DELIMITER $$
CREATE PROCEDURE edytuj_standard(IN ID INT, IN nazwa_standardu VARCHAR(20), IN TV BOOLEAN, IN WIFI BOOLEAN, IN klimatyzacja BOOLEAN, IN budzenie BOOLEAN, IN suszarka BOOLEAN, IN żelazko BOOLEAN,
                                 IN kosmetyki BOOLEAN, IN sejf BOOLEAN)
  BEGIN
    UPDATE standard
      SET standard.nazwa = nazwa_standardu, standard.czy_TV = TV, standard.czy_WIFI = WIFI, standard.czy_klimatyzacja = klimatyzacja, standard.czy_budzenie_na_życzenie = budzenie,
          standard.czy_suszarka_do_włosów = suszarka, standard.czy_żelazko = żelazko, standard.czy_zestaw_kosmetyków = kosmetyki, standard.czy_sejf = sejf
      WHERE standard.standard_ID = ID;
  END $$
DELIMITER ;
DROP PROCEDURE edytuj_standard;


DELIMITER $$
CREATE PROCEDURE edytuj_usluge(IN ID INT, IN nazwa_uslugi VARCHAR(20), IN cena_uslugi INT, IN opis_uslugi VARCHAR(100))
  BEGIN
    UPDATE uslugi
      SET uslugi.nazwa = nazwa_uslugi, uslugi.cena = cena_uslugi, uslugi.opis = opis_uslugi
      WHERE uslugi.usluga_ID = ID;
  END $$
DELIMITER ;
DROP PROCEDURE edytuj_usluge;


DELIMITER $$
CREATE PROCEDURE edytuj_goscia(IN login_goscia VARCHAR(20), IN imie_goscia VARCHAR(20), IN nazwisko_goscia VARCHAR(20), IN PESEL_goscia VARCHAR(20), IN numer_dowodu_goscia VARCHAR(20))
  BEGIN
    UPDATE goscie
      SET imie = imie_goscia, nazwisko = nazwisko_goscia, PESEL = PESEL_goscia, nr_dowodu = numer_dowodu_goscia
      WHERE login = login_goscia;
  END $$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE edytuj_rezerwacje(IN ID INT, IN login VARCHAR(20), IN poczatek_rezerw DATE, IN koniec_rezerw DATE, IN pokoj INT, IN odwolanie BOOLEAN, IN platnosc VARCHAR(20))
  BEGIN
    IF (login NOT IN (SELECT login FROM goscie)) THEN
       SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT  = 'W bazie hotelu nie ma gościa o podanym loginie!';
    END IF;
    IF (pokoj NOT IN (SELECT nr_pokoju FROM pokoje)) THEN
       SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT  = 'Pokój o podanym numerze nie istnieje!';
    END IF;
    IF (DATE(NOW()) > poczatek_rezerw) THEN
       SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT  = 'Zły termin przyjazdu!';
    END IF;
    SET FOREIGN_KEY_CHECKS=0;
    UPDATE rezerwacje
      SET rezerwacje.login_goscia = login, rezerwacje.poczatek = poczatek_rezerw, rezerwacje.koniec = koniec_rezerw, rezerwacje.nr_pokoju = pokoj, rezerwacje.czy_możliwość_odwołania = odwolanie
      WHERE rezerwacje.rezerwacja_ID = ID;
    UPDATE platnosci
      SET platnosci.metoda_platnosci = platnosc
      WHERE platnosci.rezerwacja_ID = ID;
    SET FOREIGN_KEY_CHECKS=1;
  END $$
DELIMITER ;
DROP PROCEDURE edytuj_rezerwacje;

DELIMITER $$
CREATE PROCEDURE dodaj_uzytkownika(IN login_uzytkownika VARCHAR(20), IN haslo_uzytkownika VARCHAR(20),
                                   IN imie_uzytkownika VARCHAR(20), IN nazwisko_uzytkownika VARCHAR(20),
                                   IN PESEL_uzytkownika VARCHAR(20), IN nr_dowodu_uzytkownika VARCHAR(20))
  BEGIN
    IF (login_uzytkownika = ANY (SELECT login FROM uzytkownicy)) THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT  = 'Wpisany login jest już zajęty!';
    END IF;

    IF (PESEL_uzytkownika = ANY (SELECT PESEL FROM goscie) || nr_dowodu_uzytkownika = ANY (SELECT nr_dowodu FROM goscie)) THEN
      SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT  = 'Gość o wpisanych danych jest już w bazie hotelu!';
    END IF ;

    INSERT INTO uzytkownicy (login, haslo, imie, nazwisko, PESEL, nr_dowodu, typ)
    VALUE (login_uzytkownika,haslo_uzytkownika,imie_uzytkownika,nazwisko_uzytkownika,PESEL_uzytkownika,nr_dowodu_uzytkownika,"gość");
  END $$
DELIMITER ;

drop PROCEDURE dodaj_uzytkownika;
DELIMITER $$
CREATE PROCEDURE znajdź_wynajmującego(IN numer_pokoju INTEGER)
  BEGIN
    SELECT imie AS Imię, nazwisko AS Nazwisko, PESEL
      FROM goscie
      INNER JOIN rezerwacje ON goscie.login = rezerwacje.login_goscia
      WHERE rezerwacje.nr_pokoju = numer_pokoju
      AND NOW() BETWEEN poczatek AND koniec;


  END $$
DELIMITER ;
DROP PROCEDURE znajdź_wynajmującego;


DELIMITER $$
CREATE PROCEDURE znajdź_pokój_osoby(IN PESEL VARCHAR(11), OUT numer_pokoju INT)
  BEGIN
    SELECT nr_pokoju INTO numer_pokoju
    FROM rezerwacje
      INNER JOIN goscie ON rezerwacje.login_goscia = goscie.login
         WHERE goscie.PESEL = PESEL
          AND NOW() BETWEEN rezerwacje.poczatek AND rezerwacje.koniec;
  END $$

DELIMITER ;
DROP PROCEDURE znajdź_pokój_osoby;

DELIMITER $$

CREATE PROCEDURE oblicz_dochód_za_okres(IN data_początkowa DATE, IN data_końcowa DATE, OUT suma INT)
  BEGIN
    SELECT sum(platnosci.kwota_za_pokój + platnosci.kwota_za_usługi) INTO suma
    FROM platnosci
    WHERE platnosci.data BETWEEN data_początkowa AND data_końcowa;
  END $$
DELIMITER ;

DROP PROCEDURE oblicz_dochód_za_okres;



DELIMITER $$
CREATE PROCEDURE szukaj_rodzaju(IN ID INT)
  BEGIN
    SELECT * FROM widok_rodzajów WHERE rodzaj_ID = ID;
  END $$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE szukaj_obecnych_rezerwacji(IN ID INT)
  BEGIN
    SELECT * FROM widok_obecnych_rezerwacji WHERE rezerwacja_ID = ID;
  END $$
DELIMITER ;


DELIMITER $$
CREATE PROCEDURE szukaj_wszystkich_rezerwacji(IN ID INT)
  BEGIN
    SELECT * FROM widok_wszystkich_rezerwacji WHERE rezerwacja_ID = ID;
  END $$
DELIMITER ;



