package heppafoorumi.dao;

public class KaikkiDao {

    private AlueDao alueDao;
    private AiheDao aiheDao;
    private ViestiDao viestiDao;

    public KaikkiDao() {
        this.alueDao = null;
        this.aiheDao = null;
        this.viestiDao = null;
    }

    public AlueDao getAlueDao() {
        return alueDao;
    }

    public AiheDao getAiheDao() {
        return aiheDao;
    }

    public ViestiDao getViestiDao() {
        return viestiDao;
    }

    public void setAlueDao(AlueDao alueDao) {
        this.alueDao = alueDao;
    }

    public void setAiheDao(AiheDao aiheDao) {
        this.aiheDao = aiheDao;
    }

    public void setViestiDao(ViestiDao viestiDao) {
        this.viestiDao = viestiDao;
    }

}
