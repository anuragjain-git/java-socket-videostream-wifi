
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.io.DataOutputStream;
import java.io.BufferedOutputStream;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import java.io.ByteArrayOutputStream;
import java.awt.*;
import java.awt.image.DataBufferByte;
import java.awt.image.WritableRaster;

public class CameraServer {
    private static final int PORT = 9999;
    private static final String SERVER_IP = "10.160.66.149"; // Replace with the actual IP address of the ClientViewer

    public static void main(String[] args) {
        try {
            ServerSocket serverSocket = new ServerSocket(PORT);
            System.out.println("Server waiting for client on port " + PORT);

            while (true) {
                Socket clientSocket = serverSocket.accept();
                System.out.println("Client connected: " + clientSocket.getInetAddress());

                try {
                    sendVideo(clientSocket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void sendVideo(Socket clientSocket) throws IOException {
        try {
            Robot robot = new Robot();
            Rectangle screenRect = new Rectangle(Toolkit.getDefaultToolkit().getScreenSize());

            DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(clientSocket.getOutputStream()));

            while (true) {
                BufferedImage screenshot = robot.createScreenCapture(screenRect);
                byte[] imageBytes = bufferedImageToByteArray(screenshot);

                dos.writeInt(imageBytes.length);
                dos.write(imageBytes, 0, imageBytes.length);
                dos.flush();

                try {
                    Thread.sleep(100); // Adjust the delay according to your needs
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    private static byte[] bufferedImageToByteArray(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "jpg", baos);
        return baos.toByteArray();
    }
}
