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
                int status = rs.getInt("breedStatus");
                byte[] image = rs.getBytes("breedImage");
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
                int status = rs.getInt("breedStatus");
                byte[] image = rs.getBytes("breedImage");
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
        String sql = "SELECT * FROM BreedTB WHERE breedSpecies = ? AND breedStatus = 1 ";
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
                        rs.getInt("breedStatus"),
                        rs.getBytes("breedImage")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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

        return list;
    }

    public String getSpeciesByBreed(String breedId) {

        String sql = "SELECT breedSpecies FROM BreedTB WHERE breedId = ?";
        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql);
            ps.setString(1, breedId);
            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getString("breedSpecies");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        return null;
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
                breed.setBreedImage(rs.getBytes("breedImage"));
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

    private StringBuilder buildFilterQuery(String searchKey, String species, String status) {
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
            } else {
                sql.append(" AND b.breedName LIKE ?");
            }
        }

        if (species != null && !species.isEmpty()) {
            sql.append(" AND b.breedSpecies = ?");
        }

        if (status != null && !status.isEmpty()) {
            sql.append(" AND b.breedStatus = ?");
        }
        return sql;
    }

    public int countFilteredBreeds(String searchKey, String species, String status) {
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        StringBuilder sqlBase = new StringBuilder("SELECT COUNT(DISTINCT b.breedId) ");
        sqlBase.append(buildFilterQuery(searchKey, species, status));

        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sqlBase.toString());

            int paramIndex = 1;
            if (searchKey != null && !searchKey.trim().isEmpty()) {
                if (searchKey.matches("\\d+")) {
                    ps.setInt(paramIndex++, Integer.parseInt(searchKey.trim()));
                } else {
                    ps.setString(paramIndex++, "%" + searchKey.trim() + "%");
                }
            }
            if (species != null && !species.isEmpty()) {
                ps.setString(paramIndex++, species);
            }
            if (status != null && !status.isEmpty()) {
                ps.setBoolean(paramIndex++, "1".equals(status));
            }

            rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return 0;
    }

    public List<Breed> filterBreedsForManager(String searchKey, String species, String status, int pageNumber, int pageSize) {
        List<Breed> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;

        StringBuilder sql = new StringBuilder(
                "SELECT b.breedId, b.breedName, b.breedSpecies, b.breedImage, b.breedStatus,"
                + " COUNT(o.orderId) AS totalPurchases"
        );

        sql.append(buildFilterQuery(searchKey, species, status));
        sql.append(" GROUP BY b.breedId, b.breedName, b.breedSpecies, b.breedImage, b.breedStatus");
        sql.append(" ORDER BY b.breedId ASC");
        sql.append(" OFFSET ? ROWS FETCH NEXT ? ROWS ONLY");

        try {
            conn = new DBContext().getConnection();
            ps = conn.prepareStatement(sql.toString());

            int paramIndex = 1;
            if (searchKey != null && !searchKey.trim().isEmpty()) {
                if (searchKey.matches("\\d+")) {
                    ps.setInt(paramIndex++, Integer.parseInt(searchKey.trim()));
                } else {
                    ps.setString(paramIndex++, "%" + searchKey.trim() + "%");
                }
            }
            if (species != null && !species.isEmpty()) {
                ps.setString(paramIndex++, species);
            }
            if (status != null && !status.isEmpty()) {
                ps.setBoolean(paramIndex++, "1".equals(status));
            }

            int offset = (pageNumber - 1) * pageSize;
            ps.setInt(paramIndex++, offset);
            ps.setInt(paramIndex++, pageSize);

            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("breedId");
                String name = rs.getString("breedName");
                String sp = rs.getString("breedSpecies");
                int st = rs.getInt("breedStatus");
                byte[] img = rs.getBytes("breedImage");
                int purchases = rs.getInt("totalPurchases");
                list.add(new Breed(id, name, sp, st, img, purchases));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
                if (ps != null) {
                    ps.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        return list;
    }

    public boolean breedNameExists(String breedName, int breedId) {
        DBContext db = new DBContext();
        List<String> names = new ArrayList<>();
        try {
            conn = db.getConnection();
            String sql = "SELECT 1 FROM BreedTB WHERE breedName = ? AND breedId!=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, breedName);
            ps.setInt(2, breedId);
            rs = ps.executeQuery();
            return rs.next();
        } catch (Exception ex) {
            Logger.getLogger(BreedDAO.class.getName()).log(Level.SEVERE, null, ex);
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
        return false;
    }

    public boolean breedNameExists(String breedName) {
        DBContext db = new DBContext();
        List<String> names = new ArrayList<>();
        try {
            conn = db.getConnection();
            String sql = "SELECT 1 FROM BreedTB WHERE breedName=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, breedName);
            rs = ps.executeQuery();
            return rs.next();
        } catch (Exception ex) {
            Logger.getLogger(BreedDAO.class.getName()).log(Level.SEVERE, null, ex);
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
        return false;
    }

    public String getBreedNameById(int id) {
        DBContext db = new DBContext();
        try {
            conn = db.getConnection();
            String sql = "SELECT breedName\n"
                    + "FROM BreedTB\n"
                    + "WHERE breedId=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                return rs.getString("breedName");
            }
        } catch (Exception ex) {
            Logger.getLogger(BreedDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean addBreed(Breed breed) {
        DBContext db = new DBContext();
        try {
            conn = db.getConnection();
            String sql = "INSERT INTO BreedTB (breedName, breedSpecies, breedImage, breedStatus)\n"
                    + "VALUES (?, ?, ?, ?);";
            ps = conn.prepareStatement(sql);
            ps.setString(1, breed.getBreedName());
            ps.setString(2, breed.getBreedSpecies());
            ps.setBytes(3, breed.getBreedImage());
            ps.setInt(4, breed.getBreedStatus());
            ps.executeUpdate();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(BreedDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean updateBreedStatusById(int breedId, int breedStatus) {
        DBContext db = new DBContext();
        try {
            conn = db.getConnection();
            String sql = "UPDATE BreedTB SET breedStatus=? WHERE breedId=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, breedStatus);
            ps.setInt(2, breedId);
            ps.executeUpdate();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(BreedDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public Breed getBreedById(int id) {
        DBContext db = new DBContext();
        Breed breed = new Breed();
        try {
            conn = db.getConnection();
            String sql = "SELECT * FROM BreedTB WHERE breedId=?";
            ps = conn.prepareStatement(sql);
            ps.setInt(1, id);
            rs = ps.executeQuery();
            while (rs.next()) {
                breed.setBreedId(rs.getInt("breedId"));
                breed.setBreedName(rs.getString("breedName"));
                breed.setBreedSpecies(rs.getString("breedSpecies"));
                breed.setBreedImage(rs.getBytes("breedImage"));
                breed.setBreedStatus(rs.getInt("breedStatus"));

                return breed;
            }
        } catch (Exception ex) {
            Logger.getLogger(BreedDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public boolean updateBreedImage(int id, byte[] image) {
        DBContext db = new DBContext();
        try {
            conn = db.getConnection();
            String sql = "UPDATE BreedTB SET breedImage=? WHERE breedId=?";
            ps = conn.prepareStatement(sql);
            ps.setBytes(1, image);
            ps.setInt(2, id);
            ps.executeUpdate();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(BreedDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean updateBreedById(Breed breed) {
        DBContext db = new DBContext();
        try {
            conn = db.getConnection();
            String sql = "UPDATE BreedTB\n"
                    + "SET breedName=?, breedSpecies=?, breedStatus=?\n"
                    + "WHERE breedId=?";
            ps = conn.prepareStatement(sql);
            ps.setString(1, breed.getBreedName());
            ps.setString(2, breed.getBreedSpecies());
            ps.setInt(3, breed.getBreedStatus());
            ps.setInt(4, breed.getBreedId());
            ps.executeUpdate();
            return true;
        } catch (Exception ex) {
            Logger.getLogger(BreedDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
}
