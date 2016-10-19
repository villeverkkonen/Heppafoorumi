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
import spark.Spark;

public class Heppafoorumi {

    private static boolean onkoLinux() {
        // http://stackoverflow.com/questions/3282498/how-can-i-detect-a-unix-like-os-in-java/3282597#3282597
        // Oletetaan ettÃ¤ jos ei ole Windows, niin on Linux.
        return (!(System.getProperty("os.name").startsWith("Windows")));
    }

    public static void main(String[] args) throws Exception {
        Spark.staticFileLocation("/public");
        // alla oleva koodi on kehityksen nopeuttamiseksi,
        // kun ei tarvitse huolehtia vanhoista palvelinprosesseista.
        boolean lopetetaankoVanhatPalvelinprosessit = true;

        if (lopetetaankoVanhatPalvelinprosessit && onkoLinux()) {
            // lopetetaan vanha palvelin, toimii Linuxissa.
            // jos kÃ¤yttÃ¶jÃ¤rjestelmÃ¤ on Windows, tÃ¤tÃ¤ lohkoa ei suoriteta.

            // tÃ¤llÃ¤ tavoin tehtynÃ¤ vanhojen prosessien niittaus toimii
            // (run toimii, clean & build myÃ¶s).
            try (PrintWriter writer = new PrintWriter("/tmp/niittaa_spark", "UTF-8")) {

                // kirjoitetaan tiedostoon /tmp/niittaa_spark bash-skripti joka
                // lopettaa porttia 4567 kuuntelevat prosessit.
                writer.println("#!/bin/bash");
                writer.println("sh -c 'for i in $(lsof -ti :4567); do kill -9 $i; done'");
                writer.close();

                // mÃ¤Ã¤ritellÃ¤Ã¤n kÃ¤ynnistettÃ¤vÃ¤ ohjelma. 
                String[] cmd = new String[]{"/bin/sh", "/tmp/niittaa_spark"};

                try {
                    // kÃ¤ynnistetÃ¤Ã¤n Ã¤sken luotu bash-skripti.
                    Process prosessi = Runtime.getRuntime().exec(cmd);
                    // odotetaan ettÃ¤ skriptin suoritus pÃ¤Ã¤ttyy.
                    prosessi.waitFor();
                } catch (IOException | InterruptedException e) {
                    // ei tehdÃ¤ mitÃ¤Ã¤n poikkeuksille.
                }
            } finally {
                // poistetaan Ã¤sken luotu bash-skripti, onnistui lopetus tai ei.
                File tiedosto = new File("/tmp/niittaa_spark");
                tiedosto.delete();
            }
        }

        // no niin, vanhat palvelinprosessit lopetettu, siirrytÃ¤Ã¤n asiaan.
        Database database = new Database("jdbc:h2:./database");

        AlueDao alueDao = new AlueDao(database);
        AiheDao aiheDao = new AiheDao(database);
        ViestiDao viestiDao = new ViestiDao(database);

        // tulostetaan kaikki alueet konsoliin.
        System.out.println("Kaikki alueet");

        List<Alue> alueet = alueDao.findAll();

        for (Alue alue : alueet) {
            System.out.println(alue);
        }

        Alue alue1 = new Alue(0, 2016, "Ponit");
        alueet.add(alue1);
        
        Alue alue2 = new Alue(1, 2016, "Offtopic");
        alueet.add(alue2);
        
//        Aihe aihe1 = new Aihe(1, 2016, alue1, "Ponit on perseestÃ¤!!!", "En tykkÃ¤Ã¤ poneista.");

        // lambda-lausekkeet HTTP-pyyntÃ¶jen kÃ¤sittelyÃ¤ varten.
        // Heppafoorumin pÃ¤Ã¤sivu.
        get("/", (request, response) -> {
            HashMap<String, Object> data = new HashMap();
            data.put("alueet", alueet);
            return new ModelAndView(data, "alueet");
        }, new ThymeleafTemplateEngine());
        
        get("/:alue", (request, response) -> {
            HashMap<String, Object> data = new HashMap();
            data.put("alue", data.get(request.params(":alue")));
            return new ModelAndView(data, "aiheet");
        }, new ThymeleafTemplateEngine());
  
        
//        tulostetaan kaikki aiheet konsoliin.
//        System.out.println("Kaikki alueet");
//
//        List<Aihe> aiheet = new List<>();
//
//        for (Alue alue : alueet) {
//            System.out.println(alue);
//        }
//
//      Aihe aihe1 = new Aihe(0, 2016, "My Litlle Pony");
//        alueet.add(aihe1);
//
//        Aihe aihe2 = new Aihe(1, 2016, "Islanninponit");
//        alueet.add(aihe2);
//  
//        Aihe aihe3 = new Aihe(2, 2016, "Ponit on perseestÃ¤!!!");
//        alueet.add(aihe3);
//
// lambda-lausekkeet HTTP-pyyntÃ¶jen kÃ¤sittelyÃ¤ varten.
// Heppafoorumin pÃ¤Ã¤sivu.
//        get("/", (request, response) -> {
//            HashMap<String, Object> data = new HashMap();
//            data.put("aiheett", aiheet);
//            return new ModelAndView(data, "aiheet");
//        }, new ThymeleafTemplateEngine());
        
    }
}
