package metro;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;


public class Main {
    public static void main(String[] args) throws UnsupportedEncodingException {

        String name = args[0];
//        name = "D:\\JMH\\HyperMetro\\HyperMetro\\task\\src\\metro\\examplefile.txt";
        StringBuilder list;
        try {
            list = ReadFile.toImport(name);
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

        String res = list.toString();


        Gson gson = new Gson();
        Type type = new TypeToken<Map<String, List<Station>>>() {
        }.getType();
        Map<String, List<Station>> myMap = gson.fromJson(res, type);

//        Collection<Map<Integer, Station>> values1 =
        List<Station> collect = new ArrayList<>();
        try {
            collect = myMap.values().stream().flatMap(r -> r.stream()).collect(Collectors.toList());
        } catch (NullPointerException e) {
            e.getMessage();
        }
//        long count =  myMap.values().stream().flatMap(r -> r.values().stream()).count();
//
//        System.out.println(count);
//        System.out.println(collect);

        List<Station> values = new ArrayList<>();
        Scanner scanner = new Scanner(System.in);
        String input = "";
        while (!input.equals("/exit")) {
            input = scanner.nextLine();
            String[] split = new String[5];

            if (input.contains("\"")) {
                split = Arrays.stream(input.split("\"+")).filter(x -> !x.equals(" ")).map(c -> c.trim()).toArray(size -> new String[size]);
            } else {
                split = Arrays.stream(input.split("\\s+")).map(c -> c.trim()).toArray(size -> new String[size]);

            }


            if (input.contains("/output")) {
                List<Station> metroLine = new LinkedList<>();

                for (Map.Entry<String, List<Station>> entry : myMap.entrySet()) {

                    if (entry.getKey().equals(split[1])) {
                        metroLine = new LinkedList<>(entry.getValue());
                        values = metroLine
                                .stream()
                                .collect(Collectors.toList());
                    }
                }

                mtd(values);
            }
            if (input.contains("/append")) {

                for (Map.Entry<String, List<Station>> entry : myMap.entrySet()) {

                    if (entry.getKey().equals(split[1])) {
                        List<Station> that = new LinkedList<>(entry.getValue());
                        entry.getValue().add(0, new Station(split[2]));
                    }
                }
            }
            if (input.contains("/add-head")) {
                for (Map.Entry<String, List<Station>> entry : myMap.entrySet()) {
                    if (entry.getKey().equals(split[1])) {
                        List<Station> that = new LinkedList<>(entry.getValue());
                        entry.getValue().add(new Station(split[2]));

                    }
                }

            }
            if (input.contains("/remove")) {

                for (Map.Entry<String, List<Station>> entry : myMap.entrySet()) {

                    if (entry.getKey().equals(split[1])) {

                        String stationName = split[2];
                        List<Station> removed = entry.getValue().stream().filter(s -> s.getName().equals(stationName)).collect(Collectors.toList());
                        entry.getValue().removeAll(removed);


                    }
                }

            }
            if (input.contains("/connect")) {
                for (Map.Entry<String, List<Station>> entry : myMap.entrySet()) {
                    if (entry.getKey().equals(split[1])) {
                        List<Station> that = new LinkedList<>(entry.getValue());
                        for (Station it : that) {
                            if (it.getName().equals(split[2])) {
                                Junction junction = new Junction(split[4], split[3]);
                                Junction[] addtransfer = new Junction[]{junction};
                                it.setTransfer(addtransfer);
                            }
                        }
                    }
                    if (entry.getKey().equals(split[3])) {
                        List<Station> that = new LinkedList<>(entry.getValue());
                        for (Station it : that) {
                            if (it.getName().equals(split[4])) {
                                Junction junction = new Junction(split[2], split[1]);
                                Junction[] addtransfer = new Junction[]{junction};
                                it.setTransfer(addtransfer);
                            }
                        }
                    }
                }

            }
            if (input.contains("/route")) {

                List<Node> nodes = new LinkedList<>();
                Set<String> keySet = myMap.keySet();
                List<String> summurized = new ArrayList<>();
                List<Node> transfered = new ArrayList<>();
                List<StationWithLine> totalStations = new ArrayList<>();

                for (Map.Entry<String, List<Station>> entry : myMap.entrySet()) {


                    List<Station> stationList = new ArrayList<>(entry.getValue());

                    List<StationWithLine> stationWithLines = stationList.stream().map(station -> {
                        StationWithLine stationWithLine =
                                new StationWithLine(station.getTransfer(), station.getName(), entry.getKey(), station.getTime(), station.getPrev(), station.getNext());
                        return stationWithLine;

                    }).collect(Collectors.toList());


                    List<String> collect1 = stationList.stream().map(v -> v.getName()).collect(Collectors.toList());

                    summurized.addAll(collect1);
                    totalStations.addAll(stationWithLines);
//                    + entry.getKey()
//                    System.out.println(collect1);

                    List <Node> nodesSmall = new ArrayList<>();
                    for (int i = 0; i < stationWithLines.size(); i++) {
                        int m = i;

                        Node nodeCurrent = new Node(stationWithLines.get(i));
                        nodesSmall.add(nodeCurrent);
                    }

                    for (int j = 0; j < nodesSmall.size(); j++) {

                        for (int k = 0; k < nodesSmall.size(); k++) {
                            List<String> previous = Arrays.stream(nodesSmall.get(k).getValue().getPrev()).collect(Collectors.toList());

                            List<String> next = Arrays.stream(nodesSmall.get(k).getValue().getNext()).collect(Collectors.toList());
                            if (previous.contains(nodesSmall.get(j).getValue().getName())) {
                                nodesSmall.get(k).getNeighbors().add(nodesSmall.get(j));
                            }
                            if (next.contains(nodesSmall.get(j).getValue().getName())) {
                                nodesSmall.get(k).getNeighbors().add(nodesSmall.get(j));
                            }
                        }
                    }

                    nodes.addAll(nodesSmall);
                }

                List<Node> collect2 = nodes.stream().filter(f -> f.getValue().getTransfer().length > 0).collect(Collectors.toList());
                transfered.addAll(collect2);


                for (int i = 0; i < nodes.size(); i++) {
                    for (int k = 0; k < transfered.size(); k++) {

                        nodes.get(i).getValue();
                        if (transfered.get(k).getValue().getTransfer()[0].getLine()
                                .equals(nodes.get(i).getValue().getLine())
                                && transfered.get(k).getValue().getTransfer()[0]
                                .getStation().equals(nodes.get(i).getValue().getName())) {
                            transfered.get(k).getNeighbors().add(nodes.get(i));
                            nodes.get(i).getNeighbors().add(transfered.get(k));
                        }
                    }

                }
//                for (Node it : transfered
//                ) {
//                    System.out.println(it.getNeighbors());
//                }

//                for (int i = 0; i < nodes.size(); i++) {
//                    System.out.println(nodes.get(i).getNeighbors());
//                }
//                System.out.println(totalStations);

                Node start = new Node(new StationWithLine());
                Node finish = new Node(new StationWithLine());
                for (int i = 0; i < nodes.size(); i++) {

                    if (nodes.get(i).getValue().getLine().equals(split[1]) && nodes.get(i).getValue().getName().equals(split[2])) {
                        start = nodes.get(i);

//                       && nodes.get(i).getValue().getName().equals(split[2])
                    }
                    if (nodes.get(i).getValue().getLine().equals(split[3]) &&
                            nodes.get(i).getValue().getName().equals(split[4])) {
                        finish = nodes.get(i);

                    }

                }

                search(finish, start);
            }

            if (input.contains("/fastest-route")) {

                List<NodeDij> nodes = new LinkedList<>();
                Set<String> keySet = myMap.keySet();
                List<String> summurized = new ArrayList<>();
                List<NodeDij> transfered = new ArrayList<>();
                List<StationWithLine> totalStations = new ArrayList<>();

                for (Map.Entry<String, List<Station>> entry : myMap.entrySet()) {


                    List<Station> stationList = new ArrayList<>(entry.getValue());

                    List<StationWithLine> stationWithLines = stationList.stream().map(station -> {
                        StationWithLine stationWithLine =
                                new StationWithLine(station.getTransfer(), station.getName(), entry.getKey(),  station.getTime(), station.getPrev(),station.getNext());
                        return stationWithLine;

                    }).collect(Collectors.toList());


                    List<String> collect1 = stationList.stream().map(v -> v.getName()).collect(Collectors.toList());

                    summurized.addAll(collect1);
                    totalStations.addAll(stationWithLines);
//                    + entry.getKey()
//                    System.out.println(collect1);
//
//                    for (int i = 0; i < stationWithLines.size(); i++) {
//
//                        stationWithLines.get(i).getName();
//                        NodeDij nodeCurrent = new NodeDij(stationWithLines.get(i));
//                        nodes.add(i, nodeCurrent);
//
//                        if (i != 0) {
//                            Integer time = 0;
//                            try {
//                                time = nodes.get(i - 1).getName().getTime();
//                            } catch (Exception e) {
//                                e.getMessage();
//                            }
//                            if (time == null) {
////                                System.out.println(nodes.get(i - 1).getName());
//                                nodeCurrent.addDestination(nodes.get(i - 1), 0);
//                            }
//                            if (time != null) {
//                                nodeCurrent.addDestination(nodes.get(i - 1), time);
//                            }
//                        }
//
//                    }

                    List <NodeDij> nodesSmall = new ArrayList<>();
                    for (int i = 0; i < stationWithLines.size(); i++) {
                        int m = i;

                        NodeDij nodeCurrent = new NodeDij(stationWithLines.get(i));
                        nodesSmall.add(nodeCurrent);
                    }

                    for (int j = 0; j < nodesSmall.size(); j++) {

                        for (int k = 0; k < nodesSmall.size(); k++) {
                            List<String> previous = Arrays.stream(nodesSmall.get(k).getName().getPrev()).collect(Collectors.toList());

                            List<String> next = Arrays.stream(nodesSmall.get(k).getName().getNext()).collect(Collectors.toList());
                            if (previous.contains(nodesSmall.get(j).getName().getName())) {
                                nodesSmall.get(k).getAdjacentNodes().put(nodesSmall.get(j),nodesSmall.get(j).getName().getTime());
                            }
                            if (next.contains(nodesSmall.get(j).getName().getName())) {
                                nodesSmall.get(k).getAdjacentNodes().put(nodesSmall.get(j),nodesSmall.get(k).getName().getTime());
                            }
                        }
                    }

                    nodes.addAll(nodesSmall);

//                    System.out.println(nodes.get(0).getNeighbors());


                }


//
                List<NodeDij> collect2 = nodes.stream().filter(f -> f.getName().getTransfer().length > 0).collect(Collectors.toList());
                transfered.addAll(collect2);


                for (int i = 0; i < nodes.size(); i++) {
                    for (int k = 0; k < transfered.size(); k++) {

                        nodes.get(i).getName();
                        if (transfered.get(k).getName().getTransfer()[0].getLine()
                                .equals(nodes.get(i).getName().getLine())
                                && transfered.get(k).getName().getTransfer()[0]
                                .getStation().equals(nodes.get(i).getName().getName())) {
                            transfered.get(k).getAdjacentNodes().put(nodes.get(i), 5);
                            nodes.get(i).getAdjacentNodes().put(transfered.get(k), 5);
                        }
                    }

                }
//                for (NodeDij it : transfered
//                ) {
//                    System.out.println(it.getAdjacentNodes());
//                }

//                for (int i = 0; i < nodes.size(); i++) {
//                    System.out.println(nodes.get(i).getAdjacentNodes());
//                }
//                System.out.println(totalStations);

                NodeDij start = new NodeDij(new StationWithLine());
                NodeDij finish = new NodeDij(new StationWithLine());
                for (int i = 0; i < nodes.size(); i++) {

                    if (nodes.get(i).getName().getLine().equals(split[1]) && nodes.get(i).getName().getName().equals(split[2])) {
                        start = nodes.get(i);

//                       && nodes.get(i).getValue().getName().equals(split[2])
                    }
                    if (nodes.get(i).getName().getLine().equals(split[3]) &&
                            nodes.get(i).getName().getName().equals(split[4])) {
                        finish = nodes.get(i);

                    }

                }

                String previousName = "";
                String previousLine = "";
                Graph.calculateShortestPathFromSource(start);
                for (NodeDij it :
                        finish.getShortestPath()) {
                    if (previousName.equals(it.getName().getName()) && !previousLine.equals(it.getName().getLine())) {
                        System.out.println("Transition to line " + it.getName().getLine());
                        System.out.println(it.getName().getName());
                    }
                    if (!previousName.equals(it.getName().getName())) {
                        System.out.println(it.getName().getName());
                    }
                    previousLine = it.getName().getLine();
                    previousName = it.getName().getName();
                }
                System.out.println(finish.getName().getName());
//                search(finish, start);
                System.out.printf("Total: %d minutes in the way", finish.getDistance());
            }

        }


    }


    public static List<String> mtd(List<Station> list) {
        List<String> list1 = new ArrayList<>();

        if (list.isEmpty()) {
            System.exit(0);
        }
        list1.add(0, "depot");
        for (int i = 0; i < list.size(); i++) {

            Junction[] transfer = list.get(i).getTransfer();
            List<String> lineStation = new LinkedList<>();
            if (transfer != null) {
                for (Junction it : transfer
                ) {
                    lineStation.add(it.getStation() + " (" + it.getLine() + ")");

                }
            }
            if (!lineStation.isEmpty()) {
                for (String lst :
                        lineStation) {


                    list1.add(list.get(i).getName() + " - " + lst);
                }
            } else {
                list1.add(list.get(i).getName());
            }

        }
        list1.add("depot");

        for (String item : list1
        ) {
            System.out.println(item);

        }
//        int m = 0;
//        while (m < list1.size()) {
//            for (int k = m; k < m + 3; k++) {
//                if (k < list1.size() && k != m + 2) {
//                    System.out.print(list1.get(k) + " - ");
//                }
//                if (k < list1.size() && k == m + 2) {
//                    System.out.print(list1.get(k));
//                    if (list1.get(k) == "depot") {
//                        m = list1.size();
//                    }
//                }
//            }
//            m++;
//            System.out.println();

        return list1;
    }

    public static int search(Node finish, Node start) {
        Map<Node, Node> parent = new HashMap<>();
        Queue<Node> queue = new ArrayDeque<>();
        queue.add(start);
        List<Node> alreadyVisited = new ArrayList<>();
        Node currentNode;
        Node first = new Node(new StationWithLine());
        parent.put(start, first);

        while (!queue.isEmpty()) {
            currentNode = queue.remove();


            if (currentNode.equals(finish)) {
                break;
//                return Optional.of(currentNode);
            } else {
                alreadyVisited.add(currentNode);
                String name = currentNode.getValue().getName();
                Optional<Node> first1 = currentNode.getNeighbors().stream().filter(s -> s.getValue().getName().equals(name)).findFirst();
                queue.addAll(currentNode.getNeighbors());

                for (Node child : currentNode.getNeighbors()
                ) {
                    if (!alreadyVisited.contains(child) & !parent.containsKey(child)) {
                        parent.put(child, currentNode);
                    }
                }
                if (first1.isPresent() && !alreadyVisited.contains(first1.get())) {
                    alreadyVisited.add(first1.get());
                }
                if (first1.isPresent()) {
                    for (Node child : first1.get().getNeighbors()
                    ) {
                        if (!alreadyVisited.contains(child) & !parent.containsKey(child)) {
                            parent.put(child, first1.get());
                        }
                    }
                }
                if (first1.isPresent()) {
                    queue.addAll(first1.get().getNeighbors());
                }
//                if(first1.isPresent()&&!alreadyVisited.contains(first1.get())){ queue.remove(first1.get());}
                queue.removeAll(alreadyVisited);
            }
        }

        Node current = finish;
        Node transitionCheck = new Node(new StationWithLine());

        int distance = 0;
        List<String> stations = new ArrayList<>();
        String lineFinish = finish.getValue().getLine();
        boolean check = false;

        while (parent.get(current) != first) {
            String line = current.getValue().getLine();

            if (!check && lineFinish.equals(line)) {

                stations.add(current.getValue().getName());
                current = parent.get(current);
                if (parent.get(current) == start) {
                    transitionCheck = current;
                }
                distance++;
            }

            if (!check && !lineFinish.equals(line)) {
                stations.add("Transition to line " + lineFinish);
                stations.add(current.getValue().getName());
                lineFinish = line;
                current = parent.get(current);
                if (parent.get(current) == start) {
                    transitionCheck = current;
                }
                distance++;
            }

        }
        if (start.getValue().getName().equals(transitionCheck.getValue().getName())
                && !start.getValue().getLine().equals(transitionCheck.getValue().getLine())) {
            stations.add("Transition to line " + transitionCheck.getValue().getLine());
            stations.add(start.getValue().getName());
        }
        if (!start.getValue().getName().equals(transitionCheck.getValue().getName())) {
            stations.add(start.getValue().getName());
        }

        stations.stream().collect(ArrayList::new,
                (list, e) -> list.add(0, e),
                (list1, list2) -> list1.addAll(0, list2)).forEach(System.out::println);
        return distance;
    }
}
