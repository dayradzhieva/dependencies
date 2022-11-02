package com.urbanise;

import java.util.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {
        Map<String, Set<String>> inputDependencies = readDependencies();

        System.out.println("----- DEPENDENCIES -----");

        analyseDependencies(inputDependencies);

        System.out.println("----- INVERSE DEPENDENCIES -----");

        analyseInverseDependencies(inputDependencies);

    }

    private static Map<String, Set<String>> readDependencies() {
        Map<String, Set<String>> inputDependencies = new HashMap<>();

        Scanner scanner = new Scanner(System.in);

        String line;
        while (true) {
            line = scanner.nextLine();
            if (line == null || line.trim().isEmpty()) {
                break;
            }
            String[] items = line.trim().split(" ");
            populateDependencies(inputDependencies, items);
        }
        return inputDependencies;
    }

    private static void populateDependencies(Map<String, Set<String>> inputDependencies, String[] items) {
        String rootDependency = items[0];
        inputDependencies.putIfAbsent(rootDependency, new HashSet<>());

        if (items.length > 1) {
            Set<String> newSubDependencies = Arrays.stream(items, 1, items.length)
                    .collect(Collectors.toSet());
            Set<String> subDependencies = inputDependencies.get(rootDependency);
            subDependencies.addAll(newSubDependencies);
        }
    }

    private static void analyseDependencies(Map<String, Set<String>> inputDependencies) {
        Map<String, Set<String>> analysedDependencies = new HashMap<>();
        for (String rootDependency : inputDependencies.keySet()) {
            analysedDependencies.putIfAbsent(rootDependency, getDependencies(inputDependencies, rootDependency));
        }

        for (String rootDependency : analysedDependencies.keySet()) {
            print(analysedDependencies, rootDependency);
        }
    }

    private static Set<String> getDependencies(Map<String, Set<String>> dependencies, String rootDependency) {
        Set<String> analysedDependencies = dependencies.get(rootDependency);

        for (String root : dependencies.keySet()) {
            if (root.equals(rootDependency)) {
                continue;
            }

            if (analysedDependencies.contains(root)) {
                analysedDependencies.addAll(dependencies.get(root));
            }
        }
        return analysedDependencies;
    }

    private static void analyseInverseDependencies(Map<String, Set<String>> inputDependencies) {
        Map<String, Set<String>> inverseDependencies = getInverseDependencies(inputDependencies);

        for (String rootDependency : inverseDependencies.keySet()) {
            print(inverseDependencies, rootDependency);
        }
    }

    private static Map<String, Set<String>> getInverseDependencies(Map<String, Set<String>> inputDependencies) {
        Map<String, Set<String>> inverseDependencies = new HashMap<>();

        for (String root : inputDependencies.keySet()) {
            inputDependencies.get(root)
                    .forEach((c) -> inverseDependencies.computeIfAbsent(c, k -> new HashSet<>()).add(root));
        }

        return inverseDependencies;
    }

    private static void print(Map<String, Set<String>> dependencies, String rootDependency) {
        if (!dependencies.containsKey(rootDependency)) {
            return;
        }
        Set<String> subDependencies = dependencies.get(rootDependency);

        System.out.print(rootDependency + " ");

        for (String subDependency : subDependencies) {
            System.out.print(subDependency + " ");
        }
        System.out.println();
    }
}
