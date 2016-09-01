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

    ObservableList<ToDoItem> todoItems = FXCollections.observableArrayList();
    ToDoContainer myContainerInstance = new ToDoContainer();

    private String currentUser = "Jessica";

    String jsonString;


    class ToDoContainer {

        ArrayList<ToDoItem> todoItemsArrayList = new ArrayList<ToDoItem>();

        public ArrayList<ToDoItem> getTodoItemsArrayList() {
            return todoItemsArrayList;
        }

        public void setTodoItemsArrayList(ArrayList<ToDoItem> todoItemsArrayList) {
            this.todoItemsArrayList = todoItemsArrayList;
        }

        public void addToDoItem(ToDoItem item) {
            todoItemsArrayList.add(item);
        }
    }


    @Override
    public void initialize(URL location, ResourceBundle resources) {
//        try {
            // populate observable list with what we read in from file.
//            populateToDoItemsFromFile();

//        } catch (ClassNotFoundException exception) {
//            exception.printStackTrace();
//        } catch (IOException exception) {
//            exception.printStackTrace();
//        }

        Scanner myScanner = new Scanner(System.in);
        currentUser = askForUserName(myScanner);

        File userFile = new File(currentUser + ".json");
        if (userFile.exists()) {
            try {
//                ArrayList<ToDoItem> toDoItemsArrayList = new ArrayList<ToDoItem>();

//                FileInputStream fis = new FileInputStream(userFile);
//                ObjectInputStream objectIn = new ObjectInputStream(fis);
//                toDoItemsArrayList = (ArrayList<ToDoItem>)objectIn.readObject();
                String jsonString = restoreTDContainer();
                myContainerInstance = jsonRestore(jsonString);
                for (ToDoItem item : myContainerInstance.getTodoItemsArrayList()) {
//                for (ToDoItem item : toDoItemsArrayList) {
                    todoItems.add(item);
                }
                System.out.println(todoItems);
//                FileReader myFileReader = new FileReader(userFile);
//                String jsonLine = myFileReader.read();
//                JsonParser toDoContainerParser = new JsonParser();
//                ArrayList<ToDoItem> toDoItemsArrayList = toDoContainerParser.parse(jsonLine, ArrayList.class);
//                System.out.println(toDoItemsArrayList);
            } catch (IOException exception) {
                exception.printStackTrace();
            } catch (ClassNotFoundException exception) {
                exception.printStackTrace();
            }
        }

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
//        currentUser = userName;
        todoList.setItems(todoItems);
    }

//    public void populateToDoItemsFromFile() {
//        String jsonTD = restoreTDContainer();
//        myContainerInstance = jsonRestore(jsonTD);
//
//        for (ToDoItem item : myContainerInstance.getTodoItemsArrayList()) {
//            todoItems.add(item);
//        }
//    }

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
//        try {
            System.out.println("Adding item ...");
            todoItems.add(new ToDoItem(todoText.getText()));
            todoText.setText("");
//            myContainerInstance.setTodoItemsArrayList((ArrayList<ToDoItem>)todoItems);
            for (ToDoItem item : todoItems) {
                myContainerInstance.addToDoItem(item);
            }
//            jsonSaveString = jsonSave(myContainerInstance);
//            saveTDContainer(jsonSaveString);
//            saveTDContainer(myContainerInstance);
            writeFile();
//        } catch (IOException exception) {
//            exception.printStackTrace();
//        }
    }

    public void removeItem() {
//        try {
            ToDoItem todoItem = (ToDoItem) todoList.getSelectionModel().getSelectedItem();
            System.out.println("Removing " + todoItem.text + " ...");
            todoItems.remove(todoItem);
            for (ToDoItem item : todoItems) {
                myContainerInstance.addToDoItem(item);
            }
//            jsonSaveString = jsonSave(myContainerInstance);
//            saveTDContainer(jsonSaveString);
//            saveTDContainer(myContainerInstance);
            writeFile();
//        } catch (IOException exception) {
//            exception.printStackTrace();
//        }
    }

    public void toggleItem() {
//        try {
            System.out.println("Toggling item ...");
            ToDoItem todoItem = (ToDoItem) todoList.getSelectionModel().getSelectedItem();
            if (todoItem != null) {
                todoItem.isDone = !todoItem.isDone;
                todoList.setItems(null);
                todoList.setItems(todoItems);
            }
            for (ToDoItem item : todoItems) {
                myContainerInstance.addToDoItem(item);
            }
//            jsonSaveString = jsonSave(myContainerInstance);
//            saveTDContainer(jsonSaveString);
//            saveTDContainer(myContainerInstance);
            writeFile();
//        } catch (IOException exception) {
//            exception.printStackTrace();
//        }
    }

    public void markAllDone() {
//        try {
            System.out.println("Marking all items as \"done\"");
            for (ToDoItem toDoItem : todoItems) {
                toDoItem.isDone = true;
            }
            todoList.setItems(null);
            todoList.setItems(todoItems);
            for (ToDoItem item : todoItems) {
                myContainerInstance.addToDoItem(item);
            }
//            jsonSaveString = jsonSave(myContainerInstance);
//            saveTDContainer(jsonSaveString);
//            saveTDContainer(myContainerInstance);
            writeFile();
//        } catch (IOException exception) {
//            exception.printStackTrace();
//        }
    }

    public void markAllNotDone() {
//        try {
            System.out.println("Marking all items as \"not done\"");
            for (ToDoItem toDoItem : todoItems) {
                toDoItem.isDone = false;
            }
            todoList.setItems(null);
            todoList.setItems(todoItems);
            for (ToDoItem item : todoItems) {
                myContainerInstance.addToDoItem(item);
            }
//            jsonSaveString = jsonSave(myContainerInstance);
//            saveTDContainer(jsonSaveString);
//            saveTDContainer(myContainerInstance);
            writeFile();
//        } catch (IOException exception) {
//            exception.printStackTrace();
//        }
    }

    public void toggleAll() {
//        try {
            System.out.println("Toggling all...");
            for (ToDoItem toDoItem : todoItems) {
                toDoItem.isDone = !toDoItem.isDone;
            }
            todoList.setItems(null);
            todoList.setItems(todoItems);
            for (ToDoItem item : todoItems) {
                myContainerInstance.addToDoItem(item);
            }
//            jsonSaveString = jsonSave(myContainerInstance);
//            saveTDContainer(jsonSaveString);
//            saveTDContainer(myContainerInstance);
            writeFile();
//        } catch (IOException exception) {
//            exception.printStackTrace();
//        }
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
        FileOutputStream fos = new FileOutputStream(currentUser + ".json");
        ObjectOutput objectOut = new ObjectOutputStream(fos);
        objectOut.writeObject(container);
        objectOut.flush();
    }

//    public void saveTDContainer(String jsonString) throws IOException {
//        FileOutputStream fos = new FileOutputStream(MY_DATA_FILE);
//        ObjectOutput objectOut = new ObjectOutputStream(fos);
//        objectOut.writeObject(jsonString);
//        objectOut.flush();
//    }

//    public ToDoContainer restoreTDContainer() throws IOException, ClassNotFoundException {
//        FileInputStream fis = new FileInputStream(currentUser + ".json");
//        ObjectInputStream objectIn = new ObjectInputStream(fis);
//        ToDoContainer restoredTDContainerObject = (ToDoContainer)objectIn.readObject();
//
//        return restoredTDContainerObject;
//    }

    public String restoreTDContainer() throws IOException, ClassNotFoundException {
//        FileInputStream fis = new FileInputStream(currentUser + ".json");
        BufferedReader in = new BufferedReader(new FileReader(currentUser + ".json"));
        try {
            StringBuilder sb = new StringBuilder();
            String line = in.readLine();

            while (line != null) {
                sb.append(line);
                sb.append("\n");
                line = in.readLine();
            }
            return sb.toString();
        } finally {
            in.close();
        }
    }

//    public String restoreTDContainer() throws IOException, ClassNotFoundException {
//        FileInputStream fis = new FileInputStream(MY_DATA_FILE);
//        ObjectInputStream objectIn = new ObjectInputStream(fis);
//        String restoredTDContainerObject = (String)objectIn.readObject();
//
//        return restoredTDContainerObject;
//    }

    public String jsonSave(ToDoContainer containerObject) {
        JsonSerializer jsonSerializer = new JsonSerializer().deep(true);
        String jsonString = jsonSerializer.serialize(containerObject);

        return jsonString;
    }

    public ToDoContainer jsonRestore(String jsonTD) {
        //Need a file reader here?
        JsonParser toDoContainerParser = new JsonParser();
        ToDoContainer TDContainerObject = toDoContainerParser.parse(jsonTD, ToDoContainer.class);

        return TDContainerObject;
    }

    public void writeFile() {
        try {
            JsonSerializer jsonSerializer = new JsonSerializer().deep(true);
            String jsonString = jsonSerializer.serialize(myContainerInstance);
            FileWriter fw = new FileWriter(currentUser + ".json");
            fw.write(jsonString);
            fw.flush();
            fw.close();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
    }

    public ToDoContainer readFile() throws IOException, ClassNotFoundException{

        FileInputStream fis = new FileInputStream(currentUser + ".json");
        ObjectInputStream objectIn = new ObjectInputStream(fis);
        ToDoContainer restoredTDContainerObject = (ToDoContainer) objectIn.readObject();
        JsonParser toDoContainerParser = new JsonParser();
        ToDoContainer TDContainerObject = toDoContainerParser.parse(jsonString, ToDoContainer.class);
        return TDContainerObject;


    }
}

