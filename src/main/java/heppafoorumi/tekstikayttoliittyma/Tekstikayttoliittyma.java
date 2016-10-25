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

    private void ohjeet() {
        System.out.println("Tämä on Heppafoorumin tekstikäyttöliittymä.\n"
                + "Tällä hetkellä olet " + this.missaValikossa + ".\n"
                + "Käytettävissäsi ovat seuraavat komennot:");

        switch (this.missaValikossa) {
            case "aluevalikossa":
                System.out.println(
                        "alueet - listaa kaikkien alueiden id:t ja nimet");
                break;
            case "aihevalikossa":
                System.out.println(
                        "viestit - listaa kaikkien viestien id:t ja nimet\n"
                        + "pois - poistuu aihevalikosta");
                break;
            case "viestivalikossa":
                System.out.println(
                        "pois - poistuu viestivalikosta");
                break;
            default:
                System.err.println("Virhe Heppafoorumin tekstikäyttöliittymän koodissa.");
                this.missaValikossa = "aluevalikossa";
                break;
        }
        System.out.println("lopeta - poistuu tekstikäyttöliittymästä");
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
            default:
                // virheellinen syöte. listataan aluevalikon ohjeet.
                ohjeet();
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
        this.ohjeet();

        while (this.jatketaanko) {
            System.out.print("Olet " + this.missaValikossa + ". Anna komento: ");
            String syote = lukija.nextLine();
            String[] osat = syote.split(" ");
            if (osat.length == 0
                    || osat[0].equalsIgnoreCase("ohjeet")
                    || osat[0].equalsIgnoreCase("apua")
                    || osat[0].equalsIgnoreCase("help")) {
                this.ohjeet();
                continue;
            }

            switch (this.missaValikossa) {
                case "aluevalikossa":
                    this.aluevalikko(osat);
                    break;
                case "aihevalikossa":
                    this.aihevalikko(osat);
                    break;
                case "viestivalikossa":
                    this.viestivalikko(osat);
                    break;
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
