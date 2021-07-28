/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Plataforma.core;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URI;
import java.util.Properties;


/**
 *
 * @author Seven
 */
public class DataManager {
    
    //Armazena o atributo a ser lido"diretório do arquivo"
    private File file;
    //Classe responsavel pelo acesso as propriedades gravadas no arquivo"Save do game"
    Properties properties;

    public DataManager(String fileName) throws IOException {
        //cria um objeto properties
        properties = new Properties();
        //Cria o objeto file com o diretorio
        file = new File(fileName);
        //Verifica se firetorio ja existe,se existir ja abre ele
        if (file.exists()) {
            load();
        }
    }
    
    public DataManager(URI fileURI) throws  IOException{
        properties = new Properties();
        file = new File(fileURI);
        if(file.exists()){
            load();
        }
    }
    //Abre o diretorio desejado usando a classe que da acesso ao arquivo enviando como parametro o atributo file como caminho
    public void load() throws IOException {
        properties.load(new FileInputStream(file));
    }

    public void save() throws IOException {
        properties.store(new FileOutputStream(file), null);
    }
    
    public void write(String propertyName, String value){
        properties.setProperty(propertyName, value);
    }
    
    public void write(String propertyName, int value){
        properties.setProperty(propertyName, String.valueOf(value));
    }
    
    public void write(String propertyName, float value){
        properties.setProperty(propertyName, String.valueOf(value));
    }
    
    public void write(String propertyName, boolean value){
        properties.setProperty(propertyName, String.valueOf(value));
    }
    
    public String read(String propertyName, String field){
        return properties.getProperty(propertyName);
    }
    
    public int read (String propertyName, int field){
        return Integer.valueOf(properties.getProperty(propertyName));
    }
    
    public float read (String propertyName, float field){
        return Float.valueOf(properties.getProperty(propertyName));
    }
    
    public boolean read (String propertyName, boolean field){
        return Boolean.valueOf(properties.getProperty(propertyName));
    }
}
