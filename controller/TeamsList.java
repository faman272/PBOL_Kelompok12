package app.controller;

import app.Manager;
import app.model.Employee;
import app.model.Team;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.TilePane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

import static app.controller.Dashboard.*;

public class TeamsList {

    @FXML private AnchorPane optionTeamsPane;
    @FXML private TilePane teamsTilePane;
    @FXML private Button backBtn;

    private ObservableList<Team> teams = FXCollections.observableArrayList();

    public void start(final Employee user) {
        Stage stage = (Stage) optionTeamsPane.getScene().getWindow();
        stage.sizeToScene();
        stage.setTitle("Teams");
        stage.setResizable(false);

        Scene scene = optionTeamsPane.getScene();
        scene.getStylesheets().add("/styles/style.css");

        loadTeams();

        // detect mouse on employee tile on Teams tab of dashboard
        for (Node node: teamsTilePane.lookupAll(".teams-indv-tile"))
        {
            node.setOnMouseEntered(mouseEvent -> {
                node.setStyle("-fx-border-width: 1px; -fx-border-color: #42a1ec");
            });

            node.setOnMouseExited(mouseEvent -> {
                node.setStyle("-fx-border-width: 1px; -fx-border-color: #e0e0e0");
            });

            // get employee info
            node.setOnMouseClicked(mouseEvent -> {
                AnchorPane anchorPane = (AnchorPane) mouseEvent.getSource();
                Label employeeNameLabel = (Label) anchorPane.lookup("#employeeName");
                Label employeeTitleLabel = (Label) anchorPane.lookup("#employeeTitle");
                Employee employee = findEmployeeByTitle(employeeNameLabel.getText(), employeeTitleLabel.getText());
                Manager.viewEmployeeInfo(employee, user);
            });
        }

        // detect mouse on Edit label on Teams tab on dashboard
        for (Node node: teamsTilePane.lookupAll(".teams-parent-tile"))
        {
            Label editLabel = (Label) node.lookup(".teamEditLabel");
            Label teamName = (Label) node.lookup(".teamNameLabel");
            Team team = findTeam(teamName.getText());

            if (!user.isAdmin()) {
                editLabel.setDisable(true);
            }

            editLabel.setOnMouseEntered(mouseEvent -> {
                editLabel.setStyle("-fx-underline: true");
            });

            editLabel.setOnMouseExited(mouseEvent -> {
                editLabel.setStyle("-fx-underline: false");
            });

            editLabel.setOnMouseClicked(mouseEvent -> {
                Manager.viewTeamEdit(team, user);
            });
        }

        backBtn.setOnAction(actionEvent -> Manager.viewDashboard(user));
    }

    /**
     * Load teams from DB.
     */
    private void loadTeams() {

        teamsTilePane.getChildren().clear(); // clear the view
        teams.clear();

        try {
            Connection connection = Manager.getConnection();
            ResultSet rs = connection.createStatement().executeQuery("select * from teams");

            // store all data in observable list
            while (rs.next())
            {
                teams.add(new Team(rs.getString("t_name"), rs.getInt("manager"), rs.getInt("analyst"),
                        rs.getInt("designer"), rs.getInt("programmer"), rs.getInt("cybersecurity")));
            }

            // now add those teams to view
            for (Team t: teams)
            {
                AnchorPane anchorPane = FXMLLoader.load(getClass().getResource("/view/team_parent_tile.fxml"));
                anchorPane.getStyleClass().add("teams-parent-tile");

                Label teamName = (Label) anchorPane.getChildren().get(0);
                teamName.setText(t.getName());
                teamName.getStyleClass().add("teamNameLabel");

                Label editLabel = (Label) anchorPane.getChildren().get(1);
                editLabel.getStyleClass().add("teamEditLabel");

                TilePane teamsParentTile = (TilePane) anchorPane.getChildren().get(2);

                // create tile for titles; manager, analyst...
                if (t.getManager() != 0) { // check if there is no person with this title
                    AnchorPane managerTile = FXMLLoader.load(getClass().getResource("/view/team_tile.fxml"));
                    managerTile.getStyleClass().add("teams-indv-tile");
                    Label name = (Label) managerTile.getChildren().get(0);
                    name.setText(getEmployeeName(t.getManager()));
                    Label title = (Label) managerTile.getChildren().get(1);
                    title.setText("Manager");
                    teamsParentTile.getChildren().add(managerTile);
                }

                if (t.getAnalyst() != 0) {
                    AnchorPane analystTile = FXMLLoader.load(getClass().getResource("/view/team_tile.fxml"));
                    analystTile.getStyleClass().add("teams-indv-tile");
                    Label name = (Label) analystTile.getChildren().get(0);
                    name.setText(getEmployeeName(t.getAnalyst()));
                    Label title = (Label) analystTile.getChildren().get(1);
                    title.setText("Analyst");
                    teamsParentTile.getChildren().add(analystTile);
                }

                if (t.getDesigner() != 0) {
                    AnchorPane designerTile = FXMLLoader.load(getClass().getResource("/view/team_tile.fxml"));
                    designerTile.getStyleClass().add("teams-indv-tile");
                    Label name = (Label) designerTile.getChildren().get(0);
                    name.setText(getEmployeeName(t.getDesigner()));
                    Label title = (Label) designerTile.getChildren().get(1);
                    title.setText("Designer");
                    teamsParentTile.getChildren().add(designerTile);
                }

                if (t.getProgrammer() != 0) {
                    AnchorPane programmerTile = FXMLLoader.load(getClass().getResource("/view/team_tile.fxml"));
                    programmerTile.getStyleClass().add("teams-indv-tile");
                    Label name = (Label) programmerTile.getChildren().get(0);
                    name.setText(getEmployeeName(t.getProgrammer()));
                    Label title = (Label) programmerTile.getChildren().get(1);
                    title.setText("Programmer");
                    teamsParentTile.getChildren().add(programmerTile);
                }

                if (t.getCyber_security() != 0) {
                    AnchorPane cyberTile = FXMLLoader.load(getClass().getResource("/view/team_tile.fxml"));
                    cyberTile.getStyleClass().add("teams-indv-tile");
                    Label name = (Label) cyberTile.getChildren().get(0);
                    name.setText(getEmployeeName(t.getCyber_security()));
                    Label title = (Label) cyberTile.getChildren().get(1);
                    title.setText("Cyber Security");
                    teamsParentTile.getChildren().add(cyberTile);
                }

                // add parent pane to the dashboard
                teamsTilePane.getChildren().add(anchorPane);
            }

        } catch (SQLException | IOException e) {
            e.printStackTrace();
            Manager.showAlert(Alert.AlertType.ERROR, "", "Couldn't load teams.");
        }
    }

}
