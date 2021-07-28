/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Plataforma.core;

import java.applet.Applet;
import java.applet.AudioClip;
import java.net.URL;
import java.util.HashMap;
import javax.imageio.IIOException;

/**
 *
 * @author Seven
 */
public class AudioManager {
    static private AudioManager instance;
    private HashMap<String,AudioClip> clips;
    
    private AudioManager(){
        clips = new HashMap<String,AudioClip>();
    }
    
    static public AudioManager getInstance(){
        if (instance == null){
            instance = new AudioManager();
        }
        return instance;
    }
    
    public AudioClip loadAudio(String fileName) throws IIOException{
        URL url = getClass().getResource("/"+ fileName);
        if(url == null){
            throw new RuntimeException("Áudio /" + fileName + " não foi encontrado.");
        }else{
            if(clips.containsKey(url)){
                return clips.get(fileName);
            }else{
                AudioClip clip = Applet.newAudioClip(getClass().getResource("/"+fileName));
                clips.put(fileName, clip);
                return clip;
            }
        }
    }
}
