import java.util.Vector;

class Particle{
    private int id;
    //Мерность графика
    private int DIMENSION;
    //Position
    private Vector<Double> position = new Vector<>();
    //Minimum Position
    private Vector<Double> localMinPosition = new Vector<>();
    //Vector
    private Vector<Double> vector = new Vector<>();
    //local min func
    private double localMinFunc;


    //Constructor
    public Particle(int id, int numberOfCoordinates){
        //id particle
        this.id = id;
        this.DIMENSION = numberOfCoordinates;
        for (int i = 0; i < numberOfCoordinates; i++){
            // Random on interval [-100; 100]
            this.position.add(Math.ceil((Math.random() * 200 - 100) * Math.pow(10,3)) / Math.pow(10,3));
            // Random on interval [0; 1]
            this.vector.add(Math.ceil((Math.random() * 1) * Math.pow(10,3)) / Math.pow(10,3));

            //localMinPosition = position
            this.localMinPosition.add(this.position.get(i));
        }
        //LocalMinFunc
        this.localMinFunc = this.func(this.position);


        System.out.print("FIRST id: " + this.id);
        for (int i = 0; i < numberOfCoordinates; i++){
            System.out.print(" x" + i + "= " + this.position.get(i));
        }
        System.out.println(" localMin = " + this.localMinFunc);

    }

    //change vector
    public void changeVector(Vector<Double> globalMinPosition){
        //Инерция
        double w = 0.729;
        //Весовой коэффицент
        double c = 1.49;
        //Случайные коэффиценты [0; 1]
        double r1 = Math.random() * 1;
        double r2 = Math.random() * 1;

        for(int i = 0; i < this.DIMENSION; i++){
            //change vector
            this.vector.set(i, (w * this.vector.get(i)) + (c * r1 * this.localMinPosition.get(i)) -
                    (c * r1 * this.position.get(i)) + (c * r2 * globalMinPosition.get(i)) -
                    (c * r2 * this.position.get(i)));
        }
    }

    //move particle
    public void move(){
        for (int i = 0; i < this.DIMENSION; i++){
            this.position.set(i, this.position.get(i) + this.vector.get(i));

            if (this.func(this.position)  < this.localMinFunc){
                this.localMinFunc = this.func(this.position);
                this.localMinPosition = this.position;
            }
        }
    }
    //func
    public double func(Vector<Double> position){
        double sum = 0;
        for (int i = 0; i < this.DIMENSION; i++){
            sum += Math.pow(position.get(i), 2);
        }
        return sum;
        //return (2 * Math.sin(x + y + 10)) / (Math.sqrt(2) * x + y + 0.5);
    }
    public double getLocalMinFunc(){
        return this.localMinFunc;
    }
    public Vector<Double> getLocalMinPosition(){
        return this.localMinPosition;
    }
    public Vector<Double> getPosition(){
        return this.position;
    }
    public void out(){
        System.out.print("ID: " + this.id);
        for (int i = 0; i < this.DIMENSION; i++){
            System.out.print("  x" + i + "= " + this.position.get(i));
        }
        for (int i = 0; i < this.DIMENSION; i++){
            System.out.print("  v" + i + "= " + this.vector.get(i));
        }
        System.out.println(" Local Min: " + this.localMinFunc);
    }
}
