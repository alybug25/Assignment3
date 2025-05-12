import java.util.*;

public class EVEMain {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        List<Map<String, String>> vehicles = new ArrayList<>(); //arraylist of all vehicles
        Map<String, String> currentVehicle = null;  //hashmap for each vehicle

        while (scanner.hasNextLine()) {
            String line = scanner.nextLine();

            //if line empty and cV not empty, assume end of that vehicle's info reached, add to arraylist, reset cV
            if (line.trim().isEmpty() && currentVehicle != null) {
                vehicles.add(currentVehicle);
                currentVehicle = null;

                continue; //end this iteration and goes onto next while-loop iteration to skip blank lines
            }

            //if cV is empty, make it a new hashmap to avoid null errors
            if (currentVehicle == null) {
                currentVehicle = new HashMap<>();
            }

            //parse k:v lines
            int colonIndex = line.indexOf(':');
            if (colonIndex == -1) continue; //skip lines without colon

            String key = line.substring(0, colonIndex).trim();
            String value = line.substring(colonIndex + 1).trim();

            //remove leading zeros for vehicle_id
            if (key.equals("vehicle_id")) {
                try {
                    value = String.valueOf(Integer.parseInt(value));
                } catch (NumberFormatException e) {
                    //if not a number, leave unchanged
                }
            }

            currentVehicle.put(key, value); //adds parse k:v pair to vehicle hashmap
        }

        //add last vehicle if not already added (case for very last vehicle)
        if (currentVehicle != null) {
            vehicles.add(currentVehicle);
        }

        //sort each vehicle by v (lambda exp for comparingInt by the value of vehicle_id parsed into integer form)
        vehicles.sort(Comparator.comparingInt(v -> Integer.parseInt(v.get("vehicle_id"))));

        //output standardised format, for each
        for (int i = 0; i < vehicles.size(); i++) {
            Map<String, String> vehicle = vehicles.get(i); //hashmap vehicle stores list of k:v pairs for i'th vehicle

            //print vehicle_id k:v pair first
            System.out.println("vehicle_id: " + vehicle.get("vehicle_id"));

            //print the rest of the keys in alphabetical order
            List<String> keys = new ArrayList<>(vehicle.keySet()); //arraylist of keys for i'th vehicle
            keys.remove("vehicle_id");
            Collections.sort(keys);

            for (String key : keys) {
                System.out.println(key + ": " + vehicle.get(key));
            }

            //print blank line between vehicles, but not after the last one
            if (i < vehicles.size() - 1) {
                System.out.println();
            }
        }

        scanner.close();
    }
}