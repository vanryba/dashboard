package com.example.twodemofx;

import java.io.IOException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.example.twodemofx.Controllers.AddContentInVBoxController;
import com.example.twodemofx.Controllers.AddEntryController;
import com.example.twodemofx.Model.EntryItemDto;
import com.example.twodemofx.Service.DatabaseManagerService;
import com.example.twodemofx.Service.RebootService;
import com.example.twodemofx.Model.InterfaceItemDto;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class MainController {

    private Stage stage;

    private List<EntryItemDto> items;

    @Getter
    private DatabaseManagerService db = new DatabaseManagerService();

    private static final Logger logger = Logger.getLogger(AddContentInVBoxController.class.getName());

    public MainController() {
    }

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    private AnchorPane actionDashboard;

    @FXML
    private Label calendar;

    @FXML
    private ImageView closeApp;

    @FXML
    private ImageView downApp;

    @FXML
    private ImageView downloadDocument;

    @FXML
    private Label numberLinks;

    @FXML
    private ImageView plusLink;

    @FXML
    private ImageView rebootApp;

    @FXML
    private Label temperature;

    @FXML
    private Label timeDashboard;

    @FXML
    private VBox vBox;

    @FXML
    void initialize() {
        setItem(new RebootService().getItems());
        rebootApp.setOnMouseClicked(reboot -> setItem(new RebootService().getItems()));
    }

    @FXML
    void closeApplication() {
        stage.close();
    }

    @FXML
    void collapseApp() {
        stage.setIconified(true);
    }

    public void rebootItemsList() {
        List<EntryItemDto> values = db.getEntryItems();
        setVbox(values);
        numberLinks.setText(String.valueOf(values.size()));
    }

    @FXML
    void addEntryWindow() throws IOException {
        Stage addEntryStage = new Stage();
        FXMLLoader loader = new FXMLLoader(getClass().getResource("add-entry.fxml"));
        Scene scene = new Scene(loader.load());
        AddEntryController addEntryController = loader.getController();
        addEntryController.setController(this);
        addEntryStage.setScene(scene);
        addEntryStage.setResizable(false);
        addEntryStage.show();
    }

    private void setItem(InterfaceItemDto dto) {
        items = dto.nameEntry;
        timeDashboard.setText(dto.getTime());
        calendar.setText(dto.getDate());
        setVbox(items);
        numberLinks.setText(String.valueOf(dto.getNameEntry().size()));
        temperature.setText(dto.getTemperature());
    }

    private void setVbox(List<EntryItemDto> values) {
        vBox.getChildren().clear();
        for (EntryItemDto value : values) {
            try {
                FXMLLoader loader = new FXMLLoader(MainApplication.class.getResource("content-for-vBox.fxml"));
                Node valueBlock = loader.load();

                AddContentInVBoxController controller = loader.getController();
                controller.setValue(value, MainController.this);

                vBox.getChildren().add(valueBlock);
            } catch (IOException e) {
                logger.log(Level.SEVERE, "Ошибка при загрузке xml", e);
            }
        }
    }
}