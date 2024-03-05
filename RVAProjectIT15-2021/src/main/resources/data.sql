insert into Banka(id, naziv, kontakt, pib)
values(nextval('BANKA_SEQ'), 'Unicredit', '+38164566970', 104),
      (nextval('BANKA_SEQ'), 'Erste banka', '+38161285679', 111),
      (nextval('BANKA_SEQ'), 'Mobi banka', '+38160007894', 126),
      (nextval('BANKA_SEQ'), 'Komercijalna banka', '+38166485836', 160);
      
insert into Filijala(id, adresa, broj_pultova, poseduje_sef, banka) 
values(nextval('FILIJALA_SEQ'), 'Bulevar cara Lazara 66', 10, true, 1),
      (nextval('FILIJALA_SEQ'), 'Dositeja Obradovica 12', 5, false, 2),
      (nextval('FILIJALA_SEQ'), 'Maksima Markovica 15', 7, false, 3),
      (nextval('FILIJALA_SEQ'), 'Bulevar cara Lazara 66', 10, true, 4);
      
insert into korisnik_usluge(id, ime, prezime, maticni_broj)
values(nextval('KORISNIKUSLUGE_SEQ'), 'Veljko', 'Marinkovic', '1907002790067'),
      (nextval('KORISNIKUSLUGE_SEQ'), 'Marko', 'Mitrovic', '0303002550162'),
      (nextval('KORISNIKUSLUGE_SEQ'), 'Ana', 'Alimpijevic', '1010998124212'),
      (nextval('KORISNIKUSLUGE_SEQ'), 'Milena', 'Savic', '1211992092560');
      
insert into Usluga(id, naziv, opis_usluge, datum_ugovora, provizija, filijala, korisnik)
values(nextval('USLUGA_SEQ'), 'Izrada kartice', 'Izrada finansijske kartice klijentu', to_date('01.02.2023.', 'dd.mm.yyyy.'), 50, 1, 1),
      (nextval('USLUGA_SEQ'), 'Podnosenje zahteva za kredit', 'Klijent podnosi zahtev za kredit, banka to moze da odobri, a i ne mora ', to_date('15.08.2020.', 'dd.mm.yyyy.'), 10000, 2, 2),
      (nextval('USLUGA_SEQ'), 'Isplata gotovine', 'Klijent dolazi u banku sa namenom da podigne novac', to_date('30.12.2022.', 'dd.mm.yyyy.'), 105, 3, 3),
      (nextval('USLUGA_SEQ'), 'Izrada kartice', 'Izrada finansijske kartice klijentu', to_date('19.07.2019.', 'dd.mm.yyyy.'), 65, 4, 4);
       

       
      

