package heppafoorumi.tekstikayttoliittyma;

import heppafoorumi.dao.AiheDao;
import heppafoorumi.dao.AlueDao;
import heppafoorumi.dao.ViestiDao;
import heppafoorumi.domain.Aihe;
import heppafoorumi.domain.Alue;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Tekstikayttoliittyma {

    final AlueDao alueDao;
    final AiheDao aiheDao;
    final ViestiDao viestiDao;
    String missaValikossa;
    boolean jatketaanko;
    boolean kaynnistetaankoSpark;
    Alue alue;
    Aihe aihe;

    public Tekstikayttoliittyma(AlueDao alueDao, AiheDao aiheDao, ViestiDao viestiDao) {
        this.alueDao = alueDao;
        this.aiheDao = aiheDao;
        this.viestiDao = viestiDao;
        this.missaValikossa = "aluevalikossa"; // alussa ollaan alueet-valikossa
        this.jatketaanko = true;
        this.kaynnistetaankoSpark = false;
        this.alue = null;
        this.aihe = null;
    }

    private void ohjeet() {
        // Tulostetaan ohjeet.
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
        System.out.println("start - käynnistää Spark-palvelimen ja poistuu tekstikäyttöliittymästä");
        System.out.println("lopeta - poistuu tekstikäyttöliittymästä");
    }

    private void aluevalikko(String komento, List<String> parametrit) throws SQLException {
        // käsitellään aluevalikon syötteet.

        switch (komento) {
            case "alue":
                if (parametrit.isEmpty() || parametrit.size() != 1) {
                    System.out.println("Virhe: komento alue ottaa yhden parametrin!\n"
                            + "Käyttö: alue <alueId>");
                    this.ohjeet();
                    return;
                }
                int alueId = Integer.parseInt(parametrit.get(0));

                Alue valittuAlue = alueDao.findOne(alueId);

                if (valittuAlue == null) {
                    System.out.println("Virhe: Ei löydetty aluetta, jonka alueId on " + alueId + "!");
                    break;
                }

                this.missaValikossa = "aihevalikossa";
                this.alue = valittuAlue;
                break;
            case "alueet":
                if (!parametrit.isEmpty()) {
                    System.out.println("Virhe: komento alueet ei ota parametreja!\n"
                            + "Käyttö: alueet");
                    this.ohjeet();
                    return;
                }
                // listataan alueet.
                System.out.println("  id aikaleima             otsikko ##### kuvaus");
                List<Alue> alueet = alueDao.findAll();
                for (Alue alue : alueet) {
                    System.out.println(alue);
                }
                break;
            case "lisaa":
                // lisätään alue.
                if (parametrit.size() >= 2) {
                    alueDao.create(parametrit.get(0), parametrit.get(1));
                } else {
                    System.out.println("Annoit liian vähän parametrejä!\n"
                            + "Käyttö: lisaa <otsikko> <teksti>");
                }
                break;
            default:
                // virheellinen syöte. listataan aluevalikon ohjeet.
                ohjeet();
                break;
        }
    }

    private void aihevalikko(String komento, List<String> parametrit) throws SQLException {
        // käsitellään aihevalikon syötteet.

        switch (komento) {
            case "aiheet":
                if (!parametrit.isEmpty()) {
                    System.out.println("Virhe: komento aiheet ei ota parametreja!\n"
                            + "Käyttö: aiheet");
                    this.ohjeet();
                    return;
                }
                // listataan aiheet.
                System.out.println("id aikaleima             nimimerkki ##### otsikko ##### kuvaus");
                List<Aihe> aiheet = aiheDao.findAll(this.alue.getId());
                for (Aihe aihe : aiheet) {
                    System.out.println(aihe);
                }
            case "lisaa":
                // lisätään aihe.
                if (parametrit.size() >= 3) {
                    aiheDao.create(this.alue.getId(), parametrit.get(0), parametrit.get(1), parametrit.get(2));
                } else {
                    System.out.println("Annoit liian vähän parametrejä!\n"
                            + "Käyttö: lisaa <nimimerkki> <otsikko> <teksti>");
                }
                break;
            default:
        }
    }

    private void viestivalikko(String komento, List<String> parametrit) throws SQLException {
        // käsitellään viestivalikon syötteet.
    }

    public boolean kaynnista() throws SQLException {
        Scanner lukija = new Scanner(System.in);
        this.ohjeet();

        while (this.jatketaanko) {
            System.out.print("Olet " + this.missaValikossa + ".");

            switch (this.missaValikossa) {
                case "aluevalikossa":
                    break;
                case "aihevalikossa":
                    System.out.println("Tällä hetkellä valittuna on seuraava alue:\n"
                            + "  id aikaleima             otsikko\n"
                            + this.alue);
                    break;
                case "viestivalikossa":
                    System.out.println("Tällä hetkellä valittuna on seuraava aihe:\n"
                            + "  id aikaleima             otsikko\n"
                            + this.aihe);
                    break;
                default:
                    System.err.println("Virhe Heppafoorumin tekstikäyttöliittymän koodissa.");
                    this.missaValikossa = "aluevalikossa";
                    break;
            }

            System.out.print(" Anna komento: ");
            String syote = lukija.nextLine();
            syote = syote.replaceAll("\n", "");

            String[] osat;

            if (syote.contains("'")) {
                // OK, syöte sisältää '.
                // Käyttö: komento 'parametri 1' 'parametri 2' ...
                // Esim. lisaa 'alueen otsikko' 'alueen kuvaus' (aluevalikossa).
                // Huom. komentoa ei pidä laittaa hipsuihin, kaikki parametrit
                // sen sijaan kyllä, jos käytetään hipsuja. Vaihtoehtoisesti ei
                // yhtään hipsua. Tekstikäyttöliittymä ei toistaiseksi
                // mahdollista hipsujen syöttämistä parametreihin.

                osat = syote.split("'");

                if (osat[osat.length - 1].equals("\n")) {
                    // korvataan mahdollinen lopun rivinvaihto tyhjällä merkkijonolla.
                    osat[osat.length - 1] = "";
                }

                boolean onkoKelvollinenSyote = true;

                // Parittomien osien tulee olla tyhjiä, muutoin kyseessä on virhe.
                for (int i = 1; i < osat.length; i += 2) {
                    if (!osat[i].isEmpty()) {
                        this.ohjeet();
                        onkoKelvollinenSyote = false;
                        break;
                    }
                }

                if (!onkoKelvollinenSyote) {
                    continue;
                }
            } else {
                // korvataan peräkkäiset välilyönnit yhdellä välilyönnillä,
                // jotta split(" ") jakaa merkkijonon osiin halutulla tavalla.
                osat = syote.split(" ");
            }
            if (osat.length == 0
                    || osat[0].equalsIgnoreCase("ohjeet")
                    || osat[0].equalsIgnoreCase("apua")
                    || osat[0].equalsIgnoreCase("help")) {
                this.ohjeet();
                continue;
            }

            String komento = osat[0].trim();
            List<String> parametritList = Arrays.asList(osat);
            ArrayList<String> parametrit = new ArrayList(parametritList);
            parametrit.remove(0);

            if (komento.equals("start")) {
                this.jatketaanko = false;
                this.kaynnistetaankoSpark = true;
                continue;
            } else if (komento.equals("lopeta")) {
                this.jatketaanko = false;
                continue;
            }

            switch (this.missaValikossa) {
                case "aluevalikossa":
                    this.aluevalikko(komento, parametrit);
                    break;
                case "aihevalikossa":
                    this.aihevalikko(komento, parametrit);
                    break;
                case "viestivalikossa":
                    this.viestivalikko(komento, parametrit);
                    break;
                default:
                    System.err.println("Virhe Heppafoorumin tekstikäyttöliittymän koodissa.");
                    this.missaValikossa = "aluevalikossa";
                    break;
            }
        }
        System.out.println("Poistuit Heppafoorumin tekstikäyttöliittymästä.\n"
                + "Kiitos ja tervetuloa uudelleen!");
        return this.kaynnistetaankoSpark;
    }
}
