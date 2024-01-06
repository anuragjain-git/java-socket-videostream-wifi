# Intranet based Video over Wi-Fi (VoWi-Fi) system within a single access point using socket programming

## Server (CameraServer): who wants to share his screen

1. ```ServerSocket``` is created on a specified Port. (we have used, 9999)

- The ServerSocket class is part of the Java standard library and is used to create a ```socket``` that listens for incoming connections from clients. It provides a way to establish a server on a specified port, waiting for clients to connect.

- A socket is a ```software endpoint``` that establishes a communication link between two processes running on a network.

- A software endpoint refers to a specific location in a software application where communication begins or terminates.

2. Once a client connects, a new Socket is created for communication with that client.

3. The sendVideo method continuously captures screen frames using the ```Robot class``` and sends them to the client over the socket.

- The Robot class is part of the java.awt package. In the context of the provided code, the Robot class is used to capture the screen.

4. The video frames are sent as JPEG images. The server converts the ```BufferedImage``` to a byte array using ImageIO.write.

- BufferedImage is a class that represents an image, it be converted to a byte array to represent the image data in a raw format

## Client (ClientViewer): who wants to view the shared screen

1. The client creates a Socket and connects to the server's IP address and port.

2. The receiveAndDisplayVideo method continuously receives the video frames from the server over the socket.

3. The received byte stream is converted back to a BufferedImage using ImageIO.read.

4. The BufferedImage is then displayed in a Swing JLabel within a JFrame.

- JLabel and JFrame are two Swing components commonly used for displaying text or image and creating the main window, managing the overall appearance and behaviorof an application.

5. The DataOutputStream on the server side writes the size of the image data followed by the actual image data, and the DataInputStream on the client side reads this information.





