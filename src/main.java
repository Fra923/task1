import java.io.*;
import java.text.DecimalFormat;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class main {
    public static void main(String[] args) {
        String input_file;
        String output_file;
        String result = "";
        try {
            if (args.length > 1) {
                input_file = args[0];
                output_file = args[1];
            } else {
                throw new Exception("ERROR| missing arguments, input and output files must be provided");
            }
            result = processFile(input_file, result);
            writeFile(output_file, result);
            System.out.println("SUCCESS| Input file was processed successfully into the output file: "+ output_file);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String processFile(String input_file, String result) throws Exception {
        BufferedReader reader = new BufferedReader(new FileReader(input_file));
        String line = reader.readLine();
        while(line!=null){
            if(line.equals("=")) break;
            Pattern pattern = Pattern.compile("^(\\d+)[\\s]?([-+*/])[\\s]?(\\d+)$");
            Matcher matcher = pattern.matcher(line);
            if(matcher.find()){
                result = result+calculator(Double.parseDouble(matcher.group(1)),Double.parseDouble(matcher.group(3)),matcher.group(2), line);
            } else {
                result = result + "Error"+System.lineSeparator();
            }
            line = reader.readLine();
        }
        return result;
    }

    public static void writeFile(String output_file, String result) throws Exception {
        BufferedWriter writer = new BufferedWriter(new FileWriter(output_file));
        writer.write(result);
        writer.close();
    }

    public static String calculator(double a, double b, String operator, String line) throws Exception {
        String result_line = line + " = ";
        Double operation_result = 0D;
        DecimalFormat decimalFormat = new DecimalFormat("0.##########");
        switch (operator) {
            case "+":
                operation_result = a + b;
                break;
            case "-":
                operation_result = a - b;
                break;
            case "*":
                operation_result = a * b;
                break;
            case "/":
                operation_result = a / b;
                break;
            default: result_line = "ERROR";
        }
        if(!result_line.equals("ERROR")) result_line = result_line + decimalFormat.format(operation_result);
        return result_line+System.lineSeparator();
    }
}
