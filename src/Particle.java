import java.util.Vector;

class Particle{
    private final int id;
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
    public Particle(int id, int numberOfCoordinates){
        //id particle
        this.id = id;
        this.DIMENSION = numberOfCoordinates;
        for (int i = 0; i < numberOfCoordinates; i++){
            // Random on interval [-5; 5]
            this.position.add(Math.ceil((Math.random() * 10 - 5) * Math.pow(10,3)) / Math.pow(10,3));
            // Random on interval [0; 1]
            this.vector.add(Math.ceil((Math.random() * 1) * Math.pow(10,3)) / Math.pow(10,3));

            //localMinPosition = position
            this.localMaxPosition.add(this.position.get(i));
        }
        //LocalMinFunc
        this.localMaxFunc = this.func(this.position);


        System.out.print("-----INIT PARTICLE id: " + this.id);
        for (int i = 0; i < numberOfCoordinates; i++){
            System.out.print(" [x" + i + ": " + this.position.get(i) + "]");
        }
        System.out.print(" [Local Maximum: " + this.localMaxFunc + "]  Vector: ");
        for (int i = 0; i < numberOfCoordinates; i++){
            System.out.print(" [x" + i + ": " + this.vector.get(i) + "]");
        }
        System.out.println();
    }

    //change vector
    public void changeVector(Vector<Double> globalMaxPosition){
        double w = 0.2;
        //Весовой коэффицент
        double c = 1.1;
        //Случайные коэффиценты [0; 1]
        double r1 = Math.random() * 1;
        double r2 = Math.random() * 1;
        System.out.println("R1: " + r1);
        System.out.println("R2: " + r2);

        for(int i = 0; i < this.DIMENSION; i++){
            //change vector
            this.vector.set(i,(this.vector.get(i) * w) + (c * r1 * this.localMaxPosition.get(i)) -
                    (c * r1 * this.position.get(i)) + (c * r2 * globalMaxPosition.get(i)) -
                    (c * r2 * this.position.get(i)));
        }

        for (int i = 0; i < this.DIMENSION; i++){
            System.out.print("-----VECTOR: " + "[x" + i + ": " + this.vector.get(i) + "]");
        }
        System.out.println();
    }

    //move particle
    public void move(){
        for (int i = 0; i < this.DIMENSION; i++){
            if (this.position.get(i) + this.vector.get(i) <= 5 && this.position.get(i) + this.vector.get(i) >= -5){
                this.position.set(i, this.position.get(i) + this.vector.get(i));
            } else{
                this.vector.set(i, 0.0);
            }

            if (this.func(this.position) > this.localMaxFunc){
                this.localMaxFunc = this.func(this.position);
                this.localMaxPosition = this.position;
            }
        }
    }
    //func
    public double func(Vector<Double> position){
        double sum = 10 * DIMENSION;
        for (int i = 0; i < this.DIMENSION; i++){
            sum += Math.pow(position.get(i), 2) - (10 * Math.cos(2 * 3.14 * position.get(i)));
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
