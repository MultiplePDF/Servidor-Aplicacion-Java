package com.example.SOAPwebservice;

import java.io.Serializable;
import java.util.Arrays;

public class SubBatch implements Serializable {

    String subBatchID;
    String userID;
    File[] files;

    public SubBatch(String subBatchID, String userID, File[] files) {
        super();
        this.subBatchID = subBatchID;
        this.userID = userID;
        this.files = files;
    }

    @Override
    public String toString() {
        return
                "{\n" +
                        "	\"subBatchID\": \"" + subBatchID + "\",\n" +
                        "	\"userID\": \"" + userID + "\",\n" +
                        "	\"files\": " + Arrays.toString(files) + ",\n" +
                        "}\n";
    }

}