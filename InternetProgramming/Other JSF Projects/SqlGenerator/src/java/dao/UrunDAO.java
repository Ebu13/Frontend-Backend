package dao;

import entities.Urun;
import util.GenericDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UrunDAO {

    private GenericDAO<Urun> genericDAO;

    public UrunDAO() {
        genericDAO = new GenericDAO<>();
    }

    public boolean insertUrun(Urun urun) {
        String[] columns = {"ad", "fiyat", "stokMiktari"};
        Object[] values = {urun.getAd(), urun.getFiyat(), urun.getStokMiktari()};
        return genericDAO.insert(urun, columns, values);
    }

    public boolean deleteUrun(long urunId) {
        return genericDAO.delete(new Urun(), "id", urunId);
    }

    public boolean updateUrun(Urun urun) {
        String[] columns = {"ad", "fiyat", "stokMiktari"};
        Object[] values = {urun.getAd(), urun.getFiyat(), urun.getStokMiktari()};
        return genericDAO.update(urun, columns, values, "id", urun.getId());
    }

    public List<Urun> getAllUrunler() {
        List<Urun> urunler = new ArrayList<>();
        ResultSet resultSet = genericDAO.select(new Urun(), new String[]{"id", "ad", "fiyat", "stokMiktari"}, null, null);
        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                    Urun urun = new Urun();
                    urun.setId(resultSet.getLong("id"));
                    urun.setAd(resultSet.getString("ad"));
                    urun.setFiyat(resultSet.getDouble("fiyat"));
                    urun.setStokMiktari(resultSet.getInt("stokMiktari"));
                    urunler.add(urun);
                }
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return urunler;
    }

    public Urun getUrunById(long urunId) {
        ResultSet resultSet = genericDAO.select(new Urun(), new String[]{"id", "ad", "fiyat", "stokMiktari"}, "id", urunId);
        if (resultSet != null) {
            try {
                if (resultSet.next()) {
                    Urun urun = new Urun();
                    urun.setId(resultSet.getLong("id"));
                    urun.setAd(resultSet.getString("ad"));
                    urun.setFiyat(resultSet.getDouble("fiyat"));
                    urun.setStokMiktari(resultSet.getInt("stokMiktari"));
                    resultSet.close();
                    return urun;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
