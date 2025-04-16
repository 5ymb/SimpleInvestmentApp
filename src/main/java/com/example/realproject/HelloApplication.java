package com.example.realproject;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;

public class HelloApplication extends Application {
    private double balance=0.0;
    private int year=0;
    private static final DecimalFormat MONEY_FORMAT = new DecimalFormat("###,###.00");



    @Override
    public void start(Stage stage)  {
        //Create objects//
        Label intialBalanceLable = new Label("Initial Balance:");
        Label intrestRateLable = new Label("Intrest Rate:");

        TextField intialBalanceTextField = new TextField();
        TextField intrestRateTextField = new TextField();

        Button calculateButton = new Button("Calculate");
        Button exportButton = new Button("Export");

        Text yearsText = new Text("0");
        Text currentYearEarnText = new Text("0");
        Text totalBalanceText = new Text("0");

        Label yearsLabel = new Label("Years");
        Label currentYearEarnLabel = new Label("Current Year Earnings");
        Label totalBalanceLabel = new Label("Total Balance");

        TextArea textArea = new TextArea();
        textArea.setPrefWidth(2000);
        textArea.setPrefHeight(2000);
        textArea.setEditable(false);


        //Design//
        yearsText.setFill(Color.RED);
        currentYearEarnText.setFill(Color.RED);
        totalBalanceText.setFill(Color.RED);
        yearsLabel.setTextFill(Color.RED);
        currentYearEarnLabel.setTextFill(Color.RED);
        totalBalanceLabel.setTextFill(Color.RED);

        yearsText.setFont(Font.font("", FontWeight.BOLD,40));
        currentYearEarnText.setFont(Font.font("", FontWeight.BOLD,40));
        totalBalanceText.setFont(Font.font("", FontWeight.BOLD,40));
        yearsLabel.setFont(Font.font("Arial", FontWeight.BOLD,20));
        currentYearEarnLabel.setFont(Font.font("Arial", FontWeight.BOLD,20));
        totalBalanceLabel.setFont(Font.font("Arial", FontWeight.BOLD,20));


        calculateButton.setPrefWidth(2000);
        calculateButton.setPrefHeight(2000);
        exportButton.setPrefWidth(2000);
        exportButton.setPrefHeight(2000);

        textArea.setStyle("-fx-border-color: lightgray;  -fx-border-width: 8;");
        textArea.setPrefHeight(2000);
        //Text panes//
        HBox h1=new HBox(25);
        h1.setAlignment(Pos.CENTER);
        h1.getChildren().addAll(intialBalanceLable,intialBalanceTextField,intrestRateLable,intrestRateTextField);

        GridPane pane=new GridPane();
        pane.setAlignment(Pos.CENTER);
        pane.setVgap(50);
        pane.setHgap(70);
        pane.setPrefSize(2000,2000);

        pane.add(yearsText,0,0);
        pane.add(currentYearEarnText,1,0);
        pane.add(totalBalanceText,2,0);
        pane.add(yearsLabel,0,1);
        pane.add(currentYearEarnLabel,1,1);
        pane.add(totalBalanceLabel,2,1);

        GridPane.setHalignment(yearsText, HPos.CENTER);
        GridPane.setHalignment(currentYearEarnText, HPos.CENTER);
        GridPane.setHalignment(totalBalanceText, HPos.CENTER);
        GridPane.setHalignment(yearsLabel, HPos.CENTER);
        GridPane.setHalignment(currentYearEarnLabel, HPos.CENTER);
        GridPane.setHalignment(totalBalanceLabel, HPos.CENTER);

        VBox vbox = new VBox(25);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(h1,calculateButton,pane,textArea,exportButton);


        //activate buttons//
        calculateButton.setOnAction(e -> {
            if(balance ==0.0) {
                balance = Double.parseDouble(intialBalanceTextField.getText());
            }
            double interestRate= Double.parseDouble(intrestRateTextField.getText());

            double currentYearEarn = balance * (interestRate/100);
            balance += currentYearEarn;
            year++;

            yearsText.setText(Integer.toString(year));
            currentYearEarnText.setText("SR"+ MONEY_FORMAT.format(currentYearEarn));
            totalBalanceText.setText("SR"+ MONEY_FORMAT.format(balance));

            textArea.appendText("Year #:" + year + "You Earned: SR" + MONEY_FORMAT.format(currentYearEarn) + "and your total balance is SR" + MONEY_FORMAT.format(balance) + "\n");
        });

        exportButton.setOnAction(e -> {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

            File file = fileChooser.showSaveDialog(stage);

            PrintWriter output = null;
            if (file != null) {
                try {
                    output = new PrintWriter(file);
                    output.print(textArea.getText());
                } catch (IOException ex) {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setHeaderText("An error occurred");
                    alert.setContentText(ex.getMessage());
                    alert.showAndWait();
                }
                if (output != null) {
                    output.close();
                }
            }
        });

        Scene scene = new Scene(vbox, 800, 600);
        stage.setTitle("Investment App");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
