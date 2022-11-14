import java.util.Vector;

class Particle{
    //Мерность графика
    private final int DIMENSION;
    //Position
    private Vector<Double> position = new Vector<>();
    //Minimum Position
    private Vector<Double> localMaxPosition = new Vector<>();
    //Vector
    private Vector<Double> vector = new Vector<>();
    //local min func
    private double localMaxFunc;


    //Constructor
    public Particle(int numberOfCoordinates){

        //Размерность
        this.DIMENSION = numberOfCoordinates;
        for (int i = 0; i < numberOfCoordinates; i++){
            // Random on interval [-500; 500]
            this.position.add(Math.random() * 1000 - 500);

            // Random on interval [0; 1]
            this.vector.add(Math.random() * 1);

            //localMinPosition = position
            this.localMaxPosition.add(this.position.get(i));
        }

        //LocalMinFunc
        this.localMaxFunc = this.func(this.position);


        //System out
        System.out.println("INIT POSITION" + " [x: " + position.get(0) + "] [Y: " + position.get(1) + "]");
        System.out.println("INIT VECTOR" + " [x: " + vector.get(0) + "] [Y: " + vector.get(1) + "]");
        System.out.println("INIT LOCAL MAXIMUM " + localMaxFunc);
    }

    //change vector
    public void changeVector(Vector<Double> globalMaxPosition){
        //Инерция
        double w = 0.712;
        //Весовой коэффицент
        double c = 1.1;
        //Случайные коэффиценты [0; 1]
        double r1 = Math.random() * 1;
        double r2 = Math.random() * 1;

        for(int i = 0; i < this.DIMENSION; i++){
            //change vector
            this.vector.set(i,(this.vector.get(i) * w) + (c * r1 * this.localMaxPosition.get(i)) -
                    (c * r1 * this.position.get(i)) + (c * r2 * globalMaxPosition.get(i)) -
                    (c * r2 * this.position.get(i)));
        }

        //System out
        System.out.println("CHANGED VECTOR" + " [X: " + vector.get(0) + "] [Y: " + vector.get(1) + "]");
    }

    //move particle
    public void move(){
        for (int i = 0; i < this.DIMENSION; i++){
            //Частица движется если новые координаты не вылезают за диапазон [-500; 500]
            if (this.position.get(i) + this.vector.get(i) <= 500 && this.position.get(i) + this.vector.get(i) >= -500){
                this.position.set(i, this.position.get(i) + this.vector.get(i));
            }
            if (this.func(this.position) >= this.localMaxFunc){
                this.localMaxFunc = this.func(this.position);
                this.localMaxPosition = this.position;
            }
        }
        //System out
        System.out.println("MOVE" + " [X: " + position.get(0) + "] [Y: " + position.get(1) + "]");
    }

    //func
    public double func(Vector<Double> position){
        double sum = 0;
        for (int i = 0; i < this.DIMENSION; i++){
            sum += -position.get(i) * Math.sin(Math.sqrt(Math.abs(position.get(i))));
        }
        return sum;
    }
    public double getLocalMaxFunc(){
        return this.localMaxFunc;
    }
    public Vector<Double> getLocalMaxPosition(){
        return this.localMaxPosition;
    }
    public Vector<Double> getPosition(){
        return this.position;
    }
    public Vector<Double> getVector(){
        return this.vector;
    }
}
