package entities;

public class Urun {

    private Long id;

    private String ad;

    private double fiyat;

    private int stokMiktari;

    public Urun(Long id, String ad, double fiyat, int stokMiktari) {
        this.id = id;
        this.ad = ad;
        this.fiyat = fiyat;
        this.stokMiktari = stokMiktari;
    }

    public Urun(String ad, double fiyat, int stokMiktari) {
        this.ad = ad;
        this.fiyat = fiyat;
        this.stokMiktari = stokMiktari;
    }

    public Urun() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAd() {
        return ad;
    }

    public void setAd(String ad) {
        this.ad = ad;
    }

    public double getFiyat() {
        return fiyat;
    }

    public void setFiyat(double fiyat) {
        this.fiyat = fiyat;
    }

    public int getStokMiktari() {
        return stokMiktari;
    }

    public void setStokMiktari(int stokMiktari) {
        this.stokMiktari = stokMiktari;
    }

}
