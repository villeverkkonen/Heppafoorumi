package heppafoorumi;

import heppafoorumi.dao.AiheDao;
import heppafoorumi.dao.AlueDao;
import heppafoorumi.dao.ViestiDao;
import heppafoorumi.domain.Alue;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import spark.ModelAndView;
import static spark.Spark.get;
import spark.template.thymeleaf.ThymeleafTemplateEngine;
import heppafoorumi.database.Database;
import heppafoorumi.domain.Aihe;
import heppafoorumi.domain.Alueraportti;
import heppafoorumi.domain.Viesti;
import heppafoorumi.tekstikayttoliittyma.Tekstikayttoliittyma;
import java.util.ArrayList;
import java.util.Collections;
import spark.Spark;
import static spark.Spark.post;

public class Heppafoorumi {

    private static boolean onkoLinux() {
        Spark.staticFileLocation("public");
        // http://stackoverflow.com/questions/3282498/how-can-i-detect-a-unix-like-os-in-java/3282597#3282597
        // Oletetaan että jos ei ole Windows, niin on Linux.
        return (!(System.getProperty("os.name").startsWith("Windows")));
    }

    public static void main(String[] args) throws Exception {
        boolean tekstikayttoliittymaaKaytossa = false;

        // alla oleva koodi on kehityksen nopeuttamiseksi,
        // kun ei tarvitse huolehtia vanhoista palvelinprosesseista.
        boolean lopetetaankoVanhatPalvelinprosessit = true;

        if (lopetetaankoVanhatPalvelinprosessit && onkoLinux()) {
            // lopetetaan vanha palvelin, toimii Linuxissa.
            // jos käyttöjärjestelmä on Windows, tätä lohkoa ei suoriteta.

            // tällä tavoin tehtynä vanhojen prosessien niittaus toimii
            // (run toimii, clean & build myös).
            try (PrintWriter writer = new PrintWriter("/tmp/niittaa_spark", "UTF-8")) {

                // kirjoitetaan tiedostoon /tmp/niittaa_spark bash-skripti joka
                // lopettaa porttia 4567 kuuntelevat prosessit.
                writer.println("#!/bin/bash");
                writer.println("for i in $(lsof -ti :4567); do kill -9 $i; done");
                writer.close();

                // määritellään käynnistettävä ohjelma. 
                String[] cmd = new String[]{"/bin/sh", "/tmp/niittaa_spark"};

                try {
                    // käynnistetään äsken luotu bash-skripti.
                    Process prosessi = Runtime.getRuntime().exec(cmd);
                    // odotetaan että skriptin suoritus päättyy.
                    prosessi.waitFor();
                } catch (IOException | InterruptedException e) {
                    // ei tehdä mitään poikkeuksille.
                }
            } finally {
                // poistetaan äsken luotu bash-skripti, onnistui lopetus tai ei.
                File tiedosto = new File("/tmp/niittaa_spark");
                tiedosto.delete();
            }
        }

        // no niin, vanhat palvelinprosessit lopetettu, siirrytään asiaan.
        Database database = new Database("jdbc:h2:./database");

        AlueDao alueDao = new AlueDao(database);
        AiheDao aiheDao = new AiheDao(database);
        ViestiDao viestiDao = new ViestiDao(database);

        if (tekstikayttoliittymaaKaytossa) {
            // käynnistetään tekstikäyttöliittymä.
            Tekstikayttoliittyma tekstikayttoliittyma = new Tekstikayttoliittyma(alueDao, aiheDao, viestiDao);

            boolean kaynnistetaanko = tekstikayttoliittyma.kaynnista();

            if (!kaynnistetaanko) {
                return;
            }
        }

        // Aihe aihe1 = new Aihe(1, Timestamp.valueOf("2016-01-01 00:00:03"), ponialue, "trolli", "Ponit on perseestä!!!", "En tykkää poneista.");
        // lambda-lausekkeet HTTP-pyyntöjen käsittelyä varten.
        // Heppafoorumin pääsivu.
        get("/", (req, res) -> {
            HashMap<String, Object> data = new HashMap();

            List<Alueraportti> alueraportit = alueDao.findTarpeellisetTiedot();
            data.put("alueraportit", alueraportit);

            //joka alueen viestien kokonaismäärän näyttäminen
            //List<Integer> viestitYhteensa = new ArrayList<>();
            //int i = 1;
            //int alueenViestit = 0;
            //for (Alue alue : alueet) {
            //    int alueId = alue.getId();
            //    alueenViestit = viestiDao.getFindAllCount(alueId);
            //    viestitYhteensa.add(alueId, alueenViestit);
            //}
            //data.put("viestit", viestitYhteensa);
            return new ModelAndView(data, "alueet");
        }, new ThymeleafTemplateEngine()
        );

        get("/aiheet/:alue", (req, res) -> {
            HashMap<String, Object> data = new HashMap();

            int alueId = Integer.parseInt(req.params(":alue"));
            Alue alue = alueDao.findOne(alueId);
            data.put("alue", alue);

            List<Aihe> aiheet = aiheDao.findAll(alueId);
            data.put("aiheet", aiheet);

            //yritys tulostaa joka aiheen uusimman viestin timestamp
            List<Viesti> uusimmatViestit = new ArrayList<>();
            for (Aihe aihe : aiheet) {                                                              //käydään läpi kaikki alueen aiheet, jotta saadaan kunkin aiheen id
                List<Viesti> lista = viestiDao.findAiheidenUusimmatViestit(alueId, aihe.getId());   //Kutsutaan metodia, jonka pitäisi ainakin palauttaa joka aiheen uusimman viestin
                for (Viesti viesti : lista) {                                                       //käydään lista läpi, siellä pitäisi olla kerrallaan aina vain yksi Viesti-olio,
                    uusimmatViestit.add(viesti);                                                    //eli uusimmatViestit.add(lista.get(0)); pitäisi must toimia myös mut sillon räjähtää,
                }                                                                                   //koska valittaa että indexOutOfBounds, tyhjä lista, ei saada getattua.

            }
            data.put("uusimmatViestit", uusimmatViestit);

            //yritys näyttää jokaisen aiheen kaikkien viestien kokonaismäärä
            //List<String> viestitYhteensa = new ArrayList<>();
            //for (Aihe aihe : aiheet) {
            //    viestitYhteensa.add(Integer.toString(viestiDao.CountAiheViestit(aihe.getId())));
            //}
            //data.put("viestitYhteensa", viestitYhteensa);
            return new ModelAndView(data, "aiheet");
        }, new ThymeleafTemplateEngine());

        get("/viestit/:alue_ja_aihe", (req, res) -> {
            HashMap<String, Object> data = new HashMap();

            String alueJaAihe = req.params(":alue_ja_aihe");
            int erotinmerkinIndeksi = alueJaAihe.indexOf('-');

            String alueString = alueJaAihe.substring(0, erotinmerkinIndeksi);
            data.put("alue_string", alueString);

            String aiheString = alueJaAihe.substring(erotinmerkinIndeksi + 1);
            int aiheId = Integer.parseInt(aiheString);
            Aihe aihe = aiheDao.findOne(aiheId);
            data.put("aihe", aihe);

            List<Viesti> viestit = viestiDao.findAll(aiheId);
            data.put("viestit", viestit);

            // viiden uusimman viestin näyttäminen
            List<Viesti> kaanteinenLista = new ArrayList(viestit);
            Collections.reverse(kaanteinenLista);
            List<Viesti> uusimmatViestit = kaanteinenLista.subList(0, Math.min(kaanteinenLista.size(), 5));
            data.put("uusimmatViestit", uusimmatViestit);

            return new ModelAndView(data, "viestit");
        }, new ThymeleafTemplateEngine());

        post("/", (req, res) -> {
            String otsikko = req.queryParams("otsikko");
            String kuvaus = req.queryParams("kuvaus");

            if (!otsikko.isEmpty() && !kuvaus.isEmpty()) {
                alueDao.create(otsikko, kuvaus);
            }

            res.redirect("/");
            return "";
        });

        post("/aiheet/:alue", (req, res) -> {
            String nimimerkki = req.queryParams("nimimerkki");
            String aihe = req.queryParams("aihe");
            String kuvaus = req.queryParams("kuvaus");
            int alueId = Integer.parseInt(req.params(":alue"));

            if (!nimimerkki.isEmpty() && !aihe.isEmpty() && !kuvaus.isEmpty()) {
                aiheDao.create(alueId, nimimerkki, aihe, kuvaus);
            }

            res.redirect("/aiheet/" + alueId);
            return "";
        });

        post("/viestit/:alue_ja_aihe", (req, res) -> {
            String alueJaAihe = req.params(":alue_ja_aihe");
            int erotinmerkinIndeksi = alueJaAihe.indexOf('-');

            String aiheString = alueJaAihe.substring(erotinmerkinIndeksi + 1);
            int aiheId = Integer.parseInt(aiheString);

            String nimimerkki = req.queryParams("nimimerkki");
            String viesti = req.queryParams("viesti");

            viestiDao.create(aiheId, nimimerkki, viesti);

            res.redirect("/viestit/" + alueJaAihe);
            return "";
        });

        //yritys delete napille
        post("/viestit/:alue_ja_aihe_ja_viesti", (req, res) -> {
            String alueJaAiheJaViesti = req.params(":alue_ja_aihe_ja_viesti");
            int erotinmerkinIndeksi = alueJaAiheJaViesti.indexOf('-');

            String aiheString = alueJaAiheJaViesti.substring(erotinmerkinIndeksi + 1, erotinmerkinIndeksi + 2);
            String viestiString = alueJaAiheJaViesti.substring(erotinmerkinIndeksi + 3);

            int aiheId = Integer.parseInt(aiheString);
            int viestiId = Integer.parseInt(viestiString);

            viestiDao.delete(aiheId, viestiId);

            String alueJaAihe = alueJaAiheJaViesti.substring(0, 2);

            res.redirect("/viestit/" + alueJaAihe);
            return "";
        });
    }
}
