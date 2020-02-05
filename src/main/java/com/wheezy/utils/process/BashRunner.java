package com.wheezy.utils.process;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

public class BashRunner
{
  /**
   * Creates a new process for executing a {@code command} and outputting the result to the {@code outputTextArea} in real time.
   * 
   * @param   command               Command to be executed
   * @param   outputTextArea        {@link javafx.scene.control.TextArea TextArea} to display the output
   * @throws  IOException
   * @throws  InterruptedException
   */
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
      if (Platform.isFxApplicationThread())
      {
        outputTextArea.appendText(output + "\n");
      }
      else
      {
        final String outputFinal = output;
        Platform.runLater(() -> outputTextArea.appendText(outputFinal + "\n"));
      }
    }
    inputStream.close();

    // Same for errors, if any
    while ((output = errorStream.readLine()) != null)
    {
      if (Platform.isFxApplicationThread())
      {
        outputTextArea.appendText(output + "\n");
      }
      else
      {
        final String outputFinal = output;
        Platform.runLater(() -> outputTextArea.appendText(outputFinal + "\n"));
      }
    }
    errorStream.close();
    process.waitFor();
  }
}