/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication1;

/**
 *
 * @author sp21-bse-042
 */
// decorator/filedecorators/FileDecoratorDemo.java


public interface DataSource {
    void writeData(String data);
    String readData();
}

public class FileDataSource implements DataSource {
    private String filename;
    
    public FileDataSource(String filename) {
        this.filename = filename;
    }
    
    @Override
    public void writeData(String data) {
        System.out.println("Writing data to " + filename);
    }
    
    @Override
    public String readData() {
        System.out.println("Reading data from " + filename);
        return "Sample data";
    }
}

public abstract class DataSourceDecorator implements DataSource {
    protected DataSource wrappee;
    
    public DataSourceDecorator(DataSource source) {
        this.wrappee = source;
    }
    
    @Override
    public void writeData(String data) {
        wrappee.writeData(data);
    }
    
    @Override
    public String readData() {
        return wrappee.readData();
    }
}

public class EncryptionDecorator extends DataSourceDecorator {
    public EncryptionDecorator(DataSource source) {
        super(source);
    }
    
    @Override
    public void writeData(String data) {
        System.out.println("Encrypting data");
        super.writeData(data);
    }
    
    @Override
    public String readData() {
        String data = super.readData();
        System.out.println("Decrypting data");
        return data;
    }
}

public class CompressionDecorator extends DataSourceDecorator {
    public CompressionDecorator(DataSource source) {
        super(source);
    }
    
    @Override
    public void writeData(String data) {
        System.out.println("Compressing data");
        super.writeData(data);
    }
    
    @Override
    public String readData() {
        String data = super.readData();
        System.out.println("Decompressing data");
        return data;
    }
}

public class UTF8Decorator extends DataSourceDecorator {
    public UTF8Decorator(DataSource source) {
        super(source);
    }
    
    @Override
    public void writeData(String data) {
        System.out.println("Converting data to UTF-8 format");
        super.writeData(data);
    }
    
    @Override
    public String readData() {
        String data = super.readData();
        System.out.println("Converting data from UTF-8 format");
        return data;
    }
}

public class FileDecoratorDemo {
    public static void main(String[] args) {
        DataSource source = new FileDataSource("data.txt");
        
        // The target file gets written with compressed and encrypted data
        DataSource compressedEncryptedSource = new CompressionDecorator(
            new EncryptionDecorator(source));
        compressedEncryptedSource.writeData("Salary data");
        
        // Now with UTF-8 conversion
        DataSource utf8Source = new UTF8Decorator(
            new CompressionDecorator(
                new EncryptionDecorator(source)));
        utf8Source.writeData("UTF-8 encoded data");
    }
}
