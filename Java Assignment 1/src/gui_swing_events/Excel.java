package gui_swing_events;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Collections;

public class Excel {
    private static ArrayList<Integer> numbers;

    public Excel() {
        numbers = new ArrayList<>();
    }

    public Excel(String input) {
        String[] strNumArray = input.split(" ");
        List<String> strNumList = new ArrayList<>(Arrays.asList(strNumArray));

        strNumList.forEach(item -> {
            try {
                double num = Double.parseDouble(item);
                numbers.add((int) num);
            } catch (NumberFormatException e) {
                throw new IllegalArgumentException("Invalid input: " + item);
            }
        });
    }

    public double findTotal() {
        double total = 0;
        for (int num : numbers) {
            total += num;
        }
        return total;
    }

    public double findAvg() {
        double total = findTotal();
        return total / numbers.size();
    }

    public double findMax() {
        return Collections.max(numbers);
    }

    public double findMin() {
        return Collections.min(numbers);
    }

    public static int translateToInt(String input) {
        try {
            return Integer.parseInt(input);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid input: " + input);
        }
    }
}
