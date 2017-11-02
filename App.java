import java.util.Scanner;

public class App {

	public static void main(String[] args) {

		Scanner in = new Scanner(System.in);
		
		System.out.println("Insira o nome do arquivo para leitura: ");
		String arq = in.next();
		
		in.close();
		
		Arquivo a = new Arquivo();
		
		a.lerStopwords();
		a.open(arq+".txt");
		a.ordena();
		a.writingFile(arq+"2.txt");		
		System.out.println(a);
	}

}
