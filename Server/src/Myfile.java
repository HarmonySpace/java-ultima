// a: paquete
package Server.src;

// a: clase Myfile
public class Myfile {
    // b: atributos
    private int id;
    private String name;
    private byte[] data;
    private String filextension;
    // b: constructor
    public Myfile(int id, String name, byte[] data, String filextension) {
        this.id = id;
        this.name = name;
        this.data = data;
        this.filextension = filextension;
    }

    // b: funciones gets y sets
    // c: id
    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }

    // c: name
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }

    // c: data
    public byte[] getData() {
        return data;
    }
    public void setData(byte[] data) {
        this.data = data;
    }
    
    // c: filextension
    public String getFilextension() {
        return filextension;
    }
    public void setFilextension(String filextension) {
        this.filextension = filextension;
    }
    
}
