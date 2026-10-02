package projects;
import drawer.Canvas;
import drawer.Colors;

public class Solids {
    public static void main(String[] args){
        // esse inicio aqui vou melhorar
        Solids solids = new Solids();
        int density = 0;
        String mode = "donut";

        if(args.length == 0){
            mode = "donut";
            density = 9000;
        }else if(args[0].equals("random")){
            mode = args[0];
            density = 500;
        }else if(args[0].equals("cube")){
            mode = args[0];
            density = 500;
        }else{
            mode = args[0];
            density = 9000;
        }

        // aqui vou criar os formatos diferentes e salvar em um arrayzao
        int[][] points = new int[density][3];

        if(mode.equals("random")){
            points = solids.randomPoints(points);
        }else if(mode.equals("cube")){
            points = solids.openCube(points);
        }else{
            points = solids.donut(points);
        }

        solids.simulate(points);
    }

    private void simulate(int[][] points) {
        Canvas drawer = new Canvas();
        int width = 150;
        int height = 60;
        int length = points.length;

        drawer.startCanvas(width, height, true);

        double aX = 0;
        double aY = 0;
        double aZ = 0;

        double[][] pointsToDraw = new double[length][4];

        drawer.setColor(Colors.GREEN);

        while(true){
            drawer.drawCanvas(true);

            //aqui vou rotacionar o formato do array, independente de qual seja
            double senA = Math.sin(aX);
            double cosA = Math.cos(aX);
            double senB = Math.sin(aY);
            double cosB = Math.cos(aY);
            double senC = Math.sin(aZ);
            double cosC = Math.cos(aZ);

            //os pontos iniciais se mantem para serem a base de renderizar, sem mudar
            for(int i = 0; i < length; i++){
                double x = points[i][0];
                double y = points[i][1];
                double z = points[i][2];

                // rodando em volta do eixo X
                double rxY = ((y * cosA) - (z * senA));
                double rxZ = ((y * senA) + (z * cosA));

                // rodando em volta do eixo Y
                double ryX = ((x * cosB) - (rxZ * senB));
                double ryZ = (-(x * senB) + (rxZ * cosB));

                // rodando em volta do eixo Z
                double rzX = ((ryX * cosC) - (rxY * senC));
                double rzY = ((ryX * senC) + (rxY * cosC));
                // atualizando valores
                pointsToDraw[i][0] = rzX;
                pointsToDraw[i][1] = rzY;
                pointsToDraw[i][2] = ryZ;
                pointsToDraw[i][3] = getDistance((int)ryZ);
            }

            // organizar em ordem de profundidade
            java.util.Arrays.sort(pointsToDraw, (p1, p2) -> Double.compare(p1[2], p2[2]));

            // desenhar
            for(int i = 0; i < length; i++){
                int x = (int) ((width / 2) + pointsToDraw[i][0]);
                int y = (int) (((height) + pointsToDraw[i][1]) / 2);
                char c = (char) pointsToDraw[i][3];
                if(x > 0 && x < width && y > 0 && y < height){
                    drawer.drawChar(c,x,y);
                }
            }

            aX += 0.03;
            aY += 0.02;
            aZ += 0.01;

            drawer.delay(20);
        }
    }

    // densidade/ distancia dos pontos na profundidade
    private char getDistance(int z){
        // .:-=+*#%@
        if(z < -30){
            return '.';
        }else if( z < -15){
            return '-';
        }else if( z < 0){
            return '=';
        }else if( z < 15){
            return '%';
        }else if( z < 30){
            return '@';
        }else{
            return '#';
        }
    }

    // shapes diferentes
    private int[][] randomPoints(int[][] points){
        int range = 50;
        for(int i = 0; i < 500; i++){
            points[i][0] = (int)(Math.random() * range * 2) - range;
            points[i][1] = (int)(Math.random() * range * 2) - range;
            points[i][2] = (int)(Math.random() * range * 2) - range;
        }
        return  points;
    }

    private int[][] donut(int[][] points){
        int outerDensity = 300;
        int innerDensity = 30;

        int outerRadios = 30;
        int innerRadios = 10;

        int index = 0;

        double a = (2*Math.PI)/outerDensity;
        double b = (2*Math.PI)/innerDensity;

        // passar por todos angulos
        for(int i = 0; i < outerDensity; i++){
            for(int j = 0; j < innerDensity; j++){
                points[index][0] = (int) (Math.sin(a*i) * (outerRadios + innerRadios * Math.sin(b*j)));
                points[index][1] = (int) (Math.cos(a*i) * (outerRadios +innerRadios * Math.sin(b*j)));
                points[index][2] = (int) (Math.cos(b*j) * innerRadios);

                index++;
            }
        }

        return points;
    }

    // vou melhorar isso aqui.
    private int[][] openCube(int[][] points){
        int side = 40;
        int index = 0;

        // x lines
        for(int j = 0; j < side; j++){
            points[index][0] = j - (side/2);
            points[index][1] = side/2;
            points[index][2] = side/2;
            index++;
        }
        for(int j = 0; j < side; j++){
            points[index][0] = j - (side/2);
            points[index][1] = -side/2;
            points[index][2] = side/2;
            index++;
        }
        for(int j = 0; j < side; j++){
            points[index][0] = j - (side/2);
            points[index][1] = side/2;
            points[index][2] = -side/2;
            index++;
        }
        for(int j = 0; j < side; j++){
            points[index][0] = j - (side/2);
            points[index][1] = -side/2;
            points[index][2] = -side/2;
            index++;
        }

        // y lines
        for(int j = 0; j < side; j++){
            points[index][1] = j - (side/2);
            points[index][0] = side/2;
            points[index][2] = side/2;
            index++;
        }
        for(int j = 0; j < side; j++){
            points[index][1] = j - (side/2);
            points[index][0] = -side/2;
            points[index][2] = side/2;
            index++;
        }
        for(int j = 0; j < side; j++){
            points[index][1] = j - (side/2);
            points[index][0] = side/2;
            points[index][2] = -side/2;
            index++;
        }
        for(int j = 0; j < side; j++){
            points[index][1] = j - (side/2);
            points[index][0] = -side/2;
            points[index][2] = -side/2;
            index++;
        }

        // z lines
        for(int j = 0; j < side; j++){
            points[index][2] = j - (side/2);
            points[index][1] = side/2;
            points[index][0] = side/2;
            index++;
        }
        for(int j = 0; j < side; j++){
            points[index][2] = j - (side/2);
            points[index][1] = -side/2;
            points[index][0] = side/2;
            index++;
        }
        for(int j = 0; j < side; j++){
            points[index][2] = j - (side/2);
            points[index][1] = side/2;
            points[index][0] = -side/2;
            index++;
        }
        for(int j = 0; j < side; j++){
            points[index][2] = j - (side/2);
            points[index][1] = -side/2;
            points[index][0] = -side/2;
            index++;
        }
        return  points;
    }
}
