package heppafoorumi.tekstikayttoliittyma;

import heppafoorumi.dao.AiheDao;
import heppafoorumi.dao.AlueDao;
import heppafoorumi.dao.ViestiDao;
import java.util.Scanner;

public class Tekstikayttoliittyma {

    final AlueDao alueDao;
    final AiheDao aiheDao;
    final ViestiDao viestiDao;
    String missaValikossa;
    boolean jatketaanko;

    public Tekstikayttoliittyma(AlueDao alueDao, AiheDao aiheDao, ViestiDao viestiDao) {
        this.alueDao = alueDao;
        this.aiheDao = aiheDao;
        this.viestiDao = viestiDao;
        this.missaValikossa = "aluevalikossa"; // alussa ollaan alueet-valikossa
        this.jatketaanko = true;
    }

    private void alueohjeet() {
        System.out.println("Tämä on Heppafoorumin tekstikäyttöliittymä.\n"
                + "Tällä hetkellä olet aluevalikossa.\n"
                + "Käytettävissäsi ovat seuraavat komennot:\n"
                + "alueet - listaa kaikkien alueiden id:t ja nimet\n"
                + "lopeta - poistuu tekstikäyttöliittymästä");
    }

    private void aiheohjeet() {
        System.out.println("Tämä on Heppafoorumin tekstikäyttöliittymä.\n"
                + "Tällä hetkellä olet aihevalikossa.\n"
                + "Käytettävissäsi ovat seuraavat komennot:\n"
                + "viestit - listaa kaikkien alueiden id:t ja nimet\n"
                + "pois - poistuu aihevalikosta\n"
                + "lopeta - poistuu tekstikäyttöliittymästä");

    }

    private void viestiohjeet() {
        System.out.println("Tämä on Heppafoorumin tekstikäyttöliittymä.\n"
                + "Tällä hetkellä olet viestivalikossa.\n"
                + "Käytettävissäsi ovat seuraavat komennot:\n"
                + "pois - poistuu viestivalikosta\n"
                + "lopeta - poistuu tekstikäyttöliittymästä");
    }

    private void aluevalikko(String[] osat) {
        // käsitellään aluevalikon syötteet.

        String komento = osat[0];

        switch (komento) {
            case "alueet":
                // listataan alueet.
                break;
            case "lisaa":
                // lisätään alue.
                break;
            case "lopeta":
                // poistutaan tekstikäyttöliitymästä.
                this.jatketaanko = false;
                return;
            case "ohjeet":
                // tulostetaan aluevalikon ohjeet.
                alueohjeet();
                break;
            default:
                // virheellinen syöte. listataan aluevalikon ohjeet.
                alueohjeet();
                break;
        }
    }

    private void aihevalikko(String[] osat) {
        // käsitellään aihevalikon syötteet.
    }

    private void viestivalikko(String[] osat) {
        // käsitellään viestivalikon syötteet.
    }

    public void kaynnista() {
        Scanner lukija = new Scanner(System.in);
        while (this.jatketaanko) {
            System.out.print("Olet " + this.missaValikossa + ". Anna komento: ");
            String syote = lukija.nextLine();
            String[] osat = syote.split(" ");
            switch (this.missaValikossa) {
                case "aluevalikossa":
                    if (osat.length == 0) {
                        this.alueohjeet();
                        continue;
                    }
                    this.aluevalikko(osat);
                    continue;
                case "aihevalikossa":
                    if (osat.length == 0) {
                        this.aiheohjeet();
                        continue;
                    }
                    this.aihevalikko(osat);
                    continue;
                case "viestivalikossa":
                    if (osat.length == 0) {
                        this.viestiohjeet();
                        continue;
                    }
                    this.viestivalikko(osat);
                    continue;
                default:
                    System.err.println("Virhe Heppafoorumin tekstikäyttöliittymän koodissa.");
                    this.missaValikossa = "aluevalikossa";
                    break;
            }

        }
        System.out.println("Poistuit Heppafoorumin tekstikäyttöliittymästä.\n"
                + "Kiitos ja tervetuloa uudelleen!");
    }
}
