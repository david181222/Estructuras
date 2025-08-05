
package sounloud;

import java.awt.Desktop;
import java.net.URI;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Scanner;
import java.util.List;

import sounloud.model.Playlist;
import sounloud.model.Profile;
import sounloud.model.Queueplay;
import sounloud.model.User;
import sounloud.services.ReadSongs;
import sounloud.services.UserServer;
import sounloud.model.Song;

public class App {

    public static void main(String[] args) {
        UserServer usuarios = new UserServer();

        Scanner sc = new Scanner(System.in);
        List<Song> songs = new ArrayList<>();
        ReadSongs leerSongs = new ReadSongs();
        leerSongs.leerArchivo();
        songs = leerSongs.getReadSongs();

        Playlist play1 = new Playlist("DEATH", "De Kojima", songs);
        play1.addSong(new Song("The Man Who Sold The World", "Midge Ure", 5.43,
                "https://www.youtube.com/watch?v=mXHKjFKBC0g"));
       

        Profile profile1 = new Profile("DavidPorter", "Música de Death Stranding", "Rock");
        profile1.setPlaylist(play1);

        User user1 = new User("David");
        usuarios.addUser(user1);
        user1.setProfile(profile1);
        user1.showProfiles();

        Queueplay colaReproduccion = new Queueplay();
        colaReproduccion.addPlaylist(profile1.getPlaylist().get(0));

        
         /*System.out.println("Escriba su nombre de usuario:");
          usuarios.showUsers();
         String userSc = sc.nextLine();
           
          System.out.println("Seleccione uno de sus perfiles:");
          usuarios.getUser(userSc).showProfiles();
          int profileSc = Integer.parseInt(sc.nextLine())- 1;
          
          
          System.out.println("Seleccione una playlist");
          usuarios.getUser(userSc).getUserProfiles().get(profileSc).showPlaylists();
          int playlistSc = Integer.parseInt(sc.nextLine()) - 1;
          
          System.out.println("Seleccione una canción");
          usuarios.getUser(userSc).getUserProfiles().get(profileSc).getPlaylist().get(
          playlistSc).showSongs();
          int songSc = Integer.parseInt(sc.nextLine())- 1;
          Song selectedSong =
          usuarios.getUser(userSc).getUserProfiles().get(profileSc).getPlaylist().get(
          playlistSc).getSongs().get(songSc);
          colaReproduccion.startListeningSelectedSong(selectedSong);
         */

        //colaReproduccion.startListening();
         colaReproduccion.startListeningRandom();

    }
}