package com.cherry.logs;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.util.HashSet;
import java.util.Set;

public class ReadLogs {
    public static void ReadFiles() {
        try {
            JFileChooser f = new JFileChooser();
            f.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
            f.showOpenDialog(null);

            if (f.getSelectedFile() != null) {
                System.out.println("Chosen directory  : " + f.getSelectedFile());
                System.out.println();
            }

            File inputFolder = new File(f.getSelectedFile().getPath());
            Set<String> alreadyMetNames = new HashSet<>();
            File output = new File("result.htm");
            try (FileWriter fw = new FileWriter(output);
                 BufferedWriter bw = new BufferedWriter(fw)) {


                bw.write("<html>");
                bw.write("<head><link  type=\"text/css\" href=\"https://cdn.datatables.net/1.10.16/css/jquery.dataTables.min.css\" rel=\"stylesheet\"></link></head>");
                bw.write("<body>");
                bw.write("<h1>Logs</h1>");
                bw.write("<table id=\"myTable\" border=\"1\"><thead><tr><th>Sl No</th><th>Log Text</th></tr></thead><tbody>");

                int lineCount = 1;
                for (File file : inputFolder.listFiles()) {
                    try (FileReader fr = new FileReader(file);
                         BufferedReader br = new BufferedReader(fr)) {
                        String line;

                        // Start to parse the file at the first row containing data
                        while ((line = br.readLine()) != null) {
                            String[] country = line.split(",");

                            if (alreadyMetNames.add(country[5])) {// If the name has not already been met
                                String htmlLine = "<tr><td>" + lineCount + "</td><td>" + country[5] + "</td></tr>";
                                lineCount++;
                                bw.write(htmlLine);
                                bw.newLine();
                            }
                        }


                    }
                }
                bw.write("</tbody></table>");
                bw.write("</body>");
                bw.write("<script type=\"text/javascript\" charset=\"utf8\" src=\"https://code.jquery.com/jquery-3.3.1.min.js\" integrity=\"sha256-FgpCb/KJQlLNfOu91ta32o/NMZxltwRo8QtmkMRdAu8=\" crossorigin=\"anonymous\"></script>");
                bw.write("<script type=\"text/javascript\" charset=\"utf8\" src=\"https://cdn.datatables.net/1.10.16/js/jquery.dataTables.min.js\"></script>");
                bw.write("<script>$(document).ready(function(){$('#myTable').DataTable();});</script>");
                bw.write("</html>");


                Desktop.getDesktop().browse(output.toURI());
            }


            System.out.println("Your file is here : " + output.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();

            //If you didn't choose any directory
        } catch (NullPointerException ex) {
            System.err.println("Proper file hasn't been chosen");

           //For uncorrected files
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.err.println("You choose uncorrected file");
            System.out.println("Please, choose proper file");
        }
    }
}
