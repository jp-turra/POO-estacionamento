package Utils;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Array;
import java.util.ArrayList;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import Classes.Marca;
import Classes.Modelo;

public class Utils {
    public Object getDatabaseObject(String ref) {
        String basePath =  System.getProperty("user.dir");
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            return gson.fromJson(new FileReader(basePath+"/src/Utils/"+ref+".json"), Object.class);
        } catch (Exception e) {
            System.out.println("\nArquivo não encontrado\n");
            return null;
        }
    }
    public ArrayList<Marca> getMarcas() {
        String basePath =  System.getProperty("user.dir");
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Marca[] arrayList = gson.fromJson(new FileReader(basePath+"/src/Utils/Marca.json"), Marca[].class);
            ArrayList<Marca> marcas = new ArrayList<Marca>();
            
            for (Marca m : arrayList) {
                marcas.add(m);
            }
            return marcas;
        } catch (Exception e) {
            System.out.println("\nArquivo não encontrado\n");
            return null;
        }
    }
    public ArrayList<Modelo> getModelos() {
        String basePath =  System.getProperty("user.dir");
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            Modelo[] arrayList = gson.fromJson(new FileReader(basePath+"/src/Utils/Modelo.json"), Modelo[].class);
            ArrayList<Modelo> modelos = new ArrayList<Modelo>();
            for (Modelo m : arrayList) {
                modelos.add(m);
            }
            return modelos;
        } catch (Exception e) {
            System.out.println("\nArquivo não encontrado\n");
            return null;
        }
    }

    public void setDatabaseObject(Object json, String ref) {
        String basePath =  System.getProperty("user.dir");
        try {
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            FileWriter file = new FileWriter(basePath+"/src/Utils/"+ref+".json");
            file.write(gson.toJson(json));
            file.close();
        } catch (Exception e) {
            System.out.println("\nErro ao salvar dados\n");
        }
    }
    
}
