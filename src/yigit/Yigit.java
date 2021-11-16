package yigit;

/**
 *
 * @author Emre
 */

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JOptionPane;


public class Yigit extends Applet implements ActionListener {
    
    public final int MAX_SIZE=10;
    private int top = -1;
    private int[] elements = new int[MAX_SIZE];
    
    private TextField tField;
    private Canvas canvas = new MyCanvas();
    private Graphics2D g2;
    private int y;  // Yığtın görüntülenebilmesi için gerekli
    
    @Override
    public void init()
    {
        for(int i = 0; i<MAX_SIZE; i++) {
            elements[i] = Integer.MIN_VALUE;
        }
        
	setBackground(Color.white);
	setLayout(null);
        setSize(350, 350);
        
        
	tField = new TextField();
	tField.setBounds(120,100,120,35);
	this.add(tField);
        
	Button ekle=new Button("Ekle()");
	ekle.setBounds(60,20,80,50);
	this.add(ekle);
	ekle.addActionListener(this);
 
	Button cikar=new Button("Çıkar()");
	cikar.setBounds(150,20,80,50);
	this.add(cikar);
	cikar.addActionListener(this);
        
        Button goster=new Button("Göster()");
	goster.setBounds(240,20,80,50);
	this.add(goster);
	goster.addActionListener(this);
        
        this.add(canvas);
        
    }

    @Override
    public void paint(Graphics g) {
        g.drawString("Sayı", 80, 120);
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e)  // Butona tıklandığını kontrol ediyor
    {
	String button = e.getActionCommand();
        char ch = button.charAt(0);
	
        switch (ch) {
            case 'E':
                if (tamsayimi(tField.getText())) Ekle(Integer.valueOf(tField.getText()));  //Yalnızca tam sayı girişi yapılmış mı kontol ediyor
                else infoBox("Giriş geçersiz\nTekrar deneyin");
                break;
            case 'Ç':
                Cikar();
                break;
            case 'G':
                Goster();
                break;
            default:
                break;
        }
    }
    
    private boolean tamsayimi(String text) {    // Tam sayı mı kontrol ediyor
        boolean number = true;
        System.out.println(text);
        for(int i=0; i<text.length(); i++) {
            if(text.charAt(i)>='0' && text.charAt(i)<='9');
            else number = false;
        } return number;
   }
    
    private void Ekle(int x) {  // Yığıta ekleme
        if (dolumu()) {
            infoBox("Yığıt DOLU");
            return;
        }
        ++top;
        elements[top] = x;  // Yığıtın üzerine x’i ekle
        canvas.repaint();
        tField.setText("");
    }
    
    private void Cikar() {   // Son ekleneni yığıttan çıkarır
        if(Goster()) {
            elements[top] = Integer.MIN_VALUE;
            top--;
            canvas.repaint();
        }
    }
    
    private boolean Goster() { // Yığıtı gösterir
        if (bosmu()) {
            infoBox("Yığıt BOŞ");
            return false;
        }
        tField.setText(Integer.toString(elements[top]));
        return true;
    }
    
    private boolean dolumu() {  // Yığıt dolu mu kontol eder
        
        return (top == MAX_SIZE-1);
    }
    
    private boolean bosmu() {   // Yığıt boş mu kontrol eder
        
        return (top == -1);
    }
    
    class MyCanvas extends Canvas { // Yığıtı görüntülemek için oluşturduğum canvas

        public MyCanvas () {
           setBackground (Color.GRAY);
           setBounds(110, 150, 130, 180);
        }
        
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            g2 = (Graphics2D) g;
            y = 0;
            for(int i=0, j=0, k=0; i<10; i++, j+=15) {
                g2.drawString(String.valueOf(i), 20, 162-j);
                g2.drawRect(30, 150-j, 40, 15);
                if(!bosmu()) {
                    if(elements[i]!=Integer.MIN_VALUE)
                        g2.drawString(Integer.toString(elements[i]), 45, 163-j);

                    if(k!=top+1) {
                        y += 15;
                        k++;
                    }
                }
            }
            if(!bosmu()) g2.drawString("← Top", 75, 178-y);
        }
    }
    
    public static void infoBox(String infoMessage) {    // Hata oluşursa açılır pencere (popup) ile gösterilir
        JOptionPane.showMessageDialog(null, infoMessage, "Hata", JOptionPane.WARNING_MESSAGE);
    }
}

