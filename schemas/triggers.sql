-- triggers

DELIMITER $$
CREATE TRIGGER sprawdź_czy_ocena_istnieje
  BEFORE INSERT ON oceny
  FOR EACH ROW
  BEGIN
    DECLARE nr_rezerwacji INTEGER;
    DECLARE ocena INTEGER;
    DECLARE istnieje BOOLEAN;
    SET nr_rezerwacji = new.rezerwacja_ID;
    SET ocena = new.ocena;


    SET istnieje = (nr_rezerwacji) IN (SELECT oceny.rezerwacja_ID FROM oceny);

    IF istnieje
      THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT  = 'Dodałeś już ocenę do tego pokoju!';
    END IF;

  end $$
DELIMITER ;

DROP TRIGGER sprawdź_czy_ocena_istnieje;


DELIMITER $$
CREATE TRIGGER sprawdź_możliwość_rezerwacji
  BEFORE INSERT ON rezerwacje
  FOR EACH ROW
  BEGIN
    DECLARE temp_poczatek DATE;
    DECLARE temp_koniec DATE;
    DECLARE temp_nr_pokoju INTEGER;
    DECLARE zajęta BOOLEAN;

    SET temp_poczatek = new.poczatek;
    SET temp_koniec = new.koniec;
    SET temp_nr_pokoju = new.nr_pokoju;

    SET zajęta = EXISTS(SELECT nr_pokoju FROM rezerwacje
                  WHERE rezerwacje.nr_pokoju = temp_nr_pokoju
                    AND ((temp_poczatek BETWEEN rezerwacje.poczatek AND rezerwacje.koniec)
                        OR (temp_koniec BETWEEN rezerwacje.poczatek AND rezerwacje.koniec)
                        OR (temp_poczatek < rezerwacje.poczatek AND temp_koniec > rezerwacje.koniec))
                   );

    IF zajęta
      THEN SIGNAL SQLSTATE '45000' SET MESSAGE_TEXT  = 'Pokój został właśnie zajęty! \nMusisz wybrać inną ofertę.';
    END IF;

  END $$
DELIMITER ;

DELIMITER $$
CREATE TRIGGER dodaj_goscia
  AFTER INSERT ON uzytkownicy FOR EACH ROW
  BEGIN
    IF (new.typ = 'gość') THEN
      INSERT INTO goscie (login, imie, nazwisko, PESEL, nr_dowodu) VALUE (new.login, new.imie, new.nazwisko, new.PESEL, new.nr_dowodu);
    END IF;
  END $$
DELIMITER ;

