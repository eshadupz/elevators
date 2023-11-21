import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

public class Main {
    private static Properties defaultProps;

    public static void main(String[] args) {
        // Read the property file to get simulation parameters.
        // You can use java.util.Properties for this.
        Properties properties = loadProperties(args);
        defaultProps = new Properties();
        try {
            defaultProps.load(new FileInputStream("/Users/eshadupuguntla/IdeaProjects/Elevators/src/default.properties"));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // Initialize the ElevatorSimulation with the parameters from the property file.
        ElevatorSimulation simulation = new ElevatorSimulation(properties);

        // Run the simulation.
        simulation.runSimulation();
    }

    private static Properties loadProperties(String[] args) {
        Properties props = new Properties();

        if (args.length > 0) {
            try (FileInputStream input = new FileInputStream(args[0])) {
                props.load(input);
            } catch (IOException e) {
                System.out.println("Error reading property file. Using default properties.");
            }
        } else {
            System.out.println("No property file specified. Using default properties.");
        }

        return props.isEmpty() ? defaultProps : props;
    }
}
