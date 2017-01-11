package frame;

import javax.swing.*;
import java.awt.*;

/**
 * from  http://www.codefans.net/articles/1743.shtml
 *
 * Created by syxya on 2017/1/11.
 */
public class SpleshWindow extends JWindow {
    private String statusStr=null; //信息窗口中要显示的信息
    private Image logoImg=null; //信息窗口中的显示图片

    public SpleshWindow(JFrame owner) { //以JFrame 对象为参数，可以是信息窗口和主窗口交互
        super( owner );
// 加载图片
        logoImg=getToolkit().getImage( ClassLoader.getSystemResource("images/splesh.jpg") );
// 等待图片加载完成
        java.awt.MediaTracker tracker=new java.awt.MediaTracker( this ); //创建一个MediaTracker 对象
        tracker.addImage( logoImg , 0 ); //将图片放入MediaTracker 对象中，序号为0
        try{ //等待直到图片加载完成
            tracker.waitForAll();
        }catch ( InterruptedException e ) {
            e.printStackTrace();
        }
// 设置信息窗体在屏幕上的显示位置
        setLocation( getToolkit().getScreenSize().width/2 - logoImg.getWidth(this)/2 , getToolkit().getScreenSize().height/2 -
                logoImg.getHeight(this)/2 );
        setSize( logoImg.getWidth(this) , logoImg.getHeight(this) ); // 设置窗口大小
    }


    public void setStatus( String status ){
        statusStr=status;
        paint( getGraphics() ); // 重画窗口来更新信息窗口中的显示信息
    }


    public void paint(Graphics g) {
/**@todo Override this java.awt.Component method*/
        super.paint(g);
//绘制图片
        if ( logoImg!=null )
            g.drawImage( logoImg , 0 , 0 , this );
//绘制显示信息
        if ( statusStr!=null ){
            g.setColor(Color.red);
            g.drawString( statusStr , 240 , getSize().height-250 );
        }
    }
}
