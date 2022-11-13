import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

public class Panel extends JPanel{
    //Размер экрана
    static final int SCREEN_WIDTH = 900;
    static final int SCREEN_HEIGHT = 600;
    //Рзамер частицы
    static final int PARTICLE_SIZE = 60;

    static final int DELAY = 1000;

    //Элементы управления
    JTextField numberOfParticles = new JTextField();
    JButton create = new JButton("Create Swarm");
    JButton start = new JButton("Start");
    JButton stop = new JButton("Stop");
    JTextField result = new JTextField();
    JTextField position = new JTextField();
    JTextField positionX = new JTextField();
    JButton delete = new JButton("Delete");
    JButton oneStep = new JButton("one step");
    //Рой
    Swarm swarm;


    Timer timer;
    boolean flag = false;
    Panel(){
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);


        this.numberOfParticles.setBackground(Color.WHITE);
        this.create.setBackground(Color.WHITE);
        this.start.setBackground(Color.WHITE);
        this.stop.setBackground(Color.WHITE);
        this.result.setBackground(Color.WHITE);
        this.position.setBackground(Color.WHITE);
        this.positionX.setBackground(Color.WHITE);

        this.delete.setBackground(Color.WHITE);
        this.oneStep.setBackground(Color.WHITE);

        this.result.setEditable(false);
        this.position.setEditable(false);

        //Количество частиц
        this.numberOfParticles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!numberOfParticles.getText().matches("\\d+")) {
                    JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame, "Введите число");
                    numberOfParticles.setText("");
                } else if ( Integer.parseInt(numberOfParticles.getText()) < 1){
                    JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame, "Количество частиц должно быть больше 1");
                    numberOfParticles.setText("");
                } else if (Integer.parseInt(numberOfParticles.getText()) > 500){
                    JFrame frame = new JFrame();
                    JOptionPane.showMessageDialog(frame, "Количество частиц должно быть меньше 500");
                    numberOfParticles.setText("");
                } else{
                    numberOfParticles.setEditable(false);
                }
            }
        });
        //Создание роя
        this.create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int temp = Integer.parseInt(numberOfParticles.getText());
                swarm = new Swarm(temp, 2);
                repaint();
            }
        });
        //Запуск симуляции
        this.start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flag = true;
                for (int i = 0; i < 100; i++){
                    if (!flag){
                        break;
                    }
                    swarm.playSwarm();
                    result.setText("" + Math.ceil(swarm.getGlobalMaxFunc() *
                            Math.pow(10,3))/Math.pow(10,3));
                    position.setText("X: " + swarm.getGlobalMaxPosition().get(0) + " Y: " + swarm.getGlobalMaxPosition().get(1));
                    repaint();
                }
            }
        });
        //Остановка симуляции
        this.stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                flag = false;
            }
        });
        //Удаление роя
        this.delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                numberOfParticles.setEditable(true);
                numberOfParticles.setText("");

                result.setText("");
                position.setText("");
                swarm = null;
                repaint();
            }
        });
        this.oneStep.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                swarm.playSwarm();
                result.setText("" + Math.ceil(swarm.getGlobalMaxFunc() *
                        Math.pow(10,3))/Math.pow(10,3));
                position.setText("Y: " + swarm.getGlobalMaxPosition().get(1));
                positionX.setText("X: " + swarm.getGlobalMaxPosition().get(0));
                repaint();
            }
        });


        this.add(numberOfParticles);
        this.add(create);
        this.add(start);
        this.add(stop);
        this.add(result);
        this.add(position);
        this.add(positionX);
        this.add(delete);
        this.add(oneStep);
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        //Элементы управления
        this.numberOfParticles.setBounds(50, 20, 200, 40);
        this.create.setBounds(50, 80, 200, 40);
        this.start.setBounds(50, 140, 200, 40);
        this.stop.setBounds(50, 200, 200, 40);
        this.result.setBounds(50, 260, 200, 40);
        this.position.setBounds(50, 320, 200, 40);
        this.positionX.setBounds(50, 380, 200, 40);
        this.delete.setBounds(50, 440, 200, 40);
        this.oneStep.setBounds(50, 500, 200, 40);


        //График
        for (int i = 0; i < SCREEN_HEIGHT/PARTICLE_SIZE; i++){
            g.drawLine(i * PARTICLE_SIZE + 300, 0, i * PARTICLE_SIZE + 300, SCREEN_HEIGHT);
            g.drawLine(300, i * PARTICLE_SIZE, SCREEN_WIDTH, i*PARTICLE_SIZE );
        }
        g.setColor(Color.WHITE);
        g.drawLine(600, SCREEN_HEIGHT, 600,0);
        g.drawLine(SCREEN_WIDTH, 300, 300,300);
        g.drawString("Y 100", 560, 12);
        g.drawString("X 100", 860, 320);

        if (swarm != null) {
            //Отрисовка частицы
            ArrayList<Particle> swarmList = swarm.getSwarmList();
            for (Particle particle : swarmList){
                double temp = particle.getPosition().get(0);
                double temp2 = particle.getPosition().get(1);
                double tempVector = particle.getVector().get(0);
                double tempVector2 = particle.getVector().get(1);
                g.fillOval((int) temp * 60 + 600, (int) temp2 * 60 + 300,12,12);
                g.drawLine((int) temp * 60 + 600, (int) temp2 * 60 + 300,(int) temp * 60 + 600 +
                                (int) tempVector * 60,(int) temp2 * 60 + 300 + (int) tempVector2 * 50);
            }
        }
    }
}
