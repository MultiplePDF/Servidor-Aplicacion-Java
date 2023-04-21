package com.example.SOAPwebservice;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class DivideArray {

    public static void main(String[] args) {
        int[] nums = {80,70,93,100,88,78}; // Array de elementos enteros
        int k = 3; // Número de arrays en los que dividir

        // Dividimos el array en k sub-arrays con la suma mínima
        List<List<Integer>> result = splitArray(nums);

        // Mostramos los k sub-arrays con suma mínima
        for (List<Integer> arr : result) {
            System.out.println("Sub-array: " + arr);
        }
    }

    public static List<List<Integer>> splitArray(int[] nums) {
        int n = nums.length;
        final int k = 3;
        // Calculamos la suma total del array
        int totalSum = 0;
        for (int num : nums) {
            totalSum += num;
        }

        // Calculamos el tamaño mínimo de cada sub-array
        int targetSum = totalSum / k;

        // Creamos un array de k sub-arrays vacíos
        List<List<Integer>> result = new ArrayList<>();
        for (int i = 0; i < k; i++) {
            result.add(new ArrayList<>());
        }

        // Distribuimos los elementos del array en los sub-arrays
        for (int i = n - 1; i >= 0; i--) {
            int num = nums[i];

            // Buscamos el sub-array con la suma más baja
            int minIndex = 0;
            int minSum = Integer.MAX_VALUE;
            for (int j = 0; j < k; j++) {
                int sum = sum(result.get(j));
                if (sum < minSum) {
                    minSum = sum;
                    minIndex = j;
                }
            }

            // Agregamos el elemento al sub-array con la suma más baja
            result.get(minIndex).add(num);
        }

        // Ordenamos los sub-arrays de menor a mayor
        for (int i = 0; i < k; i++) {
            Collections.sort(result.get(i));
        }

        return result;
    }

    public static int sum(List<Integer> arr) {
        int total = 0;
        for (int num : arr) {
            total += num;
        }
        return total;
    }
}
