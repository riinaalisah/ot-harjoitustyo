package ui;

import dao.FileUserDao;
import dao.FileWordPairDao;
import domain.SanastosovellusService;

import domain.WordPair;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.util.List;
import java.util.Properties;

public class SanastosovellusUI extends Application {

    private SanastosovellusService appService;

    private Scene appScene;
    private Scene newUserScene;
    private Scene loginScene;

    private VBox wordPairs;
    private Label menuLabel = new Label();

    @Override
    public void init() throws Exception {
        Properties properties = new Properties();

        properties.load(new FileInputStream("config.properties"));

        String userFile = properties.getProperty("userFile");
        String wordPairFile = properties.getProperty("wordPairFile");

        FileUserDao userDao = new FileUserDao(userFile);
        FileWordPairDao wordPairDao = new FileWordPairDao(wordPairFile, userDao);
        appService = new SanastosovellusService(wordPairDao, userDao);
    }

    public Node createWordPairNode(WordPair pair) {
        HBox box = new HBox(10);
        Label wordLabel = new Label(pair.getWord());
        Label translationLabel = new Label(pair.getTranslation());
        wordLabel.setMinHeight(20);
        translationLabel.setMinHeight(20);

        Region spacer = new Region();
        HBox.setHgrow(spacer, Priority.ALWAYS);
        box.setPadding(new Insets(0,5,0,5));

        box.getChildren().addAll(wordLabel, translationLabel, spacer);
        return box;
    }

    public void redrawWordPairList() {
        wordPairs.getChildren().clear();

        List<WordPair> pairs = appService.getPairs();
        pairs.forEach(pair->{
            wordPairs.getChildren().add(createWordPairNode(pair));
        });
    }

    @Override
    public void start(Stage primaryStage) {
        // login scene

        VBox loginPane = new VBox(10);
        HBox usernameInputPane = new HBox(10);
        HBox pwdInputPane = new HBox(10);
        loginPane.setPadding(new Insets(10));

        Label loginLabel = new Label("Käyttäjänimi");
        Label pwdLabel = new Label("Salasana");

        TextField usernameInput = new TextField();
        PasswordField pwdInput = new PasswordField();

        usernameInputPane.getChildren().addAll(loginLabel, usernameInput);
        pwdInputPane.getChildren().addAll(pwdLabel, pwdInput);

        Label loginMessage = new Label();

        Button loginButton = new Button("Kirjaudu sisään");
        Button createButton = new Button("Luo uusi käyttäjä");
        loginButton.setOnAction(e->{
            String username = usernameInput.getText();
            String pwd = pwdInput.getText();
            menuLabel.setText("Kirjautuneena sisään " + username);
            if (appService.login(username, pwd)) {
                loginMessage.setText("");
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

        loginScene = new Scene(loginPane, 300, 250);

        // new createNewUserScene

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

        newUserScene = new Scene(newUserPane, 300, 450);

        // main scene

        ScrollPane wordPairScrollbar = new ScrollPane();
        BorderPane mainPane = new BorderPane(wordPairScrollbar);
        appScene = new Scene(mainPane, 500, 450);

        HBox menuPane = new HBox(10);
        Region menuSpacer = new Region();
        HBox.setHgrow(menuSpacer, Priority.ALWAYS);
        Button practiceButton = new Button("Harjoittele");
        Button logoutButton = new Button("Kirjaudu ulos");
        menuPane.getChildren().addAll(menuLabel, menuSpacer,practiceButton, logoutButton);
        logoutButton.setOnAction(e->{
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

        wordPairScrollbar.setContent(wordPairs);
        mainPane.setBottom(createForm);
        mainPane.setTop(menuPane);

        addWordPair.setOnAction(e->{
            appService.addWordPair(newWordInput.getText(), newTranslationInput.getText());
            newWordInput.setText("");
            newTranslationInput.setText("");
            redrawWordPairList();
        });


        // setup primary stage

        primaryStage.setTitle("Sanastosovellus");
        primaryStage.setScene(loginScene);
        primaryStage.show();
        primaryStage.setOnCloseRequest(e->{
            System.out.println("Suljetaan");
            System.out.println(appService.getLoggedUser());
            if (appService.getLoggedUser() != null) {
                e.consume();
            }
        });


    }

    @Override
    public void stop() {
        System.out.println("Sovellus sulkeutuu");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
