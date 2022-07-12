// a: paquete Server
package Server.src;

// a: librerias necesarias
// b: componentes
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
// b: eventos
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
// b: manupulación de archivos
import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Array;
import java.lang.reflect.Field;
// b: sockets
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
// b: componentes gráficos
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.border.EmptyBorder;
import javax.swing.event.MouseInputListener;
import javax.swing.plaf.basic.BasicSliderUI.ComponentHandler;

// b: objetos
// c: Myfile
import Server.src.Myfile;

// a: comienzo de la clase Server
public class Server {
    static ArrayList<Myfile> misArchivos = new ArrayList<Myfile>();

}