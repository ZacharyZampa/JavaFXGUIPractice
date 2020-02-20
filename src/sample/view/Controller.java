package sample.view;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import sample.DateUtil;
import sample.Main;
import sample.Person;

public class Controller {

    // Reference to the main application.
    private Main mainApp;

    public Button button;
    public Button newButton;  // name of variable must be same name as item id in GUI / fxml

    @FXML
    private TableView<Person> personTable;
    @FXML
    private TableColumn<Person, String> fColumn;
    @FXML
    private TableColumn<Person, String> lColumn;

    @FXML
    private Label fNameLabel;
    @FXML
    private Label lNameLabel;
    @FXML
    private Label sLabel;
    @FXML
    private Label pLabel;
    @FXML
    private Label cLabel;
    @FXML
    private Label bLabel;

    /**
     * Fills all text fields to show details about the person.
     * If the specified person is null, all text fields are cleared.
     *
     * @param person the person or null
     */
    private void showPersonDetails(Person person) {
        if (person != null) {
            // Fill the labels with info from the person object.
            fNameLabel.setText(person.getFirstName());
            lNameLabel.setText(person.getLastName());
            sLabel.setText(person.getStreet());
            pLabel.setText(Integer.toString(person.getPostalCode()));
            cLabel.setText(person.getCity());

            bLabel.setText(DateUtil.format(person.getBirthday()));
        } else {
            // Person is null, remove all the text.
            fNameLabel.setText("");
            lNameLabel.setText("");
            sLabel.setText("");
            pLabel.setText("");
            cLabel.setText("");
            bLabel.setText("");
        }
    }


    /**
     * Called when the user clicks on the delete button.
     */
    @FXML
    private void handleDeletePerson() {
        int selectedIndex = personTable.getSelectionModel().getSelectedIndex();
        if (selectedIndex >= 0) {
            personTable.getItems().remove(selectedIndex);
        } else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }

    /**
     * Called when the user clicks the new button. Opens a dialog to edit
     * details for a new person.
     */
    @FXML
    private void handleNewPerson() {
        Person tmpPerson = new Person();
        boolean okClicked = mainApp.showPersonEditDialog(tmpPerson);
        if (okClicked) {
            mainApp.getPersonData().add(tmpPerson);
        }
    }

    /**
     * Called when the user clicks the edit button. Opens a dialog to edit
     * details for the selected person.
     */
    @FXML
    private void handleEditPerson() {
        Person selectedPerson = personTable.getSelectionModel().getSelectedItem();
        if (selectedPerson != null) {
            boolean okClicked = mainApp.showPersonEditDialog(selectedPerson);
            if (okClicked) {
                showPersonDetails(selectedPerson);
            }
        } else {
            // Nothing selected.
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.initOwner(mainApp.getPrimaryStage());
            alert.setTitle("No Selection");
            alert.setHeaderText("No Person Selected");
            alert.setContentText("Please select a person in the table.");

            alert.showAndWait();
        }
    }

    /**
     * The constructor.
     * The constructor is called before the initialize() method.
     */
    public Controller() {
    }

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    public void initialize() {
        // Initialize the person table with the two columns.
        fColumn.setCellValueFactory(cellData -> cellData.getValue().firstNameProperty());
        lColumn.setCellValueFactory(cellData -> cellData.getValue().lastNameProperty());

        // clear Person details
        showPersonDetails(null);

        // Listen for selection changes and show the person details when changed
        personTable.getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> showPersonDetails(newValue));
    }

    /**
     * Is called by the main application to give a reference back to itself.
     *
     * @param mainApp
     */
    public void setMainApp(Main mainApp) {
        this.mainApp = mainApp;

        // Add observable list data to the table
        personTable.setItems(mainApp.getPersonData());
    }

}
