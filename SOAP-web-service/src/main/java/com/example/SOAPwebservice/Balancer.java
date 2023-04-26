package com.example.SOAPwebservice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Balancer {

    static final int TOTAL_NODES = 3;

    public static void main(String[] args) {
        File[] files = new File[6];
        files[0] = new File("1", "1", 1);
        files[0].size = 100;

        files[1] = new File("1", "1", 2);
        files[1].size = 13;

        files[2] = new File("1", "1", 3);
        files[2].size = 88;

        files[3] = new File("1", "1", 4);
        files[3].size = 91;

        files[4] = new File("1", "1", 5);
        files[4].size = 50;

        files[5] = new File("1", "1", 6);
        files[5].size = 72;

        SubBatch subBatch = new SubBatch("1","1",files);
        List<SubBatch> divided = divideSubBatch(subBatch);

        for (SubBatch subBatch1 : divided) {
            File[] files1 = subBatch1.files;
            System.out.println(Arrays.toString(files1));
        }
    }

    public static List<SubBatch> divideSubBatch(SubBatch subBatch) {
        File[] files = subBatch.files;

        int totalFiles = files.length;

        List<SubBatch> dividedSubBatch = new ArrayList<>();
        for (int i = 0; i < TOTAL_NODES; i++) {
            SubBatch newSubBatch = new SubBatch(subBatch.subBatchID, subBatch.userID, new File[files.length]);
            dividedSubBatch.add(newSubBatch);
        }

        int[] currentFileIdx = new int[TOTAL_NODES];
        // Distribuimos los elementos del array en los sub-arrays
        for (int i = totalFiles - 1; i >= 0; i--) {
            File file = files[i];

            // Buscamos el sub-array con la suma más baja
            int minIndex = 0;
            int minSum = Integer.MAX_VALUE;
            for (int j = 0; j < TOTAL_NODES; j++) {
                int sum = _sum(dividedSubBatch.get(j).files);
                if (sum < minSum) {
                    minSum = sum;
                    minIndex = j;
                }
            }

            // Agregamos el elemento al sub-array con la suma más baja
            dividedSubBatch.get(minIndex).files[currentFileIdx[minIndex]++] = file;
        }

        for (SubBatch batch : dividedSubBatch)
            batch.files = Arrays.stream(batch.files).filter(s -> (s != null)).toArray(File[]::new);

        return dividedSubBatch;
    }

    private static int _sum(File[] files) {
        int total = 0;
        for (File file : files)
            if (file != null)
                total += file.size;

        return total;
    }
}
