import java.io.*;
import javax.swing.*;

public class TextAreaOutputStream extends OutputStream {
	private JTextArea taConsole;
	
	public TextAreaOutputStream(JTextArea text) {
		taConsole = text;
	}
	
	
	public void write(int b) throws IOException{
		taConsole.append(String.valueOf((char) b));
	}
}
