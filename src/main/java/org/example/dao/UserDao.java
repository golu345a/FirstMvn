package org.example.dao;

import org.example.exception.DatabaseException;
import org.example.exception.DuplicateRecordException;
import org.example.model.User;
import org.example.util.DBUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class UserDao {

    private static final  String INSERT_USER =
            "INSERT INTO user(name,email,mobile) VALUES (?,?,?)";

    private static final String UPDATE_USER =
            "UPDATE user SET name = ?, email = ?, mobile = ? WHERE id = ?";

    private static final String DELETE_USER =
            "DELETE FROM user WHERE id = ?";

    private static final String FIND_BY_ID =
            "SELECT id, name, email, mobile FROM user WHERE id = ?";

    private static final String FIND_ALL =
            "SELECT id, name, email, mobile FROM user";

    public long save(User user){

        try(Connection con = DBUtil.getConnection();
            PreparedStatement ps = con.prepareStatement(INSERT_USER, Statement.RETURN_GENERATED_KEYS)){
            ps.setString(1,user.getName());
            ps.setString(2,user.getEmail());
            ps.setString(3,user.getMobile());
            ps.executeUpdate();

            try (ResultSet rs = ps.getGeneratedKeys()) {

                if (rs.next()) {
                    return rs.getLong(1);
                }

                throw new DatabaseException("Unable to retrieve generated id.");
            }

        } catch (SQLException e) {
            // MySQL Duplicate Key
            if (e.getErrorCode() == 1062) {
                throw new DuplicateRecordException(
                        "User with email '%s' already exists.".formatted(user.getEmail())
                );
            }
            throw new DatabaseException("Failed to save user.", e);

        }

    }



    public int update(User user) {

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(UPDATE_USER)) {

            ps.setString(1, user.getName());
            ps.setString(2, user.getEmail());
            ps.setString(3,user.getMobile());
            ps.setLong(4, user.getId());

            return ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Failed to update user.", e);
        }
    }

    
    public int delete(long id) {

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(DELETE_USER)) {

            ps.setLong(1, id);

            return ps.executeUpdate();

        } catch (SQLException e) {
            throw new DatabaseException("Failed to delete user.", e);
        }
    }
    
    public Optional<User> findById(long id) {

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_BY_ID)) {

            ps.setLong(1, id);

            try (ResultSet rs = ps.executeQuery()) {

                if (rs.next()) {

                    User user = mapUser(rs);

                    return Optional.of(user);
                }

                return Optional.empty();
            }

        } catch (SQLException e) {
            throw new DatabaseException("Failed to fetch user.", e);
        }
    }

    
    public List<User> findAll() {

        List<User> employees = new ArrayList<>();

        try (Connection connection = DBUtil.getConnection();
             PreparedStatement ps = connection.prepareStatement(FIND_ALL);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                employees.add(mapUser(rs));
            }

            return employees;

        } catch (SQLException e) {
            throw new DatabaseException("Failed to fetch employees.", e);
        }
    }

    /**
     * Maps ResultSet row to User object.
     */
    private User mapUser(ResultSet rs) throws SQLException {

        User user = new User();

        user.setId(rs.getLong("id"));
        user.setName(rs.getString("name"));
        user.setEmail(rs.getString("email"));
        user.setMobile(rs.getString("mobile"));

        return user;
    }


}
