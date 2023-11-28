package com.stephen.servlets;

import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.stephen.entities.DoublyLinkedHashTable;
import com.stephen.entities.DoublyLinkedHashTableTypeAdapter;
import com.stephen.entities.Node;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;

import java.util.ArrayList;

/**
 * @author Stephen Chen
 * receive and process user's operation and send redirect
 * extend the HttpServlet class and is annotated with @WebServlet("/list")
 */
@WebServlet("/list")
public class UserServlet extends HttpServlet {

    // Type constants for adding content to Node
    final String STRING = "STRING";
    final String IMAGE = "IMAGE";
    final String DOUBLE = "DOUBLE";
    final String URL = "URL";
    final String LIST = "LIST";

    DoublyLinkedHashTable list = null; // Current selected list

    /**
     * read json file and set the current list.
     * @param key The name of the list.
     */
    public void setList(String key) {
        try{

            // Gson object used for JSON serialization/deserialization
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(DoublyLinkedHashTable.class, new DoublyLinkedHashTableTypeAdapter())
                    .create();

            // read all lists from the file as a JsonObject
            BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/dataBase.json"));
            JsonObject jsonObject = gson.fromJson(reader, JsonObject.class);
            reader.close();
            System.out.println(key);

            // if JsonObject contains required key, read correspond list as a DoublyLinkedHashTable
            if (jsonObject.has(key)) {
                JsonObject target = jsonObject.get(key).getAsJsonObject();
                this.list = gson.fromJson(target, DoublyLinkedHashTable.class);
            }

            // if not found, create a new empty DoublyLinkedHashTable
            else{
                this.list = new DoublyLinkedHashTable(key);
            }

        // catch exception if there is error with database file
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("file not found");
        }

    }

    /**
     * override the doGet method in the HttpServlet class
     * handle HTTP GET requests performs different actions based on the method parameter
     * receive and execute required operations to a list
     * if the method parameter is "delete", it deletes item from current list
     * if the method parameter is "traverse", it displays current list
     * if the method parameter is "update", it modifies selected item in current list
     * if the method parameter is "deleteList", it deletes current list from database
     * if the method parameter is "search", it does search for item name in current list
     * forward the request to correspond jsp file or other servlet
     * @param req an HttpServletRequest object that contains the request the client has made of the servlet
     * @param resp an HttpServletResponse object that contains the response the servlet sends to the client
     * @throws ServletException if the request for the GET could not be handled
     * @throws IOException if an input or output error is detected when the servlet handles the GET request
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        String key = req.getParameter("key");

        // if key equals null, there is no change to current list
        if (key != null) {

            // if key length is 0, user want to create a new list
            if (key.length() == 0) {
                resp.sendRedirect("addList.jsp");
                return;
            }

            // if current list is null or key not equals to current list name, set to new list
            if (list == null || !key.equals(list.getName())) setList(key);
        }

        // default method is traverse
        if (method == null) method = "traverse";
        switch (method) {

            // delete item
            case "delete" -> {
                String name = req.getParameter("name");
                list.remove(name);
                list.writeIntoJson();
                resp.sendRedirect("/list");
            }

            // traverse current list
            case "traverse" -> {
                req.setAttribute("list", list);
                req.getRequestDispatcher("traverse.jsp").forward(req, resp);
            }

            // modify item
            case "update" -> {
                String name = req.getParameter("name");
                req.setAttribute("item", list.find(name));
                req.getRequestDispatcher("update.jsp").forward(req,resp);
            }

            // delete current list
            case "deleteList" ->{
                if (list != null) list.remove_from_json();
                resp.sendRedirect("/init");
            }

            // search in current list
            case "search" -> {
                String name = req.getParameter("search_name");
                System.out.println("list: "+list.getName());
                System.out.println("item "+list.find(name).getName());
                req.setAttribute("item", list.find(name));
                req.getRequestDispatcher("search.jsp").forward(req, resp);
            }
        }
    }

    /**
     * Override the doPost method in the HttpServlet class
     * handle HTTP POST requests and performs different actions based on the method parameter
     * if the method parameter is "addNode", it gets all user input and create a new item
     * if the method parameter is "update", it modifies selected item in current list
     * if the method parameter is "search", it does search for item name in current list
     * if the method parameter is "rename", it deletes current list from database
     * finally, it forwards the request to the corresponding JSP page
     * @param req an HttpServletRequest object that contains the request the client has made of the servlet
     * @param resp an HttpServletResponse object that contains the response the servlet sends to the client
     * @throws ServletException if the request for the POST could not be handled
     * @throws IOException if an input or output error is detected when the servlet handles the POST request
     */
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String method = req.getParameter("method");
        String name = req.getParameter("name");

        // create a normal node and set name required
        Node node = new Node(new ArrayList<>());
        node.setName(name);

        switch (method){

            // Create a new node using inputs
            case "addNode":

                // Grab all parameters and add to new node
                String urls = req.getParameter("urls");
                String images = req.getParameter("images");
                String doubles = req.getParameter("doubles");
                String strings = req.getParameter("strings");
                String lists = req.getParameter("lists");
                node.add_content(STRING, strings);
                node.add_content(URL, urls);
                node.add_content(IMAGE, images);
                node.add_content(DOUBLE, doubles);
                node.add_content(LIST, lists);
                list.insert(node);
                list.writeIntoJson();
                resp.sendRedirect("/list?method=traverse");
                break;

            // read all contented and create a new node to replace
            case "update":
                System.out.println(req.getParameter("method"));
                System.out.println(req.getParameter("count"));
                int size = Integer.parseInt(req.getParameter("count"));

                // all objects are stored with the names which are their indexes in the ArrayList
                for(int i = 0; i <= size; i ++){
                    String object = req.getParameter(String.valueOf(i));

                    //handle empty object
                    if (object.length() == 0) continue;

                    // input with the format: Type:Content
                    String[] temp = object.split(":",2);
                    node.add_content(temp[0].toUpperCase(), temp[1]);
                }

                // Modify and write into json and display to user
                list.modify(name, node);
                list.writeIntoJson();
                resp.sendRedirect("/list?method=traverse");
                break;

            // search in current list
            case "search":
                name = req.getParameter("search_name");
                System.out.println(name.length());
                req.setAttribute("item", list.find(name));
                req.getRequestDispatcher("search.jsp").forward(req,resp);
                break;

            // rename current list and modify in the database
            case "rename":
                String new_name = req.getParameter("new_name");
                list.rename_to_json(new_name);
                resp.sendRedirect("/list");
        }


    }
}
