//package cn_project;

import java.io.IOException;
import java.net.ConnectException;
import java.net.Socket;
import java.io.DataInputStream;
import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

public class ClientViewer {
	// should not be between 0 to 1023 (well known port), 1024- 49151 user/registered port
    private static final int PORT = 9998;
    private static final String SERVER_IP = "IP_ADDRESS"; // Replace with the actual IP address of the server

    public static void main(String[] args) {
        while(true) {
	    	try {
	            Socket socket = new Socket(SERVER_IP, PORT);
	            System.out.println("Connected to server.");
	            
	           	receiveAndDisplayVideo(socket);
	            
	        } catch (ConnectException ce) {
                // Handle connection timeout exception
                System.out.println("Connection timed out. Retrying...");
                
                // Wait for a while before attempting to reconnect
                try {
                    Thread.sleep(5000); // Adjust the sleep duration as needed
                } catch (InterruptedException ex) {
                    ex.printStackTrace();
                }
                
	        } catch (IOException  e) {
	            e.printStackTrace();
	        }
        }
    }

    private static void receiveAndDisplayVideo(Socket socket) throws IOException {
        DataInputStream dis = new DataInputStream(socket.getInputStream());

        JFrame frame = new JFrame("Video Viewer");
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        JLabel label = new JLabel();
        frame.getContentPane().add(label);
        frame.pack();
        frame.setVisible(true);

        while (true) {
            try {
                int imageSize = dis.readInt();
                byte[] imageBytes = new byte[imageSize];
                dis.readFully(imageBytes, 0, imageSize);

                BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
                label.setIcon(new ImageIcon(image));
                frame.repaint();
            } catch (IOException e) {
            	frame.setVisible(false);
            	// Connection closed by the server
                System.out.println("Connection closed by the server");
                break;
            }
        }
    }
}
