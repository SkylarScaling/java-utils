package com.wheezy.utils.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javafx.scene.control.TextArea;

public class BashRunner
{
  public static void run(String command, TextArea outputTextArea) throws IOException, InterruptedException
  {
    List<String> commands = new ArrayList<>();
    commands.add("bash");
    commands.add("-c");
    commands.add(command);

    ProcessBuilder builder = new ProcessBuilder(commands);
    
    Process process = builder.start();

    // Capture the output
    BufferedReader inputStream = new BufferedReader(new InputStreamReader(process.getInputStream()));
    BufferedReader errorStream = new BufferedReader(new InputStreamReader(process.getErrorStream()));

    String output;
    
    // Get the output of the process
    while ((output = inputStream.readLine()) != null)
    {
      // This will be displayed in the console...
      System.out.println(output);
      outputTextArea.appendText(output + "\n");
    }
    inputStream.close();

    // Same for errors, if any
    while ((output = errorStream.readLine()) != null)
    {
      System.out.println(output);
      outputTextArea.appendText(output + "\n");
    }
    errorStream.close();
    process.waitFor();
  }
}