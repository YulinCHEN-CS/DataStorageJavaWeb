package com.stephen.entities;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.google.gson.annotations.Expose;
import com.google.gson.reflect.TypeToken;

import java.io.*;
import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * @author Stephen Chen
 * This class represents a doubly linked hash table that stores nodes with a string name key.
 * The implementation is based on a hash map and a doubly linked list.
 */
public class DoublyLinkedHashTable {

    // The @Expose annotation specifies that these variables should be included in serialization and deserialization
    @Expose
    private final HashMap<String, Node> hash_map; //Hash map to store <nodeName, Node>
    private final Node head; // start of list traverse
    private final Node tail; // end of list traverse
    @Expose
    private String name; // list name
    private final String file_path = "src/main/resources/dataBase.json"; // database location

    /**
     * Constructor to create a new doubly linked hash table with the given name.
     * @param name the name of the hash table
     */
    public DoublyLinkedHashTable(String name){
        this.name = name;
        this.hash_map = new HashMap<>();
        this.head = new Node(null);
        this.head.setName("Head");
        this.tail = new Node(null);
        this.tail.setName("Tail");
        this.head.setNext(this.tail);
        this.tail.setPrev(this.head);
    }

    /**
     * Checks if the doubly linked hash table is empty.
     * @return true if the doubly linked hash table is empty, false otherwise
     */
    public boolean isEmpty(){
        return head.getNext() == this.tail;
    }
    /**
     * getters and setters
     */
    public String getName(){
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Node getHead() {
        return head;
    }

    public Node getTail() {
        return tail;
    }

    public HashMap<String, Node> getHash_map() {
        return hash_map;
    }

    /**
     * @return size of data stored excluding head and tail
     */
    public int getSize(){
        return hash_map.size();
    }

    /**
     * Finds the node with the given name key in the hash table.
     * @param key the name key of the node to find
     * @return the node with the given name key if it exists in the hash table, null otherwise
     */
    public Node find(String key){
        if (! isEmpty() && this.hash_map.containsKey(key)) return hash_map.get(key);
        return null;
    }

    /**
     * Inserts the given node into the hash table and doubly linked list.
     * @param new_node the node to insert
     * @return true if the insertion is successful, false otherwise (if the node already exists in the hash table)
     */
    public boolean insert(Node new_node){
        String key = new_node.getName();
        if(find(key) != null){
            System.out.println("already exist");
            return false;
        }
        else{
            hash_map.put(key, new_node);
            new_node.setPrev(this.tail.getPrev());
            new_node.setNext(this.tail);
            this.tail.getPrev().setNext(new_node);
            this.tail.setPrev(new_node);
        }
        return true;
    }

    /**
     * Removes the node with the given name key in the hash table.
     * @param key: the name of the node to find
     */
    public void remove(String key){
        Node node = find(key);
        if (node != null){
            node.getPrev().setNext(node.getNext());
            node.getNext().setPrev(node.getPrev());
            hash_map.remove(key);
        }
    }

    /**
     * Finds the node with the given name key in the hash table and replaces it with new_node.
     * @param key: the name of the node to modify
     * @param new_node: modified node
     */
    public void modify(String key, Node new_node){
        Node node = find(key);
        if (new_node == null){
            remove(key);
            return;
        }
        if (node == null) System.out.println("item not exists");
        else{
            new_node.setPrev(node.getPrev());
            new_node.setNext(node.getNext());
            node.getPrev().setNext(new_node);
            node.getNext().setPrev(new_node);
            hash_map.put(key, new_node);
        }
    }

    /**
     * Display the name of items in Doubly Linked Hash Table with order of insert to console
     */
    public void display_linked_list(){
        Node node = head.getNext();
        while(node != tail){
            System.out.println(node.getName());
            node = node.getNext();
        }
    }

    /**
     * Update the Doubly Linked Hash Table to JSON file, name exist->update, name doesn't exist->add
     * @throws IOException throws an IOException if there is an error with the file
     */
    public void writeIntoJson() throws IOException {

        // create a Gson object and specify the self-defined adapter,
        // set the fields that do not contain annotations, and the output format
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(DoublyLinkedHashTable.class, new DoublyLinkedHashTableTypeAdapter())
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();

        // Get the current Doubly Linked Hash Table data type and create a JsonObject object
        Type doublyLinkedHashTableType = new TypeToken<DoublyLinkedHashTable>(){}.getType();
        JsonObject jsonObject;

        // Determine whether the output file exists, if not, create a new JsonObject object,
        // otherwise read the content of the original file into the JsonObject object
        File file = new File(file_path);
        if (!file.exists()){
            jsonObject = new JsonObject();
        }
        else{
            String jsonContent = new String(java.nio.file.Files.readAllBytes(java.nio.file.Paths.get(file_path)));
            if (jsonContent.length() == 0) jsonObject = new JsonObject();
            else jsonObject = gson.fromJson(jsonContent, JsonObject.class);
        }
        this.display_linked_list();

        // Add the current doubly linked list data to the JsonObject object and convert it to JSON string format
        jsonObject.add(this.name, gson.toJsonTree(this, doublyLinkedHashTableType).getAsJsonObject());
        String jsonString = gson.toJson(jsonObject);
        System.out.println(gson.toJson(gson.toJsonTree(this, doublyLinkedHashTableType).getAsJsonObject()));

        // Output the JSON String to the specified file
        FileWriter fw = new FileWriter(file_path);
        fw.write(jsonString);
        fw.close();
    }
    /**
     * Update the name of this Doubly Linked Hash Table to JSON file
     * @param new_name new name of this Doubly Linked Hash Table
     * @throws IOException throws an IOException if there is an error with the file
     */
    public void rename_to_json(String new_name) throws IOException {

        // create a Gson object and specify the self-defined adapter,
        // set the fields that do not contain annotations, and the output format
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(DoublyLinkedHashTable.class, new DoublyLinkedHashTableTypeAdapter())
                .excludeFieldsWithoutExposeAnnotation()
                .setPrettyPrinting()
                .create();

        // read the JSON file into a JsonObject
        BufferedReader reader = new BufferedReader(new FileReader(file_path));
        JsonObject lists_in_json = gson.fromJson(reader, JsonObject.class);

        // check if the JsonObject contains the current DoublyLinkedHashTable object
        if (lists_in_json.has(this.getName())){

            // remove the current DoublyLinkedHashTable object from the JsonObject
            Type doublyLinkedHashTableType = new TypeToken<DoublyLinkedHashTable>(){}.getType();
            lists_in_json.remove(this.getName());

            // set the new name and add the current DoublyLinkedHashTable object back into the JsonObject
            this.setName(new_name);
            lists_in_json.add(new_name, gson.toJsonTree(this, doublyLinkedHashTableType).getAsJsonObject());

            // convert the JsonObject to a JSON string and write it to the JSON file
            String jsonString = gson.toJson(lists_in_json);
            FileWriter fw = new FileWriter(file_path);
            fw.write(jsonString);
            fw.close();
        }
    }

    /**
     * Remove this Doubly Linked Hash Table to JSON file
     * @throws IOException throws an IOException if there is an error with the file
     */
    public void remove_from_json() throws IOException {

        // set the output format
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .create();

        // read the JSON file into a JsonObject
        BufferedReader reader = new BufferedReader(new FileReader(file_path));
        JsonObject lists_in_json = gson.fromJson(reader, JsonObject.class);

        // remove the current object from the JsonObject if it exists
        if (lists_in_json.has(this.getName())){
            lists_in_json.remove(this.getName());

            // convert the updated JsonObject back to JSON string and write it back to the file
            String jsonString = gson.toJson(lists_in_json);
            FileWriter fw = new FileWriter(file_path);
            fw.write(jsonString);
            fw.close();
        }
    }
}
