package sample.view;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import sample.DateUtil;
import sample.Person;

public class PersonEditDialogController {

    @FXML
    private TextField fNameField;
    @FXML
    private TextField lNameField;
    @FXML
    private TextField sField;
    @FXML
    private TextField pField;
    @FXML
    private TextField cField;
    @FXML
    private TextField bField;


    private Stage dialogStage;
    private Person person;
    private boolean okClicked = false;

    /**
     * Initializes the controller class. This method is automatically called
     * after the fxml file has been loaded.
     */
    @FXML
    private void initialize() {
    }

    /**
     * Sets the stage of this dialog.
     *
     * @param dialogStage
     */
    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    /**
     * Sets the person to be edited in the dialog.
     *
     * @param person
     */
    public void setPerson(Person person) {
        this.person = person;

        fNameField.setText(person.getFirstName());
        lNameField.setText(person.getLastName());
        sField.setText(person.getStreet());
        pField.setText(Integer.toString(person.getPostalCode()));
        cField.setText(person.getCity());
        bField.setText(DateUtil.format(person.getBirthday()));
        bField.setPromptText("dd.mm.yyyy");
    }

    /**
     * Returns true if the user clicked OK, false otherwise.`
     *
     * @return
     */
    public boolean isOkClicked() {
        return okClicked;
    }

    /**
     * Called when the user clicks ok.
     */
    @FXML
    private void handleOk() {
        if (isInputValid()) {
            person.setFirstName(fNameField.getText());
            person.setLastName(lNameField.getText());
            person.setStreet(sField.getText());
            person.setPostalCode(Integer.parseInt(pField.getText()));
            person.setCity(cField.getText());
            person.setBirthday(DateUtil.parse(bField.getText()));

            okClicked = true;
            dialogStage.close();
        }
    }

    /**
     * Called when the user clicks cancel.
     */
    @FXML
    private void handleCancel() {
        dialogStage.close();
    }

    /**
     * Validates the user input in the text fields.
     *
     * @return true if the input is valid
     */
    private boolean isInputValid() {
        String errorMessage = "";

        if (fNameField.getText() == null || fNameField.getText().length() == 0) {
            errorMessage += "No first name!\n";
        }
        if (lNameField.getText() == null || lNameField.getText().length() == 0) {
            errorMessage += "No last name!\n";
        }
        if (sField.getText() == null || sField.getText().length() == 0) {
            errorMessage += "No street!\n";
        }

        if (pField.getText() == null || pField.getText().length() == 0) {
            errorMessage += "No postal code!\n";
        } else {
            // try to parse the postal code into an int.
            try {
                Integer.parseInt(pField.getText());
            } catch (NumberFormatException e) {
                errorMessage += "No valid postal code (must be an integer)!\n";
            }
        }

        if (cField.getText() == null || cField.getText().length() == 0) {
            errorMessage += "No city!\n";
        }

        if (bField.getText() == null || bField.getText().length() == 0) {
            errorMessage += "No birthday!\n";
        } else {
            if (!DateUtil.validDate(bField.getText())) {
                errorMessage += "No valid birthday. Use the format dd.mm.yyyy!\n";
            }
        }

        if (errorMessage.length() == 0) {
            return true;
        } else {
            // Show the error message.
            Alert alert = new Alert(AlertType.ERROR);
            alert.initOwner(dialogStage);
            alert.setTitle("Invalid Fields");
            alert.setHeaderText("Please correct the invalid fields");
            alert.setContentText(errorMessage);

            alert.showAndWait();

            return false;
        }
    }
}
