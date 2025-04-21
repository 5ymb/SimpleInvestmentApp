import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import javafx.scene.paint.Color;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.FileChooser;
import javafx.stage.Stage;


public class HelloApplication extends Application {

    private int year = 0;
    private double balance = 0.0;

    @Override
    public void start(Stage primaryStage) {



        //labels and textfields
        Label initialBalanceLabel = new Label("Initial Balance");
        TextField initialBalanceTF = new TextField();

        Label interestRateLabel = new Label("Interest Rate");
        TextField interestRateTF = new TextField();

        Label errorLetters = new Label();

        HBox hbox1 = new HBox(25);
        hbox1.getChildren().addAll(initialBalanceLabel, initialBalanceTF, interestRateLabel, interestRateTF, errorLetters);
        hbox1.setAlignment(Pos.CENTER);





        //textarea
        TextArea textArea = new TextArea();
        textArea.setPrefSize(2000, 250);
        textArea.setEditable(false);






        //buttons

        
        //button1
        Button button1 = new Button("Calculate");

        button1.setPrefHeight(50);
        button1.setPrefWidth(2000);




        //button2
        Button exportBtn = new Button("Export");
        exportBtn.setPrefHeight(50);
        exportBtn.setPrefWidth(2000);






        //labels that are in red
        Label totalYears = new Label("0");
        totalYears.setFont(Font.font("Arial", FontWeight.BOLD, 60));
        totalYears.setTextFill(Color.RED);

        Label years = new Label("Years");
        years.setTextFill(Color.RED);
        VBox yearsVBox = new VBox(40);
        yearsVBox.getChildren().addAll(totalYears, years);
        yearsVBox.setAlignment(Pos.CENTER);

        Label currentYears = new Label("0");
        currentYears.setFont(Font.font("Arial", FontWeight.BOLD, 60));
        currentYears.setTextFill(Color.RED);
        Label currentYearsEarning = new Label("Current years earning");
        currentYearsEarning.setTextFill(Color.RED);
        VBox currentYearsVBox = new VBox(40);
        currentYearsVBox.getChildren().addAll(currentYears, currentYearsEarning);
        currentYearsVBox.setAlignment(Pos.CENTER);

        Label numBalance = new Label("0");
        numBalance.setTextFill(Color.RED);
        numBalance.setFont(Font.font("Arial", FontWeight.BOLD, 60));
        Label totalBalance = new Label("Total Balance");
        totalBalance.setTextFill(Color.RED);
        VBox totalBalanceVBox = new VBox(40);
        totalBalanceVBox.getChildren().addAll(numBalance, totalBalance);
        totalBalanceVBox.setAlignment(Pos.CENTER);

        HBox hbox2 = new HBox(30);
        hbox2.setAlignment(Pos.CENTER);
        hbox2.getChildren().addAll(yearsVBox, currentYearsVBox, totalBalanceVBox);






        //vbox that contains labels, textfield and buttons
        VBox vbox1 = new VBox(25);
        vbox1.getChildren().addAll(hbox1, button1, hbox2, textArea, exportBtn);





        //activate buttons
        button1.setOnAction(e -> {

            try{
                if (balance == 0.0) {
                    balance = Double.parseDouble(initialBalanceTF.getText());
                }
                double rate = Double.parseDouble(interestRateTF.getText()) / 100.0;

                double currentYearEarn = balance * rate;
                currentYearEarn = Math.round(currentYearEarn * 100.0) / 100.0;

                balance = balance + currentYearEarn;
                balance = Math.round(balance * 100.0) / 100.0;

                year++;

                totalYears.setText(Integer.toString(year));
                currentYears.setText("SR " + currentYearEarn);
                numBalance.setText("SR " + balance);

                textArea.appendText(  "Year #" + year+ ": You Earned SR " + currentYearEarn + ", Total Balance SR " + balance + "\n"
                );
            }catch(Exception ex){

                errorLetters.setText("You Only Can Put Numbers...");
                errorLetters.setTextFill(Color.RED);

            }


        });

        exportBtn.setOnAction(e -> {
            FileChooser chooser1 = new FileChooser();
            chooser1.getExtensionFilters().add(
                    new FileChooser.ExtensionFilter("Text Files (*.txt)", "*.txt")
            );
            File file = chooser1.showSaveDialog(primaryStage);
            if (file == null) {
                return;
            }

            try (PrintWriter writer1 = new PrintWriter(file);){

                writer1.print(textArea.getText());


            } catch (IOException ex) {
                initialBalanceTF.appendText("Export Failed");




            }

        });

        //lines that runs the program
        StackPane root = new StackPane();
        root.getChildren().addAll(vbox1);
        Scene scene = new Scene(root, 1000, 625);
        primaryStage.setTitle("Investment app");
        primaryStage.setScene(scene);
        primaryStage.show();

    }


    public static void main(String[] args) {
        launch(args);
    }

}
