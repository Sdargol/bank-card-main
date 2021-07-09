package org.sdargol.db.dao;

import org.sdargol.db.dao.api.IDAORoles;
import org.sdargol.db.dao.core.IDAO;
import org.sdargol.db.h2.ConnectionPool;
import org.sdargol.dto.DTORoles;
import org.sdargol.dto.response.DTOMessage;
import org.sdargol.http.security.Roles;
import org.sdargol.http.security.RolesMapper;
import org.sdargol.utils.Log;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DAORoles implements IDAORoles, IDAO {
    private final static Logger LOGGER = Log.getLogger(DAORoles.class.getName());

    @Override
    public DTORoles getByUserId(int id) {
        DTORoles rolesUser = new DTORoles();
        Set<Roles> roles = new HashSet<>();
        rolesUser.setRoles(roles);

        try (Connection connection = ConnectionPool.getConnection()) {
            String sql = "SELECT * FROM roles WHERE user_id = (?) ";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1,id);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                rolesUser.setId(rs.getInt("id"));
                rolesUser.setUserId(rs.getInt("user_id"));
                roles.add(
                        RolesMapper.stringToRole(
                                rs.getString("role")
                        )
                );
            }

            rs.close();
            ps.close();
        } catch (SQLException e) {
            LOGGER.log(Level.WARNING, "Error load roles user", e);
        }
        return rolesUser;
    }

    @Override
    public DTOMessage add(DTORoles roles) {
        DTOMessage msg;
        try(Connection connection = ConnectionPool.getConnection()) {
            String sql = "INSERT INTO roles (user_id, role) VALUES (?,?)";
            PreparedStatement ps = connection.prepareStatement(sql);
            for(Roles r: roles.getRoles()){
                ps.setInt(1, roles.getUserId());
                ps.setString(2, r.toString());
                ps.addBatch();
            }

            int[] res = ps.executeBatch();

            msg = new DTOMessage("Roles for user id " +
                    roles.getUserId() +
                    " successfully created, rows: " +
                    res.length);

            ps.close();
        } catch (SQLException e) {
            msg = new DTOMessage(e.getMessage());
            LOGGER.log(Level.WARNING, "Error create roles for user id " + roles.getUserId() , e);
        }
        return msg;
    }
}
