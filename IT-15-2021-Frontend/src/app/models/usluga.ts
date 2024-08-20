import { Filijala } from "./filijala";
import { Korisnici } from "./korisnici";

export class Usluga {
    id!: number;
    naziv!: string;
    opisUsluge!: string;
    datumUgovora!: Date;
    provizija!: number;
    filijala!: Filijala;
    korisnik!: Korisnici
}