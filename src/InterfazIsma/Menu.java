package InterfazIsma;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;


public class Menu {

    private static final VentanaCoches Coches = new VentanaCoches();
    private static final VentanaClientes Clientes = new VentanaClientes();
    private static final VentanaDistribucion Distribucion = new VentanaDistribucion();
    private static final VentanaVentas Ventas = new VentanaVentas();

    private static JFrame frame;

    private static JButton ButtonCoches, ButtonCliente, ButtonDistribucion, ButtonVentas, ButtonAbout;
    private static ImageIcon IconCoches, IconCliente, IconDistribucion, IconVentas, IconTotoro;

    private static JPanel VentanaMenu() {
        PanelTransparente panel = new PanelTransparente();

        panel.setBackgroundImage(panel.createImg("/Users/Lynchaniano/Documents/NetBeansJava/Coches/src/images/concesionario.jpg").getImage());

        ButtonCoches = new JButton();
        IconCoches = new ImageIcon("/Users/Lynchaniano/Documents/NetBeansJava/Coches/src/images/coche.jpg");
        ButtonCoches.setIcon(IconCoches);

        /* ACTIVO EL LISTENER*/
        ButtonCoches.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    Coches.setVisible(true);

                } catch (Exception err) {
                    JOptionPane.showMessageDialog(null, "", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(ButtonCoches);

        ButtonCliente = new JButton();
        IconCliente = new ImageIcon("/Users/Lynchaniano/Documents/NetBeansJava/Coches/src/images/cliente.jpg");
        ButtonCliente.setIcon(IconCliente);
        
        ButtonCliente.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    Clientes.setVisible(true);

                } catch (Exception err) {
                    JOptionPane.showMessageDialog(null, "", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(ButtonCliente);

        ButtonDistribucion = new JButton();
        IconDistribucion = new ImageIcon("/Users/Lynchaniano/Documents/NetBeansJava/Coches/src/images/distribucion.jpg");
        ButtonDistribucion.setIcon(IconDistribucion);
        
        ButtonDistribucion.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    
                    Distribucion.setVisible(true);

                } catch (Exception err) {
                    JOptionPane.showMessageDialog(null, "", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(ButtonDistribucion);

        ButtonVentas = new JButton();
        IconVentas = new ImageIcon("/Users/Lynchaniano/Documents/NetBeansJava/Coches/src/images/venta.jpg");
        ButtonVentas.setIcon(IconVentas);
        
        ButtonVentas.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    Ventas.setVisible(true);

                } catch (Exception err) {
                    JOptionPane.showMessageDialog(null, "", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(ButtonVentas);
        
        ButtonAbout = new JButton();
        IconTotoro = new ImageIcon("/Users/Lynchaniano/Documents/NetBeansJava/Coches/src/images/Totoro.jpg");
        ButtonAbout.setIcon(IconTotoro);
        
        ButtonAbout.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    
                    

                } catch (Exception err) {
                    JOptionPane.showMessageDialog(null, "", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        panel.add(ButtonAbout);

        return panel;
    }

    /**
     * Create the GUI and show it. For thread safety, this method should be
     * invoked from the event dispatch thread.
     */
    private static void createAndShowGUI() {

        // Create and set up the window.
        frame = new JFrame("MENÚ OPCIONES");
        
        frame.setResizable(false);//Evito que se pueda redimensionar la ventana
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Component contents = VentanaMenu();
        frame.getContentPane().add(contents, BorderLayout.CENTER);

        //Set window size
        frame.setPreferredSize(new Dimension(560, 348));

        // Display the window.
        frame.pack();
        frame.setLocationByPlatform(true);
        frame.setVisible(true);

    }

    public static void main(String[] args) {
  // Schedule a job for the event dispatch thread:
        // creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                createAndShowGUI();
            }
        });
    }
}
