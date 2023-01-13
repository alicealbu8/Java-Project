package gui;

import domain.weather;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import service.Service;

import java.util.Comparator;
import java.util.Vector;
import java.util.stream.Collectors;

public class Controller {
    private Service service;
    @FXML
    private TextArea textArea;
    @FXML
    private TextField t1;
    @FXML
    private TextField h;

    @FXML
    private TextField textField;

    @FXML
    private ListView<weather> listView = new ListView<>();

    @FXML
    private ComboBox<String> comboBox = new ComboBox<>();

    public Controller(Service service) {
        this.service = service;
    }

    public void initialize(){

        service.openConnection();
        service.createSchema();
        //service.AddinSchema();

        ObservableList<weather> listObs = FXCollections.observableArrayList();

        Comparator<weather> compareWeather = Comparator
                .comparing(weather::getStartHour);

        Vector<weather> routesSorted = service.getAll().stream()
                .sorted(compareWeather)
                .collect(Collectors.toCollection(Vector::new));

        for(weather w:routesSorted)
            listObs.add(w);

        listView.setItems(listObs);

        ObservableList<String> listCombo = FXCollections.observableArrayList();

        Vector<String> vector = new Vector<>();
        for(weather w:service.getAll()){
            if(w.getDescription()!=null){
            String[] parts = w.getDescription().split(", ");
            vector.add(parts[0]);}
        }
        for(String s:vector)
            if(!listCombo.contains(s))
                listCombo.add(s);
        comboBox.setItems(listCombo);

    }
    @FXML
    void seeAfterCombo(ActionEvent event) {

        ObservableList<weather> listObs = FXCollections.observableArrayList();
        Vector<weather> vec = new Vector<weather>();

        for(weather w:service.getAll())
            if(w.getDescription()!=null)
                vec.add(w);

        Comparator<weather> compareWeather = Comparator
                .comparing(weather::getStartHour);

        Vector<weather> weatherSorted = vec.stream()
                .sorted(compareWeather)
                .collect(Collectors.toCollection(Vector::new));

        Vector<weather> weatherfiltered = weatherSorted.stream()
                .filter(w1->w1.getDescription().contains(comboBox.getSelectionModel().getSelectedItem()))
                .collect(Collectors.toCollection(Vector::new));

        for(weather w:weatherfiltered)
            listObs.add(w);

        listView.setItems(listObs);

    }

    @FXML
    void ListClicked(MouseEvent event) {

        listView.getSelectionModel().getSelectedItems().toString();
        String[] parts = listView.getSelectionModel().getSelectedItems().toString().split(", ");
        textArea.setText(parts[3]+" "+parts[4]);
        textField.setText(parts[3]+" "+parts[4]);
    }

    @FXML
    void listViewClicked(ActionEvent event) {
        String[] parts = new String[0];
        String text = textField.getText();
        if(text.contains("prec"))
            parts = text.split(" ");
        int prec = listView.getSelectionModel().getSelectedItem().getProbabilityPrec();
        service.updateSchema(String.valueOf(prec), parts[1]);

    }
    @FXML
    void Txt(ActionEvent event) {

        String text = t1.getText();
        int TotalHour = 0;
        for(weather w:service.getAll())
            if(w.getDescription()!=null)
                if(w.getDescription().contains(text))
                    TotalHour=TotalHour+(w.getEndHour()-w.getStartHour());
        System.out.println(TotalHour);
        h.setText(String.valueOf(TotalHour));
    }
}
