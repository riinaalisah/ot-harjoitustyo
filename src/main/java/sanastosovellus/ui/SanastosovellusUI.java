package sanastosovellus.ui;

import javafx.scene.control.*;
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
    private Label errorLabel = new Label();
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
        primaryStage.setOnCloseRequest(e -> {
            System.out.println("Suljetaan");
            stop();
        });
    }

    /**
     * Creates a HBox element with word, translation and checkbox (for deletion)
     *
     * @param pair Word pair to create
     * @return HBox with elements for word, translation and checkbox
     */
    public Node createWordPairNode(WordPair pair) {
        HBox box = new HBox(20);

        Label wordLabel = new Label(pair.getWord());
        Label translationLabel = new Label(pair.getTranslation());
        CheckBox cb = new CheckBox();
        cb.setIndeterminate(false);

        HBox wordLabelBox = new HBox(wordLabel);
        HBox translationLabelBox = new HBox(translationLabel);

        wordLabelBox.setMinHeight(20);
        translationLabelBox.setMinHeight(20);

        wordLabelBox.setPrefWidth(60);
        translationLabelBox.setPrefWidth(60);

        Region spacer = new Region();
        HBox.setHgrow(wordLabelBox, Priority.ALWAYS);
        HBox.setHgrow(translationLabelBox, Priority.ALWAYS);
        box.setPadding(new Insets(0, 5, 0, 5));

        box.getChildren().addAll(wordLabelBox, translationLabelBox, cb, spacer);
        return box;
    }

    /**
     * Updates word pair list and creates a node for every word pair
     */
    public void redrawWordPairList() {
        wordPairs.getChildren().clear();

        List<WordPair> pairs = appService.getPairs();
        pairs.forEach(pair -> {
            Node newNode = createWordPairNode(pair);
            wordPairs.getChildren().addAll(newNode);
        });
    }


    /**
     * Sets the login scene
     *
     * @param primaryStage Primary stage
     */
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
        loginButton.setOnAction(e -> {
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

        Button createButton = new Button("Luo uusi käyttäjä");
        createButton.setOnAction(e -> {
            usernameInput.setText("");
            pwdInput.setText("");
            setNewUserScene(primaryStage);
            primaryStage.setScene(newUserScene);
        });

        loginPane.getChildren().addAll(loginMessage, usernameInputPane, pwdInputPane, loginButton, createButton);

        loginScene = new Scene(loginPane, 350, 250);
    }

    /**
     * Sets scene fro creating a new user
     *
     * @param primaryStage Primary stage
     */
    public void setNewUserScene(Stage primaryStage) {
        VBox newUserPane = new VBox(10);
        newUserPane.setPadding(new Insets(10));
        HBox newUsernamePane = new HBox(10);
        HBox newPwdPane = new HBox(10);
        Label userCreationMsg = new Label();

        Label newUsernameLabel = new Label("Käyttäjänimi");
        TextField newUsernameInput = new TextField();
        newUsernameLabel.setPrefWidth(100);
        newUsernamePane.getChildren().addAll(newUsernameLabel, newUsernameInput);

        PasswordField newPwdInput = new PasswordField();
        Label newPwdLabel = new Label("Salasana");
        newPwdLabel.setPrefWidth(100);
        newPwdPane.getChildren().addAll(newPwdLabel, newPwdInput);

        // HBox for two buttons: create button and back button
        HBox buttonBox = new HBox(10);

        Button createNewUserButton = new Button("Luo uusi käyttäjä");
        createNewUserButton.setOnAction(e -> {
            String username = newUsernameInput.getText();
            String pwd = newPwdInput.getText();
            if (username.trim().isEmpty() || pwd.trim().isEmpty()) {
                userCreationMsg.setText("Tyhjät syötteet eivät ole sallittuja.");
                userCreationMsg.setTextFill(Color.RED);
            } else if (username.length() < 5 || pwd.length() < 5) {
                userCreationMsg.setText("Käyttäjänimi tai salasana liian lyhyt.");
                userCreationMsg.setTextFill(Color.RED);
            } else if (appService.createUser(username, pwd)) {
                userCreationMsg.setText("");
                loginMessage.setText("Uusi käyttäjä luotu.");
                loginMessage.setTextFill(Color.GREEN);
                setLoginScene(primaryStage);
                primaryStage.setScene(loginScene);
            } else {
                userCreationMsg.setText("Käyttäjänimen tulee olla uniikki.");
                userCreationMsg.setTextFill(Color.RED);
            }
        });

        Button backButton = new Button("Takaisin");
        backButton.setOnAction(e -> {
            primaryStage.setScene(loginScene);
        });

        buttonBox.getChildren().addAll(createNewUserButton, backButton);

        newUserPane.getChildren().addAll(userCreationMsg, newUsernamePane, newPwdPane, buttonBox);
        newUserScene = new Scene(newUserPane, 350, 250);
    }

    /**
     * Sets the main scene where words are listed
     *
     * @param primaryStage Primary stage
     */
    public void setMainScene(Stage primaryStage) {
        ScrollPane wordPairScrollbar = new ScrollPane();
        wordPairScrollbar.setPadding(new Insets(15));
        BorderPane mainPane = new BorderPane(wordPairScrollbar);

        /**
         * Set menu pane
         */
        HBox menuPane = new HBox(10);
        Region menuSpacer = new Region();
        menuPane.setPadding(new Insets(10));
        HBox.setHgrow(menuSpacer, Priority.ALWAYS);

        Button logoutButton = new Button("Kirjaudu ulos");
        logoutButton.setOnAction(e -> {
            usersWordpairs = null;
            appService.logout();
            primaryStage.setScene(loginScene);
        });

        menuPane.getChildren().addAll(menuLabel, menuSpacer, logoutButton);

        /**
         * Set word pair creation form
         */
        VBox createForm = new VBox(10);
        createForm.setPadding(new Insets(10));
        HBox createFormLabels = new HBox(10);
        HBox createFormInputs = new HBox(10);

        // Labels
        Label wordLabel = new Label("Sana");
        wordLabel.setPrefWidth(170);
        Label translationLabel = new Label("Käännös");
        createFormLabels.getChildren().addAll(wordLabel, translationLabel);

        // Inputs and create button
        TextField newWordInput = new TextField();
        TextField newTranslationInput = new TextField();
        Region spacer = new Region();
        Button addWordPair = new Button("Lisää sanapari");
        addWordPair.setOnAction(e -> {
            if (newWordInput.getText().trim().isEmpty() || newTranslationInput.getText().trim().isEmpty()) {
                errorLabel.setText("Tyhjät syötteet eivät ole sallittuja.");
                errorLabel.setTextFill(Color.RED);
            } else {
                errorLabel.setText("");
                appService.addWordPair(newWordInput.getText(), newTranslationInput.getText());
                newWordInput.setText("");
                newTranslationInput.setText("");
                redrawWordPairList();
                usersWordpairs = appService.getPairs();
            }

        });
        HBox.setHgrow(spacer, Priority.ALWAYS);
        createFormInputs.getChildren().addAll(newWordInput, newTranslationInput, spacer, addWordPair);

        createForm.getChildren().addAll(createFormLabels, createFormInputs, errorLabel);

        /**
         * Set word pair list
         */
        wordPairs = new VBox(10);
        wordPairs.setMaxWidth(280);
        wordPairs.setMinWidth(280);
        redrawWordPairList();

        /**
         * Set side pane with practice and deletion buttons
         */
        VBox buttonBox = new VBox(10);
        buttonBox.setPadding(new Insets(10));

        // Practice button for direction word -> translation
        Button practiceButtonDefault = new Button("Harjoittele (suomi - käännös)");
        practiceButtonDefault.setOnAction(e -> {
            if (usersWordpairs.size() > 0) {
                // shuffle the words so they are not always in the same order
                Collections.shuffle(usersWordpairs);
                practiceMessage.setText("");
                setPracticeScene(primaryStage, 0, true);
                primaryStage.setScene(practiceScene);
            }

        });

        // Practice button for direction translation -> word
        Button practiceButtonOther = new Button("Harjoittele (käännös - suomi)");
        practiceButtonOther.setOnAction(e -> {
            if (usersWordpairs.size() > 0) {
                Collections.shuffle(usersWordpairs);
                practiceMessage.setText("");
                setPracticeScene(primaryStage, 0, false);
                primaryStage.setScene(practiceScene);
            }
        });

        // Button for deleting word pairs from list
        Button deleteWordPairsButton = new Button("Poista valitut sanaparit");
        deleteWordPairsButton.setOnAction(e -> {
            // Collect checkboxes to list
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
            // Delete word pairs that are checked
            for (int i = 0; i < boxes.size(); i++) {
                CheckBox box = boxes.get(i);
                if (box.isSelected()) {
                    appService.deleteWordPair(usersWordpairs.get(i));
                }
            }
            redrawWordPairList();
            usersWordpairs = appService.getPairs();
        });

        buttonBox.getChildren().addAll(practiceButtonDefault, practiceButtonOther, deleteWordPairsButton);

        // Set everything to place
        wordPairScrollbar.setContent(wordPairs);
        mainPane.setTop(menuPane);
        mainPane.setBottom(createForm);
        mainPane.setRight(buttonBox);

        appScene = new Scene(mainPane, 600, 450);
    }

    /**
     * Sets practice scene
     *
     * @param primaryStage     Stage
     * @param index            Index number for current word
     * @param defaultDirection Direction of practice, true if direction is word to translation
     */
    public void setPracticeScene(Stage primaryStage, int index, boolean defaultDirection) {

        VBox practicePane = new VBox(10);
        practicePane.setPadding(new Insets(10));

        HBox wordPairPane = new HBox(16);
        Label wordLabel = new Label();
        TextField answerInput = new TextField();
        Button answerButton = new Button("Vastaa");

        if (defaultDirection) {
            wordLabel.setText(usersWordpairs.get(index).getWord());
        } else {
            wordLabel.setText(usersWordpairs.get(index).getTranslation());
        }
        wordPairPane.getChildren().addAll(wordLabel, answerInput, answerButton);

        answerButton.setOnAction(e -> {
            /**
             * Direction word -> translation
             */
            if (defaultDirection) {
                if (answerInput.getText().equals(usersWordpairs.get(index).getTranslation())) {
                    practiceMessage.setText("Oikein! " + usersWordpairs.get(index).getWord() + " = " + usersWordpairs.get(index).getTranslation());
                    practiceMessage.setTextFill(Color.GREEN);

                    /**
                     * Next word if there are words left
                     */
                    if (index < usersWordpairs.size() - 1) {
                        setPracticeScene(primaryStage, index + 1, true);
                        primaryStage.setScene(practiceScene);
                    }

                } else {
                    practiceMessage.setText("Väärin, yritä uudelleen.");
                    practiceMessage.setTextFill(Color.RED);
                }

                /**
                 * Direction translation -> word
                 */
            } else {

                if (answerInput.getText().equals(usersWordpairs.get(index).getWord())) {
                    practiceMessage.setText("Oikein! " + usersWordpairs.get(index).getTranslation() + " = " + usersWordpairs.get(index).getWord());
                    practiceMessage.setTextFill(Color.GREEN);

                    /**
                     * Next word if there are words left
                     */
                    if (index < usersWordpairs.size() - 1) {
                        setPracticeScene(primaryStage, index + 1, false);
                        primaryStage.setScene(practiceScene);
                    }

                } else {
                    practiceMessage.setText("Väärin, yritä uudelleen.");
                    practiceMessage.setTextFill(Color.RED);
                }
            }

        });

        HBox buttonBox = new HBox(10);
        buttonBox.setPadding(new Insets(10, 0, 0, 0));

        /**
         * Button for skipping a word
         */
        Button nextButton = new Button("Ohita");
        nextButton.setOnAction(e -> {
            if (defaultDirection) {
                practiceMessage.setText(usersWordpairs.get(index).getWord() + " = " + usersWordpairs.get(index).getTranslation());
            } else {
                practiceMessage.setText(usersWordpairs.get(index).getTranslation() + " = " + usersWordpairs.get(index).getWord());
            }

            practiceMessage.setTextFill(Color.CHOCOLATE);
            if (index < usersWordpairs.size() - 1) {
                setPracticeScene(primaryStage, index + 1, defaultDirection);
                primaryStage.setScene(practiceScene);
            }

        });

        Button backButton = new Button("Lopeta");
        backButton.setOnAction(e -> {
            setMainScene(primaryStage);
            primaryStage.setScene(appScene);
        });

        buttonBox.getChildren().addAll(nextButton, backButton);
        practicePane.getChildren().addAll(practiceMessage, wordPairPane, buttonBox);

        practiceScene = new Scene(practicePane, 400, 250);
    }

    @Override
    public void stop() {
        System.out.println("Sovellus sulkeutuu");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
