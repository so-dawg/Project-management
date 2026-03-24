import logic.Member;
import logic.Owner;
import logic.Project;
import logic.Task;
import logic.IUser;
import logic.UserRegistry;
import ui.AppFrame;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;

public class Main {

  public static void main(String[] args) {
    System.out.println("========================================");
    System.out.println("   PROJECT MANAGEMENT SYSTEM           ");
    System.out.println("========================================\n");

    System.out.println("Launching application...\n");

    // Launch the application
    SwingUtilities.invokeLater(() -> {
      AppFrame app = new AppFrame();
      app.setVisible(true);
    });

    System.out.println("========================================");
    System.out.println("       APPLICATION STARTED             ");
    System.out.println("========================================");
    System.out.println("\nTest accounts:");
    System.out.println("  Admin: admin@example.com / Password1!");
    System.out.println("  Member: john@example.com / Password1!\n");
  }

}
