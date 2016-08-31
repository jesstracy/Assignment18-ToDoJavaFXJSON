package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.Scanner;

import jodd.json.JsonParser;
import jodd.json.JsonSerializer;

public class Controller implements Initializable {

    @FXML
    ListView todoList;

    @FXML
    TextField todoText;

//    ObservableList<ToDoItem> todoItems = FXCollections.observableArrayList();

    private ArrayList<User> users = new ArrayList<User>();

    ObservableList<ToDoItem> todoItems = FXCollections.observableArrayList();
    ToDoContainer myContainerInstance = new ToDoContainer();

    private String currentUser;

    final String MY_DATA_FILE = "todo.json";


    class ToDoContainer {

        ArrayList<ToDoItem> todoItemsArrayList = (ArrayList<ToDoItem>)todoItems;

        public ArrayList<ToDoItem> getTodoItemsArrayList() {
            return todoItemsArrayList;
        }

        public void setTodoItemsArrayList(ArrayList<ToDoItem> todoItemsArrayList) {
            this.todoItemsArrayList = todoItemsArrayList;
        }
    }




    // **********************************************************************************************************
    class User {
        private String userName;

        private ArrayList<ToDoItem> toDoItemsUser = new ArrayList<ToDoItem>();
//        ObservableList<ToDoItem> todoItems = FXCollections.observableArrayList();

        public User(String userName) {
            this.userName = userName;
        }

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

//        public void addTodoItem(ToDoItem item) {
//            todoItems.add(item);
//        }

//        public ObservableList<ToDoItem> getTodoItems() {
//            return todoItems;
//        }

//        public void putUsersListInObservableFile() {
//            for (ToDoItem item : toDoItemsUser) {
//                item.setOwner(this.userName);
//                myContainerInstance.getTodoItems().add(item);
//            }
//        }


        public ArrayList<ToDoItem> getToDoItemsUser() {
            return toDoItemsUser;
        }

        public void setToDoItemsUser(ArrayList<ToDoItem> toDoItemsUser) {
            this.toDoItemsUser = toDoItemsUser;
        }

        public void addToDoItemUser(ToDoItem item) {
            this.toDoItemsUser.add(item);
        }
    }
    // **********************************************************************************************************


    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        File listOfUsers = new File("ListOfUsers.txt");
//        if (listOfUsers.exists()) {
//            try {
//                Scanner listOfUsersFileReader = new Scanner(listOfUsers);
//                String currentLine = listOfUsersFileReader.nextLine();
//                String[] names = currentLine.split(",");
//                for (String name : names) {
//                    User returningUser = new User(name);
//
//                    // get their to do list items
//                    File userFile = new File(name + ".txt");
//                    try {
//                        Scanner userFileScanner = new Scanner(userFile);
//                        String userCurrentLine;
//                        String text;
//                        boolean isDone;
//                        while (userFileScanner.hasNext()) {
//                            userCurrentLine = userFileScanner.nextLine();
//                            text = userCurrentLine.split("=")[1];
//                            userCurrentLine = userFileScanner.nextLine();
//                            isDone = Boolean.valueOf(userCurrentLine.split("=")[1]);
//                            ToDoItem myItem = new ToDoItem(text, isDone);
////                            returningUser.addTodoItem(myItem);
//                            returningUser.addToDoItemUser(myItem);
//                        }
//                    } catch (Exception exception) {
//                        exception.printStackTrace();
//                    }
//                    // add them to list of all users!
//                    users.add(returningUser);
//                }
//            } catch (Exception exception) {
//                exception.printStackTrace();
//            }
//        }

        Scanner myScanner = new Scanner(System.in);
        String userName = askForUserName(myScanner);


//        User myUser = null;
//        // check if already on list of users
//        boolean alreadyUser = false;
//        for (User user : users) {
//            if (userName.equals(user.getUserName())) {
//                myUser = user;
//                System.out.println("Welcome back, " + userName + "!");
//                alreadyUser = true;
//                user.putUsersListInObservableFile();
//            }
//        }
//        if (!alreadyUser) {
//            System.out.println("Welcome new user!");
//            myUser = new User(userName);
//            users.add(myUser);
//        }
        currentUser = userName;
        todoList.setItems(todoItems);
    }

    public String askForUserName(Scanner myScanner) {
        System.out.print("What is your name? ");
        String userName = myScanner.nextLine();
        return userName;
    }

    public void handleEnterButton(KeyEvent event) {
        if (event.getCode() == KeyCode.ENTER) {
            addItem();
        }
    }

    public void addItem() {
        System.out.println("Adding item ...");
        todoItems.add(new ToDoItem(todoText.getText()));
        todoText.setText("");
//        printToFile();
//        jsonSave(todoItems);
    }

    public void removeItem() {
        ToDoItem todoItem = (ToDoItem)todoList.getSelectionModel().getSelectedItem();
        System.out.println("Removing " + todoItem.text + " ...");
        todoItems.remove(todoItem);
//        printToFile();
//        jsonSave(todoItems);
    }

    public void toggleItem() {
        System.out.println("Toggling item ...");
        ToDoItem todoItem = (ToDoItem)todoList.getSelectionModel().getSelectedItem();
        if (todoItem != null) {
            todoItem.isDone = !todoItem.isDone;
            todoList.setItems(null);
            todoList.setItems(todoItems);
        }
//        printToFile();
//        jsonSave(todoItems);
    }

    public void markAllDone() {
        System.out.println("Marking all items as \"done\"");
        for (ToDoItem toDoItem : todoItems) {
            toDoItem.isDone = true;
        }
        todoList.setItems(null);
        todoList.setItems(todoItems);
//        printToFile();
//        jsonSave(todoItems);
    }

    public void markAllNotDone() {
        System.out.println("Marking all items as \"not done\"");
        for (ToDoItem toDoItem : todoItems) {
            toDoItem.isDone = false;
        }
        todoList.setItems(null);
        todoList.setItems(todoItems);
//        printToFile();
//        jsonSave(todoItems);
    }

    public void toggleAll() {
        System.out.println("Toggling all...");
        for (ToDoItem toDoItem : todoItems) {
            toDoItem.isDone = !toDoItem.isDone;
        }
        todoList.setItems(null);
        todoList.setItems(todoItems);
//        printToFile();
//        jsonSave(todoItems);
    }

//    public void printToFile() {
//        try {
//            File listOfUsers = new File("ListOfUsers.txt");
//            FileWriter myFileWriter = new FileWriter(listOfUsers);
//            // It's doing it right now for everyuser, and I just want it to do it for the current user, but I can't pass in as a parameter.
//            for (User user : users) {
//                //Write user's name in list of users file
//                myFileWriter.write(user.getUserName() + ",");
//
//            }
//            myFileWriter.close();
////            printUserList();
//            //AND print the user's todos in their own file
//            File userFile = new File(currentUser + ".txt");
//            FileWriter myUserFileWriter = new FileWriter(userFile);
//            for (ToDoItem item : myContainerInstance.getTodoItems()) {
//                myUserFileWriter.write("toDoItem.text=" + item.getText() + "\n");
//                myUserFileWriter.write("toDoItem.isDone=" + item.isDone() + "\n");
//            }
//            myUserFileWriter.close();
//        } catch (Exception exception) {
//            exception.printStackTrace();
//        }
//    }


public void saveTDContainer(ToDoContainer container) throws IOException {
    FileOutputStream fos = new FileOutputStream(MY_DATA_FILE);
    ObjectOutput objectOut = new ObjectOutputStream(fos);
    objectOut.writeObject(container);
    objectOut.flush();
}

    public ToDoContainer restoreTDContainer() throws IOException, ClassNotFoundException {
        FileInputStream fis = new FileInputStream(MY_DATA_FILE);
        ObjectInputStream objectIn = new ObjectInputStream(fis);
        ToDoContainer restoredTDContainerObject = (ToDoContainer)objectIn.readObject();

        return restoredTDContainerObject;
    }

    public String jsonSave(ToDoContainer containerObject) {
        JsonSerializer jsonSerializer = new JsonSerializer().deep(true);
        String jsonString = jsonSerializer.serialize(containerObject);

        return jsonString;
    }

    public ToDoContainer jsonRestore(String jsonTD) {
        JsonParser toDoContainerParser = new JsonParser();
        ToDoContainer TDContainerObject = toDoContainerParser.parse(jsonTD, ToDoContainer.class);

        return TDContainerObject;
    }

}

