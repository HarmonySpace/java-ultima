package Client.src;

// a: librerias necesarias
// b: librerias para la manipulación de archivos
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;
// b: librerias para la interface gráfica
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
// b: librerias para eventos 
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


// a: comienzo de la clase Client
public class Client {
    /**
     * @param args
     */

    // b: commienza el main / debug
    public static void main(String[] args) {
        final File[] _archivoEnviar = new File[1];
        // c: interfaz
        // d: se define el marco
        JFrame marco = new JFrame("Practica de Java-Cliente");
        marco.setSize(400, 450);
        marco.setLayout(new BoxLayout(marco.getContentPane(), BoxLayout.Y_AXIS));
        marco.setDefaultCloseOperation(marco.EXIT_ON_CLOSE);
        // e: labels
        JLabel titulo = new JLabel("Puente para envio de datos");
        titulo.setFont(new Font("Arial", Font.BOLD, 25));
        titulo.setBorder(new EmptyBorder(20, 0, 0, 0));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
        /** Definimos la propiedad del nombre del Archivo */
        JLabel nombreArchivo = new JLabel("Escoge el archivo");
        nombreArchivo.setFont(new Font("Arial", Font.BOLD, 25));
        nombreArchivo.setBorder(new EmptyBorder(20, 0, 0, 0));
        nombreArchivo.setAlignmentX(Component.CENTER_ALIGNMENT);
        // e: panel de botones
        JPanel panel = new JPanel();
        panel.setBorder(new EmptyBorder(75, 0, 10, 0));
        // e: botón enviar
        JButton btEnviaArchivo = new JButton("Envia archivo");
        btEnviaArchivo.setPreferredSize(new Dimension(150, 75));
        btEnviaArchivo.setFont(new Font("Arial", Font.BOLD, 20));
        // e: botón escoger
        JButton btEscogeArchivo = new JButton("Escoger archivo");
        btEscogeArchivo.setPreferredSize(new Dimension(150, 75));
        btEscogeArchivo.setFont(new Font("Arial", Font.BOLD, 20));
        /// e: se añaden los botones al panel de botones
        panel.add(btEscogeArchivo);
        panel.add(btEnviaArchivo);
        
        // c: eventos
        // d: evento del botón escoger
        btEscogeArchivo.addActionListener(new ActionListener() {
            /*
             * (non-Javadoc)
             * 
             * @see
             * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
             * En esta parte es donde se escogerá el archivo que se enviará luego,
             * lo que se seleccione se almacenará en la variable _archivoEnviar.
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser jFileChooser = new JFileChooser();
                jFileChooser.setDialogTitle("Escoge un archivo");
                if (jFileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                    _archivoEnviar[0] = jFileChooser.getSelectedFile();
                    nombreArchivo.setText("El archivo que desea enviar es: " + _archivoEnviar[0].getName());
                }
            }
        });
        // d: evento del botón enviar
        btEnviaArchivo.addActionListener(new ActionListener() {
            /*
             * (non-Javadoc)
             * 
             * @see
             * java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
             * En esta parte se definirá lo que es el socket y así mismo verificará que
             * el archivo no esté vacío
             */
            @Override
            public void actionPerformed(ActionEvent e) {
                /* 
                 * Verificamos que el archivo no esté vacío de lo contrario se
                 * mostrará el mensaje en pantalla
                 */
                if (_archivoEnviar[0] == null) {
                    nombreArchivo.setText("Por favor escoja un archivo primero");
                } else {
                    try {
                        // d: Realizamos la lectura del archivo seleccionado
                        FileInputStream archivoRecibido = new FileInputStream(_archivoEnviar[0].getAbsolutePath());
                        // d: Inicializamos el socket desde el puerto 5003
                        Socket socket = new Socket("localhost", 5003);
                        // d: Permitiremos escribir los datos en un archivo de salida
                        DataOutputStream archivoSalida = new DataOutputStream(socket.getOutputStream());
                        // d: Obtenemos el nombre del archivo a enviar y su tamaño en bytes
                        String auxNombreArchivo = _archivoEnviar[0].getName();
                        byte[] auxNombreArchivoByte = auxNombreArchivo.getBytes();

                        byte[] contenidoArchivo = new byte[(int) _archivoEnviar[0].length()];
                        archivoRecibido.read(contenidoArchivo);

                        archivoSalida.writeInt(auxNombreArchivoByte.length);
                        archivoSalida.write(auxNombreArchivoByte);

                        archivoSalida.writeInt(contenidoArchivo.length);
                        archivoSalida.write(contenidoArchivo);

                    } catch (IOException err) {
                        err.printStackTrace();
                    }

                }

            }
        });
        // a: se agregan los componentes al marco
        marco.add(titulo);
        marco.add(nombreArchivo);
        marco.add(panel);
        marco.setVisible(true);
    }

}
