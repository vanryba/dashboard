package com.example.twodemofx.Controllers;

import com.example.twodemofx.HelloController;
import com.example.twodemofx.Model.EntryItemDto;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

import java.io.IOException;

public class AddContentInVBoxController {

    private static final String URL_REGEX
            = "^(https?|ftp)://[\\w-]+(\\.[\\w-]+)+([\\w.,@?^=%&:/~+#-]*[\\w@?^=%&/~+#-])?$";

    private static final Logger logger = Logger.getLogger(AddContentInVBoxController.class.getName());
    public AnchorPane anchorPaneOnVbox;

    private HelloController controller;

    private EntryItemDto dto;

    @FXML
    private ImageView copyEntry;

    @FXML
    private ImageView goToWeb;

    @FXML
    private Label nameEntry;

    @FXML
    private ImageView deleteEntry;

    @FXML
    void clickOnDelete() {
        deleteEntry.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                controller.getDb().deleteLink(dto.id);
                controller.rebootItemsList();
            }
        });
    }

    @FXML
    void clickOnCopyEntry() {
        copyEntry.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                copyInClipboard();
            }
        });
    }

    private void copyInClipboard() {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent clipboardContent = new ClipboardContent();
        clipboardContent.putString(dto.valueEntry);
        clipboard.setContent(clipboardContent);
    }

    @FXML
    private void doubleClickEvent() {
        nameEntry.setOnMouseClicked(event -> {
            if (isDoubleClick(event)) {
                eventForButton(event.getButton());
            }
        });
    }

    @FXML
    void clickOnGoToWeb() {
        goToWeb.setOnMouseClicked(event -> {
            if (event.getButton() == MouseButton.PRIMARY) {
                switchingToBrowser();
            }
        });
    }

    private void switchingToBrowser() {
        if (isURL()) {
            try {
                getProcessBuilder(getOs()).start();
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Ошибка при открытии ссылки", e);
            }
        }
    }

    public void setValue(EntryItemDto dto, HelloController controller) {
        nameEntry.setText(dto.name);
        this.dto = dto;
        this.controller = controller;
    }

    private boolean isDoubleClick(MouseEvent event) {
        return event.getClickCount() == 2;
    }

    private void eventForButton(MouseButton button) {
        switch (button) {
            case PRIMARY -> copyInClipboard();
            case SECONDARY -> switchingToBrowser();
        }
    }

    private boolean isURL() {
        return dto.valueEntry.matches(URL_REGEX);
    }

    private String getOs() {
        String os = System.getProperty("os.name").toLowerCase();
        return Stream.of("mac", "nux", "nix", "win")
                .filter(os::contains)
                .findFirst()
                .orElse("noName");
    }

    private ProcessBuilder getProcessBuilder(String osType) {
        return switch (osType) {
            case "win" -> new ProcessBuilder("cmd", "/c", "start", dto.valueEntry);
            case "mac" -> new ProcessBuilder("open", dto.valueEntry);
            case "nix", "nux" -> new ProcessBuilder("xdg-open", dto.valueEntry);
            default -> throw new UnsupportedOperationException("Неизвестная операционная система");
        };
    }

}
