package com.urbanise;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        Map<String, Set<String>> dependencies = new HashMap<>();
        Scanner scanner = new Scanner(System.in);

        String line;
        while(true) {
            line = scanner.nextLine();
            if (line == null || line.trim().isEmpty()) {
                break;
            }
            String[] items = line.trim().split(" ");
            populateDependencies(dependencies, items);
        }

        for (String rootDependency: dependencies.keySet()) {
            System.out.print(rootDependency + " ");
            print(dependencies, rootDependency);
            System.out.println("");
        }
    }

    private static void populateDependencies(Map<String, Set<String>> dependencies, String[] items) {
        String rootDependency = items[0];
        dependencies.putIfAbsent(rootDependency, new HashSet<>());

        if (items.length > 1) {
            Set<String> newSubDependencies = Arrays.stream(items, 1, items.length)
                    .collect(Collectors.toSet());
            Set<String> subDependencies = dependencies.get(rootDependency);
            subDependencies.addAll(newSubDependencies);
        }
    }

    private static void print(Map<String, Set<String>> dependencies, String rootDependency) {
        if (!dependencies.containsKey(rootDependency)) {
            return;
        }

        Set<String> subDependencies = dependencies.get(rootDependency);


        for (String subDependency: subDependencies) {
            System.out.print(subDependency + " ");
            print(dependencies, subDependency);
        }

//        A B C
//        B C E
//        C G
//        D A F
//        E F
//        F H
    }
}
