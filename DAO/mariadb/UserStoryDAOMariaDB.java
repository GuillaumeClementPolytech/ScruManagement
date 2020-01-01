package DAO.mariadb;

import DAO.UserStoryDAO;
import business.system.UserStory;

import java.sql.*;

public class UserStoryDAOMariaDB extends UserStoryDAO {

    public UserStoryDAOMariaDB(String addressDataBase, String userDataBase, String passWordDataBase) throws SQLException {
        super(addressDataBase, userDataBase, passWordDataBase);
    }

    @Override
    public UserStory getUserStoryById(int id) throws SQLException {
        String sql = "Select * from UserStory " +
                "Where idUserStory = ?";

        PreparedStatement pre = this.connection.prepareStatement(sql);

        pre.setInt(1, id);

        ResultSet resultSet = pre.executeQuery();

        boolean success = resultSet.first();

        if (!success) {
            return null;
        }

        String desc = resultSet.getString("descriptionUserStory");
        int score = resultSet.getInt("score");
        Date deadline = resultSet.getDate("deadline");
        String name = resultSet.getString("nameUserStory");

        return new UserStory(id, name, desc, score, deadline);
    }

    @Override
    public boolean deleteUserStory(int id) throws SQLException {

        // Delete ColumnUserStory
        String sql = "DELETE from ColumnUserStory where idComponent = ?";
        PreparedStatement pre = this.connection.prepareStatement(sql);

        pre.setInt(1, id);

        int nbAff = pre.executeUpdate();

        // Delete User Story
        sql = "DELETE from UserStory where idUserStory = ?";

        pre = this.connection.prepareStatement(sql);

        pre.setInt(1, id);

        int nbAffected = pre.executeUpdate();

        return (nbAffected > 0) && (nbAff > 0);
    }

    @Override
    public boolean updateUserStory(int id, UserStory newUS) throws SQLException {
        String sql = "UPDATE UserStory " +
                "SET score = ? " +
                "and deadline = ? " +
                "and descriptionUserStory = ? " +
                "and name = ? " +
                "where idUserStory = ?";

        PreparedStatement pre = this.connection.prepareStatement(sql);

        pre.setInt(1, newUS.getScore());
        pre.setDate(2, newUS.getDeadline());
        pre.setString(3, newUS.getDescription());
        pre.setString(4, newUS.getName());
        pre.setInt(5, id);

        int nbAffected = pre.executeUpdate();

        return nbAffected > 0;
    }

    /**
     * If sucess, set UserStory id to generated id
     *
     * @param newUS
     * @param projectID
     * @return
     * @throws SQLException
     */
    @Override
    public boolean addUserStory(UserStory newUS, int projectID) throws SQLException {

        // Insert User Story
        boolean success = insertUserStory(newUS);
        if (!success) {
            return false;
        }

        // Search Column to Insert into
        int idColumn = getUserStoryBacklogColumn(projectID);
        if (idColumn == -1) {
            return false;
        }

        // Insert Column User Story
        String sql = "INSERT INTO ColumnUserStory" +
                "Values (?, ?)";

        PreparedStatement pre = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        pre.setInt(1, idColumn);
        pre.setInt(2, newUS.getId());

        int nbAffected = pre.executeUpdate();

        ResultSet rs = pre.getGeneratedKeys();

        if(rs.next()){
            newUS.setId(rs.getInt(1));
        }

        return nbAffected > 0;
    }

    private int getUserStoryBacklogColumn(int projectID) throws SQLException {
        // TODO : put real type
        String sql = "Select c.idColumn from Backlog b, ColumnBacklog c " +
                "WHERE b.idProject = ? " +
                "and b.typeBacklog = 1 " +
                "and b.idBacklog = c.idBacklog";

        PreparedStatement pre = this.connection.prepareStatement(sql);

        pre.setInt(1, projectID);

        ResultSet rs = pre.executeQuery();

        int idColumn = -1;
        if (rs.next()) {
            idColumn = rs.getInt(1);
        }
        return idColumn;
    }

    private boolean insertUserStory(UserStory newUS) throws SQLException {
        String sql = "Insert into UserStory " +
                "values ( ?, ?, ?, ?)";

        PreparedStatement pre = this.connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

        pre.setInt(1, newUS.getScore());
        pre.setDate(2, newUS.getDeadline());
        pre.setString(3, newUS.getDescription());
        pre.setString(4, newUS.getName());

        int nbAffected = pre.executeUpdate();
        ResultSet rs = pre.getGeneratedKeys();

        if (nbAffected > 0) {
            return false;
        }

        int idUS = -1;
        if (rs.next()) {
            idUS = rs.getInt(1);
        } else {
            return false;
        }

        newUS.setId(idUS);
        return true;
    }
}