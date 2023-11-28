package com.stephen.entities;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;

/**
 * @author Stephen Chen
 * A custom TypeAdapter for serializing and deserializing Node objects to and from JSON using Gson.
 */
public class NodeTypeAdapter extends TypeAdapter<Node> {

    /**
     * Serializes a Node object to JSON using Gson.
     * @param out the JsonWriter object used for writing JSON
     * @param node the Node object to be serialized
     * @throws IOException if there is an error writing to the JsonWriter
     */
    @Override
    public void write(JsonWriter out, Node node) throws IOException {
        try{
            if (node.getName() == null){
                return;
            }
            out.beginObject();

            // write the name of the Node object
            out.name("name").value(node.getName());
            out.name("objects");
            System.out.println(node.getName());
            System.out.println(node.getSize());
            System.out.println(node.get_raw_objects() instanceof ArrayList<?>);
            System.out.println(node.toString());

            // write objects as an array of type:content pairs
            if (node.get_raw_objects() instanceof ArrayList<?>) {
                out.beginArray();
                for (Object obj : (ArrayList<?>) node.get_raw_objects()) {

                    // switched by object class
                    if (obj instanceof URL) {
                        out.beginObject().name("type").value("URL").name("content").value(((URL) obj).toExternalForm()).endObject();
                    } else if (obj instanceof Image) {
                        out.beginObject().name("type").value("IMAGE").name("content").value(((Image) obj).getSource()).endObject();
                    } else if (obj instanceof Double) {
                        out.beginObject().name("type").value("DOUBLE").name("content").value((Double) obj).endObject();}
                    else if (obj instanceof DoublyLinkedHashTable) {
                        out.beginObject().name("type").value("DOUBLYLINKEDHASHTABLE").name("content").value(((DoublyLinkedHashTable) obj).getName()).endObject();
                    } else {
                        out.beginObject().name("type").value("STRING").name("content").value((String) obj).endObject();
                    }
                }
                out.endArray();
            }
            out.endObject();
        }
        catch (IllegalStateException e){
            System.out.println("Wrong");
            System.out.println(node.toString());
            throw e;
        }
    }

    /**
     * Deserializes a Node object from JSON using Gson.
     * @param in the JsonReader object used for reading JSON
     * @return the deserialized Node object
     * @throws IOException if there is an error reading from the JsonReader
     */
    @Override
    public Node read(JsonReader in) throws IOException {
        String name = null;
        Object objects = null;
        in.beginObject();
        while (in.hasNext()) {

            // store name in a temp variable
            String key = in.nextName();
            if (key.equals("name")) name = in.nextString();

            // Store objects into an ArrayList
            else if (key.equals("objects")) {
                if (in.peek() == JsonToken.BEGIN_ARRAY) {
                    ArrayList<Object> list = new ArrayList<>();
                    in.beginArray();
                    while (in.hasNext()) {
                        in.beginObject();
                        String type = null;
                        String content = null;
                        while (in.hasNext()) {

                            // recognise the label in JSON
                            String innerKey = in.nextName();
                            if (innerKey.equals("type")) {
                                type = in.nextString();
                            } else if (innerKey.equals("content")) {
                                content = in.nextString();
                            }
                        }
                        Object obj = null;

                        // Add content to ArrayList
                        try {
                            switch (type) {
                                case "URL":
                                    obj = new URL(content);
                                    break;
                                case "IMAGE":
                                    obj = new Image(content);
                                    break;
                                case "DOUBLE":
                                    obj = Double.parseDouble(content);
                                    break;
                                case "DOUBLYLINKEDHASHTABLE":
                                    obj = new DoublyLinkedHashTable(content);
                                    break;
                                default:
                                    obj = content;
                            }
                            list.add(obj);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                        in.endObject();
                    }
                    in.endArray();
                    objects = list;
                }
            }
        }
        in.endObject();

        // Generate new Node and set Name
        Node node = new Node(objects);
        node.setName(name);
        System.out.println(node.toString());
        return node;
    }
}
