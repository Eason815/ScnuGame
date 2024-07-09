import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class Test1 {

    private static int score = 10;  // 示例分数

    public static void main(String[] args) {
        int x = new Random().nextInt(700);
        int y = new Random().nextInt(500);
//        SwingUtilities.invokeLater(Test1::showCountdown);
        System.out.println(x);
        System.out.println(y);

        x = new Random().nextInt(700);
        y = new Random().nextInt(500);

        System.out.println(x);
        System.out.println(y);
    }

    private static void showCountdown() {
        JFrame frame = new JFrame("关卡倒计时");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 150);
        frame.setLayout(new BorderLayout());

        JLabel countdownLabel = new JLabel("5", SwingConstants.CENTER);
        countdownLabel.setFont(new Font("Serif", Font.BOLD, 48));
        frame.add(countdownLabel, BorderLayout.CENTER);

        JLabel scoreLabel = new JLabel("您的得分为：" + score, SwingConstants.CENTER);
        frame.add(scoreLabel, BorderLayout.NORTH);

        frame.setVisible(true);

        new Thread(() -> {
            for (int i = 5; i > 0; i--) {
                int finalI = i;
                SwingUtilities.invokeLater(() -> countdownLabel.setText(String.valueOf(finalI)));
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            SwingUtilities.invokeLater(() -> frame.dispose());
            // 在倒计时结束后，进行下一关的加载
            loadNextLevel();
        }).start();
    }

    private static void loadNextLevel() {
        // 加载下一关的逻辑
        System.out.println("进入下一关");
    }
}

