package com.example.SOAPwebservice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DivideArray {

//    public static void main(String[] args) {
//        int[] nums = {100, 100, 100, 100, 100, 100, 100, 100, 100}; // Array de elementos enteros
//        int k = 3; // Número de arrays en los que dividir
//
//        // Dividimos el array en k sub-arrays con la suma mínima
//        List<List<Integer>> result = splitArray(nums);
//
//        // Mostramos los k sub-arrays con suma mínima
//        for (List<Integer> arr : result) {
//            System.out.println("Sub-array: " + arr);
//        }
//    }

    public static List<SubBatch> splitArray(SubBatch subBatch) {
        File[] files = subBatch.files;

        int n = files.length;
        final int k = 3;

        // Creamos un array de k sub-arrays vacíos
        List<SubBatch> dividedSubBatch = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            SubBatch newSubBatch = new SubBatch(subBatch.subBatchID, subBatch.userID, new File[files.length]);
            dividedSubBatch.add(newSubBatch);
        }

        int[] a = new int[k];
        // Distribuimos los elementos del array en los sub-arrays
        for (int i = n - 1; i >= 0; i--) {
            File file = files[i];

            // Buscamos el sub-array con la suma más baja
            int minIndex = 0;
            int minSum = Integer.MAX_VALUE;
            for (int j = 0; j < k; j++) {
                int sum = sum(dividedSubBatch.get(j).files);
                if (sum < minSum) {
                    minSum = sum;
                    minIndex = j;
                }
            }

            // Agregamos el elemento al sub-array con la suma más baja
            dividedSubBatch.get(minIndex).files[a[minIndex]++] = file;
        }

        for (SubBatch batch : dividedSubBatch) {
            File[] batchFiles = batch.files;
            batchFiles = Arrays.stream(batchFiles).filter(s -> (s != null)).toArray(File[]::new);
            batch.files = batchFiles;
        }

        return dividedSubBatch;
    }

    public static int sum(File[] files) {
        int total = 0;
        for (File file : files)
            if (file != null)
                total += file.size;

        return total;
    }
}
