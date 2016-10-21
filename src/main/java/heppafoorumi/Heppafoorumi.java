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
import heppafoorumi.domain.Viesti;
import static spark.Spark.post;

public class Heppafoorumi {

    private static boolean onkoLinux() {
        // http://stackoverflow.com/questions/3282498/how-can-i-detect-a-unix-like-os-in-java/3282597#3282597
        // Oletetaan että jos ei ole Windows, niin on Linux.
        return (!(System.getProperty("os.name").startsWith("Windows")));
    }

    public static void main(String[] args) throws Exception {
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

        //        Aihe aihe1 = new Aihe(1, Timestamp.valueOf("2016-01-01 00:00:03"), ponialue, "trolli", "Ponit on perseestä!!!", "En tykkää poneista.");
        // lambda-lausekkeet HTTP-pyyntöjen käsittelyä varten.
        // Heppafoorumin pääsivu.
        get("/", (req, res) -> {
            List<Alue> alueet = alueDao.findAll();
            HashMap<String, Object> data = new HashMap();
            data.put("alueet", alueet);
            return new ModelAndView(data, "alueet");
        }, new ThymeleafTemplateEngine());

        get("/:alue", (req, res) -> {
            HashMap<String, Object> data = new HashMap();

            int alueId = Integer.parseInt(req.params(":alue"));
            Alue alue = alueDao.findOne(alueId);
            data.put("alueet", alue);

            List<Aihe> aiheet = aiheDao.findAll(alueId);
            data.put("aiheet", aiheet);

            return new ModelAndView(data, "aiheet");
        }, new ThymeleafTemplateEngine());

        get("/:alue/:aihe", (req, res) -> {
            HashMap<String, Object> data = new HashMap();

            int alueId = Integer.parseInt(req.params(":alue"));
            Alue alue = alueDao.findOne(alueId);
            data.put("alueet", alue);

            int aiheId = Integer.parseInt(req.params(":aihe"));
            Aihe aihe = aiheDao.findOne(aiheId);
            data.put("aiheet", aihe);

            List<Viesti> viestit = viestiDao.findAll(aiheId);
            data.put("viestit", viestit);

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

        post("/:alue", (req, res) -> {
            String nimimerkki = req.queryParams("nimimerkki");
            String aihe = req.queryParams("aihe");
            String kuvaus = req.queryParams("kuvaus");

            if (!nimimerkki.isEmpty() && !aihe.isEmpty() && !kuvaus.isEmpty()) {
                int alueId = Integer.parseInt(req.params(":alue"));
                aiheDao.create(alueId, nimimerkki, aihe, kuvaus);
            }

            res.redirect(req.params("/:alue"));
            return "";
        });

        post(":alue/:aihe", (req, res) -> {
            String nimimerkki = req.queryParams("nimimerkki");
            String viesti = req.queryParams("viesti");

            viestiDao.create(Integer.parseInt(req.params(":aihe")), nimimerkki, viesti);

            res.redirect("/" + req.params(":alue/:aihe"));
            return "";
        });

        //yritys delete napille
        post("/poistaAlue", (req, res) -> {
            // en tiedä mistä haetaan alueen int id
            alueDao.delete(5);

            res.redirect("/");
            return "";
        });
    }
}
