import java.io.*;

public class Dispositivo {
    private int id;
    private String marca;
    private String modelo;
    private boolean estado;
    private int tipo; // 0 = genérico, 1 = ordenador, 2 = impresora
    private boolean borrado;
    private int idAjeno;

    // Constructor que genera un nuevo ID automáticamente
    public Dispositivo(String marca, String modelo, boolean estado, int tipo, int idAjeno) {
        this.id = obtenerNuevoId();
        this.marca = marca;
        this.modelo = modelo;
        this.estado = estado;
        this.tipo = tipo;
        this.borrado = false;
        this.idAjeno = idAjeno;
    }

    // Constructor que solo asigna el ID
    public Dispositivo(int id) {
        this.id = id;
        this.marca = " ";
        this.modelo = " ";
        this.estado = true;
        this.tipo = 0;
        this.borrado = false;
        this.idAjeno = -1;
    }

    // Getters
    public int getId() { return id; }
    public String getMarca() { return marca; }
    public String getModelo() { return modelo; }
    public boolean isEstado() { return estado; }
    public int getTipo() { return tipo; }
    public boolean isBorrado() { return borrado; }
    public int getIdAjeno() { return idAjeno; }
    
    // Setters
    public void setMarca(String marca) { this.marca = marca; }
    public void setModelo(String modelo) { this.modelo = modelo; }
    public void setEstado(boolean estado) { this.estado = estado; }
    public void setTipo(int tipo) { this.tipo = tipo; }
    public void setBorrado(boolean borrado) { this.borrado = borrado; }
    public void setIdAjeno(int idAjeno) { this.idAjeno = idAjeno; }
    
    // toString
    @Override
    public String toString() {
        return "Marca: " + marca + ". Modelo: " + modelo + ". Estado: " + (estado ? "funciona" : "no funciona");
    }
    
    // Método para obtener el nuevo ID sumando 1 al mayor existente
    protected static int obtenerNuevoId() {
        File archivo = new File("dispositivos.dat");
        if (!archivo.exists()) {
            return 1; // Si el archivo no existe, el primer ID será 1
        }
    
        int maxId = 0;
        try (RandomAccessFile raf = new RandomAccessFile("dispositivos.dat", "r")) {
            while (raf.getFilePointer() < raf.length()) {
                int id = raf.readInt();
                raf.readUTF(); // Marca
                raf.readUTF(); // Modelo
                raf.readBoolean(); // Estado
                raf.readInt(); // Tipo
                raf.readBoolean(); // Borrado
                raf.readInt(); // idAjeno
                if (id > maxId) maxId = id;
            }
        } catch (IOException e) {
            System.out.println("Error al obtener el ID: " + e.getMessage());
        }
        return maxId + 1;
    }
    
    
    // Método para guardar el dispositivo en el archivo
// Método para actualizar o guardar el dispositivo en el archivo
public int save() {
    try (RandomAccessFile raf = new RandomAccessFile("dispositivos.dat", "rw")) {
        while (raf.getFilePointer() < raf.length()) {
            long pos = raf.getFilePointer(); // Guardamos la posición inicial
            int idLeido = raf.readInt();
            raf.readUTF(); // Marca
            raf.readUTF(); // Modelo
            raf.readBoolean(); // Estado
            raf.readInt(); // Tipo
            raf.readBoolean(); // Borrado
            raf.readInt(); // idAjeno

            if (idLeido == this.id) { // Si encontramos el mismo ID, actualizamos
                raf.seek(pos); // Volvemos a la posición inicial para sobrescribir
                raf.writeInt(id);
                raf.writeUTF(String.format("%-20s", marca));
                raf.writeUTF(String.format("%-20s", modelo));
                raf.writeBoolean(estado);
                raf.writeInt(tipo);
                raf.writeBoolean(borrado);
                raf.writeInt(idAjeno);
                return 0; // Éxito
            }
        }

        // Si no se encontró, lo añadimos al final (nuevo dispositivo)
        raf.seek(raf.length());
        raf.writeInt(id);
        raf.writeUTF(String.format("%-20s", marca));
        raf.writeUTF(String.format("%-20s", modelo));
        raf.writeBoolean(estado);
        raf.writeInt(tipo);
        raf.writeBoolean(borrado);
        raf.writeInt(idAjeno);
        return 0; // Éxito
    } catch (IOException e) {
        return 1; // Error
    }
}

    
    // Método para cargar un dispositivo desde el archivo
    public int load(int idBuscado) {
        try (RandomAccessFile raf = new RandomAccessFile("dispositivos.dat", "r")) {
            while (raf.getFilePointer() < raf.length()) {
                int idLeido = raf.readInt();
                String marcaLeida = raf.readUTF().trim();
                String modeloLeido = raf.readUTF().trim();
                boolean estadoLeido = raf.readBoolean();
                int tipoLeido = raf.readInt();
                boolean borradoLeido = raf.readBoolean();
                int idAjenoLeido = raf.readInt();

                if (idLeido == idBuscado) {
                    this.id = idLeido;
                    this.marca = marcaLeida;
                    this.modelo = modeloLeido;
                    this.estado = estadoLeido;
                    this.tipo = tipoLeido;
                    this.borrado = borradoLeido;
                    this.idAjeno = idAjenoLeido;
                    return 0; // Éxito
                }
            }
        } catch (IOException e) {
            return 1; // Error
        }
        return 1; // No encontrado
    }
    
    // Método para eliminar un dispositivo (marcar como borrado)
    public int delete() {
        try (RandomAccessFile raf = new RandomAccessFile("dispositivos.dat", "rw")) {
            while (raf.getFilePointer() < raf.length()) {
                long pos = raf.getFilePointer();
                int idLeido = raf.readInt();
                raf.seek(pos + 40); // Saltamos al campo borrado
                if (idLeido == this.id) {
                    raf.writeBoolean(true);
                    this.borrado = true;
                    return 0; // Éxito
                }
            }
        } catch (IOException e) {
            return 1; // Error
        }
        return 1; // No encontrado
    }
}
