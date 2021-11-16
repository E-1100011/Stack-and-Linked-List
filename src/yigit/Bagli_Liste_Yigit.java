package yigit;

/**
 *
 * @author Emre
 */

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JOptionPane;


public class Bagli_Liste_Yigit extends Applet implements ActionListener {
    
    private int top = -1;
    Yigin yigin = new Yigin();
    
    private TextField tField;
    private Canvas canvas = new MyCanvas();
    private Graphics2D g2;
    private int y;  // Yığtın görüntülenebilmesi için gerekli
    
    @Override
    public void init()
    {
        
	setBackground(Color.white);
	setLayout(null);
        setSize(450, 350);
        
        
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
        if(!yigin.bosmu()) g.drawString("Top", 14, 212);
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e)  // Butona tıklandığını kontrol ediyor
    {
	String button = e.getActionCommand();
        char ch = button.charAt(0);
	
        switch (ch) {
            case 'E':
                if (tamsayimi(tField.getText())) yigin.Ekle(Integer.valueOf(tField.getText()));  //Yalnızca tam sayı girişi yapılmış mı kontol ediyor
                else infoBox("Giriş geçersiz\nTekrar deneyin");
                break;
            case 'Ç':
                yigin.Cikar();
                break;
            case 'G':
                yigin.Goster();
                break;
            default:
                break;
        }
    }
    
    public class YiginDugumu {
        int eleman;
        YiginDugumu sonraki;
        YiginDugumu(int e){
        eleman = e; sonraki = null;
        }
    }
    public class Yigin {
        private YiginDugumu top;
        private int elemanSayisi=0;
        public Yigin() {top = null;}
        
        public int Top(){
            if (bosmu()){
                infoBox("Yığıt BOŞ");
                return -1; // Hata
            }
            return top.eleman;
        }
        
        private void Ekle(int e) {  // Yığıta ekleme
            YiginDugumu x = new YiginDugumu(e);
            x.sonraki = top;
            top = x;
            elemanSayisi++;
            canvas.repaint();
            repaint();
            tField.setText("");
        }

        private int Cikar() {   // Son ekleneni yığıttan çıkarır
            if(Goster()) {
                YiginDugumu temp = top;
                top = top.sonraki;  // Bir sonraki elemana geç
                elemanSayisi--;
                canvas.repaint();
                repaint();
                return temp.eleman;
            }return -1;
        }

        private boolean Goster() { // Yığıtı gösterir
            if (bosmu()) {
                infoBox("Yığıt BOŞ");
                return false;
            }
            tField.setText(Integer.toString(yigin.Top()));
            return true;
        }
    
        private boolean bosmu() {   // Yığıt boş mu kontrol eder
            if (top == null)
                return true;
            else
                return false;
        }
    }; 
    
    private boolean tamsayimi(String text) {    // Tam sayı mı kontrol ediyor
        boolean number = true;
        for(int i=0; i<text.length(); i++) {
            if(text.charAt(i)>='0' && text.charAt(i)<='9');
            else number = false;
        } return number;
   }   
    
    class MyCanvas extends Canvas { // Yığıtı görüntülemek için oluşturduğum canvas
        
        public MyCanvas () {
           setBackground (Color.GRAY);
           setBounds(40, 170, 360, 140);
        }
        
        @Override
        public void paint(Graphics g) {
            super.paint(g);
            g2 = (Graphics2D) g;
            y = 0;
            YiginDugumu temp = yigin.top;
            
            for(int i=0, j=0; i<yigin.elemanSayisi; i++, j+=50) {
                if(!yigin.bosmu()) {
                    if(i%7 == 0 && i != 0) {
                        System.out.println(i%7 == 0);
                        j = 3;
                        if(i == 7)
                            j = 0;System.out.println("j:"+j);
                        g2.drawLine(350+j, 37+y, 355, 37+y);
                        g2.drawLine(355, 37+y, 355, 47+y);
                        g2.drawLine(355, 47+y, 3, 47+y);
                        g2.drawLine(3, 47+y, 3, 57+y);
                        y += 20;
                        j = 3;
                    }
                    
                    g2.drawRect(10+j, 30+y, 40, 15);
                    okCiz(j, 37+y, 10+j, 37+y);
                    
                    g2.drawString(Integer.toString(temp.eleman), 20+j, 43+y);
                    temp = temp.sonraki;
                }
            }
        }
        public void okCiz(int x1, int y1, int x2, int y2) {
            g2.drawLine(x1, y1, x2, y2);
            g2.drawLine(x2-3, y1-2, x2, y2);
            g2.drawLine(x2-3, y1+2, x2, y2);
        }
    }
    
    public static void infoBox(String infoMessage) {    // Hata oluşursa açılır pencere (popup) ile gösterilir
        JOptionPane.showMessageDialog(null, infoMessage, "Hata", JOptionPane.WARNING_MESSAGE);
    }
}

