/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Users;

import Devices.MobileDTO;
import Utils.Utils;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 *
 * @author Tuấn Anh
 */
public class UserDAO {

    private static final String LOGIN = "select fullName, roleID from tbl_User where userID = ? and password = ?";
    private static final String SEARCH = "select mobileID, description, mobileName, yearOfProduction, price, quantity, notSale from tbl_Mobile where price >= ? and price <= ?";
    private static final String CHECK_INVENTORY = "select mobileID, description, mobileName, yearOfProduction, price, quantity, notSale from tbl_Mobile where quantity < 10";
    private static final String SEARCHV2 = "select mobileID, description, mobileName, yearOfProduction, price, quantity, notSale from tbl_Mobile where mobileID like ? or description like ?";
    private static final String GET_ALL_MOBILES = "select mobileID, description, mobileName, yearOfProduction, price, quantity, notSale from tbl_Mobile";
    private static final String UPDATE_QUANTITY = "update tbl_Mobile SET quantity -= ? Where mobileID = ?";
    private static final String UPDATE_QUANTITY_V2 = "update tbl_hasBought SET quantity += ? Where mobileID = ?";
    private static final String GET_MAX_PRICE = "select top 1 * from tbl_Mobile order by price DESC";
    private static final String GET_QUANTITY = "select quantity from tbl_Mobile where mobileID =?";
    private static final String DELETE = "UPDATE dbo.tbl_hasBought\n"
            + "SET mobileID = NULL\n"
            + "WHERE mobileID IN (SELECT mobileID FROM tbl_Mobile WHERE description = ?);\n"
            + "\n"
            + "DELETE FROM tbl_Mobile\n"
            + "WHERE description = ?;";
    private static final String SEARCH_HISTORY = "select h.mobileID, m.description, m.price, h.quantity\n"
            + "from tbl_hasBought h\n"
            + "left join tbl_Mobile m on h.mobileID = m.mobileID\n"
            + "where userID = ?";
    private static final String INSERT_PRODUCT = "insert into tbl_hasBought(userID, mobileID, quantity, description) values (?, ?, ?, ?)";
    private static final String INSERT = "insert into tbl_Mobile(mobileID, description, price, mobileName, yearOfProduction, quantity, notSale) values (?, ?, ?, ?, ?, ?, ?)";
    private static final String UPDATE = "update tbl_Mobile SET description = ?, price = ?, quantity = ?, notSale = ? where mobileID = ?";
    private static final String CHECK_DUPLICATE = "SELECT userID FROM tbl_User WHERE userID=?";
    private static final String INSERT_USER = "INSERT INTO tbl_User(userID, password, roleID, fullName)" + "VALUES(?,?,?,?)";
    private static final String SEARCHV3 = "SELECT userID, fullName FROM tbl_User WHERE fullName like ? and roleID = 2";
    private static final String UPDATEV2 = "UPDATE tbl_User SET fullName=? WHERE userID=?";
    private static final String DELETEV2 = "DELETE tbl_User WHERE userID=?";
    public UserDTO checkLogin(String userID, String password) throws SQLException {
        UserDTO loginUser = null;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = Utils.getConnection();
            if (conn != null) {
                ps = conn.prepareStatement(LOGIN);
                ps.setString(1, userID);
                ps.setString(2, password);
                rs = ps.executeQuery();
                if (rs.next()) {
                    String fullName = rs.getString("fullName");
                    int roleID = rs.getInt("roleID");
                    loginUser = new UserDTO(userID, "******", fullName, roleID);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return loginUser;
    }

    public List<MobileDTO> getMobileList(float min, float max) throws SQLException {
        List<MobileDTO> mobileList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = Utils.getConnection();
            if (conn != null) {
                ps = conn.prepareStatement(SEARCH);
                ps.setFloat(1, min);
                ps.setFloat(2, max);
                rs = ps.executeQuery();
                while (rs.next()) {
                    String mobileID = rs.getString("mobileID");
                    String desciption = rs.getString("description");
                    String mobileName = rs.getString("mobileName");
                    int yearOfProduction = rs.getInt("yearOfProduction");
                    float price = rs.getFloat("price");
                    int quantity = rs.getInt("quantity");
                    int notSale = rs.getInt("notSale");
                    mobileList.add(new MobileDTO(mobileID, desciption, price, mobileName, yearOfProduction, quantity, notSale));
                    Collections.sort(mobileList, new Comparator<MobileDTO>() {
                        @Override
                        public int compare(MobileDTO mobile1, MobileDTO mobile2) {
                            return Float.compare(mobile1.getPrice(), mobile2.getPrice());
                        }
                    });
                }
            }
        } catch (Exception e) {
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return mobileList;
    }

    public List<MobileDTO> getAllMobiles() throws SQLException {
        List<MobileDTO> mobileList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = Utils.getConnection();
            if (conn != null) {
                ps = conn.prepareStatement(GET_ALL_MOBILES);
                rs = ps.executeQuery();
                while (rs.next()) {
                    String mobileID = rs.getString("mobileID");
                    String desciption = rs.getString("description");
                    String mobileName = rs.getString("mobileName");
                    int yearOfProduction = rs.getInt("yearOfProduction");
                    float price = rs.getFloat("price");
                    int quantity = rs.getInt("quantity");
                    int notSale = rs.getInt("notSale");
                    mobileList.add(new MobileDTO(mobileID, desciption, price, mobileName, yearOfProduction, quantity, notSale));
                    Collections.sort(mobileList, new Comparator<MobileDTO>() {
                        @Override
                        public int compare(MobileDTO mobile1, MobileDTO mobile2) {
                            return Float.compare(mobile1.getPrice(), mobile2.getPrice());
                        }
                    });
                }
            }
        } catch (Exception e) {
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return mobileList;
    }

    public List<MobileDTO> getMobileListV2(String search) throws SQLException {
        List<MobileDTO> mobileList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = Utils.getConnection();
            if (conn != null) {
                ps = conn.prepareStatement(SEARCHV2);
                ps.setString(1, "%" + search + "%");
                ps.setString(2, "%" + search + "%");
                rs = ps.executeQuery();
                while (rs.next()) {
                    String mobileID = rs.getString("mobileID");
                    String desciption = rs.getString("description");
                    String mobileName = rs.getString("mobileName");
                    int yearOfProduction = rs.getInt("yearOfProduction");
                    float price = rs.getFloat("price");
                    int quantity = rs.getInt("quantity");
                    int notSale = rs.getInt("notSale");
                    mobileList.add(new MobileDTO(mobileID, desciption, price, mobileName, yearOfProduction, quantity, notSale));
                }
            }
        } catch (Exception e) {
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return mobileList;
    }

    public List<UserDTO> getListUser(String search) throws SQLException {
        List<UserDTO> list = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = Utils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(SEARCHV3);
                ptm.setString(1, "%" + search + "%");
                rs = ptm.executeQuery();
                while (rs.next()) {
                    String userID = rs.getString("userID");
                    String fullName = rs.getString("fullName");
                    String password = "*****";
                    list.add(new UserDTO(userID, password, fullName, 2));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return list;
    }

    public List<MobileDTO> getHistoryList(String userID) throws SQLException {
        List<MobileDTO> mobileList = new ArrayList<>();
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = Utils.getConnection();
            if (conn != null) {
                ps = conn.prepareStatement(SEARCH_HISTORY);
                ps.setString(1, userID);
                rs = ps.executeQuery();
                while (rs.next()) {
                    String mobileID = rs.getString("mobileID");
                    String desciption = rs.getString("description");
                    float price = rs.getFloat("price");
                    int quantity = rs.getInt("quantity");
                    mobileList.add(new MobileDTO(mobileID, desciption, price, "", 0, quantity, 0));
                }
            }
        } catch (Exception e) {
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return mobileList;
    }

    public int getQuantity(MobileDTO mobile) throws SQLException {
        int quantity = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = Utils.getConnection();
            if (conn != null) {
                ps = conn.prepareStatement(GET_QUANTITY);
                ps.setString(1, mobile.getMobileID());
                rs = ps.executeQuery();
                while (rs.next()) {
                    quantity = rs.getInt("quantity");
                }
            }
        } catch (Exception e) {
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return quantity;
    }

    public int getMaxPrice() throws SQLException {
        int maxPrice = 0;
        Connection conn = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        try {
            conn = Utils.getConnection();
            if (conn != null) {
                ps = conn.prepareStatement(GET_MAX_PRICE);
                rs = ps.executeQuery();
                while (rs.next()) {
                    maxPrice = rs.getInt("price");
                }
            }
        } catch (Exception e) {
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return maxPrice;
    }

    public boolean updateQuantity(MobileDTO mobile) throws SQLException {
        boolean checkUpdate = false;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = Utils.getConnection();
            if (conn != null) {
                ps = conn.prepareStatement(UPDATE_QUANTITY);
                ps.setInt(1, mobile.getQuantity());
                ps.setString(2, mobile.getMobileID());
                checkUpdate = ps.executeUpdate() > 0 ? true : false;
            }
        } catch (Exception e) {
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return checkUpdate;
    }

    public boolean updateQuantityV2(MobileDTO mobile) throws SQLException {
        boolean checkUpdate = false;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = Utils.getConnection();
            if (conn != null) {
                ps = conn.prepareStatement(UPDATE_QUANTITY_V2);
                ps.setInt(1, mobile.getQuantity());
                ps.setString(2, mobile.getMobileID());
                checkUpdate = ps.executeUpdate() > 0 ? true : false;
            }
        } catch (Exception e) {
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return checkUpdate;
    }

    public boolean deleteMobile(String description) throws SQLException {
        boolean checkDelete = false;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = Utils.getConnection();
            if (conn != null) {
                ps = conn.prepareStatement(DELETE);
                ps.setString(1, description);
                ps.setString(2, description);
                checkDelete = ps.executeUpdate() > 0 ? true : false;
            }
        } catch (Exception e) {
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return checkDelete;
    }

    public boolean deleteStaff(String userID) throws SQLException {
        boolean checkDelete = false;
        Connection conn = null;
        PreparedStatement ps = null;
        try {
            conn = Utils.getConnection();
            if (conn != null) {
                ps = conn.prepareStatement(DELETEV2);
                ps.setString(1, userID);
                checkDelete = ps.executeUpdate() > 0 ? true : false;
            }
        } catch (Exception e) {
        } finally {
            if (ps != null) {
                ps.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return checkDelete;
    }

    public boolean insert(UserDTO user, MobileDTO mobile) throws SQLException, ClassNotFoundException {
        boolean checkInsert = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = Utils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(INSERT_PRODUCT);
                ptm.setString(1, user.getUserID());
                ptm.setString(2, mobile.getMobileID());
                ptm.setInt(3, mobile.getQuantity());
                ptm.setString(4, mobile.getDescription());
                checkInsert = ptm.executeUpdate() > 0 ? true : false;
            }
        } catch (Exception ex) {
        } finally {
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return checkInsert;
    }

    public boolean insertV2(MobileDTO mobile) throws SQLException, ClassNotFoundException {
        boolean checkInsert = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = Utils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(INSERT);
                ptm.setString(1, mobile.getMobileID());
                ptm.setString(2, mobile.getDescription());
                ptm.setFloat(3, mobile.getPrice());
                ptm.setString(4, mobile.getMobileName());
                ptm.setInt(5, mobile.getYearOfProduction());
                ptm.setInt(6, mobile.getQuantity());
                ptm.setInt(7, mobile.getStatus());
                checkInsert = ptm.executeUpdate() > 0 ? true : false;
            }
        } catch (Exception ex) {
        } finally {
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return checkInsert;
    }

    public boolean insertV3(UserDTO user) throws SQLException, ClassNotFoundException {
        boolean checkInsert = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = Utils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(INSERT_USER);
                ptm.setString(1, user.getUserID());
                ptm.setString(2, user.getPassword());
                ptm.setInt(3, user.getRole());
                ptm.setString(4, user.getFullName());
                checkInsert = ptm.executeUpdate() > 0 ? true : false;
            }
        } catch (Exception ex) {
        } finally {
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return checkInsert;
    }

    public boolean update(MobileDTO mobile) throws SQLException, ClassNotFoundException {
        boolean checkUpdate = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = Utils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(UPDATE);
                ptm.setString(1, mobile.getDescription());
                ptm.setFloat(2, mobile.getPrice());
                ptm.setInt(3, mobile.getQuantity());
                ptm.setInt(4, mobile.getStatus());
                ptm.setString(5, mobile.getMobileID());
                checkUpdate = ptm.executeUpdate() > 0 ? true : false;
            }
        } catch (Exception ex) {
        } finally {
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return checkUpdate;
    }

    public boolean updateV2(UserDTO user) throws SQLException {
        boolean checkUpdate = false;
        Connection conn = null;
        PreparedStatement ptm = null;
        try {
            conn = Utils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(UPDATEV2);
                ptm.setString(1, user.getFullName());
                ptm.setString(2, user.getUserID());
                checkUpdate = ptm.executeUpdate() > 0 ? true : false;
            }
        } catch (Exception ex) {

        } finally {
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return checkUpdate;
    }

    public boolean checkDuplicate(String userID) throws SQLException {
        boolean check = false;
        UserDTO user = null;
        Connection conn = null;
        PreparedStatement ptm = null;
        ResultSet rs = null;
        try {
            conn = Utils.getConnection();
            if (conn != null) {
                ptm = conn.prepareStatement(CHECK_DUPLICATE);
                ptm.setString(1, userID);
                rs = ptm.executeQuery();
                if (rs.next()) {
                    check = true;
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            if (rs != null) {
                rs.close();
            }
            if (ptm != null) {
                ptm.close();
            }
            if (conn != null) {
                conn.close();
            }
        }
        return check;
    }

    public List<MobileDTO> checkLowInventory() {
        List<MobileDTO> mobileList = new ArrayList<>();
        Connection connection = null;
        PreparedStatement pre = null;
        ResultSet rs = null;
        try {
            connection = new Utils().getConnection();
            pre = connection.prepareStatement(CHECK_INVENTORY);
            rs = pre.executeQuery();
                while (rs.next()) {
                    String mobileID = rs.getString("mobileID");
                    String desciption = rs.getString("description");
                    String mobileName = rs.getString("mobileName");
                    int yearOfProduction = rs.getInt("yearOfProduction");
                    float price = rs.getFloat("price");
                    int quantity = rs.getInt("quantity");
                    int notSale = rs.getInt("notSale");
                    mobileList.add(new MobileDTO(mobileID, desciption, price, mobileName, yearOfProduction, quantity, notSale));
                    Collections.sort(mobileList, new Comparator<MobileDTO>() {
                        @Override
                        public int compare(MobileDTO mobile1, MobileDTO mobile2) {
                            return Float.compare(mobile1.getPrice(), mobile2.getPrice());
                        }
                    });
                }
        } catch (Exception e) {
        }
        return mobileList;
    }
}
