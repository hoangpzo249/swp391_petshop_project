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
                + "WHERE breedSpecies = N'Dog' "
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
                + "WHERE breedSpecies = N'Cat' "
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

    public List<Breed> getAllBreedsForManager() {
        DBContext db = new DBContext();
        List<Breed> listbreed = new ArrayList<>();
        try {
            conn = db.getConnection();
            String sql = "SELECT \n"
                    + "    b.breedId,\n"
                    + "    b.breedName,\n"
                    + "    b.breedSpecies,\n"
                    + "    b.breedImage,\n"
                    + "    b.breedStatus,\n"
                    + "    COUNT(o.orderId) AS totalPurchases\n"
                    + "FROM \n"
                    + "    BreedTB b\n"
                    + "LEFT JOIN \n"
                    + "    PetTB p \n"
                    + "      ON p.breedId = b.breedId\n"
                    + "LEFT JOIN \n"
                    + "    OrderContentTB oc \n"
                    + "      ON oc.petId = p.petId\n"
                    + "LEFT JOIN \n"
                    + "    OrderTB o \n"
                    + "      ON o.orderId = oc.orderId \n"
                    + "     AND o.orderStatus = 'Delivered'\n"
                    + "GROUP BY \n"
                    + "    b.breedId,\n"
                    + "    b.breedName,\n"
                    + "    b.breedSpecies,\n"
                    + "    b.breedImage,\n"
                    + "    b.breedStatus";
            ps = conn.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                int id = rs.getInt("breedId");
                String name = rs.getString("breedName");
                String species = rs.getString("breedSpecies");
                boolean status = rs.getBoolean("breedStatus");
                String image = rs.getString("breedImage");
                int purchases = rs.getInt("totalPurchases");
                listbreed.add(new Breed(id, name, species, status, image, purchases));
            }
            return listbreed;
        } catch (Exception ex) {
            Logger.getLogger(BreedDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}
