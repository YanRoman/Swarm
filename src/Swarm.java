import com.sun.source.tree.BreakTree;

import java.util.ArrayList;
import java.util.Vector;

class Swarm{
    //Мерность
    private final int DIMENSION;
    //global min position
    private Vector<Double> globalMinPosition = new Vector<>();
    //global min func
    private double globalMinFunc;
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
        this.globalMinPosition=swarmList.get(0).getLocalMinPosition();
        //Инициализация глобального минимума
        this.globalMinFunc = swarmList.get(0).getLocalMinFunc();
    }

    public void playSwarm(){
        for (Particle particle : swarmList) {
            if (particle.getLocalMinFunc() < this.globalMinFunc) {
                this.globalMinFunc = particle.getLocalMinFunc();
                for (int j = 0; j < this.DIMENSION; j++){
                    this.globalMinPosition.set(j, particle.getLocalMinPosition().get(j));
                }
                System.out.println("new Global minimum = " + this.globalMinFunc);
            }
        }


        //change vectors and move particle
        for (Particle particle : swarmList) {

            particle.changeVector(this.globalMinPosition);
            particle.move();
            particle.out();
        }
    }

    public ArrayList<Particle> getSwarmList(){
        return this.swarmList;
    }
    public double getGlobalMinFunc(){
        return this.globalMinFunc;
    }
    public Vector<Double> getGlobalMinPosition(){
        return this.globalMinPosition;
    }
}
