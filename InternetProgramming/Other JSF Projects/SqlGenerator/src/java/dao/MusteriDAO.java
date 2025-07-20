package dao;

import entities.Musteri;
import util.GenericDAO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class MusteriDAO {

    private GenericDAO<Musteri> genericDAO;

    public MusteriDAO() {
        genericDAO = new GenericDAO<>();
    }

    public boolean insertMusteri(Musteri musteri) {
        String[] columns = {"ad", "soyad", "email", "telefon"};
        Object[] values = {musteri.getAd(), musteri.getSoyad(), musteri.getEmail(), musteri.getTelefon()};
        return genericDAO.insert(musteri, columns, values);
    }

    public boolean deleteMusteri(long musteriId) {
        return genericDAO.delete(new Musteri(), "id", musteriId);
    }

    public boolean updateMusteri(Musteri musteri) {
        String[] columns = {"ad", "soyad", "email", "telefon"};
        Object[] values = {musteri.getAd(), musteri.getSoyad(), musteri.getEmail(), musteri.getTelefon()};
        return genericDAO.update(musteri, columns, values, "id", musteri.getId());
    }

    public List<Musteri> getAllMusteriler() {
        List<Musteri> musteriler = new ArrayList<>();
        ResultSet resultSet = genericDAO.select(new Musteri(), new String[]{"id", "ad", "soyad", "email", "telefon"}, null, null);
        if (resultSet != null) {
            try {
                while (resultSet.next()) {
                    Musteri musteri = new Musteri();
                    musteri.setId(resultSet.getLong("id"));
                    musteri.setAd(resultSet.getString("ad"));
                    musteri.setSoyad(resultSet.getString("soyad"));
                    musteri.setEmail(resultSet.getString("email"));
                    musteri.setTelefon(resultSet.getString("telefon"));
                    musteriler.add(musteri);
                }
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return musteriler;
    }

    public Musteri getMusteriById(long musteriId) {
        ResultSet resultSet = genericDAO.select(new Musteri(), new String[]{"id", "ad", "soyad", "email", "telefon"}, "id", musteriId);
        if (resultSet != null) {
            try {
                if (resultSet.next()) {
                    Musteri musteri = new Musteri();
                    musteri.setId(resultSet.getLong("id"));
                    musteri.setAd(resultSet.getString("ad"));
                    musteri.setSoyad(resultSet.getString("soyad"));
                    musteri.setEmail(resultSet.getString("email"));
                    musteri.setTelefon(resultSet.getString("telefon"));
                    resultSet.close();
                    return musteri;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
