/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package persaccounting.AppStockAccounting.Model;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import persaccounting.AppStockAccounting.Entity.Commodity;
import persaccounting.AppStockAccounting.Utils.DbUtil;

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
        con = DbUtil.getConnection();
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
            preparedStatement.executeUpdate();
            ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
        if (generatedKeys.next()) {
            c.setId(generatedKeys.getInt(1));
        }
        preparedStatement.close();
        } catch (Exception e) {
            // TODO: implement; OPersian's note
        }
    }

    public void deleteCommodity(int commoditytId) {
        try {
            PreparedStatement preparedStatement = con.prepareStatement(SQL_DELETE);
            preparedStatement.setInt(1, commoditytId);
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            // TODO: implement; OPersian's note
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
            preparedStatement.executeUpdate();
            preparedStatement.close();
        } catch (Exception e) {
            // TODO: implement; OPersian's note
        }
    }

    public List<Commodity> getAllCommodities() {
        List<Commodity> commoditiestList = new ArrayList<Commodity>();
        try {
            Statement statement = con.createStatement();
            ResultSet rs = statement.executeQuery(SQL_SELECT_ALL);
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
        } catch (Exception e) {
            e.printStackTrace();
            // TODO: implement; OPersian's note
        }
        return commoditiestList;
    }

    public Commodity getCommodityById(int commodityId) {
        Commodity st = new Commodity();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(SQL_SELECT_BY_ID);
            preparedStatement.setInt(1, commodityId);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                st.setId(rs.getInt("id"));
                st.setCommodityName(rs.getString("commodityName"));
                st.setCommodityDescription(rs.getString("commodityDescription"));				
                st.setCommodityQuantityInStock(rs.getInt("commodityQuantityInStock"));
                st.setCommodityPriceWithoutTax(rs.getInt("commodityPriceWithoutTax"));
                preparedStatement.close();
            }
        } catch (Exception e) {
            // TODO: implement; OPersian's note
        }
        return st;
    }
}