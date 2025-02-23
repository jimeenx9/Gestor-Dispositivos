import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static ArrayList<Dispositivo> listaDispositivos = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        cargarDatos();
        menúPrincipal();
    }

    private static void menúPrincipal() {
        int opcion;
        do {
            System.out.println("\nMENÚ PRINCIPAL");
            System.out.println("1. Añadir dispositivo");
            System.out.println("2. Mostrar dispositivos");
            System.out.println("3. Buscar dispositivo");
            System.out.println("4. Borrar dispositivo");
            System.out.println("5. Cambiar estado dispositivo");
            System.out.println("6. Modificar dispositivo");
            System.out.println("0. Salir");
            System.out.print("Seleccione una opción: ");
            opcion = scanner.nextInt();
            scanner.nextLine();
            
            switch (opcion) {
                case 1: añadirDispositivo(); break;
                case 2: mostrarDispositivos(); break;
                case 3: buscarDispositivo(); break;
                case 4: borrarDispositivo(); break;
                case 5: cambiarEstadoDispositivo(); break;
                case 6: modificarDispositivo(); break;
                case 0: System.out.println("Saliendo..."); break;
                default: System.out.println("Opción inválida.");
            }
        } while (opcion != 0);
    }

    private static void cargarDatos() {
        System.out.println("Cargando dispositivos desde ficheros...");
    }

    private static void añadirDispositivo() {
        System.out.print("Ingrese tipo (1=Ordenador, 2=Impresora): ");
        int tipo = scanner.nextInt();
        scanner.nextLine();
        
        System.out.print("Marca: ");
        String marca = scanner.nextLine();
        
        System.out.print("Modelo: ");
        String modelo = scanner.nextLine();
        
        System.out.print("Estado (true=funciona, false=no funciona): ");
        boolean estado = Boolean.parseBoolean(scanner.nextLine());
        
        Dispositivo dispositivo;
        if (tipo == 1) {
            System.out.print("RAM (GB): ");
            int ram = scanner.nextInt();
            scanner.nextLine();
            
            System.out.print("Procesador: ");
            String procesador = scanner.nextLine();
            
            System.out.print("Tamaño Disco (GB): ");
            int tamDisco = scanner.nextInt();
            scanner.nextLine();
            
            System.out.print("Tipo Disco (0=HDD, 1=SSD, 2=NVMe): ");
            int tipoDisco = scanner.nextInt();
            scanner.nextLine();
            
            dispositivo = new Ordenador(marca, modelo, estado, ram, procesador, tamDisco, tipoDisco);
        } else {
            System.out.print("Tipo (0=Láser, 1=Inyección de tinta): ");
            int tipoImpresora = scanner.nextInt();
            scanner.nextLine();
            
            System.out.print("¿Tiene color? (true/false): ");
            boolean color = Boolean.parseBoolean(scanner.nextLine());
            
            System.out.print("¿Tiene escáner? (true/false): ");
            boolean tieneScanner = Boolean.parseBoolean(scanner.nextLine());
            
            dispositivo = new Impresora(marca, modelo, estado, tipoImpresora, color, tieneScanner);
        }
        
        listaDispositivos.add(dispositivo);
        dispositivo.save();
        System.out.println("Dispositivo añadido correctamente.");
    }

    private static void mostrarDispositivos() {
        if (listaDispositivos.isEmpty()) {
            System.out.println("No hay dispositivos registrados.");
        } else {
            for (Dispositivo d : listaDispositivos) {
                System.out.println(d.toString().replace(". ", "\n"));
                System.out.println();

            }
        }
    }

    private static void buscarDispositivo() {
        System.out.print("Ingrese ID del dispositivo: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        for (Dispositivo d : listaDispositivos) {
            if (d.getId() == id) {
                System.out.println(d.toString().replace(". ", "\n"));
                System.out.println();

                return;
            }
        }
        System.out.println("Dispositivo no encontrado.");
    }

    private static void borrarDispositivo() {
        System.out.print("Ingrese ID del dispositivo a borrar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        for (Dispositivo d : listaDispositivos) {
            if (d.getId() == id) {
                d.delete();
                listaDispositivos.remove(d);
                System.out.println("Dispositivo eliminado.");
                return;
            }
        }
        System.out.println("Dispositivo no encontrado.");
    }

    private static void cambiarEstadoDispositivo() {
        System.out.print("Ingrese ID del dispositivo a modificar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        for (Dispositivo d : listaDispositivos) {
            if (d.getId() == id) {
                d.setEstado(!d.isEstado());
                d.save();
                System.out.println("Estado actualizado.");
                return;
            }
        }
        System.out.println("Dispositivo no encontrado.");
    }

    private static void modificarDispositivo() {
        System.out.print("Ingrese ID del dispositivo a modificar: ");
        int id = scanner.nextInt();
        scanner.nextLine();
        for (Dispositivo d : listaDispositivos) {
            if (d.getId() == id) {
                System.out.print("Nueva marca: ");
                d.setMarca(scanner.nextLine());
                System.out.print("Nuevo modelo: ");
                d.setModelo(scanner.nextLine());
                d.save();
                System.out.println("Dispositivo actualizado.");
                return;
            }
        }
        System.out.println("Dispositivo no encontrado.");
    }
}
