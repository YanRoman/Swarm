import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Time;
import java.util.ArrayList;

public class Panel extends JPanel{
    //Рой
    Swarm swarm;

    //Количество частиц
    int numberOfParticles = 4;

    //Ширина графика
    static final int GRAPH_WIDTH = 500;

    //Высота графика
    static final int GRAPH_HEIGHT = 500;

    //ширина панели с элементами управления
    static final int BUTTONS_PANEL_WIDTH = 300;

    //Элементы управления
    JButton bNumberOfParticles = new JButton("Select number of particles");
    JButton create = new JButton("Create Swarm " + numberOfParticles + " Particles");
    JButton start = new JButton("100 steps");
    JTextField result = new JTextField("Result");
    JTextField position = new JTextField("Y position");
    JTextField positionX = new JTextField("X position");
    JButton delete = new JButton("Delete");
    JButton oneStep = new JButton("One step");

    //Конструктор
    Panel(){
        //Инициализация Panel
        this.setPreferredSize(new Dimension(800,500));
        this.setBackground(Color.BLACK);
        this.setFocusable(true);

        //Устанавливаем цвет background
        this.bNumberOfParticles.setBackground(Color.WHITE);
        this.create.setBackground(Color.WHITE);
        this.start.setBackground(Color.WHITE);
        this.result.setBackground(Color.WHITE);
        this.position.setBackground(Color.WHITE);
        this.positionX.setBackground(Color.WHITE);
        this.delete.setBackground(Color.WHITE);
        this.oneStep.setBackground(Color.WHITE);

        //Запрещаем редактирование
        this.result.setEditable(false);
        this.position.setEditable(false);
        this.positionX.setEditable(false);

        //Количество частиц
        this.bNumberOfParticles.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame frame = new JFrame();
                String temp = JOptionPane.showInputDialog(frame, "Enter number of particles");

                //Проверка на корректность введенных данных
                if (temp != null){
                    if(!temp.matches("\\d+")) {
                        JOptionPane.showMessageDialog(frame, "Введите число",
                                "Inane error", JOptionPane.ERROR_MESSAGE);
                    } else if (Integer.parseInt(temp) < 1){
                        JOptionPane.showMessageDialog(frame, "Количество частиц должно быть больше 1",
                                "Inane error", JOptionPane.ERROR_MESSAGE);
                    } else if (Integer.parseInt(temp) > 500){
                        JOptionPane.showMessageDialog(frame, "Количество частиц должно быть меньше 500",
                                "Inane error", JOptionPane.ERROR_MESSAGE);
                    } else{
                        numberOfParticles = Integer.parseInt(temp);
                    }
                    create.setText("Create Swarm " + numberOfParticles + " Particles");
                }

            }
        });
        //Создание роя
        this.create.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Создаем новый рой
                swarm = new Swarm(numberOfParticles, 2);
                //Отрисовываем
                repaint();
            }
        });
        //Запуск симуляции
        this.start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Делаем 100 итераций
                for (int i = 0; i < 100; i++){
                    swarm.playSwarm();

                    //Заполняем новый глобальный максимум
                    result.setText("" + Math.ceil(swarm.getGlobalMaxFunc() *
                            Math.pow(10,3))/Math.pow(10,3));

                    //Заполняем новую позицию глобального максимума
                    position.setText("Y: " + swarm.getGlobalMaxPosition().get(1));
                    positionX.setText("X: " + swarm.getGlobalMaxPosition().get(0));

                    //Отрисовываем
                    repaint();
                }
            }
        });
        //Удаление роя
        this.delete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Обновляем все элементы управления
                result.setText("Result");
                position.setText("Y position");
                positionX.setText("X position");
                //Удаляем рой
                swarm = null;
                numberOfParticles = 4;
                create.setText("Create Swarm " + numberOfParticles + " Particles");
                //Отрисовываем
                repaint();
            }
        });
        //Одна итерация
        this.oneStep.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //Запускаем улей на 1 итерацию
                swarm.playSwarm();

                //Заполняем новый глобальный максимум
                result.setText("" + Math.ceil(swarm.getGlobalMaxFunc() *
                        Math.pow(10,3))/Math.pow(10,3));

                //Заполняем новую позицию глобального максимума
                position.setText("Y: " + swarm.getGlobalMaxPosition().get(1));
                positionX.setText("X: " + swarm.getGlobalMaxPosition().get(0));

                //Отрисовываем
                repaint();
            }
        });
        this.add(bNumberOfParticles);
        this.add(create);
        this.add(start);
        this.add(result);
        this.add(position);
        this.add(positionX);
        this.add(delete);
        this.add(oneStep);
    }

    //Отрисовка Pane
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        draw(g);
    }

    //Отрисовка Pane
    public void draw(Graphics g){

        //Элементы управления
        this.bNumberOfParticles.setBounds(50, 20, 200, 40);
        this.create.setBounds(50, 80, 200, 40);
        this.start.setBounds(50, 140, 200, 40);
        this.oneStep.setBounds(50, 200, 200, 40);
        this.result.setBounds(50, 260, 200, 40);
        this.position.setBounds(50, 320, 200, 40);
        this.positionX.setBounds(50, 380, 200, 40);
        this.delete.setBounds(50, 440, 200, 40);

        //График
        for (int i = 0; i <= 100; i++){
            //Вертикальные линии
            g.drawLine(i * 10 + BUTTONS_PANEL_WIDTH, 0,
                       i * 10 + BUTTONS_PANEL_WIDTH, GRAPH_HEIGHT);
            //Горизонтальные линии
            g.drawLine(BUTTONS_PANEL_WIDTH, i * 10,
                    GRAPH_WIDTH + BUTTONS_PANEL_WIDTH, i*10);
        }

        g.setColor(Color.WHITE);
        //Вертикальная ось
        g.drawLine(GRAPH_WIDTH/2 + BUTTONS_PANEL_WIDTH, 0, GRAPH_WIDTH/2 + BUTTONS_PANEL_WIDTH,GRAPH_HEIGHT);
        //Горизонтальная ось
        g.drawLine(BUTTONS_PANEL_WIDTH, GRAPH_HEIGHT/2, GRAPH_HEIGHT+ BUTTONS_PANEL_WIDTH,GRAPH_HEIGHT/2);

        //Если рой создан
        if (swarm != null) {
            //Отрисовка частиц
            //Список частиц
            ArrayList<Particle> swarmList = swarm.getSwarmList();
            for(Particle particle : swarmList){
                //Делим на 2, так-как график [0;500] а значения [-500;500]
                double tempPositionX = particle.getPosition().get(0) / 2;
                double tempPositionY = particle.getPosition().get(1) / 2;


                //вычисления положения x на графике
                int posX = (int)tempPositionX + BUTTONS_PANEL_WIDTH + GRAPH_WIDTH/2;
                //вычисления положения y на графике
                int posY = GRAPH_HEIGHT - ((int)tempPositionY + GRAPH_HEIGHT/2);

                //Рисовка частицы, размером в 10 пикселей
                g.fillOval(posX, posY, 10, 10);
            }
        }
    }
}
