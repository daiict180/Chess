import java.awt.*;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.util.*;

public class white {
	
	private static Socket socket;
    private final JPanel gui = new JPanel(new BorderLayout(3, 3));
    private static JButton[][] chessBoardSquares = new JButton[8][8];
    private JPanel chessBoard;
    static String chess[][] = new String[8][8];
    private JLabel message = new JLabel("White_Start Game!");
    private static final String COLS = "ABCDEFGH";
    static int z = 0;
    static int a = -1;
    static int b = -1;
    static String host ;
    static boolean click = false;
    
    white() {
        initializeGui();
    }

    public final void initializeGui() {
    	
    	JFrame frame = new JFrame();
        host = JOptionPane.showInputDialog(frame, "Enter server IP", "Chess Game", JOptionPane.QUESTION_MESSAGE);
    	
        // set up the main GUI
    	for(int i = 0 ; i < 8 ; i ++){
    		for(int j = 0; j < 8 ; j++){
    			chess[i][j] = "";
    		}
    	}
    	gui.setBorder(new EmptyBorder(5, 5, 5, 5));
        JToolBar tools = new JToolBar();
        tools.setFloatable(false);
        gui.add(tools, BorderLayout.PAGE_START);
        JButton b1 = new JButton("New Game");
        //JFrame frame = new JFrame();
        
        tools.addSeparator();
        tools.add(message);

        chessBoard = new JPanel(new GridLayout(0, 9));
        chessBoard.setBorder(new LineBorder(Color.BLACK));
        gui.add(chessBoard);

        // create the chess board squares
        Insets buttonMargin = new Insets(0,0,0,0);
        for (int ii = 0; ii < chessBoardSquares.length; ii++) {
            for (int jj = 0; jj < chessBoardSquares[ii].length; jj++) {
                JButton b = new JButton();
                b.setMargin(buttonMargin);
                // our chess pieces are 64x64 px in size, so we'll
                // 'fill this in' using a transparent icon..
                if ((jj % 2 == 1 && ii % 2 == 1) || (jj % 2 == 0 && ii % 2 == 0)) {
                    b.setBackground(Color.WHITE);
                    b.setForeground(Color.BLACK);
                } else {
                    b.setBackground(Color.GRAY);
                    b.setForeground(Color.WHITE);
                }
                chessBoardSquares[jj][ii] = b;
                chessBoardSquares[jj][ii].setActionCommand(""+jj+ii);
                chessBoardSquares[jj][ii].addActionListener(actionListener);
            }
        }
        
        chess[0][0] = "B_Rook";
        chess[7][0] = ("B_Rook");
        chess[1][0] = ("B_Knight");
        chess[6][0]=("B_Knight");
        chess[2][0]=("B_Bishop");
        chess[5][0]=("B_Bishop");
        chess[3][0]=("B_Queen");
        chess[4][0]=("B_King");
        chess[0][1]=("B_Pawn");
        chess[7][1]=("B_Pawn");
        chess[1][1]=("B_Pawn");
        chess[6][1]=("B_Pawn");
        chess[2][1]=("B_Pawn");
        chess[5][1]=("B_Pawn");
        chess[3][1]=("B_Pawn");
        chess[4][1]=("B_Pawn");
        
        
        chess[0][7]=("W_Rook");
        chess[7][7]=("W_Rook");
        chess[1][7]=("W_Knight");
        chess[6][7]=("W_Knight");
        chess[2][7]=("W_Bishop");
        chess[5][7]=("W_Bishop");
        chess[3][7]=("W_Queen");
        chess[4][7]=("W_King");
        chess[0][6]=("W_Pawn");
        chess[7][6]=("W_Pawn");
        chess[1][6]=("W_Pawn");
        chess[6][6]=("W_Pawn");
        chess[2][6]=("W_Pawn");
        chess[5][6]=("W_Pawn");
        chess[3][6]=("W_Pawn");
        chess[4][6]=("W_Pawn");
        
        chessimages();
        //fill the chess board
        chessBoard.add(new JLabel(""));
        // fill the top row
        for (int ii = 0; ii < 8; ii++) {
            chessBoard.add(
                    new JLabel(COLS.substring(ii, ii + 1),
                    SwingConstants.CENTER));
        }
        // fill the black non-pawn piece row
        for (int ii = 0; ii < 8; ii++) {
            for (int jj = 0; jj < 8; jj++) {
                switch (jj) {
                    case 0:
                        chessBoard.add(new JLabel("" + (ii + 1),
                                SwingConstants.CENTER));
                    default:
                        chessBoard.add(chessBoardSquares[jj][ii]);
                }
            }
        }
    }
    
    ActionListener actionListener = new ActionListener() {
        public void actionPerformed(ActionEvent actionEvent) {
            String position = (String) actionEvent.getActionCommand();
            b = position.charAt(0)-47;
            a = position.charAt(1)-47;
            z =1;
            click = true;
        }
    };
    
    public final JComponent getChessBoard() {
        return chessBoard;
    }

    public final JComponent getGui() {
        return gui;
    }

    public static void main(String[] args) {
        Runnable r = new Runnable() {

            @Override
            public void run() {
                white cb =
                        new white();

                JFrame f = new JFrame("Chess game using socket progamming");
                f.add(cb.getGui());
                f.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                f.setLocationByPlatform(true);

                // ensures the frame is the minimum size it needs to be
                // in order display the components within it
                f.pack();
                // ensures the minimum size is enforced.
                f.setMinimumSize(f.getSize());
                f.setVisible(true);
                
            }
        };
        SwingUtilities.invokeLater(r);
        playgame();
    }
    
    public static void playgame(){
    	Scanner s = new Scanner(System.in);
    	System.out.println("White's Board\n");
    	while(true){
        	
        	//White's Turn
    		int x = 1;
    	
    		String sendMessage = null;
    		
    		int c11,r11,c22,r22;
    		
    		while(z != 1){
    			System.out.println(z);
    		}
    		z = 0;
			c11 = b;
			r11 = a;
    		while(z != 1){
    			System.out.println(z);
    		}
    		z = 0;
			c22 = b;
			r22 = a;
    		String a1 = "" + c11+r11;
            String b1 = "" + c22+r22;
            //c11 = a1.charAt(0)-64;
        	//r11 = a1.charAt(1)-48;
        	//c22 = b1.charAt(0)-64;
        	//r22 = b1.charAt(1)-48;
            if(a1.length() == 2 && b1.length() == 2 && c11<9 && c22<9 && r11<9 && r22<9){
            	String p = chess[c11-1][r11-1];
            	if(p!="" && p.charAt(0) == 'W'){//decided according to turn
            		int check = 0;
            		if(p == "W_Pawn")
            			check = w_pawn(c11,r11,c22,r22);
            		else if(p == "W_Rook")
            			check = w_rook(c11,r11,c22,r22);
            		else if(p == "W_Knight")
            			check = w_knight(c11,r11,c22,r22);
            		else if(p == "W_Bishop")
            			check = w_bishop(c11,r11,c22,r22);
            		else if(p == "W_King")
            			check = w_king(c11,r11,c22,r22);
            		else if(p == "W_Queen")
            			check = Math.max(w_rook(c11,r11,c22,r22), w_bishop(c11,r11,c22,r22));
            		
            		if(check == 1){
            			if(chess[c22-1][r22-1] == "B_King")
                			JOptionPane.showMessageDialog(null, "White won");
            		String temp = chess[c22-1][r22-1];
            		chess[c11-1][r11-1]=("");
            		chess[c22-1][r22-1]=(p);
            		if(checkcondition() == 1){
            		chessimages();
            		x = 0;
            		sendMessage = a1 + " " + b1 + "\n";
            		}
            		else
            			{
            			chess[c11-1][r11-1] = p;
            			chess[c22-1][r22-1] = temp;
            			JOptionPane.showMessageDialog(null, "Check!");
            			}
            		}
            		else 
            			JOptionPane.showMessageDialog(null, "invalid move");
            	}
            	else JOptionPane.showMessageDialog(null, "invalid move");
            	}
            else{
            	JOptionPane.showMessageDialog(null, "invalid move");
            }
            
            
            if(x==0){
    		try
            { 
                int port = 25000;
                InetAddress address = InetAddress.getByName(host);
                socket = new Socket(address, port);
     
                //Send the message to the server
                OutputStream os = socket.getOutputStream();
                OutputStreamWriter osw = new OutputStreamWriter(os);
                BufferedWriter bw = new BufferedWriter(osw);
     
                bw.write(sendMessage);
                bw.flush();
                System.out.println("White : "+sendMessage);
     
                //Get the return message from the server
                InputStream is = socket.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader br = new BufferedReader(isr);
                String message = br.readLine();
                System.out.println("Black : " +message);
                
                int c1 = message.charAt(0)-48;
            	int r1 = message.charAt(1)-48;
            	int c2 = message.charAt(3)-48;
            	int r2 = message.charAt(4)-48;
            	String p = chess[c1-1][r1-1];
            	if(chess[c2-1][r2-1] == "W_King")
        			JOptionPane.showMessageDialog(null, "Black won");
            	chess[c1-1][r1-1]=("");
        		chess[c2-1][r2-1]=(p);
            	chessimages();
            	if(checkcondition() == 0)
            		JOptionPane.showMessageDialog(null, "Check!");
            }
            catch (Exception exception) 
            {
                exception.printStackTrace();
            }
    		
    		finally
            {
                //Closing the socket
                try
                {
                    //socket.close();
                }
                catch(Exception e)
                {
                    //e.printStackTrace();
                }
            }
            }
    		
        	//System.out.println("Black's turn");
            
            //}
            }
    }
    
    
    //Black pieces moving Constraints
    
    public static int b_king(int c1, int r1, int c2, int r2){
    	String p = chess[c2-1][r2-1];
    	if(Math.abs(c1-c2) < 2 && Math.abs(r1-r2) < 2 && (p=="" || p.contains("W")))
    		return 1;
    	else
    		return 0;
    }
    
    public static int b_bishop(int c1, int r1, int c2, int r2){
    	if(Math.abs(c2-c1) != Math.abs(r2-r1))
    		return 0;
    	int x =1;
    	int y =1;
    	if(r1>r2)
    		x = -1;
    	if(c1>c2)
    		y = -1;
    	
		for(int i = 1; i < Math.abs(r2-r1) ; i++){
			if(chess[(c1+(i*y))-1][(r1+(i*x))-1] != "")
				return 0;
		}
		if(chess[c2-1][r2-1].contains("B"))
			return 0;
    	if(Math.abs(c2-c1) == Math.abs(r2-r1))
    		return 1;
    	else
    		return 0;
    }
    
    public static int b_knight(int c1, int r1, int c2, int r2){  
    	String p = chess[c2-1][r2-1];
    	if(Math.abs(c2-c1) == 1 && Math.abs(r2-r1)==2 && (p=="" || p.contains("W")))
    		return 1;
    	else if(Math.abs(c2-c1)==2 && Math.abs(r2-r1)==1 && (p=="" || p.contains("W")))
    		return 1;
    	else return 0;
    }
    
    public static int b_pawn(int c1, int r1, int c2, int r2){
    	if(c1 == c2 && r2-r1 < 2 && chess[c2-1][r2-1] == "")
    		return 1;
    	else if(c1==c2 && r2-r1 <3 && chess[c2-1][r2-1] == "" && r1==2)
    		return 1;
    	else if(Math.abs(c2-c1) == 1 && r2 - r1 == 1 && chess[c2-1][r2-1] != "" && chess[c2-1][r2-1].charAt(0) == 'W')
    		return 1;
    	else return 0;
    }
    
    public static int b_rook(int c1, int r1, int c2, int r2){
    	if(r1 == r2){
    		int q = 1;
    		String p = chess[c2-1][r2-1];
    		if(c1>c2)
    		q = -1;
    		
    		for(int i = 1; i < Math.abs(c2-c1) ; i++){
    			if(chess[(c1+(i*q))-1][r1-1] != "")
    				return 0;
    		}
    		
    		if(p == "" || p.contains("B"))
    			return 1;
    		else
    			return 0;
    	}
    	else if(c1 == c2){
    		String p = chess[c2-1][r2-1];
    		int q = 1;
    		if(r1>r2)
    			q = -1;
    			
    		for(int i = 1; i < Math.abs(r2-r1) ; i++){
    			if(chess[c1-1][(r1+(i*q))-1] != "")
    				return 0;
    		}
    		
    		if(p == "" || p.contains("W"))
    			return 1;
    		else
    			return 0;
    		
    	}
    	else
    		return 0;
    }
    
    
    
//White pieces moving Constraints
    
    public static int w_king(int c1, int r1, int c2, int r2){
    	String p = chess[c2-1][r2-1];
    	if(Math.abs(c1-c2) < 2 && Math.abs(r1-r2) < 2 && (p=="" || p.contains("B")))
    		return 1;
    	else
    		return 0;
    }
    
    public static int w_bishop(int c1, int r1, int c2, int r2){
    	if(Math.abs(c2-c1) != Math.abs(r2-r1))
    		return 0;
    	int x =1;
    	int y =1;
    	if(r1>r2)
    		x = -1;
    	if(c1>c2)
    		y = -1;
    	
		for(int i = 1; i < Math.abs(r2-r1) ; i++){
			if(chess[(c1+(i*y))-1][(r1+(i*x))-1] != "")
				return 0;
		}
		if(chess[c2-1][r2-1].contains("W"))
			return 0;
    	if(Math.abs(c2-c1) == Math.abs(r2-r1))
    		return 1;
    	else
    		return 0;
    }
    
    public static int w_knight(int c1, int r1, int c2, int r2){  
    	String p = chess[c2-1][r2-1];
    	if(Math.abs(c2-c1) == 1 && Math.abs(r2-r1)==2 && (p=="" || p.contains("B")))
    		return 1;
    	else if(Math.abs(c2-c1)==2 && Math.abs(r2-r1)==1 && (p=="" || p.contains("B")))
    		return 1;
    	else return 0;
    }
    
    public static int w_pawn(int c1, int r1, int c2, int r2){
    	if(c1==c2 && r1-r2<2 && chess[c2-1][r2-1] == "")
    		return 1;
    	else if(c1==c2 && r1-r2 <3 && chess[c2-1][r2-1] == "" && r1==7)
    		return 1;
    	else if(Math.abs(c2-c1)==1 && r1-r2==1 && chess[c2-1][r2-1] != "" && chess[c2-1][r2-1].charAt(0) == 'B')
    		return 1;
    	else return 0;
    }
    
    public static int w_rook(int c1, int r1, int c2, int r2){
    	if(r1 == r2){
    		int q = 1;
    		String p = chess[c2-1][r2-1];
    		if(c1>c2)
    		q = -1;
    		
    		for(int i = 1; i < Math.abs(c2-c1) ; i++){
    			if(chess[(c1+(i*q))-1][r1-1] != "")
    				return 0;
    		}
    		if(p == "" || p.contains("B"))
    			return 1;
    		else
    			return 0;
    	}
    	else if(c1 == c2){
    		String p = chess[c2-1][r2-1];
    		int q = 1;
    		if(r1>r2)
    			q = -1;
    			
    		for(int i = 1; i < Math.abs(r2-r1) ; i++){
    			if(chess[c1-1][(r1+(i*q))-1] != "")
    				return 0;
    		}
    		if(p == "" || p.contains("B"))
    			return 1;
    		else
    			return 0;
    	}
    	else
    		return 0;
    }
    
    
    public static void chessimages(){
    	ImageIcon P_B_W = new ImageIcon("P_B_W.PNG");
    	ImageIcon KI_B_W = new ImageIcon("KI_B_W.PNG");
    	ImageIcon Q_B_W = new ImageIcon("Q_B_W.PNG");
    	ImageIcon R_B_W = new ImageIcon("R_B_W.PNG");
    	ImageIcon K_B_W = new ImageIcon("K_B_W.PNG");
    	ImageIcon B_B_W = new ImageIcon("B_B_W.PNG");
    	ImageIcon P_B_B = new ImageIcon("P_B_B.PNG");
    	ImageIcon KI_B_B = new ImageIcon("KI_B_B.PNG");
    	ImageIcon Q_B_B = new ImageIcon("Q_B_B.PNG");
    	ImageIcon R_B_B = new ImageIcon("R_B_B.PNG");
    	ImageIcon K_B_B = new ImageIcon("K_B_B.PNG");
    	ImageIcon B_B_B = new ImageIcon("B_B_B.PNG");
    	
    	ImageIcon P_W_W = new ImageIcon("P_W_W.PNG");
    	ImageIcon KI_W_W = new ImageIcon("KI_W_W.PNG");
    	ImageIcon Q_W_W = new ImageIcon("Q_W_W.PNG");
    	ImageIcon R_W_W = new ImageIcon("R_W_W.PNG");
    	ImageIcon K_W_W = new ImageIcon("K_W_W.PNG");
    	ImageIcon B_W_W = new ImageIcon("B_W_W.PNG");
    	ImageIcon P_W_B = new ImageIcon("P_W_B.PNG");
    	ImageIcon KI_W_B = new ImageIcon("KI_W_B.PNG");
    	ImageIcon Q_W_B = new ImageIcon("Q_W_B.PNG");
    	ImageIcon R_W_B = new ImageIcon("R_W_B.PNG");
    	ImageIcon K_W_B = new ImageIcon("K_W_B.PNG");
    	ImageIcon B_W_B = new ImageIcon("B_W_B.PNG");
    	
    	for(int i = 0 ; i < 8 ; i ++){
    		for(int j = 0 ; j < 8 ; j++){
    			if(chess[i][j].equals("W_Pawn")){
    				if(chessBoardSquares[i][j].getBackground().getRGB() == -8355712)
    					chessBoardSquares[i][j].setIcon(P_W_B);
    				if(chessBoardSquares[i][j].getBackground().getRGB() == -1)
    					chessBoardSquares[i][j].setIcon(P_W_W);
    			}
    				
    			if(chess[i][j].equals("W_Bishop")){
    				if(chessBoardSquares[i][j].getBackground().getRGB() == -8355712)
    					chessBoardSquares[i][j].setIcon(B_W_B);
        			if(chessBoardSquares[i][j].getBackground().getRGB() == -1)
        				chessBoardSquares[i][j].setIcon(B_W_W);
    			}
    			
    			if(chess[i][j].equals("W_King")){
    				if(chessBoardSquares[i][j].getBackground().getRGB() == -8355712)
    					chessBoardSquares[i][j].setIcon(KI_W_B);
        			if(chessBoardSquares[i][j].getBackground().getRGB() == -1)
        				chessBoardSquares[i][j].setIcon(KI_W_W);
    			}
    			
    			if(chess[i][j].equals("W_Queen")){
    				if(chessBoardSquares[i][j].getBackground().getRGB() == -8355712)
    					chessBoardSquares[i][j].setIcon(Q_W_B);
        			if(chessBoardSquares[i][j].getBackground().getRGB() == -1)
        				chessBoardSquares[i][j].setIcon(Q_W_W);
    			}
    			
    			if(chess[i][j].equals("W_Knight")){
    				if(chessBoardSquares[i][j].getBackground().getRGB() == -8355712)
    					chessBoardSquares[i][j].setIcon(K_W_B);
        			if(chessBoardSquares[i][j].getBackground().getRGB() == -1)
        				chessBoardSquares[i][j].setIcon(K_W_W);
    			}
    			
    			if(chess[i][j].equals("W_Rook")){
    				if(chessBoardSquares[i][j].getBackground().getRGB() == -8355712)
    					chessBoardSquares[i][j].setIcon(R_W_B);
        			if(chessBoardSquares[i][j].getBackground().getRGB() == -1)
        				chessBoardSquares[i][j].setIcon(R_W_W);
    			}
    			
    			
    			if(chess[i][j].equals("B_Pawn")){
    				if(chessBoardSquares[i][j].getBackground().getRGB() == -8355712)
    					chessBoardSquares[i][j].setIcon(P_B_B);
    				if(chessBoardSquares[i][j].getBackground().getRGB() == -1)
    					chessBoardSquares[i][j].setIcon(P_B_W);
    			}
    				
    			if(chess[i][j].equals("B_Bishop")){
    				if(chessBoardSquares[i][j].getBackground().getRGB() == -8355712)
    					chessBoardSquares[i][j].setIcon(B_B_B);
        			if(chessBoardSquares[i][j].getBackground().getRGB() == -1)
        				chessBoardSquares[i][j].setIcon(B_B_W);
    			}
    			
    			if(chess[i][j].equals("B_King")){
    				if(chessBoardSquares[i][j].getBackground().getRGB() == -8355712)
    					chessBoardSquares[i][j].setIcon(KI_B_B);
        			if(chessBoardSquares[i][j].getBackground().getRGB() == -1)
        				chessBoardSquares[i][j].setIcon(KI_B_W);
    			}
    			
    			if(chess[i][j].equals("B_Queen")){
    				if(chessBoardSquares[i][j].getBackground().getRGB() == -8355712)
    					chessBoardSquares[i][j].setIcon(Q_B_B);
        			if(chessBoardSquares[i][j].getBackground().getRGB() == -1)
        				chessBoardSquares[i][j].setIcon(Q_B_W);
    			}
    			
    			if(chess[i][j].equals("B_Knight")){
    				if(chessBoardSquares[i][j].getBackground().getRGB() == -8355712)
    					chessBoardSquares[i][j].setIcon(K_B_B);
        			if(chessBoardSquares[i][j].getBackground().getRGB() == -1)
        				chessBoardSquares[i][j].setIcon(K_B_W);
    			}
    			
    			if(chess[i][j].equals("B_Rook")){
    				if(chessBoardSquares[i][j].getBackground().getRGB() == -8355712)
    					chessBoardSquares[i][j].setIcon(R_B_B);
        			if(chessBoardSquares[i][j].getBackground().getRGB() == -1)
        				chessBoardSquares[i][j].setIcon(R_B_W);
    			}
    			if(chess[i][j].equals(""))
    				chessBoardSquares[i][j].setIcon(null);
    		}
    	}
    	
    	}
    
    public static int checkcondition(){
    	int m=0,n=0;
    	for(int i = 0 ; i < 8 ; i++){
    		for(int j  = 0 ; j < 8 ; j++){
    			if(chess[i][j] == "W_King"){
    				m = i+1;
    				n = j+1;
    			}
    		}
    	}
    	for(int i = 0 ; i < 8 ; i++){
    		for(int j = 0 ; j < 8 ; j++){
    			if(chess[i][j] == "B_Pawn" && b_pawn(i+1,j+1,m,n) == 1)
    				return 0;
    			else if(chess[i][j] == "B_Rook" && b_rook(i+1,j+1,m,n) == 1)
    				return 0;
    			else if(chess[i][j] == "B_Bishop" && b_bishop(i+1,j+1,m,n) == 1)
    				return 0;
    			else if(chess[i][j] == "B_Knight" && b_knight(i+1,j+1,m,n) == 1)
    				return 0;
    			else if(chess[i][j] == "B_Queen" && (b_bishop(i+1,j+1,m,n) == 1 || b_rook(i+1,j+1,m,n) == 1))
    				return 0;
    			else if(chess[i][j] == "B_King" && b_king(i+1,j+1,m,n) == 1)
    				return 0;
    		}
    	}
    	return 1;
    	
    }
    
    
}