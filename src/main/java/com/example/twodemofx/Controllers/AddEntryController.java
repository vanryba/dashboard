package com.example.twodemofx.Controllers;

import com.example.twodemofx.MainController;
import com.example.twodemofx.Model.AddEntryDto;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class AddEntryController {

    private MainController controller;

    @FXML
    private AnchorPane anchor;

    @FXML
    private Button addButton;

    @FXML
    private TextField nameEntry;

    @FXML
    private TextField valueEntry;

    @FXML
    void addAndCloseButton() {
        Stage stage = (Stage) addButton.getScene().getWindow();
        AddEntryDto dto = getDto();
        if (dtoIsNotEmpty(dto)) {
            controller.getDb().addLink(dto);
        }
        controller.rebootItemsList();
        stage.close();
    }

    @FXML
    private void keyPressEvent() {
        anchor.setOnKeyPressed(event -> {
            if (event.getCode() == KeyCode.ENTER) {
                addAndCloseButton();
            }
        });
    }

    private boolean dtoIsNotEmpty(AddEntryDto dto) {
        return dto.getName() != null && !dto.getName().isEmpty() && !dto.getName().isBlank();
    }

    public AddEntryDto getDto() {
        return AddEntryDto.builder()
                .name(nameEntry.getText())
                .valueEntry(valueEntry.getText())
                .build();
    }

    public void setController(MainController controller) {
        this.controller = controller;
    }

}
