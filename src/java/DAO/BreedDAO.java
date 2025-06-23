package DAO;

import Models.Breed;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Lenovo
 */
public class BreedDAO {

    private Connection conn;
    private PreparedStatement ps;
    private ResultSet rs;

    public List<Breed> displayDogBreeds() {
        List<Breed> listBreed = new ArrayList<>();
        String sql = ""
                + "SELECT breedId, breedName, breedSpecies, breedStatus, breedImage "
                + "FROM BreedTB "
                + "WHERE breedSpecies = N'Chó' "
                + "ORDER BY breedName ASC";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("breedId");
                String name = rs.getString("breedName");
                String species = rs.getString("breedSpecies");
                boolean status = rs.getBoolean("breedStatus");
                String image = rs.getString("breedImage");
                listBreed.add(new Breed(id, name, species, status, image));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listBreed;
    }

    public List<Breed> displayCatBreeds() {
        List<Breed> listBreed = new ArrayList<>();
        String sql = ""
                + "SELECT breedId, breedName, breedSpecies, breedStatus, breedImage "
                + "FROM BreedTB "
                + "WHERE breedSpecies = N'Mèo' "
                + "ORDER BY breedName ASC";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("breedId");
                String name = rs.getString("breedName");
                String species = rs.getString("breedSpecies");
                boolean status = rs.getBoolean("breedStatus");
                String image = rs.getString("breedImage");
                listBreed.add(new Breed(id, name, species, status, image));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listBreed;
    }

    public List<Breed> getAllBreeds() {
        List<Breed> list = new ArrayList<>();
        String sql = "SELECT breedId, breedName FROM BreedTB WHERE breedStatus = 1 ORDER BY breedName ASC";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Breed breed = new Breed();
                breed.setBreedId(rs.getInt("breedId"));
                breed.setBreedName(rs.getString("breedName"));
                list.add(breed);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public List<String> getAllSpecies() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT DISTINCT breedSpecies FROM BreedTB WHERE breedStatus = 1 ORDER BY breedSpecies ASC";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("breedSpecies"));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public List<Breed> getBreedsBySpecies(String species) {
        List<Breed> list = new ArrayList<>();
        String sql = "SELECT * FROM BreedTB WHERE breedSpecies = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, species);
            rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new Breed(
                        rs.getInt("breedId"),
                        rs.getString("breedName"),
                        rs.getString("breedSpecies"),
                        rs.getBoolean("breedStatus"),
                        rs.getString("breedImage")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public List<Breed> get6BreedHot() {
        List<Breed> listbreed = new ArrayList<>();
        String sql = "SELECT TOP 6 b.breedId, b.breedName, b.breedSpecies, b.breedImage, COUNT(p.petId) AS petCount\n"
                + "FROM BreedTB b\n"
                + "JOIN PetTB p ON b.breedId = p.breedId\n"
                + "GROUP BY b.breedId, b.breedName, b.breedSpecies, b.breedImage\n"
                + "ORDER BY petCount DESC;";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                Breed breed = new Breed();
                breed.setBreedId(rs.getInt("breedId"));
                breed.setBreedName(rs.getString("breedName"));
                breed.setBreedSpecies(rs.getString("breedSpecies"));
                breed.setBreedImage(rs.getString("breedImage"));
                listbreed.add(breed);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return listbreed;
    }

    private StringBuilder buildFilterQuery(String searchKey, String species, String status, List<Object> params) {
        StringBuilder sql = new StringBuilder(
                " FROM BreedTB b"
                + " LEFT JOIN PetTB p ON p.breedId = b.breedId"
                + " LEFT JOIN OrderContentTB oc ON oc.petId = p.petId"
                + " LEFT JOIN OrderTB o ON o.orderId = oc.orderId AND o.orderStatus = 'Delivered'"
                + " WHERE 1=1 "
        );

        if (searchKey != null && !searchKey.trim().isEmpty()) {
            if (searchKey.matches("\\d+")) {
                sql.append(" AND b.breedId = ?");
                params.add(Integer.parseInt(searchKey.trim()));
            } else {
                sql.append(" AND b.breedName LIKE ?");
                params.add("%" + searchKey.trim() + "%");
            }
        }

        if (species != null && !species.isEmpty()) {
            sql.append(" AND b.breedSpecies = ?");
            params.add(species);
        }

        if (status != null && !status.isEmpty()) {
            sql.append(" AND b.breedStatus = ?");
            params.add("1".equals(status));
        }
        return sql;
    }

    public int countFilteredBreeds(String searchKey, String species, String status) {
        List<Object> params = new ArrayList<>();
        StringBuilder sqlBase = new StringBuilder("SELECT COUNT(DISTINCT b.breedId) ");

        sqlBase.append(buildFilterQuery(searchKey, species, status, params));

        try (Connection conn = new DBContext().getConnection(); PreparedStatement ps = conn.prepareStatement(sqlBase.toString())) {
            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return rs.getInt(1);
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public List<Breed> filterBreedsForManager(String searchKey, String species, String status, int pageNumber, int pageSize) {
        List<Breed> list = new ArrayList<>();
        List<Object> params = new ArrayList<>();

        StringBuilder sql = new StringBuilder(
                "SELECT b.breedId, b.breedName, b.breedSpecies, b.breedImage, b.breedStatus,"
                + " COUNT(o.orderId) AS totalPurchases"
        );

        sql.append(buildFilterQuery(searchKey, species, status, params));

        sql.append(" GROUP BY b.breedId, b.breedName, b.breedSpecies, b.breedImage, b.breedStatus");
        sql.append(" ORDER BY b.breedId ASC");

        sql.append(" OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");
        int offset = (pageNumber - 1) * pageSize;
        params.add(offset);
        params.add(pageSize);

        try (Connection conn = new DBContext().getConnection(); PreparedStatement ps = conn.prepareStatement(sql.toString())) {

            for (int i = 0; i < params.size(); i++) {
                ps.setObject(i + 1, params.get(i));
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("breedId");
                    String name = rs.getString("breedName");
                    String sp = rs.getString("breedSpecies");
                    boolean st = rs.getBoolean("breedStatus");
                    String img = rs.getString("breedImage");
                    int purchases = rs.getInt("totalPurchases");
                    list.add(new Breed(id, name, sp, st, img, purchases));
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return list;
    }

    public boolean breedNameExists(String breedName) {
        DBContext db = new DBContext();
        List<String> names = new ArrayList<>();
        try {
            conn = db.getConnection();
            String sql = "SELECT breedName FROM BreedTB";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                names.add(rs.getString(1));
            }
            if (names.contains(breedName)) {
                return true;
            } else {
                return false;
            }
        } catch (Exception ex) {
            Logger.getLogger(BreedDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return true;
    }
}
