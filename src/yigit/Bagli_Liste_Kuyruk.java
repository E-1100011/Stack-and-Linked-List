package yigit;

/**
 *
 * @author Emre
 */

import java.applet.Applet;
import java.awt.*;
import java.awt.event.*;
import javax.swing.JOptionPane;


public class Bagli_Liste_Kuyruk extends Applet implements ActionListener {
    
    BListe bListe = new BListe();
    
    private TextField tField;
    private Canvas canvas = new MyCanvas();
    private Graphics2D g2;
    private int y,x;  // Yığtın görüntülenebilmesi için gerekli
    
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
        if(!bListe.bosmu()) {
            g.drawString("front", 10, 212);
        }
    }
    
    
    @Override
    public void actionPerformed(ActionEvent e)  // Butona tıklandığını kontrol ediyor
    {
	String button = e.getActionCommand();
        char ch = button.charAt(0);
	
        switch (ch) {
            case 'E':
                if (tamsayimi(tField.getText())) bListe.sonaEkle(Integer.valueOf(tField.getText()));  //Yalnızca tam sayı girişi yapılmış mı kontol ediyor
                else infoBox("Giriş geçersiz\nTekrar deneyin");
                break;
            case 'Ç':
                bListe.Cikar();
                break;
            case 'G':
                bListe.Goster();
                break;
            default:
                break;
        }
    }
    
    class Dugum {
        public int eleman;
        public Dugum sonraki; // Sonraki düğümün adresi
        public Dugum (int gelenVeri) {
            eleman = gelenVeri;
        }
    }
    
    class BListe {
        private Dugum Front, Rear;
        private int elemanSayisi = 0;
        public BListe() {
            Front = null;
            Rear = null;
        }
        
        private void sonaEkle(int e) {  // Yığıta ekleme
            Dugum yeni = new Dugum(e);
            yeni.sonraki = null;
            if (Front == null) {
                Front = yeni;
                Rear = yeni;
            }
            else {
                Rear.sonraki = yeni;
                Rear = yeni;
            }
            elemanSayisi++;
            canvas.repaint();
            repaint();
            tField.setText("");
        }

        private int Cikar() {   // Son ekleneni yığıttan çıkarır
            if(Goster()) {
                Dugum temp = bListe.Front;
                bListe.Front = bListe.Front.sonraki;  // Bir sonraki elemana geç
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
            tField.setText(Integer.toString(bListe.Front.eleman));
            return true;
        }
    
        private boolean bosmu() {   // Yığıt boş mu kontrol eder
            return (Front == null) ? true : false;
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
            
            Dugum temp = bListe.Front;
            
            for(int i=0, x=0; i<bListe.elemanSayisi; i++, x+=50) {
                if(!bListe.bosmu()) {
                    if(i%7 == 0 && i != 0) {
                        x = 3;
                        if(i == 7)
                            x = 0;
                        g2.drawLine(350+x, 37+y, 355, 37+y);
                        g2.drawLine(355, 37+y, 355, 47+y);
                        g2.drawLine(355, 47+y, 3, 47+y);
                        g2.drawLine(3, 47+y, 3, 57+y);
                        y += 20;
                        x = 3;
                    }
                    
                    g2.drawRect(10+x, 30+y, 40, 15);
                    okCiz(x, 37+y, 10+x, 37+y);
                    
                    g2.drawString(Integer.toString(temp.eleman), 20+x, 43+y);
                    if(temp.sonraki==null){
                        g2.drawLine(30+x, 58+y, 30+x, 45+y);
                        g2.drawLine(27+x, 50+y, 30+x, 45+y);
                        g2.drawLine(33+x, 50+y, 30+x, 45+y);
                        g2.drawString("rear", 20+x, 70+y);
                    }
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


