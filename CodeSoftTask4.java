import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.JSONObject;
import java.util.Scanner;

public class CurrencyConverter {
    private static final String API_URL = "https://api.apilayer.com/exchangerates_data/latest";
    private static final String API_KEY = "API_KEY"; 

    public static String getExchangeRates(String baseCurrency) throws IOException {
        URL url = new URL(API_URL + "?base=" + baseCurrency);
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
        connection.setRequestMethod("GET");
        connection.setRequestProperty("Authorization", "Bearer " + API_KEY);

        BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = reader.readLine()) != null) {
            response.append(line);
        }
        reader.close();

        connection.disconnect();

        return response.toString();
    }
}
 // You'll need to include the JSON library in your project

public class CurrencyConverter {
    // ... (previous code)

    public static double convertCurrency(double amount, String baseCurrency, String targetCurrency) throws IOException {
        String ratesJson = getExchangeRates(baseCurrency);
        JSONObject ratesObject = new JSONObject(ratesJson);
        double conversionRate = ratesObject.getJSONObject("rates").getDouble(targetCurrency);
        return amount * conversionRate;
    }
}

public class CurrencyConverter {

    public static void main(String[] args) {
        try {
            Scanner scanner = new Scanner(System.in);

            System.out.print("Enter the base currency ");
            String baseCurrency = scanner.next();

            System.out.print("Enter the target currency ");
            String targetCurrency = scanner.next();

            System.out.print("Enter the amount to convert: ");
            double amount = scanner.nextDouble();

            double convertedAmount = convertCurrency(amount, baseCurrency, targetCurrency);
            System.out.println("Converted amount: " + convertedAmount + " " + targetCurrency);
        } catch (IOException e) {
            System.err.println("Error fetching exchange rates: " + e.getMessage());
        }
    }
}
