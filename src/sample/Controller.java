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

    ObservableList<ToDoItem> todoItems = FXCollections.observableArrayList();
    ToDoContainer myContainerInstance = new ToDoContainer();

    private String currentUser;


    // Why static?? -> the restoredisk method could not see the constructor when it wasn't static/
    private static class ToDoContainer {

        public ToDoContainer() {

        }

        public ArrayList<ToDoItem> todoItemsArrayList = new ArrayList<ToDoItem>();

//        public ArrayList<ToDoItem> getTodoItemsArrayList() {
//            return todoItemsArrayList;
//        }
//
//        public void setTodoItemsArrayList(ArrayList<ToDoItem> todoItemsArrayList) {
//            this.todoItemsArrayList = todoItemsArrayList;
//        }
//
//        public void addToDoItem(ToDoItem item) {
//            todoItemsArrayList.add(item);
//        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        Scanner myScanner = new Scanner(System.in);
        currentUser = askForUserName(myScanner);


        File userFile = new File(currentUser + ".json");
        if (userFile.exists()) {
            readFromDisk();
        }

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

        saveToDisk();
    }

    public void removeItem() {
        ToDoItem todoItem = (ToDoItem) todoList.getSelectionModel().getSelectedItem();
        System.out.println("Removing " + todoItem.text + " ...");
        todoItems.remove(todoItem);

        saveToDisk();
    }

    public void toggleItem() {
        System.out.println("Toggling item ...");
        ToDoItem todoItem = (ToDoItem) todoList.getSelectionModel().getSelectedItem();
        if (todoItem != null) {
            todoItem.isDone = !todoItem.isDone;
            todoList.setItems(null);
            todoList.setItems(todoItems);
        }
        saveToDisk();
    }

    public void markAllDone() {
        System.out.println("Marking all items as \"done\"");
        for (ToDoItem toDoItem : todoItems) {
            toDoItem.isDone = true;
        }
        todoList.setItems(null);
        todoList.setItems(todoItems);

        saveToDisk();
    }

    public void markAllNotDone() {
        System.out.println("Marking all items as \"not done\"");
        for (ToDoItem toDoItem : todoItems) {
            toDoItem.isDone = false;
        }
        todoList.setItems(null);
        todoList.setItems(todoItems);

        saveToDisk();
    }

    public void toggleAll() {
        System.out.println("Toggling all...");
        for (ToDoItem toDoItem : todoItems) {
            toDoItem.isDone = !toDoItem.isDone;
        }
        todoList.setItems(null);
        todoList.setItems(todoItems);

        saveToDisk();
    }

//    public String jsonSave(ToDoContainer containerObject) {
//        JsonSerializer jsonSerializer = new JsonSerializer().deep(true);
//        String jsonString = jsonSerializer.serialize(containerObject);
//
//        return jsonString;
//    }

//    public ToDoContainer jsonRestore(String jsonTD) {
//        JsonParser toDoContainerParser = new JsonParser();
//        ToDoContainer TDContainerObject = toDoContainerParser.parse(jsonTD, ToDoContainer.class);
//
//        return TDContainerObject;
//    }


    private void saveToDisk() {
        ToDoContainer newContainer = new ToDoContainer();
        newContainer.todoItemsArrayList.clear();
        for (ToDoItem item : todoItems) {
            newContainer.todoItemsArrayList.add(item);
            System.out.println("adding...");
        }

        JsonSerializer jsonSerializer = new JsonSerializer().deep(true);
        String jsonString = jsonSerializer.serialize(newContainer);

        try {
            FileWriter fw = new FileWriter(currentUser + ".json");
            fw.write(jsonString);
            fw.flush();
            fw.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }

    }

    private void readFromDisk() {
        try {
            File userFile = new File(currentUser + ".json");
            Scanner fileScanner = new Scanner(userFile);
            String toDoJsonString = fileScanner.nextLine();
            JsonParser toDoContainerParser = new JsonParser();
            ToDoContainer toDoContainer = toDoContainerParser.parse(toDoJsonString, ToDoContainer.class);
            for (ToDoItem item : toDoContainer.todoItemsArrayList) {
                todoItems.add(item);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Did not find file.");
        }
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

}

