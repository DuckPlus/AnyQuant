package util;




        import java.awt.*;
        import java.io.File;
        import java.io.IOException;

        import javax.imageio.ImageIO;
        import javax.swing.JDialog;
        import javax.swing.JFrame;
        import javax.swing.JLabel;
        import javax.swing.SwingConstants;

        import javafx.stage.Stage;

/**
 * 提示框
 * @author czq
 * @version 2015年12月5日 下午7:16:54
 */
@SuppressWarnings("serial")
public class TipsDialog extends JDialog{

    private static Stage stage = null;

    private final static int MAX_TIMES = 100;

    private final static int PAUSE_TIME = 1000;

    private int nowTime = 1;
    private String text = "";
    private static Rectangle bounds = new Rectangle(990, 600, 300, 100);

    private static  Image red = null;
    private static  Image green = null;

    static{

        try {
            green = ImageIO.read(new File("src/main/java/ui/source/img/TipsDialog-Right.png"));
            red = ImageIO.read(new File("src/main/java/ui/source/img/TipsDialog-Right.png"));

        } catch (IOException e) {
            e.printStackTrace();
        }


    }


    private Image bg = red;

    private final  JLabel label = new JLabel() {
        @Override
        public void paintComponent(Graphics g){

            setAlpha(g, nowTime/(double)MAX_TIMES);
            g.drawImage(bg, 0, 0, null);
            g.setFont(new Font("华文细黑", Font.PLAIN, 18));
            if(text.length() > 10){
                g.drawString(text.substring(0, 9), 96, 53);
                g.drawString(text.substring(9), 96, 73);
            }else{
                g.drawString(text, 96, 60);
            }

            super.paintComponent(g);
        }

    };

    /**
     * 构造器1 ， 使用默认颜色和字体
     * @param message
     */
    public TipsDialog(String message) {
        this(message , Color.red);
    }

    public TipsDialog(String message , Color color) {
        this(message, color, new Font("华文细黑", Font.PLAIN, 18));
    }

    public TipsDialog(String message , Color color , Font font) {
        this(message, color, font, false);

    }

    /**
     * 构造器2，可以设置所在frame的位置和大小
     * @param message
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public TipsDialog(String message,int x,int y,int width,int height){
        this(message);
        bounds=new Rectangle(x, y, width, height);

    }


    /**
     *
     * @param message
     * @param color
     * @param font
     * @param isCenter 消息是否放在图片中间（叠在图上面）
     */
    public TipsDialog(String message , Color color , Font font , boolean isCenter) {
       
        setUndecorated(true);
        if(color.equals(Color.GREEN)||color.equals(Color.BLUE)){
            bg = green;
        }

        setBackground(new Color(0f, 1f, 0f, 0f));
        label.setBackground(new Color(0f, 0f, 0f, 0f));
        label.setFont(font);
        text = message;
//        label.setText(message);

        bounds = new Rectangle((int) (stage.getX() + 792), (int)(stage.getY() +  553), 299, 139);
        setBounds(bounds);
        label.setHorizontalTextPosition(SwingConstants.CENTER);
        label.setVerticalTextPosition(SwingConstants.CENTER);

//        if(isCenter){
//        	label.setHorizontalTextPosition(SwingConstants.CENTER);
//        	label.setVerticalTextPosition(SwingConstants.CENTER);
//        }


        new Thread(new Runnable() {
            @Override
            public void run() {
                initGUI();

            }
        }).start();



    }



    private void initGUI() {

        setBounds(bounds);
        label.setBounds(0 , 0 , (int) bounds.getWidth() ,  (int)bounds.getHeight());
        //add(label);
        getContentPane().setBackground(new Color(0f, 0f, 0f, 0f));
        getContentPane().add(label);

        this.setVisible(true);
        while( nowTime < MAX_TIMES){

            nowTime++;
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            label.repaint();
        }

        try {
            Thread.sleep(PAUSE_TIME);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        while( nowTime > 0){
            nowTime--;
            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            label.repaint();

        }

        dispose();

    }

    public static void setStage(Stage stage){
        if(stage != null){
            TipsDialog.stage = stage;
        }

    }

    final static public void setAlpha(Graphics g, double d) {
        AlphaComposite alphacomposite = AlphaComposite
                .getInstance(3, (float) d);
        ((Graphics2D) g).setComposite(alphacomposite);
    }




}