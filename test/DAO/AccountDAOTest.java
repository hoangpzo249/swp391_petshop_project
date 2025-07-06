/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package DAO;

import Models.Account;
import java.util.List;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author HuyHoang
 */
public class AccountDAOTest {
    
    public AccountDAOTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of registerAcc method, of class AccountDAO.
     */
    @Test
    public void testRegisterAcc() {
        System.out.println("registerAcc");
        Account account = null;
        AccountDAO instance = new AccountDAO();
        instance.registerAcc(account);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of quickLogin method, of class AccountDAO.
     */
    @Test
    public void testQuickLogin() {
        System.out.println("quickLogin");
        AccountDAO instance = new AccountDAO();
        Account expResult = null;
        Account result = instance.quickLogin();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of registerAccCusByAdmin method, of class AccountDAO.
     */
    @Test
    public void testRegisterAccCusByAdmin() {
        System.out.println("registerAccCusByAdmin");
        Account account = null;
        AccountDAO instance = new AccountDAO();
        instance.registerAccCusByAdmin(account);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of registerAccStaffByAdmin method, of class AccountDAO.
     */
    @Test
    public void testRegisterAccStaffByAdmin() {
        System.out.println("registerAccStaffByAdmin");
        Account account = null;
        AccountDAO instance = new AccountDAO();
        int expResult = 0;
        int result = instance.registerAccStaffByAdmin(account);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isEmailExist method, of class AccountDAO.
     */
    @Test
    public void testIsEmailExist() {
        System.out.println("isEmailExist");
        String email = "";
        AccountDAO instance = new AccountDAO();
        boolean expResult = false;
        boolean result = instance.isEmailExist(email);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isUsernameExist method, of class AccountDAO.
     */
    @Test
    public void testIsUsernameExist() {
        System.out.println("isUsernameExist");
        String username = "";
        AccountDAO instance = new AccountDAO();
        boolean expResult = false;
        boolean result = instance.isUsernameExist(username);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkPass method, of class AccountDAO.
     */
    @Test
    public void testCheckPass() {
        System.out.println("checkPass");
        String email = "";
        String password = "";
        AccountDAO instance = new AccountDAO();
        boolean expResult = false;
        boolean result = instance.checkPass(email, password);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of checkPassAdmin method, of class AccountDAO.
     */
    @Test
    public void testCheckPassAdmin() {
        System.out.println("checkPassAdmin");
        String password = "";
        AccountDAO instance = new AccountDAO();
        boolean expResult = false;
        boolean result = instance.checkPassAdmin(password);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of isLoginAcc method, of class AccountDAO.
     */
    @Test
    public void testIsLoginAcc() {
        System.out.println("isLoginAcc");
        String email = "";
        String pass = "";
        AccountDAO instance = new AccountDAO();
        Account expResult = null;
        Account result = instance.isLoginAcc(email, pass);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updatePass method, of class AccountDAO.
     */
    @Test
    public void testUpdatePass() {
        System.out.println("updatePass");
        String email = "";
        String pass = "";
        AccountDAO instance = new AccountDAO();
        boolean expResult = false;
        boolean result = instance.updatePass(email, pass);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateEmail method, of class AccountDAO.
     */
    @Test
    public void testUpdateEmail() {
        System.out.println("updateEmail");
        String email = "";
        int accId = 0;
        AccountDAO instance = new AccountDAO();
        boolean expResult = false;
        boolean result = instance.updateEmail(email, accId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateRole method, of class AccountDAO.
     */
    @Test
    public void testUpdateRole() {
        System.out.println("updateRole");
        String role = "";
        int accId = 0;
        AccountDAO instance = new AccountDAO();
        boolean expResult = false;
        boolean result = instance.updateRole(role, accId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of banAcc method, of class AccountDAO.
     */
    @Test
    public void testBanAcc() {
        System.out.println("banAcc");
        int accId = 0;
        AccountDAO instance = new AccountDAO();
        boolean expResult = false;
        boolean result = instance.banAcc(accId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of unBanAcc method, of class AccountDAO.
     */
    @Test
    public void testUnBanAcc() {
        System.out.println("unBanAcc");
        int accId = 0;
        AccountDAO instance = new AccountDAO();
        boolean expResult = false;
        boolean result = instance.unBanAcc(accId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of ggByEmail method, of class AccountDAO.
     */
    @Test
    public void testGgByEmail() {
        System.out.println("ggByEmail");
        String email = "";
        AccountDAO instance = new AccountDAO();
        Account expResult = null;
        Account result = instance.ggByEmail(email);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of registerAccByGG method, of class AccountDAO.
     */
    @Test
    public void testRegisterAccByGG() {
        System.out.println("registerAccByGG");
        Account account = null;
        AccountDAO instance = new AccountDAO();
        instance.registerAccByGG(account);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of genPass method, of class AccountDAO.
     */
    @Test
    public void testGenPass() {
        System.out.println("genPass");
        String expResult = "";
        String result = AccountDAO.genPass();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of updateProfile method, of class AccountDAO.
     */
    @Test
    public void testUpdateProfile() {
        System.out.println("updateProfile");
        Account acc = null;
        AccountDAO instance = new AccountDAO();
        instance.updateProfile(acc);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of uploadAvatar method, of class AccountDAO.
     */
    @Test
    public void testUploadAvatar() {
        System.out.println("uploadAvatar");
        int accId = 0;
        byte[] avatar = null;
        AccountDAO instance = new AccountDAO();
        boolean expResult = false;
        boolean result = instance.uploadAvatar(accId, avatar);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAllAccount method, of class AccountDAO.
     */
    @Test
    public void testGetAllAccount() {
        System.out.println("getAllAccount");
        AccountDAO instance = new AccountDAO();
        List<Account> expResult = null;
        List<Account> result = instance.getAllAccount();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of get10AccountNew method, of class AccountDAO.
     */
    @Test
    public void testGet10AccountNew() {
        System.out.println("get10AccountNew");
        AccountDAO instance = new AccountDAO();
        List<Account> expResult = null;
        List<Account> result = instance.get10AccountNew();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAccId method, of class AccountDAO.
     */
    @Test
    public void testGetAccId_int_String() {
        System.out.println("getAccId");
        int id = 0;
        String role = "";
        AccountDAO instance = new AccountDAO();
        Account expResult = null;
        Account result = instance.getAccId(id, role);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAccId method, of class AccountDAO.
     */
    @Test
    public void testGetAccId_int() {
        System.out.println("getAccId");
        int id = 0;
        AccountDAO instance = new AccountDAO();
        Account expResult = null;
        Account result = instance.getAccId(id);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAccRole method, of class AccountDAO.
     */
    @Test
    public void testGetAccRole() {
        System.out.println("getAccRole");
        String role = "";
        AccountDAO instance = new AccountDAO();
        List<Account> expResult = null;
        List<Account> result = instance.getAccRole(role);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getAccIdStatus method, of class AccountDAO.
     */
    @Test
    public void testGetAccIdStatus() {
        System.out.println("getAccIdStatus");
        int id = 0;
        String role = "";
        String ban = "";
        AccountDAO instance = new AccountDAO();
        Account expResult = null;
        Account result = instance.getAccIdStatus(id, role, ban);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of countAllAcc method, of class AccountDAO.
     */
    @Test
    public void testCountAllAcc() {
        System.out.println("countAllAcc");
        AccountDAO instance = new AccountDAO();
        int expResult = 0;
        int result = instance.countAllAcc();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of countAllAccActive method, of class AccountDAO.
     */
    @Test
    public void testCountAllAccActive() {
        System.out.println("countAllAccActive");
        AccountDAO instance = new AccountDAO();
        int expResult = 0;
        int result = instance.countAllAccActive();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of countAllAccInactive method, of class AccountDAO.
     */
    @Test
    public void testCountAllAccInactive() {
        System.out.println("countAllAccInactive");
        AccountDAO instance = new AccountDAO();
        int expResult = 0;
        int result = instance.countAllAccInactive();
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of searchAcc method, of class AccountDAO.
     */
    @Test
    public void testSearchAcc() {
        System.out.println("searchAcc");
        String keyword = "";
        AccountDAO instance = new AccountDAO();
        List<Account> expResult = null;
        List<Account> result = instance.searchAcc(keyword);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of filterRoleAcc method, of class AccountDAO.
     */
    @Test
    public void testFilterRoleAcc() {
        System.out.println("filterRoleAcc");
        String role = "";
        AccountDAO instance = new AccountDAO();
        List<Account> expResult = null;
        List<Account> result = instance.filterRoleAcc(role);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of filterStatusAcc method, of class AccountDAO.
     */
    @Test
    public void testFilterStatusAcc() {
        System.out.println("filterStatusAcc");
        String status = "";
        AccountDAO instance = new AccountDAO();
        List<Account> expResult = null;
        List<Account> result = instance.filterStatusAcc(status);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of FindUserInfo method, of class AccountDAO.
     */
    @Test
    public void testFindUserInfo() {
        System.out.println("FindUserInfo");
        String username = "";
        AccountDAO instance = new AccountDAO();
        Account expResult = null;
        Account result = instance.FindUserInfo(username);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of LoginUser method, of class AccountDAO.
     */
    @Test
    public void testLoginUser() {
        System.out.println("LoginUser");
        String username = "";
        String password = "";
        AccountDAO instance = new AccountDAO();
        boolean expResult = false;
        boolean result = instance.LoginUser(username, password);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of shipperAccount method, of class AccountDAO.
     */
    @Test
    public void testShipperAccount() {
        System.out.println("shipperAccount");
        int newId = 0;
        AccountDAO instance = new AccountDAO();
        instance.shipperAccount(newId);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getUserFullnameById method, of class AccountDAO.
     */
    @Test
    public void testGetUserFullnameById() {
        System.out.println("getUserFullnameById");
        int accId = 0;
        AccountDAO instance = new AccountDAO();
        String expResult = "";
        String result = instance.getUserFullnameById(accId);
        assertEquals(expResult, result);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
    
}
