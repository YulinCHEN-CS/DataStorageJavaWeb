package com.stephen.entities;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * @author Stephen Chen
 * A custom TypeAdapter for serializing and deserializing DoublyLinkedHashTable objects to and from JSON using Gson.
 */
public class DoublyLinkedHashTableTypeAdapter extends TypeAdapter<DoublyLinkedHashTable> {
    /**
     * Serializes a DoublyLinkedHashTable object to JSON using Gson.
     * @param out the JsonWriter object used for writing JSON
     * @param list the DoublyLinkedHashTable object to be serialized
     * @throws IOException if there is an error writing to the JsonWriter
     */
    @Override
    public void write(JsonWriter out, DoublyLinkedHashTable list) throws IOException {
        if (list.getName() == null) {
            return;
        }
        out.beginObject();

        // write the name of the DoublyLinkedHashTable object
        out.name("name").value(list.getName());

        // write hash_map as an array of key:value pairs
        out.name("hash_map");
        out.beginArray();
        Node node = list.getHead().getNext();
        while(node != list.getTail()){
            String key = node.getName();
            out.beginObject();
            out.name("key").value(key);

            // use NodeTypeAdapter to write node class into json with insert order
            out.name("node");
            new NodeTypeAdapter().write(out, node);
            out.endObject();
            node = node.getNext();
        }
        out.endArray();
        out.endObject();
    }

    /**
     * Deserializes a DoublyLinkedHashTable object from JSON using Gson.
     * @param in the JsonReader object used for reading JSON
     * @return the deserialized DoublyLinkedHashTable object
     * @throws IOException if there is an error reading from the JsonReader
     */
    @Override
    public DoublyLinkedHashTable read(JsonReader in) throws IOException {

        // Process the null object
        if (in.peek() == JsonToken.NULL) {
            return null;
        }

        // create a new DoublyLinkedHashTable object
        DoublyLinkedHashTable doublyLinkedHashTable = new DoublyLinkedHashTable("");

        // read the JSON object and populate the DoublyLinkedHashTable object
        in.beginObject();
        while (in.hasNext()) {
            String name = in.nextName();
            if (name.equals("name")) {
                doublyLinkedHashTable.setName(in.nextString());
            } else if (name.equals("hash_map")) {
                in.beginArray();
                Node prev = doublyLinkedHashTable.getHead();
                String key;
                Node node = null;
                while (in.hasNext()) {
                    key = null;
                    node = null;
                    in.beginObject();
                    while (in.hasNext()) {
                        String fieldName = in.nextName();
                        if (fieldName.equals("key")) {
                            key = in.nextString();
                        }

                        // use NodeTypeAdapter to read node class into json and set order
                        else if (fieldName.equals("node")) {
                            node = new NodeTypeAdapter().read(in);
                            prev.setNext(node);
                            node.setPrev(prev);
                            prev = node;
                        } else {
                            in.skipValue();
                        }
                    }
                    in.endObject();

                    // Put the new pair into hash table
                    if (key != null) {
                        doublyLinkedHashTable.getHash_map().put(key, node);
                    }
                }
                node.setNext(doublyLinkedHashTable.getTail());
                doublyLinkedHashTable.getTail().setPrev(node);

                // display to console and check
                System.out.println("read");
                doublyLinkedHashTable.display_linked_list();
                in.endArray();
            } else {
                in.skipValue();
            }
        }
        in.endObject();
        return doublyLinkedHashTable;
    }
}
