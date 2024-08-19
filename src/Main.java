import javax.swing.SwingUtilities;

import config.App;
import config.Theme;
import models.Database;
import views.Window;

public class Main {

	public static void main(String[] args) {
		// Helps avoid unpredictable errors caused by Swing
		SwingUtilities.invokeLater(Main::run);
	}

	public static void run() {
		Theme.apply();
		Database database = new Database();
		Window.init("Library Management System", App.WIDTH, App.HEADING);
		LibraryController controller = new LibraryController(database);
	}
}