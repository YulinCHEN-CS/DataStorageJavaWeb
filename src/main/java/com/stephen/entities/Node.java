package com.stephen.entities;

import com.google.gson.annotations.Expose;

import java.net.URL;
import java.util.ArrayList;

/**
 * @author Stephen Chen
 * This class represents a Node that stores an ArrayList of Objects with a string name key.
 * Also store pointer to previous and next Node
 */
public class Node {

    // The @Expose annotation specifies that these variables should be included in serialization and deserialization
    @Expose
    private String name; // Node name
    @Expose
    private Object objects; // ArrayList to store objects or Null for head and tail Node
    private Node next; // Pointer to next Node
    private Node prev; // Pointer to previous Node

    /**
     * Constructor to create a node with given content.
     * @param object given content of node, ArrayList for normal Node and Null for head and tail Node
     */
    public Node(Object object){
        this.name = null;
        this.objects = object;
        this.next = null;
        this.prev = null;
    }
    /**
     * getters and setters
     */
    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return (String)name;
    }

    public Object get_raw_objects() {
        return objects;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public Node getNext() {
        return next;
    }

    public void setPrev(Node prev) {
        this.prev = prev;
    }

    public Node getPrev() {
        return prev;
    }

    /**
     * @return 0 for head and tail, else size of ArrayList
     */
    public int getSize(){
        if (objects == null) return 0;
        return ((ArrayList<?>)(objects)).size();
    }

    /**
     * Adds content to the Node object
     * @param type type of content("URL", "IMAGE", "DOUBLE", "LIST", default: "String")
     * @param content content to be added represented in String
     * @return true if success else false
     */
    public boolean add_content(String type, String content) {
        // If the content is Null, return false
        boolean success = false;

        if (objects instanceof ArrayList<?>){
            success = true;
            Object obj;
            if (content.length() ==0) return false;
            try{

                // Create new object according to the data type
                switch (type){
                    case "URL":
                        obj = new URL(content);
                        break;
                    case "IMAGE":
                        obj = new Image(content);
                        break;
                    case "DOUBLE":

                        // Change integer entered to double
                        try{
                            obj = Double.parseDouble(content);
                        }
                        catch (Exception e){
                            obj = (double) Integer.parseInt(content);
                        }
                        break;
                    case "LIST":
                        obj = new DoublyLinkedHashTable(content);
                        break;
                    default:

                        // as String
                        obj = content;
                        break;
                }

                // add to objects list
                ((ArrayList)(this.objects)).add(obj);
            }
            catch (Exception e){
                e.printStackTrace();
                success = false;
            }

        }
        return success;
    }

    /**
     * Erase all content in objects
     */
    public void empty_objects(){
        this.objects = null;
    }

    /**
     * String representation for debugging
     * @return the String representation of objects in a Node
     */
    public String toString(){
        // Process Null
        if (objects == null) return "";

        // Use String builder to connect String pieces
        StringBuilder results = new StringBuilder();
        for (Object item: (ArrayList)this.objects){

            // Create string form according to object type
            results.append(item.getClass().getSimpleName()).append(":");
            if (item instanceof URL) results.append(((URL) item).toExternalForm());
            else if (item instanceof Image) results.append((((Image) item).getSource()));
            else if(item instanceof Double) results.append(item);
            else if(item instanceof DoublyLinkedHashTable) results.append(((DoublyLinkedHashTable) item).getName());
            else results.append(((String) item));
            results.append(";");
        }
        return results.toString();
    }




}
