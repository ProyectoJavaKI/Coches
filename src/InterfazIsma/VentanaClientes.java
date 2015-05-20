package InterfazIsma;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.BoxLayout;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author Lynchaniano
 */
public class VentanaClientes extends JFrame  {

    private ConexionDB conexion = new ConexionDB();//La conexión con la base de datos

    //public boolean TablaCoches = true;//Para controlar la tabla a visualizar

    /*UN JPANEL PARA CONTENER TODOS LOS DEMÁS*/
    JPanel panelClientes;

    /*ESTO PARA EL FORMULARIO PARA INSERTAR CLIENTES*/
    //El DNI se añade automáticamente!!
    JPanel JPanelInsertar;//El Panel que contendrá todos los campos de insercción de datos
    JLabel JLabelCabeceraInsertar;//La cabecera del panelClientes
    JLabel JlabelNombreInsertar;
    JTextField JTextFieldNombreInsertar;
    JLabel JlabelApellidoInsertar;
    JTextField JTextFieldApellidoInsertar;
    JLabel JLabelCiudadInsertar;
    JTextField JTextFieldCiudadInsertar;
    JButton JButtonAceptarInsertar;
    JButton JButtonCancelarInsertar;
    String insertar;

    /*ESTO DEL FORMULARIO PARA MODIFICAR CLIENTES*/
    JComboBox JComboDNI;
    DefaultComboBoxModel ModeloDniModificar;
    JPanel JPanelModificar;
    JLabel JLabelCabeceraModificar;
    JLabel JLabelDNIModificar;
    JLabel JlabelNombreModificar;
    JTextField JTextFieldNombreModificar;
    JLabel JLabelApellidoModificar;
    JTextField JTextFieldApellidoModificar;
    JLabel JLabelCiudadModificar;
    JTextField JTextFieldCiudadModificar;
    JButton JButtonAceptarModificar;
    JButton JButtonResetearModificar;


    /*ESTO PARA EL COMBO DE ELIMINACIÓN DE CLIENTES*/
    JPanel JpanelEliminarCliente;
    JLabel JLabelCabeceraEliminar;
    JComboBox JComboEliminarCliente;
    DefaultComboBoxModel ModeloClienteEliminar;
    JButton JButtonEliminarClientes;

    /*ESTO ES PARA LA SECCIÓN DE LA TABLA CLIENTES*/
    JScrollPane JScrollPaneTabla;
    CustomDefaultTableModel ModeloTablaClientes;
    JTable Tabla_Clientes;

    /*Estos JLabel son para ajustar los campos en los GridLayouts*/
    JLabel RCarro, RCarro2, RCarro3;

    public VentanaClientes() {
        super();//Heredo el JFrame
        /*DEFINO LA VENTANA MADRE*/
        setSize(720, 380);//le doy altura y ancho a la ventana (JFrame)
        setTitle("GESTIÓN CLIENTES");//la titulo
        setResizable(false);//Evito que se pueda redimensionar la ventana
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);//Habilito el botón de cierre en la ventana
        setLocationRelativeTo(null);

        /*INICIALIZO UN PANEL CONTENEDOR GENERAL*/
        panelClientes = (JPanel) this.getContentPane();
        panelClientes.setLayout(new BorderLayout());//Border... para distribuir el resto de paneles.(EAST,WEST,NORTH,SOUTH,CENTER)

        /*----------------------------------------------------------------------------------------------------------------
         DIBUJO EL PANEL DE QUE ALMACENARÁ LA TABLA CON LOS DATOS DE LOS CLIENTES
         ------------------------------------------------------------------------------------------------------------------*/
        ModeloTablaClientes = new CustomDefaultTableModel();
        Tabla_Clientes = new JTable(ModeloTablaClientes);
        JScrollPaneTabla = new JScrollPane(Tabla_Clientes);
        panelClientes.add("Center", JScrollPaneTabla);//Lo situo al centro del BorderLayout
        ModeloTablaClientes.addColumn("DNI");
        ModeloTablaClientes.addColumn("NOMBRE");
        ModeloTablaClientes.addColumn("APELLIDO");
        ModeloTablaClientes.addColumn("CIUDAD");


        /*----------------------------------------------------------------------------------------------------------------
         DIBUJO EL PANEL DE INSERCIÓN Y SUS COMPONENTES
         ------------------------------------------------------------------------------------------------------------------*/
        JPanelInsertar = new JPanel();//Inicializo el panelClientes que contendrá el formulario
        JPanelInsertar.setLayout(new BoxLayout(JPanelInsertar, BoxLayout.Y_AXIS));
        JPanelInsertar.setBackground(Color.decode("#BEECE2"));//Le añado un color de fondo
        JPanelInsertar.setBorder(new EmptyBorder(10, 10, 10, 10));//Le pongo unos Borders para separarlos un poco de la ventana (JFrame).
        panelClientes.add("West", JPanelInsertar);//Lo situo a la izquierda del Panel General

        /*INSERTAMOS LOS CAMPOS DE INSERCIÓN*/
        JLabelCabeceraInsertar = new JLabel("INSERTAR");
        JLabelCabeceraInsertar.setFont(new Font("Arial", Font.ROMAN_BASELINE, 25));
        JLabelCabeceraInsertar.setForeground(Color.decode("#8A0808"));//Personaliza el color del botón por código RGB
        JPanelInsertar.add(JLabelCabeceraInsertar);

        RCarro3 = new JLabel(" ");
        JPanelInsertar.add(RCarro3);

        JlabelNombreInsertar = new JLabel("Nombre:");
        JPanelInsertar.add(JlabelNombreInsertar);

        JTextFieldNombreInsertar = new JTextField();
        JPanelInsertar.add(JTextFieldNombreInsertar);

        RCarro = new JLabel(" ");
        JPanelInsertar.add(RCarro);

        JlabelApellidoInsertar = new JLabel("Apellido:");
        JPanelInsertar.add(JlabelApellidoInsertar);

        JTextFieldApellidoInsertar = new JTextField();
        JPanelInsertar.add(JTextFieldApellidoInsertar);

        RCarro2 = new JLabel(" ");
        JPanelInsertar.add(RCarro2);

        JLabelCiudadInsertar = new JLabel("Ciudad:");
        JPanelInsertar.add(JLabelCiudadInsertar);

        JTextFieldCiudadInsertar = new JTextField();
        JPanelInsertar.add(JTextFieldCiudadInsertar);

         /*INSERTAMOS LOS BOTONES Y SU LISTENER*/
        JButtonAceptarInsertar = new JButton("ACEPTAR");
        JPanelInsertar.add(JButtonAceptarInsertar);
        JButtonCancelarInsertar = new JButton("RESET");
        JPanelInsertar.add(JButtonCancelarInsertar);

       /* ACTIVO EL LISTENER*/
        JButtonAceptarInsertar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    Inserta_Cliente();
                    LimpiarJTable();
                    Cargar_Tabla_Clientes();
//                    //Compruebo su validez con un método definido posteriormente
//                    if (compruebaValidez()) {
//                        JOptionPane.showMessageDialog(null,
//                                "Se realizó el alta correctamente", "OK", JOptionPane.INFORMATION_MESSAGE);
//
//                    } else {
//                        JOptionPane.showMessageDialog(null, "Los datos introducidos no son correctos", "Error", JOptionPane.ERROR_MESSAGE);
//                    }

                } catch (Exception err) {
                    JOptionPane.showMessageDialog(null, "", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        /* ACTIVO EL LISTENER*/ 
        JButtonCancelarInsertar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    JTextFieldNombreInsertar.setText(null);
                    JTextFieldApellidoInsertar.setText(null);
                    JTextFieldCiudadInsertar.setText(null);
                } catch (Exception err) {
                }
            }
        });

        /*----------------------------------------------------------------------------------------------------------------
         DIBUJO EL PANEL DE MODIFICACIÓN Y SUS COMPONENTES
         ------------------------------------------------------------------------------------------------------------------*/
        JPanelModificar = new JPanel();//Inicializo el panelClientes que contendrá el formulario
        JPanelModificar.setLayout(new BoxLayout(JPanelModificar, BoxLayout.Y_AXIS));
        JPanelModificar.setBackground(Color.decode("#C1E7EE"));//Le añado un color de fondo
        JPanelModificar.setBorder(new EmptyBorder(10, 10, 10, 10));//Le pongo unos Borders para separarlos un poco de la ventana (JFrame).
        panelClientes.add("East", JPanelModificar);//Lo situo a la derecha del Panel General

        /*INSERTAMOS LOS CAMPOS DE MODIFICACIÓN*/
        JLabelCabeceraModificar = new JLabel("MODIFICAR");
        JLabelCabeceraModificar.setFont(new Font("Arial", Font.ROMAN_BASELINE, 25));
        JLabelCabeceraModificar.setForeground(Color.decode("#8A0808"));//Personaliza el color del botón por código RGB
        JPanelModificar.add(JLabelCabeceraModificar);

        JLabelDNIModificar = new JLabel("DNI");
        JPanelModificar.add(JLabelDNIModificar);

        ModeloDniModificar = new DefaultComboBoxModel();
        JComboDNI = new JComboBox(ModeloDniModificar);
        JPanelModificar.add(JComboDNI);

        JlabelNombreModificar = new JLabel("Nombre:");
        JPanelModificar.add(JlabelNombreModificar);

        JTextFieldNombreModificar = new JTextField();
        JPanelModificar.add(JTextFieldNombreModificar);

        JLabelApellidoModificar = new JLabel("Apellido:");
        JPanelModificar.add(JLabelApellidoModificar);

        JTextFieldApellidoModificar = new JTextField();
        JPanelModificar.add(JTextFieldApellidoModificar);

        JLabelCiudadModificar = new JLabel("Ciudad:");
        JPanelModificar.add(JLabelCiudadModificar);

        JTextFieldCiudadModificar = new JTextField();
        JPanelModificar.add(JTextFieldCiudadModificar);

        /*INSERTO EL BOTÓN ACEPTAR MODIFICACIÓN*/
        JButtonAceptarModificar = new JButton("ACEPTAR");
        JPanelModificar.add(JButtonAceptarModificar);

        //* ACTIVO EL LISTENER*/
        JButtonAceptarModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    Modificar_Cliente();
                    LimpiarJTable();
                    Cargar_Tabla_Clientes();

                } catch (Exception err) {
                    JOptionPane.showMessageDialog(null, "", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        /*INSERTO EL BOTÓN RESET MODIFICACIÓN*/
        JButtonResetearModificar = new JButton("RESET");
        JPanelModificar.add(JButtonResetearModificar);

        /* ACTIVO EL LISTENER*/
        JButtonResetearModificar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    JTextFieldNombreModificar.setText(null);
                    JTextFieldApellidoModificar.setText(null);
                    JTextFieldCiudadModificar.setText(null);
                } catch (Exception err) {
                    JOptionPane.showMessageDialog(null, "", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        /*----------------------------------------------------------------------------------------------------------------
         DIBUJO EL PANEL DE ELIMINACIÓN Y SUS COMPONENTES
         ------------------------------------------------------------------------------------------------------------------*/
        JpanelEliminarCliente = new JPanel();//Inicializo el panelClientes en el que irán los botones
        JpanelEliminarCliente.setLayout(new FlowLayout());
        JpanelEliminarCliente.setBackground(Color.decode("#F78181"));
        JpanelEliminarCliente.setBorder(new EmptyBorder(10, 10, 10, 10));//Le pongo unos Borders para separarlos un poco de la ventana (JFrame).
        panelClientes.add("South", JpanelEliminarCliente);//Lo situo abajo del BorderLayout

        JLabelCabeceraEliminar = new JLabel("ELIMINAR");
        JLabelCabeceraEliminar.setFont(new Font("Arial", Font.ROMAN_BASELINE, 25));
        JLabelCabeceraEliminar.setForeground(Color.decode("#000000"));//Personaliza el color del botón por código RGB
        JpanelEliminarCliente.add(JLabelCabeceraEliminar);

        ModeloClienteEliminar = new DefaultComboBoxModel();

        JComboEliminarCliente = new JComboBox();
        JComboEliminarCliente.setModel(ModeloClienteEliminar);

        JpanelEliminarCliente.add(JComboEliminarCliente);
        /*INSERTO EL BOTÓN ELIMINAR Y SU LISTENER*/
        JButtonEliminarClientes = new JButton("ELIMINAR");
        JpanelEliminarCliente.add(JButtonEliminarClientes);
        /* ACTIVO EL LISTENER*/
        JButtonEliminarClientes.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                try {
                    Eliminar_Cliente();
                    LimpiarJTable();
                    Cargar_Tabla_Clientes();

                } catch (Exception err) {
                }
            }
        });
        
        /*POR ÚLTIMO, RELLENAMOS LA TABLA CON DATOS DE LA BASE*/
        Cargar_Tabla_Clientes();
    }
    /*MÉTODO PARA CARGAR DATOS AL JTABLE A TRAVÉS DE CONSULTA SQL
        APROVECHANDO EL RECORRIDO DE LAS TABLAS,TAMBIEN RELLENA LOS JCOMBOBOX*/
    void Cargar_Tabla_Clientes() {

        Connection miConexion;
        miConexion = (Connection) conexion.ConectarMysql();

        try (Statement st = miConexion.createStatement()) {
            String consulta = "SELECT * FROM `COCHES`.`CLIENTES`";
            ResultSet rs = st.executeQuery(consulta);
            Object[] fila = new Object[4];
            while (rs.next()) {
                fila[0] = (String) (rs.getObject("DNI"));
                fila[1] = (String) (rs.getObject("NOMBRE"));
                fila[2] = (String) (rs.getObject("APELLIDO"));
                fila[3] = (String) (rs.getObject("CIUDAD"));
                ModeloTablaClientes.addRow(fila); // Añade una fila al final de la tabla
                JComboEliminarCliente.addItem(fila[0]); // Añade una fila al final combo elimina
                JComboDNI.addItem(fila[0]); // Añade una fila al final del combo actualiza
            } //fin while
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(VentanaClientes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
     /*MÉTODO PARA INSERTAR DATOS EN LA BASE
        TAMBIÉN RESETEA EL JTABLE Y LOS JCOMOBOX PARA PODER ACTUALIZARLOS Y EVITAR DUPLICIDAD EN SU CONTENIDO */
    void Inserta_Cliente() {

        Connection miConexion = (Connection) conexion.ConectarMysql();

        try (Statement st = miConexion.createStatement()) {
            String consulta = "SELECT * FROM `COCHES`.`CLIENTES`";
            ResultSet rs = st.executeQuery(consulta);
            Object[] fila = new Object[1];
            while (rs.next()) {
                fila[0] = rs.getObject("DNI");
            }
            int ultimo_num = Integer.parseInt((String) fila[0]);

            ultimo_num++;
            insertar = "INSERT INTO `COCHES`.`CLIENTES`(`DNI`, `NOMBRE`,`APELLIDO`,`CIUDAD`)"
                    + " VALUES ('"
                    + "0" + ultimo_num + "', '"
                    + JTextFieldNombreInsertar.getText() + "', '"
                    + JTextFieldApellidoInsertar.getText() + "', '"
                    + JTextFieldCiudadInsertar.getText() + "')";
                    st.execute(insertar);
                    ModeloTablaClientes.setRowCount(0);
                    JComboEliminarCliente.removeAllItems();
                    JComboDNI.removeAllItems();
            st.close();
        } catch (SQLException ex) {
            Logger.getLogger(VentanaClientes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    /*MÉTODO PARA ELIMINAR DATOS DE LA BASE
        TAMBIÉN RESETEA EL JTABLE Y LOS JCOMBOBOX PARA PODER ACTUALIZAR SU CONTENIDO*/
    void Eliminar_Cliente(){
        Object[] opciones = {"Sí, Eliminar este cliente",  "No, no eliminar"};
        int respuestaUsuario = JOptionPane.showOptionDialog(this, "Va a eliminar un cohce ¿es ta seguro?: ",
        "Confirmar eliminación del Coche", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[1]);        
        if(respuestaUsuario == 0)
        {
            Connection miConexion = (Connection) conexion.ConectarMysql();
            String opt = (String) JComboEliminarCliente.getSelectedItem();
            try (Statement st = miConexion.createStatement()) {

                String insertar = "DELETE FROM `coches`.`clientes` WHERE `DNI` = '"+opt+"'";
                st.execute(insertar);
                ModeloTablaClientes.setRowCount(0);
                JComboEliminarCliente.removeAllItems();
                JComboDNI.removeAllItems();
                st.close();
            } 
            catch (SQLException ex) {
                Logger.getLogger(VentanaClientes.class.getName()).log(Level.SEVERE, null, ex);
                ModeloTablaClientes.setRowCount(0);
                JComboEliminarCliente.removeAllItems();
                JComboDNI.removeAllItems();
            }
        }
    }
        /*MÉTODO PARA MODIFICAR DATOS DE LA BASE
        TAMBIÉN RESETEA EL JTABLE Y LOS JCOMBOBOX PARA PODER ACTUALIZAR SU CONTENIDO*/
    void Modificar_Cliente(){
        Object[] opciones = {"Sí, actualizar datos de este cliente",  "No, no actualizar datos de este cliente"};
        int respuestaUsuario = JOptionPane.showOptionDialog(this, "Va a actualizar la informacion de un cohce ¿es ta seguro?: ",
        "Confirmar actualizacion del Coche", JOptionPane.YES_NO_CANCEL_OPTION, JOptionPane.QUESTION_MESSAGE, null, opciones, opciones[1]);        
        if(respuestaUsuario == 0)
        {
            Connection miConexion = (Connection) conexion.ConectarMysql();
            try (Statement st = miConexion.createStatement()) {

                String insertar = "UPDATE `coches`.`clientes` SET `NOMBRE`='"+JTextFieldNombreModificar.getText()+"',`APELLIDO`='"+JTextFieldApellidoModificar.getText()+"', `CIUDAD`='"+JTextFieldCiudadModificar.getText()+"' WHERE `DNI`='"+JComboDNI.getSelectedItem()+"'";
                st.executeUpdate(insertar);
                ModeloTablaClientes.setRowCount(0);
                JComboDNI.removeAllItems();
                JComboEliminarCliente.removeAllItems();
                st.close();
            } 
            catch (SQLException ex) {
                Logger.getLogger(VentanaClientes.class.getName()).log(Level.SEVERE, null, ex);
                ModeloTablaClientes.setRowCount(0);
                JComboEliminarCliente.removeAllItems();
                JComboDNI.removeAllItems();
            }
        }
    }
    /*RESETEAR DATOS DE JTABLE (Para poder insertar nuevos)*/

    void LimpiarJTable() {
        int a = ModeloTablaClientes.getRowCount();
        for (int i = 0; i < a; i++) {
            ModeloTablaClientes.removeRow(0);
        }
    }

    /*CREAMOS UN MODELO DE TABLA PERSONALIZADO PARA EVITAR QUE EL USUARIO INTERACTÚE CON LOS DATOS */
    public class CustomDefaultTableModel extends DefaultTableModel {

        public boolean isCellEditable(int row, int column) {
            return false;
        }

        public Class getColumnClass(int columna) {
            if (columna == 0) {
                return String.class; // en la bbdd es un Char(3) por eso pongo String
            }
            if (columna == 1) {
                return String.class;
            }
            if (columna == 2) {
                return String.class;
            }
            return Object.class;
        }
    }
  

  
}
