package com.stephen.servlets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.stephen.entities.DoublyLinkedHashTable;
import com.stephen.entities.DoublyLinkedHashTableTypeAdapter;
import com.stephen.entities.Node;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

/**
 * @author Stephen Chen
 * represents a servlet that initializes a JSON database for a web application.
 * extends the HttpServlet class and is annotated with @WebServlet("/init").
 */
@WebServlet("/init")
public class InitServlet extends HttpServlet {
    final String file_path = "src/main/resources/dataBase.json"; // Path to the JSON database file

    // Constant strings representing types of content
    final String STRING = "STRING";
    final String IMAGE = "IMAGE";
    final String DOUBLE = "DOUBLE";
    final String URL = "URL";
    final String LIST = "LIST";

    // JsonObject containing lists stored in the JSON database
    JsonObject lists_in_json;

    // Gson object used for JSON serialization/deserialization
    Gson gson = new GsonBuilder()
            .registerTypeAdapter(DoublyLinkedHashTable.class, new DoublyLinkedHashTableTypeAdapter())
            .create();

    // List of keys representing the names of lists stored in the JSON database
    ArrayList<String> keys;

    /**
     * Constructor that initializes the JSON database if it does not already exist.
     * @throws IOException if there is an I/O error reading or writing the JSON database file
     */
    public InitServlet() throws IOException {

        // Access the file, create it if not existed
        File database = new File(file_path);
        if (database.length() == 0){

            // Create an example list1 if the database file is empty
            DoublyLinkedHashTable list1 = new DoublyLinkedHashTable("Example1");

            // Create example Node Item1 and add contents
            Node node1 = new Node(new ArrayList<>());
            node1.setName("Item1");
            node1.add_content(STRING, "Hello World");
            node1.add_content(URL, "https://chat.openai.com/");
            node1.add_content(IMAGE, "https://blog.hootsuite.com/wp-content/uploads/2020/02/Image-copyright.png");
            node1.add_content(DOUBLE, "90.37848");

            // Create example Node Item2 and add contents
            Node node2 = new Node(new ArrayList<>());
            node2.setName("Item2");
            node2.add_content(URL, "https://chat.openai.com/");
            node2.add_content(IMAGE, "https://media.sproutsocial.com/uploads/2017/02/10x-featured-social-media-image-size.png");
            node2.add_content(DOUBLE, "90.37848");

            // Create example Node Item3 and add contents
            Node node3 = new Node(new ArrayList<>());
            node3.setName("Item3");
            node3.add_content(URL, "https://chat.openai.com/");
            node3.add_content(IMAGE, "https://www.simplilearn.com/ice9/free_resources_article_thumb/what_is_image_Processing.jpg");
            node3.add_content(LIST, "Example2");

            // add nodes into list1 and write into database
            list1.insert(node1);
            list1.insert(node2);
            list1.insert(node3);
            list1.writeIntoJson();

            // Create an example list2 if the database file is empty
            DoublyLinkedHashTable list2 = new DoublyLinkedHashTable("Example2");

            // Create example Node Item1 and add contents
            Node node4 = new Node(new ArrayList<>());
            node4.setName("Item1");
            node4.add_content(STRING, "Hello Stephen");
            node4.add_content(IMAGE, "/Users/stephchen/Desktop/Screenshot 2023-02-19 at 22.48.43 (2).png");

            // add nodes into list2 and write into database
            list2.insert(node4);
            list2.writeIntoJson();
        }
    }

    /**
     * overrides the doGet method in the HttpServlet class
     * handles HTTP GET requests and reads a JSON file
     * sets the keys attribute to the list of keys in the JSON file
     * forwards the request to the overview.jsp page
     * @param req an HttpServletRequest object that contains the request the client has made of the servlet
     * @param resp an HttpServletResponse object that contains the response the servlet sends to the client
     * @throws ServletException if the request for the GET could not be handled
     * @throws IOException if an input or output error is detected when the servlet handles the GET request
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");

        // set overview to be default method
        if (method==null) method = "overview";

        // in case more than 1 method
        switch (method){
            case "overview":

                // read all available list and send their name to overview.jsp
                BufferedReader reader = new BufferedReader(new FileReader(file_path));
                lists_in_json = gson.fromJson(reader, JsonObject.class);
                reader.close();
                keys = new ArrayList<>(lists_in_json.keySet());
                req.setAttribute("keys", keys);
                req.getRequestDispatcher("overview.jsp").forward(req, resp);
                break;
        }

    }

    /**
     * Overrides the doPost method in the HttpServlet class
     * handles HTTP POST requests and performs different actions based on the method parameter
     * If the method parameter is "spotlight_search", it searches for an item in the JSON file and sets the result_nodes and result_lists attributes
     * If the method parameter is not "spotlight_search", it reads the JSON file, extracts the selected list, and sets the selected_list attribute
     * Finally, it forwards the request to the corresponding JSP page
     * @param req an HttpServletRequest object that contains the request the client has made of the servlet
     * @param resp an HttpServletResponse object that contains the response the servlet sends to the client
     * @throws ServletException if the request for the POST could not be handled
     * @throws IOException if an input or output error is detected when the servlet handles the POST request
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");

        // assign different task based on parameter method
        switch (method){
            case "spotlight_search":
                String item_name = req.getParameter("item_name");
                ArrayList<String> result_nodes = new ArrayList<>(); // store Node name in that list
                ArrayList<String> result_lists = new ArrayList<>(); // store list name containing nodes

                // Traverse all available keys
                for (String key: keys) {

                    // get correspond list from jsonObject and find
                    JsonObject target = lists_in_json.get(key).getAsJsonObject();
                    DoublyLinkedHashTable list = gson.fromJson(target, DoublyLinkedHashTable.class);
                    Node temp = list.find(item_name);

                    // if something found, add the node and list to ArrayList
                    if (temp != null) {
                        result_nodes.add(temp.getName());
                        result_lists.add(key);
                    }
                }

                // send request to spotlight_search.jsp to represent
                req.setAttribute("result_nodes", result_nodes);
                req.setAttribute("result_lists", result_lists);
                req.getRequestDispatcher("/spotlight_search.jsp").forward(req, resp);
                break;

            default:
                // set the key to current list key and get correspond list
                String key = req.getParameter("key");
                String jsonContent = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(file_path)));
                JsonObject jsonObject = gson.fromJson(jsonContent, JsonObject.class);
                JsonObject a1JsonObject = jsonObject.getAsJsonObject(key);
                DoublyLinkedHashTable selected_list = gson.fromJson(a1JsonObject, DoublyLinkedHashTable.class);

                // send request with selected list to the list servlet
                req.setAttribute("selected_list", selected_list);
                req.getRequestDispatcher("/list").forward(req, resp);
                break;
        }
    }
}
