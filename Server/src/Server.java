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

    // b: inicia el main
    public static void main(String[] args) throws IOException {
        // c: interfaz
        // d: se define el marco o ventana
        int idArchivo = 0;
        JFrame marco = new JFrame("Practica de Java-Server");
        marco.setSize(400, 400);
        marco.setLayout(new BoxLayout(marco.getContentPane(), BoxLayout.Y_AXIS));
        marco.setDefaultCloseOperation(marco.EXIT_ON_CLOSE);

        // e: se define el panel
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));

        // e: scroll bar
        JScrollPane scroll = new JScrollPane(panel);
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // e: labels
        JLabel titulo = new JLabel("Recibidor de archivos");
        titulo.setFont(new Font("Arial", Font.BOLD, 25));
        titulo.setBorder(new EmptyBorder(20, 0, 10, 0));
        titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        // e: se añaden los componentes al marco
        marco.add(titulo);
        marco.add(scroll);
        marco.setVisible(true);
        
        
        // c: sockets
        // d: espera en el puerto 5003
        ServerSocket socketServidor = new ServerSocket(5003);

        // d: espera la conección de un cliente
        while (true) {
            try {
                // e: Se espera que el cliente se una al socket
                Socket socket = socketServidor.accept();
                // e: se obtiene la inforación del cliente
                DataInputStream archivoRecibido = new DataInputStream(socket.getInputStream());
                // e: se comprueba si se envió algo
                int tamanioArchivo = archivoRecibido.readInt();

                // e: si el cliente envió algo
                if (tamanioArchivo > 0) {
                    byte[] inforByte = new byte[tamanioArchivo];
                    archivoRecibido.readFully(inforByte, 0, inforByte.length);
                    String nombreArchivo = new String(inforByte);
                    // f: longitud del archivo
                    int longitudFile = archivoRecibido.readInt();
                    // f: si el archivo existe
                    if (longitudFile > 0) {
                        byte[] tamanioFile = new byte[longitudFile];
                        archivoRecibido.readFully(tamanioFile, 0, longitudFile);
                        // g: interfaz gráfica
                        // a: panel 
                        JPanel columnaArchivo = new JPanel();
                        columnaArchivo.setLayout(new BoxLayout(columnaArchivo, BoxLayout.Y_AXIS));
                        // a: labels
                        JLabel tituloArchivo = new JLabel(nombreArchivo);
                        tituloArchivo.setFont(new Font("Arial", Font.BOLD, 20));
                        tituloArchivo.setBorder(new EmptyBorder(10, 0, 10, 0));

                        // g: getfilextension function
                        if (getFilextension(nombreArchivo).equalsIgnoreCase("txt")) {
                            columnaArchivo.setName(String.valueOf(idArchivo));
                            columnaArchivo.addMouseListener(getMyMouseListener());
                            // a: se añade al marco
                            columnaArchivo.add(tituloArchivo);
                            panel.add(columnaArchivo);
                            marco.validate();
                        } else {
                            columnaArchivo.setName(String.valueOf(idArchivo));
                            columnaArchivo.addMouseListener(getMyMouseListener());
                            // a: se añade al marco
                            columnaArchivo.add(tituloArchivo);
                            panel.add(columnaArchivo);
                            marco.validate();
                        }
                        // g: se crea u objeto de Myfile
                        misArchivos.add(new Myfile(idArchivo, nombreArchivo, tamanioFile, getFilextension(nombreArchivo)));
                    }

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    // c: evento de mause
    public static MouseInputListener getMyMouseListener() {
        return new MouseInputListener() {
            // d: interfaz gráfica
            @Override
            // e: información del archivo
            public void mouseClicked(MouseEvent e) {
                JPanel panel = (JPanel) e.getSource();
                int idArchivo = Integer.parseInt(panel.getName());
                for (Myfile valor : misArchivos) {
                    if (valor.getId() == idArchivo) {
                        JFrame mostrarMarco = crearMarco(valor.getName(), valor.getData(), valor.getFilextension());
                        mostrarMarco.setVisible(true);
                    }
                }

            }
            // c: descargar el archivo
            private JFrame crearMarco(String nombreArchivo, byte[] fileData, String fileExtension) {
                // d: interfaz gráfica
                // e: marco
                JFrame marco = new JFrame("Descargar");
                marco.setSize(400, 400);
                // e: Panel
                JPanel panel = new JPanel();
                panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
                // e: labels
                JLabel titulo = new JLabel("¿Desea descargar este archivo");
                titulo.setAlignmentX(Component.CENTER_ALIGNMENT);
                titulo.setFont(new Font("Arial", Font.BOLD, 25));
                titulo.setBorder(new EmptyBorder(20, 0, 10, 0));
                JLabel prompt = new JLabel();
                prompt.setAlignmentX(Component.CENTER_ALIGNMENT);
                prompt.setFont(new Font("Arial", Font.BOLD, 25));
                prompt.setBorder(new EmptyBorder(20, 0, 10, 0));
                
                // e: botones
                JButton btSi = new JButton("SI");
                btSi.setPreferredSize(new Dimension(150, 75));
                JButton btNo = new JButton("NO");
                btNo.setPreferredSize(new Dimension(150, 75));
                JLabel labelArchivo = new JLabel();
                labelArchivo.setAlignmentX(Component.CENTER_ALIGNMENT);


                JPanel panelBotones = new JPanel();
                panelBotones.setBorder(new EmptyBorder(20, 0, 10, 0));
                panelBotones.add(btSi);
                panelBotones.add(btNo);

                if (fileExtension.equalsIgnoreCase("Ext")) {
                    labelArchivo.setText("<html>" + new String(fileData) + "</html>");

                } else {
                    labelArchivo.setIcon(new ImageIcon(fileData));
                }

                // f: si se oprime el boton si
                btSi.addActionListener((ActionListener) new ActionListener() {

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        File fileToDowload = new File(nombreArchivo);
                        try {
                            FileOutputStream fileOutputStream = new FileOutputStream(fileToDowload);
                            fileOutputStream.write(fileData);
                            fileOutputStream.close();

                            marco.dispose();
                        } catch (Exception err) {
                            err.printStackTrace();
                        }

                    }
                });
                // f: si no se oprimme el botón
                btNo.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        marco.dispose();
                    }
                });
                // f: se añaden los componentes al panel y luego al marco
                panel.add(titulo);
                panel.add(prompt);
                panel.add(labelArchivo);
                panel.add(panelBotones);
                marco.add(panel);

                return marco;
            };

            @Override
            public void mousePressed(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseReleased(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseExited(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseDragged(MouseEvent e) {
                // TODO Auto-generated method stub

            }

            @Override
            public void mouseMoved(MouseEvent e) {
                // TODO Auto-generated method stub

            }

        };
    }


    // b: functions
    // c: get file extension
    public static String getFilextension(String nombreArchivo) {
        int i = nombreArchivo.lastIndexOf('.');
        if (i > 0) {
            return nombreArchivo.substring(i + 1);
        } else {
            return "NO se encontro esa extension";
        }
    }

}