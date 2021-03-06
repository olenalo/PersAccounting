/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persaccounting.AppStockAccounting.Model;

import com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.sql.*;
import persaccounting.AppStockAccounting.Entity.Commodity;
import persaccounting.Auxiliaries.AlertManagement;
import persaccounting.Configs;

/**
 *
 * @author Olena
 */

public class CommodityDAO {
    private static Connection con;
    private static CommodityDAO instance;
    
    // info --> commodity
    private static final String SQL_INSERT = 
        "insert into commodity("            
            + "commodityName,"
            + "commodityDescription,"
            + "commodityQuantityInStock,"
            + "commodityPriceWithoutTax)"            
        + " values (?,?,?,?)";
    
    private static final String SQL_DELETE = 
        "delete from commodity where id=?";
    
    private static final String SQL_UPDATE = 
        "update commodity set "
            + "commodityName=?, "
            + "commodityDescription=?, "
            + "commodityQuantityInStock=?, "
            + "commodityPriceWithoutTax=? "            
        + "where id=?";
    
    private static final String SQL_SELECT_ALL = 
        "select * from commodity";
    
    private static final String SQL_SELECT_BY_ID = 
        "select * from commodity where id=?";

    private CommodityDAO() {
        // con = DbUtil.getConnection();
        
        // Register JDBC driver:
        try {
            System.out.println("Searching for JDBC_DRIVER..."); // debug
            Class.forName(Configs.JDBC_DRIVER);  // ClassNotFoundException
            /* java.sql.SQLException: No suitable driver found for jdbc:mysql://localhost:3306/persaccounting */
            System.out.println("JDBC_DRIVER has been found."); // debug 
            
        } catch (ClassNotFoundException e) {
            
            AlertManagement.displayErrorAlert(
                "JDBC driver could not be found!",
                "Please confidure JDBC driver! Error itself: " + e.toString());
            
            System.out.println("-------- JDBC_DRIVER could not be found! --------");
            e.printStackTrace();
        }
        
        // Open a connection:
        try {
            System.out.println("Connecting to database..."); // debug
            con = DriverManager.getConnection(
                    Configs.DB_URL,
                    Configs.USER,
                    Configs.PASS);
            System.out.println("Successfully connected to db."); // debug 
            
        } catch (SQLException error) {
            
            AlertManagement.displayErrorAlert(
                "Could not establish database connection, SQLException is thrown!",
                "\nError itself: \n" + error.toString());

            error.printStackTrace();
            
        } catch(Exception error){
            
            AlertManagement.displayErrorAlert(
                "Could not establish database connection!",
                "\nError itself: \n" + error.toString());
            error.printStackTrace();
        } 
    }

    public static CommodityDAO getInstance() {
        if(instance == null) {
            instance = new CommodityDAO();
        }
        return instance;
    }

    public void addCommodity(Commodity c) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement(
                SQL_INSERT, Statement.RETURN_GENERATED_KEYS
            );
            // Parameters start with 1
            preparedStatement.setString(1, c.getCommodityName());
            preparedStatement.setString(2, c.getCommodityDescription());
            preparedStatement.setDouble(3, c.getCommodityQuantityInStock());
            preparedStatement.setDouble(4, c.getCommodityPriceWithoutTax());
            
            System.out.println("-----------------SQL_INSERT-----------------"); // debug            
            System.out.println("Creating SQL_INSERT java statement..."); // debug                
            preparedStatement.executeUpdate();
            System.out.println("SQL query has been successfully executed."); // debug
            
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        if (generatedKeys.next()) {
            c.setId(generatedKeys.getInt(1));
        }
        preparedStatement.close();
        
        } catch (SQLException error) {
            
            AlertManagement.displayErrorAlert(
                "while trying to add commodity, in particular, "
                        + "\nwhile SQL query executing. "
                        + "\nSQLException is thrown!",
                error.toString());

            // debug:
            System.out.println("Could not add commodity! ");
            error.printStackTrace();
            
        } catch (Exception e) { // NumberFormatException - in handleOK()
            
            AlertManagement.displayErrorAlert(
                "while trying to add commodity!",
                e.toString());
            
            // debug:
            System.out.println("Could not add commodity! ");
            e.printStackTrace();   
        }
    }

    public void deleteCommodity(int commoditytId) {
        try {
            
            AlertManagement.displayPreventionAlert(
                "Attention!", 
                "Commodity will be deleted!", 
                "This is just to prevent you. "
                    + "\nAs soon as you close this alert, "
                    + "\nrequest will be proceeded");           
            
            PreparedStatement preparedStatement = con.prepareStatement(SQL_DELETE);
            preparedStatement.setInt(1, commoditytId);
            
            System.out.println("-----------------SQL_DELETE-----------------"); // debug            
            System.out.println("Creating SQL_DELETE java statement..."); // debug            
            preparedStatement.executeUpdate();
            System.out.println("SQL query has been successfully executed."); // debug

            preparedStatement.close();
            
        } catch (SQLException error) {
            
            AlertManagement.displayErrorAlert(
                "while trying to delete commodity, in particular, "
                        + "\nwhile SQL query executing. "
                        + "\nSQLException is thrown!",
                error.toString());

            // debug:
            System.out.println("Could not delete commodity! ");
            error.printStackTrace();
            
        } catch (Exception e) { // NumberFormatException - in handleOK()
            
            AlertManagement.displayErrorAlert(
                "while trying to delete commodity!",
                e.toString());
            
            // debug:
            System.out.println("Could not delete commodity! ");
            e.printStackTrace();   
        }
    }

    public void updateCommodity(Commodity c) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement(SQL_UPDATE);
            preparedStatement.setString(1, c.getCommodityName());
            preparedStatement.setString(2, c.getCommodityDescription());
            preparedStatement.setDouble(3, c.getCommodityQuantityInStock());
            preparedStatement.setDouble(4, c.getCommodityPriceWithoutTax());			
            preparedStatement.setInt(5, c.getId());
            
            System.out.println("-----------------UPDATE-----------------"); // debug            
            System.out.println("Creating SQL_UPDATE java statement..."); // debug
            preparedStatement.executeUpdate();
            System.out.println("SQL query has been successfully executed."); // debug
            
            preparedStatement.close();
            
        } catch (SQLException error) {
            
            AlertManagement.displayErrorAlert(
                "while trying to update commodity, in particular, "
                        + "\nwhile SQL query executing. "
                        + "\nSQLException is thrown!",
                error.toString());

            // debug:
            System.out.println("Could not update commodity! ");
            error.printStackTrace();
            
        } catch (Exception e) { // NumberFormatException - in handleOK()
            
            AlertManagement.displayErrorAlert(
                "while trying to update commodity!",
                e.toString());
            
            // debug:
            System.out.println("Could not update commodity! ");
            e.printStackTrace();   
        }
    }

    public List<Commodity> getAllCommodities() {
        List<Commodity> commoditiestList = new ArrayList<Commodity>();
        try {
            System.out.println("========================================="); // debug                        
            System.out.println("-----------------GET_ALL-----------------"); // debug            
            System.out.println("Creating GET_ALL java statement..."); // debug
            Statement statement = con.createStatement();
            
            System.out.println("Executing the next SQL query: \n" + SQL_SELECT_ALL); // debug
            ResultSet rs = statement.executeQuery(SQL_SELECT_ALL);
            System.out.println("SQL query has been successfully executed."); // debug
            
            while (rs.next()) {
                Commodity c = new Commodity();				
                c.setId(rs.getInt("id"));
                c.setCommodityName(rs.getString("commodityName"));
                c.setCommodityDescription(rs.getString("commodityDescription"));				
                c.setCommodityQuantityInStock(rs.getDouble("commodityQuantityInStock"));
                c.setCommodityPriceWithoutTax(rs.getDouble("commodityPriceWithoutTax"));				
                commoditiestList.add(c);				
            }
            statement.close();
            
        } catch (MySQLSyntaxErrorException e) { // NB: attention to import!
         // com.mysql.jdbc.exceptions.jdbc4.MySQLSyntaxErrorException: Table 'persaccounting.commodity' doesn't exist
           
            AlertManagement.displayErrorAlert(
                "while trying to acces the database table!",
                e.toString());
            
            // debug:
            System.out.println("Could not acces the database table! ");
            e.printStackTrace(); 
        }
        
        catch (Exception e) {
            AlertManagement.displayErrorAlert(
                "while trying to acces the list of commodities!",
                e.toString());
            
            // debug:
            System.out.println("Could not get the list of commodities! ");
            e.printStackTrace(); 
        }
        
        return commoditiestList;
    }

    public Commodity getCommodityById(int commodityId) {
        Commodity c = new Commodity();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(SQL_SELECT_BY_ID);
            preparedStatement.setInt(1, commodityId);
            // TODO: prints to debug
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                c.setId(rs.getInt("id"));
                c.setCommodityName(rs.getString("commodityName"));
                c.setCommodityDescription(rs.getString("commodityDescription"));				
                c.setCommodityQuantityInStock(rs.getInt("commodityQuantityInStock"));
                c.setCommodityPriceWithoutTax(rs.getInt("commodityPriceWithoutTax"));
                preparedStatement.close();
            }
        } catch (MySQLSyntaxErrorException e) { // NB: attention to import!
           
            AlertManagement.displayErrorAlert(
                "while trying to acces an item from a database table!",
                e.toString());
            
            // debug:
            System.out.println("Could not acces an item from a database table! ");
            e.printStackTrace(); 
        }
        
        catch (Exception e) {
            AlertManagement.displayErrorAlert(
                "while trying to acces an item from a database table!",
                e.toString());
            
            // debug:
            System.out.println("Could not access an item from a database table!");
            e.printStackTrace(); 
        }
        return c;
    }
}
