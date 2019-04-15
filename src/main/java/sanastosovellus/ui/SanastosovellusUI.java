package sanastosovellus.ui;

import com.sun.javafx.charts.ChartLayoutAnimator;
import javafx.geometry.Pos;
import javafx.scene.control.*;
import jdk.nashorn.internal.runtime.WithObject;
import sanastosovellus.dao.FileUserDao;
import sanastosovellus.dao.FileWordPairDao;
import sanastosovellus.domain.AppService;

import sanastosovellus.domain.WordPair;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

public class SanastosovellusUI extends Application {

    private AppService appService;

    private Scene appScene;
    private Scene newUserScene;
    private Scene loginScene;
    private Scene practiceScene;

    private VBox wordPairs;
    private Label menuLabel = new Label();
    private Label loginMessage = new Label();
    private Label practiceMessage = new Label();

    private List<WordPair> usersWordpairs;

    @Override
    public void init() throws Exception {
        Properties properties = new Properties();

        properties.load(new FileInputStream("config.properties"));

        String userFile = properties.getProperty("userFile");
        String wordPairFile = properties.getProperty("wordPairFile");

        FileUserDao userDao = new FileUserDao(userFile);
        FileWordPairDao wordPairDao = new FileWordPairDao(wordPairFile, userDao);
        appService = new AppService(wordPairDao, userDao);
    }

    public Node createWordPairNode(WordPair pair) {
        HBox box = new HBox(20);

        Label wordLabel = new Label(pair.getWord());
        Label translationLabel = new Label(pair.getTranslation());
        CheckBox cb = new CheckBox();
        cb.setIndeterminate(false);

        HBox wordLabelBox = new HBox(wordLabel);
        HBox translationLabelBox = new HBox(translationLabel);

        wordLabelBox.setAlignment(Pos.BASELINE_LEFT);
        translationLabelBox.setAlignment(Pos.BASELINE_CENTER);

        wordLabel.setMinHeight(20);
        translationLabel.setMinHeight(20);

        Region spacer = new Region();
        HBox.setHgrow(wordLabelBox, Priority.ALWAYS);
        HBox.setHgrow(translationLabelBox, Priority.ALWAYS);
        box.setPadding(new Insets(0,5,0,5));


        box.getChildren().addAll(wordLabelBox, translationLabelBox, cb, spacer);
        return box;
    }

    public void redrawWordPairList() {
        wordPairs.getChildren().clear();

        List<WordPair> pairs = appService.getPairs();
        pairs.forEach(pair->{
            Node newNode = createWordPairNode(pair);
            wordPairs.getChildren().addAll(newNode);
        });
    }

    @Override
    public void start(Stage primaryStage) {

        // set login scene
        setLoginScene(primaryStage);

        // new createNewUserScene
        setNewUserScene(primaryStage);

        // main scene
        setMainScene(primaryStage);

        // setup primary stage
        primaryStage.setTitle("Sanastosovellus");
        primaryStage.setScene(loginScene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e->{
            System.out.println("Suljetaan");
            stop();
        });
    }

    public void setLoginScene(Stage primaryStage) {
        VBox loginPane = new VBox(10);
        HBox usernameInputPane = new HBox(10);
        HBox pwdInputPane = new HBox(10);
        loginPane.setPadding(new Insets(10));

        Label usernameLabel = new Label("Käyttäjänimi");
        usernameLabel.setPrefWidth(100);
        Label pwdLabel = new Label("Salasana");
        pwdLabel.setPrefWidth(100);

        TextField usernameInput = new TextField();
        PasswordField pwdInput = new PasswordField();

        usernameInputPane.getChildren().addAll(usernameLabel, usernameInput);
        pwdInputPane.getChildren().addAll(pwdLabel, pwdInput);

        Button loginButton = new Button("Kirjaudu sisään");
        Button createButton = new Button("Luo uusi käyttäjä");
        loginButton.setOnAction(e->{
            String username = usernameInput.getText();
            String pwd = pwdInput.getText();
            menuLabel.setText("Kirjautuneena sisään " + username);
            if (appService.login(username, pwd)) {
                loginMessage.setText("");
                usersWordpairs = appService.getPairs();
                redrawWordPairList();
                primaryStage.setScene(appScene);
                usernameInput.setText("");
                pwdInput.setText("");
            } else {
                loginMessage.setText("Kyseistä käyttäjää ei löydy.");
                loginMessage.setTextFill(Color.RED);
            }
        });

        createButton.setOnAction(e->{
            usernameInput.setText("");
            primaryStage.setScene(newUserScene);
        });

        loginPane.getChildren().addAll(loginMessage, usernameInputPane, pwdInputPane, loginButton, createButton);

        loginScene = new Scene(loginPane, 350, 250);
    }

    public void setNewUserScene(Stage primaryStage) {
        VBox newUserPane = new VBox(10);

        HBox newUsernamePane = new HBox(10);
        newUsernamePane.setPadding(new Insets(10));
        TextField newUsernameInput = new TextField();
        Label newUsernameLabel = new Label("Käyttäjänimi");
        newUsernameLabel.setPrefWidth(100);
        newUsernamePane.getChildren().addAll(newUsernameLabel, newUsernameInput);

        HBox newPwdPane = new HBox(10);
        newPwdPane.setPadding(new Insets(10));
        PasswordField newPwdInput = new PasswordField();
        Label newPwdLabel = new Label("Salasana");
        newPwdLabel.setPrefWidth(100);
        newPwdPane.getChildren().addAll(newPwdLabel, newPwdInput);

        Label userCreationMsg = new Label();

        Button createNewUserButton = new Button("Luo uusi käyttäjä");
        createNewUserButton.setPadding(new Insets(10));

        createNewUserButton.setOnAction(e->{
            String username = newUsernameInput.getText();
            String pwd = newPwdInput.getText();

            if (username.length() < 5 || pwd.length() < 5) {
                userCreationMsg.setText("Käyttäjänimi tai salasana liian lyhyt.");
                userCreationMsg.setTextFill(Color.RED);
            } else if (appService.createUser(username, pwd)) {
                userCreationMsg.setText("");
                loginMessage.setText("Uusi käyttäjä luotu.");
                loginMessage.setTextFill(Color.GREEN);
                primaryStage.setScene(loginScene);
            } else {
                userCreationMsg.setText("Käyttäjänimen tulee olla uniikki.");
                userCreationMsg.setTextFill(Color.RED);
            }
        });

        newUserPane.getChildren().addAll(userCreationMsg, newUsernamePane, newPwdPane, createNewUserButton);

        newUserScene = new Scene(newUserPane, 350, 250);
    }

    public void setMainScene(Stage primaryStage) {
        ScrollPane wordPairScrollbar = new ScrollPane();
        BorderPane mainPane = new BorderPane(wordPairScrollbar);
        appScene = new Scene(mainPane, 500, 450);

        HBox menuPane = new HBox(10);
        Region menuSpacer = new Region();
        HBox.setHgrow(menuSpacer, Priority.ALWAYS);
        Button practiceButton = new Button("Harjoittele");
        practiceButton.setOnAction(e->{
            // shuffle the words so they are not always in the same order
            Collections.shuffle(usersWordpairs);
            setPracticeScene(primaryStage, 0);
            primaryStage.setScene(practiceScene);
        });
        Button logoutButton = new Button("Kirjaudu ulos");
        menuPane.getChildren().addAll(menuLabel, menuSpacer,practiceButton, logoutButton);
        logoutButton.setOnAction(e->{
            usersWordpairs = null;
            appService.logout();
            primaryStage.setScene(loginScene);
        });

        HBox createForm = new HBox(10);
        Button addWordPair = new Button("Lisää sanapari");
        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        TextField newWordInput = new TextField();
        TextField newTranslationInput = new TextField();
        createForm.getChildren().addAll(newWordInput, newTranslationInput, spacer, addWordPair);

        wordPairs = new VBox(10);
        wordPairs.setMaxWidth(280);
        wordPairs.setMinWidth(280);
        redrawWordPairList();

        Button deleteWordPairsButton = new Button("Poista valitut sanaparit");

        deleteWordPairsButton.setOnAction(e->{
            /**
             * collects checkboxes to list
             */
            List<CheckBox> boxes = new ArrayList<>();
            for (Object node : wordPairs.getChildren()) {
                if (node instanceof HBox) {
                    for (Node n : ((HBox) node).getChildren()) {
                        if (n instanceof CheckBox) {
                            boxes.add((CheckBox) n);
                        }
                    }
                }
            }
            /**
             * deletes word pairs that are checked
             */
            for (int i = 0; i < boxes.size(); i++) {
                CheckBox box = boxes.get(i);
                if (box.isSelected()) {
                    appService.deleteWordPair(usersWordpairs.get(i));
                    usersWordpairs = appService.getPairs();
                }
            }
            redrawWordPairList();
        });

        wordPairScrollbar.setPadding(new Insets(15));
        wordPairScrollbar.setContent(wordPairs);
        mainPane.setBottom(createForm);
        mainPane.setTop(menuPane);
        mainPane.setRight(deleteWordPairsButton);

        addWordPair.setOnAction(e->{
            appService.addWordPair(newWordInput.getText(), newTranslationInput.getText());
            newWordInput.setText("");
            newTranslationInput.setText("");
            redrawWordPairList();
            usersWordpairs = appService.getPairs();
        });

    }

    public void setPracticeScene(Stage primaryStage, int index) {

        VBox practicePane = new VBox(10);
        practicePane.setPadding(new Insets(10));

        HBox wordPairPane = new HBox(16);
        Label wordLabel = new Label();
        TextField answerInput = new TextField();
        Button answerButton = new Button("Vastaa");

        Button nextButton = new Button("Ohita");
        Button backButton = new Button("Lopeta");

        wordLabel.setText(usersWordpairs.get(index).getWord());
        wordPairPane.getChildren().addAll(wordLabel, answerInput, answerButton);

        answerButton.setOnAction(e->{
            if (answerInput.getText().equals(usersWordpairs.get(index).getTranslation())) {
                practiceMessage.setText("Oikein! " + usersWordpairs.get(index).getWord() + " = " + usersWordpairs.get(index).getTranslation());
                practiceMessage.setTextFill(Color.GREEN);

                /**
                 * Next word if there are words left
                 */
                if (index < usersWordpairs.size()-1) {
                    setPracticeScene(primaryStage, index + 1);
                    primaryStage.setScene(practiceScene);
                }

            } else {
                practiceMessage.setText("Väärin, yritä uudelleen.");
                practiceMessage.setTextFill(Color.RED);
            }
        });

        nextButton.setOnAction(e->{
            setPracticeScene(primaryStage, index + 1);
            primaryStage.setScene(practiceScene);
        });

        backButton.setOnAction(e->{
            setMainScene(primaryStage);
            primaryStage.setScene(appScene);
        });

        if (index < usersWordpairs.size()-1) {
            practicePane.getChildren().addAll(practiceMessage, wordPairPane, nextButton, backButton);
        } else {
            practicePane.getChildren().addAll(practiceMessage, wordPairPane, backButton);
        }

        practiceScene = new Scene(practicePane, 400, 400);
    }

    @Override
    public void stop() {
        System.out.println("Sovellus sulkeutuu");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
