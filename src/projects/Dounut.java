// importando cores e logica de renderizacao no canvas ascii
// e dando nome ao projeto
package projects;
import drawer.Canvas;
import drawer.Colors;

// Dounut Isometrico ou ortografico, sem perspectiva nem luminosidade.
// pontos gerados, e depois rotacionados
// a distancia e controlada por tipos de caracteres na direcao z (profundidade)

public class Dounut {

    // inicializando o programa
    public static void main(String[] args){
        Dounut don = new Dounut();
        don.start();
    }

    private void start() {
        int initialRingDensity = 50; // Denciadade do anel maior
        int outerRingDensity = 20; // dencidade do anel no tubo
        int totalPoints = initialRingDensity * outerRingDensity; // todos os pontos
        int[][] points = new int[initialRingDensity * outerRingDensity][3]; // criando os pontos, cada ponto tem uma coordedana, por enquanto vazia
        
        int R = 70; // Raio do anel maior
        int r = 20; // Raio do anel menor

        int index = 0;

        for(int i = 0; i < initialRingDensity; i++){
            // passando por cada angulo do anel maior para depois criar um menor
            // essa e a formula de converter grau para radiano
            double t = (double) i / initialRingDensity * Math.PI * 2;

            for(int j = 0; j < outerRingDensity; j++){
                // passando por cada angulo do anel menor
                double t2 = (double) j / outerRingDensity * Math.PI * 2;

                double distance = R + r * Math.cos(t2); // todas distancias do pontos do anel menor para o centro do maior, (em linha reta por enquanto)
                
                // rotacao em volta do maior
                int x = (int) Math.round(distance * Math.cos(t)); 
                int y = (int) Math.round(distance * Math.sin(t));
                // em volta do menor
                int z = (int) Math.round(r * Math.sin(t2));

                points[index][0] = x;
                points[index][1] = y;
                points[index][2] = z;

                //salvar em cada ponto
                index++;
            }
        }

        Canvas drawer = new Canvas();
        drawer.startCanvas(120, 50, true);

        double A = 0.1;
        double B = 0.2;
        double C = 0.1;

        int[][] renderBuffer = new int[totalPoints][4]; // salvar aqui para nao sobrescrever quando organizar por distancia

        while(true){
            drawer.drawCanvas(true);

            //calculando antes de aplicar
            double cosA = Math.cos(A), sinA = Math.sin(A);
            double cosB = Math.cos(B), sinB = Math.sin(B);
            double cosC = Math.cos(C), sinC = Math.sin(C);

            // passar por todos os pontos
            for(int i = 0; i < initialRingDensity * outerRingDensity; i++){
                double x = points[i][0];
                double y = points[i][1];
                double z = points[i][2];

                // rotacao em todos os eixos, ja definimos o tanto que vai rodar ali em baixo
                double xy = y * cosA - z * sinA;
                double xz = y * sinA + z * cosA;

                double yx = x * cosB + xz * sinB;
                double yz = -x * sinB + xz * cosB;

                double zx = yx * cosC - xy * sinC;
                double zy = yx * sinC + xy * cosC;

                int rX = (int) Math.round(zx / 3.0);
                int rY = (int) Math.round(zy / 3.0);
                int rZ = (int) Math.round(yz / 3.0);

                // tentar pegar a profundidade
                char c = getCharacter(rZ);
                
                //salva no buffer
                renderBuffer[i][0] = rX;
                renderBuffer[i][1] = rY;
                renderBuffer[i][2] = rZ;
                renderBuffer[i][3] = c;
            }

            // Organiza por distancia de z
            java.util.Arrays.sort(renderBuffer, (p1, p2) -> Integer.compare(p1[2], p2[2]));

            // desenha agora pela distancia
            for(int i = 0; i < totalPoints; i++){
                char c = (char) renderBuffer[i][3];
                int drawX = 60 + renderBuffer[i][0];
                int drawY = 50 + renderBuffer[i][1];
                
                // na hora de desenhar, o z nao existe, mas colocarmos o caractere
                // e dividi por 2 porque o canvas em ascii e mais alto
                drawer.drawChar(c, drawX, drawY/2);
            }

            // velocidade de rotacao de cada 1
            A += 0.2;
            B += 0.05;
            C += 0.05;

            drawer.delay(50);
        }
    }

    private char getCharacter(int z){
        if(z < -10){
            return ' ';
        } else if(z < -1){
            return '.';
        } else if(z < 4){
            return '*';
        } else if(z < 13){
            return 'o';
        } else if(z < 14){
            return 'O';
        } else {
            return '@';
        }
    }
}