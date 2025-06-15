package DAO;

import Models.Breed;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

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
                byte[] imageBytes = rs.getBytes("breedImage");
                listBreed.add(new Breed(id, name, species, status, imageBytes));
            }
        } catch (Exception ex) {
            // handle as needed
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
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
                byte[] imageBytes = rs.getBytes("breedImage");
                listBreed.add(new Breed(id, name, species, status, imageBytes));
            }
        } catch (Exception ex) {
            // handle as needed
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
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
            // handle as needed
        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
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

        } finally {
            try {
                if (rs != null) {
                    rs.close();
                }
            } catch (Exception e) {
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
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
                        rs.getBytes("breedImage")
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
            }
            try {
                if (ps != null) {
                    ps.close();
                }
            } catch (Exception e) {
            }
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (Exception e) {
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
                breed.setBreedImage(rs.getBytes("breedImage"));

                listbreed.add(breed);
            }
        } catch (Exception e) {
        }
        return listbreed;
    }
}
