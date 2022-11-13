import com.sun.source.tree.BreakTree;

import java.util.ArrayList;
import java.util.Vector;

class Swarm{
    //Мерность
    private final int DIMENSION;
    //global min position
    private Vector<Double> globalMaxPosition = new Vector<>();
    //global min func
    private double globalMaxFunc;
    //swarm
    private ArrayList<Particle> swarmList = new ArrayList<>();




    //Constructor
    public Swarm(int numberOfParticles, int numberOfCoordinates){
        //Количество корординат
        this.DIMENSION = numberOfCoordinates;
        //Создание частиц
        for (int i = 0; i < numberOfParticles; i++){
            swarmList.add(new Particle(i, numberOfCoordinates));
        }
        //Инициализация позиции Глобального минимума
        this.globalMaxPosition=swarmList.get(0).getLocalMaxPosition();
        //Инициализация глобального минимума
        this.globalMaxFunc = swarmList.get(0).getLocalMaxFunc();
    }

    public void playSwarm(){

        //Глобальный максимум
        for (Particle particle : swarmList) {
            if (particle.getLocalMaxFunc() > this.globalMaxFunc) {
                this.globalMaxFunc = particle.getLocalMaxFunc();
                for (int j = 0; j < this.DIMENSION; j++){
                    this.globalMaxPosition.set(j, particle.getLocalMaxPosition().get(j));
                }
                System.out.println("-----Global maximum = " + this.globalMaxFunc);
                for (int i = 0; i < DIMENSION; i++){
                    System.out.print(" [x" + i + ": " + this.globalMaxPosition.get(i) + "]");
                }
                System.out.println();
            }
        }


        //Движение частиц
        for (Particle particle : swarmList) {


            particle.move();
            particle.changeVector(this.globalMaxPosition);
        }
    }

    public ArrayList<Particle> getSwarmList(){
        return this.swarmList;
    }
    public double getGlobalMaxFunc(){
        return this.globalMaxFunc;
    }
    public Vector<Double> getGlobalMaxPosition(){
        return this.globalMaxPosition;
    }
}
