package com.urbanise;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        Map<String, Set<String>> dependencies = new HashMap<>();
        Map<String, Set<String>> analysedDependencies = new HashMap<>();
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
            Set<String> allDependencies = dependencyAnalysis(dependencies, rootDependency);
            analysedDependencies.putIfAbsent(rootDependency, allDependencies);
        }


        for (String rootDependency: analysedDependencies.keySet()) {
            print(analysedDependencies, rootDependency);
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

    private static Set<String> dependencyAnalysis (Map<String, Set<String>> dependencies, String rootDependency){
        Set<String> analysedDependencies = dependencies.get(rootDependency);
        Set<String> subDependencies;

        for (String root: dependencies.keySet()) {
            if(root.equals(rootDependency)){
                continue;
            }
            subDependencies = dependencies.get(root);
            if(analysedDependencies.contains(root)) {
                analysedDependencies.addAll(subDependencies);
            }
        }
        return analysedDependencies;
    }

    private static void print(Map<String, Set<String>> dependencies, String rootDependency) {
        if (!dependencies.containsKey(rootDependency)) {
            return;
        }
        Set<String> subDependencies = dependencies.get(rootDependency);

        System.out.print(rootDependency + " ");

        for (String subDependency: subDependencies) {
            System.out.print(subDependency + " ");
        }
        System.out.println();
    }
    //        A B C
    //        B C E
    //        C G
    //        D A F
    //        E F
    //        F H
}
