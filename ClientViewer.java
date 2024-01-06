

import java.io.IOException;
import java.net.Socket;
import java.io.DataInputStream;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;

public class ClientViewer {
    private static final int PORT = 9999;
    private static final String SERVER_IP = "10.160.66.149"; // Replace with the actual IP address of the server

    public static void main(String[] args) {
        try {
            Socket socket = new Socket(SERVER_IP, PORT);
            System.out.println("Connected to server.");

            receiveAndDisplayVideo(socket);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void receiveAndDisplayVideo(Socket socket) throws IOException {
        DataInputStream dis = new DataInputStream(socket.getInputStream());

        JFrame frame = new JFrame("Video Viewer");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel label = new JLabel();
        frame.getContentPane().add(label);
        frame.pack();
        frame.setVisible(true);

        while (true) {
            int imageSize = dis.readInt();
            byte[] imageBytes = new byte[imageSize];
            dis.readFully(imageBytes, 0, imageSize);

            BufferedImage image = ImageIO.read(new ByteArrayInputStream(imageBytes));
            label.setIcon(new ImageIcon(image));
            frame.repaint();
        }
    }
}
